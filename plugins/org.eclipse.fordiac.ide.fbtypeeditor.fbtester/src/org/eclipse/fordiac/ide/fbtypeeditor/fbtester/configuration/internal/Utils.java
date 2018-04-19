/*******************************************************************************
 * Copyright (c) 2012 - 2014 Profactor GmbH, fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *    - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtester.configuration.internal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.MessageFormat;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Activator;
import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;


/**
 * The Class Utils.
 */
/**
 * @author kerbled
 * 
 */
public final class Utils {

	/** The Constant ASN1_TAG_IECSTRING. */
	private static final int ASN1_TAG_IECSTRING = 80;
	
	private Utils() {
		//private constructor to make class non instantiable
	}

	/**
	 * Deploy the network required for testing a function block.
	 * 
	 * @param type
	 *            the type
	 * @param monitoringPort
	 *            the monitoring port
	 * @param runtimePort
	 *            the runtime port
	 * @param ipAddress
	 * 
	 * @return the string
	 */
	public static String deployNetwork(FBType type, String ipAddress,
			int port) {
		int id = 0;
		try {

			Socket socket = new Socket(InetAddress.getByName(ipAddress),
					port);
			DataOutputStream outputStream = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			DataInputStream inputStream = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			socket.setSoTimeout(10000);

			// create monitoring resource

			// create test resource
			String request = MessageFormat.format(
					Messages.FBTester_CreateResourceInstance, new Object[] {
							id++, "_" + type.getName() + "_RES", "EMB_RES" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			sendREQ("", request, outputStream, inputStream); //$NON-NLS-1$

			request = MessageFormat
					.format(Messages.FBTester_CreateFBInstance, new Object[] {
							id++, "_" + type.getName(), type.getName() }); //$NON-NLS-1$
			sendREQ("_" + type.getName() + "_RES", request, outputStream, //$NON-NLS-1$ //$NON-NLS-2$
					inputStream);

			// start test resource
			request = MessageFormat.format(Messages.FBTester_Start,
					new Object[] { id++ });
			sendREQ("_" + type.getName() + "_RES", request, outputStream, //$NON-NLS-1$ //$NON-NLS-2$
					inputStream);

			socket.close();
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	/**
	 * Clean the network required for testing a function block.
	 * 
	 * @param type
	 *            the type
	 * @param monitoringPort
	 *            the monitoring port
	 * @param runtimePort
	 *            the runtime port
	 * @param ipAddress
	 * 
	 * @return the string
	 */
	public static String cleanNetwork(FBType type, String ipAddress,
			int port, Socket socket) {
		int id = 0;
		try {

			if (null == socket) {
				socket = new Socket(InetAddress.getByName(ipAddress),
						port);
			}
			if (!socket.isConnected()) {
				SocketAddress endpoint = new InetSocketAddress(InetAddress.getByName(ipAddress), port);
				socket.connect(endpoint);
			}			
			DataOutputStream outputStream = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			DataInputStream inputStream = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			
			socket.setSoTimeout(10000);

			String kill = MessageFormat.format(Messages.FBTester_KillFB,
					new Object[] { id++, "_" + type.getName() + "_RES" }); //$NON-NLS-1$ //$NON-NLS-2$
			String delete = MessageFormat.format(Messages.FBTester_DeleteFB,
					new Object[] { id++, "_" + type.getName() + "_RES" }); //$NON-NLS-1$ //$NON-NLS-2$

			sendREQ("", kill, outputStream, inputStream); //$NON-NLS-1$

			sendREQ("", delete, outputStream, inputStream); //$NON-NLS-1$
			
			
			socket.close();
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	/**
	 * Send a management commmand to the rutime.
	 * 
	 * @param destination
	 *            the destination
	 * @param request
	 *            the request
	 * @param outputStream
	 *            the output stream
	 * @param inputStream
	 *            the input stream
	 * 
	 * @return the string
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static synchronized String sendREQ(final String destination,
			final String request, DataOutputStream outputStream,
			DataInputStream inputStream) throws Exception {

		String output = ""; //$NON-NLS-1$
		if (outputStream != null && inputStream != null) {
			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(destination.length());
			outputStream.writeBytes(destination);

			// Do NOT flush here, all data should be sent within 1 ethernet frame
			// in case packet fragmentation is not properly handled by server

			outputStream.writeByte(ASN1_TAG_IECSTRING);
			outputStream.writeShort(request.length());
			outputStream.writeBytes(request);
			outputStream.flush();

			System.out.println(request);
			StringBuilder response = new StringBuilder();
			@SuppressWarnings("unused")
			byte b = inputStream.readByte();
			
			short size = inputStream.readShort();

			for (int i = 0; i < size; i++) {
				response.append((char) inputStream.readByte());
			}
			System.out.println(response);

			if (response.toString().contains("Reason")) { //$NON-NLS-1$
				throw new Exception(response.toString());
			}
			output = response.toString();
		}
		return output;
	}

}
