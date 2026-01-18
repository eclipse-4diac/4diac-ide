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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.DragEditPartsTracker;

public class ScrollingDragEditPartsTracker extends DragEditPartsTracker {
	private final TrackerMarginBoundsHelper boundsHelper = new TrackerMarginBoundsHelper();
	EditPartViewer initialViewer;

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
	protected boolean handleDragStarted() {
		initialViewer = getCurrentViewer();
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
		if (differentTargetViewer()) {
			final Point location = getLocation();
			final Point converted = new Point(initialViewer.getControl()
					.toControl(getCurrentViewer().getControl().toDisplay(location.x, location.y)));
			getTargetRequest().setLocation(converted);
			getTargetRequest().getExtendedData().clear();
			final Dimension moveDelta = getLocation().getDifference(converted);
			getTargetRequest().setMoveDelta(new Point(moveDelta));
			snapPoint(getTargetRequest());
		}
		boundsHelper.createFigures(getTargetEditPart());
		super.showSourceFeedback();
		if (differentTargetViewer()) {
			updateTargetRequest();
		}
	}

	private boolean differentTargetViewer() {
		return initialViewer != null && initialViewer != getCurrentViewer();
	}
}