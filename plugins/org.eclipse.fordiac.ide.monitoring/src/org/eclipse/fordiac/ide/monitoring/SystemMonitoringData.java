/*******************************************************************************
 * Copyright (c) 2016 - 2018 fortiss GmbH, Johannes Kepler University
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Alois Zoitl - Harmonized deployment and monitoring
 *   Michael Oberlehner - added subapp monitoring
 *******************************************************************************/
package org.eclipse.fordiac.ide.monitoring;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementInteractor;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.deployment.monitoringbase.PortElement;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringElement;
import org.eclipse.fordiac.ide.model.monitoring.SubAppPortElement;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;
import org.eclipse.fordiac.ide.monitoring.model.SubAppPortHelper;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SystemMonitoringData {

	private final AutomationSystem system;

	private final Map<IInterfaceElement, MonitoringBaseElement> monitoredElements = new HashMap<>();
	private final Map<String, MonitoringBaseElement> monitoredElementsPerPortStrings = new HashMap<>();

	private final Map<String, List<MonitoringElement>> subappElements = new HashMap<>();

	private boolean monitoringEnabled = false;

	public Map<String, List<MonitoringElement>> getSubappElements() {
		return subappElements;
	}

	private final Map<Device, DeviceMonitoringHandler> deviceHandlers = new HashMap<>();

	public SystemMonitoringData(final AutomationSystem system) {
		this.system = system;
	}

	AutomationSystem getSystem() {
		return system;
	}

	Collection<MonitoringBaseElement> getMonitoredElements() {
		return monitoredElements.values();
	}

	DeviceMonitoringHandler getDevMonitoringHandler(final Device dev) {
		return deviceHandlers.get(dev);
	}

	void addDevMonitoringHandler(final Device dev, final DeviceMonitoringHandler handler) {
		deviceHandlers.put(dev, handler);
	}

	Map<Device, DeviceMonitoringHandler> getDevMonitoringHandlers() {
		return deviceHandlers;
	}

	void removeDeviceMonitoringHandler(final Device dev) {
		deviceHandlers.remove(dev);
	}

	public void enableSystem() {
		final EnableSystemMonitoringRunnable enable = new EnableSystemMonitoringRunnable(this);
		final Shell shell = Display.getDefault().getActiveShell();
		try {
			new ProgressMonitorDialog(shell).run(true, true, enable);
			monitoringEnabled = true;
		} catch (final InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt(); // mark interruption
			MessageDialog.openInformation(shell, "Enable Monitoring Aborted", "Enable Monitoring Aborted");
		}
	}

	public void enableSystemSynch(final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		final EnableSystemMonitoringRunnable enable = new EnableSystemMonitoringRunnable(this);
		enable.run(monitor);
		monitoringEnabled = true;
	}

	public void disableSystem() {
		final DisableSystemMonitoringRunnable disable = new DisableSystemMonitoringRunnable(this);
		final Shell shell = Display.getDefault().getActiveShell();
		try {
			new ProgressMonitorDialog(shell).run(true, true, disable);
			monitoringEnabled = false;
		} catch (final InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt(); // mark interruption
			MessageDialog.openInformation(shell, "Disable Monitoring Aborted", "Disable Monitoring Aborted");
		}
	}

	public void disableSystemSynch(final IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		final DisableSystemMonitoringRunnable disable = new DisableSystemMonitoringRunnable(this);
		monitoringEnabled = false;
		disable.run(monitor);
	}

	public MonitoringBaseElement getMonitoringElementByPortString(final String portString) {
		return monitoredElementsPerPortStrings.get(portString);
	}

	public void sendRemoveWatch(final MonitoringBaseElement element) {
		final IDeviceManagementInteractor devMgmInteractor = getDevMgmInteractor(element.getPort().getDevice());
		if (null != devMgmInteractor && devMgmInteractor.isConnected()) {
			try {
				devMgmInteractor.removeWatch(element);
			} catch (final DeploymentException e) {
				// TODO think if error should be shown to the user
				FordiacLogHelper.logError("Could not remove watch for " + element.getQualifiedString(), e); //$NON-NLS-1$
			}
		}
	}

	public void sendAddWatch(final MonitoringBaseElement element) {
		final IDeviceManagementInteractor devMgmInteractor = getDevMgmInteractor(element.getPort().getDevice());
		if (null != devMgmInteractor && devMgmInteractor.isConnected()) {
			try {
				devMgmInteractor.addWatch(element);
			} catch (final DeploymentException e) {
				// TODO think if error should be shown to the user
				FordiacLogHelper.logError("Could not add watch for " + element.getQualifiedString(), e); //$NON-NLS-1$
			}
		}
	}

	public IDeviceManagementInteractor getDevMgmInteractor(final Device device) {
		final DeviceMonitoringHandler handler = getDevMonitoringHandler(device);
		return (null != handler) ? handler.getDevMgmInteractor() : null;
	}

	public boolean isMonitoringForSystemEnabled() {
		return monitoringEnabled;
	}

	public void removeMonitoringElement(final MonitoringBaseElement element) {
		final PortElement port = element.getPort();

		if (element instanceof MonitoringElement) {
			sendRemoveWatch(element);
		}
		monitoredElements.remove(port.getInterfaceElement());
		monitoredElementsPerPortStrings.remove(port.getPortString());
		handleSubappElements(element, port);
	}

	public void handleSubappElements(final MonitoringBaseElement element, final PortElement port) {
		if (subappElements.containsKey(port.getPortString())) {
			removeSubappElement(element, port.getPortString());
		}
		if (element instanceof final SubappMonitoringElement sme) {
			removeSubappElement(element, sme.getAnchor().getPort().getPortString());
		}
	}

	public void removeSubappElement(final MonitoringBaseElement element, final String portString) {
		if (getSubappElements().containsKey(portString)) {
			final List<MonitoringElement> subappPins = getSubappElements().get(portString);
			final boolean remove = subappPins.remove(element);
			Assert.isTrue(remove);
			if (subappPins.isEmpty()) {
				getSubappElements().remove(portString);
			}
		}
	}

	public void addMonitoringElement(final MonitoringBaseElement element) {
		final PortElement port = element.getPort();
		monitoredElements.put(port.getInterfaceElement(), element);

		if (port instanceof SubAppPortElement) {
			final String portString = SubAppPortHelper.findConnectedMonitoredSubappPort(port.getInterfaceElement(),
					getSubappElements());
			if (portString != null) {
				addToSubappGroup(element, portString);
			}
			addSubappMonitoringElement(element);
		} else {
			addToSubappGroup(element, port);
			handleConnectedSubappPorts(element);
			monitoredElementsPerPortStrings.put(port.getPortString(), element);
		}

		if (element instanceof MonitoringElement) {
			sendAddWatch(element);
		}
	}

	public void addSubappMonitoringElement(final MonitoringBaseElement element) {
		final PortElement anchor = ((SubappMonitoringElement) element).getAnchor().getPort();
		final String portString = anchor.getPortString();
		if (!addToSubappGroup(element, anchor)) {
			createNewSubappGroup(element, portString);
		}
	}

	public boolean addToSubappGroup(final MonitoringBaseElement element, final PortElement port) {
		// This element has not been created, but there exists a dummy subapp port
		return addToSubappGroup(element, port.getPortString());
	}

	public boolean addToSubappGroup(final MonitoringBaseElement element, final String portString) {
		if (getSubappElements().containsKey(portString)) {
			final List<MonitoringElement> subappPins = getSubappElements().get(portString);
			subappPins.add((MonitoringElement) element);
			return true;
		}

		return false;
	}

	public void createNewSubappGroup(final MonitoringBaseElement element, final String portString) {
		final List<MonitoringElement> l = new ArrayList<>();
		l.add((SubappMonitoringElement) element);
		getSubappElements().put(portString, l);
		addExistingElementToSubappGroup(portString, l);
	}

	public void addExistingElementToSubappGroup(final String portString, final List<MonitoringElement> l) {
		final MonitoringBaseElement monitoringBaseElement = monitoredElementsPerPortStrings.get(portString);
		if (monitoringBaseElement != null) {
			l.add((MonitoringElement) monitoringBaseElement);
		}
	}

	private void handleConnectedSubappPorts(final MonitoringBaseElement element) {

		final FBNetworkElement fb = element.getPort().getFb();
		if (!fb.isNestedInSubApp()) {
			return;
		}
		final IInterfaceElement interfaceElement = element.getPort().getInterfaceElement();

		final String findConnectedMonitoredSubappPort = SubAppPortHelper
				.findConnectedMonitoredSubappPort(interfaceElement, getSubappElements());

		if (findConnectedMonitoredSubappPort != null) {
			final List<MonitoringElement> list = subappElements.get(findConnectedMonitoredSubappPort);

			if (!list.contains(element)) {
				list.add((MonitoringElement) element);
			}
		}

	}

	public MonitoringBaseElement getMonitoredElement(final IInterfaceElement port) {
		return monitoredElements.get(port);
	}

	public Entry<String, List<MonitoringElement>> getSubappElements(final MonitoringBaseElement m) {
		for (final Entry<String, List<MonitoringElement>> e : getSubappElements().entrySet()) {
			for (final MonitoringElement me : e.getValue()) {
				if (me.equals(m)) {
					return e;
				}
			}
		}
		return null;
	}

	/**
	 * Check if all port strings are still the same as when they where added to the
	 * map.
	 *
	 * Differences can occur when blocks or subapps are renamed and the application
	 * is redeployed.
	 */
	public void updatePortStringMapping() {
		monitoredElementsPerPortStrings.entrySet().stream()
				.filter(entry -> !entry.getKey().equals(entry.getValue().getPort().getPortString())).forEach(entry -> {
					final MonitoringBaseElement value = entry.getValue();
					monitoredElementsPerPortStrings.remove(entry.getKey());
					monitoredElementsPerPortStrings.put(value.getPort().getPortString(), value);
				});
	}

}
