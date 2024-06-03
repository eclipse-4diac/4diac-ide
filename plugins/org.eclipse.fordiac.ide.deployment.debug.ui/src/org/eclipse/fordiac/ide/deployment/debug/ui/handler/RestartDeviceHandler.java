/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation
 *******************************************************************************/

package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;

public class RestartDeviceHandler extends AbstractDeviceDeploymentCommand {

	@Override
	protected void executeCommand(final IDeviceManagementInteractor executor) throws DeploymentException {
		// TODO Auto-generated method stub

	}

	@Override
	protected String getErrorMessageHeader() {
		// TODO Auto-generated method stub
		return null;
	}

}
