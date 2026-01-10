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
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.debug.replaydebugging.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @brief Core part of the replay debugging mechanism. Ii allows navigation over
 *        a series of events and provide information of the values of the
 *        datapoints.
 *
 *        This class provides functionality to move forward, backward, or jump
 *        to a specific event number (position) in a sequence of events. When
 *        navigating to a different event number, the update callback contains
 *        the final state of the datapoints that have changed between the
 *        previous position and the new position. The information is stored as
 *        an initial state and a sequence of event changes, where each event
 *        change contains the datapoints that were changed and their new values.
 *        The state is stored as a map of datapoint names to their values,
 *        allowing easy access to the current state of each datapoint.
 */
public class ReplayNavigator {

	public static class DatapointsState extends HashMap<String, String> {
	}

	// identifier for the replay navigator, which includes the automation system,
	// device, and resource names
	public record Identifier(String automationSystemName, String deviceName, String resourceName) {
	}

	/**
	 * Interface for listening to state updates from the replay navigator. This
	 * interface allows external components to receive updates about the current
	 * state of the datapoints.
	 */
	public interface StateListener {
		void update(ReplayNavigator replayNavigator, ReplayNavigator.DatapointsState changedValues);
	}

	// all the datapoints with their current values
	private DatapointsState currentState = new DatapointsState();

	// sequence of events that changed the were triggered
	private final List<EventChange> eventChanges;

	// current event number (position) in the sequence of events
	private int currentEventNumber;

	private final Identifier identifier;

	// this field is intended to indicate at which events the datapoints were
	// changed. Currently it is not used in the GUI
	private final HashMap<String, List<Integer>> datapointsChangedAt = new HashMap<>();

	private final List<StateListener> stateListeners = new ArrayList<>();

	public ReplayNavigator(final Identifier identifier, final DatapointsState initialState,
			final List<EventChange> eventChanges) {
		this.identifier = identifier;
		this.currentState = initialState;
		this.eventChanges = eventChanges;
		currentEventNumber = 0;

		// store at which event numbers the events were triggered
		for (int i = 0; i < eventChanges.size(); i++) {
			for (final DataPointChange datapoint : eventChanges.get(i).newValues()) {
				List<Integer> indices = datapointsChangedAt.get(datapoint.datapoint());
				if (indices == null) {
					indices = new ArrayList<>();
					datapointsChangedAt.put(datapoint.datapoint(), indices);
				}
				indices.add(Integer.valueOf(i));
			}
		}
	}

	/**
	 * Adds a {@link StateListener} instance that will be notified when the internal
	 * state changes.
	 *
	 * @param stateListener the {@link StateListener} to be added
	 */
	public void addStateChangeListener(final StateListener stateListener) {
		stateListeners.add(stateListener);
	}

	public void removeStateChangeListener(final StateListener stateListener) {
		stateListeners.remove(stateListener);
	}

	/**
	 * @brief Returns the current event change based on the current event number.
	 * @return The current EventChange or null for event 0 or if the current event
	 *         number is out of bounds.
	 */
	public EventChange getCurrentEventChange() {
		if (currentEventNumber <= 0 || currentEventNumber > eventChanges.size()) { // for event 0 we handle as no change
																					// happened
			return null;
		}
		return eventChanges.get(currentEventNumber - 1); // minus one since event 1 is at index 0
	}

	/**
	 * @brief Returns the total number of events available for navigation.
	 * @return The number of events.
	 */
	public int getAmountOfEvents() {
		return eventChanges.size();
	}

	/**
	 * @brief Gets the current event number (position) in the navigation sequence.
	 * @return The current event number.
	 */
	public int getCurrentEventNumber() {
		return currentEventNumber;
	}

	/**
	 * @brief Retrieves the current state of all datapoints.
	 * @return The current state as a DatapointsState map.
	 */
	public DatapointsState getCurrentState() {
		return currentState;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	/**
	 * @brief Returns a list of event positions that have affected the specified
	 *        datapoint.
	 * @param datapoint The name of the datapoint to query.
	 * @return A vector of event positions that have changed the datapoint.
	 */
	public List<Integer> getEventsThatTouch(final String datapoint) {
		final List<Integer> touchedEvents = datapointsChangedAt.get(datapoint);
		return (touchedEvents != null) ? touchedEvents : new ArrayList<>();
	}

	/**
	 * @brief Moves the navigator one event forward
	 */
	public void moveOneEventForward() {
		moveToEvent(currentEventNumber + 1);
	}

	/**
	 * @brief Moves the navigator one event backward
	 */
	public void moveOneEventBackwards() {
		moveToEvent(currentEventNumber - 1);
	}

	/**
	 * @brief Moves the navigator to the specified event number.
	 *
	 *        The logic for moving to a specific event number is as follows: - Get
	 *        the changes from each event and accumulate them - Update the current
	 *        state with the accumulated changes
	 *
	 *        For forward and backward movements, special care is taken on which
	 *        value to take and which is the first and last event to consider.
	 *
	 * @param eventNumber The event number to move to.
	 */
	public void moveToEvent(final int eventNumber) {
		if (eventNumber < 0 || eventNumber > getAmountOfEvents()) {
			return;
		}
		final DatapointsState changedValues = getChangesFromTo(currentEventNumber, eventNumber);
		currentEventNumber = eventNumber;
		updateCache(changedValues);
	}

	private DatapointsState getChangesFromTo(final int initialEventNumber, final int finalEventNumber) {
		if (initialEventNumber < finalEventNumber) {
			return getChangesFromToForward(initialEventNumber, finalEventNumber);
		}
		return getChangesFromToBackwards(initialEventNumber, finalEventNumber);

	}

	private DatapointsState getChangesFromToForward(final int initialEventNumber, final int finalEventNumber) {
		final DatapointsState changedValues = new DatapointsState();
		for (int i = initialEventNumber + 1; i <= finalEventNumber; i++) { // we start accumulating changes from the
																			// next event change
			final EventChange eventChange = eventChanges.get(i - 1); // minus one since event 1 is at index 0
			for (final DataPointChange dataPointChange : eventChange.newValues()) {
				final String newValue = dataPointChange.newValue();
				changedValues.put(dataPointChange.datapoint(), newValue);
			}
		}
		return changedValues;
	}

	private DatapointsState getChangesFromToBackwards(final int initialEventNumber, final int finalEventNumber) {
		final DatapointsState changedValues = new DatapointsState();
		for (int i = initialEventNumber; i >= finalEventNumber + 1; i--) {
			final EventChange eventChange = eventChanges.get(i - 1);
			for (final DataPointChange dataPointChange : eventChange.newValues()) {
				final String newValue = dataPointChange.oldValue();
				changedValues.put(dataPointChange.datapoint(), newValue);
			}
		}
		return changedValues;
	}

	private void updateCache(final DatapointsState changedValues) {
		currentState.putAll(changedValues);
		notifyStateChange(changedValues);
	}

	private void notifyStateChange(final DatapointsState changedValues) {
		for (final StateListener listener : stateListeners) {
			listener.update(this, changedValues);
		}
	}

}
