/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.onlineedit.handlers;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;

public class OnlineCreateFBHandler extends AbstractOnlineFBHandler {

	@Override
	protected void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		executor.createFBInstance(new FBDeploymentData("", getResFB()), getResource()); //$NON-NLS-1$  //TODO correctly determine prefix
	}
	
	@Override	
	protected String getErrorMessageHeader() {
		return "Online Create Function Block Error";
	}

}
