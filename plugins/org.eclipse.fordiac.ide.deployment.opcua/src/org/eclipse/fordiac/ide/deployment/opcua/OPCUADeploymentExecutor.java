/*******************************************************************************
 * Copyright (c) 2022, 2024 Markus Meingast, Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.deployment.opcua;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static org.eclipse.milo.opcua.stack.core.util.ConversionUtil.toList;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.ResponseMapping;
import org.eclipse.fordiac.ide.deployment.interactors.ForteTypeNameCreator;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.opcua.helpers.Constants;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener2;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.SessionActivityListener;
import org.eclipse.milo.opcua.sdk.client.api.UaSession;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseDirection;
import org.eclipse.milo.opcua.stack.core.types.enumerated.BrowseResultMask;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.eclipse.milo.opcua.stack.core.types.structured.BrowseDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.CallMethodRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.CallMethodResult;
import org.eclipse.milo.opcua.stack.core.types.structured.CallResponse;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.ReferenceDescription;
import org.xml.sax.InputSource;

public class OPCUADeploymentExecutor implements IDeviceManagementInteractor {

	public enum ConnectionStatus {
		CONNECTED, DISCONNECTED, NOT_CONNECTED
	}

	private final OpcUaClient client;
	private ConnectionStatus connectionStatus;
	private final Device device;
	private NodeId resourceNode;

	private final List<CallMethodRequest> requests = new ArrayList<>();
	private final List<String> requestMessages = new ArrayList<>();
	private boolean combinedRequest = false;

	private final List<IDeploymentListener> listeners = new ArrayList<>();
	private final Map<String, NodeId> availableResources = new HashMap<>();

	private final ResponseMapping respMapping = new ResponseMapping();

	public OPCUADeploymentExecutor(final Device dev) {
		this.device = dev;
		this.client = createClient();
		this.resourceNode = null;
		this.connectionStatus = ConnectionStatus.NOT_CONNECTED;
	}

	protected OpcUaClient createClient() {
		try {
			String mgrId = DeploymentHelper.getMgrID(device);
			mgrId = mgrId.substring(1, mgrId.length() - 1);
			final List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(mgrId).get();
			final OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();

			cfg.setEndpoint(endpoints.get(0));
			final OpcUaClient newClient = OpcUaClient.create(cfg.build());

			newClient.addSessionActivityListener(new SessionActivityListener() {
				@Override
				public void onSessionActive(final UaSession session) {
					SessionActivityListener.super.onSessionActive(session);
					connectionStatus = ConnectionStatus.CONNECTED;
				}

				@Override
				public void onSessionInactive(final UaSession session) {
					SessionActivityListener.super.onSessionInactive(session);
					connectionStatus = ConnectionStatus.DISCONNECTED;
				}
			});
			return newClient;
		} catch (final DeploymentException e) {
			FordiacLogHelper
					.logError(MessageFormat.format(Messages.OPCUADeploymentExecutor_GetMgrIDFailed, e.getMessage()), e);
		} catch (final ExecutionException | UaException e) {
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_CreateClientFailed, e.getMessage()), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
		return null;
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
		if (client == null) {
			throw new DeploymentException(Messages.OPCUADeploymentExecutor_CouldNotConnectToDevice);
		}
		try {
			client.connect().get();
			for (final IDeploymentListener listener : listeners) {
				listener.connectionOpened(device);
			}
		} catch (final ExecutionException e) {
			throw new DeploymentException(Messages.OPCUADeploymentExecutor_CouldNotConnectToDevice, e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void disconnect() throws DeploymentException {
		if (isConnected()) {
			try {
				client.disconnect().get();
				for (final IDeploymentListener listener : listeners) {
					listener.connectionClosed(device);
				}
			} catch (final ExecutionException e) {
				throw new DeploymentException(Messages.OPCUADeploymentExecutor_CouldNotDisconnectFromDevice, e);
			} catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				FordiacLogHelper.logError(
						MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			}
		}
	}

	/**
	 * Sends single request
	 *
	 * @param destination - Only needed for displaying on Deployment Console
	 **/
	private synchronized CompletableFuture<CallMethodResult> sendREQ(final String destination,
			final CallMethodRequest request, final String message) {
		return client.call(request).thenCompose(result -> {
			if (!result.getStatusCode().isGood()) {
				displayCommand(result.getStatusCode(), destination, message);
			}
			return CompletableFuture.completedFuture(result);
		});
	}

	/**
	 * Sends list of requests
	 *
	 * @param destination - Only needed for displaying on Deployment Console
	 */
	private synchronized List<CallMethodResult> sendREQ(final String destination) throws IOException {
		CallResponse response;
		try {
			response = client.call(requests).get();
		} catch (final ExecutionException e) {
			throw new IOException(MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestFailed, destination), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return Collections.emptyList();
		}
		return handleResponse(response, destination);
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
		combinedRequest = true;
		final String resName = resource.getName();
		final String resType = resource.getTypeName();
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.CREATE_RESOURCE_NODE,
				new Variant[] { new Variant(resName), new Variant(resType) });
		final String message = MessageFormat.format(Constants.CREATE_RESOURCE_INSTANCE, resName, resType);
		CallMethodResult result = null;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_CreateResourceFailed, resName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
		if (result != null) {
			resourceNode = processResult(result);
		}
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value)
			throws DeploymentException {
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.WRITE_RESOURCE_NODE,
				new Variant[] { new Variant(resName), new Variant(value) });
		final String message = MessageFormat.format(Constants.WRITE_RESOURCE_PARAMETER, value);
		try {
			sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_WriteResourceFailed, resName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value)
			throws DeploymentException {
		final String devName = device.getName();
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.WRITE_DEVICE_NODE,
				new Variant[] { new Variant(value) });
		final String message = MessageFormat.format(Constants.WRITE_DEVICE_PARAMETER, value);
		try {
			sendREQ(devName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_WriteDeviceFailed, devName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		if (resourceNode == null) {
			return;
		}
		final String fbType = ForteTypeNameCreator.getForteTypeName(fbData.getFb());
		final String fullFbName = MessageFormat.format(Constants.FB_NAME_FORMAT, fbData.getPrefix(),
				fbData.getFb().getName());
		if (fbType.isEmpty()) {

			throw new DeploymentException(MessageFormat
					.format(Messages.OPCUADeploymentExecutor_CreateFBInstanceFailedNoTypeFound, fullFbName));
		}
		final CallMethodRequest request = new CallMethodRequest(resourceNode, Constants.CREATE_FB_NODE,
				new Variant[] { new Variant(fullFbName), new Variant(fbType) });
		final String message = MessageFormat.format(Constants.CREATE_FB_INSTANCE, fullFbName, fbType);
		requests.add(request);
		requestMessages.add(message);
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData,
			final VarDeclaration varDecl) throws DeploymentException {
		final String destination = MessageFormat.format(Constants.FB_PORT_NAME_FORMAT, fbData.getPrefix(),
				fbData.getFb().getName(), varDecl.getName());
		writeFBParameter(resource, destination, value);
	}

	@Override
	public void writeFBParameter(final Resource resource, final String destination, final String value)
			throws DeploymentException {
		final String message = MessageFormat.format(Constants.WRITE_FB_PARAMETER, destination, value);
		if (combinedRequest) { // Add request to list of requests
			writeFBParameterCombined(destination, value, message);
		} else { // Execute single request
			writeFBParameterSingle(resource.getName(), destination, value, message);
		}
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {
		if (resourceNode == null) {
			return;
		}
		final IInterfaceElement sourceData = connData.getSource();
		final IInterfaceElement destinationData = connData.getDestination();

		if (sourceData == null || sourceData.getFBNetworkElement() == null || destinationData == null
				|| destinationData.getFBNetworkElement() == null) {
			throw new DeploymentException(MessageFormat
					.format(Messages.OPCUADeploymentExecutor_CreateConnectionFailedNoDataFound, res.getName()));
		}

		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		final String source = MessageFormat.format(Constants.FB_PORT_NAME_FORMAT, connData.getSourcePrefix(),
				sourceFB.getName(), sourceData.getName());
		final String destination = MessageFormat.format(Constants.FB_PORT_NAME_FORMAT, connData.getDestinationPrefix(),
				destinationFB.getName(), destinationData.getName());
		final CallMethodRequest request = new CallMethodRequest(resourceNode, Constants.CREATE_CONNECTION_NODE,
				new Variant[] { new Variant(source), new Variant(destination) });
		final String message = MessageFormat.format(Constants.CREATE_CONNECTION, destination, source);
		requests.add(request);
		requestMessages.add(message);
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		if (resourceNode == null) {
			return;
		}
		final String fullFbName = MessageFormat.format(Constants.FB_NAME_FORMAT, fbData.getPrefix(),
				fbData.getFb().getName());
		final CallMethodRequest request = new CallMethodRequest(resourceNode, Constants.START_FB_NODE,
				new Variant[] { new Variant(fullFbName) });
		final String message = MessageFormat.format(Constants.START_FB, fullFbName);
		try {
			sendREQ(res.getName(), request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_StartFBFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void startResource(final Resource resource) throws DeploymentException {
		if (resourceNode == null) {
			return;
		}
		final String resourceName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.START_RESOURCE_NODE,
				new Variant[] { new Variant(resourceName) });
		final String message = MessageFormat.format(Constants.START_RESOURCE, resourceName);
		requests.add(request);
		requestMessages.add(message);

		try {
			sendREQ(resourceName);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_StartResourceFailed, resourceName), e);
		}
		requests.clear();
		requestMessages.clear();
		combinedRequest = false;
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		final String devName = dev.getName();
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.START_DEVICE_NODE,
				new Variant[] {});
		final String message = MessageFormat.format(Constants.START_DEVICE, devName);
		try {
			sendREQ(devName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_StartDeviceFailed, devName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		final CallMethodRequest killRequest = new CallMethodRequest(Constants.MGMT_NODE, Constants.KILL_RESOURCE_NODE,
				new Variant[] { new Variant(resName) });
		String message = MessageFormat.format(Constants.KILL_RESOURCE, resName);
		try {
			sendREQ(resName, killRequest, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_KillResourceFailed, resName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}

		final CallMethodRequest deleteRequest = new CallMethodRequest(Constants.MGMT_NODE,
				Constants.DELETE_RESOURCE_NODE, new Variant[] { new Variant(resName) });
		message = MessageFormat.format(Constants.DELETE_RESOURCE, resName);
		try {
			sendREQ(resName, deleteRequest, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_DeleteResourceFailed, resName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		if (!getResourcesHandle()) {
			return;
		}

		final String fullFbName = MessageFormat.format(Constants.FB_NAME_FORMAT, fbData.getPrefix(),
				fbData.getFb().getName());
		final String resName = res.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.DELETE_FB_NODE, new Variant[] { new Variant(fullFbName), });
		final String message = MessageFormat.format(Constants.DELETE_FB_INSTANCE, fullFbName);
		try {
			sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_DeleteFBFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {
		if (!getResourcesHandle()) {
			return;
		}

		final IInterfaceElement sourceData = connData.getSource();
		final IInterfaceElement destinationData = connData.getDestination();

		if (sourceData == null || sourceData.getFBNetworkElement() == null || destinationData == null
				|| destinationData.getFBNetworkElement() == null) {
			throw new DeploymentException(Messages.OPCUADeploymentExecutor_CreateConnectionFailedNoDataFound);
		}

		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		final String source = MessageFormat.format(Constants.FB_PORT_NAME_FORMAT, connData.getSourcePrefix(),
				sourceFB.getName(), sourceData.getName());
		final String destination = MessageFormat.format(Constants.FB_PORT_NAME_FORMAT, connData.getDestinationPrefix(),
				destinationFB.getName(), destinationData.getName());
		final String resName = res.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.DELETE_CONNECTION_NODE, new Variant[] { new Variant(source), new Variant(destination) });
		final String message = MessageFormat.format(Constants.DELETE_CONNECTION, destination, source);
		try {
			sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_DeleteConnectionFailed, destination, source),
					e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		final String devName = dev.getName();
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.KILL_DEVICE_NODE,
				new Variant[] {});
		final String message = MessageFormat.format(Constants.KILL_DEVICE, devName);
		try {
			sendREQ(devName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_KillDeviceFailed, devName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.QUERY_RESOURCES_NODE,
				new Variant[] {});
		final String message = Constants.QUERY_RESOURCES;
		try {
			final CallMethodResult result = sendREQ("", request, message).get(); //$NON-NLS-1$
			final Response response = parseResponse(result, Constants.QUERY_RESPONSE);
			if (response != Constants.EMPTY_RESPONSE) {
				return getQueryElements(response);
			}
			FordiacLogHelper.logError(MessageFormat.format(Messages.OPCUADeploymentExecutor_ErrorOnQueryResources,
					getIEC61499Status(result.getStatusCode())));
		} catch (final IOException | ExecutionException e) {
			throw new DeploymentException(Messages.OPCUADeploymentExecutor_QueryResourcesFailed, e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
		return Collections.emptyList();
	}

	@Override
	public Response readWatches() throws DeploymentException {
		final CallMethodRequest request = new CallMethodRequest(Constants.MGMT_NODE, Constants.READ_WATCHES_NODE,
				new Variant[] {});
		final String message = Constants.READ_WATCHES;
		try {
			final CallMethodResult result = sendREQ("", request, message).get(); //$NON-NLS-1$
			return parseResponse(result, Constants.WATCHES_RESPONSE);
		} catch (final IOException | ExecutionException e) {
			throw new DeploymentException(Messages.OPCUADeploymentExecutor_ReadWatchesFailed, e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return Constants.EMPTY_RESPONSE;
		}
	}

	@Override
	public boolean addWatch(final Resource resource, final String fullFbName) throws DeploymentException {
		if (availableResources.isEmpty() && !getResourcesHandle()) {
			return false;
		}
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.ADD_WATCH_NODE, new Variant[] { new Variant(fullFbName) });
		final String message = MessageFormat.format(Constants.ADD_WATCH, fullFbName);
		CallMethodResult result;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_AddWatchFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return false;
		}
		final StatusCode opcuaStatus = result.getStatusCode();
		logResponseStatus(opcuaStatus, resName, fullFbName, Messages.OPCUADeploymentExecutor_ErrorOnMonitoringRequest);
		return !Constants.MGM_RESPONSE_UNKNOWN.equals(getIEC61499Status(opcuaStatus));
	}

	@Override
	public boolean removeWatch(final Resource resource, final String fullFbName) throws DeploymentException {
		if (availableResources.isEmpty() && !getResourcesHandle()) {
			return false;
		}
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.REMOVE_WATCH_NODE, new Variant[] { new Variant(fullFbName) });
		final String message = MessageFormat.format(Constants.REMOVE_WATCH, fullFbName);
		CallMethodResult result;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RemoveWatchFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return false;
		}
		final StatusCode opcuaStatus = result.getStatusCode();
		logResponseStatus(opcuaStatus, resName, fullFbName, Messages.OPCUADeploymentExecutor_ErrorOnMonitoringRequest);
		return !Constants.MGM_RESPONSE_UNKNOWN.equals(getIEC61499Status(opcuaStatus));
	}

	@Override
	public void triggerEvent(final Resource resource, final String fullFbName) throws DeploymentException {
		if (availableResources.isEmpty() && !getResourcesHandle()) {
			return;
		}
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.TRIGGER_EVENT_NODE, new Variant[] { new Variant(fullFbName) });
		final String message = MessageFormat.format(Constants.TRIGGER_EVENT, fullFbName);
		CallMethodResult result;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_TriggerEventFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return;
		}
		logResponseStatus(result.getStatusCode(), resName, fullFbName,
				Messages.OPCUADeploymentExecutor_ErrorOnMonitoringRequest);
	}

	@Override
	public void forceValue(final Resource resource, final String fullFbName, final String value)
			throws DeploymentException {
		if (availableResources.isEmpty() && !getResourcesHandle()) {
			return;
		}
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.FORCE_VALUE_NODE, new Variant[] { new Variant(fullFbName), new Variant(value) });
		final String message = MessageFormat.format(Constants.FORCE_VALUE, fullFbName);
		CallMethodResult result;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_ForceValueFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return;
		}
		logResponseStatus(result.getStatusCode(), resName, fullFbName,
				Messages.OPCUADeploymentExecutor_ErrorOnMonitoringRequest);
	}

	@Override
	public void clearForce(final Resource resource, final String fullFbName) throws DeploymentException {
		if (availableResources.isEmpty() && !getResourcesHandle()) {
			return;
		}
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.CLEAR_FORCE_NODE, new Variant[] { new Variant(fullFbName) });
		final String message = MessageFormat.format(Constants.CLEAR_FORCE, fullFbName);
		CallMethodResult result;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_ClearForceFailed, fullFbName), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return;
		}
		logResponseStatus(result.getStatusCode(), resName, fullFbName,
				Messages.OPCUADeploymentExecutor_ErrorOnMonitoringRequest);
	}

	/**************************************************************************
	 * Helper Methods
	 **************************************************************************/

	/**
	 * Browses Resource Nodes and caches available Resources
	 *
	 * @return CompletableFuture of Browse result status
	 */
	private CompletableFuture<StatusCode> browseResources() {
		final BrowseDescription browse = new BrowseDescription(Constants.MGMT_NODE, BrowseDirection.Forward,
				Identifiers.References, Boolean.TRUE, uint(NodeClass.Object.getValue()),
				uint(BrowseResultMask.All.getValue()));
		return client.browse(browse).thenCompose(result -> {
			final List<ReferenceDescription> references = toList(result.getReferences());
			for (final ReferenceDescription rd : references) {
				rd.getNodeId().toNodeId(client.getNamespaceTable())
						.ifPresent(nodeId -> availableResources.put(rd.getBrowseName().getName(), nodeId));
			}
			return CompletableFuture.completedFuture(result.getStatusCode());
		});
	}

	private boolean getResourcesHandle() throws DeploymentException {
		StatusCode status = StatusCode.BAD;
		try {
			status = browseResources().get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(Messages.OPCUADeploymentExecutor_BrowseOPCUAFailed);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
		}
		return status.isGood() && !availableResources.isEmpty();
	}

	private void writeFBParameterCombined(final String destination, final String value, final String message) {
		if (resourceNode == null) {
			return;
		}
		final CallMethodRequest request = new CallMethodRequest(resourceNode, Constants.WRITE_FB_NODE,
				new Variant[] { new Variant(destination), new Variant(value) });
		requests.add(request);
		requestMessages.add(message);
	}

	private void writeFBParameterSingle(final String resName, final String destination, final String value,
			final String message) throws DeploymentException {
		if (!availableResources.containsKey(resName) && !getResourcesHandle()) {
			return;
		}
		final CallMethodRequest request = new CallMethodRequest(availableResources.get(resName),
				Constants.WRITE_FB_NODE, new Variant[] { new Variant(destination), new Variant(value) });
		CallMethodResult result;
		try {
			result = sendREQ(resName, request, message).get();
		} catch (final ExecutionException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_WriteFBFailed, destination, value), e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			FordiacLogHelper.logError(
					MessageFormat.format(Messages.OPCUADeploymentExecutor_RequestInterrupted, e.getMessage()), e);
			return;
		}
		logResponseStatus(result.getStatusCode(), resName, destination,
				Messages.OPCUADeploymentExecutor_ErrorOnWriteRequest);
	}

	private String getDestinationInfo(final String destination) {
		String info = client.getConfig().getEndpoint().getEndpointUrl();
		if (!destination.equals("")) { //$NON-NLS-1$
			info += ": " + destination; //$NON-NLS-1$

		}
		return info;
	}

	private static List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> getQueryElements(
			final Response response) {
		if (null == response.getFblist() || null == response.getFblist().getFbs()) {
			return Collections.emptyList();
		}
		return response.getFblist().getFbs().stream().map(fb -> {
			final org.eclipse.fordiac.ide.deployment.devResponse.Resource res = DevResponseFactory.eINSTANCE
					.createResource();
			res.setName(fb.getName());
			res.setType(fb.getType());
			return res;
		}).toList();
	}

	private Response parseResponse(final CallMethodResult result, final String responseType) throws IOException {
		if ((result != null && result.getStatusCode().isGood())
				&& (result.getOutputArguments()[0].getValue() instanceof final String response)) {
			return parseXMLResponse(MessageFormat.format(responseType, response));
		}
		return Constants.EMPTY_RESPONSE;
	}

	private Response parseXMLResponse(final String encodedResponse) throws IOException {
		if (null != encodedResponse) {
			final InputSource source = new InputSource(new StringReader(encodedResponse));
			final XMLResource xmlResource = new XMLResourceImpl();
			xmlResource.load(source, respMapping.getLoadOptions());
			for (final EObject object : xmlResource.getContents()) {
				if (object instanceof final Response response) {
					return response;
				}
			}
		}
		return Constants.EMPTY_RESPONSE;
	}

	private static NodeId processResult(final CallMethodResult result) {
		final StatusCode statusCode = result.getStatusCode();
		if (statusCode.isGood()) {
			return (NodeId) result.getOutputArguments()[0].getValue();
		}
		final StatusCode[] inputArgumentResults = result.getInputArgumentResults();
		if (inputArgumentResults != null) {
			for (int i = 0; i < inputArgumentResults.length; i++) {
				FordiacLogHelper.logInfo(MessageFormat.format("Input Argument Result {0}: {1}", Integer.valueOf(i), //$NON-NLS-1$
						inputArgumentResults[i]));
			}
		}
		return null;
	}

	private List<CallMethodResult> handleResponse(final CallResponse response, final String destination) {
		final List<CallMethodResult> results = Arrays.asList(response.getResults());
		if (results.size() != requestMessages.size()) {
			FordiacLogHelper.logInfo("Result list size does not match number of requests!"); //$NON-NLS-1$
		}
		for (int i = 0; i < results.size(); i++) {
			final CallMethodResult result = results.get(i);
			final StatusCode opcuaStatus = result.getStatusCode();
			if (!opcuaStatus.isGood()) {
				final String message = requestMessages.get(i);
				displayCommand(opcuaStatus, destination, message);
			}
		}
		return results;
	}

	private void displayCommand(final StatusCode opcuaStatus, final String destination, final String message) {
		final String info = getDestinationInfo(destination);
		final String responseMessage = MessageFormat.format(Constants.RESPONSE, getIEC61499Status(opcuaStatus));
		for (final IDeploymentListener listener : listeners) {
			listener.postCommandSent(info, destination, message);
		}
		for (final IDeploymentListener listener : listeners) {
			if (listener instanceof final IDeploymentListener2 listener2) {
				listener2.postResponseReceived(info, message, responseMessage, destination);
			} else {
				listener.postResponseReceived(responseMessage, destination);
			}
		}
	}

	private static void logResponseStatus(final StatusCode opcuaStatus, final String resourceName,
			final String errorElement, final String messageTemplate) {
		if (!opcuaStatus.isGood()) {
			FordiacLogHelper.logError(
					MessageFormat.format(messageTemplate, getIEC61499Status(opcuaStatus), resourceName, errorElement));
		}
	}

	private static String getIEC61499Status(final StatusCode opcuaStatus) {
		final String status = Constants.RESPONSE_MAP.get(Long.valueOf(opcuaStatus.getValue()));
		if (status != null) {
			return status;
		}
		FordiacLogHelper.logInfo(
				MessageFormat.format(Messages.OPCUADeploymentExecutor_UnknownResponseCode, opcuaStatus.toString()));
		return Constants.MGM_RESPONSE_UNKNOWN;
	}

	@Override
	public void resetResource(final String resName) throws DeploymentException {
		// TODO Needs to be implemented due to interface changes in
		// IDeviceManagementInteractor
	}

	@Override
	public void killResource(final String resName) throws DeploymentException {
		// TODO Needs to be implemented due to interface changes in
		// IDeviceManagementInteractor
	}

	@Override
	public void stopResource(final Resource res) throws DeploymentException {
		// TOD Needs to be implemented due to interface changes in
		// IDeviceManagementInteractor
	}
}