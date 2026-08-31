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
import java.util.Collections;
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

	public interface StructureListener {
		void eventAdded(Timeline timeline);

		void eventsRemoved(Timeline timeline, int removedStartEventIndex, List<EventChange> removedChanges);

		void timelineSpawned(Timeline spawnedTimeline);

		void timelineRemoved(Timeline parentTimeline, Timeline removedTimeline, int spawnedAtEventNumber);

		void timelineStateChanged(Timeline timeline);
	}

	private Timeline parentTimeline = null;

	private final List<EventChange> eventChanges = new ArrayList<>();

	private final Map<Timeline, Integer> spawnedTimelines = new HashMap<>();

	private final Set<StructureListener> structureListeners = new HashSet<>();

	// map from datapoints to the event numbers where they are changed
	private final HashMap<String, Set<Integer>> eventsWhereDatapointsChange = new HashMap<>();

	private int firstDeletableEventIndex = 0;

	public int getFirstDeletableEventIndex() {
		return firstDeletableEventIndex;
	}

	public Set<Integer> getEventsThatTouch(final List<String> datapoints) {

		final Set<Integer> result = new HashSet<>();
		if (datapoints == null) {
			return result;
		}

		for (final var datapoint : datapoints) {
			if (!eventsWhereDatapointsChange.containsKey(datapoint)) {
				continue;
			}
			for (final var eventIntex : eventsWhereDatapointsChange.get(datapoint)) {
				result.add(eventIntex);
			}
		}
		return result;
	}

	public void setFirstDeletableEventIndex(final int firstDeletableEventIndex) {
		this.firstDeletableEventIndex = firstDeletableEventIndex;
		notifyTimelineStateChanged();
	}

	public void addEventChange(final List<DataPointChange> newValues) {
		eventChanges.add(new EventChange(eventChanges.size(), newValues));
		// add mapping from datapoints to event numbers
		for (final var newValue : newValues) {
			eventsWhereDatapointsChange.computeIfAbsent(newValue.datapoint(), _ -> new HashSet<>());
			eventsWhereDatapointsChange.get(newValue.datapoint()).add(Integer.valueOf(eventChanges.size() - 1));
		}
		notifyNewEvent();
	}

	public void removeEventsFrom(final int eventNumber) {
		if (eventNumber < 0 || eventNumber >= eventChanges.size() || eventNumber < firstDeletableEventIndex) {
			return;
		}

		// a view to actual values
		final var toRemove = eventChanges.subList(eventNumber, eventChanges.size());

		// remove mapping from datapoints to event numbers
		for (var i = 0; i < eventChanges.size() - eventNumber; i++) {
			for (final var datapointChange : toRemove.get(i).newValues()) {
				eventsWhereDatapointsChange.get(datapointChange.datapoint()).remove(Integer.valueOf(i + eventNumber));
			}
		}

		// remove timelines which spawn from removed events
		for (final var entry : spawnedTimelines.entrySet()) {
			final var spawnedTimeline = entry.getKey();
			final var spawnedAtEventNumber = entry.getValue().intValue();
			if (spawnedAtEventNumber >= eventNumber) {
				removeSpawnedTimeline(spawnedTimeline);
			}
		}

		// create a deep copy of toRemove
		final var toRemoveCopy = new ArrayList<>(toRemove);
		toRemove.clear();
		notifyRemoveEvents(eventNumber, toRemoveCopy);

	}

	public List<EventChange> getEventsFrom(final int eventNumber) {
		if (eventNumber < 0 || eventNumber > eventChanges.size()) {
			return Collections.emptyList();
		}
		return new ArrayList<>(eventChanges.subList(eventNumber, eventChanges.size()));
	}

	public void addSpawnedTimeline(final Timeline spawnedTimeline, final int eventNumber) {
		spawnedTimelines.put(spawnedTimeline, Integer.valueOf(eventNumber));
		spawnedTimeline.parentTimeline = this;
		notifyNewSpawnedTimeline(spawnedTimeline);
	}

	public void removeSpawnedTimeline(final Timeline spawnedTimeline) {
		// remove child timelines first
		for (final var childTimeline : spawnedTimeline.getSpawnedTimelines()) {
			spawnedTimeline.removeSpawnedTimeline(childTimeline);
		}
		final var spawnedAtEventNumber = spawnedTimelines.get(spawnedTimeline);

		// we notify first and then remove the timeline, so listeners can react on a
		// valid tree of timelines.
		notifyRemovedSpawnedTimeline(spawnedTimeline, spawnedAtEventNumber.intValue());

		spawnedTimelines.remove(spawnedTimeline);
		spawnedTimeline.parentTimeline = null;
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
	public void addStructureListener(final StructureListener timelineListener) {
		structureListeners.add(timelineListener);
	}

	public void removeStructureListener(final StructureListener timelineListener) {
		structureListeners.remove(timelineListener);
	}

	private void notifyNewEvent() {
		for (final var listener : structureListeners) {
			listener.eventAdded(this);
		}
	}

	private void notifyRemoveEvents(final int removedStartEventIndex, final List<EventChange> removedChanges) {
		// defense copy when removing events, as listeners might remove timelines and
		// thus modify the list of listeners while we are notifying
		for (final var listener : new HashSet<>(structureListeners)) {
			listener.eventsRemoved(this, removedStartEventIndex, removedChanges);
		}
	}

	private void notifyRemovedSpawnedTimeline(final Timeline removedTimeline, final int spawnedAtEventNumber) {
		for (final var listener : new HashSet<>(structureListeners)) {
			listener.timelineRemoved(this, removedTimeline, spawnedAtEventNumber);
		}
	}

	private void notifyNewSpawnedTimeline(final Timeline spawnedTimeline) {
		for (final var listener : structureListeners) {
			listener.timelineSpawned(spawnedTimeline);
		}
	}

	private void notifyTimelineStateChanged() {
		for (final var listener : structureListeners) {
			listener.timelineStateChanged(this);
		}
	}

}
