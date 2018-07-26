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

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.systemmanagement.Activator;
import org.eclipse.jface.operation.IRunnableWithProgress;

class EnableSystemMonitoringRunnable implements IRunnableWithProgress {

	private final SystemMonitoringData systemMonitoringData;
	
	public EnableSystemMonitoringRunnable(SystemMonitoringData systemMonitoringData) {
		this.systemMonitoringData = systemMonitoringData;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
		List<Device> devices = this.systemMonitoringData.getSystem().getSystemConfiguration().getDevices(); 
		int count = devices.size() * 2 ;  // the * 2 is for creating the polling threads
		count += this.systemMonitoringData.getMonitoredElements().size();
		
		monitor.beginTask("Enable monitoring for system", count);
		connectToDevices(devices, monitor);
		addWatches(monitor);
		startPollingThreads(monitor);
		monitor.done();
	}

	private void connectToDevices(List<Device> devices, IProgressMonitor monitor) {
		monitor.subTask("Connecting to the devices");
		for (Device dev : devices) {
			if(monitor.isCanceled()) { 
				break;
			}
			IDeviceManagementInteractor devMgmInteractor = getDevMgmInteractor(dev); 
			try {
				devMgmInteractor.connect();
			} catch (DeploymentException e) {
				systemMonitoringData.removeDeviceMonitoringHandler(dev);
				Activator.getDefault().logError("Monitoring: Cann  not connect to device " + dev.getName(), e);
			}
			monitor.worked(1);
		}
	}

	private void addWatches(IProgressMonitor monitor) {
		monitor.subTask("Adding the watches");
		for (MonitoringBaseElement element : systemMonitoringData.getMonitoredElements()){			
			if(monitor.isCanceled()) {
				break;
			}
			if (element instanceof MonitoringElement) {
				monitor.subTask("Add watch for: " + element.getPortString());
				systemMonitoringData.sendAddWatch(element);
				monitor.worked(1);
			}
		}		
	}

	private void startPollingThreads(IProgressMonitor monitor) {
		monitor.subTask("Enabling the polling threads");		
		for (Entry<Device, DeviceMonitoringHandler> runner: systemMonitoringData.getDevMonitoringHandlers().entrySet()){
			if(monitor.isCanceled()) {
				break;
			}
			runner.getValue().enable();
			monitor.worked(1);
		}		
	}
	
	private IDeviceManagementInteractor getDevMgmInteractor(Device dev) {
		return getOrCreateDevMonitoringHandler(dev).getDevMgmInteractor();
	}

	private DeviceMonitoringHandler getOrCreateDevMonitoringHandler(Device dev) {
		DeviceMonitoringHandler retVal = systemMonitoringData.getDevMonitoringHandler(dev);
		if(null == retVal) {
			retVal = new DeviceMonitoringHandler(dev, systemMonitoringData);
			systemMonitoringData.addDevMonitoringHandler(dev, retVal);
		}
		return retVal;		
	}

}