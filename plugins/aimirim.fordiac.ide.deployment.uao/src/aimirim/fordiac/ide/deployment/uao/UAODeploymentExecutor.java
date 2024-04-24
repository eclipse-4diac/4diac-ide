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
package aimirim.fordiac.ide.deployment.uao;

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

import aimirim.fordiac.ide.deployment.uao.helpers.Constants;

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

import aimirim.fordiac.ide.deployment.uao.helpers.UAOClient;


public class UAODeploymentExecutor implements IDeviceManagementInteractor {

	public enum ConnectionStatus {
		CONNECTED, DISCONNECTED, NOT_CONNECTED
	}
	
	private Document DeployXml ;
	private Element systemElement;
	private Element deviceElement;
	private Element resourceElement;
	private Element fbNetwork;
	private Element eventConnection;
	private Element dataConnection;
	
	private ConnectionStatus connectionStatus;
	private final Device device;
	private final UAOClient client;
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
	
	/** Initialize UAODeploymentExecutor class.
	 * @param dev The current device.*/
	public UAODeploymentExecutor(final Device dev) {
		this.device = dev;
		this.client = createClient(dev);
		this.connectionStatus = ConnectionStatus.NOT_CONNECTED;
		this.DeployXml = createInitialXml(dev);
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
		FordiacLogHelper.logInfo("UAODeploymentExecutor | connect");		
		try {
			client.connect();
			if (client.connectionCheck()) {
				client.authenticate();
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
		FordiacLogHelper.logInfo("UAODeploymentExecutor | createResource "+resource.getName()); //$NON-NLS-1$
        resourceElement = DeployXml.createElement("Resource");
        resourceElement.setAttribute("Name", resource.getName());
        resourceElement.setAttribute("Type", resource.getTypeName());
        
        fbNetwork = DeployXml.createElement("FBNetwork");
        eventConnection = DeployXml.createElement("EventConnections");
        dataConnection = DeployXml.createElement("DataConnections");
        
        resourceElement.appendChild(fbNetwork);
        deviceElement.appendChild(resourceElement);
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeResourceParameter "+parameter+"="+value); //$NON-NLS-1$
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value) throws DeploymentException {
		FordiacLogHelper.logWarning("UAODeploymentExecutor | writeDeviceParameter "+parameter+"="+value); //$NON-NLS-1$
		
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		client.connectionCheck();
		final FBNetworkElement fb = fbData.getFb();
		FordiacLogHelper.logInfo("UAODeploymentExecutor | createFBInstance "+fbData.getPrefix()+fb.getName()); //$NON-NLS-1$
		fbNetwork.appendChild(
			createFB(fbData.getPrefix()+fb.getName(), fb.getTypeName())
		);
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData,
			final VarDeclaration varDecl) throws DeploymentException {
		client.connectionCheck();
		FordiacLogHelper.logInfo("UAODeploymentExecutor | writeFBParameter "+varDecl.getName()+"="+value); //$NON-NLS-1$
		final FBNetworkElement fb = fbData.getFb();
		String fbFullName = fbData.getPrefix()+fb.getName();
		
		findFbByName(fbFullName).appendChild(
			createParameter(varDecl.getName(),value)
		);
	}

	@Override
	public void createConnection(final Resource res, final ConnectionDeploymentData connData)
			throws DeploymentException {
		client.connectionCheck();
		FordiacLogHelper.logInfo("UAODeploymentExecutor | createConnection"); //$NON-NLS-1$
		final IInterfaceElement sourceData = connData.getSource();
		final IInterfaceElement destinationData = connData.getDestination();

		if (sourceData == null || sourceData.getFBNetworkElement() == null || destinationData == null
				|| destinationData.getFBNetworkElement() == null) {
			throw new DeploymentException(MessageFormat
					.format(Messages.UAODeploymentExecutor_CreateConnectionFailedNoDataFound, res.getName()));
		}
		
		final FBNetworkElement sourceFB = sourceData.getFBNetworkElement();
		final FBNetworkElement destinationFB = destinationData.getFBNetworkElement();
		final String source = String.format("%s%s.%s",connData.getSourcePrefix(),sourceFB.getName(),sourceData.getName());  
		final String destination = String.format("%s%s.%s",connData.getDestinationPrefix(),destinationFB.getName(),destinationData.getName());
		
		if (sourceData.getTypeName()=="Event" && destinationData.getTypeName()=="Event") {
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
		FordiacLogHelper.logInfo("UAODeploymentExecutor | startFB"); //$NON-NLS-1$
		
	}

	@Override
	public void startResource(final Resource resource) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | startResource"); //$NON-NLS-1$
		// XXX: UAO Runtime does now have an implicit START block. It needs to be deployed.
		//		To fix this a new resource that does not already have a START FB is needed.
		FB fb = resource.getFBNetwork().getFBNamed("START");
		if (fb!=null) {
			fbNetwork.appendChild(createFB(fb.getName(),fb.getTypeName()));	
		}
		// Append the connections after all FBs were inserted in FBNetwork
		fbNetwork.appendChild(eventConnection);
		fbNetwork.appendChild(dataConnection);
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | startDevice"); //$NON-NLS-1$
		client.connectionCheck();
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
			Messages.UAODeploymentExecutor_CommandNotImplemented, "deleteResource"));
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_CommandNotImplemented, "deleteFB"));
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData connData) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
			Messages.UAODeploymentExecutor_CommandNotImplemented, "deleteConnection"));
		
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_CommandNotImplemented, "killDevice"));
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		FordiacLogHelper.logInfo("UAODeploymentExecutor | queryResources"); //$NON-NLS-1$
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
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Watch"));
	}

	@Override
	public void removeWatch(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Watch"));
	}

	@Override
	public void triggerEvent(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Event Trigger"));
	}

	@Override
	public void forceValue(final MonitoringBaseElement element, final String value) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Value Force"));
	}

	@Override
	public void clearForce(final MonitoringBaseElement element) throws DeploymentException {
		throw new DeploymentException(MessageFormat.format(
				Messages.UAODeploymentExecutor_FeatureNotImplemented, "Value Force"));
	}


	/** Extract SSL information from device variables.
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
	
	/** Creates and configure the UAO connection.
	 * @param dev The current device.*/
	private UAOClient createClient(final Device dev) {
		String mgrId = null;
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
		
		String wsEndpoint = String.format("ws://%s",mgrId);
		FordiacLogHelper.logInfo("Connecting to "+wsEndpoint); //$NON-NLS-1$
		
		UAOClient newClient = null;
		try {
			newClient = new UAOClient(wsEndpoint,1000,ssl);
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
		Document doc = docBuilder.newDocument();
		systemElement = createSystem(doc, dev);
		deviceElement = createDevice(doc, dev);
		systemElement.appendChild(deviceElement);
		doc.appendChild(systemElement);
		
	    return(doc);
	}
	
	/** Creates the System xml element.
	 * @param doc XML document.
	 * @param dev Device to deploy.
	 * @return XML Element.*/
	private Element createSystem(Document doc, final Device dev) {
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
	    LocalDateTime now = LocalDateTime.now();
	    String version = System.getProperty("org.eclipse.fordiac.ide.version");
	  
	    String projName = dev.getTypeEntry().getTypeLibrary().getSystems().keySet().toArray()[0].toString();
	   
	    Element sysEl = doc.createElement("System");

	    sysEl.setAttribute("ProjectName", projName); //4diac Project Name
	    sysEl.setAttribute("ProjectGuid", UUID.nameUUIDFromBytes(projName.getBytes()).toString());
	    sysEl.setAttribute("BuildTime", dtf.format(now));
	    sysEl.setAttribute("DeployTime", "");
	    sysEl.setAttribute("StudioVersion", "Eclipse 4diac IDE v"+version); // 4diac version info
	    sysEl.setAttribute("SnapshotGuid", UUID.randomUUID().toString());
        
        return(sysEl);
	}
	
	/** Creates a Device xml element.
	 * @param doc XML document.
	 * @param dev Device to deploy.
	 * @return XML Element.*/
	private Element createDevice(Document doc, final Device dev) {
        Element devEl = doc.createElement("Device");
        devEl.setAttribute("Name", dev.getName());
        devEl.setAttribute("Type", dev.getTypeName());
        return(devEl);
	}
	
	/** Search for a FB by it's name in class FBNetwork.
	 * @param fbFullName The name with all prefixes.
	 * @return XML Element.*/
	private Element findFbByName(String fbFullName) {
		Element fb = null;
		NodeList fblist = fbNetwork.getElementsByTagName("FB");
		for (int i=0; i<fblist.getLength(); i++) {
		   Element fbi = (Element) fblist.item(i);
		   if (fbi.getAttribute("Name").equals(fbFullName)) {
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
		Element param = DeployXml.createElement("Parameter");
		param.setAttribute("Name", Name);
		param.setAttribute("Value", Value);
		return(param);
	}
	
	/** Creates a FB xml element assuming the namespace to
	 * "IEC61499.Standard".
	 * @param Name with all prefixes needed.
	 * @param Type
	 * @return FB element.*/
	private Element createFB(String Name, String Type) {
        return(createFB(Name,Type,"IEC61499.Standard"));
	}
	
	/** Creates a FB xml element.
	 * @param Name with all prefixes needed.
	 * @param Type
	 * @param Namespace where the runtime will find the implementation
	 * @return FB element.*/
	private Element createFB(String Name, String Type, String Namespace) {
		Element fbEl = DeployXml.createElement("FB");
        fbEl.setAttribute("Name", Name);
        fbEl.setAttribute("Type", Type);
        fbEl.setAttribute("Namespace", Namespace);
        return(fbEl);
	}

	/** Creates a connection xml element.
	 * @param Src Name of the connection source.
	 * @param Dest Name of the connection destination.
	 * @return XML element.*/
	private Element createConnection(String Src, String Dest) {
		Element conEl = DeployXml.createElement("Connection");
		conEl.setAttribute("Comment", "");
		conEl.setAttribute("Source", Src);
		conEl.setAttribute("Destination", Dest);
        return(conEl);
	}
	
//	/** Debug Function to export the internal XML file.
//	 * @param doc XML Document.
//	 * @param output file. */
//    private void writeXml(Document doc, OutputStream output) throws TransformerException {
//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        DOMSource source = new DOMSource(doc);
//        StreamResult result = new StreamResult(output);
//        transformer.transform(source, result);
//    }
    
}
