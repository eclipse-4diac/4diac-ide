/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler University
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import org.eclipse.fordiac.ide.deployment.ui.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.DeviceEditPart;

public abstract class AbstractDeviceDeploymentCommand extends AbstractDeploymentCommand {

	public AbstractDeviceDeploymentCommand() {
		super();
	}

	@Override
	protected boolean prepareParametersToExecute(Object element) {
		if (element instanceof Device) {
			setDevice((Device) element);
			return true;
		} else if (element instanceof DeviceEditPart) {
			setDevice(((DeviceEditPart) element).getModel());
			return true;
		}
		return false;
	}

	@Override
	protected String getCurrentElementName() {
		return Messages.AbstractDeviceDeploymentCommand_DeviceName + getDevice().getName();
	}

}