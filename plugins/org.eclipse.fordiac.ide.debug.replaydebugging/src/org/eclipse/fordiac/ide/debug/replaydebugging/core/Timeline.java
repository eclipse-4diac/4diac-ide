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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @brief Class representing a single timeline of events in the execution of a
 *        resource
 *
 *        It provides methods for adding events, getting the changes between
 *        events and managing spawned timelines.
 */
public class Timeline {

	public interface NewEventListener {
		void eventAdded(Timeline timeline);
	}

	public interface NewSpawnedTimelineListener {
		void timelineSpawned(Timeline spawnedTimeline);
	}

	private Timeline parentTimeline = null;

	private final List<EventChange> eventChanges = new ArrayList<>();

	private final Map<Timeline, Integer> spawnedTimelines = new HashMap<>();

	private final Set<NewEventListener> newEventsListeners = new HashSet<>();

	private final Set<NewSpawnedTimelineListener> newSpawnedTimelineListeners = new HashSet<>();

	public void addEventChange(final List<DataPointChange> newValues) {
		eventChanges.add(new EventChange(eventChanges.size(), newValues));
		notifyNewEvent();
	}

	public void addSpawnedTimeline(final Timeline spawnedTimeline, final int eventNumber) {
		spawnedTimelines.put(spawnedTimeline, Integer.valueOf(eventNumber));
		spawnedTimeline.parentTimeline = this;
		notifyNewSpawnedTimeline(spawnedTimeline);
	}

	public int getSpawnedTimelineEventNumber(final Timeline timeline) {
		return spawnedTimelines.get(timeline).intValue();
	}

	public static int getSpawnedTimelineGlobalEventNumber(final Timeline timeline) {

		int total = 0;
		Timeline currentTimeline = timeline;
		var parentTimeline = currentTimeline.getParentTimeline();
		while (parentTimeline != null) {
			total += parentTimeline.getSpawnedTimelineEventNumber(currentTimeline) + 1;
			currentTimeline = parentTimeline;
			parentTimeline = parentTimeline.getParentTimeline();
		}
		return total;
	}

	public EventChange getEventChange(final int eventNumber) {
		if (eventNumber < 0 || eventNumber > eventChanges.size()) {
			return null;
		}
		return eventChanges.get(eventNumber);
	}

	public int getTotalMaxEventNumber() {
		var maxEventNumber = eventChanges.size();
		for (final var entry : spawnedTimelines.entrySet()) {
			final var longestSpawnedTimelineMaxEventNumber = entry.getKey().getTotalMaxEventNumber()
					+ entry.getValue().intValue();
			if (longestSpawnedTimelineMaxEventNumber > maxEventNumber) {
				maxEventNumber = longestSpawnedTimelineMaxEventNumber;
			}
		}
		return maxEventNumber;
	}

	public int getGlobalIndexStart() {
		int globalStart = 0;
		var currentTimeline = this;
		var currentParentTimeline = parentTimeline;
		while (currentParentTimeline != null) {
			globalStart += currentParentTimeline.getSpawnedTimelineEventNumber(currentTimeline) + 1;
			currentTimeline = currentParentTimeline;
			currentParentTimeline = currentParentTimeline.getParentTimeline();
		}

		return globalStart;
	}

	public int getGlobalIndexEnd() {
		return getGlobalIndexStart() + getMaxEventNumber();
	}

	public int getMaxEventNumber() {
		return eventChanges.size() - 1;
	}

	public Timeline getParentTimeline() {
		return parentTimeline;
	}

	public Set<Timeline> getSpawnedTimelines() {
		return spawnedTimelines.keySet();
	}

	private DatapointsState getChangingState(final int index, final boolean newValues) {
		final var changedValues = new DatapointsState();
		final EventChange eventChange = eventChanges.get(index);
		for (final DataPointChange dataPointChange : eventChange.newValues()) {
			final String value = newValues ? dataPointChange.newValue() : dataPointChange.oldValue();
			changedValues.put(dataPointChange.datapoint(), value);
		}
		return changedValues;
	}

	public DatapointsState getInitialStateAtEnteringTimeline() {
		return getChangingState(0, true);
	}

	public DatapointsState getInitialStateAtLeavingTimeline() {
		return getChangingState(0, false);
	}

	public DatapointsState getChangesFromTo(final int initialEventNumber, final int finalEventNumber) {
		if (initialEventNumber < finalEventNumber) {
			return getChangesFromToForward(initialEventNumber, finalEventNumber);
		}
		return getChangesFromToBackwards(initialEventNumber, finalEventNumber);
	}

	private DatapointsState getChangesFromToForward(final int initialEventNumber, final int finalEventNumber) {
		final var changedValues = new DatapointsState();
		for (int i = initialEventNumber + 1; i <= finalEventNumber; i++) { // we start accumulating changes from the
																			// next event change
			final EventChange eventChange = eventChanges.get(i);
			for (final DataPointChange dataPointChange : eventChange.newValues()) {
				final String newValue = dataPointChange.newValue();
				changedValues.put(dataPointChange.datapoint(), newValue);
			}
		}
		return changedValues;
	}

	private DatapointsState getChangesFromToBackwards(final int initialEventNumber, final int finalEventNumber) {
		final var changedValues = new DatapointsState();
		for (int i = initialEventNumber; i > finalEventNumber; i--) {
			final EventChange eventChange = eventChanges.get(i);
			for (final DataPointChange dataPointChange : eventChange.newValues()) {
				final String newValue = dataPointChange.oldValue();
				changedValues.put(dataPointChange.datapoint(), newValue);
			}
		}
		return changedValues;
	}

	// Timeline listeners
	public void addNewEventListener(final NewEventListener timelineListener) {
		newEventsListeners.add(timelineListener);
	}

	public void removeNewEventListener(final NewEventListener timelineListener) {
		newEventsListeners.remove(timelineListener);
	}

	public void addNewSpawnedTimelineListener(final NewSpawnedTimelineListener timelineListener) {
		newSpawnedTimelineListeners.add(timelineListener);
	}

	public void removeNewSpawnedTimelineListener(final NewSpawnedTimelineListener timelineListener) {
		newSpawnedTimelineListeners.remove(timelineListener);
	}

	private void notifyNewEvent() {
		for (final var listener : newEventsListeners) {
			listener.eventAdded(this);
		}
	}

	private void notifyNewSpawnedTimeline(final Timeline spawnedTimeline) {
		for (final var listener : newSpawnedTimelineListeners) {
			listener.timelineSpawned(spawnedTimeline);
		}
	}

}
