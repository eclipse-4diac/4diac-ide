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
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.EventPosition;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;

/**
 * @brief Represents a resource in the replay debugging UI model
 *
 *        It offers a name and the replay navigator, and manages the models for
 *        connections between timelines.
 */
public class Resource implements ReplayNavigator.StateListener {
	private final ReplayNavigator replayNavigator;
	private final TimelineModel rootTimelineModel;

	public Resource(final ReplayNavigator replayNavigator) {
		this.replayNavigator = replayNavigator;
		this.rootTimelineModel = new TimelineModel(replayNavigator.getRootTimeline(), this::eventSelected);
		replayNavigator.addStateChangeListener(this);
		updateCurrentPosition();
	}

	public void dispose() {
		replayNavigator.removeStateChangeListener(this);
	}

	public ReplayNavigator getReplayNavigator() {
		return replayNavigator;
	}

	public String getName() {
		return replayNavigator.getIdentifier().resourceName();
	}

	public TimelineModel getRootTimelineModel() {
		return rootTimelineModel;
	}

	private void updateCurrentPosition() {
		final var currentEventPosition = replayNavigator.getCurrentEventPosition();

		var runnerTimeline = currentEventPosition.timeline();
		final List<Timeline> presentTimelines = new ArrayList<>();
		while (runnerTimeline != null) {
			presentTimelines.add(runnerTimeline);
			runnerTimeline = runnerTimeline.getParentTimeline();
		}

		rootTimelineModel.updateCurrentPosition(currentEventPosition, presentTimelines.reversed());
	}

	private void eventSelected(final Timeline timeline, final Integer index) {
		replayNavigator.moveToEvent(new EventPosition(timeline, index.intValue()));
	}

	public void moveForward() {
		replayNavigator.moveOneEventForward();
	}

	public void moveBackwards() {
		replayNavigator.moveOneEventBackwards();
	}

	public void moveUp() {
		final var currentEventPosition = replayNavigator.getCurrentEventPosition();
		final var currentTimeline = currentEventPosition.timeline();
		final var currentEvent = currentEventPosition.eventNumber();

		final var finalPosition = findNextTimelineWithValidEventNumber(currentTimeline, currentEvent, true);
		if (finalPosition == null) {
			return;
		}
		replayNavigator.moveToEvent(finalPosition);
	}

	public void moveDown() {
		final var currentEventPosition = replayNavigator.getCurrentEventPosition();
		final var currentTimeline = currentEventPosition.timeline();
		final var currentEvent = currentEventPosition.eventNumber();

		var hasSpawnedAtCurrentPosition = false;
		for (final var spawnedTimeline : currentTimeline.getSpawnedTimelines()) {
			if (currentTimeline.getSpawnedTimelineEventNumber(spawnedTimeline) == currentEvent) {
				hasSpawnedAtCurrentPosition = true;
				break;
			}
		}
		// if there's at least one spawned timeline, move down but one event later to
		// follow the first timeline that appears
		final var finalPosition = findNextTimelineWithValidEventNumber(currentTimeline,
				hasSpawnedAtCurrentPosition ? currentEvent + 1 : currentEvent, false);
		if (finalPosition == null) {
			return;
		}
		replayNavigator.moveToEvent(finalPosition);
	}

	private ReplayNavigator.EventPosition findNextTimelineWithValidEventNumber(final Timeline currentTimeline,
			final int eventNumber, final boolean reversed) {

		final List<Timeline> timelines = new ArrayList<>();

		// Collect all Timelines inside this Resource
		collectTimelinesBFS(replayNavigator.getRootTimeline(), timelines);

		if (reversed) {
			Collections.reverse(timelines);
		}

		// Find index of current timeline
		int currentIndex = -1;
		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i) == currentTimeline) {
				currentIndex = i;
				break;
			}
		}

		if (currentIndex == -1) {
			return null; // current timeline not found
		}

		final int currentGlobalEventNumber = currentTimeline.getGlobalIndexStart() + eventNumber;

		// Continue searching AFTER the current one
		for (int i = currentIndex + 1; i < timelines.size(); i++) {

			final Timeline timeline = timelines.get(i);

			final int start = timeline.getGlobalIndexStart();
			final int end = timeline.getGlobalIndexEnd();

			if (currentGlobalEventNumber >= start && currentGlobalEventNumber <= end) {
				return new ReplayNavigator.EventPosition(timeline, currentGlobalEventNumber - start);
			}
		}

		return null;
	}

	private void collectTimelinesBFS(final Timeline timeline, final List<Timeline> result) {

		result.add(timeline);

		final var spawnedTimelines = new ArrayList<>(timeline.getSpawnedTimelines());

		Collections.sort(spawnedTimelines, TimelineModel.getTimelineComparator(timeline, Function.identity()));

		for (final var spawned : spawnedTimelines) {
			collectTimelinesBFS(spawned, result);
		}

	}

	// callback from the replay navigator

	@Override
	public void stateUpdated(final ReplayNavigator replayNavigator, final DatapointsState changedValues) {
		updateCurrentPosition();
	}

}
