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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;
import org.eclipse.fordiac.ide.monitoring.preferences.PreferenceConstants;
import org.eclipse.fordiac.ide.systemmanagement.Activator;

class DeviceMonitoringHandler implements Runnable {

	private final Device device;
	private final IDeviceManagementInteractor devInteractor;
	private final SystemMonitoringData systemMonData;
	private final Thread thread;

	private boolean running = true;

	private synchronized void setRunning(final boolean val) {
		running = val;
	}

	private synchronized boolean isRunning() {
		return running;
	}

	public DeviceMonitoringHandler(final Device device, final SystemMonitoringData systemMonData) {
		this.device = device;
		devInteractor = DeviceManagementInteractorFactory.INSTANCE.getDeviceManagementInteractor(device);
		this.systemMonData = systemMonData;
		this.thread = new Thread(this);
	}

	public Thread getThread() {
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
			final int pollingIntervall = PreferenceConstants.getPollingInterval();
			while (isRunning()) {
				try {
					Thread.sleep(pollingIntervall);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();  // mark interruption
					Activator.getDefault().logError(e.getMessage(), e);
				}
				if (devInteractor.isConnected()) {
					try {
						final Response resp = devInteractor.readWatches();
						if (null != resp) {
							updateWatches(resp);
						}
					} catch (final DeploymentException e) {
						handleDeviceIssue();
					}
				} else {
					setRunning(false);
				}

			}
		}
	}

	private void updateWatches(final Response resp) {
		if (resp.getWatches() != null) {
			for (final org.eclipse.fordiac.ide.deployment.devResponse.Resource res : resp.getWatches().getResources()) {
				final String resName = device.getName() + "." + res.getName() + "."; //$NON-NLS-1$ //$NON-NLS-2$
				updateFbs(res, resName);
			}
		}
	}



	public void updateFbs(final org.eclipse.fordiac.ide.deployment.devResponse.Resource res, final String resName) {

		final HashMap<String, SubappGroup> collectedGroups = new HashMap<>();

		for (final FB fb : res.getFbs()) {
			final String fbName = resName + fb.getName() + "."; //$NON-NLS-1$
			for (final Port port : fb.getPorts()) {
				String portString = fbName + port.getName();
				final MonitoringBaseElement element = systemMonData.getMonitoringElementByPortString(portString);

				List<MonitoringElement> subappPins = systemMonData.getSubappElements().get(portString);
				final Entry<String, List<MonitoringElement>> subappEntry = systemMonData.getSubappElements(element);

				if (element != null && subappPins == null && subappEntry != null) {
					subappPins = subappEntry.getValue();
					portString = subappEntry.getKey();
				}

				if (subappPins != null) {
					SubappGroup subappGroup = null;
					if (collectedGroups.containsKey(portString)) {
						subappGroup = collectedGroups.get(portString);

					} else {
						// create new group
						subappGroup = new SubappGroup(subappPins);
						collectedGroups.put(portString, subappGroup);
					}
					if (element != null) {
						subappGroup.assignPort((MonitoringElement) element, port);
					}
					subappGroup.assignSubappPorts(port, portString);

				} else {
					if (element instanceof MonitoringElement) {
						updateMonitoringElement((MonitoringElement) element, port);
					}
				}
			}
		}

		updateSubappPorts(collectedGroups);
	}

	private static void updateSubappPorts(final Map<String, SubappGroup> collectedGroups) {
		for (final SubappGroup subappGroup : collectedGroups.values()) {
			updateSubAppGroup(subappGroup);
		}

	}

	private static void updateSubAppGroup(final SubappGroup subappGroup) {
		// if the anchors have inconsistent values we need to display that
		boolean inconsistent = false;
		String currentVal = ""; //$NON-NLS-1$

		for (final MonitoringElement monitoringElement : subappGroup.collectedSubappPins) {
			final Port port = subappGroup.getPort(monitoringElement);
			updateMonitoringElement(monitoringElement, port);
			inconsistent = isInconsistent(currentVal, monitoringElement);
			currentVal = monitoringElement.getCurrentValue();
		}

		if (inconsistent) {
			setSubappPinsToInconsistentState(subappGroup);
		}

	}

	public static boolean isInconsistent(final String currentVal, final MonitoringElement monitoringElement) {
		return !currentVal.isEmpty() && !(monitoringElement instanceof SubappMonitoringElement)
				&& !currentVal.equals(monitoringElement.getCurrentValue());
	}

	private static void setSubappPinsToInconsistentState(final SubappGroup subappGroup) {
		subappGroup.collectedSubappPins.stream().filter(SubappMonitoringElement.class::isInstance)
		.forEach(e -> e.setCurrentValue("?")); //$NON-NLS-1$

	}

	private static void updateMonitoringElement(final MonitoringElement monitoringElement, final Port p) {

		for (final Data d : p.getDataValues()) {
			long timeAsLong = 0;
			try {
				timeAsLong = Long.parseLong(d.getTime());
			} catch (final NumberFormatException nfe) {
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
		} catch (final DeploymentException e) {
			// we don't need to do anything here
		}
		systemMonData.getMonitoredElements().stream()
		.filter(el -> (el.getPort().getDevice().equals(device) && (el instanceof MonitoringElement)))
		.forEach(el -> ((MonitoringElement) el).setCurrentValue("")); //$NON-NLS-1$
	}

	public static class SubappGroup {

		List<MonitoringElement> collectedSubappPins;
		HashMap<MonitoringElement, Port> pins = new HashMap<>();

		public SubappGroup(final List<MonitoringElement> collectedSubappPins) {
			this.collectedSubappPins = collectedSubappPins;
		}

		public Port getPort(final MonitoringElement e) {
			return pins.get(e);
		}

		public void assignPort(final MonitoringElement e, final Port p) {
			for (final MonitoringElement monitoringElement : collectedSubappPins) {
				if (monitoringElement instanceof SubappMonitoringElement
						&& ((SubappMonitoringElement) monitoringElement).getAnchor().equals(e)) {
					pins.put(monitoringElement, p);
				}
			}
			pins.put(e, p);
		}

		public void assignSubappPorts(final Port p, final String portString) {
			for (final MonitoringElement monitoringElement : collectedSubappPins) {
				if (monitoringElement instanceof SubappMonitoringElement) {
					final MonitoringBaseElement anchor = ((SubappMonitoringElement) monitoringElement).getAnchor();
					if (anchor.getPort().getPortString().equals(portString) && !pins.containsKey(monitoringElement)) {
						pins.put(monitoringElement, p);
					}
				}
			}
		}

	}

}