/*******************************************************************************
 * Copyright (c) 2012 - 2016 Profactor GmbH, AIT, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Filip Andren, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.PortElement;
import org.eclipse.fordiac.ide.monitoring.communication.TCPCommunicationObject;
import org.eclipse.fordiac.ide.systemmanagement.Activator;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;


/**
 * Singleton instance that Coordinates and manages all the Ports to be
 * monitored.
 * 
 * @author gebenh
 */
public class MonitoringManager extends AbstractMonitoringManager {

	/** The instance. */
	private static MonitoringManager instance;
	
	private final Map<String, SystemMonitoringData> systemMonitoringData = new Hashtable<>();

	/**
	 * Gets the single instance of MonitoringManager.
	 * 
	 * @return single instance of MonitoringManager
	 */
	public static MonitoringManager getInstance() {
		if (instance == null) {
			instance = new MonitoringManager();
		}
		return instance;
	}

	/**
	 * Instantiates a new monitoring manager.
	 */
	private MonitoringManager() {
	}
	

	/**
	 * Notify add port.
	 * 
	 * @param port
	 *            the port
	 */
	private void notifyAddPort(PortElement port) {
		for (IMonitoringListener monitoringListener : monitoringListeners) {
			monitoringListener.notifyAddPort(port);
		}
	}

	/**
	 * Notify remove port.
	 * 
	 * @param port
	 *            the port
	 */
	private void notifyRemovePort(PortElement port) {
		for (IMonitoringListener monitoringListener : monitoringListeners) {
			monitoringListener.notifyRemovePort(port);
		}
	}

	/**
	 * Notify trigger event.
	 * 
	 * @param port
	 *            the port
	 */
	private void notifyTriggerEvent(PortElement port) {
		for (IMonitoringListener monitoringListener : monitoringListeners) {
			monitoringListener.notifyTriggerEvent(port);
		}
	}

	/**
	 * Gets the monitoring element.
	 * 
	 * @param port
	 *            the port
	 * 
	 * @return the monitoring element
	 */
	public MonitoringBaseElement getMonitoringElement(IInterfaceElement port) {
		if (port != null) {
			//TODO model refactoring - add way to get system from port 
			for (SystemMonitoringData data : systemMonitoringData.values()) {
				MonitoringBaseElement element = data.getMonitoredElement(port);
				if(null != element){
					return element;
				}
			}			
		}
		return null;
	}

	/**
	 * Adds monitoring elements.
	 * 
	 * @param elemeent
	 *            the monitoring element
	 */
	public void addMonitoringElement(MonitoringBaseElement element) { 
		PortElement port = element.getPort();
		SystemMonitoringData data = getSystemMonitoringData(port.getSystem());
		
		data.addMonitoringElement(element);

		notifyAddPort(port);
        
		if (element instanceof MonitoringElement) {
			notifyWatchesAdapterPortAdded(port);
		}
	}

	/**
	 * Removes the monitoring element.
	 * 
	 * @param element
	 *            the monitoring element
	 */
	public void removeMonitoringElement(MonitoringBaseElement element) {
		SystemMonitoringData data = getSystemMonitoringData(element.getPort().getSystem());
		
		data.removeMonitoringElement(element);
		
		if (element instanceof MonitoringElement) {
			notifyWatchesAdapterPortRemoved(element.getPort());
		}
		notifyRemovePort(element.getPort());
	}

