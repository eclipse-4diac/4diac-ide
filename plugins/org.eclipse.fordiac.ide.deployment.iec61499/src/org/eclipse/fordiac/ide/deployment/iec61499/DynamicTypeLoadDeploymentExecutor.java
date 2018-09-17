/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH, Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked this class for the new device managment interaction 
 *                 interface  
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.FBCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
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
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.swt.widgets.Display;
import org.xml.sax.InputSource;

public class DynamicTypeLoadDeploymentExecutor extends DeploymentExecutor {
	
	private static final String CREATE_FB_TYPE = "<Request ID=\"{0}\" Action=\"CREATE\"><FBType Name=\"{1}\">{2}</FBType></Request>"; //$NON-NLS-1$
	private static final String CREATE_ADAPTER_TYPE = "<Request ID=\"{0}\" Action=\"CREATE\"><AdapterType Name=\"{1}\">{2}</AdapterType></Request>"; //$NON-NLS-1$
	private static final String QUERY_FB_TYPES = "<Request ID=\"{0}\" Action=\"QUERY\"><FBType Name=\"*\" /></Request>"; //$NON-NLS-1$
	private static final String QUERY_ADAPTER_TYPES = "<Request ID=\"{0}\" Action=\"QUERY\"><AdapterType Name=\"*\" /></Request>"; //$NON-NLS-1$
	private static final String QUERY_FB_INSTANCES = "<Request ID=\"{0}\" Action=\"QUERY\"><FB Name=\"*\" Type=\"*\"/></Request>"; //$NON-NLS-1$
	private static final String QUERY_CONNECTIONS = "<Request ID=\"{0}\" Action=\"QUERY\"><Connection Source=\"{1}\" Destination=\"{2}\"/></Request>"; //$NON-NLS-1$


	private static final Logger logger = Logger.getLogger(DynamicTypeLoadDeploymentExecutor.class);
	private ResponseMapping respMapping = new ResponseMapping();
	
	public DynamicTypeLoadDeploymentExecutor(Device dev, IDeviceManagementCommunicationHandler overrideHandler) {
		super(dev, overrideHandler);
		// nothing to do here
	}

	@Override
	public void createFBInstance(final FBDeploymentData fbData, final Resource res) throws DeploymentException {
		// check first if FBType exists
		Map<String, AdapterType> adapters = getAdapterTypes(fbData.fb.getType().getInterfaceList());
		if (!adapters.isEmpty()) {
			queryAdapterTypes(adapters, res);
		}
		queryFBTypes(fbData.fb, res);
		super.createFBInstance(fbData, res);
	}

	private static Map<String, AdapterType> getAdapterTypes(InterfaceList interfaceList) {
		Map<String, AdapterType> list = new HashMap<>();
		interfaceList.getPlugs().forEach(e -> list.put(e.getTypeName(), (AdapterType) EcoreUtil.copy(e.getType())));
		interfaceList.getSockets().forEach(e -> list.put(e.getTypeName(), (AdapterType) EcoreUtil.copy(e.getType())));
		return list;
	}

	public void createFBType(final FBType fbType, final Resource res) throws DeploymentException {
		if(null != getTypes()) {
			setAttribute(res.getDevice(), "FBType", getTypes()); //$NON-NLS-1$
		}
		if((fbType instanceof BasicFBType || fbType instanceof CompositeFBType) 
				&& ( (null != getTypes() && !getTypes().contains(fbType.getName())) 
						|| (null == getTypes() && !isAttribute(res.getDevice(), fbType.getName(), "FBType"))) ){ //$NON-NLS-1$
			if(fbType instanceof CompositeFBType) {
				createFBTypesOfCFB(fbType, res);
			}
			String request = createLuaRequestMessage(fbType);
			sendCreateFBTypeREQ(fbType, request);
		}
	}

	private void sendCreateFBTypeREQ(final FBType fbType, String request) throws DeploymentException {
		try {				
			String result = getDevMgmComHandler().sendREQ("", request); //$NON-NLS-1$
			if (result.contains("Reason")) { //$NON-NLS-1$
				throw new DeploymentException("LUA skript for " + fbType.getName() + " FBType not executed");
			} else {
				getTypes().add(fbType.getName());
			}
		} catch (IOException e) {
			logger.error(MessageFormat.format(Messages.DTL_CreateTypeFailed, "LUA script")); //$NON-NLS-1$
		}
	}

