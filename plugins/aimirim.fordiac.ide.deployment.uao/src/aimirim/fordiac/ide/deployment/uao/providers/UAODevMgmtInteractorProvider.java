/*******************************************************************************
 * Copyright (c) 2017 Aimirim STI
 * 
 *  Contributors:
 *   - Pedro Ricardo
 *   - Felipe Adriano
 *******************************************************************************/
package aimirim.fordiac.ide.deployment.uao.providers;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractorProvider;
import aimirim.fordiac.ide.deployment.uao.UAODeploymentExecutor;
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