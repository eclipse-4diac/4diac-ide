/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, AIT, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Matthias Plasch, Filip Andren, Alois Zoitl, Gerd Kainz
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring  
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.deployment.data.FBDeploymentData;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.AbstractMonitoringManager;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;


/**
 * Singleton instance that Coordinates and manages all the Ports to be
 * monitored.
 */
public class MonitoringManager extends AbstractMonitoringManager {
	
	private final Map<AutomationSystem, SystemMonitoringData> systemMonitoringData = new HashMap<>();

	/**
	 * Gets the single instance of MonitoringManager.
	 * 
	 * @return single instance of MonitoringManager
	 */
	public static MonitoringManager getInstance() {
		return (MonitoringManager)AbstractMonitoringManager.getMonitoringManager();
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
	public void enableSystem(AutomationSystem system) {
		getSystemMonitoringData(system).enableSystem();
	}
	
	@Override
	public void enableSystemSynch(AutomationSystem system, IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		getSystemMonitoringData(system).enableSystemSynch(monitor);		
	}


	/**
	 * Disable system.
	 * 
	 * @param system
	 *            the system
	 */
	@Override
	public void disableSystem(AutomationSystem system) {
		getSystemMonitoringData(system).disableSystem();
	}
	
	@Override
	public void disableSystemSynch(AutomationSystem system, IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		getSystemMonitoringData(system).disableSystemSynch(monitor);	
	}
	
	@Override
	public boolean isSystemMonitored(AutomationSystem system) {
		return getSystemMonitoringData(system).isMonitoringForSystemEnabled();
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
		return data.isMonitoringForSystemEnabled();
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
			IDeviceManagementInteractor devMgmInteractor = data.getDevMgmInteractor(monitoringElement.getPort().getDevice());
			if(devMgmInteractor != null){
				try {
					devMgmInteractor.triggerEvent(monitoringElement);
				} catch (DeploymentException e) {
					// TODO think if error should be shown to the user
					Activator.getDefault().logError("Could not trigger event for " + element.getQualifiedString(), e);
				}
				notifyTriggerEvent(monitoringElement.getPort());
			}
		} 
	}

	public void writeValue(MonitoringElement element, String value) {
		AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			showSystemNotFoundErrorMsg(element);
			return;
		}
		Device device = element.getPort().getDevice();
		if (device == null) {
			showDeviceNotFounderroMsg(element);
			return;
		}
		
		IDeviceManagementInteractor devMgmInteractor = getSystemMonitoringData(automationSystem).getDevMgmInteractor(device);

		if(devMgmInteractor != null){
			String fullName = element.getQualifiedString();
			fullName = fullName.substring(0, fullName.lastIndexOf('.')); // strip interface name
			fullName = fullName.substring(0, fullName.lastIndexOf('.') +1 ); // strip fbName
			
			FBDeploymentData data = new FBDeploymentData(fullName, element.getPort().getFb());
			try {
				devMgmInteractor.writeFBParameter(element.getPort().getResource(), value, data, (VarDeclaration)element.getPort().getInterfaceElement());
			} catch (DeploymentException e) {
				// TODO think if error should be shown to the user
				Activator.getDefault().logError("Could not write value to " + element.getQualifiedString(), e);
			}
		}
	}

	public void forceValue(MonitoringElement element, String value) {
		AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			showSystemNotFoundErrorMsg(element);
			return;
		}
		Device device = element.getPort().getDevice();
		if (device == null) {
			showDeviceNotFounderroMsg(element);
			return;
		}
		
		element.forceValue(value);
		IDeviceManagementInteractor devMgmInteractor = getSystemMonitoringData(automationSystem).getDevMgmInteractor(device);

		if(devMgmInteractor != null){
			try {
				if (element.isForce()) {
						devMgmInteractor.forceValue(element, value);
				} else {
					devMgmInteractor.clearForce(element);				
				}
			} catch (DeploymentException e) {
				// TODO think if error should be shown to the user
				Activator.getDefault().logError("Could not force value of " + element.getQualifiedString() + "to " + value, e);
			}
		}
	}
	
	public SystemMonitoringData getSystemMonitoringData(AutomationSystem system) {
		SystemMonitoringData retVal = systemMonitoringData.get(system);
		if(null == retVal){
			retVal = createSystemMonitoringData(system);
		}
		return retVal;
	}

	private SystemMonitoringData createSystemMonitoringData(AutomationSystem system) {
		SystemMonitoringData newData = new SystemMonitoringData(system);
		systemMonitoringData.put(system, newData); 
		return newData;
	}
	
	private static void showDeviceNotFounderroMsg(MonitoringElement element) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "Device could not be found for FB port: " + element.getPort() + ".");
	}

	private static void showSystemNotFoundErrorMsg(MonitoringElement element) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", "System could not be found for FB port: " + element.getPort() + ".");
	}
	

}
