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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @brief Core part of the replay debugging mechanism. Ii allows navigation over
 *        a series of events and provide information of the values of the
 *        datapoints.
 *
 *        This class provides functionality to move forward, backward, or jump
 *        to a specific event number (position) between timelines. When
 *        navigating to a different event number, the update callback contains
 *        the final state of the datapoints that have changed between the
 *        previous position and the new position. The information is stored as
 *        an initial state and a sequence of event changes, where each event
 *        change contains the datapoints that were changed and their new values.
 *        The state is stored as a map of datapoint names to their values,
 *        allowing easy access to the current state of each datapoint.
 */
public class ReplayNavigator implements Timeline.StructureListener {

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
		void stateUpdated(ReplayNavigator replayNavigator, DatapointsState changedValues);
	}

	/**
	 * Interface for listening for navigator changes not related to the state of the
	 * datapoints, such as the amount of events.
	 */
	public interface NavigatorConfigListener {
		void update(ReplayNavigator replayNavigator);
	}

	/**
	 * Current overall position of the navigator, which includes the timeline and
	 * the event number in that timeline.
	 */
	public record EventPosition(Timeline timeline, int eventNumber) {
	}

	// all the datapoints with their current values
	private final DatapointsState currentState;

	private final Timeline rootTimeline = new Timeline();

	// current event number (position) in the sequence of events
	private EventPosition currentEventPosition = new EventPosition(rootTimeline, 0);

	private final Identifier identifier;

	private int maxEventNumber = 1;

	private final List<StateListener> stateListeners = new CopyOnWriteArrayList<>();

	private final List<NavigatorConfigListener> navigatorConfigListeners = new ArrayList<>();

	public ReplayNavigator(final Identifier identifier, final DatapointsState initialState) {
		this.identifier = identifier;
		this.currentState = initialState;
		rootTimeline.addEventChange(initialState.entrySet().stream()
				.map(entry -> new DataPointChange(entry.getKey(), entry.getValue(), entry.getValue())).toList());
		rootTimeline.addStructureListener(this);
	}

	/**
	 * @brief Adds a new event change to the navigator at the current position. If
	 *        the current position is not at the end of the timeline, a new spawned
	 *        timeline is created to add the event.
	 *
	 * @param newValues values of the datapoints that changed in the new event
	 */
	public void addEventChange(final List<DataPointChange> newValues) {
		var timelineToAdd = currentEventPosition.timeline();

		if (currentEventPosition.timeline().getMaxEventNumber() != currentEventPosition.eventNumber()) {
			// we are not at the end
			timelineToAdd = new Timeline();
			currentEventPosition.timeline().addSpawnedTimeline(timelineToAdd, currentEventPosition.eventNumber());
		}
		timelineToAdd.addEventChange(newValues);
		moveToEvent(new EventPosition(timelineToAdd, timelineToAdd.getMaxEventNumber()));
	}

	public void markCurrentStateAsNotDeletable() {
		markCurrentStateAsNotDeletable(rootTimeline);
	}

	private static void markCurrentStateAsNotDeletable(final Timeline timeline) {
		timeline.setFirstDeletableEventIndex(timeline.getMaxEventNumber() + 1);
		for (final Timeline spawnedTimeline : timeline.getSpawnedTimelines()) {
			markCurrentStateAsNotDeletable(spawnedTimeline);
		}
	}

	public Timeline getRootTimeline() {
		return rootTimeline;
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

	public void addNavigatorConfigListener(final NavigatorConfigListener navigatorConfigListener) {
		navigatorConfigListeners.add(navigatorConfigListener);
	}

	public void removeNavigatorConfigListener(final NavigatorConfigListener navigatorConfigListener) {
		navigatorConfigListeners.remove(navigatorConfigListener);
	}

	/**
	 * @brief Returns the current event change based on the current event number.
	 *
	 * @return The current EventChange or null if the current event number is out of
	 *         bounds.
	 */
	public EventChange getCurrentEventChange() {
		return currentEventPosition.timeline().getEventChange(currentEventPosition.eventNumber());
	}

	/**
	 * @brief Returns the total number of events available for navigation, including
	 *        all spawned timelines.
	 *
	 * @return The number of events.
	 */
	public int getAmountOfEvents() {
		return maxEventNumber;
	}

	/**
	 * @brief Gets the current event position (timline and event number in it)
	 *
	 * @return The current event position.
	 */
	public EventPosition getCurrentEventPosition() {
		return currentEventPosition;
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
	 * @brief Moves the navigator one event forward
	 */
	public void moveOneEventForward() {
		// there's no deeper timeline or the next event lays still on the current
		// timeline
		if (currentEventPosition.timeline().getMaxEventNumber() == currentEventPosition.eventNumber()) {
			return;
		}
		moveToEvent(new EventPosition(currentEventPosition.timeline(), currentEventPosition.eventNumber() + 1));
	}

	/**
	 * @brief Moves the navigator one event backward. If the current event number is
	 *        0, it moves to the parent timeline (if any) to the event number where
	 *        the current timeline was spawned.
	 */
	public void moveOneEventBackwards() {
		if (currentEventPosition.eventNumber() == 0) {
			final Timeline parentTimeline = currentEventPosition.timeline().getParentTimeline();
			if (parentTimeline == null) {
				return;
			}
			moveToEvent(new EventPosition(parentTimeline,
					parentTimeline.getSpawnedTimelineEventNumber(currentEventPosition.timeline())));
			return;
		}
		moveToEvent(new EventPosition(currentEventPosition.timeline(), currentEventPosition.eventNumber() - 1));
	}

	/**
	 * @brief Moves the navigator to the specified event position.
	 *
	 *        The logic for moving to a specific event number is as follows: get the
	 *        changes from each event and accumulate them and then update the
	 *        current state with the accumulated changes
	 *
	 * @param eventNumber The event position to move to.
	 */
	public void moveToEvent(final EventPosition position) {
		final DatapointsState changedValues = getChangesFromTo(currentEventPosition, position);
		currentEventPosition = position;
		updateCache(changedValues);
	}

	/**
	 * Goes from the initial to final position across timelines accumulating the
	 * changes of each event.
	 *
	 * @param initialPosition starting position of the navigation
	 * @param finalPosition   ending position of the navigation
	 * @return the accumulated changes between the initial and final position
	 */
	private static DatapointsState getChangesFromTo(final EventPosition initialPosition,
			final EventPosition finalPosition) {
		final var pathBetweenTimeliens = pathBetween(initialPosition.timeline(), finalPosition.timeline());

		final var changedValues = new DatapointsState();
		var currentInitialEventNumber = initialPosition.eventNumber();

		while (!pathBetweenTimeliens.isEmpty()) {
			final Timeline currentTimeline = pathBetweenTimeliens.pop();
			final Timeline nextTimeline = pathBetweenTimeliens.peek();
			if (nextTimeline == null) {
				// both positions are on the same timeline, so we can directly get the changes
				// between them
				changedValues.putAll(
						currentTimeline.getChangesFromTo(currentInitialEventNumber, finalPosition.eventNumber()));
			} else if (currentTimeline.getSpawnedTimelines().contains(nextTimeline)) {
				// we are going down in the timeline tree, so we need to get the changes from
				// the current event to the spawn event of the next timeline
				final var spawnEventNumber = currentTimeline.getSpawnedTimelineEventNumber(nextTimeline);
				changedValues.putAll(currentTimeline.getChangesFromTo(currentInitialEventNumber, spawnEventNumber));
				changedValues.putAll(nextTimeline.getInitialStateAtEnteringTimeline());
				currentInitialEventNumber = 0; // after the first timeline, we always want to start from event 0
			} else {
				// we are going up in the timeline tree, so we need to get the changes from the
				// current event the initial event of the current timeline, which is the spawn
				// event of the current timeline in the
				// next timeline
				changedValues.putAll(currentTimeline.getChangesFromTo(currentInitialEventNumber, 0));
				changedValues.putAll(currentTimeline.getInitialStateAtLeavingTimeline());
				final var spawnEventNumber = nextTimeline.getSpawnedTimelineEventNumber(currentTimeline);
				currentInitialEventNumber = spawnEventNumber; // after going up, we want to start from the spawn event
																// of the next timeline
			}
		}
		return changedValues;
	}

	public DatapointsState getStateAtEventPosition(final EventPosition position) {
		// DatapointsState extends HashMap, so we need to create a new instance to avoid
		// modifying the current state
		final var currentStateTemp = new DatapointsState();
		currentStateTemp.putAll(currentState);
		currentStateTemp.putAll(getChangesFromTo(currentEventPosition, position));
		return currentStateTemp;
	}

	/**
	 * Finds the lowest common ancestor of two timelines, which is the timeline that
	 * is the closest common parent of both timelines. This is used to find the path
	 * between two timelines when navigating
	 *
	 * @param initialTimeline the first timeline
	 * @param finalTimeline   the second timeline
	 * @return the lowest common ancestor timeline, or null if there is no common
	 *         ancestor
	 */
	private static Timeline lowestCommonAncestor(Timeline initialTimeline, Timeline finalTimeline) {
		final Set<Timeline> ancestors = new HashSet<>();

		while (initialTimeline != null) {
			ancestors.add(initialTimeline);
			initialTimeline = initialTimeline.getParentTimeline();
		}

		while (finalTimeline != null) {
			if (ancestors.contains(finalTimeline)) {
				return finalTimeline;
			}
			finalTimeline = finalTimeline.getParentTimeline();
		}

		return null;
	}

	/**
	 * Finds the path between two timelines, which is the sequence of timelines that
	 * connects the initial timeline to the final timeline through their common
	 * ancestor. The logic goes backwards from the initial timeline to the common
	 * ancestor and then forwards from the common ancestor to the final timeline.
	 *
	 * @param initialTimeline the starting timeline
	 * @param finalTimeline   the target timeline
	 * @return
	 */
	private static Deque<Timeline> pathBetween(final Timeline initialTimeline, final Timeline finalTimeline) {
		final Timeline lca = lowestCommonAncestor(initialTimeline, finalTimeline);
		if (lca == null) {
			return new ArrayDeque<>();
		}

		final Deque<Timeline> path = new ArrayDeque<>();

		// Walk from initialTimeline up to LCA
		Timeline curr = initialTimeline;
		while (curr != lca) {
			path.add(curr);
			curr = curr.getParentTimeline();
		}
		path.add(lca);

		// Walk from b up to LCA (store separately)
		final Deque<Timeline> tail = new ArrayDeque<>();
		curr = finalTimeline;
		while (curr != lca) {
			tail.add(curr);
			curr = curr.getParentTimeline();
		}

		// Append LCA -> B part
		path.addAll(tail.reversed());
		return path;
	}

	private void updateCache(final DatapointsState changedValues) {
		currentState.putAll(changedValues);
		notifyStateChange(changedValues);
	}

	private void notifyStateChange(final DatapointsState changedValues) {
		for (final StateListener listener : stateListeners) {
			listener.stateUpdated(this, changedValues);
		}
	}

	@Override
	public void timelineSpawned(final Timeline timeline) {
		timeline.addStructureListener(this);
		maxEventNumber = rootTimeline.getTotalMaxEventNumber();
	}

	@Override
	public void timelineRemoved(final Timeline parentTimeline, final Timeline removedTimeline,
			final int spawnedAtEventNumber) {
		if (getCurrentEventPosition().timeline() == removedTimeline) {
			moveToEvent(new EventPosition(parentTimeline, spawnedAtEventNumber));
		}
		removedTimeline.removeStructureListener(this);
		maxEventNumber = rootTimeline.getTotalMaxEventNumber();
	}

	@Override
	public void eventAdded(final Timeline timeline) {
		maxEventNumber = rootTimeline.getTotalMaxEventNumber();
	}

	@Override
	public void eventsRemoved(final Timeline timeline, final int removedStartEventIndex,
			final List<EventChange> removedChanges) {
		if (getCurrentEventPosition().timeline() == timeline
				&& getCurrentEventPosition().eventNumber() >= removedStartEventIndex) {

			// move to the previous existing event manually, either in this timeline or in
			// the parent if the start index is 0

			// treat the removed events as its own timeline for easier management of changes
			final Timeline temp = new Timeline();
			for (final EventChange removedChange : removedChanges) {
				temp.addEventChange(removedChange.newValues());
			}

			final var initialEventNumber = getCurrentEventPosition().eventNumber() - removedStartEventIndex;

			final var changedValues = temp.getChangesFromTo(initialEventNumber, 0);

			if (removedStartEventIndex == 0) {
				changedValues.putAll(temp.getInitialStateAtLeavingTimeline());
				currentEventPosition = new EventPosition(timeline.getParentTimeline(),
						timeline.getParentTimeline().getSpawnedTimelineEventNumber(timeline));
			} else {
				currentEventPosition = new EventPosition(timeline, removedStartEventIndex - 1);
			}
			updateCache(changedValues);
		}
		if (removedStartEventIndex == 0) {
			timeline.getParentTimeline().removeSpawnedTimeline(timeline);
		}
		maxEventNumber = rootTimeline.getTotalMaxEventNumber();
	}

	@Override
	public void timelineStateChanged(final Timeline timeline) {
		// nothing to do here
	}

}
