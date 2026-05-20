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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.CommonConstants;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.TimelineAnchor;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.TimelineWithChildrenFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.EventMarker;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineConnection;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineModel;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
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
public class TimelineEditPart extends AbstractGraphicalEditPart implements NodeEditPart, PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		final var model = (TimelineModel) getModel();
		return new TimelineWithChildrenFigure(model.getGlobalStartPosition(), model.getEventMarkers().size());
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
		final var model = (TimelineModel) getModel();

		return Stream.concat(model.getSpawnedTimelineModels().stream(), model.getEventMarkers().stream())
				.collect(Collectors.toList());
	}

	@Override
	public IFigure getContentPane() {
		return ((TimelineWithChildrenFigure) getFigure()).getSpawnedTimelinesPane();
	}

	@Override
	protected void addChildVisual(final EditPart childEditPart, final int index) {
		final var fig = (TimelineWithChildrenFigure) getFigure();

		if (childEditPart instanceof final EventMarkerEditPart marker) {
			final IFigure markerFigure = ((GraphicalEditPart) childEditPart).getFigure();

			fig.getTimelineFigure().add(markerFigure);

			final int x = (((EventMarker) marker.getModel()).getIndex()
					+ ((TimelineModel) getModel()).getGlobalStartPosition()) * CommonConstants.TOTAL_MARKER_SPACE;

			fig.getTimelineFigure().setConstraint(markerFigure,
					new Rectangle(x, 0, CommonConstants.MARKER_SIZE, CommonConstants.MARKER_SIZE));

		} else {
			fig.getSpawnedTimelinesPane().add(((GraphicalEditPart) childEditPart).getFigure());
		}
	}

	@Override
	protected void removeChildVisual(final EditPart childEditPart) {
		final var fig = (TimelineWithChildrenFigure) getFigure();

		if (childEditPart instanceof EventMarkerEditPart) {
			final IFigure markerFigure = ((GraphicalEditPart) childEditPart).getFigure();
			fig.getSpawnedTimelinesPane().remove(markerFigure);
		} else {
			fig.getSpawnedTimelinesPane().remove(((GraphicalEditPart) childEditPart).getFigure());
		}
	}

	@Override
	protected void refreshChildren() {
		super.refreshChildren();

		// remove and add all timelines to sort them when a new one is added
		final var fig = (TimelineWithChildrenFigure) getFigure();
		final IFigure pane = fig.getSpawnedTimelinesPane();

		final List<IFigure> children = new ArrayList<>(pane.getChildren());
		children.forEach(pane::remove);

		getModelChildren().stream().filter(TimelineModel.class::isInstance)
				.map(m -> (GraphicalEditPart) getViewer().getEditPartRegistry().get(m)).filter(Objects::nonNull)
				.forEach(ep -> pane.add(ep.getFigure()));

		pane.revalidate();
		pane.repaint();
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		final var fig = (TimelineWithChildrenFigure) getFigure();
		fig.getLineFigure().setFirstInvalid(((TimelineModel) getModel()).getFirstInvalid());
	}

	@Override
	public void activate() {
		super.activate();
		((TimelineModel) getModel()).addPropertyChangeListener(this);
	}

	@Override
	public void deactivate() {
		((TimelineModel) getModel()).removePropertyChangeListener(this);
		((TimelineModel) getModel()).dispose();
		super.deactivate();
	}

	private static void revalidateUpToRoot(IFigure figure) {
		while (figure != null) {
			figure.revalidate();
			figure = figure.getParent();
		}
	}

	private void safeRefresh(final boolean newTimelines, final boolean newEvents) {
		final Display display = getViewer().getControl().getDisplay();
		display.asyncExec(() -> {
			if (isActive()) {
				if (newTimelines || newEvents) {
					final var fig = ((TimelineWithChildrenFigure) getFigure());
					fig.updateMaxNumberOfEvents(((TimelineModel) getModel()).getEventMarkers().size());
					refreshChildren();
					refreshSourceConnections();

					revalidateUpToRoot(fig); // invalidate the whole chain
					fig.revalidate(); // then queue the layout pass
				}
				refreshVisuals();
			}
		});
	}

	// connections and anchors

	@Override
	public List<?> getModelSourceConnections() {
		return ((TimelineModel) getModel()).getSources();
	}

	@Override
	public List<?> getModelTargetConnections() {
		return ((TimelineModel) getModel()).getTargets();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final ConnectionEditPart connection) {
		final TimelineConnection conn = (TimelineConnection) connection.getModel();
		return new TimelineAnchor(((TimelineWithChildrenFigure) getFigure()).getLineFigure(), conn.spawnedIndex(),
				((TimelineModel) getModel()).getEventMarkers().size(), true);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final ConnectionEditPart connection) {
		return new TimelineAnchor(((TimelineWithChildrenFigure) getFigure()).getLineFigure(), 0,
				((TimelineModel) getModel()).getEventMarkers().size(), false);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(final Request request) {
		return null;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(TimelineModel.PROPERTY_EVENT_ADDED)) {
			safeRefresh(false, true);
		} else if (evt.getPropertyName().equals(TimelineModel.PROPERTY_TIMELINE_ADDED)) {
			safeRefresh(true, false);
		} else if (evt.getPropertyName().equals(TimelineModel.PROPERTY_STATE_CHANGED)) {
			safeRefresh(false, false);
		}
	}

}
