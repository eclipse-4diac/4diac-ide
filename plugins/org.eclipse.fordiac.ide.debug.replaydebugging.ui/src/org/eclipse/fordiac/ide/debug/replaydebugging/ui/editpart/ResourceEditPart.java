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

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.NameStackedFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Resource;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

/**
 * @brief EditPart for the Resource element in the replay navigator.
 *
 *        This class is responsible for creating the figure that represents a
 *        resource in the replay navigator, providing the content pane for its
 *        children, and handling the navigation actions (forward, backward, up,
 *        down) by interacting with the ReplayNavigator of the resource.
 */
public class ResourceEditPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
		return new NameStackedFigure(getReplayNavigatorName());
	}

	private String getReplayNavigatorName() {
		final var model = (Resource) getModel();
		return model.getName();
	}

	@Override
	public IFigure getContentPane() {
		return ((NameStackedFigure) getFigure()).getContentPane();
	}

	@Override
	protected List<?> getModelChildren() {
		final var model = (Resource) getModel();
		return List.of(model.getReplayNavigator().getRootTimeline());
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new NonResizableEditPolicy());
	}

	public void moveForward() {
		final var replayNavigator = ((Resource) getModel()).getReplayNavigator();
		replayNavigator.moveOneEventForward();
	}

	public void moveBackwards() {
		final var replayNavigator = ((Resource) getModel()).getReplayNavigator();
		replayNavigator.moveOneEventBackwards();
	}

	public void moveUp() {
		final var replayNavigator = ((Resource) getModel()).getReplayNavigator();
		final var currentEventPosition = replayNavigator.getCurrentEventPosition();
		final var currentTimeline = currentEventPosition.timeline();
		final var currentEvent = currentEventPosition.eventNumber();

		final var finalPosition = findNextTimelineWithValidEventNumber(currentTimeline, currentEvent, true);
		if (finalPosition == null) {
			return;
		}
		replayNavigator.moveToEvent(finalPosition);
	}

	private void collectTimelinesDFS(final GraphicalEditPart part, final List<TimelineEditPart> result) {

		if (part instanceof final TimelineEditPart tep) {
			result.add(tep);
		}

		for (final Object child : part.getChildren()) {
			if (child instanceof final GraphicalEditPart gep) {
				collectTimelinesDFS(gep, result);
			}
		}
	}

	private ReplayNavigator.EventPosition findNextTimelineWithValidEventNumber(final Timeline currentTimeline,
			final int eventNumber, final boolean reversed) {

		final List<TimelineEditPart> timelines = new ArrayList<>();

		// Collect all TimelineEditParts inside this ResourceEditPart
		collectTimelinesDFS(this, timelines);

		if (reversed) {
			Collections.reverse(timelines);
		}

		// Find index of current timeline
		int currentIndex = -1;
		for (int i = 0; i < timelines.size(); i++) {
			if (timelines.get(i).getModel() == currentTimeline) {
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

			final Timeline timeline = (Timeline) timelines.get(i).getModel();

			final int start = timeline.getGlobalIndexStart();
			final int end = timeline.getGlobalIndexEnd();

			if (currentGlobalEventNumber >= start && currentGlobalEventNumber <= end) {
				return new ReplayNavigator.EventPosition(timeline, currentGlobalEventNumber - start);
			}
		}

		return null;
	}

	public void moveDown() {
		final var replayNavigator = ((Resource) getModel()).getReplayNavigator();
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

}
