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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


/**
 * Abstract superclass for all communication channels to low level control.
 */
public abstract class CChannel {

	private static final String INVALID_ID = "INVALID_ID"; //$NON-NLS-1$

	/**
	 * opens a channel to a remote communication partner
	 * @param channelID unique ID of the remote communication partner
	 * @param receiver the receiver of remote messages, must be of type {@link IIecReceivable}
	 * @return channel to remote communication partner
	 * @throws CommException
	 */
	public static CChannel register(String channelID,IIecReceivable receiver) throws CommException {
		return null;
	}

	/**
	 * Returns a port number parsed from the given string.
	 * 
	 * @param id
	 *            A string in the form of a socket identifier, e.g., <TT>
	 *            240.0.0.2:50</TT>.
	 * @exception CommException
	 *                INVALID_ID
	 */
	public static int getPort(String id) throws CommException {
		int n = id.indexOf(':');
		if ((n <= 0) || (n == (id.length() - 1))) {
			throw new CommException(INVALID_ID);
		}
		try {
			return Integer.valueOf(id.substring(n + 1)).intValue();
		} catch (NumberFormatException e) {
			throw new CommException(INVALID_ID);
		}
	}

	/**
	 * Returns an InetAddress parsed from the given string.
	 * 
	 * @param id
	 *            A string in the form of a socket identifier, e.g., <TT>
	 *            240.0.0.2:50</TT>.
	 * @exception CommException
	 *                INVALID_ID
	 */
	public static InetAddress getInetAddress(String id) throws CommException {
		int n = id.indexOf(':');
		if (n <= 0) {
			throw new CommException(INVALID_ID);
		}
		try {
			return InetAddress.getByName(id.substring(0, n));
		} catch (UnknownHostException e) {
			throw new CommException(INVALID_ID);
		}
	}

	/**
	 * closes the channel to the remote communication partner
	 * @throws CommException
	 */
	public abstract void deregister() throws CommException;
	
	/**
	 * callback function for receiving (and decoding) messages, called by {@link CCommThread} 
	 * @return List<{@link IEC_ANY}> received data-structures
	 * @throws IOException, CommException
	 */
	public abstract List<IEC_ANY> receiveFrom() throws IOException, CommException;
}