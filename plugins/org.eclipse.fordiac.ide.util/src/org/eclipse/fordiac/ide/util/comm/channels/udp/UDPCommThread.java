/*******************************************************************************
 * Copyright (c) 2011, 2015 TU Wien ACIN, fortiss GmbH
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

import java.io.IOException;

import org.eclipse.fordiac.ide.util.comm.channels.CCommThread;
import org.eclipse.fordiac.ide.util.comm.channels.IIecReceivable;



/**
 * This class implements {@link CCommThread} for UDP communication. The receiving channel is established within this Thread, 
 */
public class UDPCommThread extends CCommThread {

	private UDPChannel channel;
	private IIecReceivable receiver;

	public UDPCommThread(UDPChannel channel, IIecReceivable receiver) {
		this.channel = channel;
		this.receiver=receiver;
	}

	@Override
	public void run() {
		while (!isInterrupted() && channel.getSocket() != null) {
			try {
				receiver.receiveIECData(channel.receiveFrom());
			} catch (IOException e) {

				return;
			}
		}
	}

}
