/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *   Alois Zoitl - re-enabled monitoring after command execution
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor.IDeviceManagementInteractorCloser;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractDeploymentCommand extends AbstractHandler {

	private Device device = null;

	/**
	 * Set of systems which where monitored and where monitoring has to be
	 * re-enabled after executing the deployment command.
	 */
	private final Set<AutomationSystem> monitoredSystems = new HashSet<>();

	protected void setDevice(final Device device) {
		this.device = device;
	}

	protected Device getDevice() {
		return device;
	}

	private static class OnlineDeploymentErrorCheckListener implements IDeploymentListener {

		private final AbstractDeploymentCommand currentObject;
		private String lastMessage = ""; //$NON-NLS-1$
		private String lastCommand = ""; //$NON-NLS-1$

		public OnlineDeploymentErrorCheckListener(final AbstractDeploymentCommand currentObject) {
			this.currentObject = currentObject;
		}

		@Override
		public void connectionOpened(final Device dev) {
			// nothing to do here
		}

		@Override
		public void postCommandSent(final String info, final String destination, final String command) {
			lastCommand = command;
		}

		@Override
		public void postResponseReceived(final String response, final String source) {
			if (response.contains("Reason")) { //$NON-NLS-1$
				showDeploymentError(response.substring(response.lastIndexOf("Reason") + 8, response.length() - 4), //$NON-NLS-1$
						source, currentObject);
			}
		}

		@Override
		public void connectionClosed(final Device dev) {
			// nothing to do here
		}

		public void showDeploymentError(final String response, final String source,
				final AbstractDeploymentCommand currentElement) {
			String currentMessage;

			if (!lastCommand.equals("")) { //$NON-NLS-1$
				currentMessage = MessageFormat.format(Messages.AbstractDeploymentCommand_ExtendedDeploymentErrorMessage,
						currentElement.getCurrentElementName(), lastCommand, response, source);
			} else {
				currentMessage = MessageFormat.format(Messages.AbstractDeploymentCommand_SimpleDeploymentErrorMessage,
						currentElement.getCurrentElementName(), response, source);
			}

			if (!lastMessage.equals(currentMessage)) {
				// when deleting Resources two messages are sent (KILL and delete Resource) and
				// both failed creating two popups with the same information
				final Shell shell = Display.getDefault().getActiveShell();
				MessageDialog.openError(shell, currentElement.getErrorMessageHeader(), currentMessage);
				lastMessage = currentMessage;
			}
		}
	}

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		monitoredSystems.clear();
		final List<Object> list = getObjectSelectionArray(event);
		for (final Object currentElement : list) {
			if (prepareParametersToExecute(currentElement)) {
				final IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE
						.getDeviceManagementInteractor(device);
				if (null != interactor) {
					final OnlineDeploymentErrorCheckListener errorChecker = new OnlineDeploymentErrorCheckListener(
							this);
					interactor.addDeploymentListener(errorChecker);

					try (IDeviceManagementInteractorCloser closer = interactor::disconnect) {
						interactor.connect();
						executeCommand(interactor);
					} catch (final DeploymentException e) {
						errorChecker.showDeploymentError(e.getMessage(), DeploymentHelper.getMgrIDSafe(device), this);
					}
					interactor.removeDeploymentListener(errorChecker);
				} else {
					manageExecutorError();
				}
			}
		}
		return null;
	}

	private static List<Object> getObjectSelectionArray(final ExecutionEvent event) {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof final IStructuredSelection structuredSelection) {
			return structuredSelection.toList();
		}
		return Collections.emptyList();
	}

	protected static String getPrefixFor(final FBNetworkElement fb) {
		if (fb.eContainer().eContainer() instanceof final INamedElement namedEl) {
			return namedEl.getQualifiedName() + "."; //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	protected abstract boolean prepareParametersToExecute(Object element);

	protected abstract void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException;

	protected void manageExecutorError() {
		DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(device, null);
	}

	protected abstract String getErrorMessageHeader();

	protected abstract String getCurrentElementName();

}
