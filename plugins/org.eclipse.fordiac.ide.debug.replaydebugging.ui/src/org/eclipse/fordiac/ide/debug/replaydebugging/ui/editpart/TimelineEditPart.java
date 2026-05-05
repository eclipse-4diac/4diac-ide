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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.DatapointsState;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.ReplayNavigator.StateListener;
import org.eclipse.fordiac.ide.debug.replaydebugging.core.Timeline;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.TimelineAnchor;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.TimelineFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.TimelineFigure.SelectedEventListener;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.TimelineWithChildrenFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.Session;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineConnection;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.swt.widgets.Display;

/**
 * @brief EditPart for a timeline in the replay debugging view.
 *
 *        It can be selected, allowing keyboard navigation to work. It uses a
 *        TimlineWithChildrenFigure as its figure, returning the children figure
 *        as content pane for the child timelines. It listens to the timeline
 *        for new events and new spawned timelines, refreshing itself
 *        accordingly. It also listens to the replay navigator for state
 *        changes, refreshing the event states when they happen.
 *
 *        The edit part also centers the current event in the view when it
 *        changes, but only if the current event is outside of the current view
 *        area.
 */
public class TimelineEditPart extends AbstractGraphicalEditPart implements NodeEditPart, Timeline.NewEventListener,
		Timeline.NewSpawnedTimelineListener, SelectedEventListener, StateListener {

	private ReplayNavigator replayNavigator;

	@Override
	protected IFigure createFigure() {
		final var timeline = (Timeline) getModel();
		replayNavigator = ((Session) getViewer().getContents().getModel()).getResource(timeline).getReplayNavigator();
		return new TimelineWithChildrenFigure(timeline);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new NonResizableEditPolicy() {
			@Override
			public void showSelection() {
				// Redirect selection to the parent
				final EditPart parent = getHost().getParent();
				if (parent != null && parent.getViewer() != null) {
					parent.getViewer().select(parent);
				}
			}
		});
	}

	@Override
	protected List<?> getModelChildren() {
		final Timeline model = (Timeline) getModel();
		final ArrayList<Timeline> timelines = new ArrayList<>(model.getSpawnedTimelines());

		Collections.sort(timelines,
				(timeline1, timeline2) -> Integer.compare(model.getSpawnedTimelineEventNumber(timeline2),
						model.getSpawnedTimelineEventNumber(timeline1)));
		return timelines;
	}

	@Override
	public IFigure getContentPane() {
		return ((TimelineWithChildrenFigure) getFigure()).getChildrenFigure();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateCurrentEvent();
	}

	@Override
	protected void refreshChildren() {
		super.refreshChildren();
		((GraphicalEditPart) getViewer().getContents()).getFigure().invalidateTree();
	}

	private TimelineFigure getTimelineFigure() {
		return ((TimelineWithChildrenFigure) getFigure()).getTimelineFigure();
	}

	private void updateCurrentEvent() {
		final var fig = getTimelineFigure();

		final var currentPosition = replayNavigator.getCurrentEventPosition();

		final Timeline model = (Timeline) getModel();

		var currentTimeline = currentPosition.timeline();
		var firstInvalid = currentPosition.eventNumber() + 1;

		while (currentTimeline != model) {
			final var parentTimeline = currentTimeline.getParentTimeline();
			if (parentTimeline == null) {
				firstInvalid = 0;
				break;
			}
			firstInvalid = parentTimeline.getSpawnedTimelineEventNumber(currentTimeline) + 1;
			currentTimeline = currentTimeline.getParentTimeline();
		}

		fig.updateEventStates(firstInvalid,
				currentPosition.timeline() == getModel() ? currentPosition.eventNumber() : -1);

		centerOnFigure(fig.getCurrentSelectedEventFigure());
	}

	private void centerOnFigure(final IFigure figure) {
		if (figure == null || !figure.isShowing()) {
			return;
		}

		final ScalableRootEditPart root = (ScalableRootEditPart) getViewer().getRootEditPart();

		final Viewport viewport = (Viewport) root.getFigure();
		final IFigure contents = viewport.getContents();

		// Convert figure bounds → viewport content coordinates
		final Rectangle bounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(bounds);
		contents.translateToRelative(bounds);

		final Rectangle viewArea = viewport.getClientArea();

		final int centerX = bounds.x + bounds.width / 2;
		final int centerY = bounds.y + bounds.height / 2;

		final int viewLeft = viewArea.x;
		final int viewTop = viewArea.y;
		final int viewRight = viewLeft + viewArea.width;
		final int viewBottom = viewTop + viewArea.height;

		// Only center if center point is outside
		final int margin = 20;

		if (centerX >= viewLeft + margin && centerX <= viewRight - margin && centerY >= viewTop + margin
				&& centerY <= viewBottom - margin) {
			return;
		}

		int targetX = centerX - viewArea.width / 2;
		int targetY = centerY - viewArea.height / 2;

		final int maxX = contents.getBounds().width - viewArea.width;
		final int maxY = contents.getBounds().height - viewArea.height;

		targetX = Math.clamp(targetX, 0, Math.max(0, maxX));
		targetY = Math.clamp(targetY, 0, Math.max(0, maxY));

		viewport.setHorizontalLocation(targetX);
		viewport.setVerticalLocation(targetY);
	}

	@Override
	public void activate() {
		super.activate();
		final Timeline timeline = (Timeline) getModel();
		timeline.addNewEventListener(this);
		timeline.addNewSpawnedTimelineListener(this);

		replayNavigator.addStateChangeListener(this);
		getTimelineFigure().addEventSelectionListener(this);
	}

	@Override
	public void deactivate() {
		final Timeline model = (Timeline) getModel();
		model.removeNewEventListener(this);
		model.removeNewSpawnedTimelineListener(this);

		replayNavigator.removeStateChangeListener(this);
		getTimelineFigure().removeEventSelectionListener(this);
		super.deactivate();
	}

	private void safeRefresh(final boolean newTimelines, final boolean newEvents) {
		final Display display = getViewer().getControl().getDisplay();
		display.asyncExec(() -> {
			if (isActive()) {

				if (newEvents) {
					getTimelineFigure().rebuildMarkers();
				}
				if (newTimelines) {
					refreshChildren();
					refreshSourceConnections();
				}
				refreshVisuals();
			}
		});
	}

	// calls from the model

	@Override
	public void timelineSpawned(final Timeline spawnedTimeline) {
		final Timeline timeline = (Timeline) getModel();
		((Session) getViewer().getContents().getModel()).getResource(timeline).addTimeline(spawnedTimeline);
		safeRefresh(true, false);
	}

	@Override
	public void eventAdded(final Timeline timeline) {
		safeRefresh(false, true);
	}

	@Override
	public void stateUpdated(final ReplayNavigator replayNavigator, final DatapointsState changedValues) {
		safeRefresh(false, false);
	}

	// calls from the figure

	@Override
	public void eventSelected(final Timeline timeline, final int eventIndex) {
		replayNavigator.moveToEvent(new ReplayNavigator.EventPosition(timeline, eventIndex));
	}

	// connections and anchors

	@Override
	public List<?> getModelSourceConnections() {
		final Timeline timeline = (Timeline) getModel();
		return ((Session) getViewer().getContents().getModel()).getResource(timeline).getSources(timeline);
	}

	@Override
	public List<?> getModelTargetConnections() {
		final Timeline timeline = (Timeline) getModel();
		return ((Session) getViewer().getContents().getModel()).getResource(timeline).getTargets(timeline);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		final TimelineConnection conn = (TimelineConnection) connection.getModel();
		return new TimelineAnchor(getTimelineFigure(), conn.spawnedIndex(), (Timeline) getModel(), true);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new TimelineAnchor(getTimelineFigure(), 0, (Timeline) getModel(), false);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return null;
	}

}
