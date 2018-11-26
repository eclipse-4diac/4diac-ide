/*******************************************************************************
 * Copyright (c) 2011 TU Wien ACIN
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.comm;

import java.util.List;

import org.eclipse.fordiac.ide.util.comm.channels.CChannel;
import org.eclipse.fordiac.ide.util.comm.channels.ChannelManager;
import org.eclipse.fordiac.ide.util.comm.channels.IChannel;
import org.eclipse.fordiac.ide.util.comm.channels.IIecSender;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


public class UDPPublisher implements IIecSender {

	private String id;
	
	public UDPPublisher() {
	}
	
	@Override
	public boolean initialize(String id) {
		this.id=""; //$NON-NLS-1$
		try {
			CChannel.getInetAddress(id);
			CChannel.getPort(id);
		}
		catch (CommException e) {
			return false;
		}
		this.id=id;
		return true;
	}

	@Override
	public void sendIECData(List<IEC_ANY> sendData) throws CommException {
			ChannelManager.send(id, IChannel.UDP, sendData);
		
	}

	@Override
	public boolean deInitialize(String id) {
		//only abstract methods used
		this.
		id=""; //$NON-NLS-1$
		return true;
	}

}