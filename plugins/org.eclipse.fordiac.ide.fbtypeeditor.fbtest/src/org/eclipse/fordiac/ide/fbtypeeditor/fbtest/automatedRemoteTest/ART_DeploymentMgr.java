/*******************************************************************************
 * Copyright (c) 2011 - 2018 TU Wien ACIN, Profactor GmbH, fortiss GmbH,
 * 							 Johannes Kepler University
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

import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.DeploymentExecutor;
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
	private Device dev;

	private FBType fbType;

	private int reqID = 0;
	private String address;

	private int uID =- 2;

	private boolean deploymentError;
	private int deploymentResponseCounter=0;
	public String MgmtResponse=""; //$NON-NLS-1$
	public String MgmtCommands=""; //$NON-NLS-1$

	public ART_DeploymentMgr(FBType fbType, String address, int paUID) {

		deploymentError=false;
		MgmtResponse=""; //$NON-NLS-1$
		MgmtCommands=""; //$NON-NLS-1$
		uID=paUID;
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
		if (!entries.isEmpty()) {
			PaletteEntry entry = entries.get(0);
			res.setPaletteEntry(entry);
		} 
				
		res.setName("__"+fbType.getName()+"Test"+uID);  //$NON-NLS-1$//$NON-NLS-2$

		executor = new DeploymentExecutor(dev);
		
		IDeploymentListener listener = new IDeploymentListener() {
			
			@Override
			public void connectionOpened() {
				// do nothing
			}
			
			@Override
			public void postCommandSent(String info, String destination, String command) {
				MgmtCommands+=(destination+command+"\n"); //$NON-NLS-1$
			}
			
			@Override
			public void postResponseReceived(String response, String source) {
				if (response.toLowerCase().indexOf("reason")>-1) { //$NON-NLS-1$
					deploymentError=true;
					MgmtCommands+=(response+"\n\n"); //$NON-NLS-1$
					MgmtResponse+=response;
				}
				deploymentResponseCounter--;
				
			}
			
			@Override
			public void connectionClosed() {
				//do nothing
			}
		};
		
		executor.addDeploymentListener(listener);
	}

	
	public boolean cleanRes() {
		try {
			executor.connect();
			deploymentResponseCounter++; //Kill Res
			deploymentResponseCounter++; //Delete Res
			executor.deleteResource(res);
			executor.disconnect();
			
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;
		}

		return true;
		
	}
	
	public boolean deploy(String TestChannelID) {
		MgmtResponse=""; //$NON-NLS-1$
		MgmtCommands=""; //$NON-NLS-1$
		boolean errorOcurred = false;
		int numEI=FBTHelper.getEISize(fbType);
		int numEO=FBTHelper.getEOSize(fbType);

		int numDI=FBTHelper.getDISize(fbType);
		int numDO=FBTHelper.getDOSize(fbType);

		try {
			executor.connect();
		} catch (Exception e1) {
			MgmtCommands="Error during connection to device: "+address+"\n"; //$NON-NLS-2$
			errorOcurred = true;
		} 

		if (errorOcurred)
		{
			return false;
		}

		try {
			deploymentResponseCounter++;
			executor.createResource(res);
		} catch (DeploymentException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			errorOcurred = true;
		} 

		while ((!errorOcurred)&&(!deploymentError)&& deploymentResponseCounter>0) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
		
		if (deploymentError || errorOcurred) {
//no clean-up required; creation of resource failed!
			
			return false;
			
		}
		
		String destination = res.getName();
		
		try {
			sendREQ(res.getName(), createFBRequest("FBuT",fbType.getName())); //$NON-NLS-1$
			sendREQ(res.getName(), createFBRequest("Server","SERVER_"+(numDO+1)+"_"+(numDI+1)) ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			sendREQ(res.getName(), createFBRequest("MuX","E_MUX_"+numEO)); //$NON-NLS-1$ //$NON-NLS-2$
			sendREQ(res.getName(), createFBRequest("DeMuX","E_DEMUX_"+numEI)); //$NON-NLS-1$ //$NON-NLS-2$

			//EventConns
			sendREQ(res.getName(), createConnectionRequest("Server", "IND", "DeMuX", "EI")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			sendREQ(res.getName(), createConnectionRequest("MuX", "EO", "Server", "RSP")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			sendREQ(res.getName(), createConnectionRequest("START", "COLD", "Server", "INIT")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			if (null != fbType.getInterfaceList()) {
				if (null!= fbType.getInterfaceList().getEventInputs()) {
					for (Iterator<Event> iterator=fbType.getInterfaceList().getEventInputs().iterator(); iterator.hasNext();) {
						Event eventInput = iterator.next();
						sendREQ(res.getName(), createConnectionRequest("DeMuX", "EO"+(FBTHelper.getEIID(fbType, eventInput.getName())+1), "FBuT", eventInput.getName())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
				}
				if (null!= fbType.getInterfaceList().getEventOutputs()) {
					for (Iterator<Event> iterator=fbType.getInterfaceList().getEventOutputs().iterator(); iterator.hasNext();) {
						Event eventOutput = iterator.next();
						sendREQ(res.getName(), createConnectionRequest("FBuT", eventOutput.getName(), "MuX", "EI"+(FBTHelper.getEOID(fbType, eventOutput.getName())+1))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					}
				}

			}

			sendREQ(destination, writeFBParameterRequest("Server.ID", TestChannelID)); //$NON-NLS-1$
			sendREQ(destination, writeFBParameterRequest("Server.QI", "1")); //$NON-NLS-1$ //$NON-NLS-2$

			//DataConns
			sendREQ(res.getName(), createConnectionRequest("Server", "RD_1", "DeMuX", "K")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			sendREQ(res.getName(), createConnectionRequest("MuX", "K", "Server", "SD_1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			//Server to FBuT
			if (null!= fbType.getInterfaceList().getInputVars()) {
				for (Iterator<VarDeclaration> iterator=fbType.getInterfaceList().getInputVars().iterator(); iterator.hasNext();) {
					VarDeclaration digitalInput = iterator.next();
					sendREQ(res.getName(), createConnectionRequest("Server", "RD_"+(2+FBTHelper.getDIID(fbType, digitalInput.getName())), "FBuT", digitalInput.getName())); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
			}
			
			
			//FBuT to Server
			if (null!= fbType.getInterfaceList().getOutputVars()) {
				for (Iterator<VarDeclaration> iterator=fbType.getInterfaceList().getOutputVars().iterator(); iterator.hasNext();) {
					VarDeclaration digitalOutput = iterator.next();
					sendREQ(res.getName(), createConnectionRequest( "FBuT", digitalOutput.getName(), "Server", "SD_"+(2+FBTHelper.getDOID(fbType, digitalOutput.getName())))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				}
			}
			
			deploymentResponseCounter++;
			executor.startResource(res);
			
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			errorOcurred = true;
		}

		
		try {
			executor.disconnect();
		} catch (DeploymentException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

		if (deploymentError) {
			cleanRes();
			return false;
		}
		
		return (!errorOcurred);
	}


	private String createFBRequest(String fbName, String fbTypeName) {
		return MessageFormat.format(DeploymentExecutor.CREATE_FB_INSTANCE, this.reqID++, fbName, fbTypeName);
	}

	private String createConnectionRequest(String srcFBName, String srcIfElemName, String dstFBName, String dstIfElemName) {
		return MessageFormat.format(DeploymentExecutor.CREATE_CONNECTION, this.reqID++, srcFBName + "." //$NON-NLS-1$
						+ srcIfElemName,
						dstFBName + "." //$NON-NLS-1$
						+ dstIfElemName );
	}

	
	private String writeFBParameterRequest(final String Dst, final String value) {
		return MessageFormat.format(DeploymentExecutor.WRITE_PARAMETER, this.reqID++, value, Dst);
	}

	private void sendREQ(final String destination, final String request) throws IOException {
		deploymentResponseCounter++;
		executor.sendREQ(destination, request);
	}

}