	/**
	 * Contains port.
	 * 
	 * @param port
	 *            the port
	 * 
	 * @return true, if successful
	 */
	public boolean containsPort(IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return (null != getMonitoringElement(interfaceElement));
		}
		return false;
	}
	
	
	public Collection<MonitoringBaseElement> getElementsToMonitor(){
		List<MonitoringBaseElement> elements = new ArrayList<>();
		
		for (SystemMonitoringData data : systemMonitoringData.values()) {
			elements.addAll(data.getMonitoredElements());
		}
		return elements;
	}

	/**
	 * Enable system.
	 * 
	 * @param system
	 *            the system
	 */
	@Override
	public void enableSystem(String system) {
		// get system from the SystemManager
		AutomationSystem automationSystem = SystemManager.INSTANCE.getSystemForName(system);
		if (automationSystem == null) {
			Activator.getDefault().logError(system + " could not be found.");
			return;
		} 
		
		SystemMonitoringData systemData = getSystemMonitoringData(system);
		systemData.enableSystem();
	}


	/**
	 * Disable system.
	 * 
	 * @param system
	 *            the system
	 */
	@Override
	public void disableSystem(String system) {
		AutomationSystem automationSystem = SystemManager.INSTANCE.getSystemForName(system);
		if (automationSystem == null) {
			Activator.getDefault().logError(system + " could not be found for deactivating monitoring.");
			return;
		} 
		
		SystemMonitoringData systemData = getSystemMonitoringData(system);
		systemData.disableSystem();
	}

	/**
	 * Contains system.
	 * 
	 * @param system
	 *            the system
	 * 
	 * @return true, if successful
	 */
	public boolean monitoringForSystemEnabled(AutomationSystem system) {
		SystemMonitoringData data = getSystemMonitoringData(system);
		return data.monitoringForSystemEnabled();
	}

	public enum BreakPoint {
		add, remove, clear
	}

	/**
	 * Trigger event.
	 * 
	 * @param port
	 *            the port
	 */
	public void toggleBreakpoint(MonitoringBaseElement element, BreakPoint set) {
		if (element instanceof MonitoringElement){
			MonitoringElement monitoringElement = (MonitoringElement)element;

			SystemMonitoringData data = getSystemMonitoringData(monitoringElement.getPort().getSystem());
			TCPCommunicationObject commObject = data.getCommObject(monitoringElement.getPort().getDevice());
			if(null != commObject){
				commObject.toggleBreakpoint(monitoringElement, set);
				if (set.equals(BreakPoint.add)) {
					monitoringElement.setBreakpoint(true);
					breakpoints.getBreakpoints().add(monitoringElement);
				} else if (set.equals(BreakPoint.remove)) {
					monitoringElement.setBreakpoint(false);
					breakpoints.getBreakpoints().remove(monitoringElement);
				} else if (set.equals(BreakPoint.clear)) {
				}
			}
		}
	}

	/**
	 * Trigger event.
	 * 
	 * @param port
	 *            the port
	 */
	public void triggerEvent(IInterfaceElement interfaceElement) {
		MonitoringBaseElement element = getMonitoringElement(interfaceElement);
		
		if (element instanceof MonitoringElement)
		{
			MonitoringElement monitoringElement = (MonitoringElement)element;

			SystemMonitoringData data = getSystemMonitoringData(monitoringElement.getPort().getSystem());
			TCPCommunicationObject commObject = data.getCommObject(monitoringElement.getPort().getDevice());
			if(commObject != null){
				commObject.triggerEvent(monitoringElement);
				notifyTriggerEvent(monitoringElement.getPort());
			}
		} 
	}

	public void writeValue(MonitoringElement element, String value) {
		AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			Activator.getDefault().logError("System could not be found to write value (" + element.getPort() + ").");
			MessageDialog.openError(Display.getDefault().getActiveShell(),
					"Error", "System could not be found for write value ("
							+ element.getPort() + ").");
			return;
		}
		Device device = element.getPort().getDevice();
		if (device == null) {
			Activator.getDefault().logError("Device could not be found for write value (" + element.getPort() + ").");
			MessageDialog.openError(Display.getDefault().getActiveShell(),
					"Error", "Device could not be found for write value ("
							+ element.getPort() + ").");
			return;
		}
		
		TCPCommunicationObject commObject = getSystemMonitoringData(automationSystem).getCommObject(device);

		if(commObject != null){
			commObject.writeValue(element, value);
		}
	}
	

	public void forceValue(MonitoringElement element, String value) {
		AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			Activator.getDefault().logError("System could not be found to force value (" + element.getPort() + ").");
			MessageDialog.openError(Display.getDefault().getActiveShell(),
					"Error", "System could not be found for force value ("
							+ element.getPort() + ").");
			return;
		}
		Device device = element.getPort().getDevice();
		if (device == null) {
			Activator.getDefault().logError("Device could not be found for force value (" + element.getPort() + ").");
			MessageDialog.openError(Display.getDefault().getActiveShell(),
					"Error", "Device could not be found for force value ("
							+ element.getPort() + ").");
			return;
		}
		
		element.forceValue(value);
		TCPCommunicationObject commObject = getSystemMonitoringData(automationSystem).getCommObject(device);

		if(commObject != null){
			if (element.isForce()) {
				commObject.forceValue(element, value);
			} else {
				commObject.clearForce(element);				
			}
		}
	}

	public MonitoringBaseElement getMonitoringElementByPortString(String systemName, String portString) {
		SystemMonitoringData systemData = getSystemMonitoringData(systemName);
		if(null != systemData){
			return systemData.getMonitoringElementByPortString(portString);
		}
		return null;
	}

	private SystemMonitoringData getSystemMonitoringData(String systemName) {
		SystemMonitoringData retVal = systemMonitoringData.get(systemName);
		if(null == retVal){
			retVal = createSystemMonitoringData(
						SystemManager.INSTANCE.getSystemForName(systemName));
		}
		return retVal;
	}
	
	private SystemMonitoringData getSystemMonitoringData(AutomationSystem system) {
		SystemMonitoringData retVal = systemMonitoringData.get(system.getName());
		if(null == retVal){
			retVal = createSystemMonitoringData(system);
		}
		return retVal;
	}

	private SystemMonitoringData createSystemMonitoringData(AutomationSystem system) {
		SystemMonitoringData newData = new SystemMonitoringData(system);
		systemMonitoringData.put(system.getName(), newData);
		return newData;
	}
}
