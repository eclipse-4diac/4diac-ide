/*******************************************************************************
 * Copyright (c) 2012 - 2014 Profactor GmbH, fortiss GmbH
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.text.MessageFormat;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtester.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public final class TestNetworkFactory {

	/** The Constant ASN1_TAG_IECSTRING. */
	private static final int ASN1_TAG_IECSTRING = 80;
	private static final int timeOut = 10000;

	/** Deploy the network required for testing a function block.
	 *
	 * @param type           the type
	 * @param monitoringPort the monitoring port
	 * @param runtimePort    the runtime port
	 * @param ipAddress
	 *
	 * @return the string */
	public static String deployNetwork(final FBType type, final String ipAddress, final int port) {
		int id = 0;
		try (Socket socket = new Socket(InetAddress.getByName(ipAddress), port)) {
			final DataOutputStream outputStream = new DataOutputStream(
					new BufferedOutputStream(socket.getOutputStream()));
			final DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			socket.setSoTimeout(timeOut);

			// create monitoring resource
			id = createTestResource(type, id, outputStream, inputStream);
			id = startTestResource(type, id, outputStream, inputStream);
		} catch (final IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	private static int startTestResource(final FBType type, int id, final DataOutputStream outputStream,
			final DataInputStream inputStream) throws IOException {
		final String request = MessageFormat.format(Messages.FBTester_Start, Integer.valueOf(id));
		sendREQ("_" + type.getName() + "_RES", request, outputStream, //$NON-NLS-1$ //$NON-NLS-2$
				inputStream);
		return id + 1;
	}

	private static int createTestResource(final FBType type, int id, final DataOutputStream outputStream,
			final DataInputStream inputStream) throws IOException {
		String request = MessageFormat.format(Messages.FBTester_CreateResourceInstance, Integer.valueOf(id),
				"_" + type.getName() + "_RES", "EMB_RES"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		sendREQ("", request, outputStream, inputStream); //$NON-NLS-1$

		request = MessageFormat.format(Messages.FBTester_CreateFBInstance, Integer.valueOf(id + 1),
				"_" + type.getName(), //$NON-NLS-1$
				type.getName());
		sendREQ("_" + type.getName() + "_RES", request, outputStream, //$NON-NLS-1$ //$NON-NLS-2$
				inputStream);
		return id + 2;
	}

	/** Clean the network required for testing a function block.
	 *
	 * @param type           the type
	 * @param monitoringPort the monitoring port
	 * @param runtimePort    the runtime port
	 * @param ipAddress
	 *
	 * @return the string */
	public static String cleanNetwork(final FBType type, final String ipAddress, final int port, final Socket socket) {
		int id = 0;
		try (Socket socketToUse = null == socket ? new Socket(InetAddress.getByName(ipAddress), port) : socket) {
			if (!socketToUse.isConnected()) {
				final SocketAddress endpoint = new InetSocketAddress(InetAddress.getByName(ipAddress), port);
				socketToUse.connect(endpoint);
			}
			final DataOutputStream outputStream = new DataOutputStream(
					new BufferedOutputStream(socketToUse.getOutputStream()));
			final DataInputStream inputStream = new DataInputStream(
					new BufferedInputStream(socketToUse.getInputStream()));

			socketToUse.setSoTimeout(timeOut);

			final String kill = MessageFormat.format(Messages.FBTester_KillFB, Integer.valueOf(id),
					"_" + type.getName() + "_RES"); //$NON-NLS-1$ //$NON-NLS-2$
			id++;
			final String delete = MessageFormat.format(Messages.FBTester_DeleteFB, Integer.valueOf(id),
					"_" + type.getName() + "_RES"); //$NON-NLS-1$ //$NON-NLS-2$
			id++;

			sendREQ("", kill, outputStream, inputStream); //$NON-NLS-1$
			sendREQ("", delete, outputStream, inputStream); //$NON-NLS-1$

		} catch (final RuntimeException | IOException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return e.getMessage();
		}
		return null;
	}

	/** Send a management commmand to the rutime.
	 *
	 * @param destination  the destination
	 * @param request      the request
	 * @param outputStream the output stream
	 * @param inputStream  the input stream
	 *
	 * @return the string
	 * @throws IOException
	 *
	 * @throws Exception   the exception */
	public static synchronized String sendREQ(final String destination, final String request,
			final DataOutputStream outputStream, final DataInputStream inputStream)
			throws RuntimeException, IOException {

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

			final StringBuilder response = new StringBuilder();
			@SuppressWarnings("unused")
			final byte b = inputStream.readByte();

			final short size = inputStream.readShort();

			for (int i = 0; i < size; i++) {
				response.append((char) inputStream.readByte());
			}

			if (response.toString().contains("Reason")) { //$NON-NLS-1$
				throw new RuntimeException(response.toString());
			}
			output = response.toString();
		}
		return output;
	}

	private TestNetworkFactory() {
		// private constructor to make class non instantiable
	}
}
