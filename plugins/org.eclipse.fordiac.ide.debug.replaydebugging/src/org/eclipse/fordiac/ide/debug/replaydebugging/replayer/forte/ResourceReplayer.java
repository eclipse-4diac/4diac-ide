/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
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

package org.eclipse.fordiac.ide.debug.replaydebugging.replayer.forte;

import java.util.Optional;
import java.util.Set;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IResourceReplayer;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchData;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;

public class ResourceReplayer implements IResourceReplayer {

	private final IDeviceManagementExecutorService executorService;
	private final Set<String> allPorts;
	private DatapointsState currentState;
	private final Resource resource;

	public ResourceReplayer(final IDeviceManagementExecutorService executorService, final Resource resource)
			throws DeploymentException {
		this.executorService = executorService;
		this.resource = resource;
		allPorts = Utils.collectAllValueHolders(resource);
		for (final String portName : allPorts) {
			executorService.addWatch(resource, portName);
		}
		readWatchesIntoState(); // get the initial state
	}

	@Override
	public Optional<String> replayNextEvent() {
		try {
			final var lastEvent = executorService.replayNextEvent(resource);
			readWatchesIntoState();
			return lastEvent;
		} catch (final DeploymentException e) {
			FordiacLogHelper.logError("Error in processing next event!", e); //$NON-NLS-1$
			return Optional.empty();
		}
	}

	@Override
	public DatapointsState getCurrentState() {
		return currentState;
	}

	private void readWatchesIntoState() throws DeploymentException {
		final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(executorService.readWatches());
		transformWatchDataIntoCurrentState(watchData, resource, allPorts);
	}

	/**
	 * @brief It reads the data from a watchData response from the device
	 *        (DeploymentDebugWatchData), gets the values of all datapoints in
	 *        portNames and stores DatapointState.
	 *
	 * @return a map with the port names as keys and their values as values.
	 */
	private void transformWatchDataIntoCurrentState(final DeploymentDebugWatchData data, final Resource resource,
			final Set<String> portNames) {
		currentState = new DatapointsState();
		for (final String portName : portNames) {
			final Data portData = data.getLastData(resource, portName);
			if (portData == null) {
				// if there is no data for this port, we just skip it
				// this means that the forte device could not add the watch
				continue;
			}
			currentState.put(portName, portData.getValue());
		}
	}

	@Override
	public void injectEvent(final String name) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCurrentState(final DatapointsState targetState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void forceValue(final String name, final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearForce(final String name) {
		// TODO Auto-generated method stub

	}
}
