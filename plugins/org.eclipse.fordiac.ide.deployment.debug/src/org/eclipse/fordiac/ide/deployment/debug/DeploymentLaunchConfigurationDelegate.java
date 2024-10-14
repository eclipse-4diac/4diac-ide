/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.deployment.debug;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.IStatusHandler;
import org.eclipse.debug.core.model.IDisconnect;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.fordiac.ide.deployment.debug.DeploymentLaunchConfigurationAttributes.AllowTerminate;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;

public class DeploymentLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	public static final String MONITOR_MODE = "org.eclipse.fordiac.ide.deployment.debug.monitor"; //$NON-NLS-1$

	public static final String SYSTEM_FILE_ATTRIBUTE = "org.eclipse.fordiac.ide.deployment.debug.systemFile"; //$NON-NLS-1$

	public static final int ALREADY_RUNNING_CODE = 200;
	public static final IStatus ALREADY_RUNNING_STATUS = new Status(IStatus.ERROR,
			DeploymentLaunchConfigurationDelegate.class, ALREADY_RUNNING_CODE, "", null); //$NON-NLS-1$

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final IResource resource = DeploymentLaunchConfigurationAttributes.getSystemResource(configuration);
		final AutomationSystem system = DeploymentLaunchConfigurationAttributes.getSystem(configuration);
		if (system == null) {
			throw new CoreException(Status.error(Messages.DeploymentLaunchConfigurationDelegate_CannotFindSystem));
		}
		final Set<INamedElement> selection = DeploymentLaunchConfigurationAttributes.getSelection(configuration,
				system);
		final Duration pollingInterval = DeploymentLaunchConfigurationAttributes.getPollingInterval(configuration);
		final AllowTerminate allowTerminate = DeploymentLaunchConfigurationAttributes.getAllowTerminate(configuration);

		launch.setAttribute(SYSTEM_FILE_ATTRIBUTE, resource.getFullPath().toString());

		try {
			if (ILaunchManager.RUN_MODE.equals(mode)) {
				final DeploymentProcess process = new DeploymentProcess(system, selection, launch);
				process.start();
			} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
				final DeploymentDebugTarget debugTarget = new DeploymentDebugTarget(system, selection, launch,
						allowTerminate != AllowTerminate.NEVER, pollingInterval);
				debugTarget.start();
			} else if (MONITOR_MODE.equals(mode)) {
				final DeploymentDebugTarget debugTarget = new DeploymentDebugTarget(system, Set.of(), launch,
						allowTerminate == AllowTerminate.ALWAYS, pollingInterval);
				debugTarget.start();
			} else {
				throw new CoreException(Status.error(
						MessageFormat.format(Messages.DeploymentLaunchConfigurationDelegate_IllegalLaunchMode, mode)));
			}
		} catch (final DeploymentException e) {
			throw new CoreException(Status.error(Messages.DeploymentLaunchConfigurationDelegate_DeploymentError, e));
		}
	}

	@Override
	public boolean preLaunchCheck(final ILaunchConfiguration configuration, final String mode,
			final IProgressMonitor monitor) throws CoreException {
		final IResource resource = DeploymentLaunchConfigurationAttributes.getSystemResource(configuration);
		if (resource != null) {
			final List<ILaunch> activeLaunches = getActiveLaunches(resource);
			if (!activeLaunches.isEmpty() && !handleActiveLaunches(resource, activeLaunches)) {
				return false;
			}
		}
		return super.preLaunchCheck(configuration, mode, monitor);
	}

	protected static boolean handleActiveLaunches(final IResource resource, final List<ILaunch> activeLaunches)
			throws CoreException {
		final IStatusHandler handler = DebugPlugin.getDefault().getStatusHandler(ALREADY_RUNNING_STATUS);
		if (handler == null) {
			return false;
		}
		final boolean terminate = ((Boolean) handler.handleStatus(ALREADY_RUNNING_STATUS, resource.getName()))
				.booleanValue();
		if (!terminate) {
			return false;
		}
		terminateLaunches(activeLaunches);
		return true;
	}

	protected static void terminateLaunches(final List<ILaunch> launches) throws CoreException {
		for (final ILaunch launch : launches) {
			if (launch instanceof final IDisconnect disconnect && disconnect.canDisconnect()) {
				disconnect.disconnect();
			}
			if (launch.canTerminate()) {
				launch.terminate();
			}
			if (!launch.isTerminated()) {
				throw new CoreException(
						Status.error(Messages.DeploymentLaunchConfigurationDelegate_LaunchNotTerminated));
			}
		}
	}

	protected static List<ILaunch> getActiveLaunches(final IResource resource) {
		return Stream.of(DebugPlugin.getDefault().getLaunchManager().getLaunches())
				.filter(launch -> resource.getFullPath().toString().equals(launch.getAttribute(SYSTEM_FILE_ATTRIBUTE)))
				.toList();
	}

	@Override
	protected IProject[] getBuildOrder(final ILaunchConfiguration configuration, final String mode)
			throws CoreException {
		final IResource resource = DeploymentLaunchConfigurationAttributes.getSystemResource(configuration);
		if (resource != null) {
			final IProject project = resource.getProject();
			if (project != null) {
				return computeReferencedBuildOrder(new IProject[] { project });
			}
		}
		return super.getBuildOrder(configuration, mode);
	}

	@Override
	protected IProject[] getProjectsForProblemSearch(final ILaunchConfiguration configuration, final String mode)
			throws CoreException {
		return getBuildOrder(configuration, mode);
	}
}
