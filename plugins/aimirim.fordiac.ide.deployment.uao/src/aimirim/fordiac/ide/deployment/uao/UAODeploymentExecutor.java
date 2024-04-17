/*******************************************************************************
 * Copyright (c) 2017 Aimirim STI
 * 
 *  Contributors:
 *   - Pedro Ricardo
 *   - Felipe Adriano
 *******************************************************************************/
package aimirim.fordiac.ide.deployment.uao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import aimirim.fordiac.ide.deployment.uao.helpers.Constants;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class UAODeploymentExecutor implements IDeviceManagementInteractor {

	public enum ConnectionStatus {
		CONNECTED, DISCONNECTED, NOT_CONNECTED
	}

	private ConnectionStatus connectionStatus;
	private final Device device;

	private final List<IDeploymentListener> listeners = new ArrayList<>();

	public UAODeploymentExecutor(final Device dev) {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | constructor"); //$NON-NLS-1$
		this.device = dev;
		this.connectionStatus = ConnectionStatus.NOT_CONNECTED;
	}

	protected Device getDevice() {
		return device;
	}

	@Override
	public boolean isConnected() {
		return connectionStatus == ConnectionStatus.CONNECTED;
	}

	@Override
	public void connect() throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | connect");
	}

	@Override
	public void disconnect() throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | disconnect");
	}

	/************************************************************************
	 * Listener commands
	 ************************************************************************/

	@Override
	public void addDeploymentListener(final IDeploymentListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeDeploymentListener(final IDeploymentListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	/**************************************************************************
	 * Management commands
	 **************************************************************************/

	@Override
	public void createResource(final Resource resource) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | createResource"); //$NON-NLS-1$
//		final String resName = resource.getName();
//		final String resType = resource.getTypeName();
//		FordiacLogHelper.logWarning(resName);
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value)
			throws DeploymentException {
//		final String resName = resource.getName();
		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeResourceParameter"); //$NON-NLS-1$
//		FordiacLogHelper.logWarning(resName);
		
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value)
			throws DeploymentException {
//		final String devName = device.getName();
		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeResourceParameter"); //$NON-NLS-1$
//		FordiacLogHelper.logWarning(devName);
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | createFBInstance"); //$NON-NLS-1$
//		final String fbType = ForteTypeNameCreator.getForteTypeName(fbData.getFb());
//		final String fullFbName = MessageFormat.format(Constants.FB_NAME_FORMAT, fbData.getPrefix(),
//				fbData.getFb().getName());
//		if (fbType.isEmpty()) {
//			throw new DeploymentException(MessageFormat
//					.format(Messages.UAODeploymentExecutor_CreateFBInstanceFailedNoTypeFound, fullFbName));
//		}
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData,
			final VarDeclaration varDecl) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeFBParameter"); //$NON-NLS-1$
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | createConnection"); //$NON-NLS-1$
//		final IInterfaceElement sourceData = connData.getSource();
//		final IInterfaceElement destinationData = connData.getDestination();

//		if (sourceData == null || sourceData.getFBNetworkElement() == null || destinationData == null
//				|| destinationData.getFBNetworkElement() == null) {
//			throw new DeploymentException(MessageFormat
//					.format(Messages.UAODeploymentExecutor_CreateConnectionFailedNoDataFound, res.getName()));
//		}
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | startFB"); //$NON-NLS-1$
		
	}

	@Override
	public void startResource(final Resource resource) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | startResource"); //$NON-NLS-1$
//		final String resourceName = resource.getName();
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
//		final String devName = dev.getName();
		FordiacLogHelper.logWarning("UAODeploymentExecutor | startDevice"); //$NON-NLS-1$
		
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | deleteResource"); //$NON-NLS-1$
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | deleteFB"); //$NON-NLS-1$
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {

		FordiacLogHelper.logWarning("UAODeploymentExecutor | deleteConnection"); //$NON-NLS-1$
//		final IInterfaceElement sourceData = connData.getSource();
//		final IInterfaceElement destinationData = connData.getDestination();

//		if (sourceData == null || sourceData.getFBNetworkElement() == null || destinationData == null
//				|| destinationData.getFBNetworkElement() == null) {
//			throw new DeploymentException(Messages.UAODeploymentExecutor_CreateConnectionFailedNoDataFound);
//		}

//		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
//		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		
//		final String resName = res.getName();
		
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
//		final String devName = dev.getName();
		FordiacLogHelper.logWarning("UAODeploymentExecutor | killDevice"); //$NON-NLS-1$
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | queryResources"); //$NON-NLS-1$
		return Collections.emptyList();
	}

	@Override
	public Response readWatches() throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | readWatches"); //$NON-NLS-1$
		return Constants.EMPTY_RESPONSE;
	}

	@Override
	public void addWatch(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | addWatch"); //$NON-NLS-1$
//		final String fullFbName = element.getQualifiedString();
//		final String resName = element.getResourceString();
		
	}

	@Override
	public void removeWatch(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | removeWatch"); //$NON-NLS-1$
//		final String fullFbName = element.getQualifiedString();
//		final String resName = element.getResourceString();
	}

	@Override
	public void triggerEvent(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | triggerEvent"); //$NON-NLS-1$
//		final String fullFbName = element.getQualifiedString();
//		final String resName = element.getResourceString();
	}

	@Override
	public void forceValue(final MonitoringBaseElement element, final String value) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | forceValue"); //$NON-NLS-1$
//		final String fullFbName = element.getQualifiedString();
//		final String resName = element.getResourceString();
	}

	@Override
	public void clearForce(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | clearForce"); //$NON-NLS-1$
//		final String fullFbName = element.getQualifiedString();
//		final String resName = element.getResourceString();
	}

	/**************************************************************************
	 * Helper Methods
	 **************************************************************************/

}