/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH
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
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;
import org.eclipse.fordiac.ide.monitoring.communication.TCPCommunicationObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

class SystemMonitoringData {
	
	private final AutomationSystem system;
	
	private final Map<IInterfaceElement, MonitoringBaseElement> monitoredElements = new Hashtable<>();
	private final Map<String, MonitoringBaseElement> monitoredElementsPerPortStrings = new Hashtable<>();

	//TODO of java has a something like a c++ std::tuple it would be good to use here
	private final Hashtable<Device, TCPCommunicationObject> openCommunication = new Hashtable<>();
	private final Hashtable<Device, DevicePolling> pollingThreads = new Hashtable<>();
	
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

	TCPCommunicationObject getCommObject(Device dev){
		return openCommunication.get(dev);
	}
	
	void addCommObject(Device dev, TCPCommunicationObject comObj){
		openCommunication.put(dev, comObj);
	}
	
	DevicePolling getPollingThread(Device dev){
		return pollingThreads.get(dev);
	}
	
	void addPollingThread(Device dev, DevicePolling thread){
		pollingThreads.put(dev, thread);
	}
	
	void removePollingThread(Device dev){
		pollingThreads.remove(dev);
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


	public MonitoringBaseElement getMonitoringElementByPortString(String portString) {
		return monitoredElementsPerPortStrings.get(portString);
	}


	public void sendRemoveWatch(MonitoringBaseElement element) {
		TCPCommunicationObject commObject = getCommObject(element.getPort().getDevice());
		if(null != commObject){
			commObject.removeWatch(element);
		}		
	}

	public void sendAddWatch(MonitoringBaseElement element) {
		TCPCommunicationObject commObject = getCommObject(element.getPort().getDevice());
		if(null != commObject){
			commObject.addWatch(element);
		}
	}


	public boolean monitoringForSystemEnabled() {
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
