/*******************************************************************************
 * Copyright (c) 2007 - 2018 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl, Florian Noack, Gerhard Ebenhofer, Monika Wenger 
 *  		- initial API and implementation and/or initial documentation
 *  Alois Zoitl - Harmonized deployment and monitoring communication
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.AbstractDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DeploymentExecutor extends AbstractDeviceManagementInteractor{
	
	public static final String CREATE_RESOURCE_INSTANCE = "<Request ID=\"{0}\" Action=\"CREATE\"><FB Name=\"{1}\" Type=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String CREATE_FB_INSTANCE = "<Request ID=\"{0}\" Action=\"CREATE\"><FB Name=\"{1}\" Type=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String CREATE_CONNECTION = "<Request ID=\"{0}\" Action=\"CREATE\"><Connection Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String WRITE_PARAMETER = "<Request ID=\"{0}\" Action=\"WRITE\"><Connection Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String START = "<Request ID=\"{0}\" Action=\"START\"/>"; //$NON-NLS-1$
	public static final String START_FB = "<Request ID=\"{0}\" Action=\"START\"><FB Name=\"{1}\" Type=\"{2}\"/></Request>"; //$NON-NLS-1$
	public static final String KILL_FB = "<Request ID=\"{0}\" Action=\"KILL\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$
	public static final String KILL_DEVICE = "<Request ID=\"{0}\" Action=\"KILL\"></Request>"; //$NON-NLS-1$
	public static final String STOP_FB = "<Request ID=\"{0}\" Action=\"STOP\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$
	public static final String DELETE_FB = "<Request ID=\"{0}\" Action=\"DELETE\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$
	public static final String DELETE_CONNECTION = "<Request ID=\"{0}\" Action=\"DELETE\"><Connection Source=\"{1}\" Destination=\"{2}\"/></Request>"; //$NON-NLS-1$
	public static final String READ_WATCHES = "<Request ID=\"{0}\" Action=\"READ\"><Watches/></Request>"; //$NON-NLS-1$
	public static final String ADD_WATCH = "<Request ID=\"{0}\" Action=\"CREATE\"><Watch Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String DELETE_WATCH = "<Request ID=\"{0}\" Action=\"DELETE\"><Watch Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String FORCE_VALUE = "<Request ID=\"{0}\" Action=\"WRITE\"><Connection Source=\"{1}\" Destination=\"{2}\" force=\"{3}\" /></Request>"; //$NON-NLS-1$

	
	
	private final Set<String> genFBs = new HashSet<>();
	protected int id = 0;
	
	private ResponseMapping respMapping = new ResponseMapping();

	public DeploymentExecutor(Device dev) {
		this(dev, null);
	}
	
	public DeploymentExecutor(Device dev, IDeviceManagementCommunicationHandler overrideHandler) {
		super(dev, overrideHandler);
		genFBs.add("PUBLISH"); //$NON-NLS-1$
		genFBs.add("SUBSCRIBE"); //$NON-NLS-1$
		genFBs.add("PUBL"); //$NON-NLS-1$
		genFBs.add("SUBL"); //$NON-NLS-1$
		genFBs.add("SERVER"); //$NON-NLS-1$
		genFBs.add("CLIENT"); //$NON-NLS-1$
	}
	
	@Override
	protected IDeviceManagementCommunicationHandler createCommunicationHandler(Device dev) {
		//currently we only have the ability to connect via Ethernet to our devices, if this changes add here according factories
		return new EthernetDeviceManagementCommunicationHandler();
	}
	
	private static String getValidType(final FB fb) {
		if (fb != null && fb.getPaletteEntry() != null) {
			return fb.getTypeName();
		}
		return null;
	}

	@Override
	public void createResource(final Resource resource) throws DeploymentException {
		String request = MessageFormat.format(CREATE_RESOURCE_INSTANCE,
				id++, resource.getName(), resource.getTypeName());
		try {
			sendREQ("", request);  //$NON-NLS-1$
		} catch (EOFException e) {
			throw new DeploymentException(MessageFormat
					.format(Messages.DeploymentExecutor_DeviceConnectionClosed, resource.getName()), e);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat
					.format(Messages.DeploymentExecutor_CreateResourceFailed, resource.getName()), e);
		}
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value)
			throws DeploymentException {

		String encodedValue = encodeXMLChars(value);
		String request = generateWriteParamRequest(resource.getName(), parameter, encodedValue);
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_WriteResourceParameterFailed,
							resource.getName(), parameter), e);
		}
	}

	protected String generateWriteParamRequest(final String targetElementName, final String parameter,
			final String value) {
		return MessageFormat.format(getWriteParameterMessage(),
				id++, value, targetElementName + "." + parameter); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")  //this method needs to be overwritable by subclasses
	protected String getWriteParameterMessage() {
		return WRITE_PARAMETER;
	}

	private static String encodeXMLChars(final String value) {
		String retVal = value;
		retVal = retVal.replaceAll("\"", "&quot;"); //$NON-NLS-1$ //$NON-NLS-2$
		retVal = retVal.replaceAll("'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
		retVal = retVal.replaceAll("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		retVal = retVal.replaceAll(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		return retVal;
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData, final VarDeclaration varDecl)
			throws DeploymentException {
		String encodedValue = encodeXMLChars(value);
		String request = generateWriteParamRequest(fbData.prefix + fbData.fb.getName(), varDecl.getName(), encodedValue);
		try {
			sendREQ(resource.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_WriteFBParameterFailed,
					resource.getName(), varDecl.getName()), e);
		}

	}

	@Override
	public void createConnection(final Resource resource, final ConnectionDeploymentData connData) throws DeploymentException {
		IInterfaceElement source = connData.source;
		IInterfaceElement destination = connData.destination;
		if(null != source && null != destination && 
				null != source.getFBNetworkElement() && null != destination.getFBNetworkElement()){
			FBNetworkElement sourceFB = source.getFBNetworkElement();
			FBNetworkElement destFB = destination.getFBNetworkElement();
			String request = MessageFormat.format(CREATE_CONNECTION, 
							this.id++, connData.sourcePrefix + sourceFB.getName() + "." + source.getName(),  //$NON-NLS-1$
							connData.destinationPrefix + destFB.getName() + "." + destination.getName());  //$NON-NLS-1$
									
			try {
				sendREQ(resource.getName(), request);
			} catch (IOException e) {
				//TODO model refactoring - add here more information on what connection had the issue
				throw new DeploymentException(Messages.DeploymentExecutor_CreateConnectionFailed, e);
			}
		} else {
			throw new DeploymentException(Messages.DeploymentExecutor_CreateConnectionFailed);
		}
	}

	@Override
	public void startResource(final Resource res) throws DeploymentException {
		String request = MessageFormat.format(START, id++);
		try {
			sendREQ(res.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_StartingResourceFailed,
					res.getName()), e);
		}
	}

	@Override
	public void startDevice(Device dev) throws DeploymentException {
		String request = MessageFormat.format(START, id++);
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_StartingDeviceFailed,
					dev.getName()), e);
		}
	}

	@Override
	public void writeDeviceParameter(Device device, String parameter, String value) throws DeploymentException {
		String request = MessageFormat.format(getWriteParameterMessage(), id++, value, parameter);
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_WriteDeviceParameterFailed,
							device.getName(), parameter), e);
		}
	}

	@Override
	public void deleteResource(Resource res) throws DeploymentException {
		String kill = MessageFormat.format(KILL_FB,
				id++, res.getName());
		String delete = MessageFormat.format(DELETE_FB,
				id++, res.getName());
		try {
			sendREQ("", kill); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_KillFBFailed, res.getName()), e);
		}
		try {
			sendREQ("", delete); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_DeleteFBFailed, res.getName()), e);
		}
	}

	@Override
	public void clearDevice(Device dev) throws DeploymentException {
		for (Resource res : dev.getResource()) {
			if (!res.isDeviceTypeResource()) {
				deleteResource(res);
			}
		}

	}

	@Override
	public void deleteConnection(Resource res, ConnectionDeploymentData conData) throws DeploymentException {
		//do nothing
	}

	@Override
	public void deleteFB(Resource res, FBDeploymentData fbData) throws DeploymentException {
		//do nothing
	}

	@Override
	public void startFB(Resource res, FBDeploymentData fbData) throws DeploymentException {
		String fullFbInstanceName = fbData.prefix + fbData.fb.getName();
		String request = MessageFormat.format(START_FB,
				id++, fullFbInstanceName, fbData.fb.getTypeName());
		try {
			sendREQ(res.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_StartingFBFailed, fullFbInstanceName), e);
		}
	}
	
	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		String fbType = getValidType(fbData.fb);
		String fullFbInstanceName = fbData.prefix + fbData.fb.getName();
		if ("".equals(fbType)) { //$NON-NLS-1$
			throw new DeploymentException((MessageFormat.format(
					Messages.DeploymentExecutor_CreateFBInstanceFailedNoTypeFound, fullFbInstanceName)));
		}
		String request = MessageFormat.format(CREATE_FB_INSTANCE,
				id++, fullFbInstanceName, fbType);
		try {
			sendREQ(res.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_CreateFBInstanceFailed,
					fullFbInstanceName), e);
		}
	}

	@Override
	public void killDevice(Device dev) throws DeploymentException {
		String kill = MessageFormat.format(KILL_DEVICE, id++);
		try {
			sendREQ("", kill); //$NON-NLS-1$
		} catch (EOFException e) {
			// exception can be ignored, as no response is returned by forte			
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_KillDeviceFailed,
					dev.getName()), e);
		} finally {
			resetTypes();
		}
	}
	
	public QueryResponseHandler sendQUERY(final String destination, final String request) throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		QueryResponseHandler handler = new QueryResponseHandler();
		String response = getDevMgmComHandler().sendREQ(destination, request);
		saxParser.parse(new InputSource(new StringReader(response)), handler);
		return handler;
	}

	@Override
	public Response readWatches() throws DeploymentException {
		String request = MessageFormat.format(READ_WATCHES, id++);

		try {
			String response = getDevMgmComHandler().sendREQ("", request);  //$NON-NLS-1$
			if (response != null) {
				XMLResource resource = new XMLResourceImpl();
				InputSource source = new InputSource(new StringReader(response));
				resource.load(source, respMapping.getLoadOptions());
				for (EObject object : resource.getContents()) {
					if (object instanceof Response) {
						return (Response)object;
					}
				}
			}
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_ReadWatchesFailed,
					getDevice().getName()), e);
		}  
		
		return null;
	}
	
	@Override
	public void addWatch(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(ADD_WATCH, this.id++, element.getQualifiedString(), "*"); //$NON-NLS-1$
		try {
			String response = getDevMgmComHandler().sendREQ(element.getResourceString(), request);
			
			//TODO show somehow the feedback if the response contained a reason that it didn't work
			element.setOffline("".equals(response)); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_AddWatchesFailed,
					element.getQualifiedString()), e);
		}
	}
	 
	@Override
	public void removeWatch(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(DELETE_WATCH, this.id++, element.getQualifiedString(), "*"); //$NON-NLS-1$
		try {
			String response = getDevMgmComHandler().sendREQ(element.getResourceString(), request);
			//TODO show somehow the feedback if the response contained a reason that it didn't work
			element.setOffline("".equals(response)); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_DeleteWatchesFailed,
					element.getQualifiedString()), e);
		}
	}

	@Override
	public void triggerEvent(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(getWriteParameterMessage(), id++, "$e", element.getQualifiedString()); //$NON-NLS-1$ 
		try {
			sendREQ(element.getResourceString(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_TriggerEventFailed,
							element.getQualifiedString()), e);
		}
	}
	
	@Override
	public void forceValue(MonitoringBaseElement element, String value) throws DeploymentException{
		String request = MessageFormat.format(FORCE_VALUE,
				this.id++, value, element.getQualifiedString(), "true"); //$NON-NLS-1$
		try {
			sendREQ(element.getResourceString(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_ForceValueFailed,
							element.getQualifiedString(), value), e);
		}
	}
	
	@Override
	public void clearForce(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(FORCE_VALUE,
				this.id++, "*", element.getQualifiedString(), "false"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			sendREQ(element.getResourceString(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_ClearForceFailed,
							element.getQualifiedString()), e);
		}
	}



}
