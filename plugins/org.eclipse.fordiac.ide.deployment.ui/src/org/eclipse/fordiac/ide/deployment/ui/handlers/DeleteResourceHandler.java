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

import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.systemconfiguration.editparts.ResourceEditPart;

/**
 * The Class DeleteResourceAction.
 */
public class DeleteResourceHandler extends AbstractDeploymentCommand {


	private Resource resource;
	
	@Override
	protected boolean prepareParametersToExecute(Object element) {
		device = null;
		if (element instanceof Resource){
			resource = (Resource) element;
			device =  resource.getDevice();
			if (null != device ){
				return true;	
			}
		}else if (element instanceof ResourceEditPart){
			resource = ((ResourceEditPart) element).getModel();
			device =  resource.getDevice();
			if (null != device ){
				return true;	
			}
		}
		return false;
	}

	@Override
	protected void executeCommand(IDeviceManagementInteractor executor) throws DeploymentException {
		executor.deleteResource(resource);
	}

	
	@Override
	protected void manageExecutorError() {
		DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(device, resource);		
	}
	
	@Override
	protected String getErrorMessageHeader() {
		return "Delete Resource Error";
	}

	@Override
	protected String getCurrentElementName() {
		return "Resource: " + resource.getName();
	}
	
}
