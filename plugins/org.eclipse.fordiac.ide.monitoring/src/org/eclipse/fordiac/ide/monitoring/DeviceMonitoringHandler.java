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

import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.FB;
import org.eclipse.fordiac.ide.deployment.devResponse.Port;
import org.eclipse.fordiac.ide.deployment.devResponse.Response;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.DeviceManagementInteractorFactory;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.systemmanagement.Activator;

class DeviceMonitoringHandler implements Runnable {

	private final Device device;
	private final IDeviceManagementInteractor devInteractor;
	private final SystemMonitoringData systemMonData;
	private final Thread thread;
	
	private boolean running = true;
	
	private synchronized void setRunning(boolean val) {
		running = val;
	}
	
	private synchronized boolean isRunning() {
		return running;
	}

	public DeviceMonitoringHandler(Device device, SystemMonitoringData systemMonData) {
		this.device = device;
		devInteractor = DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor(device);
		this.systemMonData = systemMonData;
		this.thread = new Thread(this);
	}
	
	public Thread getThread(){
		return thread;
	}
	
	public IDeviceManagementInteractor getDevMgmInteractor() {
		return devInteractor;
	}
	
	public synchronized void enable() {
		setRunning(true);
		thread.start();
	}
	
	public synchronized void disable() {
		setRunning(running);
	}
	
	@Override
	public void run() {
		if (devInteractor != null) {
			int pollingIntervall = PreferenceConstants.getPollingInterval();
			while (isRunning()) {
				try {
					Thread.sleep(pollingIntervall);
				} catch (InterruptedException e) {
					Activator.getDefault().logError(e.getMessage(), e);
				}
				if(devInteractor.isConnected()){
					try {
						Response resp = devInteractor.readWatches();
						if(null != resp) {
							updateWatches(resp);
						}
					} catch (DeploymentException e) {
						handleDeviceIssue();						
					}
				} else {
					setRunning(false);
				}
				
			}
		}
	}

	private void updateWatches(Response resp) {
		if (resp.getWatches() != null) {
			for (org.eclipse.fordiac.ide.deployment.devResponse.Resource res : resp.getWatches().getResources()) {
				String resName = device.getName() + "." + res.getName() + "."; //$NON-NLS-1$ //$NON-NLS-2$

				for (FB fb : res.getFbs()) {
					String fbName = resName + fb.getName() + "."; //$NON-NLS-1$
					 
					for (Port p : fb.getPorts()) {
						final MonitoringBaseElement element = systemMonData.getMonitoringElementByPortString(fbName + p.getName());
						if (element instanceof MonitoringElement) {
							updateMonitoringElement((MonitoringElement)element, p);
						}
					}
				}
			}
		}
	}

	private static void updateMonitoringElement(MonitoringElement monitoringElement, Port p) {
		for (Data d : p.getDataValues()) {
			long timeAsLong = 0;
			try {
				timeAsLong = Long.parseLong(d.getTime());
			} catch (NumberFormatException nfe) {
				timeAsLong = 0;
			}
			monitoringElement.setSec(timeAsLong / 1000);
			monitoringElement.setUsec(timeAsLong % 1000);
			monitoringElement.setCurrentValue(d.getValue());
			if (d.getForced() != null) {
				monitoringElement.setForce(d.getForced().equals("true")); //$NON-NLS-1$
			}
		}
	}
	
	private void handleDeviceIssue() {
		// we have an issue with this device close connection clear monitoring values
		try {
			devInteractor.disconnect();
		} catch (DeploymentException e) {
			// we don't need to do anything here
		}
		systemMonData.getMonitoredElements().stream().
			filter(el -> (el.getPort().getDevice().equals(device) && (el instanceof MonitoringElement)) ).
			forEach(el -> ((MonitoringElement)el).setCurrentValue(""));  //$NON-NLS-1$
	}

}