/*******************************************************************************
 * Copyright (c) 2017 Aimirim STI
 * 
 *  Contributors:
 *   - Pedro Ricardo
 *   - Felipe Adriano
 *******************************************************************************/
package aimirim.fordiac.ide.deployment.uao;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;

import aimirim.fordiac.ide.deployment.uao.helpers.Constants;

import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.google.gson.JsonObject;

import aimirim.fordiac.ide.deployment.uao.helpers.UAOClient;


public class UAODeploymentExecutor implements IDeviceManagementInteractor {

	public enum ConnectionStatus {
		CONNECTED, DISCONNECTED, NOT_CONNECTED
	}

	private ConnectionStatus connectionStatus;
	private final Device device;
	private final UAOClient client;
	private int reqid,msgnr;
	private final List<IDeploymentListener> listeners = new ArrayList<>();
	
	/** Connection Status callback definitions */
	private WebSocketAdapter callbacks = new WebSocketAdapter() {
		public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
			connectionStatus = ConnectionStatus.CONNECTED;
		}
		public void onDisconnected(WebSocket websocket,
		        WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
		        boolean closedByServer) {
			connectionStatus = ConnectionStatus.DISCONNECTED;
		}
	};
	
	/** Initialize UAODeploymentExecutor class
	 * @param dev The current device.*/
	public UAODeploymentExecutor(final Device dev) {
		this.device = dev;
		this.msgnr = 0;
		this.reqid = 1;
		this.client = createClient(dev);
		this.connectionStatus = ConnectionStatus.NOT_CONNECTED;
	}

	/** Returns the initialized device.
	 * @return The current device.*/
	protected Device getDevice() {
		return device;
	}
	

	/** Check if the device is connected
	 * @return True if connected.*/
	@Override
	public boolean isConnected() {
		return connectionStatus == ConnectionStatus.CONNECTED;
	}

	/** Tries to connect with device.*/
	@Override
	public void connect() throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | connect");		
		try {
			client.connect();
		} catch (WebSocketException e) {
			throw new DeploymentException(e.getMessage());
		}
	}

	/** Check connection and Disconnect the device.*/
	@Override
	public void disconnect() throws DeploymentException {
		if(client.isOpen()) {
			client.disconnect();
		}
		connectionStatus = ConnectionStatus.NOT_CONNECTED;
	}

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

	@Override
	public void createResource(final Resource resource) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | createResource"); //$NON-NLS-1$
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | writeResourceParameter"); //$NON-NLS-1$
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | writeResourceParameter"); //$NON-NLS-1$
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) {
		final FBNetworkElement fb = fbData.getFb();
		final String fbType = fb.getTypeName();
		final String fbName = fb.getName();
		FordiacLogHelper.logInfo("UAODeploymentExecutor | createFBInstance T: "+fbType+" | N: "+fbName+" Pre: "+fbData.getPrefix()); //$NON-NLS-1$
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData,
			final VarDeclaration varDecl) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | writeFBParameter"); //$NON-NLS-1$
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | createConnection"); //$NON-NLS-1$
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | startFB"); //$NON-NLS-1$
		
	}

	@Override
	public void startResource(final Resource resource) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | startResource"); //$NON-NLS-1$
		_connectionCheck();
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | startDevice"); //$NON-NLS-1$
		
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | deleteResource"); //$NON-NLS-1$
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | deleteFB"); //$NON-NLS-1$
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData connData) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | deleteConnection"); //$NON-NLS-1$
		
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | killDevice"); //$NON-NLS-1$
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | queryResources"); //$NON-NLS-1$
		_connectionCheck();
		return Collections.emptyList();
	}

	@Override
	public Response readWatches() throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | readWatches"); //$NON-NLS-1$
		return Constants.EMPTY_RESPONSE;
	}

	@Override
	public void addWatch(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | addWatch"); //$NON-NLS-1$
		
	}

	@Override
	public void removeWatch(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | removeWatch"); //$NON-NLS-1$
	}

	@Override
	public void triggerEvent(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | triggerEvent"); //$NON-NLS-1$
	}

	@Override
	public void forceValue(final MonitoringBaseElement element, final String value) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | forceValue"); //$NON-NLS-1$
	}

	@Override
	public void clearForce(final MonitoringBaseElement element) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | clearForce"); //$NON-NLS-1$
	}


	/** Extract SSL information from device variables
	 * @param dev The current device.
	 * @return useSSL configured value.*/
	private boolean getUseSsl(final Device dev) throws DeploymentException {
		for (final VarDeclaration varDecl : dev.getVarDeclarations()) {
			if ("SSL".equalsIgnoreCase(varDecl.getName())) {
				final String val = DeploymentHelper.getVariableValue(varDecl);
				if (null != val) {
					return(Boolean.parseBoolean(val));
				}
			}
		}
		return false; //$NON-NLS-1$
	}
	
	/** Creates and configure the UAO connection
	 * @param dev The current device.*/
	private UAOClient createClient(final Device dev) {
		try {
			String mgrId = DeploymentHelper.getMgrID(dev);
			// Remove Quotes from string
			mgrId = mgrId.substring(1, mgrId.length() - 1);
			boolean ssl = getUseSsl(dev);
			String wsEndpoint = String.format("ws://%s",mgrId);
			FordiacLogHelper.logInfo("Connecting to "+wsEndpoint); //$NON-NLS-1$
			
			UAOClient newClient = new UAOClient(wsEndpoint,1000,ssl);
			newClient.addListener(callbacks);
			
			return newClient;
			
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.UAODeploymentExecutor_GetMgrIDFailed, e.getMessage()), e);
		} catch (IOException e) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.UAODeploymentExecutor_CreateClientFailed, e.getMessage()), e);
		}
		return null;
	}
	
	/** Increment message counter */
	private void _incrementCounters() {
		msgnr +=1;
		reqid +=1;
	}
	
	/** Build message body 
	 * @param operation The operation mode to ask the runtime.*/
	private JsonObject _messageBody(final String operation) {
		JsonObject REQ = new JsonObject();
		REQ.addProperty("op",operation);
		REQ.addProperty("msgnr",msgnr);
		REQ.addProperty("reqid",reqid);
		return(REQ);
	}
	
	/** Send data and wait for the response from server
	 * @param REQ Request JsonObject.
	 * @return RESP Response JsonObject received. */
	private JsonObject _sendReceive(final JsonObject REQ) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | Sent: "+REQ.toString()); //$NON-NLS-1$
		client.send(REQ);
		try {
			JsonObject RESP = client.receive(2000);
			FordiacLogHelper.logWarning("UAODeploymentExecutor | Received: "+RESP.toString());
			int status = RESP.get("result").getAsInt();
			if(status!=200) {
				String reason = RESP.get("error").getAsJsonObject().get("desc").getAsString();
				throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_RequestRejected,status,reason));
			}
			_incrementCounters();
			return(RESP);
		} catch (InterruptedException e) {
			throw new DeploymentException(MessageFormat.format(Messages.UAODeploymentExecutor_RequestInterrupted, e.getMessage()));
		} catch (TimeoutException e) {
			throw new DeploymentException(Messages.UAODeploymentExecutor_ClientRequestTimeout);
		}
	}
	
	private boolean _connectionCheck() throws DeploymentException {
		JsonObject REQ = _messageBody("stat");
		JsonObject RESP = _sendReceive(REQ);
		return(RESP.get("result").getAsInt()==200);
	}
	
}
