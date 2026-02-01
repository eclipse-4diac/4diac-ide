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

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DataPointChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.EventChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigatorManager;
import org.eclipse.fordiac.ide.debug.replaydebugging.response.ResourceResponse;
import org.eclipse.fordiac.ide.debug.replaydebugging.simulator.IResourceSimulator;
import org.eclipse.fordiac.ide.deployment.exceptions.DeploymentException;

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

	private final UpdateListener updateListener;

	private final IResourceSimulator simulator;

	private ResourceResponse resourceResponse;

	public ReplayDebuggingResource(final ReplayNavigator.Identifier reaplayNavigatorIdentifier,
			final IResourceSimulator simulator, final UpdateListener updateListener) {
		this.replayNavigatorIdentifier = reaplayNavigatorIdentifier;
		this.updateListener = updateListener;
		this.simulator = simulator;
	}

	public ResourceResponse getResourceResponse() {
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
		final ReplayNavigator.DatapointsState initialState = simulator.getCurrentState();

		resourceResponse = new ResourceResponse(replayNavigatorIdentifier.resourceName(), initialState);

		final List<EventChange> eventChanges = iterateOverAllEvents(initialState);
		replayNavigator = new ReplayNavigator(replayNavigatorIdentifier, initialState, eventChanges);
		replayNavigator.addStateChangeListener(this);
		ReplayNavigatorManager.getDefault().registerNavigator(replayNavigator);
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

		for (Optional<String> lastEvent = simulator.replayNextEvent(); lastEvent
				.isPresent(); lastEvent = simulator.replayNextEvent()) {

			final Map<String, String> currentState = simulator.getCurrentState();

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

		}
		return eventChanges;
	}

	// callback from the replay navigator when the state changes
	@Override
	public void update(final ReplayNavigator replayNavigator, final ReplayNavigator.DatapointsState changedValues) {
		resourceResponse.updateResponse(changedValues);
		updateListener.onUpdate(this);
	}
}
