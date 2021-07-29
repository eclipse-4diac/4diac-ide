/*******************************************************************************
 * Copyright (c) 2012 - 2018 Profactor GmbH, AIT, TU Wien ACIN, fortiss GmbH,
 * 							 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
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
import java.util.Set;

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
		return (MonitoringManager) AbstractMonitoringManager.getMonitoringManager();
	}

	/**
	 * Gets the monitoring element.
	 *
	 * @param port the port
	 *
	 * @return the monitoring element
	 */
	public MonitoringBaseElement getMonitoringElement(final IInterfaceElement port) {
		if (port != null) {
			
			SystemMonitoringData data = systemMonitoringData.get(port.getFBNetworkElement().getFbNetwork().getAutomationSystem());
			if (data != null) {
				return data.getMonitoredElement(port);
			}

		}
		return null;
	}

	/**
	 * Adds monitoring elements.
	 *
	 * @param elemeent the monitoring element
	 */
	public void addMonitoringElement(final MonitoringBaseElement element) {
		final PortElement port = element.getPort();
		final SystemMonitoringData data = getSystemMonitoringData(port.getSystem());

		data.addMonitoringElement(element);

		notifyAddPort(port);

		if (element instanceof MonitoringElement) {
			notifyWatchesAdapterPortAdded(port);
		}
	}

	/**
	 * Removes the monitoring element.
	 *
	 * @param element the monitoring element
	 */
	public void removeMonitoringElement(final MonitoringBaseElement element) {
		final SystemMonitoringData data = getSystemMonitoringData(element.getPort().getSystem());

		data.removeMonitoringElement(element);

		if (element instanceof MonitoringElement) {
			notifyWatchesAdapterPortRemoved(element.getPort());
		}
		notifyRemovePort(element.getPort());
	}

	/**
	 * Contains port.
	 *
	 * @param port the port
	 *
	 * @return true, if successful
	 */
	public boolean containsPort(final IInterfaceElement interfaceElement) {
		if (null != interfaceElement) {
			return (null != getMonitoringElement(interfaceElement));
		}
		return false;
	}

	public Collection<MonitoringBaseElement> getElementsToMonitor() {
		final List<MonitoringBaseElement> elements = new ArrayList<>();

		for (final SystemMonitoringData data : systemMonitoringData.values()) {
			elements.addAll(data.getMonitoredElements());
		}
		return elements;
	}

	/**
	 * Enable system.
	 *
	 * @param system the system
	 */
	@Override
	public void enableSystem(final AutomationSystem system) {
		getSystemMonitoringData(system).enableSystem();
	}

	@Override
	public void enableSystemSynch(final AutomationSystem system, final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		getSystemMonitoringData(system).enableSystemSynch(monitor);
	}

	/**
	 * Disable system.
	 *
	 * @param system the system
	 */
	@Override
	public void disableSystem(final AutomationSystem system) {
		final SystemMonitoringData data = systemMonitoringData.remove(system);
		notifyWatchesChanged();
		if (null != data) {
			data.disableSystem();
		}
	}

	@Override
	public void disableSystemSynch(final AutomationSystem system, final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		final SystemMonitoringData data = systemMonitoringData.remove(system);
		if (null != data) {
			data.disableSystemSynch(monitor);
		}
	}

	@Override
	public boolean isSystemMonitored(final AutomationSystem system) {
		return systemMonitoringData.containsKey(system);
	}

	public Set<AutomationSystem> getMonitoredSystems() {
		return systemMonitoringData.keySet();
	}

	/**
	 * Trigger event.
	 *
	 * @param port the port
	 */
	public void triggerEvent(final IInterfaceElement interfaceElement) {
		final MonitoringBaseElement element = getMonitoringElement(interfaceElement);

		if (element instanceof MonitoringElement) {
			final MonitoringElement monitoringElement = (MonitoringElement) element;

			final SystemMonitoringData data = getSystemMonitoringData(monitoringElement.getPort().getSystem());
			final IDeviceManagementInteractor devMgmInteractor = data
					.getDevMgmInteractor(monitoringElement.getPort().getDevice());
			if (devMgmInteractor != null) {
				try {
					devMgmInteractor.triggerEvent(monitoringElement);
				} catch (final DeploymentException e) {
					// TODO think if error should be shown to the user
					Activator.getDefault().logError("Could not trigger event for " + element.getQualifiedString(), e); //$NON-NLS-1$
				}
				notifyTriggerEvent(monitoringElement.getPort());
			}
		}
	}

	public void writeValue(final MonitoringElement element, final String value) {
		final AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			showSystemNotFoundErrorMsg(element);
			return;
		}
		final Device device = element.getPort().getDevice();
		if (device == null) {
			showDeviceNotFounderroMsg(element);
			return;
		}

		final IDeviceManagementInteractor devMgmInteractor = getSystemMonitoringData(automationSystem)
				.getDevMgmInteractor(device);

		if (devMgmInteractor != null) {
			String fullName = element.getQualifiedString();
			fullName = fullName.substring(0, fullName.lastIndexOf('.')); // strip interface name
			fullName = fullName.substring(0, fullName.lastIndexOf('.') + 1); // strip fbName

			final FBDeploymentData data = new FBDeploymentData(fullName, element.getPort().getFb());
			try {
				devMgmInteractor.writeFBParameter(element.getPort().getResource(), value, data,
						(VarDeclaration) element.getPort().getInterfaceElement());
			} catch (final DeploymentException e) {
				// TODO think if error should be shown to the user
				Activator.getDefault().logError("Could not write value to " + element.getQualifiedString(), e); //$NON-NLS-1$
			}
		}
	}

	public void forceValue(final MonitoringElement element, final String value) {
		final AutomationSystem automationSystem = element.getPort().getSystem();

		if (automationSystem == null) {
			showSystemNotFoundErrorMsg(element);
			return;
		}
		final Device device = element.getPort().getDevice();
		if (device == null) {
			showDeviceNotFounderroMsg(element);
			return;
		}

		element.forceValue(value);
		final IDeviceManagementInteractor devMgmInteractor = getSystemMonitoringData(automationSystem)
				.getDevMgmInteractor(device);

		if (devMgmInteractor != null) {
			try {
				if (element.isForce()) {
					devMgmInteractor.forceValue(element, value);
				} else {
					devMgmInteractor.clearForce(element);
				}
			} catch (final DeploymentException e) {
				// TODO think if error should be shown to the user
				Activator.getDefault()
				.logError("Could not force value of " + element.getQualifiedString() + "to " + value, e); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	public SystemMonitoringData getSystemMonitoringData(final AutomationSystem system) {
		return systemMonitoringData.computeIfAbsent(system, SystemMonitoringData::new);
	}

	private static void showDeviceNotFounderroMsg(final MonitoringElement element) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
				"Device could not be found for FB port: " + element.getPort() + ".");
	}

	private static void showSystemNotFoundErrorMsg(final MonitoringElement element) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), "Error",
				"System could not be found for FB port: " + element.getPort() + ".");
	}

}
