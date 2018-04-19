/*******************************************************************************
 * Copyright (c) 2013 - 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Alois Zoitl, Florian Noack, Monika Wenger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.HashSet;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.fordiac.ide.deployment.AbstractDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.Activator;
import org.eclipse.fordiac.ide.deployment.exceptions.DisconnectException;
import org.eclipse.fordiac.ide.deployment.exceptions.InvalidMgmtID;
import org.eclipse.fordiac.ide.deployment.iec61499.preferences.HoloblocDeploymentPreferences;
import org.xml.sax.InputSource;

public class EthernetDeviceManagementCommunicationHandler extends AbstractDeviceManagementCommunicationHandler {
	private static final int ASN1_TAG_IECSTRING = 80;
	private MgrInformation mgrInfo;
	private Socket socket;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;

	private class MgrInformation {
		public String iP;
		public Integer port;

		@Override
		public String toString() {
			return iP + ":" + port; //$NON-NLS-1$
		}
	}

	@Override
	public void connect(String address) throws InvalidMgmtID, UnknownHostException, IOException {
		mgrInfo = getValidMgrInformation(address);
		socket = new Socket();
		int timeout = HoloblocDeploymentPreferences.getConnectionTimeout();
		SocketAddress sockaddr = new InetSocketAddress(mgrInfo.iP, mgrInfo.port);		
		socket.connect(sockaddr, timeout);
		socket.setSoTimeout(timeout);
											// 3s as timeout
		outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}

	@Override
	public void disconnect() throws DisconnectException {
		try {
			outputStream.close();
			inputStream.close();
			socket.close();
			// TODO check this sleep!
			Thread.sleep(50);
		} catch (IOException e) {
			throw new DisconnectException(
					MessageFormat.format(Messages.DeploymentExecutor_DisconnectFailed, new Object[] {}));
		} catch (InterruptedException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
	}

	@Override
	public void sendREQ(String destination, String request) throws IOException {
		if (outputStream != null && inputStream != null) {
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(destination.length());
			outputStream.writeBytes(destination);
			// out.flush();
			// Do NOT flush here, all data should be sent within 1 ethernet
			// frame
			// in case packet fragmentation is not properly handled by server
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(request.length());
			outputStream.writeBytes(request);
			outputStream.flush();
			postCommandSent(getInfo(destination), destination, request);
			handleResponse(destination);
			// TODO error handling
		}
	}

	private String handleResponse(String destination) throws IOException {
		@SuppressWarnings("unused")
		byte b = inputStream.readByte();
		short size = inputStream.readShort();
		StringBuilder response = new StringBuilder(size); 
		for (int i = 0; i < size; i++) {
			response.append((char) inputStream.readByte());
		}
		String retVal = response.toString();
		if (0 != response.length()) { 
			responseReceived(retVal, getInfo(destination));
		}
		return retVal;
	}
	
	private String getInfo(String destination){
		String info = mgrInfo.toString();
		if (!destination.equals("")) { //$NON-NLS-1$
			info += ": " + destination; //$NON-NLS-1$
		}
		return info;
	}

	public String sendREQandRESP(String destination, String request) throws IOException {
		String response = ""; //$NON-NLS-1$
		if (outputStream != null && inputStream != null) {
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(destination.length());
			outputStream.writeBytes(destination);
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(request.length());
			outputStream.writeBytes(request);
			outputStream.flush();
			postCommandSent(getInfo(destination), destination, request);
			response = handleResponse(destination);
		}
		return response;
	}

	/**
	 * returns a valid MgrInformation if the mgrID contains valid destination
	 * string (e.g. localhost:61499) else null is returned valid ports are
	 * between 1024 - 65535
	 * 
	 * @param mgrID
	 *            the mgr id
	 * 
	 * @return the valid mgr information
	 * @throws InvalidMgmtID
	 *             when the given ide is no valid ip address port compbination
	 */
	private MgrInformation getValidMgrInformation(final String mgrID) throws InvalidMgmtID {
		if (null != mgrID) {
			String id = mgrID;
			if (id.startsWith("\"")) { //$NON-NLS-1$
				id = id.substring(1, id.length());
			}
			if (id.endsWith("\"")) { //$NON-NLS-1$
				id = id.substring(0, id.length() - 1);
			}
			String[] splitID = id.split(":"); //$NON-NLS-1$
			Integer port;
			MgrInformation mgrInfo = new MgrInformation();
			if (splitID.length == 2) {
				try {
					InetAddress adress = InetAddress.getByName(splitID[0]);
					mgrInfo.iP = adress.getHostAddress();
					port = Integer.parseInt(splitID[1]);
				} catch (NumberFormatException | UnknownHostException e) {
					throw new InvalidMgmtID(mgrID);
				}
				if (1023 < port && port < 65536) {
					mgrInfo.port = port;
					return mgrInfo;
				}
			}
		}
		throw new InvalidMgmtID(mgrID);
	}

	public void sendQUERY(final String destination, final String request) throws IOException {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			QueryResponseHandler handler = new QueryResponseHandler();
			String response = sendREQandRESP(destination, request);
			saxParser.parse(new InputSource(new StringReader(response)), handler);
//			postCommandSent(info, destination, request);
			if(request.contains("FBType")){ //$NON-NLS-1$
				getFBTypes(getInfo(destination), handler);
			}else if(request.contains("AdapterType")){ //$NON-NLS-1$
				getAdapterTypes(getInfo(destination), handler);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO maybe an error message would be good
	}

	private void getFBTypes(String info, QueryResponseHandler handler) {
		if(null == fbTypes){
			fbTypes = new HashSet<String>();
		}
		fbTypes = handler.getQueryResult();
//		responseReceived("<Response FBTypes=\"" + fbTypes.toString() + "\"/>", info);
	}
	
	
	private void getAdapterTypes(String info, QueryResponseHandler handler) {
		if(null == adapterTypes){
			adapterTypes = new HashSet<String>();
		}
		adapterTypes = handler.getQueryResult();
//		responseReceived("<Response AdapterTypes=\"" + adapterTypes.toString() + "\"/>", info);
	}
}
