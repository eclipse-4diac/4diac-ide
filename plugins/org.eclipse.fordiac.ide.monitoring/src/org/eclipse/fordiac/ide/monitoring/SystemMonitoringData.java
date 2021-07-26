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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SystemMonitoringData {

	private final AutomationSystem system;

	private final Map<IInterfaceElement, MonitoringBaseElement> monitoredElements = new HashMap<>();
	private final Map<String, MonitoringBaseElement> monitoredElementsPerPortStrings = new HashMap<>();

	private final Map<String, List<MonitoringElement>> subappElements = new HashMap<>();

	protected Map<String, List<MonitoringElement>> getSubappElements() {
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
			new ProgressMonitorDialog(Display.getDefault().getActiveShell()).run(true, true, enable);
		} catch (final InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt();  // mark interruption
			MessageDialog.openInformation(shell, "Enable Monitoring Aborted", "Enable Monitoring Aborted");
		}
	}

	public void enableSystemSynch(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		final EnableSystemMonitoringRunnable enable = new EnableSystemMonitoringRunnable(this);
		enable.run(monitor);
	}

	public void disableSystem() {
		final DisableSystemMonitoringRunnable disable = new DisableSystemMonitoringRunnable(this);
		final Shell shell = Display.getDefault().getActiveShell();
		try {
			new ProgressMonitorDialog(shell).run(true, true, disable);
		} catch (final InvocationTargetException ex) {
			MessageDialog.openError(shell, "Error", ex.getMessage());
		} catch (final InterruptedException ex) {
			Thread.currentThread().interrupt();  // mark interruption
			MessageDialog.openInformation(shell, "Disable Monitoring Aborted", "Disable Monitoring Aborted");
		}
	}

	public void disableSystemSynch(final IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		final DisableSystemMonitoringRunnable disable = new DisableSystemMonitoringRunnable(this);
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
				Activator.getDefault().logError("Could not remove watch for " + element.getQualifiedString(), e); //$NON-NLS-1$
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
				Activator.getDefault().logError("Could not add watch for " + element.getQualifiedString(), e); //$NON-NLS-1$
			}
		}
	}

	public IDeviceManagementInteractor getDevMgmInteractor(final Device device) {
		final DeviceMonitoringHandler handler = getDevMonitoringHandler(device);
		return (null != handler) ? handler.getDevMgmInteractor() : null;
	}

	public void removeMonitoringElement(final MonitoringBaseElement element) {
		final PortElement port = element.getPort();

		if (element instanceof MonitoringElement) {
			sendRemoveWatch(element);
		}
		monitoredElements.remove(port.getInterfaceElement());
		monitoredElementsPerPortStrings.remove(port.getPortString());

		if (subappElements.containsKey(port.getPortString())) {
			final List<MonitoringElement> subappPins = subappElements.get(port.getPortString());
			final boolean remove = subappPins.remove(element);
			Assert.isTrue(remove);
		}


	}

	public void addMonitoringElement(final MonitoringBaseElement element) {
		final PortElement port = element.getPort();

		monitoredElements.put(port.getInterfaceElement(), element);

		if (port instanceof SubAppPortElement) {
			final PortElement anchor = ((SubappMonitoringElement) element).getAnchor().getPort();
			final String portString = anchor.getPortString();
			if (subappElements.containsKey(portString)) {
				final List<MonitoringElement> subappPins = subappElements.get(portString);
				subappPins.add((MonitoringElement) element);
			} else {
				final List<MonitoringElement> l = new ArrayList<>();
				l.add((SubappMonitoringElement) element);
				subappElements.put(portString, l);
			}

		} else {

			// This element has not been created, but there exists a dummy subapp port
			if (subappElements.containsKey(port.getPortString())) {
				final List<MonitoringElement> subappPins = subappElements.get(port.getPortString());
				subappPins.add((MonitoringElement) element);
			}
			// TODO add this handleConnectedSubappPorts(element);

			monitoredElementsPerPortStrings.put(port.getPortString(), element);
		}

		if (element instanceof MonitoringElement) {
			sendAddWatch(element);
		}
	}

	private void handleConnectedSubappPorts(final MonitoringBaseElement element) {

		final FBNetworkElement fb = element.getPort().getFb();
		if (!fb.isNestedInSubApp()) {
			return;
		}
		final IInterfaceElement interfaceElement = element.getPort().getInterfaceElement();

		final String findConnectedMonitoredSubappPort = SubAppPortHelper
				.findConnectedMonitoredSubappPort(interfaceElement, subappElements);

		if (findConnectedMonitoredSubappPort != null) {
			subappElements.get(findConnectedMonitoredSubappPort).add((MonitoringElement) element);
		}

	}

	public MonitoringBaseElement getMonitoredElement(final IInterfaceElement port) {
		return monitoredElements.get(port);
	}

}
