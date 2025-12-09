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
package org.eclipse.fordiac.ide.debug.replaydebugging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.deployment.debug.watch.DeploymentDebugWatchData;
import org.eclipse.fordiac.ide.deployment.devResponse.Data;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
import org.eclipse.fordiac.ide.deployment.interactors.IDeviceManagementExecutorService;
import org.eclipse.fordiac.ide.model.libraryElement.Resource;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * @brief Handles the replay navigator of a resource and transforms the data
 *        from it into a response
 *
 *        This class is responsible for managing the replay navigator state,
 *        getting all the states of the replay and updating the response data
 *        accordingly.
 */
public class ReplayDebuggingResource implements ReplayNavigator.StateListener {

	/**
	 * @brief Listener interface for updates on the replay navigator.
	 */
	@FunctionalInterface
	public interface UpdateListener {
		void onUpdate(ReplayDebuggingResource replayDebuggingResource);
	}

	private ReplayNavigator replayNavigator;

	private final ReplayNavigator.Identifier replayNavigatorIdentifier;

	private final IDeviceManagementExecutorService deviceManagementExecutorService;

	private final Resource resource;

	// port names beloging to the resource
	private final Set<String> portNames;

	private final UpdateListener updateListener;

	// response data for the resources being replayed
	org.eclipse.fordiac.ide.deployment.devResponse.Resource resourceResponse = DevResponseFactory.eINSTANCE
			.createResource();

	// map from the datapoints coming from the replay navigator to the data values
	// in the response
	private final Map<String, org.eclipse.fordiac.ide.deployment.devResponse.Data> dataValues = new HashMap<>();

	public ReplayDebuggingResource(final Resource resource, final Set<String> portNames,
			final ReplayNavigator.Identifier reaplayNavigatorIdentifier,
			final IDeviceManagementExecutorService deviceManagementExecutorService,
			final UpdateListener updateListener) {
		this.replayNavigatorIdentifier = reaplayNavigatorIdentifier;
		this.deviceManagementExecutorService = deviceManagementExecutorService;
		this.resource = resource;
		this.portNames = portNames;
		this.updateListener = updateListener;
	}

	public org.eclipse.fordiac.ide.deployment.devResponse.Resource getResourceResponse() {
		return resourceResponse;
	}

	public EventChange getCurrentEventChange() {
		return replayNavigator.getCurrentEventChange();
	}

	public void load() throws DeploymentException {
		createReplayNavigator();
	}

	public void unload() {
		replayNavigator.removeStateChangeListener(this);
		ReplayNavigatorManager.getDefault().unregisterNavigator(replayNavigator);
	}

	private void createReplayNavigator() throws DeploymentException {
		for (final String portName : portNames) {
			deviceManagementExecutorService.addWatch(resource, portName);
		}

		// Read initial state
		DeploymentDebugWatchData watchData;
		watchData = new DeploymentDebugWatchData(deviceManagementExecutorService.readWatches());

		final ReplayNavigator.DatapointsState initialState = new ReplayNavigator.DatapointsState();
		initialState.putAll(getWatchData(watchData, resource, portNames));

		createResourceResponse(initialState);

		final List<EventChange> eventChanges = iterateOverAllEvents(initialState);
		replayNavigator = new ReplayNavigator(replayNavigatorIdentifier, initialState, eventChanges);
		replayNavigator.addStateChangeListener(this);
		ReplayNavigatorManager.getDefault().registerNavigator(replayNavigator);
	}

