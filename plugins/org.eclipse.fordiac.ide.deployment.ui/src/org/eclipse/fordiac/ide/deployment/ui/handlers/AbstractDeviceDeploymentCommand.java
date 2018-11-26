/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;

public abstract class AbstractDeviceDeploymentCommand extends AbstractDeploymentCommand {

	public AbstractDeviceDeploymentCommand() {
		super();
	}

	@Override
	protected boolean prepareParametersToExecute(Object element) {
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
	protected String getCurrentElementName() {
		return "Device: " + device.getName();
	}

}