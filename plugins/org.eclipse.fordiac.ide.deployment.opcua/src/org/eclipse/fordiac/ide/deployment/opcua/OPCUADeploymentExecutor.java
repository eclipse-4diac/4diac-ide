package org.eclipse.fordiac.ide.deployment.opcua;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
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

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static org.eclipse.milo.opcua.stack.core.util.ConversionUtil.toList;


public class OPCUADeploymentExecutor implements IDeviceManagementInteractor{
	
	/** Deployment Console Messages **/
	public static final String CREATE_RESOURCE_INSTANCE = "<CREATE RESOURCE> -> <Name=\"{0}\" Type=\"{1}\">"; //$NON-NLS-1$
	public static final String CREATE_FB_INSTANCE = "<CREATE FB> -> <FB Name=\"{0}\" Type=\"{1}\">"; //$NON-NLS-1$
	public static final String CREATE_CONNECTION = "<CREATE CONNECTION> -> <Connection Destination=\"{0}\" Source=\"{1}\">"; //$NON-NLS-1$
	public static final String WRITE_DEVICE_PARAMETER = "<WRITE DEVICE> -> <Name=\"{0}\" Value=\"{1}\">"; //$NON-NLS-1$
	public static final String WRITE_RESOURCE_PARAMETER = "<WRITE RESOURCE> -> <Name=\"{0}\" Value=\"{1}\">"; //$NON-NLS-1$
	public static final String WRITE_FB_PARAMETER = "<WRITE FB> -> <Connection Destination=\"{0}\" Source=\"{1}\">"; //$NON-NLS-1$
	public static final String START_DEVICE = "<START DEVICE> -> <\"{0}\">"; //$NON-NLS-1$
	public static final String START_RESOURCE = "<START RESOURCE> -> <\"{0}\">"; //$NON-NLS-1$
	public static final String START_FB = "<START FB> -> <FB Name=\"{0}\">"; //$NON-NLS-1$	
	public static final String KILL_DEVICE = "<KILL> -> <Name=\"{0}\">"; //$NON-NLS-1$
	public static final String KILL_RESOURCE = "<KILL> -> <Name=\"{0}\">"; //$NON-NLS-1$
	public static final String DELETE_RESOURCE = "<DELETE> -> <Name=\"{0}\">"; //$NON-NLS-1$
	public static final String DELETE_FB_INSTANCE = "<DELETE FB> -> <FB Name=\"{0}\">"; //$NON-NLS-1$
	public static final String DELETE_CONNECTION = "<DELETE CONNECTION> -> <Connection Destination=\"{0}\" Source=\"{1}\">"; //$NON-NLS-1$
	
	public static final String RESPONSE = "<Response> -> <{0}>\n";
	
	/** OPCUA Nodes **/
	/* Root Node */
	private static final NodeId mgmtNode = new NodeId(1, "IEC61499Mgmt"); //$NON-NLS-1$ 
	
	private static final NodeId createResourceNode = new NodeId(1, "createResource"); //$NON-NLS-1$
	private static final NodeId createFBNode = new NodeId(1, "createFB"); //$NON-NLS-1$
	private static final NodeId createConnectionNode = new NodeId(1, "createConnection"); //$NON-NLS-1$
	private static final NodeId writeDeviceNode = new NodeId(1, "writeDevice"); //$NON-NLS-1$
	private static final NodeId writeResourceNode = new NodeId(1, "writeResource"); //$NON-NLS-1$
	private static final NodeId writeFBNode = new NodeId(1, "writeFB"); //$NON-NLS-1$
	private static final NodeId startDeviceNode = new NodeId(1, "startDevice"); //$NON-NLS-1$
	private static final NodeId startResourceNode = new NodeId(1, "startResource"); //$NON-NLS-1$
	private static final NodeId startFBNode = new NodeId(1, "startFB"); //$NON-NLS-1$
	private static final NodeId killDeviceNode = new NodeId(1, "killDevice"); //$NON-NLS-1$
	private static final NodeId killResourceNode = new NodeId(1, "killResource"); //$NON-NLS-1$
	private static final NodeId deleteResourceNode = new NodeId(1, "deleteResource"); //$NON-NLS-1$
	private static final NodeId deleteFBNode = new NodeId(1, "deleteFB"); //$NON-NLS-1$
	private static final NodeId deleteConnectionNode = new NodeId(1, "deleteConnection"); //$NON-NLS-1$
	
	
	private final OpcUaClient client;
	private final Device device;
	private NodeId resourceNode;
	
