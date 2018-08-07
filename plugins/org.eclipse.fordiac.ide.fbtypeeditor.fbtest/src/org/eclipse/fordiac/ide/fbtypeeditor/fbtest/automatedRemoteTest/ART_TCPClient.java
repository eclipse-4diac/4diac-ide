/*******************************************************************************
 * Copyright (c) 2011, 2013, 2014 TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.fbtest.automatedRemoteTest;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.fbtypeeditor.fbtest.Activator;
import org.eclipse.fordiac.ide.util.comm.channels.ChannelManager;
import org.eclipse.fordiac.ide.util.comm.channels.IChannel;
import org.eclipse.fordiac.ide.util.comm.channels.IIecReceivable;
import org.eclipse.fordiac.ide.util.comm.channels.IIecSender;
import org.eclipse.fordiac.ide.util.comm.datatypes.IEC_ANY;
import org.eclipse.fordiac.ide.util.comm.exceptions.CommException;


public class ART_TCPClient implements IIecReceivable, IIecSender {

	private ChannelManager manager;

	private List<List<IEC_ANY>> receiveDataList;

	public List<List<IEC_ANY>> getReceiveDataList() {
		return receiveDataList;
	}

	private int ReceiveListSize = 0;
	
	private int counter=0;
	
	private String channelID;
	/**
	 * @param args
	 * @throws CommException 
	 */

	public ART_TCPClient(String paID, List<List<IEC_ANY>> paReceiveDataListList, int paListSize) throws CommException {
		manager = ChannelManager.getInstance();
		channelID=paID;
		
		if (!this.initialize(paID)) throw new CommException("Initialisation of CommunicationChannel to Runtime failed");
		
		receiveDataList=paReceiveDataListList;
		ReceiveListSize = paListSize;
		if (null != receiveDataList) {
			if (receiveDataList.size()!= paListSize) {
				ReceiveListSize = receiveDataList.size();
			}
		} else {
			paListSize = 0;
		}
	}
	
	private boolean ReceivedDataTypeMatch(List<IEC_ANY> receivedList,List<IEC_ANY> localList)
	{
		
		if (receivedList.size()!= localList.size())
			return false;
		boolean equal=true;
		for (int i=0;i<receivedList.size();i++)
			if (receivedList.get(i).getClass()!=localList.get(i).getClass())
				equal=false;
		
		return equal;
	}
	
	private boolean CopyData(List<IEC_ANY> receivedList,List<IEC_ANY> localList)
	{
		
		if (receivedList.size()!= localList.size())
			return false;
		for (int i=0;i<receivedList.size();i++)
			if (receivedList.get(i).getClass()==localList.get(i).getClass())
				localList.set(i, receivedList.get(i));
		
		return true;
	}

	@Override
	public synchronized void receiveIECData(List<IEC_ANY> inList) {
		if (ReceiveListSize>counter) {
		if (!ReceivedDataTypeMatch(inList, receiveDataList.get(counter)))
			{
			System.out.println("did not receive expected data");
			return;
			}
//		System.out.println("List size: " + inList.size());
		CopyData(inList, receiveDataList.get(counter));
		++counter;
		} else {
			System.out.println("no space for received data");
		}
	}
	
	protected void SendIECData(String ID, List<IEC_ANY> sendData){
		try {
			ChannelManager.send(ID, IChannel.TCP, sendData);
		} catch (CommException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		}
		
	}

	public void setMyReceiveDataList(List<List<IEC_ANY>> myReceiveData) {
		this.receiveDataList = myReceiveData;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getCounter() {
		return counter;
	}

	public void deRegister() throws CommException {
		manager.deregister(channelID);
	}

	@Override
	public boolean initialize(String pa_sID) {
		
		try {
			manager.register(pa_sID, IChannel.TCP, this);
		} catch (CommException e) {
			Activator.getDefault().logError(e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean deInitialize(String pa_sID) {
		try {
			manager.deregister(pa_sID);
		} catch (CommException e) {
			//deregistration failed
			return false;
		}
		return true;
	}

	@Override
	public synchronized void sendIECData(List<IEC_ANY> sendData) throws CommException {
		ChannelManager.send(channelID, IChannel.TCP, sendData);
		counter=0;
	}

	@Override
	public void setMyReceiveData(List<IEC_ANY> pa_loReceiveData) {
		this.receiveDataList = new ArrayList<List<IEC_ANY>>();
		this.receiveDataList.add(pa_loReceiveData);
		
	}
	
}
