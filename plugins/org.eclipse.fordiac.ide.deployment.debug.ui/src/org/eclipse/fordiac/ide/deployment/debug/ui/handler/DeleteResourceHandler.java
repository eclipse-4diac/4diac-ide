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
 *   Jose Cabral, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.debug.ui.handler;

import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.DeploymentCoordinator;
import org.eclipse.fordiac.ide.deployment.debug.ui.Messages;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.gef.EditPart;

/**
 * The Class DeleteResourceAction.
 */
public class DeleteResourceHandler extends AbstractDeploymentCommand {

	private Resource resource;

	@Override
	protected boolean prepareParametersToExecute(final Object element) {
		setDevice(null);
		if (element instanceof final Resource resourceElement) {
			this.resource = resourceElement;
			setDevice(resourceElement.getDevice());
			return getDevice() != null;
		}
		if (element instanceof final EditPart editPart) {
			return prepareParametersToExecute(editPart.getModel());
		}
		return false;
	}

	@Override
	protected void executeCommand(final IDeviceManagementInteractor executor) throws DeploymentException {
		executor.deleteResource(resource.getName());
	}

	@Override
	protected void manageExecutorError() {
		DeploymentCoordinator.printUnsupportedDeviceProfileMessageBox(getDevice(), resource);
	}

	@Override
	protected String getErrorMessageHeader() {
		return Messages.DeleteResourceHandler_DeleteResourceError;
	}

	@Override
	protected String getCurrentElementName() {
		return MessageFormat.format(Messages.DeleteResourceHandler_Resource, resource.getName());
	}

}
