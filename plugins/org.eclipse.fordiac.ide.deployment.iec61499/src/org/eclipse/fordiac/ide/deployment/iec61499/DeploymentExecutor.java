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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.AbstractDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringBase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.xml.sax.InputSource;

public class DeploymentExecutor extends AbstractDeviceManagementInteractor{
	private final Set<String> genFBs = new HashSet<>();
	int id = 0;
	
	ResponseMapping respMapping = new ResponseMapping();

	public DeploymentExecutor(Device dev) {
		this(dev, null);
	}
	
	public DeploymentExecutor(Device dev, AbstractDeviceManagementCommunicationHandler overrideHandler) {
		super(dev, overrideHandler);
		genFBs.add("PUBLISH"); //$NON-NLS-1$
		genFBs.add("SUBSCRIBE"); //$NON-NLS-1$
		genFBs.add("PUBL"); //$NON-NLS-1$
		genFBs.add("SUBL"); //$NON-NLS-1$
		genFBs.add("SERVER"); //$NON-NLS-1$
		genFBs.add("CLIENT"); //$NON-NLS-1$
	}
	
	@Override
	protected EthernetDeviceManagementCommunicationHandler getDevMgmComHandler() {
		return (EthernetDeviceManagementCommunicationHandler)super.getDevMgmComHandler();
	}

	@Override
	protected AbstractDeviceManagementCommunicationHandler createCommunicationHandler(Device dev) {
		//currently we only have the ability to connect via ethernet to our devices, if this changes add here according factories
		return new EthernetDeviceManagementCommunicationHandler();
	}
	
	@Override
	protected String getDeviceAddress(Device device) {
		return DeploymentHelper.getMgrID(device);
	}
	
	
	private static String getValidType(final FB fb) {
		if (fb != null && fb.getPaletteEntry() != null) {
			return fb.getTypeName();
		}
		return null;
	}

