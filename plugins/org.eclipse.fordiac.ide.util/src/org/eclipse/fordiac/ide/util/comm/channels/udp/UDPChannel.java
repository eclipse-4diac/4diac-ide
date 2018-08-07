/*******************************************************************************
 * Copyright (c) 2011, 2014 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Oliver Hummer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm.channels.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.fordiac.ide.util.comm.channels.CChannel;
import org.eclipse.fordiac.ide.util.comm.channels.IChannel;
import org.eclipse.fordiac.ide.util.comm.channels.IIecReceivable;
import org.eclipse.fordiac.ide.util.comm.datatypes.ASN1;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


/**
 * Implementation of {@link CChannel} for the UDP protocol. Depending on
 * IP-Address, a multicast or unicast channel is created.
 */
public class UDPChannel extends CChannel {

	/**
	 * DataInputStream receiving the data from the network stack
	 */
	public DataInputStream in;
	
	/**
	 * maximum packet lenght of UDP packets 
	 */
	public static final int UDP_PACKET_LENGTH = 1024;
	private DatagramSocket socket;
	private byte[] bytes;
	private UDPCommThread commThread;
	private InetAddress inetAddress;

	private UDPChannel(int packet_length, String UDP_ID, IIecReceivable receiver)
			throws CommException {
		super();
		inetAddress = getInetAddress(UDP_ID);
		try {
			if (inetAddress.isMulticastAddress()) {
				socket = new MulticastSocket(getPort(UDP_ID));
				((MulticastSocket) socket).joinGroup(inetAddress);
			} else {
				socket = new DatagramSocket(getPort(UDP_ID));
			}
		} catch (IOException e) {
			throw new CommException("Invalid ID");
		}
		bytes = new byte[packet_length];
		in = new DataInputStream(new ByteArrayInputStream(bytes));
		(commThread = new UDPCommThread(this, receiver)).start();
	}

	/**
	 * method for creating a new udp channel
	 * @param UDP_ID String holding Internet Address and port, e.g., "localhost:61500" or "127.0.0.1:61500".
	 * @param receiver type of communication {@link IChannel}
	 * @return Channel
	 * @throws CommException
	 */
	public static CChannel register(String UDP_ID, IIecReceivable receiver)
			throws CommException {
		return new UDPChannel(UDP_PACKET_LENGTH, UDP_ID, receiver);
	}

	@Override
	public void deregister() throws CommException {
		commThread.interrupt();
		if (socket instanceof MulticastSocket)
			try {
				((MulticastSocket) socket).leaveGroup(inetAddress);
			} catch (IOException e1) {
				throw new CommException("Invalid ID");
			}

		socket.close();
		socket.disconnect();
	}

	@Override
	public List<IEC_ANY> receiveFrom() throws IOException {
		if (socket == null)
			return null;
		DatagramPacket packet = new DatagramPacket(bytes, UDP_PACKET_LENGTH);
		packet.setLength(bytes.length);
		socket.receive(packet);

		in.reset();
		List<IEC_ANY> list = new ArrayList<IEC_ANY>();

		while (in.available() > 0) {
			IEC_ANY decode = ASN1.decodeFrom(in);
			if (decode != null)
				list.add(decode);
			else
				break;
		}
		return list;
	}

	/**
	 * @return the socket
	 */
	public DatagramSocket getSocket() {
		return socket;
	}

	/**
	 * Send given data to the the communication partner.
	 *  
	 * @param ID unique ID of the remote communication partner
	 * @param Data List of {@link IEC_ANY} to be sent to the communication partner
	 */
	public static void send(String ID, List<IEC_ANY> Data) {
		try {
			DatagramSocket myDataGramSocket = new DatagramSocket();
			InetAddress inetAddress = CChannel.getInetAddress(ID);
			int Port = CChannel.getPort(ID);

			ByteArrayOutputStream Output = new ByteArrayOutputStream();
			for (IEC_ANY elem : Data) {
				if (elem != null)
					Output.write(elem.encode());
				else
					Output.write(ASN1.NULL);
			}

			int length = Output.size();
			if (length > UDPChannel.UDP_PACKET_LENGTH)
				length = UDPChannel.UDP_PACKET_LENGTH;

			DatagramPacket myDGP = new DatagramPacket(Output.toByteArray(),
					length, inetAddress, Port);
			myDataGramSocket.send(myDGP);
			myDataGramSocket.close();

		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

	}

}
