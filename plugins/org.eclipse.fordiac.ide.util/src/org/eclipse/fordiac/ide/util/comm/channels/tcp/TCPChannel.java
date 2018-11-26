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
package org.eclipse.fordiac.ide.util.comm.channels.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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
 * Implementation of {@link CChannel} for the TCP protocol providing
 * bidirectional communication. 
 */
public class TCPChannel extends CChannel {

	/**
	 * DataInputStream receiving the data from the network stack
	 */
	public DataInputStream in;

	/**
	 * DataOutputStream sending the data to the network stack
	 */
	public DataOutputStream out;

	/**
	 * maximum packet lenght of UDP packets
	 */
	public static final int TCP_PACKET_LENGTH = 1024;
	private Socket socket;
	public TCPCommThread commThread;
	private InetAddress inetAddress;

	private TCPChannel(int packet_length, String TCP_ID, IIecReceivable receiver)
			throws CommException {
		super();
		inetAddress = getInetAddress(TCP_ID);
		try {
			if (!inetAddress.isMulticastAddress()) {
				socket = new Socket(inetAddress, getPort(TCP_ID));
				in = new DataInputStream(new BufferedInputStream(socket
						.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(socket
						.getOutputStream()));
			} else {
				throw new CommException("Invalid ID");
			}
		} catch (IOException e) {
			throw new CommException("Socket Error");
		}
		(commThread = new TCPCommThread(this, receiver)).start();
	}

	/**
	 * method for creating a new tcp channel
	 * 
	 * @param TCP_ID
	 *            String holding Internet Address and port, e.g.,
	 *            "localhost:61500" or "127.0.0.1:61500".
	 * @param receiver
	 *            type of communication {@link IChannel}
	 * @return Channel
	 * @throws CommException
	 */
	public static CChannel register(String TCP_ID, IIecReceivable receiver)
			throws CommException {
		return new TCPChannel(TCP_PACKET_LENGTH, TCP_ID, receiver);
	}

	@Override
	public void deregister() throws CommException {
		commThread.interrupt();
		try {
			socket.close();
			commThread.join(1000);
		} catch (IOException e) {
			throw new CommException("Invalid ID");
		} catch (InterruptedException e) {
			
		}
	}

	@Override
	public List<IEC_ANY> receiveFrom() throws IOException, CommException {
		if (socket == null)
			return null;

		List<IEC_ANY> list = new ArrayList<IEC_ANY>();

		if (!socket.isConnected())
			return null;
		do {
			// as long as no data is received decodeFrom is blocking, since it
			// performs a reading operation on the in datastream
			try {
			IEC_ANY decode = ASN1.decodeFrom(in);
			
			if (decode != null) {
				list.add(decode);
			} else
				break;
			}
			catch (IOException e){
				if (e instanceof EOFException){
					throw new CommException("Socket closed by server");
				}
				throw e;
			}
		} while (in.available() > 0);
		
		return list;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Send given data to the the communication partner.
	 * 
	 * @param ID
	 *            unique ID of the remote communication partner
	 * @param Data
	 *            List of {@link IEC_ANY} to be sent to the communication
	 *            partner
	 */
	public void send(List<IEC_ANY> Data) {
		try {
			for (IEC_ANY elem : Data) {
				if (elem != null)
					out.write(elem.encode());
				else
					out.write(ASN1.NULL);
			}
			out.flush();
		} catch (Exception e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}

	}

}
