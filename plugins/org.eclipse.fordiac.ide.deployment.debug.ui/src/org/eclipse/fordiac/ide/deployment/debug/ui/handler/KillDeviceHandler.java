/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - introduce abstract base class for devcie deployment commands
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;

/**
 * The Class KillDeviceAction.
 */
public class KillDeviceHandler extends AbstractDeviceDeploymentCommand {

	@Override
	public void executeCommand(final IDeviceManagementInteractor executor) throws DeploymentException {
		executor.killDevice(getDevice());
	}

	@Override
	protected String getErrorMessageHeader() {
		return Messages.KillDeviceHandler_KillDeviceError;
	}
}
