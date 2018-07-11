/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.monitoring.communication.TCPCommunicationObject;
import org.eclipse.jface.operation.IRunnableWithProgress;

class DisableSystemMonitoringRunnable implements IRunnableWithProgress {

	private final SystemMonitoringData systemMonitoringData;
	
	public DisableSystemMonitoringRunnable(SystemMonitoringData systemMonitoringData) {
		this.systemMonitoringData = systemMonitoringData;
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		
		List<Device> devices = this.systemMonitoringData.getSystem().getSystemConfiguration().getDevices(); 
		int count = devices.size() * 2 ;  // the * 2 is for creating the polling threads
		count += this.systemMonitoringData.getMonitoredElements().size();
		
		monitor.beginTask("Disable monitoring for system", count);
		stopPollingThreads(monitor);
		removeWatches(monitor);
		disconnectFromDevices(devices, monitor);
		monitor.done();
	}

	private void disconnectFromDevices(List<Device> devices, IProgressMonitor monitor) {
		monitor.subTask("Disconnecting the devices");
		for (Device dev : devices) {
			if(monitor.isCanceled()) break;
			TCPCommunicationObject commObject = systemMonitoringData.getCommObject(dev);
			if(null != commObject){
				commObject.disable();				
			}
			monitor.worked(1);
		}
	}

	private void removeWatches(IProgressMonitor monitor) {
		monitor.subTask("Connecting to the devices");
		for (MonitoringBaseElement element : systemMonitoringData.getMonitoredElements()){	
			if(monitor.isCanceled()) break;
			if (element instanceof MonitoringElement) {
				monitor.subTask("Remove watch for: " + element.getPortString());
				systemMonitoringData.sendRemoveWatch(element);
				//clear the value to show that there is currently no value
				((MonitoringElement)element).setCurrentValue("");  //$NON-NLS-1$
				monitor.worked(1);
			}
		}		
	}

	private void stopPollingThreads(IProgressMonitor monitor) {
		monitor.subTask("Enabling the polling threads");
		for (Device dev : systemMonitoringData.getSystem().getSystemConfiguration().getDevices()) {
			if(monitor.isCanceled()) break;
			DevicePolling t = systemMonitoringData.getPollingThread(dev);

			if(null != t){
				t.setRunning(false);
				systemMonitoringData.removePollingThread(dev);
			}
			monitor.worked(1);
		}		
	}

}