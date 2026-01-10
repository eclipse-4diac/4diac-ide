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

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IDeviceSimulator;
import org.eclipse.fordiac.ide.deployment.devResponse.DevResponseFactory;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;
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

	private final Resource resource;

	private final UpdateListener updateListener;

	private final IDeviceSimulator simulator;

	// response data for the resources being replayed
	org.eclipse.fordiac.ide.deployment.devResponse.Resource resourceResponse = DevResponseFactory.eINSTANCE
			.createResource();

	// map from the datapoints coming from the replay navigator to the data values
	// in the response
	private final Map<String, org.eclipse.fordiac.ide.deployment.devResponse.Data> dataValues = new HashMap<>();

	public ReplayDebuggingResource(final Resource resource, final ReplayNavigator.Identifier reaplayNavigatorIdentifier,
			final IDeviceSimulator simulator, final UpdateListener updateListener) {
		this.replayNavigatorIdentifier = reaplayNavigatorIdentifier;
		this.resource = resource;
		this.updateListener = updateListener;
		this.simulator = simulator;
	}

	public org.eclipse.fordiac.ide.deployment.devResponse.Resource getResourceResponse() {
		return resourceResponse;
	}

	public EventChange getCurrentEventChange() {
		return replayNavigator.getCurrentEventChange();
	}

	public void load() {
		createReplayNavigator();
	}

	public void unload() {
		replayNavigator.removeStateChangeListener(this);
		ReplayNavigatorManager.getDefault().unregisterNavigator(replayNavigator);
	}

	private void createReplayNavigator() {
		final ReplayNavigator.DatapointsState initialState = simulator.getCurrentState(resource);

		createResourceResponse(initialState);

		final List<EventChange> eventChanges = iterateOverAllEvents(initialState);
		replayNavigator = new ReplayNavigator(replayNavigatorIdentifier, initialState, eventChanges);
		replayNavigator.addStateChangeListener(this);
		ReplayNavigatorManager.getDefault().registerNavigator(replayNavigator);
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
	private List<EventChange> iterateOverAllEvents(final ReplayNavigator.DatapointsState initialState) {
		// simulate all events and gather all data
		int eventCounter = 0;
		final List<EventChange> eventChanges = new ArrayList<>();
		Map<String, String> previousState = new HashMap<>(initialState);

		for (Optional<String> lastEvent = simulator.replayNextEvent(resource); lastEvent
				.isPresent(); lastEvent = simulator.replayNextEvent(resource)) {

			final Map<String, String> currentState = simulator.getCurrentState(resource);

			// Process the value
			final List<DataPointChange> dataPointChanges = new ArrayList<>();
			for (final Map.Entry<String, String> entry : currentState.entrySet()) {
//				System.out.println(entry.getKey() + " : " + entry.getValue());
				final String key = entry.getKey();
				final String currentStateValue = entry.getValue();
				if (!previousState.get(key).equals(currentStateValue)) {
					dataPointChanges.add(new DataPointChange(key, previousState.get(key), currentStateValue));
				}
			}
			eventCounter = eventCounter + 1;
			eventChanges.add(new EventChange(eventCounter, lastEvent.get(), dataPointChanges));
			previousState = new HashMap<>(currentState);

			String toLog = "\nEvent triggered " + lastEvent.get() + " with the following data changes:";
			for (final DataPointChange change : dataPointChanges) {
				FordiacLogHelper.logInfo("  " + change.datapoint() + ": " + change.newValue()); //$NON-NLS-1$ //$NON-NLS-2$
				toLog += "  " + change.datapoint() + ": " + change.newValue();
			}

			FordiacLogHelper.logInfo(toLog);

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
