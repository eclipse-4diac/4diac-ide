/*******************************************************************************
 * Copyright (c) 2013 - 2018 fortiss GmbH, Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Florian Noack, Monika Wenger - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.deployment.iec61499.handlers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import org.eclipse.fordiac.ide.deployment.IDeviceManagementCommunicationHandler;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.iec61499.Messages;
import org.eclipse.fordiac.ide.deployment.iec61499.preferences.IEC61499PreferenceConstants;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class EthernetDeviceManagementCommunicationHandler implements IDeviceManagementCommunicationHandler {
	private static final int ASN1_TAG_IECSTRING = 80;
	private MgrInformation mgrInfo;
	private Socket socket;
	private DataOutputStream outputStream;
	private DataInputStream inputStream;
	private static final int LOWER_INVALID_PORT = 1023;
	private static final int UPPER_INVALID_PORT = 65536;
	private static final long MS_SLEEP_IN_DISCONNECT = 50;

	private static class MgrInformation {
		private String iP;
		private int port;

		@Override
		public String toString() {
			return iP + ":" + port; //$NON-NLS-1$
		}
	}

	@Override
	public boolean isConnected() {
		return null != socket && socket.isConnected() && !socket.isClosed();
	}

	@Override
	public void connect(final String address) throws DeploymentException {
		mgrInfo = getValidMgrInformation(address);
		socket = new Socket();
		final int timeout = IEC61499PreferenceConstants.getConnectionTimeout();
		final SocketAddress sockaddr = new InetSocketAddress(mgrInfo.iP, mgrInfo.port);
		try {
			socket.connect(sockaddr, timeout); // 3s as timeout
			socket.setSoTimeout(timeout);
			outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (final IOException e) {
			throw new DeploymentException(Messages.EthernetDeviceManagementCommunicationHandler_CouldNotConnectToDevice,
					e);
		}
	}

	@Override
	public void disconnect() throws DeploymentException {
		try {
			outputStream.close();
			inputStream.close();
			socket.close();
			Thread.sleep(MS_SLEEP_IN_DISCONNECT); // TODO check this sleep!
		} catch (final IOException e) {
			throw new DeploymentException(Messages.DeploymentExecutor_DisconnectFailed, e);
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();  // mark interruption
			FordiacLogHelper.logError(e.getMessage(), e);
		}
	}

	private String handleResponse() throws IOException {
		inputStream.readByte();
		final int size = inputStream.readUnsignedShort();
		final StringBuilder response = new StringBuilder(size);
		for (int i = 0; i < size; i++) {
			response.append((char) inputStream.readByte());
		}
		return response.toString();
	}

	@Override
	public String getInfo(final String destination) {
		String info = mgrInfo.toString();
		if (!"".equals(destination)) { //$NON-NLS-1$
			info += ": " + destination; //$NON-NLS-1$
		}
		return info;
	}

	@Override
	public String sendREQ(final String destination, final String request) throws IOException {
		String response = ""; //$NON-NLS-1$
		if (outputStream != null && inputStream != null) {
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(destination.length());
			outputStream.writeBytes(destination);
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(request.length());
			outputStream.writeBytes(request);
			outputStream.flush();
			response = handleResponse();
		}
		return response;
	}

	/**
	 * returns a valid MgrInformation if the mgrID contains valid destination string
	 * (e.g. localhost:61499) else null is returned valid ports are between 1024 -
	 * 65535
	 *
	 * @param mgrID the mgr id
	 *
	 * @return the valid mgr information
	 * @throws InvalidMgmtID when the given ide is no valid ip address port
	 *                       compbination
	 */
	private static MgrInformation getValidMgrInformation(final String mgrID) throws DeploymentException {
		if (null != mgrID) {
			final String id = trimQuoutes(mgrID);
			final String[] splitID = id.split(":"); //$NON-NLS-1$
			int port;
			final MgrInformation mgrInfo = new MgrInformation();
			if (splitID.length == 2) {
				try {
					final InetAddress adress = InetAddress.getByName(splitID[0]);
					mgrInfo.iP = adress.getHostAddress();
					port = Integer.parseInt(splitID[1]);
				} catch (NumberFormatException | UnknownHostException e) {
					throw new DeploymentException(
							MessageFormat.format(Messages.EthernetComHandler_InvalidMgmtID, mgrID), e);
				}
				if (LOWER_INVALID_PORT < port && port < UPPER_INVALID_PORT) {
					mgrInfo.port = port;
					return mgrInfo;
				}
			}
		}
		throw new DeploymentException(MessageFormat.format(Messages.EthernetComHandler_InvalidMgmtID, mgrID));
	}

	private static String trimQuoutes(final String toTrim) {
		String id = toTrim;
		if (id.startsWith("\"")) { //$NON-NLS-1$
			id = id.substring(1, id.length());
		}
		if (id.endsWith("\"")) { //$NON-NLS-1$
			id = id.substring(0, id.length() - 1);
		}
		return id;
	}

}
