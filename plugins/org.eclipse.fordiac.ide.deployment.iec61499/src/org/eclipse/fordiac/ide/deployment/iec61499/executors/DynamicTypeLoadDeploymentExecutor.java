/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH, Johannes Kepler University
 * 				 2019		Jan Holzweber
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *     - fixed E_RESTART
 *   Alois Zoitl - reworked this class for the new device managment interaction
 *                 interface
 *   Jan Holzweber - reworked deploying mechanism
 *   Fabio Gandolfi - reconstruct subapp hierarchy from resources + connections
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.executors;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppCrossingConnectionsCommand;
import org.eclipse.fordiac.ide.application.commands.NewSubAppCommand;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.Messages;
import org.eclipse.fordiac.ide.deployment.iec61499.ResponseMapping;
import org.eclipse.fordiac.ide.deployment.iec61499.handlers.QueryResponseHandler;
import org.eclipse.fordiac.ide.export.forte_lua.ForteLuaExportFilter;
import org.eclipse.fordiac.ide.model.Annotations;
import org.eclipse.fordiac.ide.model.LibraryElementTags;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.FBTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryTags;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DynamicTypeLoadDeploymentExecutor extends DeploymentExecutor {

	private static final String CREATE_FB_TYPE = "<Request ID=\"{0}\" Action=\"CREATE\"><FBType Name=\"{1}\">{2}</FBType></Request>"; //$NON-NLS-1$
	private static final String CREATE_ADAPTER_TYPE = "<Request ID=\"{0}\" Action=\"CREATE\"><AdapterType Name=\"{1}\">{2}</AdapterType></Request>"; //$NON-NLS-1$
	private static final String QUERY_FB_TYPES = "<Request ID=\"{0}\" Action=\"QUERY\"><FBType Name=\"*\" /></Request>"; //$NON-NLS-1$
	private static final String QUERY_FB_TYPE = "<Request ID=\"{0}\" Action=\"QUERY\"><FBType Name=\"{1}\" /></Request>"; //$NON-NLS-1$
	private static final String QUERY_ADAPTER_TYPES = "<Request ID=\"{0}\" Action=\"QUERY\"><AdapterType Name=\"*\" /></Request>"; //$NON-NLS-1$
	private static final String QUERY_ADAPTER_TYPE = "<Request ID=\"{0}\" Action=\"QUERY\"><AdapterType Name=\"{1}\" /></Request>"; //$NON-NLS-1$
	private static final String QUERY_CONNECTIONS = "<Request ID=\"{0}\" Action=\"QUERY\"><Connection Source=\"{1}\" Destination=\"{2}\"/></Request>"; //$NON-NLS-1$
	private static final String QUERY_PARAMETER = "<Request ID=\"{0}\" Action=\"READ\"><Connection Source=\"{1}\" Destination=\"*\" /></Request>"; //$NON-NLS-1$

	private static final Logger logger = Logger.getLogger(DynamicTypeLoadDeploymentExecutor.class);
	private final ResponseMapping respMapping = new ResponseMapping();

	public DynamicTypeLoadDeploymentExecutor(final Device dev,
			final IDeviceManagementCommunicationHandler overrideHandler) {
		super(dev, overrideHandler);
		// nothing to do here
	}

	@Override
	public void connect() throws DeploymentException {
		super.connect();

		// querying all FBTypes and AdapterTypes before trying to deploy any resources
		// or FBs
		queryFBTypes();
		queryAdapterTypes();

	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		// check for the adapters of the FB first
		final Map<String, AdapterType> adapters = getAdapterTypes(fbData.getFb().getType().getInterfaceList());
		if (!adapters.isEmpty()) {
			createAdapterTypes(adapters);
		}

		// check if we have to create the FBType first
		checkCreateFBType(fbData.getFb().getType());

		super.createFBInstance(fbData, res);

	}

	private static Map<String, AdapterType> getAdapterTypes(final InterfaceList interfaceList) {
		final Map<String, AdapterType> list = new HashMap<>();
		interfaceList.getPlugs().forEach(e -> list.put(e.getTypeName(), EcoreUtil.copy(e.getType())));
		interfaceList.getSockets().forEach(e -> list.put(e.getTypeName(), EcoreUtil.copy(e.getType())));
		return list;
	}

	private void checkCreateFBType(final FBType fbType) {
		// if the FPType does not exist create it
		if (!getTypes().contains(fbType.getName())) {
			try {
				createFBType(fbType);
			} catch (final DeploymentException ce) {
				logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed,
						fbType.getName()), ce);
			}
		}

	}

	public void createFBType(final FBType fbType) throws DeploymentException {
		setAttribute(getDevice(), "FBType", getTypes()); //$NON-NLS-1$

		if ((fbType instanceof BasicFBType) || (fbType instanceof CompositeFBType)) {
			if (fbType instanceof CompositeFBType) {
				createFBTypesOfCFB(fbType);
			}
			final String request = createLuaXmlRequestMessage(fbType);
			sendCreateFBTypeREQ(fbType, request);
		}
	}

	private void sendCreateFBTypeREQ(final FBType fbType, final String request) throws DeploymentException {
		try {
			final String result = sendREQ("", request); //$NON-NLS-1$
			if (result.contains("Reason")) { //$NON-NLS-1$
				throw new DeploymentException(MessageFormat.format(
						Messages.DynamicTypeLoadDeploymentExecutor_LUAScriptForFBTypeNotExecuted, fbType.getName()));
			}
			getTypes().add(fbType.getName());
		} catch (final IOException e) {
			logger.error(
					MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed, "LUA script"), e); //$NON-NLS-1$
		}
	}

	private void createFBTypesOfCFB(final FBType fbType) throws DeploymentException {
		for (final FBNetworkElement netelem : ((CompositeFBType) fbType).getFBNetwork().getNetworkElements()) {
			if (!getTypes().contains(netelem.getTypeName())) {
				final Map<String, AdapterType> adapters = getAdapterTypes(netelem.getInterface());
				if (!adapters.isEmpty()) {
					createAdapterTypes(adapters);
				}
				createFBType(netelem.getType());
			}
		}
	}

	private String createLuaXmlRequestMessage(final FBType fbType) {
		final ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();

		final String escapedLuaScript = escapeXmlCharacters(luaFilter.createLUA(fbType));

		return MessageFormat.format(CREATE_FB_TYPE, getNextId(), fbType.getName(), escapedLuaScript);
	}

	private static String escapeXmlCharacters(String luaScript) {
		luaScript = luaScript.replace("&", "&amp;"); //$NON-NLS-1$//$NON-NLS-2$
		luaScript = luaScript.replace("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		luaScript = luaScript.replace(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		luaScript = luaScript.replace("\"", "&quot;"); //$NON-NLS-1$ //$NON-NLS-2$
		return luaScript.replace("\'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static boolean isAttribute(final Device device, final String fbTypeName, final String attributeType) {
		if (null != device.getAttribute(attributeType)) {
			for (final String s : device.getAttributeValue(attributeType).split(",")) { //$NON-NLS-1$
				if (fbTypeName.equals(s.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	private static void setAttribute(final Device device, final String string, final Set<String> hashSet) {
		Display.getDefault()
				.asyncExec(() -> device.setAttribute(string, ElementaryTypes.STRING, String.join(", ", hashSet), //$NON-NLS-1$
						"created during deployment") //$NON-NLS-1$
				);
	}

	public void createAdapterType(final String adapterKey, final Map<String, AdapterType> adapters)
			throws DeploymentException {

		setAttribute(getDevice(), "AdapterType", getAdapterTypes()); //$NON-NLS-1$

		if (!getAdapterTypes().contains(adapterKey) || !isAttribute(getDevice(), adapterKey, "AdapterType")) { //$NON-NLS-1$
			final ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();
			final String luaSkript = luaFilter.createLUA(adapters.get(adapterKey));
			final String request = MessageFormat.format(CREATE_ADAPTER_TYPE, getNextId(), adapterKey, luaSkript);
			sendCreateAdapterTypeREQ(adapterKey, request);
		}
	}

	private void sendCreateAdapterTypeREQ(final String adapterKey, final String request) throws DeploymentException {
		try {
			final String result = sendREQ("", request); //$NON-NLS-1$
			if (result.contains("Reason")) { //$NON-NLS-1$
				throw new DeploymentException(MessageFormat.format(
						Messages.DynamicTypeLoadDeploymentExecutor_LUAScriptForAdapterTypeNotExecuted, adapterKey));
			}
			getAdapterTypes().add(adapterKey);
		} catch (final IOException e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed, "Adapter"), //$NON-NLS-1$
					e);
		}
	}

	public void queryResourcesWithNetwork(final Device dev) {
		try {
			for (final org.eclipse.fordiac.ide.deployment.devResponse.Resource resource : queryResources()) {
				final ResourceCreateCommand cmd = new ResourceCreateCommand(getResourceType(dev, resource.getType()),
						dev, false);
				cmd.execute();
				cmd.getResource().setName(resource.getName());
				dev.getResource().add(cmd.getResource());
				queryFBNetwork(cmd.getResource());
				queryConnections(cmd.getResource());
				queryParameters(cmd.getResource());
			}
		} catch (final Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Resources"), e); //$NON-NLS-1$
		}
	}

	private void queryParameters(final Resource res) {
		for (final FBNetworkElement fb : res.getFBNetwork().getNetworkElements()) {
			for (final VarDeclaration inVar : fb.getInterface().getInputVars()) {
				if (inVar.getInputConnections().isEmpty()) {
					queryParameter(res, fb, inVar);
				}
			}
		}
	}

	private void queryParameter(final Resource res, final FBNetworkElement fb, final VarDeclaration inVar) {
		final String request = MessageFormat.format(QUERY_PARAMETER, getNextId(), fb.getName() + "." + inVar.getName()); //$NON-NLS-1$
		try {
			final String result = sendREQ(res.getName(), request);
			if (result != null) {
				createParameter(inVar, parseResponse(result));
			}
		} catch (final Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Parameter"), e); //$NON-NLS-1$
		}
	}

	private static void createParameter(final VarDeclaration inVar, final Response response) {
		if (null != response.getConnection()) {
			final Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(response.getConnection().getDestination());
			inVar.setValue(value);
		}
	}

	private void queryConnections(final Resource res) {
		final String request = MessageFormat.format(QUERY_CONNECTIONS, getNextId(), "*", "*"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			final String result = sendREQ(res.getName(), request);
			if (result != null) {
				createConnections(res, parseResponse(result));
			}
		} catch (final Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Connections"), //$NON-NLS-1$
					e);
		}
	}

	private static void createConnections(final Resource res, final Response response) {
		if (null != response.getEndpointlist()) {
			for (final org.eclipse.fordiac.ide.deployment.devResponse.Connection connection : response.getEndpointlist()
					.getConnection()) {
				final String[] src = connection.getSource().split("\\."); //$NON-NLS-1$
				final FB srcFB;
				if (src.length > 2) {
					final SubApp srcSubapp = findSubAppOfFB(
							Arrays.asList(src).subList(0, src.length - 2).stream().collect(Collectors.joining(".")),
							res.getFBNetwork());
					srcFB = srcSubapp.getSubAppNetwork().getFBNamed(src[src.length - 2]);
				} else {
					srcFB = Annotations.getFBNamed(res.getFBNetwork(), src[0]);
				}
				final IInterfaceElement srcIE = srcFB.getInterfaceElement(src[src.length - 1]);

				final String[] dst = connection.getDestination().split("\\."); //$NON-NLS-1$
				final FB dstFB;
				if (dst.length > 2) {
					final SubApp dstFBSubApp = findSubAppOfFB(
							Arrays.asList(dst).subList(0, dst.length - 2).stream().collect(Collectors.joining(".")),
							res.getFBNetwork());
					dstFB = dstFBSubApp.getSubAppNetwork().getFBNamed(dst[dst.length - 2]);
				} else {
					dstFB = Annotations.getFBNamed(res.getFBNetwork(), dst[0]);
				}
				final IInterfaceElement dstIE = dstFB.getInterfaceElement(dst[dst.length - 1]);
				createConnectionCommand(res.getFBNetwork(), srcIE, dstIE);
			}
		}
	}

	private static void createConnectionCommand(final FBNetwork fbNet, final IInterfaceElement srcIE,
			final IInterfaceElement dstIE) {
		final Command cmd = CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(srcIE, dstIE);
		if (null != cmd && cmd.canExecute()) {
			cmd.execute();
		}
	}

	private void queryFBNetwork(final Resource res) {
		final String request = MessageFormat.format(QUERY_FB_INSTANCES, getNextId());
		try {
			final String result = sendREQ(res.getName(), request);
			if (result != null) {
				final InputSource source = new InputSource(new StringReader(result));
				final XMLResource xmlResource = new XMLResourceImpl();
				xmlResource.load(source, respMapping.getLoadOptions());
				createFBNetwork(res, xmlResource);
			}
		} catch (final Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Networks"), e); //$NON-NLS-1$
		}
	}

	private void createFBNetwork(final Resource res, final XMLResource xmlResource) {
		for (final EObject object : xmlResource.getContents()) {
			if (object instanceof Response) {
				createNotExistingAdapterTypes(res);
				addFBNetworkElements(res, (Response) object);
			}
		}
	}

	private void createNotExistingAdapterTypes(final Resource res) {
		for (final String entryName : getAdapterTypes()) {
			if (null == res.getDevice().getAutomationSystem().getTypeLibrary().getAdapterTypeEntry(entryName)) {
				addTypeToTypelib(res, entryName, "adp", QUERY_ADAPTER_TYPE); //$NON-NLS-1$
			}
		}
	}

	private void addFBNetworkElements(final Resource res, final Response object) {
		int i = 0;
		for (final org.eclipse.fordiac.ide.deployment.devResponse.FB fbresult : (object).getFblist().getFbs()) {
			if (!"E_RESTART".equals(fbresult.getType())) { //$NON-NLS-1$
				FBTypeEntry entry = res.getDevice().getAutomationSystem().getTypeLibrary()
						.getFBTypeEntry(getPlainType(fbresult));

				if (null == entry) {
					addTypeToTypelib(res, fbresult.getType(), TypeLibraryTags.FB_TYPE_FILE_ENDING, QUERY_FB_TYPE);
					entry = res.getDevice().getAutomationSystem().getTypeLibrary().getFBTypeEntry(fbresult.getType());
				}

				final FBCreateCommand fbcmd;
				if (fbresult.getName().contains(".")) {
					final SubApp parent = findSubAppOfFB(
							fbresult.getName().substring(0, fbresult.getName().lastIndexOf(".")), res.getFBNetwork());
					fbcmd = new FBCreateCommand(entry, parent.getSubAppNetwork(), 10, 10);
				} else {
					fbcmd = new FBCreateCommand(entry, res.getFBNetwork(), 100 * i, 10);
				}
				if (fbcmd.canExecute()) {
					fbcmd.execute();
					if (fbresult.getName().contains(".")) {
						fbcmd.getFB().setName(fbresult.getName().substring(fbresult.getName().lastIndexOf(".") + 1,
								fbresult.getName().length()));
					} else {
						fbcmd.getFB().setName(fbresult.getName());
					}
				}

				if (fbcmd.getFB() instanceof StructManipulator && getStructFromMultiplexer(res, fbresult) != null) {
					final ChangeStructCommand changeStructCmd = new ChangeStructCommand(
							(StructManipulator) fbcmd.getFB(), getStructFromMultiplexer(res, fbresult));
					if (changeStructCmd.canExecute()) {
						changeStructCmd.execute();
					}
				}
				i++;
			}
		}
	}

	private static String getPlainType(final org.eclipse.fordiac.ide.deployment.devResponse.FB fb) {
		if (fb.getType().contains(LibraryElementTags.FB_TYPE_STRUCT_MUX)) {
			return LibraryElementTags.FB_TYPE_STRUCT_MUX;
		}
		if (fb.getType().contains(LibraryElementTags.FB_TYPE_STRUCT_DEMUX)) {
			return LibraryElementTags.FB_TYPE_STRUCT_DEMUX;
		}
		return fb.getType();
	}

	private static StructuredType getStructFromMultiplexer(final Resource res,
			final org.eclipse.fordiac.ide.deployment.devResponse.FB devFB) {
		// find number in STRUCT_MUX_1_STRUCTNAME or STRUT_DEMUX_1_STRUCTNAME
		final Matcher matcher = Pattern.compile("[^0-9]*([0-9]+).*").matcher(devFB.getType());
		if (matcher.matches()) {
			final DataTypeLibrary library = res.getDevice().getAutomationSystem().getTypeLibrary().getDataTypeLibrary();
			return library.getStructuredType(devFB.getType().substring(devFB.getType().indexOf(matcher.group(1)) + 1));
		}
		return null;
	}

	private void addTypeToTypelib(final Resource res, final String typeName, final String extension,
			final String messageType) {
		final String request = MessageFormat.format(messageType, getNextId(), typeName);
		try {
			String result = sendREQ(res.getName(), request);
			if (result != null) {
				result = result.replaceFirst("<Response ID=\"\\d+\">\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
				result = result.replaceFirst("</Response>", ""); //$NON-NLS-1$ //$NON-NLS-2$
				if (!result.contains("Reason=\"UNSUPPORTED_TYPE\"") && !result.contains("Reason=\"UNSUPPORTED_CMD\"")) { //$NON-NLS-1$ //$NON-NLS-2$
					final AutomationSystem system = res.getDevice().getAutomationSystem();
					final IFile sysFile = system.getTypeEntry().getFile();
					final Path path = Paths.get(sysFile.getLocation() + File.separator + "generated" //$NON-NLS-1$
							+ File.separator + typeName + "." + extension); //$NON-NLS-1$
					final File file = new File(path.toString());
					file.getParentFile().mkdirs();
					Files.write(path, result.getBytes(), StandardOpenOption.CREATE);
					TypeLibraryManager.INSTANCE.refreshTypeLib(sysFile);
				}
			}
		} catch (final Exception e) {
			if (messageType.equals(QUERY_ADAPTER_TYPE)) {
				logger.error(
						MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Adapter Type"), //$NON-NLS-1$
						e);
			} else {
				logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "FB Type"), //$NON-NLS-1$
						e);
			}
		}
	}

	private static SubApp findSubAppOfFB(final String path, final FBNetwork network) {
		final String[] paths = path.split("\\."); //$NON-NLS-1$
		SubApp subapp = null;
		if (paths.length > 0) {
			subapp = network.getSubAppNamed(paths[0]);
			if (subapp == null) {
				subapp = createSubApp(network, paths[0]);
			}
			for (int i = 1; i < paths.length; i++) {
				final SubApp newSubapp = subapp.getSubAppNetwork().getSubAppNamed(paths[i]);
				if (newSubapp == null) {
					subapp = createSubApp(subapp.getSubAppNetwork(), paths[i]);
				} else {
					subapp = newSubapp;
				}
			}
		}
		return subapp;
	}

	private static SubApp createSubApp(final FBNetwork network, final String name) {
		final NewSubAppCommand subapp = new NewSubAppCommand(network, Collections.emptyList(), 10, 10);
		if (subapp.canExecute()) {
			subapp.execute();
			subapp.getElement().setName(name);
		}
		return subapp.getElement();
	}

	private static ResourceTypeEntry getResourceType(final Device device, final String resTypeName) {
		return device.getTypeEntry().getTypeLibrary().getResourceTypeEntry(resTypeName);
	}

	private void queryFBTypes() {
		final String request = MessageFormat.format(QUERY_FB_TYPES, getNextId());
		try {
			final QueryResponseHandler queryResp = sendQUERY("", request); //$NON-NLS-1$
			setTypes(queryResp.getQueryResult());
		} catch (final Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "FB Types"), e); //$NON-NLS-1$
		}

	}

	private void queryAdapterTypes() {
		final String request = MessageFormat.format(QUERY_ADAPTER_TYPES, getNextId());
		try {
			final QueryResponseHandler queryResp = sendQUERY("", request); //$NON-NLS-1$
			setAdapterTypes(queryResp.getQueryResult());
		} catch (final Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Adapter Types"), //$NON-NLS-1$
					e);
		}

	}

	private void createAdapterTypes(final Map<String, AdapterType> adapters) {
		adapters.keySet().forEach(e -> {
			try {
				createAdapterType(e, adapters);
			} catch (final DeploymentException ce) {
				logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed, e), ce);
			}
		});
	}

	private QueryResponseHandler sendQUERY(final String destination, final String request)
			throws IOException, ParserConfigurationException, SAXException {
		final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		final SAXParser saxParser = saxParserFactory.newSAXParser();
		saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, ""); //$NON-NLS-1$
		saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); //$NON-NLS-1$
		final QueryResponseHandler handler = new QueryResponseHandler();
		final String response = sendREQ(destination, request);
		saxParser.parse(new InputSource(new StringReader(response)), handler);
		return handler;
	}

}