	private final List<CallMethodRequest> requests = new ArrayList<>();
	private final List<String> requestMessages = new ArrayList<>();
	private final List<IDeploymentListener> listeners = new ArrayList<>();
	
	/* Future for Resource NodeId */
	CompletableFuture<NodeId> fResourceNode;
	
	
	
	HashMap<String, NodeId> availableResources = new HashMap<>();
	
	
	
	public OPCUADeploymentExecutor(final Device dev, final IDeviceManagementCommunicationHandler overrideHandler) {
		this.device = dev;
		this.client = createClient();
		this.resourceNode = null;
	}
	
	protected OpcUaClient createClient() {
		try {
			String mgrId = DeploymentHelper.getMgrID(device);
			mgrId = mgrId.substring(1, mgrId.length()-1);
			List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(mgrId).get();
			OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
			
			// TODO Change from endpoints.get(0) to proper implementation
			cfg.setEndpoint(endpoints.get(0));
			return OpcUaClient.create(cfg.build());
		}catch(Exception e) {
			FordiacLogHelper.logInfo(e.toString());
			e.printStackTrace();
		}
		FordiacLogHelper.logInfo("Could not create OPC UA client!");
		return null;
	}
	
	
	protected Device getDevice() {
		return device;
	}
	
	@Override
	public boolean isConnected() {
		// TODO Check if Client is connected
		return true;
	}

	@Override
	public void connect() throws DeploymentException {
		try {
			client.connect().get();
			for(IDeploymentListener listener : listeners) {
				listener.connectionOpened(device);
			}
		}catch(Exception e) {
			FordiacLogHelper.logInfo("Could not connect to OPC UA Server!"); //$NON-NLS-1$
			FordiacLogHelper.logInfo(e.toString());
			e.printStackTrace();
			throw new DeploymentException("Could not connect to device!", e); //$NON-NLS-1$
		}
	}

	@Override
	public void disconnect() throws DeploymentException {
		try {
			client.disconnect().get();
			for(IDeploymentListener listener: listeners) {
				listener.connectionClosed(device);
			}
		}catch(Exception e) {
			FordiacLogHelper.logInfo(e.toString());
			e.printStackTrace();
			FordiacLogHelper.logInfo("Failed to disconnect client!"); //$NON-NLS-1$
			throw new DeploymentException("Could not disconnect from device!", e); //$NON-NLS-1$
		}
	}
	
	/** @param destination - Only needed for displaying on Deployment Console **/
	private synchronized CompletableFuture<NodeId> sendResourceREQ(String destination, CallMethodRequest request, String message) throws IOException {
		for(IDeploymentListener listener: listeners) {
			listener.postCommandSent(getDestinationInfo(destination), destination, message);
		}
		return client.call(request).thenCompose(result -> {
			StatusCode status = result.getStatusCode();
			if(status.toString().length() != 0) {
				for(IDeploymentListener listener: listeners) {
					listener.postResponseReceived(MessageFormat.format(RESPONSE, status.toString()), destination); //$NON-NLS-1$
				}
			}
			return processResult(result);
			});
	}
	
	/** @param destination - Only needed for displaying on Deployment Console **/
	private synchronized CompletableFuture<CallMethodResult> sendREQ(String destination, CallMethodRequest request, String message) throws IOException {
		for(IDeploymentListener listener: listeners) {
			listener.postCommandSent(getDestinationInfo(destination), destination, message);
		}
		return client.call(request).thenCompose(result -> {
			StatusCode status = result.getStatusCode();
			if(status.toString().length() != 0) {
				for(IDeploymentListener listener: listeners) {
					listener.postResponseReceived(MessageFormat.format(RESPONSE, status.toString()), destination); //$NON-NLS-1$
				}
			}
			return CompletableFuture.completedFuture(result);
		});
	}
	
	private synchronized List<CallMethodResult> sendREQ(String destination) throws IOException {
		CallResponse response;
		try {
			response = client.call(requests).get();
		}catch(final ExecutionException e) {
			//TODO
			throw new IOException();
		}catch(final InterruptedException e) {
			//TODO
			throw new IOException();
		}
		
		List<CallMethodResult> results = Arrays.asList(response.getResults());
		if(results.size() != requestMessages.size()) {
			FordiacLogHelper.logInfo("Invalid Size of Result List!");
		}
		
		for(int i = 0; i < results.size(); i++) {
			String message = requestMessages.get(i);
			CallMethodResult result = results.get(i);
			StatusCode status = result.getStatusCode();
			for(IDeploymentListener listener: listeners) {
				listener.postCommandSent(getDestinationInfo(destination), destination, message);
				if(status.toString().length() != 0) {
					listener.postResponseReceived(MessageFormat.format(RESPONSE, status.toString()), destination);
				}
			}
		}
		return results;
	}
	
