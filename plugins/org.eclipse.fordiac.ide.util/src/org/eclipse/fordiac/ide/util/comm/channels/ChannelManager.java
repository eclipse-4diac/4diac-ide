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
package org.eclipse.fordiac.ide.util.comm.channels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.util.comm.channels.tcp.TCPChannel;
import org.eclipse.fordiac.ide.util.comm.channels.udp.UDPChannel;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


/**
 * class managing all communication channels to low level control (singleton
 * pattern)
 */
public class ChannelManager {

	private static ChannelManager manager;
	private static Map<String, CChannel> channels = new HashMap<>();

	private ChannelManager() {
		super();
	}

	/**
	 * @return the channel manager
	 */
	public static ChannelManager getInstance() {
		if (manager == null) {
			manager = new ChannelManager();
		}
		return manager;
	}

	/**
	 * If Channel with <code>channelID</code> exists, the Channel is returned,
	 * else <code>null</code> is returned
	 * 
	 * @param channelID
	 *            unique ID of the remote communication partner
	 * @return Channel
	 */
	private static CChannel getChannel(String channelID) {
		return channels.get(channelID);
	}

	/**
	 * Is Channel with <code>channelID</code> is registered at the Channel
	 * manager?
	 * 
	 * @param channelID
	 *            unique ID of the remote communication partner
	 * @return boolean
	 */
	private static boolean existsChannel(String channelID) {
		return channels.get(channelID) != null;
	}

	/**
	 * Register a channel with given channelID, channelType, and a Receiver for
	 * callback for working on the data after receiving it from the channel
	 * 
	 * @param channelID
	 *            unique ID of the remote communication partner
	 * @param channelType
	 *            type of communication {@link IChannel}
	 * @param receiver
	 *            the receiver of remote messages, must be of type
	 *            {@link IIecReceivable}
	 * @throws CommException
	 */
	public static void register(String channelID, int channelType,
			IIecReceivable receiver) throws CommException {
		if (existsChannel(channelID)) {
			throw new CommException("Channel already in use.");
		}
		channels.put(channelID, ChannelFactory.getChannel(channelID, channelType, receiver));
	}

	/**
	 * Deregister a channel, identified by the channelID, from the
	 * ChannelManager
	 * 
	 * @param channelID
	 *            unique ID of the remote communication partner
	 * @throws CommException
	 */
	public static void deregister(String channelID) throws CommException {
		CChannel channel = getChannel(channelID);
		if (channel != null) {
			channel.deregister();
			channels. remove(channelID);
		}
		else {
			throw new CommException("No Such Channel.");
		}
	}

	/**
	 * Method for sending Data to the communication partner over the channel,
	 * defined by channelType and channelID
	 * 
	 * @param channelID
	 *            unique ID of the remote communication partner
	 * @param channelType
	 *            type of communication {@link IChannel}
	 * @param data
	 *            List of {@link IEC_ANY} structures to be encoded and sent to
	 *            communication partner
	 * @throws CommException
	 */
	public static void send(String channelID, int channelType,
			List<IEC_ANY> data) {
		switch (channelType) {

		case IChannel.UDP: 
			UDPChannel.send(channelID, data);
			break;
		
		case IChannel.TCP: {
				CChannel channel = channels.get(channelID);
				if (channel instanceof TCPChannel) {
					((TCPChannel) channel).send(data);
				}
			}
		}

	}
}
