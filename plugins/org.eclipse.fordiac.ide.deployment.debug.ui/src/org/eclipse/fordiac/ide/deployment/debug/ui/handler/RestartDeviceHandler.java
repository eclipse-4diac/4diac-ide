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

import java.util.List;

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;

public class RestartDeviceHandler extends AbstractDeviceDeploymentCommand {

	@Override
	protected void executeCommand(final IDeviceManagementInteractor executor) throws DeploymentException {
		final List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> resources = executor.queryResources();
		final List<String> resourceNames = resources.stream()
				.map(org.eclipse.fordiac.ide.deployment.devResponse.Resource::getName).toList();

		for (final String resourceName : resourceNames) {
			final Resource resource = getDevice().getResourceNamed(resourceName);

			executor.stopResource(resource);
			executor.resetResource(resourceName);
			executor.startResource(resource);
		}
	}

	@Override
	protected String getErrorMessageHeader() {
		return ""; //$NON-NLS-1$
	}

}
