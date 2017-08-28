/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.monitoring.communication.TCPCommunicationObject;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.systemmanagement.Activator;

class DevicePolling implements Runnable {

	private final AutomationSystem system;
	private final Device device;
	private final TCPCommunicationObject commObj;
	private final Thread thread;
	
	private boolean running = true;

	public DevicePolling(AutomationSystem system, Device device, TCPCommunicationObject commObj) {
		this.system = system;
		this.device = device;
		this.commObj = commObj;
		this.thread = new Thread(this);
	}
	
	public Thread getThread(){
		return thread;
	}
	
	public void setRunning(boolean running){
		//TODO maybe a syncronized will be needed here
		this.running = running;
	}
	
	@Override
	public void run() {
		if (commObj != null) {
			int pollingIntervall = PreferenceConstants.getPollingInterval();
			while (running) {
				try {
					Thread.sleep(pollingIntervall);
				} catch (InterruptedException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				if(commObj.isConnected()){
					//TODO maybe a counter if something is todo would be nice
					commObj.sendReq(system, device);
					commObj.queryBreakpoints(system, device);
				} else {
					running = false;
				}
				
			}
		}
	}
}