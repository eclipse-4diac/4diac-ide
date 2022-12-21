package org.eclipse.fordiac.ide.deployment.opcua.providers;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractorProvider;
import org.eclipse.fordiac.ide.deployment.opcua.OPCUADeploymentExecutor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;

public class OPCUADevMgmtInteractorProvider implements IDeviceManagementInteractorProvider{
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
	public IDeviceManagementInteractor createInteractor(Device dev,
			IDeviceManagementCommunicationHandler overrideHandler) {
		return new OPCUADeploymentExecutor(dev, overrideHandler);
	}
}