/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.monitoring.communication.MonitorInformation;
import org.eclipse.fordiac.ide.monitoring.communication.TCPCommunicationObject;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.systemmanagement.Activator;

class DeviceMonitoringHandler implements Runnable {

	private final Device device;
	private final TCPCommunicationObject commObj;
	private final Thread thread;
	
	private boolean running = true;

	public DeviceMonitoringHandler(Device device, MonitorInformation monitorInfo) {
		this.device = device;
		this.commObj = new TCPCommunicationObject(monitorInfo);
		this.thread = new Thread(this);
	}
	
	public Thread getThread(){
		return thread;
	}
	
	public TCPCommunicationObject getCommObject() {
		return commObj;
	}
	
	public synchronized void enable() {
		running = true;
		thread.start();
	}
	
	public synchronized void disable() {
		running = false;
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
					commObj.sendReq(device.getAutomationSystem(), device);
					commObj.queryBreakpoints(device.getAutomationSystem(), device);
				} else {
					running = false;
				}
				
			}
		}
	}
}