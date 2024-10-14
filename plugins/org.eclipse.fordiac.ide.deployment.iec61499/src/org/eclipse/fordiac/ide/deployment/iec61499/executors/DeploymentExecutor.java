/*******************************************************************************
 * Copyright (c) 2007 - 2018 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl, Florian Noack, Gerhard Ebenhofer, Monika Wenger
 *  		- initial API and implementation and/or initial documentation
 *  Alois Zoitl - Harmonized deployment and monitoring communication
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.executors;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.data.ConnectionDeploymentData;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.Messages;
import org.eclipse.fordiac.ide.deployment.iec61499.ResponseMapping;
import org.eclipse.fordiac.ide.deployment.iec61499.handlers.EthernetDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.interactors.AbstractDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.interactors.ForteTypeNameCreator;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.xml.sax.InputSource;

public class DeploymentExecutor extends AbstractDeviceManagementInteractor {

	public static final String CREATE_RESOURCE_INSTANCE = "<Request ID=\"{0}\" Action=\"CREATE\"><FB Name=\"{1}\" Type=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String CREATE_FB_INSTANCE = "<Request ID=\"{0}\" Action=\"CREATE\"><FB Name=\"{1}\" Type=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String CREATE_CONNECTION = "<Request ID=\"{0}\" Action=\"CREATE\"><Connection Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String WRITE_PARAMETER = "<Request ID=\"{0}\" Action=\"WRITE\"><Connection Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String START = "<Request ID=\"{0}\" Action=\"START\"/>"; //$NON-NLS-1$
	public static final String STOP = "<Request ID=\"{0}\" Action=\"STOP\"/>"; //$NON-NLS-1$
	public static final String START_FB = "<Request ID=\"{0}\" Action=\"START\"><FB Name=\"{1}\" Type=\"{2}\"/></Request>"; //$NON-NLS-1$
	public static final String KILL_FB = "<Request ID=\"{0}\" Action=\"KILL\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$
	public static final String KILL_DEVICE = "<Request ID=\"{0}\" Action=\"KILL\"></Request>"; //$NON-NLS-1$
	public static final String STOP_FB = "<Request ID=\"{0}\" Action=\"STOP\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$
	public static final String DELETE_FB = "<Request ID=\"{0}\" Action=\"DELETE\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$
	public static final String DELETE_CONNECTION = "<Request ID=\"{0}\" Action=\"DELETE\"><Connection Source=\"{1}\" Destination=\"{2}\"/></Request>"; //$NON-NLS-1$

	public static final String QUERY_FB_INSTANCES = "<Request ID=\"{0}\" Action=\"QUERY\"><FB Name=\"*\" Type=\"*\"/></Request>"; //$NON-NLS-1$

	public static final String READ_WATCHES = "<Request ID=\"{0}\" Action=\"READ\"><Watches/></Request>"; //$NON-NLS-1$
	public static final String ADD_WATCH = "<Request ID=\"{0}\" Action=\"CREATE\"><Watch Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String DELETE_WATCH = "<Request ID=\"{0}\" Action=\"DELETE\"><Watch Source=\"{1}\" Destination=\"{2}\" /></Request>"; //$NON-NLS-1$
	public static final String FORCE_VALUE = "<Request ID=\"{0}\" Action=\"WRITE\"><Connection Source=\"{1}\" Destination=\"{2}\" force=\"{3}\" /></Request>"; //$NON-NLS-1$

	public static final String RESET_RESOURCE = "<Request ID=\"{0}\" Action=\"RESET\"><FB Name=\"{1}\" Type=\"\"/></Request>"; //$NON-NLS-1$

	public static final Response EMPTY_RESPONSE;

	static {
		// ensure that all entries in the empty response return appropriate empty values
		EMPTY_RESPONSE = DevResponseFactory.eINSTANCE.createResponse();
		EMPTY_RESPONSE.setFblist(DevResponseFactory.eINSTANCE.createFBList());
		EMPTY_RESPONSE.setID("0"); //$NON-NLS-1$
		EMPTY_RESPONSE.setWatches(DevResponseFactory.eINSTANCE.createWatches());
	}

	private final Set<String> genFBs = new HashSet<>();
	private int id = 0;

	String getNextId() {
		return Integer.toString(id++);
	}

	private final ResponseMapping respMapping = new ResponseMapping();

	public DeploymentExecutor(final Device dev) {
		this(dev, null);
	}

	public DeploymentExecutor(final Device dev, final IDeviceManagementCommunicationHandler overrideHandler) {
		super(dev, overrideHandler);
		genFBs.add("PUBLISH"); //$NON-NLS-1$
		genFBs.add("SUBSCRIBE"); //$NON-NLS-1$
		genFBs.add("PUBL"); //$NON-NLS-1$
		genFBs.add("SUBL"); //$NON-NLS-1$
		genFBs.add("SERVER"); //$NON-NLS-1$
		genFBs.add("CLIENT"); //$NON-NLS-1$
	}

	@Override
	protected IDeviceManagementCommunicationHandler createCommunicationHandler(final Device dev) {
		// currently we only have the ability to connect via Ethernet to our devices, if
		// this changes add here according factories
		return new EthernetDeviceManagementCommunicationHandler();
	}

	@Override
	public void createResource(final Resource resource) throws DeploymentException {
		final String request = MessageFormat.format(CREATE_RESOURCE_INSTANCE, getNextId(), resource.getName(),
				resource.getTypeName());
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (final EOFException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_DeviceConnectionClosed, resource.getName()), e);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_CreateResourceFailed, resource.getName()), e);
		}
	}

	@Override
	public void writeResourceParameter(final Resource resource, final String parameter, final String value)
			throws DeploymentException {

		final String encodedValue = encodeXMLChars(value);
		final String request = generateWriteParamRequest(resource.getName(), parameter, encodedValue);
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_WriteResourceParameterFailed,
					resource.getName(), parameter), e);
		}
	}

	protected String generateWriteParamRequest(final String targetElementName, final String parameter,
			final String value) {
		return generateWriteParamRequest(targetElementName + "." + parameter, value); //$NON-NLS-1$
	}

	protected String generateWriteParamRequest(final String name, final String value) {
		return MessageFormat.format(getWriteParameterMessage(), getNextId(), value, name);
	}

	@SuppressWarnings("static-method") // this method needs to be overwritable by subclasses
	protected String getWriteParameterMessage() {
		return WRITE_PARAMETER;
	}

	private static String encodeXMLChars(final String value) {
		String retVal = value;
		retVal = retVal.replace("\"", "&quot;"); //$NON-NLS-1$ //$NON-NLS-2$
		retVal = retVal.replace("'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
		retVal = retVal.replace("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		retVal = retVal.replace(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		return retVal;
	}

	@Override
	public void writeFBParameter(final Resource resource, final String name, final String value)
			throws DeploymentException {
		final String encodedValue = encodeXMLChars(value);
		final String request = generateWriteParamRequest(name, encodedValue);
		try {
			sendREQ(resource.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_WriteFBParameterFailed, resource.getName(), name),
					e);
		}
	}

	@Override
	public void writeFBParameter(final Resource resource, final String value, final FBDeploymentData fbData,
			final VarDeclaration varDecl) throws DeploymentException {
		final String encodedValue = encodeXMLChars(value);
		final String request = generateWriteParamRequest(fbData.getPrefix() + fbData.getFb().getName(),
				varDecl.getName(), encodedValue);
		try {
			sendREQ(resource.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_WriteFBParameterFailed,
					resource.getName(), varDecl.getName()), e);
		}

	}

	@Override
	public void createConnection(final Resource resource, final ConnectionDeploymentData connData)
			throws DeploymentException {
		final IInterfaceElement source = connData.getSource();
		final IInterfaceElement destination = connData.getDestination();
		if ((null == source) || (null == destination) || (null == source.getFBNetworkElement())
				|| (null == destination.getFBNetworkElement())) {
			throw new DeploymentException(Messages.DeploymentExecutor_CreateConnectionFailed);
		}
		final FBNetworkElement sourceFB = source.getFBNetworkElement();
		final FBNetworkElement destFB = destination.getFBNetworkElement();
		final String request = MessageFormat.format(CREATE_CONNECTION, getNextId(),
				connData.getSourcePrefix() + sourceFB.getName() + "." + source.getName(), //$NON-NLS-1$
				connData.getDestinationPrefix() + destFB.getName() + "." + destination.getName()); //$NON-NLS-1$

		try {
			sendREQ(resource.getName(), request);
		} catch (final IOException e) {
			// TODO model refactoring - add here more information on what connection had the
			// issue
			throw new DeploymentException(Messages.DeploymentExecutor_CreateConnectionFailed, e);
		}
	}

	@Override
	public void startResource(final Resource res) throws DeploymentException {
		final String request = MessageFormat.format(START, Integer.valueOf(getNextId()));
		try {
			sendREQ(res.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_StartingResourceFailed, res.getName()), e);
		}
	}

	@Override
	public void stopResource(final Resource res) throws DeploymentException {
		final String request = MessageFormat.format(STOP, Integer.valueOf(getNextId()));
		try {
			sendREQ(res.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_StartingResourceFailed, res.getName()), e);
		}
	}

	@Override
	public void startDevice(final Device dev) throws DeploymentException {
		final String request = MessageFormat.format(START, Integer.valueOf(getNextId()));
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_StartingDeviceFailed, dev.getName()), e);
		}
	}

	@Override
	public void writeDeviceParameter(final Device device, final String parameter, final String value)
			throws DeploymentException {
		final String encodedValue = encodeXMLChars(value);
		final String request = MessageFormat.format(getWriteParameterMessage(), Integer.valueOf(getNextId()),
				encodedValue, parameter);
		try {
			sendREQ("", request); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_WriteDeviceParameterFailed,
					device.getName(), parameter), e);
		}
	}

	@Override
	public void deleteResource(final String resName) throws DeploymentException {
		final String delete = MessageFormat.format(DELETE_FB, getNextId(), resName);
		try {
			killResource(resName);
			sendREQ("", delete); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_DeleteFBFailed, resName), e);
		}
	}

	@Override
	public void resetResource(final String resName) throws DeploymentException {
		final String reset = MessageFormat.format(RESET_RESOURCE, getNextId(), resName);
		try {
			sendREQ("", reset); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_DeleteFBFailed, resName), e);
		}
	}

	@Override
	public void killResource(final String resName) throws DeploymentException {
		final String kill = MessageFormat.format(KILL_FB, getNextId(), resName);
		try {
			sendREQ("", kill); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_KillFBFailed, resName), e);
		}
	}

	@Override
	public void deleteConnection(final Resource res, final ConnectionDeploymentData conData)
			throws DeploymentException {
		// do nothing
	}

	@Override
	public void deleteFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		// do nothing
	}

	@Override
	public void startFB(final Resource res, final FBDeploymentData fbData) throws DeploymentException {
		final String fullFbInstanceName = fbData.getPrefix() + fbData.getFb().getName();
		final String request = MessageFormat.format(START_FB, getNextId(), fullFbInstanceName,
				fbData.getFb().getFullTypeName());
		try {
			sendREQ(res.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_StartingFBFailed, fullFbInstanceName), e);
		}
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		final String fbType = ForteTypeNameCreator.getForteTypeName(fbData.getFb());
		final String fullFbInstanceName = fbData.getPrefix() + fbData.getFb().getName();
		if (fbType.isEmpty()) {
			throw new DeploymentException((MessageFormat
					.format(Messages.DeploymentExecutor_CreateFBInstanceFailedNoTypeFound, fullFbInstanceName)));
		}
		final String request = MessageFormat.format(CREATE_FB_INSTANCE, getNextId(), fullFbInstanceName, fbType);
		try {
			sendREQ(res.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_CreateFBInstanceFailed, fullFbInstanceName), e);
		}
	}

	@Override
	public void killDevice(final Device dev) throws DeploymentException {
		final String kill = MessageFormat.format(KILL_DEVICE, getNextId());
		try {
			sendREQ("", kill); //$NON-NLS-1$
		} catch (final EOFException e) {
			// exception can be ignored, as no response is returned by forte
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_KillDeviceFailed, dev.getName()), e);
		} finally {
			resetTypes();
		}
	}

	@Override
	public List<org.eclipse.fordiac.ide.deployment.devResponse.Resource> queryResources() throws DeploymentException {
		String result;
		try {
			result = sendREQ("", MessageFormat.format(QUERY_FB_INSTANCES, getNextId())); //$NON-NLS-1$
			final Response resp = parseResponse(result);
			if (null != resp.getFblist() && null != resp.getFblist().getFbs()) {
				return resp.getFblist().getFbs().stream().map(fb -> {
					final org.eclipse.fordiac.ide.deployment.devResponse.Resource res = DevResponseFactory.eINSTANCE
							.createResource();
					res.setName(fb.getName());
					res.setType(fb.getType());
					return res;
				}).toList();
			}
			return Collections.emptyList();
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_QueryResourcesFailed, getDevice().getName()), e);
		}
	}

	protected Response parseResponse(final String result) throws IOException {
		if (null != result) {
			final InputSource source = new InputSource(new StringReader(result));
			final XMLResource xmlResource = new XMLResourceImpl();
			xmlResource.load(source, respMapping.getLoadOptions());
			for (final EObject object : xmlResource.getContents()) {
				if (object instanceof final Response response) {
					return response;
				}
			}
		}
		return EMPTY_RESPONSE;
	}

	@Override
	public Response readWatches() throws DeploymentException {
		final String request = MessageFormat.format(READ_WATCHES, getNextId());
		try {
			return parseResponse(sendREQ("", request)); //$NON-NLS-1$
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_ReadWatchesFailed, getDevice().getName()), e);
		}
	}

	@Override
	public boolean addWatch(final Resource resource, final String name) throws DeploymentException {
		final String request = MessageFormat.format(ADD_WATCH, getNextId(), name, "*"); //$NON-NLS-1$
		try {
			final String response = sendREQ(resource.getName(), request);
			return !response.isBlank();
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_AddWatchesFailed, name), e);
		}
	}

	@Override
	public boolean removeWatch(final Resource resource, final String name) throws DeploymentException {
		final String request = MessageFormat.format(DELETE_WATCH, getNextId(), name, "*"); //$NON-NLS-1$
		try {
			final String response = sendREQ(resource.getName(), request);
			return !response.isBlank();
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_DeleteWatchesFailed, name),
					e);
		}
	}

	@Override
	public void triggerEvent(final Resource resource, final String name) throws DeploymentException {
		final String request = MessageFormat.format(getWriteParameterMessage(), getNextId(), "$e", //$NON-NLS-1$
				name);
		try {
			sendREQ(resource.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_TriggerEventFailed, name),
					e);
		}
	}

	@Override
	public void forceValue(final Resource resource, final String name, final String value) throws DeploymentException {
		final String encodedValue = encodeXMLChars(value);
		final String request = MessageFormat.format(FORCE_VALUE, getNextId(), encodedValue, name, "true"); //$NON-NLS-1$
		try {
			sendREQ(resource.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(
					MessageFormat.format(Messages.DeploymentExecutor_ForceValueFailed, name, value), e);
		}
	}

	@Override
	public void clearForce(final Resource resource, final String name) throws DeploymentException {
		final String request = MessageFormat.format(FORCE_VALUE, getNextId(), "*", name, //$NON-NLS-1$
				"false"); //$NON-NLS-1$
		try {
			sendREQ(resource.getName(), request);
		} catch (final IOException e) {
			throw new DeploymentException(MessageFormat.format(Messages.DeploymentExecutor_ClearForceFailed, name), e);
		}
	}
}
