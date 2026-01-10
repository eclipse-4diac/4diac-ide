/*******************************************************************************
 * Copyright (c) 2025 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.simulator.forte;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.fordiac.ide.debug.replaydebugging.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.Utils;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IDeviceSimulator;
import org.eclipse.fordiac.ide.deployment.debug.Messages;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchData;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.libraryElement.Device;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

public class DeviceSimulator implements IDeviceSimulator {

	private final Device device;
	private final String path;
	private final IDeviceManagementExecutorService executorService;
	private Map<String, Set<String>> allPortsByResource = new HashMap<>();
	private final Map<Resource, ReplayNavigator.DatapointsState> currentStates = new HashMap<>();

	public DeviceSimulator(final IDeviceManagementExecutorService executorService, final Device device,
			final String path) {
		this.device = device;
		this.path = path;
		this.executorService = executorService;
	}

	@Override
	public Optional<String> replayNextEvent(final Resource resource) {
		try {
			final var lastEvent = executorService.replayNextEvent(resource);

			// read the current state after the event
			final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(executorService.readWatches());
			currentStates.put(resource, getWatchData(watchData, resource, allPortsByResource.get(resource.getName())));

			return lastEvent;
		} catch (final DeploymentException e) {
			e.printStackTrace();
			return Optional.empty();
		}

	}

	@Override
	public ReplayNavigator.DatapointsState getCurrentState(final Resource resource) {
		return currentStates.get(resource);
	}

	@Override
	public boolean start() {
		try {
			executorService.connect();
			executorService.readTraces(device, path);

			allPortsByResource = Utils.collectAllPorts(device);

			for (final Resource resource : device.getResource()) {
				for (final String portName : allPortsByResource.get(resource.getName())) {
					executorService.addWatch(resource, portName);
				}

				// Read initial state
				final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(executorService.readWatches());
				currentStates.put(resource,
						getWatchData(watchData, resource, allPortsByResource.get(resource.getName())));
			}
		} catch (final DeploymentException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean stop() {
		try {
			executorService.disconnect();
		} catch (final DeploymentException e) {
			FordiacLogHelper
					.logError(MessageFormat.format(Messages.DeploymentDebugDevice_ConnectError, device.getName()));
			return false;
		}
		return true;
	}

	/**
	 * @brief It reads the data from a watchData response from the device
	 *        (DeploymentDebugWatchData), gets the values of all datapoints in
	 *        portNames and stores DatapointState.
	 *
	 * @return a map with the port names as keys and their values as values.
	 */
	private static ReplayNavigator.DatapointsState getWatchData(final DeploymentDebugWatchData data,
			final Resource resource, final Set<String> portNames) {
		final ReplayNavigator.DatapointsState result = new ReplayNavigator.DatapointsState();
		for (final String portName : portNames) {
			final Data portData = data.getLastData(resource, portName);
			if (portData == null) {
				// if there is no data for this port, we just skip it
				// this means that the forte device could not add the watch
				continue;
			}
			result.put(portName, portData.getValue());
		}
		return result;
	}

}
