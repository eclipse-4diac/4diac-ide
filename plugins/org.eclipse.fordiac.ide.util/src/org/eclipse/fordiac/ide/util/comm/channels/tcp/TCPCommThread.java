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

import java.io.IOException;

import org.eclipse.fordiac.ide.util.Activator;
import org.eclipse.fordiac.ide.util.comm.channels.CCommThread;
import org.eclipse.fordiac.ide.util.comm.channels.IIecReceivable;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


public class TCPCommThread extends CCommThread {

	private TCPChannel channel;
	private IIecReceivable receiver;

	public TCPCommThread(TCPChannel channel, IIecReceivable receiver) {
		this.channel = channel;
		this.receiver = receiver;
	}

	@Override
	public void run() {
		while (!isInterrupted() && channel.getSocket() != null) {
			try {

				if (!channel.getSocket().isClosed())
					receiver.receiveIECData(channel.receiveFrom());
			} catch (IOException e) {
				if (!isInterrupted()) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				return;
			} catch (CommException e) {
				// Socket closed by peer
				return;
			}

		}
	}

}