	private void createFBTypesOfCFB(final FBType fbType, final Resource res) throws DeploymentException {
		for(FBNetworkElement netelem : ((CompositeFBType) fbType).getFBNetwork().getNetworkElements()) {
			if(!getTypes().contains(netelem.getTypeName())) {
				Map<String, AdapterType> adapters = getAdapterTypes(netelem.getInterface());						
				if(!adapters.isEmpty()) {
					loopAdapterTypes(adapters, res);
				}
				createFBType((FBType) netelem.getType(), res);
			}
		}
	}

	private String createLuaRequestMessage(final FBType fbType) {
		ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();
		String luaSkript = luaFilter.createLUA(fbType);
		return MessageFormat.format(CREATE_FB_TYPE, id++, fbType.getName(), luaSkript);
	}

	private static boolean isAttribute(Device device, String fbTypeName, String attributeType) {
		if(null != device.getAttribute(attributeType)) {			
			for(String s : device.getAttributeValue(attributeType).split(",")) { //$NON-NLS-1$
				if(fbTypeName.equals(s.trim())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static void setAttribute(Device device, String string, Set<String> hashSet) {
		Display.getDefault().asyncExec(() -> 
			device.setAttribute(string, "STRING", String.join(", ", hashSet), "created during deployment") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		);
	}
	
	public void createAdapterType(final String adapterKey, Map<String, AdapterType> adapters, final Resource res) throws DeploymentException {
		if(null != getAdapterTypes()) {
			setAttribute(res.getDevice(), "AdapterType", getAdapterTypes());
		}
		if ( (null != getAdapterTypes()) && (!getAdapterTypes().contains(adapterKey) || 
				!isAttribute(res.getDevice(), adapterKey, "AdapterType")) ) {			
			ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();
			String luaSkript = luaFilter.createLUA(adapters.get(adapterKey));
			String request = MessageFormat.format(CREATE_ADAPTER_TYPE, id++, adapterKey, luaSkript);
			sendCreateAdapterTypeREQ(adapterKey, request);
		}
	}

	private void sendCreateAdapterTypeREQ(final String adapterKey, String request) throws DeploymentException {
		try {
			String result = getDevMgmComHandler().sendREQ("", request); //$NON-NLS-1$
			if (result.contains("Reason")) { //$NON-NLS-1$
				throw new DeploymentException("LUA skript for " + adapterKey + " AdapterType not executed");
			} else {
				getAdapterTypes().add(adapterKey);
			}
		} catch (IOException e) {
			logger.error(MessageFormat.format(Messages.DTL_CreateTypeFailed, "Adapter")); //$NON-NLS-1$
		}
	}
	
	public void queryResources(Device dev) {
		String request = MessageFormat.format(QUERY_FB_INSTANCES, id++);
		try {
			String result = getDevMgmComHandler().sendREQ("", request);		
			if (result != null) {
				InputSource source = new InputSource(new StringReader(result));
				XMLResource xmlResource = new XMLResourceImpl();
				xmlResource.load(source, respMapping.getLoadOptions());
				for (EObject object : xmlResource.getContents()) {
					if(object instanceof Response) {
						for(org.eclipse.fordiac.ide.deployment.devResponse.FB resource : ((Response)object).getFblist().getFbs()) {								
							Resource res = LibraryElementFactory.eINSTANCE.createResource();
							res.setName(resource.getName());
							res.setPaletteEntry(getResourceType(dev, resource.getType()));
							res.setFBNetwork(LibraryElementFactory.eINSTANCE.createFBNetwork());
							dev.getResource().add(res);
							queryFBNetwork(res);
							queryConnections(res);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DTL_QueryFailed, "Resources")); //$NON-NLS-1$
		}
	}
	
	private void queryConnections(Resource res) {
		String request = MessageFormat.format(QUERY_CONNECTIONS, id++, "*", "*");
		try {
			String result = getDevMgmComHandler().sendREQ(res.getName(), request);		
			if (result != null) {
				InputSource source = new InputSource(new StringReader(result));
				XMLResource xmlResource = new XMLResourceImpl();
				xmlResource.load(source, respMapping.getLoadOptions());
				createConnections(res, xmlResource);
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DTL_QueryFailed, "Connections")); //$NON-NLS-1$
		}
	}

	private void createConnections(Resource res, XMLResource xmlResource) {
		for (EObject object : xmlResource.getContents()) {
			if(object instanceof Response) {
				for(org.eclipse.fordiac.ide.deployment.devResponse.Connection connection : ((Response)object).getEndpointlist().getConnection()) {
					//TODO currently no subapps supported
					String[] src = connection.getSource().split("\\.");
					FB srcFB = Annotations.getFBNamed(res.getFBNetwork(), src[0]);
					IInterfaceElement srcIE = Annotations.getInterfaceElement(srcFB, src[src.length - 1]);
					String[] dst = connection.getDestination().split("\\.");
					FB dstFB = Annotations.getFBNamed(res.getFBNetwork(), dst[0]);
					IInterfaceElement dstIE = Annotations.getInterfaceElement(dstFB, dst[dst.length - 1]);
					createConnectionCommand(res.getFBNetwork(), srcIE, dstIE);
				}
			}
		}
	}

	private void createConnectionCommand(FBNetwork fbNet, IInterfaceElement srcIE, IInterfaceElement dstIE) {
		AbstractConnectionCreateCommand cmd = null;
		if(srcIE instanceof Event) {
			cmd = new EventConnectionCreateCommand(fbNet);
		}else if(srcIE instanceof AdapterDeclaration){
			cmd = new AdapterConnectionCreateCommand(fbNet);
		}else if(srcIE instanceof VarDeclaration) {
			cmd = new DataConnectionCreateCommand(fbNet);
		}
		if(null != cmd) {			
			cmd.setSource(srcIE);
			cmd.setDestination(dstIE);
			if(cmd.canExecute()) {
				cmd.execute();
			}
		}
	}

	private void queryFBNetwork(Resource res) {
		String request = MessageFormat.format(QUERY_FB_INSTANCES, id++);
		try {
			String result = getDevMgmComHandler().sendREQ(res.getName(), request);		
			if (result != null) {
				InputSource source = new InputSource(new StringReader(result));
				XMLResource xmlResource = new XMLResourceImpl();
				xmlResource.load(source, respMapping.getLoadOptions());
				createFBNetwork(res, xmlResource);
			}
		} catch (Exception e) {
			logger.error(MessageFormat.format(Messages.DTL_QueryFailed, "Networks")); //$NON-NLS-1$
		}
	}

	private void createFBNetwork(Resource res, XMLResource xmlResource) {
		for (EObject object : xmlResource.getContents()) {
			if(object instanceof Response) {
				int i = 0;
				for(org.eclipse.fordiac.ide.deployment.devResponse.FB fbresult : ((Response)object).getFblist().getFbs()) {								
					//this version assumes that all fb types are available within the ide
					FBTypePaletteEntry entry = (FBTypePaletteEntry) res.getDevice().getAutomationSystem().getPalette().getTypeEntry(fbresult.getType());
					FBCreateCommand fbcmd = new FBCreateCommand(entry, res.getFBNetwork(), 100 * i, 10);
					if(fbcmd.canExecute()) {
						fbcmd.execute();
						fbcmd.getFB().setName(fbresult.getName());
					}
					i++;
				}
			}
		}
	}

	private ResourceTypeEntry getResourceType(Device device, String resTypeName) {
		List<PaletteEntry> typeEntries = device.getPaletteEntry().getGroup().getPallete().getTypeEntries(resTypeName);
		if(!typeEntries.isEmpty() && typeEntries.get(0) instanceof ResourceTypeEntry) {
			return (ResourceTypeEntry)typeEntries.get(0);
		}
		return null;
	}
	
	private void queryFBTypes(FB fb, Resource res) {
		if (null == getTypes()) {
			String request = MessageFormat.format(QUERY_FB_TYPES, id++);
			try {
				QueryResponseHandler queryResp = sendQUERY(res.getName(), request);
				setTypes(queryResp.getQueryResult());
			} catch (Exception e) {
				logger.error(MessageFormat.format(Messages.DTL_QueryFailed, "FB Types")); //$NON-NLS-1$
			}
		}
		FBType fbType = fb.getType();
		try {
			createFBType(fbType, res);
		} catch (DeploymentException ce) {
			logger.error(MessageFormat.format(Messages.DTL_CreateTypeFailed, fbType.getName()));
		}
	}

	private void queryAdapterTypes(Map<String, AdapterType> adapters, Resource res) {
		if (null == getAdapterTypes()) {
			String request = MessageFormat.format(QUERY_ADAPTER_TYPES, id++);
			try {
				QueryResponseHandler queryResp = sendQUERY(res.getName(), request);
				setAdapterTypes(queryResp.getQueryResult());
			} catch (Exception e1) {
				logger.error(MessageFormat.format(Messages.DTL_QueryFailed, "Adapter Types")); //$NON-NLS-1$
			}
		}
		loopAdapterTypes(adapters, res);
	}

	private void loopAdapterTypes(Map<String, AdapterType> adapters, Resource res) {
		adapters.keySet().forEach(e -> {
			try {
				createAdapterType(e, adapters, res);
			} catch (DeploymentException ce) {
				logger.error(MessageFormat.format(Messages.DTL_CreateTypeFailed, e));
			}
		});
	}
}
