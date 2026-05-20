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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.figure.EventMarkerFigure;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.EventMarker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
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
public class EventMarkerEditPart extends AbstractGraphicalEditPart
		implements EventMarkerFigure.SelectedEventListener, PropertyChangeListener {

	@Override
	protected IFigure createFigure() {
		final var eventMarker = (EventMarker) getModel();
		return new EventMarkerFigure(eventMarker.getIndex());
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
	protected void refreshVisuals() {
		super.refreshVisuals();
		final var figure = getEventMarkerFigure();
		figure.setIsValid(getEventMarkerModel().getValid());
		figure.setIsCurrentEvent(getEventMarkerModel().getIsCurrentEvent());
		figure.repaint();
	}

	private void safeRefresh() {
		final Display display = getViewer().getControl().getDisplay();
		display.asyncExec(() -> {
			if (isActive()) {
				refreshVisuals();
			}
		});
	}

	@Override
	protected void refreshChildren() {
		super.refreshChildren();
		((GraphicalEditPart) getViewer().getContents()).getFigure().invalidateTree();
	}

	private EventMarker getEventMarkerModel() {
		return (EventMarker) getModel();
	}

	private EventMarkerFigure getEventMarkerFigure() {
		return ((EventMarkerFigure) getFigure());
	}

	private void centerOnFigure() {
		if (!figure.isShowing()) {
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
		((EventMarker) getModel()).addPropertyChangeListener(this);
		getEventMarkerFigure().addEventSelectionListener(this);
	}

	@Override
	public void deactivate() {
		getEventMarkerFigure().removeEventSelectionListener(this);
		((EventMarker) getModel()).removePropertyChangeListener(this);
		super.deactivate();
	}

	// calls from the figure

	@Override
	public void eventSelected(final int eventIndex) {
		centerOnFigure();
		getEventMarkerModel().eventSelected();
	}

	// call from model

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(EventMarker.PROPERTY_EVENT_CHANGED)) {
			safeRefresh();
		}
	}

}
