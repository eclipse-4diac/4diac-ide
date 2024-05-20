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
package org.eclipse.fordiac.ide.deployment.uao;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;

import org.eclipse.fordiac.ide.deployment.uao.helpers.Constants;

import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.eclipse.fordiac.ide.deployment.uao.helpers.UAOClient;


public class UAODeploymentExecutor implements IDeviceManagementInteractor {

	public enum ConnectionStatus {
		CONNECTED, DISCONNECTED, NOT_CONNECTED
	}
	
	private Document deployXml ;
	private Element systemElement;
	private Element deviceElement;
	private Element resourceElement;
	private Element fbNetwork;
	private Element eventConnection;
	private Element dataConnection;
	private String snapshotGuid;
	private String projectGuid;
	private int MAX_AUTH_RETRY = 10;
	
	private ConnectionStatus connectionStatus;
	private final Device device;
	private final UAOClient client;
	private final List<IDeploymentListener> listeners = new ArrayList<>();
	
	/** Connection Status callback definitions */
	private WebSocketAdapter callbacks = new WebSocketAdapter() {
		@Override
		public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
			connectionStatus = ConnectionStatus.CONNECTED;
		}
		@Override
		public void onDisconnected(WebSocket websocket,
		        WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
		        boolean closedByServer) {
			connectionStatus = ConnectionStatus.DISCONNECTED;
		}
	};
	
	/** Initialize UAODeploymentExecutor class.
	 * @param dev The current device.*/
	public UAODeploymentExecutor(final Device dev) {
		this.device = dev;
		this.client = createClient(dev);
		this.connectionStatus = ConnectionStatus.NOT_CONNECTED;
		this.deployXml = createInitialXml(dev);
	}

	/** Returns the initialized device.
	 * @return The current device.*/
	protected Device getDevice() {
		return device;
	}
	
	@Override
	public boolean isConnected() {
		return connectionStatus == ConnectionStatus.CONNECTED;
	}

	@Override
	public void connect() throws DeploymentException {
		boolean success = false;
		int retry = 0;
		String error = ""; //$NON-NLS-1$
		
		try {
			client.connect();
			if (client.connectionCheck()) {
				// XXX: Sometimes the credentials get denied by the runtime
				//      for some unknown reason. To avoid a new click on deploy,
				//      some retries are performed reseting the client key pair.
				while(retry<MAX_AUTH_RETRY) {
					try {
						success = client.authenticate();
						if (success) {break;}
					} catch (DeploymentException e) {
						// It usually works on the first three retries
						client.regenKeyPair();
						error = e.getMessage();
						FordiacLogHelper.logInfo("UAODeploymentExecutor | Auth retry "+(retry+1)); //$NON-NLS-1$
					}
					retry++;				}
				
				if (retry>=MAX_AUTH_RETRY) {
					throw new DeploymentException("Max Authentication Retries exceeded. "+error); //$NON-NLS-1$
				}
			}
		} catch (WebSocketException e) {
			throw new DeploymentException(e.getMessage());
		}
	}

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
        resourceElement = deployXml.createElement("Resource"); //$NON-NLS-1$
        resourceElement.setAttribute("Name", resource.getName()); //$NON-NLS-1$
        resourceElement.setAttribute("Type", resource.getTypeName()); //$NON-NLS-1$
        
        fbNetwork = deployXml.createElement("FBNetwork"); //$NON-NLS-1$
        eventConnection = deployXml.createElement("EventConnections"); //$NON-NLS-1$
        dataConnection = deployXml.createElement("DataConnections"); //$NON-NLS-1$
        
        resourceElement.appendChild(fbNetwork);
        deviceElement.appendChild(resourceElement);
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value) throws DeploymentException {
//		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeResourceParameter "+parameter+"="+value); //$NON-NLS-1$
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value) throws DeploymentException {
//		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeDeviceParameter "+parameter+"="+value); //$NON-NLS-1$
		
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		client.connectionCheck();
		final FBNetworkElement fb = fbData.getFb();
		
		fbNetwork.appendChild(
			createFB(prefixUAO(fbData.getPrefix())+fb.getName(), fb.getTypeName())
		);
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData,
			final VarDeclaration varDecl) throws DeploymentException {
		client.connectionCheck();
		final FBNetworkElement fb = fbData.getFb();
		
		String fbFullName = prefixUAO(fbData.getPrefix())+fb.getName();
		
		findFbByName(fbFullName).appendChild(
			createParameter(varDecl.getName(),value)
		);
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {
		client.connectionCheck();
		final IInterfaceElement sourceData = connData.getSource();
		final IInterfaceElement destinationData = connData.getDestination();

		if (sourceData == null || sourceData.getFBNetworkElement() == null || destinationData == null
				|| destinationData.getFBNetworkElement() == null) {
			throw new DeploymentException(MessageFormat
					.format(Messages.UAODeploymentExecutor_CreateConnectionFailedNoDataFound, res.getName()));
		}

		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		final String source = String.format("%s%s.%s",prefixUAO(connData.getSourcePrefix()),sourceFB.getName(),sourceData.getName()); //$NON-NLS-1$  
		final String destination = String.format("%s%s.%s",prefixUAO(connData.getDestinationPrefix()),destinationFB.getName(),destinationData.getName()); //$NON-NLS-1$
		
		if (sourceData.getTypeName()=="Event" && destinationData.getTypeName()=="Event") { //$NON-NLS-1$ //$NON-NLS-2$
			eventConnection.appendChild(
				createConnection(source,destination)
			);
		} else {
			dataConnection.appendChild(
				createConnection(source,destination)	
			);
		}
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
//		FordiacLogHelper.logInfo("UAODeploymentExecutor | startFB"); //$NON-NLS-1$
	}

	@Override
	public void startResource(final Resource resource) throws DeploymentException {
		// XXX: UAO Runtime does now have an implicit START block. It needs to be deployed.
		//      To fix this a new resource that does not already have a START FB is needed.
		FB fb = resource.getFBNetwork().getFBNamed("START"); //$NON-NLS-1$
		if (fb!=null) {
			fbNetwork.appendChild(createFB(fb.getName(),fb.getTypeName()));	
		}
		// Append the connections after all FBs were inserted in FBNetwork
		fbNetwork.appendChild(eventConnection);
		fbNetwork.appendChild(dataConnection);
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		client.connectionCheck();
		String from = client.getDeviceState();
		client.deploy(deployXml, projectGuid, snapshotGuid);
		String to = client.getDeviceState();
		FordiacLogHelper.logInfo("UAODeploymentExecutor | Runtime state from \""+from+"\" to \""+to+"\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		client.connectionCheck();
		String from = client.getDeviceState();
		client.flow_command("clean"); //$NON-NLS-1$
		String to = client.getDeviceState();
		FordiacLogHelper.logInfo("UAODeploymentExecutor | Runtime state from \""+from+"\" to \""+to+"\""); //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Online Change Delete FB")); //$NON-NLS-1$
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData connData) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
			Messages.UAODeploymentExecutor_FeatureNotImplemented, "Online Change Delete Connection")); //$NON-NLS-1$
		
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		client.connectionCheck();
		client.restart();
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		client.connectionCheck();
		return Collections.emptyList();
	}

	@Override
	public Response readWatches() throws DeploymentException {
		return Constants.EMPTY_RESPONSE;
	}

	@Override
	public void addWatch(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Watch"));  //$NON-NLS-1$
	}

	@Override
	public void removeWatch(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Watch")); //$NON-NLS-1$
	}

	@Override
	public void triggerEvent(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Event Trigger")); //$NON-NLS-1$
	}

	@Override
	public void forceValue(final MonitoringBaseElement element, final String value) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Value Force")); //$NON-NLS-1$
	}

	@Override
	public void clearForce(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Value Force")); //$NON-NLS-1$
	}

	/** Parse the 4diac '.' prefix to the UAO '_' one
	 * @param original 4diac FB prefix.
	 * @return UAO type of prefix.*/
	private static String prefixUAO(String original) {
		String uaoFormat = original;
		if(original.contains(".")) { //$NON-NLS-1$
			uaoFormat = original.replace(".", "_"); //$NON-NLS-1$  //$NON-NLS-2$
		}
		return(uaoFormat);
	}
	
	/** Extract SSL information from device variables.
	 * @param dev The current device.
	 * @return useSSL configured value.*/
	private static boolean getUseSsl(final Device dev) throws DeploymentException {
		for (final VarDeclaration varDecl : dev.getVarDeclarations()) {
			if ("SSL".equalsIgnoreCase(varDecl.getName())) { //$NON-NLS-1$
				final String val = DeploymentHelper.getVariableValue(varDecl);
				if (null != val) {
					return(Boolean.parseBoolean(val));
				}
			}
		}
		return false;
	}
	
	/** Creates and configure the UAO connection.
	 * @param dev The current device.*/
	private UAOClient createClient(final Device dev) {
		String mgrId = ""; //$NON-NLS-1$
		try {
			mgrId = DeploymentHelper.getMgrID(dev);
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.UAODeploymentExecutor_GetMgrIDFailed, e.getMessage()), e);
		}
		// Remove Quotes from string
		mgrId = mgrId.substring(1, mgrId.length() - 1);
		
		boolean ssl = false;
		try {
			ssl = getUseSsl(dev);
		} catch (DeploymentException e) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.UAODeploymentExecutor_GetSSLFailed, e.getMessage()), e);
		}
		
		UAOClient newClient = null;
		try {
			newClient = new UAOClient(mgrId,1000,ssl);
			newClient.addListener(callbacks);
		} catch (DeploymentException e) {
			FordiacLogHelper.logError(MessageFormat.format(Messages.UAODeploymentExecutor_ClientConnectionFailed, e.getMessage()), e);
		}
		
		return newClient;
	}
	
	
	/** Build the xml document to keep the deploy data.
	 * @param dev Device to deploy.
	 * @return A initial XML with the system and device already in it.*/
	private Document createInitialXml(final Device dev) {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document doc = null;
		if (docBuilder!=null) {
			doc = docBuilder.newDocument();
			systemElement = createSystem(doc, dev);
			deviceElement = createDevice(doc, dev);
			systemElement.appendChild(deviceElement);
			doc.appendChild(systemElement);
		}
	    return(doc);
	}
	
	/** Creates the System xml element.
	 * @param doc XML document.
	 * @param dev Device to deploy.
	 * @return XML Element.*/
	private Element createSystem(Document doc, final Device dev) {
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"); //$NON-NLS-1$
	    LocalDateTime now = LocalDateTime.now();
	    String version = System.getProperty("org.eclipse.fordiac.ide.version"); //$NON-NLS-1$
	  
	    String projName = dev.getTypeEntry().getTypeLibrary().getSystems().keySet().toArray()[0].toString();
	   
	    Element sysEl = doc.createElement("System"); //$NON-NLS-1$
	    
	    this.projectGuid = UUID.nameUUIDFromBytes(projName.getBytes()).toString();
	    this.snapshotGuid = UUID.randomUUID().toString();
	    
	    sysEl.setAttribute("ProjectName", projName); //4diac Project Name //$NON-NLS-1$
	    sysEl.setAttribute("ProjectGuid", projectGuid); //$NON-NLS-1$
	    sysEl.setAttribute("BuildTime", dtf.format(now)); //$NON-NLS-1$
	    sysEl.setAttribute("DeployTime", ""); //$NON-NLS-1$ //$NON-NLS-2$
	    sysEl.setAttribute("StudioVersion", "Eclipse 4diac IDE v"+version); // 4diac version info //$NON-NLS-1$ //$NON-NLS-2$
	    sysEl.setAttribute("SnapshotGuid", snapshotGuid); //$NON-NLS-1$
        
        return(sysEl);
	}
	
	/** Creates a Device xml element.
	 * @param doc XML document.
	 * @param dev Device to deploy.
	 * @return XML Element.*/
	private static Element createDevice(Document doc, final Device dev) {
        Element devEl = doc.createElement("Device"); //$NON-NLS-1$
        devEl.setAttribute("Name", dev.getName()); //$NON-NLS-1$
        devEl.setAttribute("Type", dev.getTypeName()); //$NON-NLS-1$
        return(devEl);
	}
	
	/** Search for a FB by it's name in class FBNetwork.
	 * @param fbFullName The name with all prefixes.
	 * @return XML Element.*/
	private Element findFbByName(String fbFullName) {
		Element fb = null;
		NodeList fblist = fbNetwork.getElementsByTagName("FB"); //$NON-NLS-1$
		for (int i=0; i<fblist.getLength(); i++) {
		   Element fbi = (Element) fblist.item(i);
		   if (fbi.getAttribute("Name").equals(fbFullName)) { //$NON-NLS-1$
			   fb = fbi;
			   break;
		   }
		}
		return(fb);
	}
	
	/** Creates a Parameter xml element
	 * @param Name
	 * @param Value
	 * @return */
	private Element createParameter(String Name, String Value) {
		Element param = deployXml.createElement("Parameter"); //$NON-NLS-1$
		param.setAttribute("Name", Name); //$NON-NLS-1$
		param.setAttribute("Value", Value); //$NON-NLS-1$
		return(param);
	}
	
	/** Creates a FB xml element assuming the namespace to
	 * "IEC61499.Standard".
	 * @param Name with all prefixes needed.
	 * @param Type
	 * @return FB element.*/
	private Element createFB(String Name, String Type) {
        return(createFB(Name,Type,"IEC61499.Standard")); //$NON-NLS-1$
	}
	
	/** Creates a FB xml element.
	 * @param Name with all prefixes needed.
	 * @param Type
	 * @param Namespace where the runtime will find the implementation
	 * @return FB element.*/
	private Element createFB(String Name, String Type, String Namespace) {
		Element fbEl = deployXml.createElement("FB"); //$NON-NLS-1$
        fbEl.setAttribute("Name", Name); //$NON-NLS-1$
        fbEl.setAttribute("Type", Type); //$NON-NLS-1$
        fbEl.setAttribute("Namespace", Namespace); //$NON-NLS-1$
        return(fbEl);
	}

	/** Creates a connection xml element.
	 * @param Src Name of the connection source.
	 * @param Dest Name of the connection destination.
	 * @return XML element.*/
	private Element createConnection(String Src, String Dest) {
		Element conEl = deployXml.createElement("Connection"); //$NON-NLS-1$
		conEl.setAttribute("Comment", ""); //$NON-NLS-1$ //$NON-NLS-2$
		conEl.setAttribute("Source", Src); //$NON-NLS-1$
		conEl.setAttribute("Destination", Dest); //$NON-NLS-1$
        return(conEl);
	}
	
}
