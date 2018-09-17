/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.onlineedit.handlers;

import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;

public class OnlineStartFBHandler extends AbstractOnlineFBHandler {

	@Override
	protected void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		executor.startFB(resource, new FBDeploymentData("", resFB)); //$NON-NLS-1$  //TODO correctly determine prefix
	}
	
	@Override	
	protected String getErrorMessageHeader() {
		return "Online Start Function Block Error";
	}

}
