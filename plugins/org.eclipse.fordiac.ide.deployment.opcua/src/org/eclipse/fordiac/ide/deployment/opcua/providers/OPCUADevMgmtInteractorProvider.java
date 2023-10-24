/*******************************************************************************
 * Copyright (c) 2023, 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Markus Meingast
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.opcua.providers;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractorProvider;
import org.eclipse.fordiac.ide.deployment.opcua.OPCUADeploymentExecutor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class OPCUADevMgmtInteractorProvider implements IDeviceManagementInteractorProvider {
	private static final String PROFILE_NAME = "OPC UA"; //$NON-NLS-1$

	@Override
	public boolean supports(final String profile) {
		return getProfileName().equals(profile);
	}

	@Override
	public String getProfileName() {
		return PROFILE_NAME;
	}

	@Override
	public IDeviceManagementInteractor createInteractor(final Device dev,
			final IDeviceManagementCommunicationHandler overrideHandler) {
		return new OPCUADeploymentExecutor(dev, overrideHandler);
	}
}