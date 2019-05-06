/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 				 2018 Johannes Kepler University 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - introduce abstract base class for devcie deployment commands  
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.ui.handlers;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

/**
 * The Class SendClearAction.
 */
public class CleanDeviceHandler extends AbstractDeviceDeploymentCommand {

	@Override
	protected void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		for (Resource res : getDevice().getResource()) {
			if (!res.isDeviceTypeResource()) {
				executor.deleteResource(res.getName());
			}
		}
	}

	@Override
	protected String getErrorMessageHeader() {
		return "Clean Device Error";
	}

}
