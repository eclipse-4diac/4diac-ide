/*******************************************************************************
 * Copyright (c) 2011 - 2017 TU Wien ACIN, Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Alois Zoitl, Gerhard Ebenhofer, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.automatedRemoteTest;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.deployment.exceptions.CreateResourceInstanceException;
import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.iec61499.DeploymentExecutor;
import org.eclipse.fordiac.ide.deployment.iec61499.EthernetDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.iec61499.Messages;
import org.eclipse.fordiac.ide.deployment.util.IDeploymentListener;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.util.FBTHelper;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class ART_DeploymentMgr {

	private Resource res;
	private DeploymentExecutor executor;
	private IDeploymentListener listener;
	private Device dev;

	private FBType fbType;

	private int REQID=0;
	private String address;

	private int UID=-2;

	private boolean deploymentError;
	private int deploymentResponseCounter=0;
	public String MgmtResponse=""; //$NON-NLS-1$
	public String MgmtCommands=""; //$NON-NLS-1$

	public ART_DeploymentMgr(FBType fbType, String address, int paUID) {

		deploymentError=false;
		MgmtResponse=""; //$NON-NLS-1$
		MgmtCommands=""; //$NON-NLS-1$
		UID=paUID;
		this.fbType=fbType;
		if (null!= address) {
			this.address=address;
		} else {
			this.address = "localhost:61499"; //$NON-NLS-1$
		}

		LibraryElementFactory libFactory = org.eclipse.fordiac.ide.model.libraryElement.impl.LibraryElementFactoryImpl.init();
		DeviceType devType = libFactory.createDeviceType();
		devType.setName("RMT_DEV"); //$NON-NLS-1$

		dev = libFactory.createDevice();

		dev.setPaletteEntry(null);
		dev.setProfile("HOLOBLOC"); //$NON-NLS-1$
		dev.setName("TestDevice"); //$NON-NLS-1$


		res = libFactory.createResource();
		res.setDevice(dev);
		
		List<PaletteEntry> entries = TypeLibrary.getInstance().getPalette().getTypeEntries("EMB_RES"); //$NON-NLS-1$
		if (entries.size() > 0) {
			PaletteEntry entry = entries.get(0);
			res.setPaletteEntry(entry);
		} 
				
		res.setName("__"+fbType.getName()+"Test"+UID);  //$NON-NLS-1$//$NON-NLS-2$

		executor = new DeploymentExecutor();
		
		listener = new IDeploymentListener() {
			
			@Override
			public void responseReceived(String response, String source) {
				if (response.toLowerCase().indexOf("reason")>-1) { //$NON-NLS-1$
					deploymentError=true;
					MgmtCommands+=(response+"\n\n"); //$NON-NLS-1$
					MgmtResponse+=response;
				}
				deploymentResponseCounter--;
				
			}
			
			@Override
			public void postCommandSent(String info, String destination, String command) {
			}
			
			@Override
			public void postCommandSent(String message) {
			}
			
			@Override
			public void postCommandSent(String command, String destination) {
				MgmtCommands+=(destination+command+"\n"); //$NON-NLS-1$
			}
			
			@Override
			public void finished() {
			}
		};
		
		executor.getDevMgmComHandler().addDeploymentListener(listener);
	}

	
	public boolean cleanRes() {
		try {
			executor.getDevMgmComHandler().connect(address);
			deploymentResponseCounter++; //Kill Res
			deploymentResponseCounter++; //Delete Res
			executor.deleteResource(res);
			executor.getDevMgmComHandler().disconnect();
			
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;
		}

		return true;
		
	}
	
	public boolean deploy(String TestChannelID) {
		MgmtResponse=""; //$NON-NLS-1$
		MgmtCommands=""; //$NON-NLS-1$
		boolean Error=false;
		int numEI=FBTHelper.getEISize(fbType);
		int numEO=FBTHelper.getEOSize(fbType);

		int numDI=FBTHelper.getDISize(fbType);
		int numDO=FBTHelper.getDOSize(fbType);

		try {
			executor.getDevMgmComHandler().connect(address);
		} catch (Exception e1) {
			MgmtCommands="Error during connection to device: "+address+"\n"; //$NON-NLS-2$
			Error = true;
		} 

		if (Error)
		{
			return false;
		}

		try {
			deploymentResponseCounter++;
			executor.createResource(res);
		} catch (CreateResourceInstanceException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			Error = true;
		} 

		while ((!Error)&&(!deploymentError)&& deploymentResponseCounter>0) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		
		if (deploymentError || Error) {
//no clean-up required; creation of resource failed!
			
			return false;
			
		}
		
		String destination = res.getName();
		
		try {
			sendREQ(res.getName(), createFB_Request("FBuT",fbType.getName())); //$NON-NLS-1$
			sendREQ(res.getName(), createFB_Request("Server","SERVER_"+(numDO+1)+"_"+(numDI+1)) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			sendREQ(res.getName(), createFB_Request("MuX","E_MUX_"+numEO)); //$NON-NLS-1$ //$NON-NLS-2$
			sendREQ(res.getName(), createFB_Request("DeMuX","E_DEMUX_"+numEI)); //$NON-NLS-1$ //$NON-NLS-2$

			//EventConns
			sendREQ(res.getName(), createConnection_Request("Server", "IND", "DeMuX", "EI")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			sendREQ(res.getName(), createConnection_Request("MuX", "EO", "Server", "RSP")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			sendREQ(res.getName(), createConnection_Request("START", "COLD", "Server", "INIT")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			if (null != fbType.getInterfaceList()) {
				if (null!= fbType.getInterfaceList().getEventInputs()) {
					for (Iterator<Event> iterator=fbType.getInterfaceList().getEventInputs().iterator(); iterator.hasNext();) {
						Event EI = iterator.next();
						sendREQ(res.getName(), createConnection_Request("DeMuX", "EO"+(FBTHelper.getEIID(fbType, EI.getName())+1), "FBuT", EI.getName())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
				}
				if (null!= fbType.getInterfaceList().getEventOutputs()) {
					for (Iterator<Event> iterator=fbType.getInterfaceList().getEventOutputs().iterator(); iterator.hasNext();) {
						Event EO = iterator.next();
						sendREQ(res.getName(), createConnection_Request("FBuT", EO.getName(), "MuX", "EI"+(FBTHelper.getEOID(fbType, EO.getName())+1))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
				}

			}

			sendREQ(destination, writeFBParameter_Request("Server.ID", TestChannelID)); //$NON-NLS-1$
			sendREQ(destination, writeFBParameter_Request("Server.QI", "1")); //$NON-NLS-1$ //$NON-NLS-2$

			//DataConns
			sendREQ(res.getName(), createConnection_Request("Server", "RD_1", "DeMuX", "K")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			sendREQ(res.getName(), createConnection_Request("MuX", "K", "Server", "SD_1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			//Server to FBuT
			if (null!= fbType.getInterfaceList().getInputVars()) {
				for (Iterator<VarDeclaration> iterator=fbType.getInterfaceList().getInputVars().iterator(); iterator.hasNext();) {
					VarDeclaration DI = iterator.next();
					sendREQ(res.getName(), createConnection_Request("Server", "RD_"+(2+FBTHelper.getDIID(fbType, DI.getName())), "FBuT", DI.getName())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
			}
			
			
			//FBuT to Server
			if (null!= fbType.getInterfaceList().getOutputVars()) {
				for (Iterator<VarDeclaration> iterator=fbType.getInterfaceList().getOutputVars().iterator(); iterator.hasNext();) {
					VarDeclaration DO = iterator.next();
					sendREQ(res.getName(), createConnection_Request( "FBuT", DO.getName(), "Server", "SD_"+(2+FBTHelper.getDOID(fbType, DO.getName())))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
			}
			
			deploymentResponseCounter++;
			executor.startResource(res);
			
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			Error = true;
		}

		
		try {
			executor.getDevMgmComHandler().disconnect();
		} catch (DisconnectException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		if (deploymentError) {
			cleanRes();
			return false;
		}
		
		return (!Error);
	}


	private String createFB_Request(String fbName, String fbTypeName) {
		return MessageFormat.format(
				Messages.DeploymentExecutor_CreateFBInstance, new Object[] { this.REQID++,
						fbName, fbTypeName });
	}

	private String createConnection_Request(String SrcFBName, String SrcIfElemName, String DstFBName, String DstIfElemName) {
		return MessageFormat.format(
				Messages.DeploymentExecutor_CreateConnection, new Object[] {
						this.REQID++, SrcFBName + "." //$NON-NLS-1$
						+ SrcIfElemName,
						DstFBName + "." //$NON-NLS-1$
						+ DstIfElemName });
	}

	
	private String writeFBParameter_Request(final String Dst, final String value) {
		return MessageFormat.format(
				Messages.DeploymentExecutor_WriteParameter, new Object[] { this.REQID++,
						value, Dst });

	}

	private void sendREQ(final String destination, final String request) throws IOException {
		deploymentResponseCounter++;
		executor.getDevMgmComHandler().sendREQ(destination, request);
	}

}
