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
 *  Alois Zoitl, Florian Noack, Gerhard Ebenhofer, 
 *  Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.io.EOFException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.AbstractDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.util.DeploymentHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public class DeploymentExecutor extends AbstractDeviceManagementInteractor{
	private final Set<String> genFBs = new HashSet<>();
	int id = 0;

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
		String request = generateWriteParmaRequest(resource.getName(), parameter, encodedValue);
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_WriteResourceParameterFailed,
							new Object[] { resource.getName(), parameter }), e);
		}
	}

	protected String generateWriteParmaRequest(final String targetElementName, final String parameter,
			final String value) {
		return MessageFormat.format(getWriteParameterMessage(),
				new Object[] { id++, value, targetElementName + "." + parameter }); //$NON-NLS-1$
	}

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
		String request = generateWriteParmaRequest(fbData.prefix + fbData.fb.getName(), varDecl.getName(), encodedValue);
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
}
