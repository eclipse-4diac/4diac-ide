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
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.export.forte_lua.ForteLuaExportFilter;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.swt.widgets.Display;

public class DynamicTypeLoadDeploymentExecutor extends DeploymentExecutor {

	public DynamicTypeLoadDeploymentExecutor(Device dev, AbstractDeviceManagementCommunicationHandler overrideHandler) {
		super(dev, overrideHandler);
		// nothing todo here
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
		interfaceList.getPlugs().forEach((e) -> list.put(e.getTypeName(), (AdapterType) EcoreUtil.copy(e.getType())));
		interfaceList.getSockets().forEach((e) -> list.put(e.getTypeName(), (AdapterType) EcoreUtil.copy(e.getType())));
		return list;
	}

	public void createFBType(final FBType fbType, final Resource res) throws DeploymentException {
		if(null != getTypes()) {
			setAttribute(res.getDevice(), "FBType", getTypes()); //$NON-NLS-1$
		}
		if ((fbType instanceof BasicFBType || fbType instanceof CompositeFBType)
				&& ( (null != getTypes() && !getTypes().contains(fbType.getName())) 
						|| (null == getTypes() && !isAttribute(res.getDevice(), fbType.getName(), "FBType")))) { //$NON-NLS-1$
			if(fbType instanceof CompositeFBType) {
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
			ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();
			String luaSkript = luaFilter.createLUA(fbType);
			String request = MessageFormat.format(Messages.DTL_CreateFBType,
					new Object[] { id++, fbType.getName(), luaSkript });
			try {				
				String result = getDevMgmComHandler().sendREQ("", request); //$NON-NLS-1$
				if (result.contains("Reason")) { //$NON-NLS-1$
					throw new DeploymentException("LUA skript for " + fbType.getName() + " FBType not executed");
				} else {
					getTypes().add(fbType.getName());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				device.setAttribute(string, "STRING", String.join(", ", hashSet), "created during deployment"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		});
	}
	
	public void createAdapterType(final String adapterKey, Map<String, AdapterType> adapters, final Resource res) throws DeploymentException {
		if(null != getAdapterTypes()) {
			setAttribute(res.getDevice(), "AdapterType", getAdapterTypes());
		}
		if ( (null != getAdapterTypes()) && (!getAdapterTypes().contains(adapterKey) || 
				!isAttribute(res.getDevice(), adapterKey, "AdapterType")) ) {			
			ForteLuaExportFilter luaFilter = new ForteLuaExportFilter();
			String luaSkript = luaFilter.createLUA(adapters.get(adapterKey));
			String request = MessageFormat.format(Messages.DTL_CreateAdapterType,
					new Object[] { id++, adapterKey, luaSkript });
			try {
				String result = getDevMgmComHandler().sendREQ("", request); //$NON-NLS-1$
				if (result.contains("Reason")) { //$NON-NLS-1$
					throw new DeploymentException("LUA skript for " + adapterKey + " AdapterType not executed");
				} else {
					getAdapterTypes().add(adapterKey);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void queryFBTypes(FB fb, Resource res) {
		if (null == getTypes()) {
			String request = MessageFormat.format(Messages.DTL_QueryFBTypes, new Object[] { id++ });
			try {
				QueryResponseHandler queryResp = sendQUERY(res.getName(), request);
				setTypes(queryResp.getQueryResult());
			} catch (Exception e) {
				System.out.println(MessageFormat.format(Messages.DTL_QueryFailed, new Object[] { "FB Types" })); //$NON-NLS-1$
			}
		}
		FBType fbType = fb.getType();
		try {
			createFBType(fbType, res);
		} catch (DeploymentException ce) {
			System.out.println(MessageFormat.format(Messages.DTL_CreateTypeFailed, new Object[] { fbType.getName() }));
		}
	}

	private void queryAdapterTypes(Map<String, AdapterType> adapters, Resource res) {
		if (null == getAdapterTypes()) {
			String request = MessageFormat.format(Messages.DTL_QueryAdapterTypes, new Object[] { id++ });
			try {
				QueryResponseHandler queryResp = sendQUERY(res.getName(), request);
				setAdapterTypes(queryResp.getQueryResult());
			} catch (Exception e1) {
				System.out.println(MessageFormat.format(Messages.DTL_QueryFailed, new Object[] { "Adapter Types" })); //$NON-NLS-1$
			}
		}
		loopAdapterTypes(adapters, res);
	}

	private void loopAdapterTypes(Map<String, AdapterType> adapters, Resource res) {
		adapters.keySet().forEach((e) -> {
			try {
				createAdapterType(e, adapters, res);
			} catch (DeploymentException ce) {
				System.out.println(MessageFormat.format(Messages.DTL_CreateTypeFailed, new Object[] { e }));
			}
		});
	}
	

}
