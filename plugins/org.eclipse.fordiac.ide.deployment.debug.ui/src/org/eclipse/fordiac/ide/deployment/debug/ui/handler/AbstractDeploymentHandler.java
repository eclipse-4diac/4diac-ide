/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentDebugDevice;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchUtils;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractDeploymentHandler<T extends INamedElement> extends AbstractHandler {

	private static final int CONNECTION_POLLING_INTERVAL = 100;

	@Override
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		if (HandlerUtil.getCurrentSelection(event) instanceof final IStructuredSelection selection) {
			final Shell shell = HandlerUtil.getActiveShell(event);
			try {
				for (final Object selectedElement : selection) {
					execute(selectedElement, shell);
				}
			} catch (final DeploymentException e) {
				ErrorDialog.openError(shell, null, null, Status.error(e.getLocalizedMessage(), e));
			}
		}
		return null;
	}

	private void execute(final Object selectedElement, final Shell shell)
			throws DeploymentException, ExecutionException {
		final List<T> targets = getTargets(selectedElement);
		if (targets == null) {
			return;
		}
		for (final T target : targets) {
			execute(target, shell);
		}
	}

	private void execute(final T target, final Shell shell) throws DeploymentException, ExecutionException {
		final Resource resource = DeploymentDebugWatchUtils.getResource(target);
		if (resource == null) {
			throw new DeploymentException(MessageFormat.format(Messages.AbstractDeploymentHandler_ElementNotInResource,
					target.getQualifiedName()));
		}
		final Optional<DeploymentDebugDevice> activeDevice = findActiveDevice(resource.getDevice());
		if (activeDevice.isEmpty()) {
			perform(target, resource, shell);
		} else {
			perform(target, resource, activeDevice.get().getDeviceManagementExecutorService(), shell);
		}
	}

	protected void perform(final T target, final Resource resource, final Shell shell)
			throws DeploymentException, ExecutionException {
		final AutomationSystem system = resource.getAutomationSystem();
		if (system == null) {
			throw new DeploymentException(MessageFormat.format(Messages.AbstractDeploymentHandler_ElementNotInSystem,
					target.getQualifiedName()));
		}
		if (!MessageDialog.openConfirm(shell, Messages.AbstractDeploymentHandler_LaunchDialogTitle,
				MessageFormat.format(Messages.AbstractDeploymentHandler_LaunchDialogMessage, system.getName()))) {
			return; // canceled
		}
		final ILaunch launch = launch(system);
		final IDeviceManagementInteractor interactor = waitForConnection(shell, launch, resource.getDevice());
		if (interactor != null) {
			perform(target, resource, interactor, shell);
		}
	}

	protected abstract void perform(T target, Resource resource, IDeviceManagementInteractor interactor, Shell shell)
			throws DeploymentException;

	protected abstract List<T> getTargets(Object selectedElement);

	protected static ILaunch launch(final AutomationSystem system) throws DeploymentException {
		final Optional<IResource> resource = getResource(system);
		if (resource.isEmpty()) {
			throw new DeploymentException(
					MessageFormat.format(Messages.AbstractDeploymentHandler_NoFileForSystem, system.getName()));
		}
		try {
			final ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			final ILaunchConfigurationType type = manager
					.getLaunchConfigurationType(DeploymentLaunchConfigurationAttributes.ID);
			final ILaunchConfigurationWorkingCopy configuration = type.newInstance(null,
					manager.generateLaunchConfigurationName(resource.get().getName()));
			configuration.setAttribute(DeploymentLaunchConfigurationAttributes.SYSTEM,
					resource.get().getFullPath().toString());
			return configuration.launch(DeploymentLaunchConfigurationDelegate.MONITOR_MODE, new NullProgressMonitor());
		} catch (final CoreException e) {
			throw new DeploymentException(e.getMessage(), e);
		}
	}

	protected static IDeviceManagementInteractor waitForConnection(final Shell shell, final ILaunch launch,
			final Device device) throws ExecutionException {
		final IDeviceManagementInteractor[] result = new IDeviceManagementInteractor[1];
		try {
			PlatformUI.getWorkbench().getProgressService()
					.busyCursorWhile(monitor -> result[0] = waitForConnection(launch, device, monitor));
		} catch (final InvocationTargetException e) {
			throw new ExecutionException(e.getMessage(), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new ExecutionException(e.getMessage(), e);
		}
		return result[0];
	}

	protected static IDeviceManagementInteractor waitForConnection(final ILaunch launch, final Device device,
			final IProgressMonitor monitor) throws InterruptedException {
		while (!monitor.isCanceled() && !launch.isTerminated()) {
			final Optional<DeploymentDebugDevice> activeDevice = findActiveDevice(launch, device);
			if (activeDevice.isPresent()) {
				return activeDevice.get().getDeviceManagementExecutorService();
			}
			Thread.sleep(CONNECTION_POLLING_INTERVAL);
		}
		return null;
	}

	protected static Optional<DeploymentDebugDevice> findActiveDevice(final Device device) {
		return getResource(device.getAutomationSystem()).map(DeploymentLaunchConfigurationDelegate::getActiveLaunches)
				.stream().flatMap(List::stream).map(launch -> findActiveDevice(launch, device))
				.flatMap(Optional::stream).findAny();
	}

	protected static Optional<DeploymentDebugDevice> findActiveDevice(final ILaunch launch, final Device device) {
		return Stream.of(launch.getDebugTargets()).filter(DeploymentDebugDevice.class::isInstance)
				.map(DeploymentDebugDevice.class::cast)
				.filter(activeDevice -> activeDevice.getName().equals(device.getName()) && activeDevice.isAlive())
				.findAny();
	}

	protected static Optional<IResource> getResource(final AutomationSystem system) {
		return Optional.ofNullable(system).map(AutomationSystem::getTypeEntry).map(TypeEntry::getFile);
	}
}
