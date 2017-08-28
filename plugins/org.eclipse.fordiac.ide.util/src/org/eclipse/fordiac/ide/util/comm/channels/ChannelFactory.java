/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Oliver Hummer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm.channels;

import org.eclipse.fordiac.ide.util.comm.channels.tcp.TCPChannel;
import org.eclipse.fordiac.ide.util.comm.channels.udp.UDPChannel;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


/**
 * Factory for low level communication channels 
 */
class ChannelFactory {

	/**
	 * provide channels to remote communication partners
	 * @param channelID unique ID of the remote communication partner
	 * @param channelType type of communication {@link IChannel}
	 * @param receiver the receiver of remote messages, must be of type {@link IIecReceivable}
	 * @return channel to remote communication partner
	 * @throws CommException
	 */
	public static CChannel getChannel(String channelID, int channelType, IIecReceivable receiver)
			throws CommException {
		switch (channelType) {

		case IChannel.UDP: {
			return UDPChannel.register(channelID,receiver);
		}
		case IChannel.TCP: {
			return TCPChannel.register(channelID,receiver);
		}
		default:
			throw new CommException("Unsupported Channel Type: " + channelType);
		}
	}

}
