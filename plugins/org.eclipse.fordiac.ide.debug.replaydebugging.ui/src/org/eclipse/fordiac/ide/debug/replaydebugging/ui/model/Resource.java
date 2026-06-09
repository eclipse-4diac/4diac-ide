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
import java.util.List;

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

	// callback from the replay navigator

	@Override
	public void stateUpdated(final ReplayNavigator replayNavigator, final DatapointsState changedValues) {
		updateCurrentPosition();
	}

}
