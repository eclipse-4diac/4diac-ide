/*******************************************************************************
 * Copyright (c) 2024 AIMIRIM STI - https://en.aimirimsti.com.br/
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pedro Ricardo
 *   Felipe Adriano
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.uao.providers;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractorProvider;
import org.eclipse.fordiac.ide.deployment.uao.UAODeploymentExecutor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class UAODevMgmtInteractorProvider implements IDeviceManagementInteractorProvider {
	private static final String PROFILE_NAME = "UAO"; //$NON-NLS-1$

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
		return new UAODeploymentExecutor(dev);
	}
}