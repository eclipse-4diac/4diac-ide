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
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.export.forte_lua.ForteLuaExportFilter;
import org.eclipse.fordiac.ide.model.Annotations;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.systemconfiguration.commands.ResourceCreateCommand;
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
	private ResponseMapping respMapping = new ResponseMapping();

	public DynamicTypeLoadDeploymentExecutor(Device dev, IDeviceManagementCommunicationHandler overrideHandler) {
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
		Map<String, AdapterType> adapters = getAdapterTypes(fbData.getFb().getType().getInterfaceList());
		if (!adapters.isEmpty()) {
			createAdapterTypes(adapters);
		}

		// check if we have to create the FBType first
		checkCreateFBType(fbData.getFb().getType());

		super.createFBInstance(fbData, res);

	}

	private static Map<String, AdapterType> getAdapterTypes(InterfaceList interfaceList) {
		Map<String, AdapterType> list = new HashMap<>();
		interfaceList.getPlugs().forEach(e -> list.put(e.getTypeName(), EcoreUtil.copy(e.getType())));
		interfaceList.getSockets().forEach(e -> list.put(e.getTypeName(), EcoreUtil.copy(e.getType())));
		return list;
	}

	private void checkCreateFBType(FBType fbType) {
		// if the FPType does not exist create it
		if (!getTypes().contains(fbType.getName())) {
			try {
				createFBType(fbType);
			} catch (DeploymentException ce) {
				logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed,
						fbType.getName()));
			}
		}

	}

	public void createFBType(final FBType fbType) throws DeploymentException {
		setAttribute(getDevice(), "FBType", getTypes()); //$NON-NLS-1$

		if ((fbType instanceof BasicFBType) || (fbType instanceof CompositeFBType)) {
			if (fbType instanceof CompositeFBType) {
				createFBTypesOfCFB(fbType);
			}
			String request = createLuaXmlRequestMessage(fbType);
			sendCreateFBTypeREQ(fbType, request);
		}
	}

	private void sendCreateFBTypeREQ(final FBType fbType, String request) throws DeploymentException {
		try {
			String result = sendREQ("", request); //$NON-NLS-1$
			if (result.contains("Reason")) { //$NON-NLS-1$
				throw new DeploymentException(MessageFormat.format(
						Messages.DynamicTypeLoadDeploymentExecutor_LUAScriptForFBTypeNotExecuted, fbType.getName()));
			} else {
				getTypes().add(fbType.getName());
			}
		} catch (IOException e) {
			logger.error(
					MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed, "LUA script")); //$NON-NLS-1$
		}
	}

	private void createFBTypesOfCFB(final FBType fbType) throws DeploymentException {
		for (FBNetworkElement netelem : ((CompositeFBType) fbType).getFBNetwork().getNetworkElements()) {
			if (!getTypes().contains(netelem.getTypeName())) {
				Map<String, AdapterType> adapters = getAdapterTypes(netelem.getInterface());
				if (!adapters.isEmpty()) {
					createAdapterTypes(adapters);
				}
				createFBType(netelem.getType());
			}
		}
	}

	private String createLuaXmlRequestMessage(final FBType fbType) {
		ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();

		String escapedLuaScript = escapeXmlCharacters(luaFilter.createLUA(fbType));

		return MessageFormat.format(CREATE_FB_TYPE, getNextId(), fbType.getName(), escapedLuaScript);
	}

	private String escapeXmlCharacters(String luaScript) {
		luaScript = luaScript.replace("&", "&amp;"); //$NON-NLS-1$//$NON-NLS-2$
		luaScript = luaScript.replace("<", "&lt;"); //$NON-NLS-1$ //$NON-NLS-2$
		luaScript = luaScript.replace(">", "&gt;"); //$NON-NLS-1$ //$NON-NLS-2$
		luaScript = luaScript.replace("\"", "&quot;"); //$NON-NLS-1$ //$NON-NLS-2$
		return luaScript.replace("\'", "&apos;"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static boolean isAttribute(Device device, String fbTypeName, String attributeType) {
		if (null != device.getAttribute(attributeType)) {
			for (String s : device.getAttributeValue(attributeType).split(",")) { //$NON-NLS-1$
				if (fbTypeName.equals(s.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	private static void setAttribute(Device device, String string, Set<String> hashSet) {
		Display.getDefault().asyncExec(
				() -> device.setAttribute(string, "STRING", String.join(", ", hashSet), "created during deployment") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		);
	}

	public void createAdapterType(final String adapterKey, Map<String, AdapterType> adapters)
			throws DeploymentException {

		setAttribute(getDevice(), "AdapterType", getAdapterTypes()); //$NON-NLS-1$

		if (!getAdapterTypes().contains(adapterKey) || !isAttribute(getDevice(), adapterKey, "AdapterType")) { //$NON-NLS-1$
			ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();
			String luaSkript = luaFilter.createLUA(adapters.get(adapterKey));
			String request = MessageFormat.format(CREATE_ADAPTER_TYPE, getNextId(), adapterKey, luaSkript);
			sendCreateAdapterTypeREQ(adapterKey, request);
		}
	}

	private void sendCreateAdapterTypeREQ(final String adapterKey, String request) throws DeploymentException {
		try {
			String result = sendREQ("", request); //$NON-NLS-1$
			if (result.contains("Reason")) { //$NON-NLS-1$
				throw new DeploymentException(MessageFormat.format(
						Messages.DynamicTypeLoadDeploymentExecutor_LUAScriptForAdapterTypeNotExecuted, adapterKey));
			}
			getAdapterTypes().add(adapterKey);
		} catch (IOException e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed, "Adapter")); //$NON-NLS-1$
		}
	}

	public void queryResourcesWithNetwork(Device dev) {
		try {
			for (org.eclipse.fordiac.ide.deployment.devResponse.Resource resource : queryResources()) {
				ResourceCreateCommand cmd = new ResourceCreateCommand(getResourceType(dev, resource.getType()), dev,
						false);
				cmd.execute();
				cmd.getResource().setName(resource.getName());
				dev.getResource().add(cmd.getResource());
				queryFBNetwork(cmd.getResource());
				queryConnections(cmd.getResource());
				queryParameters(cmd.getResource());
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Resources")); //$NON-NLS-1$
		}
	}

	private void queryParameters(Resource res) {
		for (FBNetworkElement fb : res.getFBNetwork().getNetworkElements()) {
			for (VarDeclaration inVar : fb.getInterface().getInputVars()) {
				if (inVar.getInputConnections().isEmpty()) {
					queryParameter(res, fb, inVar);
				}
			}
		}
	}

	private void queryParameter(Resource res, FBNetworkElement fb, VarDeclaration inVar) {
		@SuppressWarnings("nls")
		String request = MessageFormat.format(QUERY_PARAMETER, getNextId(), fb.getName() + "." + inVar.getName());
		try {
			String result = sendREQ(res.getName(), request);
			if (result != null) {
				createParameter(res, inVar, parseResponse(result));
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Parameter")); //$NON-NLS-1$
		}
	}

	private static void createParameter(Resource res, VarDeclaration inVar, Response response) {
		if (null != response.getConnection()) {
			Value value = LibraryElementFactory.eINSTANCE.createValue();
			value.setValue(response.getConnection().getDestination());
			inVar.setValue(value);
		}
	}

	private void queryConnections(Resource res) {
		String request = MessageFormat.format(QUERY_CONNECTIONS, getNextId(), "*", "*"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			String result = sendREQ(res.getName(), request);
			if (result != null) {
				createConnections(res, parseResponse(result));
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Connections")); //$NON-NLS-1$
		}
	}

	private static void createConnections(Resource res, Response response) {
		if (null != response.getEndpointlist()) {
			for (org.eclipse.fordiac.ide.deployment.devResponse.Connection connection : response.getEndpointlist()
					.getConnection()) {
				// TODO currently no subapps supported - bug 538333
				String[] src = connection.getSource().split("\\."); //$NON-NLS-1$
				FB srcFB = Annotations.getFBNamed(res.getFBNetwork(), src[0]);
				IInterfaceElement srcIE = Annotations.getInterfaceElement(srcFB, src[src.length - 1]);
				String[] dst = connection.getDestination().split("\\."); //$NON-NLS-1$
				FB dstFB = Annotations.getFBNamed(res.getFBNetwork(), dst[0]);
				IInterfaceElement dstIE = Annotations.getInterfaceElement(dstFB, dst[dst.length - 1]);
				createConnectionCommand(res.getFBNetwork(), srcIE, dstIE);
			}
		}
	}

	private static void createConnectionCommand(FBNetwork fbNet, IInterfaceElement srcIE, IInterfaceElement dstIE) {
		AbstractConnectionCreateCommand cmd = null;
		if (srcIE instanceof Event) {
			cmd = new EventConnectionCreateCommand(fbNet);
		} else if (srcIE instanceof AdapterDeclaration) {
			cmd = new AdapterConnectionCreateCommand(fbNet);
		} else if (srcIE instanceof VarDeclaration) {
			cmd = new DataConnectionCreateCommand(fbNet);
		}
		if (null != cmd) {
			cmd.setSource(srcIE);
			cmd.setDestination(dstIE);
			if (cmd.canExecute()) {
				cmd.execute();
			}
		}
	}

	private void queryFBNetwork(Resource res) {
		String request = MessageFormat.format(QUERY_FB_INSTANCES, getNextId());
		try {
			String result = sendREQ(res.getName(), request);
			if (result != null) {
				InputSource source = new InputSource(new StringReader(result));
				XMLResource xmlResource = new XMLResourceImpl();
				xmlResource.load(source, respMapping.getLoadOptions());
				createFBNetwork(res, xmlResource);
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Networks")); //$NON-NLS-1$
		}
	}

	private void createFBNetwork(Resource res, XMLResource xmlResource) {
		for (EObject object : xmlResource.getContents()) {
			if (object instanceof Response) {
				createNotExistingAdapterTypes(res);
				addFBNetworkElements(res, (Response) object);
			}
		}
	}

	private void createNotExistingAdapterTypes(Resource res) {
		for (String entryName : getAdapterTypes()) {
			if (null == res.getDevice().getAutomationSystem().getPalette().getTypeEntry(entryName)) {
				addTypeToTypelib(res, entryName, "adp", QUERY_ADAPTER_TYPE); //$NON-NLS-1$
			}
		}
	}

	private void addFBNetworkElements(Resource res, Response object) {
		int i = 0;
		for (org.eclipse.fordiac.ide.deployment.devResponse.FB fbresult : (object).getFblist().getFbs()) {
			if (!"E_RESTART".equals(fbresult.getType())) { //$NON-NLS-1$
				FBTypePaletteEntry entry = (FBTypePaletteEntry) res.getDevice().getAutomationSystem().getPalette()
						.getTypeEntry(fbresult.getType());
				if (null == entry) {
					addTypeToTypelib(res, fbresult.getType(), "fbt", QUERY_FB_TYPE); //$NON-NLS-1$
					entry = (FBTypePaletteEntry) res.getDevice().getAutomationSystem().getPalette()
							.getTypeEntry(fbresult.getType());
				}
				FBCreateCommand fbcmd = new FBCreateCommand(entry, res.getFBNetwork(), 100 * i, 10);
				if (fbcmd.canExecute()) {
					fbcmd.execute();
					fbcmd.getFB().setName(fbresult.getName());
				}
				i++;
			}
		}
	}

	private void addTypeToTypelib(Resource res, String typeName, String extension, String messageType) {
		String request = MessageFormat.format(messageType, getNextId(), typeName);
		try {
			String result = sendREQ(res.getName(), request);
			if (result != null) {
				result = result.replaceFirst("<Response ID=\"[0-9]+\">\n", ""); //$NON-NLS-1$ //$NON-NLS-2$
				result = result.replaceFirst("</Response>", ""); //$NON-NLS-1$ //$NON-NLS-2$
				if (!result.contains("Reason=\"UNSUPPORTED_TYPE\"") && !result.contains("Reason=\"UNSUPPORTED_CMD\"")) { //$NON-NLS-1$ //$NON-NLS-2$
					AutomationSystem system = res.getDevice().getAutomationSystem();
					Path path = Paths.get(system.getProject().getLocation() + File.separator + "generated" //$NON-NLS-1$
							+ File.separator + typeName + "." + extension); //$NON-NLS-1$
					File file = new File(path.toString());
					file.getParentFile().mkdirs();
					Files.write(path, result.getBytes(), StandardOpenOption.CREATE);
					Palette palette = system.getPalette();
					TypeLibrary.refreshPalette(palette);
				}
			}
		} catch (Exception e) {
			if (messageType.equals(QUERY_ADAPTER_TYPE)) {
				logger.error(
						MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Adapter Type")); //$NON-NLS-1$
			} else {
				logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "FB Type")); //$NON-NLS-1$
			}
		}
	}

	private static ResourceTypeEntry getResourceType(Device device, String resTypeName) {
		List<PaletteEntry> typeEntries = device.getPaletteEntry().getGroup().getPallete().getTypeEntries(resTypeName);
		if (!typeEntries.isEmpty() && (typeEntries.get(0) instanceof ResourceTypeEntry)) {
			return (ResourceTypeEntry) typeEntries.get(0);
		}
		return null;
	}

	private void queryFBTypes() {
		String request = MessageFormat.format(QUERY_FB_TYPES, getNextId());
		try {
			QueryResponseHandler queryResp = sendQUERY("", request); //$NON-NLS-1$
			setTypes(queryResp.getQueryResult());
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "FB Types")); //$NON-NLS-1$
		}

	}

	private void queryAdapterTypes() {
		String request = MessageFormat.format(QUERY_ADAPTER_TYPES, getNextId());
		try {
			QueryResponseHandler queryResp = sendQUERY("", request); //$NON-NLS-1$
			setAdapterTypes(queryResp.getQueryResult());
		} catch (Exception e1) {
			logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_QueryFailed, "Adapter Types")); //$NON-NLS-1$
		}

	}

	private void createAdapterTypes(Map<String, AdapterType> adapters) {
		adapters.keySet().forEach(e -> {
			try {
				createAdapterType(e, adapters);
			} catch (DeploymentException ce) {
				logger.error(MessageFormat.format(Messages.DynamicTypeLoadDeploymentExecutor_CreateTypeFailed, e));
			}
		});
	}

	private QueryResponseHandler sendQUERY(final String destination, final String request)
			throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		SAXParser saxParser = saxParserFactory.newSAXParser();
		QueryResponseHandler handler = new QueryResponseHandler();
		String response = sendREQ(destination, request);
		saxParser.parse(new InputSource(new StringReader(response)), handler);
		return handler;
	}

}