	/**
	 * @brief It reads the data from a watchData response from the device
	 *        (DeploymentDebugWatchData), gets the values of all datapoints in
	 *        portNames and stores it in a map for the replay navigator.
	 *
	 * @return a map with the port names as keys and their values as values.
	 */
	private HashMap<String, String> getWatchData(final DeploymentDebugWatchData data, final Resource resource,
			final Set<String> portNames) {
		final HashMap<String, String> result = new HashMap<>();
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

	/**
	 * @brief Creates the resource response based on the initial state of the replay
	 *        navigator.
	 *
	 *        This method iterates over the initial state of the replay navigator,
	 *        creates FBs and ports in the response, and stores the data values for
	 *        each datapoint.
	 *
	 * @param initialState The initial state of the replay navigator containing
	 *                     datapoints and their values.
	 */
	private void createResourceResponse(final ReplayNavigator.DatapointsState initialState) {
		resourceResponse.setName(resource.getName());

		// handle FBs in the response
		final EList<org.eclipse.fordiac.ide.deployment.devResponse.FB> responseFBs = resourceResponse.getFbs();

		// list of already added FBs
		final HashMap<String, org.eclipse.fordiac.ide.deployment.devResponse.FB> existingResponseFBs = new HashMap<>();
		for (final Map.Entry<String, String> entry : initialState.entrySet()) {
			final String datapoint = entry.getKey();
			final String value = entry.getValue();

			final int lastDot = datapoint.lastIndexOf('.');
			final String fbName = datapoint.substring(0, lastDot);
			final String portName = datapoint.substring(lastDot + 1);

			org.eclipse.fordiac.ide.deployment.devResponse.FB responseFB;
			if (!existingResponseFBs.containsKey(fbName)) {
				// create response FB if first time seeing it
				responseFB = DevResponseFactory.eINSTANCE.createFB();
				responseFB.setName(fbName);
				responseFBs.add(responseFB);

				// add it to the list of existing FBs
				existingResponseFBs.put(fbName, responseFB);
			} else {
				responseFB = existingResponseFBs.get(fbName);
			}
			final EList<org.eclipse.fordiac.ide.deployment.devResponse.Port> responseFBPorts = responseFB.getPorts();
			final org.eclipse.fordiac.ide.deployment.devResponse.Port responsePort = DevResponseFactory.eINSTANCE
					.createPort();
			responsePort.setName(portName);
			responseFBPorts.add(responsePort);

			final EList<org.eclipse.fordiac.ide.deployment.devResponse.Data> responsePortDataValues = responsePort
					.getDataValues();
			final org.eclipse.fordiac.ide.deployment.devResponse.Data dataValuesValue = DevResponseFactory.eINSTANCE
					.createData();
			dataValuesValue.setValue(value);
			responsePortDataValues.add(dataValuesValue);

			// store the quick access to the data values
			dataValues.put(datapoint, dataValuesValue);
		}
	}

	/**
	 * @brief Iterates over all events in the replay navigator and gathers the data
	 *        changes.
	 *
	 *        This method simulates all events, collects the data changes for each
	 *        event, and returns a vector of EventChange objects.
	 *
	 * @param initialState The initial state of the replay navigator containing
	 *                     datapoints and their values.
	 * @return A list of EventChange objects representing the changes in data points
	 *         for each event.
	 *
	 * @throws DeploymentException If an error occurs during the replay of events.
	 */
	private List<EventChange> iterateOverAllEvents(final ReplayNavigator.DatapointsState initialState)
			throws DeploymentException {
		// simulate all events and gather all data
		int eventCounter = 0;
		final List<EventChange> eventChanges = new ArrayList<>();
		Map<String, String> previousState = new HashMap<>(initialState);

		for (Optional<String> lastEvent = deviceManagementExecutorService.replayNextEvent(resource); lastEvent
				.isPresent(); lastEvent = deviceManagementExecutorService.replayNextEvent(resource)) {
			final DeploymentDebugWatchData watchData = new DeploymentDebugWatchData(
					deviceManagementExecutorService.readWatches());
			final Map<String, String> currentState = getWatchData(watchData, resource,
					initialState.keySet().stream().collect(Collectors.toSet()));

			// Process the value
			final List<DataPointChange> dataPointChanges = new ArrayList<>();
			for (final Map.Entry<String, String> entry : currentState.entrySet()) {
				final String key = entry.getKey();
				final String currentStateValue = entry.getValue();
				if (!previousState.get(key).equals(currentStateValue)) {
					dataPointChanges.add(new DataPointChange(key, previousState.get(key), currentStateValue));
				}
			}
			eventCounter = eventCounter + 1;
			eventChanges.add(new EventChange(eventCounter, lastEvent.get(), dataPointChanges));
			previousState = new HashMap<>(currentState);

			FordiacLogHelper.logInfo("\nEvent triggered " + lastEvent.get() + " with the following data changes:");
			for (final DataPointChange change : dataPointChanges) {
				FordiacLogHelper.logInfo("  " + change.datapoint() + ": " + change.newValue()); //$NON-NLS-1$ //$NON-NLS-2$
			}

		}
		return eventChanges;
	}

	// callback from the replay navigator when the state changes
	@Override
	public void update(final ReplayNavigator replayNavigator, final ReplayNavigator.DatapointsState changedValues) {
		for (final Map.Entry<String, String> entry : changedValues.entrySet()) {
			final String datapoint = entry.getKey();
			final String value = entry.getValue();

			final org.eclipse.fordiac.ide.deployment.devResponse.Data dataValue = dataValues.get(datapoint);
			dataValue.setValue(value);

		}
		updateListener.onUpdate(this);
	}
}
