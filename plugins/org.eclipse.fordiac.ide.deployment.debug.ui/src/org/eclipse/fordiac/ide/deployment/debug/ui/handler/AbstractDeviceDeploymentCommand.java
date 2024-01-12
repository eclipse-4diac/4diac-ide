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
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.gef.EditPart;

public abstract class AbstractDeviceDeploymentCommand extends AbstractDeploymentCommand {

	protected AbstractDeviceDeploymentCommand() {
	}

	@Override
	protected boolean prepareParametersToExecute(final Object element) {
		if (element instanceof final Device device) {
			setDevice(device);
			return true;
		}
		if (element instanceof final EditPart editPart) {
			return prepareParametersToExecute(editPart.getModel());
		}
		return false;
	}

	@Override
	protected String getCurrentElementName() {
		return MessageFormat.format(Messages.AbstractDeviceDeploymentCommand_DeviceName, getDevice().getName());
	}

}