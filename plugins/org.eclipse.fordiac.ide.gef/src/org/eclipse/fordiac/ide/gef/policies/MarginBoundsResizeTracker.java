/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.fordiac.ide.gef.utilities.CollisionChangeBoundsRequest;
import org.eclipse.fordiac.ide.gef.utilities.TrackerMarginBoundsHelper;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;

public class MarginBoundsResizeTracker extends ResizeTracker {
	private final TrackerMarginBoundsHelper boundsHelper = new TrackerMarginBoundsHelper();

	public MarginBoundsResizeTracker(final GraphicalEditPart owner, final int direction) {
		super(owner, direction);
	}

	@Override
	protected Request createSourceRequest() {
		final ChangeBoundsRequest request = new CollisionChangeBoundsRequest(REQ_RESIZE, boundsHelper.getFigures());
		request.setResizeDirection(getResizeDirection());
		return request;
	}

	@Override
	protected boolean handleDragStarted() {
		boundsHelper.initDrag(getOwner(), getCurrentViewer().getSelectedEditParts());
		return super.handleDragStarted();
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
