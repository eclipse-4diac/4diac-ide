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
import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.EventChange;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigatorManager;
import org.eclipse.fordiac.ide.debug.replaydebugging.replayer.IResourceReplayer;
import org.eclipse.fordiac.ide.debug.replaydebugging.response.ResourceResponse;
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

	private final IResourceReplayer replayer;

	private ResourceResponse resourceResponse;

	public ReplayDebuggingResource(final ReplayNavigator.Identifier reaplayNavigatorIdentifier,
			final IResourceReplayer replayer, final UpdateListener updateListener) {
		this.replayNavigatorIdentifier = reaplayNavigatorIdentifier;
		this.updateListener = updateListener;
		this.replayer = replayer;
	}

	public ResourceResponse getResourceResponse() {
		return resourceResponse;
	}

	public void triggerEvent(final String name) {
		updateSimulatorWithReplayNavigator();
		replayer.injectEvent(name);
		runAllEvents();
	}

	public void forceValue(final String watchPoint, final String value) {
		replayer.forceValue(watchPoint, value);
	}

	public void clearForce(final String watchPoint) {
		replayer.clearForce(watchPoint);
	}

	private void updateSimulatorWithReplayNavigator() {
		replayer.setCurrentState(replayNavigator.getCurrentState());
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
		final DatapointsState initialState = replayer.getCurrentState();

		resourceResponse = new ResourceResponse(replayNavigatorIdentifier.resourceName(), initialState);
		replayNavigator = new ReplayNavigator(replayNavigatorIdentifier, initialState);
		replayNavigator.addStateChangeListener(this);
		ReplayNavigatorManager.getDefault().registerNavigator(replayNavigator);
		runAllEvents();
		replayNavigator.markCurrentStateAsNotDeletable();
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
	private void runAllEvents() {
		// simulate all events and gather all data
		HashMap<String, String> previousState = new HashMap<>(replayer.getCurrentState());

		for (Optional<String> lastEvent = replayer.replayNextEvent(); lastEvent
				.isPresent(); lastEvent = replayer.replayNextEvent()) {

			final var currentState = replayer.getCurrentState();

			// Process the value
			final List<DataPointChange> dataPointChanges = new ArrayList<>();
			for (final Map.Entry<String, String> entry : currentState.entrySet()) {
				final var datapoint = entry.getKey();
				final var currentValue = entry.getValue();
				if (!previousState.get(datapoint).equals(currentValue)) {
					dataPointChanges.add(new DataPointChange(datapoint, previousState.get(datapoint), currentValue));
				}
			}
			replayNavigator.addEventChange(dataPointChanges);
			previousState = new HashMap<>(currentState);
		}
	}

	// callback from the replay navigator when the state changes
	@Override
	public void stateUpdated(final ReplayNavigator replayNavigator, final DatapointsState changedValues) {
		resourceResponse.updateResponse(changedValues);
		updateListener.onUpdate(this);
	}
}
