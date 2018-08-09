/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;

/**
 * The Class KillDeviceAction.
 */
public class KillDeviceHandler extends AbstractDeploymentCommand {


	@Override
	public boolean prepareParametersToExecute(Object element) {
		if (element instanceof Device){
			device =  (Device) element;
			return true;
		}else if(element instanceof DeviceEditPart){
			device =  ((DeviceEditPart) element).getModel();
			return true;
		}
		return false;
	}

	@Override
	public void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		executor.killDevice(device);
	}
	
	@Override
	protected String getErrorMessageHeader() {
		return "Kill Device Error";
	}

	@Override
	protected String getCurrentElementName() {
		return "Device: " + device.getName();
	}
}
