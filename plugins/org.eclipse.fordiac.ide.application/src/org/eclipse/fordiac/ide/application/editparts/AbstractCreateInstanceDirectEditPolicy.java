/*******************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.editparts;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

abstract class AbstractCreateInstanceDirectEditPolicy extends DirectEditPolicy {

	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		final Object value = request.getCellEditor().getValue();
		final Point refPoint = getInsertionPoint(request);
		if (value instanceof final TypeEntry typeEntry) {
			return getElementCreateCommand(typeEntry, refPoint);
		}
		return null;
	}

	protected abstract Command getElementCreateCommand(TypeEntry value, Point refPoint);

	private Point getInsertionPoint(final DirectEditRequest request) {
		final Point refPoint = new Point(FBNetworkRootEditPart.getInsertPos(request, getHost().getViewer(), getZoom()));
		final Point topLeft = getHost().getFigure().getClientArea().getTopLeft();
		refPoint.translate(-topLeft.x, -topLeft.y);
		return refPoint;
	}

	private double getZoom() {
		final RootEditPart root = getHost().getRoot();
		if (root instanceof final ScalableFreeformRootEditPart scalableEditPart) {
			return scalableEditPart.getZoomManager().getZoom();
		}
		return 1.0;
	}

	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		// we don't need to do anything here for creating new fb instances
	}

}