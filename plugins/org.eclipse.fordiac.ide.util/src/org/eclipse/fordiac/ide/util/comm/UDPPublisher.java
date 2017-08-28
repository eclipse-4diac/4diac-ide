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

	protected ChannelManager m_oManager;
	protected boolean m_bInitialized=false;
	protected String m_sID;
	
	public UDPPublisher() {
		m_oManager = ChannelManager.getInstance();
	}
	
	public boolean Initialize(String pa_sID) {
		m_bInitialized=false;
		m_sID=""; //$NON-NLS-1$
		try {
			CChannel.getInetAddress(pa_sID);
			CChannel.getPort(pa_sID);
		}
		catch (CommException e) {
			return false;
		}
		m_bInitialized=true;
		m_sID=pa_sID;
		return true;
	}

	public void SendIECData(List<IEC_ANY> sendData) throws CommException {
			ChannelManager.send(m_sID, IChannel.UDP, sendData);
		
	}

	@Override
	public boolean DeInitialize(String pa_sID) {
		//only abstract methods used;
		m_bInitialized=false;
		m_sID=""; //$NON-NLS-1$
		return true;
	}

}