	@Override
	public void createResource(final Resource resource) throws DeploymentException {
		String request = MessageFormat.format(Messages.DeploymentExecutor_CreateResourceInstance,
				new Object[] { id++, resource.getName(), resource.getTypeName() });
		try {
			sendREQ("", request);  //$NON-NLS-1$
		} catch (EOFException e) {
			throw new DeploymentException(MessageFormat
					.format(Messages.DeploymentExecutor_DeviceConnectionClosed, new Object[] { resource.getName() }), e);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat
					.format(Messages.DeploymentExecutor_CreateResourceFailed, new Object[] { resource.getName() }), e);
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
							new Object[] { resource.getName(), parameter }), e);
		}
	}

	protected String generateWriteParamRequest(final String targetElementName, final String parameter,
			final String value) {
		return MessageFormat.format(getWriteParameterMessage(),
				new Object[] { id++, value, targetElementName + "." + parameter }); //$NON-NLS-1$
	}

	@SuppressWarnings("static-method")  //this method needs to be overwritable by subclasses
	protected String getWriteParameterMessage() {
		return Messages.DeploymentExecutor_WriteParameter;
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
					new Object[] { resource.getName(), varDecl.getName() }), e);
		}

	}

	public void createConnection(final Resource resource, final ConnectionDeploymentData connData) throws DeploymentException {
		IInterfaceElement source = connData.source;
		IInterfaceElement destination = connData.destination;
		if(null != source && null != destination && 
				null != source.getFBNetworkElement() && null != destination.getFBNetworkElement()){
			FBNetworkElement sourceFB = source.getFBNetworkElement();
			FBNetworkElement destFB = destination.getFBNetworkElement();
			String request = MessageFormat.format(
					Messages.DeploymentExecutor_CreateConnection, new Object[] {
							this.id++, connData.sourcePrefix + sourceFB.getName() + "." + source.getName(),  //$NON-NLS-1$
							connData.destinationPrefix + destFB.getName() + "." + destination.getName() });  //$NON-NLS-1$
									
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
		String request = MessageFormat.format(Messages.DeploymentExecutor_Start, new Object[] {id++ });
		try {
			sendREQ(res.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_StartingResourceFailed,
					new Object[] { res.getName() }), e);
		}
	}

	@Override
	public void startDevice(Device dev) throws DeploymentException {
		String request = MessageFormat.format(Messages.DeploymentExecutor_Start, new Object[] {id++ });
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_StartingDeviceFailed,
					new Object[] { dev.getName() }), e);
		}
	}

	@Override
	public void writeDeviceParameter(Device device, String parameter, String value) throws DeploymentException {
		String request = MessageFormat.format(getWriteParameterMessage(), new Object[] {id++, value, parameter });
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_WriteDeviceParameterFailed,
							new Object[] { device.getName(), parameter }), e);
		}
	}

	public void deleteResource(Resource res) throws DeploymentException {
		String kill = MessageFormat.format(Messages.DeploymentExecutor_KillFB,
				new Object[] {id++, res.getName() });
		String delete = MessageFormat.format(Messages.DeploymentExecutor_DeleteFB,
				new Object[] {id++, res.getName() });
		try {
			sendREQ("", kill); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_KillFBFailed, new Object[] { res.getName(), }), e);
		}
		try {
			sendREQ("", delete); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_DeleteFBFailed, new Object[] { res.getName(), }), e);
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

	}

	@Override
	public void deleteFB(Resource res, FBDeploymentData fbData) throws DeploymentException {

	}

	@Override
	public void startFB(Resource res, FBDeploymentData fbData) throws DeploymentException {
		String fullFbInstanceName = fbData.prefix + fbData.fb.getName();
		String request = MessageFormat.format(Messages.DeploymentExecutor_StartFB,
				new Object[] { id++, fullFbInstanceName, fbData.fb.getTypeName() });
		try {
			sendREQ(res.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_StartingFBFailed, new Object[] { fullFbInstanceName }), e);
		}
	}
	
	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		String fbType = getValidType(fbData.fb);
		String fullFbInstanceName = fbData.prefix + fbData.fb.getName();
		if ("".equals(fbType)) { //$NON-NLS-1$
			throw new DeploymentException((MessageFormat.format(
					Messages.DeploymentExecutor_CreateFBInstanceFailedNoTypeFound, new Object[] { fullFbInstanceName })));
		}
		String request = MessageFormat.format(Messages.DeploymentExecutor_CreateFBInstance,
				new Object[] {id++, fullFbInstanceName, fbType });
		try {
			sendREQ(res.getName(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_CreateFBInstanceFailed,
					new Object[] { fullFbInstanceName }), e);
		}
	}

	@Override
	public void killDevice(Device dev) throws DeploymentException {
		String kill = MessageFormat.format(Messages.DeploymentExecutor_KillDevice, new Object[] { id++ });
		try {
			sendREQ("", kill); //$NON-NLS-1$
		} catch (IOException e) {
			if (e instanceof EOFException) {
				// exception can be ignored, as no response is returned by forte
			} else {
				throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_KillDeviceFailed,
						new Object[] { dev.getName() }), e);
			}
		} finally {
			resetTypes();
		}
	}
	
	public void sendREQ(String destination, String request) throws IOException {
		getDevMgmComHandler().sendREQandRESP(destination, request);
	}

	@Override
	public Response readWatches() throws DeploymentException {
		String request = MessageFormat.format(Messages.DeploymentExecutor_Read_Watches, new Object[] { id++ });

		try {
			String response = getDevMgmComHandler().sendREQandRESP("", request);  //$NON-NLS-1$
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
					new Object[] { getDevice().getName() }), e);
		}  
		
		return null;
	}
	
	@Override
	public void addWatch(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(Messages.DeploymentExecutor_Add_Watch, new Object[] { this.id++, element.getQualifiedString(), "*" }); //$NON-NLS-1$
		try {
			String response = getDevMgmComHandler().sendREQandRESP(element.getResourceString(), request);
			//TODO show somehow the feedback if the response contained a reason that it didn't work
			element.setOffline(response == null);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_AddWatchesFailed,
					new Object[] { element.getQualifiedString() }), e);
		}
	}
	 
	@Override
	public void removeWatch(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(Messages.DeploymentExecutor_Delete_Watch, new Object[] { this.id++, element.getQualifiedString(), "*" }); //$NON-NLS-1$
		try {
			String response = getDevMgmComHandler().sendREQandRESP(element.getResourceString(), request);
			//TODO show somehow the feedback if the response contained a reason that it didn't work
			element.setOffline(response == null);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_DeleteWatchesFailed,
					new Object[] { element.getQualifiedString() }), e);
		}
	}

	@Override
	public void triggerEvent(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(getWriteParameterMessage(), new Object[] {id++, "$e", element.getQualifiedString()}); //$NON-NLS-1$ 
		try {
			sendREQ(element.getResourceString(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_TriggerEventFailed,
							new Object[] { element.getQualifiedString() }), e);
		}
	}
	
	@Override
	public void forceValue(MonitoringBaseElement element, String value) throws DeploymentException{
		String request = MessageFormat.format(Messages.DeploymentExecutor_Force_Value,
				new Object[] { this.id++, value, element.getQualifiedString(), "true"}); //$NON-NLS-1$
		try {
			sendREQ(element.getResourceString(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_ForceValueFailed,
							new Object[] { element.getQualifiedString(), value }), e);
		}
	}
	
	@Override
	public void clearForce(MonitoringBaseElement element) throws DeploymentException {
		String request = MessageFormat.format(Messages.DeploymentExecutor_Force_Value,
				new Object[] { this.id++, "*", element.getQualifiedString(), "false"}); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			sendREQ(element.getResourceString(), request);
		} catch (IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_ClearForceFailed,
							new Object[] { element.getQualifiedString() }), e);
		}
	}

}