	/************************************************************************
	 * Listener commands 
	 ************************************************************************/

	@Override
	public void addDeploymentListener(IDeploymentListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeDeploymentListener(IDeploymentListener listener) {
		if(listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
	
	/**************************************************************************
	 * Management commands 
	 **************************************************************************/

	@Override
	public void createResource(Resource resource) throws DeploymentException {
		final String resName = resource.getName();
		final String resType = resource.getTypeName();
		final CallMethodRequest request = new CallMethodRequest(
				mgmtNode,
				createResourceNode,
				new Variant[] {
					new Variant(resName),
					new Variant(resType)
				});
		String message = MessageFormat.format(CREATE_RESOURCE_INSTANCE, resName, resType);
		try {
			fResourceNode = sendResourceREQ(resName, request, message);
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Create Resource Instance \"{0}\" failed.", resName), e); //$NON-NLS-1$
		}
	}
	
	@Override
	public void writeResourceParameter(Resource resource, String parameter, String value) throws DeploymentException {
		// TODO Check if "parameter" value is needed in request
		final String resName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(
				mgmtNode,
				writeResourceNode,
				new Variant[] {
						new Variant(resName),
						new Variant(value)
				});
		String message = MessageFormat.format(WRITE_RESOURCE_PARAMETER, value);
		try {
			sendREQ(resName, request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Write resource parameter failed for resource: \"{0}\".", resName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void writeDeviceParameter(Device device, String parameter, String value) throws DeploymentException {
		// TODO Check if "parameter" value is needed in request
		final String devName = device.getName();
		final CallMethodRequest request = new CallMethodRequest(
				mgmtNode,
				writeDeviceNode,
				new Variant[] {
						new Variant(value)
				});
		String message = MessageFormat.format(WRITE_DEVICE_PARAMETER, value);
		try {
			sendREQ(devName, request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Write device parameter failed for device: \"{0}\".", devName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void createFBInstance(FBDeploymentData fbData, Resource res) throws DeploymentException {
		if(!fResourceNode.isDone() || resourceNode == null) getNodeId();
		
		final String fbType = getValidType(fbData.getFb());
		final String fullFbName = MessageFormat.format("{0}{1}", fbData.getPrefix(), fbData.getFb().getName()); //$NON-NLS-1$
		if("".equals(fbType)) {
			throw new DeploymentException(
					MessageFormat.format("Not FB Type found: {0}", fullFbName)); //$NON-NLS-1$
		}
		final CallMethodRequest request = new CallMethodRequest(
				resourceNode, 
				createFBNode,
				new Variant[] {
					new Variant(fullFbName),
					new Variant(fbType)
				});
		String message = MessageFormat.format(CREATE_FB_INSTANCE, fullFbName, fbType);
		requests.add(request);
		requestMessages.add(message);
	}

	@Override
	public void writeFBParameter(Resource resource, String value, FBDeploymentData fbData, VarDeclaration varDecl)
			throws DeploymentException {
		if(!fResourceNode.isDone() || resourceNode == null) getNodeId();
		
		final String destination = MessageFormat.format("{0}{1}.{2}", fbData.getPrefix(), fbData.getFb().getName(), varDecl.getName()); //$NON-NLS-1$
		final CallMethodRequest request = new CallMethodRequest(
				resourceNode,
				writeFBNode,
				new Variant[] {
						new Variant(destination),
						new Variant(value)
				});
		String message = MessageFormat.format(WRITE_FB_PARAMETER, destination, value);
		requests.add(request);
		requestMessages.add(message);
	}

	@Override
	public void createConnection(Resource res, ConnectionDeploymentData connData) throws DeploymentException {
		if(!fResourceNode.isDone() || resourceNode == null) getNodeId();
		
		final IInterfaceElement sourceData = connData.getSource();
		final IInterfaceElement destinationData = connData.getDestination();
		
		if(sourceData == null || sourceData.getFBNetworkElement() == null
				|| destinationData == null || destinationData.getFBNetworkElement() == null) {
			throw new DeploymentException("No Connection Data found!"); //$NON-NLS-1$
		}
		
		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		final String source = MessageFormat.format("{0}{1}.{2}", connData.getSourcePrefix(), sourceFB.getName(), sourceData.getName());
		final String destination = MessageFormat.format("{0}{1}.{2}", connData.getDestinationPrefix(), destinationFB.getName(), destinationData.getName());
		final CallMethodRequest request = new CallMethodRequest(
				resourceNode,
				createConnectionNode,
				new Variant[] {
						new Variant(source),
						new Variant(destination)
				});
		String message = MessageFormat.format(CREATE_CONNECTION, destination, source);
		requests.add(request);
		requestMessages.add(message);
	}

	@Override
	public void startFB(Resource res, FBDeploymentData fbData) throws DeploymentException {
		final String fullFbName = MessageFormat.format("{0}{1}", fbData.getPrefix(), fbData.getFb().getName()); //$NON-NLS-1$
		final CallMethodRequest request = new CallMethodRequest(
				resourceNode, 
				startFBNode,
				new Variant[] {
					new Variant(fullFbName)
				});
		String message = MessageFormat.format(START_FB, fullFbName);
		try {
			sendREQ(res.getName(), request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Start FB failed for FB: \"{0}\".", fullFbName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void startResource(Resource resource) throws DeploymentException {
		String resourceName = resource.getName();
		final CallMethodRequest request = new CallMethodRequest(
				mgmtNode,
				startResourceNode,
				new Variant[] {
						new Variant(resourceName)
				});
		String message = MessageFormat.format(START_RESOURCE, resourceName);
		requests.add(request);
		requestMessages.add(message);
		
		try {
			sendREQ(resourceName);
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Start Resource failed for Resource: \"{0}\"!", resourceName), e); //$NON-NLS-1$
		}
		requests.clear();
		requestMessages.clear();
	}

	@Override
	public void startDevice(Device dev) throws DeploymentException {
		final String devName = dev.getName();
		final CallMethodRequest request = new CallMethodRequest(
				mgmtNode,
				startDeviceNode,
				new Variant[] {});
		final String message = MessageFormat.format(START_DEVICE, devName);
		try {
			sendREQ(devName, request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Start Device failed for Device: \"{0}\"!", devName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void deleteResource(String resName) throws DeploymentException {
		final CallMethodRequest killRequest = new CallMethodRequest(
				mgmtNode,
				killResourceNode,
				new Variant[] {
						new Variant(resName)
				});
		String message = MessageFormat.format(KILL_RESOURCE, resName);
		try {
			sendREQ(resName, killRequest, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Kill Resource failed for Resource: \"{0}\".", resName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
		
		final CallMethodRequest deleteRequest = new CallMethodRequest(
				mgmtNode,
				deleteResourceNode,
				new Variant[] {
						new Variant(resName)
				});
		message = MessageFormat.format(DELETE_RESOURCE, resName);
		try {
			sendREQ(resName, deleteRequest, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Delete Resource failed for Resource: \"{0}\".", resName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void deleteFB(Resource res, FBDeploymentData fbData) throws DeploymentException {
		if(!getResourcesHandle()) return;
		
		final String fullFbName = MessageFormat.format("{0}{1}", fbData.getPrefix(), fbData.getFb().getName()); //$NON-NLS-1$
		final String resName = res.getName();
		final CallMethodRequest request = new CallMethodRequest(
				availableResources.get(resName), 
				deleteFBNode,
				new Variant[] {
					new Variant(fullFbName),
				});
		String message = MessageFormat.format(DELETE_FB_INSTANCE, fullFbName);
		try {
			sendREQ(resName, request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Delete FB failed for FB: \"{0}\".", fullFbName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void deleteConnection(Resource res, ConnectionDeploymentData connData) throws DeploymentException {
		if(!getResourcesHandle()) return;
		
		final IInterfaceElement sourceData = connData.getSource();
		final IInterfaceElement destinationData = connData.getDestination();
		
		if(sourceData == null || sourceData.getFBNetworkElement() == null
				|| destinationData == null || destinationData.getFBNetworkElement() == null) {
			throw new DeploymentException("No Connection Data found!"); //$NON-NLS-1$
		}
		
		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		final String source = MessageFormat.format("{0}{1}.{2}", connData.getSourcePrefix(), sourceFB.getName(), sourceData.getName());
		final String destination = MessageFormat.format("{0}{1}.{2}", connData.getDestinationPrefix(), destinationFB.getName(), destinationData.getName());
		final String resName = res.getName();
		final CallMethodRequest request = new CallMethodRequest(
				availableResources.get(resName),
				deleteConnectionNode,
				new Variant[] {
						new Variant(source),
						new Variant(destination)
				});
		String message = MessageFormat.format(DELETE_CONNECTION, destination, source);
		try {
			sendREQ(resName, request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Delete Connection failed for Connection: Destination=\"{0}\" Source=\"{1}\".", destination, source), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public void killDevice(Device dev) throws DeploymentException {	
		final String devName = dev.getName();
		final CallMethodRequest request = new CallMethodRequest(
				mgmtNode,
				killDeviceNode,
				new Variant[] {});
		final String message = MessageFormat.format(KILL_DEVICE, devName);
		try {
			sendREQ(devName, request, message).get();
		}catch(IOException e) {
			//TODO Refactor Exception Handling
			throw new DeploymentException(MessageFormat.format("Kill Device failed for Device: \"{0}\".", devName), e); //$NON-NLS-1$
		}catch(Exception e) {
			//TODO
		}
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public Response readWatches() throws DeploymentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addWatch(MonitoringBaseElement element) throws DeploymentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeWatch(MonitoringBaseElement element) throws DeploymentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void triggerEvent(MonitoringBaseElement element) throws DeploymentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void forceValue(MonitoringBaseElement element, String value) throws DeploymentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearForce(MonitoringBaseElement element) throws DeploymentException {
		// TODO Auto-generated method stub
		
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
	    BrowseDescription browse = new BrowseDescription(
	        mgmtNode,
	        BrowseDirection.Forward,
	        Identifiers.References,
	        true,
	        uint(NodeClass.Object.getValue()),
	        uint(BrowseResultMask.All.getValue())
	    );    
	         return client.browse(browse).thenCompose(result -> {    	
	        	List<ReferenceDescription> references = toList(result.getReferences());
		        for (ReferenceDescription rd : references) {
		        	rd.getNodeId().toNodeId(client.getNamespaceTable())
		        		.ifPresent(nodeId -> availableResources.put(rd.getBrowseName().getName(), nodeId));
		        	
		        	
//		        	if(rd.getBrowseName().getName().equals(resName)) {
//		        		rd.getNodeId().toNodeId(client.getNamespaceTable())
//		                .ifPresent(nodeId -> resources.put(resName, nodeId));
//		        	}
		        }
		        return CompletableFuture.completedFuture(result.getStatusCode()); //$NON-NLS-1$
			});
	}

	
	
	private boolean getResourcesHandle() {
		StatusCode status = StatusCode.UNCERTAIN;
		try {
			status = browseResources().get();
		} catch (InterruptedException | ExecutionException e) {
	        FordiacLogHelper.logError("Browsing Request failed!" + e.getMessage());
	    }
		return status.isGood() && !availableResources.isEmpty();
	}
	
	private String getDestinationInfo(String destination) {
		String info = client.getConfig().getEndpoint().getEndpointUrl();
		if(!destination.equals("")) { //$NON-NLS-1$
			info += ": " + destination; //$NON-NLS-1$
			
		}
		return info;
	}
	
	private CompletableFuture<NodeId> processResult(CallMethodResult result) {
		StatusCode statusCode = result.getStatusCode();
		
		if(statusCode.isGood()) {
			NodeId nodeId = (NodeId) result.getOutputArguments()[0].getValue();
			resourceNode = nodeId;
			return CompletableFuture.completedFuture(nodeId);
		} else {
			StatusCode[] inputArgumentResults = result.getInputArgumentResults();
			if(inputArgumentResults != null) {
				for (int i = 0; i < inputArgumentResults.length; i++) {
					FordiacLogHelper.logError(MessageFormat.format("Input Argument Result {0}: {1}", i, inputArgumentResults[i])); //$NON-NLS-1$
				}
			}
            CompletableFuture<NodeId> f = new CompletableFuture<>();
            // TODO Refactor for UaException
            f.completeExceptionally(new UaException(statusCode));
            return f;
		}
	}
	
	private void getNodeId() {
		if(fResourceNode != null) {
			try {
				fResourceNode.get();
			}catch ( Exception e) {
				// TODO
			}
		}
	}
	
	private static String getValidType(final FBNetworkElement fb) {
		if(fb != null && fb.getTypeEntry() != null) {
			if(fb instanceof StructManipulator) {
				return fb.getTypeName() + "_1" + ((StructManipulator) fb).getStructType().getName(); //$NON-NLS-1$
			}
			return fb.getTypeName();
		}
		return null;
	}
}