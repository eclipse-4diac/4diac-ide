/*******************************************************************************
 * Copyright (c) 2019, 2025 Johannes Kepler University Linz, Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Sebastian Hollersbacher - Added Figures with Margin to other Elements
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.tools;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.utilities.CollisionChangeBoundsRequest;
import org.eclipse.fordiac.ide.gef.utilities.TrackerMarginBoundsHelper;
import org.eclipse.fordiac.ide.model.ui.editors.AdvancedScrollingGraphicalViewer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.swt.events.MouseEvent;

public class ScrollingDragEditPartsTracker extends DragEditPartsTracker {
	private final TrackerMarginBoundsHelper boundsHelper = new TrackerMarginBoundsHelper();

	public ScrollingDragEditPartsTracker(final EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	@Override
	protected Request createTargetRequest() {
		if (isCloneActive()) {
			return new ChangeBoundsRequest(REQ_CLONE);
		}
		return new CollisionChangeBoundsRequest(REQ_MOVE, boundsHelper.getFigures(), boundsHelper.getParentFigure());
	}

	@Override
	public void mouseDrag(final MouseEvent me, final EditPartViewer viewer) {
		if (isActive() && viewer instanceof final AdvancedScrollingGraphicalViewer scrollingViewer) {
			final Point oldViewPort = scrollingViewer.getViewLocation();
			((AdvancedScrollingGraphicalViewer) viewer).checkScrollPositionDuringDrag(me);
			final Dimension delta = oldViewPort.getDifference(scrollingViewer.getViewLocation());
			// Compensate the moved scrolling in the start position for correct dropping of
			// moved parts
			setStartLocation(getStartLocation().getTranslated(delta));
		}
		super.mouseDrag(me, viewer);
	}

	@Override
	protected boolean handleDragStarted() {
		boundsHelper.initDrag(getSourceEditPart(), getCurrentViewer().getSelectedEditParts());
		return super.handleDragStarted();
	}

	@Override
	protected void setCloneActive(final boolean cloneActive) {
		super.setCloneActive(cloneActive);
		boundsHelper.createFigures(getTargetEditPart());
	}

	@Override
	protected void eraseSourceFeedback() {
		boundsHelper.clearFigureList();
		super.eraseSourceFeedback();
	}

	@Override
	protected void showSourceFeedback() {
		boundsHelper.createFigures(getTargetEditPart());
		super.showSourceFeedback();
	}
}