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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SystemMonitoringData {
	
	private final AutomationSystem system;
	
	private final Map<IInterfaceElement, MonitoringBaseElement> monitoredElements = new HashMap<>();
	private final Map<String, MonitoringBaseElement> monitoredElementsPerPortStrings = new HashMap<>();

	private final Map<Device, DeviceMonitoringHandler> deviceHandlers = new HashMap<>();
	
	private boolean monitoringEnabled = false;
	
	public SystemMonitoringData(AutomationSystem system) {
		this.system = system;
	}

	
	AutomationSystem getSystem(){
		return system;
	}
	
	Collection<MonitoringBaseElement> getMonitoredElements(){ 
		return monitoredElements.values();
	}
	
	DeviceMonitoringHandler getDevMonitoringHandler(Device dev){
		return deviceHandlers.get(dev);
	}
	
	void addDevMonitoringHandler(Device dev, DeviceMonitoringHandler handler){
		deviceHandlers.put(dev, handler);
	}
	
	Map<Device, DeviceMonitoringHandler>getDevMonitoringHandlers(){
		return deviceHandlers;
	}
	
	void removeDeviceMonitoringHandler(Device dev){
		deviceHandlers.remove(dev);
	}
	
	public void enableSystem() {
		EnableSystemMonitoringRunnable enable = new EnableSystemMonitoringRunnable(this);
		Shell shell = Display.getDefault().getActiveShell();
		try {
			new ProgressMonitorDialog(Display.getDefault().getActiveShell()).run(
					true, true, enable);
			monitoringEnabled = true;
		} catch (InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (InterruptedException ex) {
			MessageDialog.openInformation(shell, "Enable Monitoring Aborted",
					"Enable Monitoring Aborted");
		}
	}
	
	public void enableSystemSynch(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		EnableSystemMonitoringRunnable enable = new EnableSystemMonitoringRunnable(this);
		enable.run(monitor);
		monitoringEnabled = true;
	}


	public void disableSystem() {
		DisableSystemMonitoringRunnable disable = new DisableSystemMonitoringRunnable(this);
		Shell shell = Display.getDefault().getActiveShell();
		try {
			new ProgressMonitorDialog(Display.getDefault().getActiveShell()).run(
					true, true, disable);
			monitoringEnabled = false;
		} catch (InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (InterruptedException ex) {
			MessageDialog.openInformation(shell, "Disable Monitoring Aborted",
					"Disable Monitoring Aborted");
		}
		
	}
	
	public void disableSystemSynch(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		DisableSystemMonitoringRunnable disable = new DisableSystemMonitoringRunnable(this);
		monitoringEnabled = false;
		disable.run(monitor);
	}


	public MonitoringBaseElement getMonitoringElementByPortString(String portString) {
		return monitoredElementsPerPortStrings.get(portString);
	}


	public void sendRemoveWatch(MonitoringBaseElement element) {
		IDeviceManagementInteractor devMgmInteractor = getDevMgmInteractor(element.getPort().getDevice());
		if(null != devMgmInteractor && devMgmInteractor.isConnected()){
			try {
				devMgmInteractor.removeWatch(element);
			} catch (DeploymentException e) {
				// TODO think if error should be shown to the user
				Activator.getDefault().logError("Could not remove watch for " + element.getQualifiedString(), e);
			}
		}		
	}

	public void sendAddWatch(MonitoringBaseElement element) {
		IDeviceManagementInteractor devMgmInteractor = getDevMgmInteractor(element.getPort().getDevice());
		if(null != devMgmInteractor && devMgmInteractor.isConnected()){
			try {
				devMgmInteractor.addWatch(element);
			} catch (DeploymentException e) {
				// TODO think if error should be shown to the user
				Activator.getDefault().logError("Could not add watch for " + element.getQualifiedString(), e);
			}
		}
	}


	public IDeviceManagementInteractor getDevMgmInteractor(Device device) {
		DeviceMonitoringHandler handler = getDevMonitoringHandler(device);
		return (null != handler) ? handler.getDevMgmInteractor() : null;
	}


	public boolean isMonitoringForSystemEnabled() {
		return monitoringEnabled;
	}


	public void removeMonitoringElement(MonitoringBaseElement element) {
		PortElement port = element.getPort();
		
		if (element instanceof MonitoringElement) {
			sendRemoveWatch(element);
		}
		monitoredElements.remove(port.getInterfaceElement());
		monitoredElementsPerPortStrings.remove(port.getPortString());		
	}


	public void addMonitoringElement(MonitoringBaseElement element) {
		PortElement port = element.getPort();

		monitoredElements.put(port.getInterfaceElement(), element);
		monitoredElementsPerPortStrings.put(port.getPortString(), element);
        
		if (element instanceof MonitoringElement) {
			sendAddWatch(element);
		}		
	}


	public MonitoringBaseElement getMonitoredElement(IInterfaceElement port) {
		return monitoredElements.get(port);
	}

	
	
}
