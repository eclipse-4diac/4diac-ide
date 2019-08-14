/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2019 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *   Alois Zoitl - re-enabled monitoring after command execution
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor.IDeviceManagementInteractorCloser;
import org.eclipse.fordiac.ide.deployment.monitoringbase.AbstractMonitoringManager;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

public abstract class AbstractDeploymentCommand extends AbstractHandler {

	private Device device = null;

	/**
	 * Set of systems which where monitored and where monitoring has to be
	 * re-enabled after executing the deployment command.
	 */
	private Set<AutomationSystem> monitoredSystems = new HashSet<>();

	protected void setDevice(Device device) {
		this.device = device;
	}

	protected Device getDevice() {
		return device;
	}

	private static class OnlineDeploymentErrorCheckListener implements IDeploymentListener {

		private final AbstractDeploymentCommand currentObject;
		private String lastMessage = ""; //$NON-NLS-1$
		private String lastCommand = ""; //$NON-NLS-1$

		public OnlineDeploymentErrorCheckListener(AbstractDeploymentCommand currentObject) {
			this.currentObject = currentObject;
		}

		@Override
		public void connectionOpened() {
			// nothing to do here
		}

		@Override
		public void postCommandSent(String info, String destination, String command) {
			lastCommand = command;
		}

		@Override
		public void postResponseReceived(String response, String source) {
			if (response.contains("Reason")) { //$NON-NLS-1$
				showDeploymentError(response.substring(response.lastIndexOf("Reason") + 8, response.length() - 4), //$NON-NLS-1$
						source, currentObject, false);
			}
		}

		@Override
		public void connectionClosed() {
			// nothing to do here
		}

		public void showDeploymentError(String response, String source, AbstractDeploymentCommand currentElement,
				boolean showWithConsole) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IViewPart view = page.findView("org.eclipse.fordiac.ide.deployment.ui.views.Output"); //$NON-NLS-1$

			String currentMessage;

			if (!lastCommand.equals("")) {
				currentMessage = currentElement.getCurrentElementName() + "\n" + "Request: " + lastCommand
						+ "\nProblem: " + response + "\nSource: " + source
						+ "\nCheck the Deplopyment Console for more information";

			} else {
				currentMessage = currentElement.getCurrentElementName() + "\n" + "Problem: " + response + "\nSource: "
						+ source;
			}

			if ((null == view || showWithConsole) && (!lastMessage.equals(currentMessage))) {
				// when deleting Resources two messages are sent (KILL and delete Resource) and
				// both failed creating two popups with the same information
				Shell shell = Display.getDefault().getActiveShell();
				MessageDialog.openError(shell, currentElement.getErrorMessageHeader(), currentMessage);
				lastMessage = currentMessage;
			}
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		monitoredSystems.clear();
		List<Object> list = getObjectSelectionArray(event);
		for (Object currentElement : list) {
			if (prepareParametersToExecute(currentElement)) {
				IDeviceManagementInteractor interactor = DeviceManagementInteractorFactory.INSTANCE
						.getDeviceManagementInteractor(device);
				if (null != interactor) {
					DeploymentCoordinator.INSTANCE.enableOutput(interactor);
					OnlineDeploymentErrorCheckListener errorChecker = new OnlineDeploymentErrorCheckListener(this);
					interactor.addDeploymentListener(errorChecker);

					checkAndSaveMonitoringState(device.getAutomationSystem());

					try (IDeviceManagementInteractorCloser closer = interactor::disconnect) {
						interactor.connect();
						executeCommand(interactor);
					} catch (DeploymentException e) {
						errorChecker.showDeploymentError(e.getMessage(), DeploymentHelper.getMgrID(device), this, true);
					}
					interactor.removeDeploymentListener(errorChecker);
					DeploymentCoordinator.INSTANCE.disableOutput(interactor);
				} else {
					manageExecutorError();
				}
			}
		}
		reenableMonitoring();
		return null;
	}

	private void checkAndSaveMonitoringState(AutomationSystem system) {
		AbstractMonitoringManager monMan = AbstractMonitoringManager.getMonitoringManager();
		if (monMan.isSystemMonitored(system)) {
			monMan.disableSystem(system);
			monitoredSystems.add(system);
		}
	}

	private void reenableMonitoring() {
		AbstractMonitoringManager monMan = AbstractMonitoringManager.getMonitoringManager();
		monitoredSystems.forEach(monMan::enableSystem);
	}

	@SuppressWarnings("unchecked")
	private static List<Object> getObjectSelectionArray(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof StructuredSelection) {
			return ((StructuredSelection) selection).toList();
		}
		return Collections.emptyList();
	}

	protected abstract boolean prepareParametersToExecute(Object element);

	protected abstract void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException;

	protected void manageExecutorError() {
		DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(device, null);
	}

	protected abstract String getErrorMessageHeader();

	protected abstract String getCurrentElementName();

}
