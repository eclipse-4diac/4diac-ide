/*******************************************************************************
 * Copyright (c) 2021, 2024 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.editors.NewInstanceDirectEditManager;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.SelectionRequest;

public abstract class AbstractCreateInstanceDirectEditPolicy extends DirectEditPolicy {

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

	public void performDirectEdit(final SelectionRequest request) {
		final NewInstanceDirectEditManager directEditManager = createDirectEditManager();
		directEditManager.updateRefPosition(
				new org.eclipse.swt.graphics.Point(request.getLocation().x, request.getLocation().y));
		if (request.getExtendedData().isEmpty()) {
			directEditManager.show();
		} else if (request.getExtendedData().keySet().iterator().next() instanceof final String key) {
			directEditManager.show(key);
		}
	}

	private NewInstanceDirectEditManager createDirectEditManager() {
		return new NewInstanceDirectEditManager(getHost(), getTypeLibrary(), false);
	}

	private TypeLibrary getTypeLibrary() {
		return TypeLibraryManager.INSTANCE.getTypeLibraryFromContext(getModel());
	}

	private EObject getModel() {
		return (EObject) getHost().getModel();
	}

	private Point getInsertionPoint(final DirectEditRequest request) {
		final Point refPoint = getInsertPos(request);
		final SnapToHelper helper = getHost().getAdapter(SnapToHelper.class);
		if (helper != null) {
			getHost().getFigure().translateToAbsolute(refPoint);
			final PrecisionPoint preciseLocation = new PrecisionPoint(refPoint);
			final PrecisionPoint result = new PrecisionPoint(refPoint);
			helper.snapPoint(null, PositionConstants.HORIZONTAL | PositionConstants.VERTICAL, preciseLocation, result);
			getHost().getFigure().translateToRelative(result);
			return result;
		}

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

	public Point getInsertPos(final LocationRequest request) {
		final Point location = request.getLocation();
		final FigureCanvas figureCanvas = (FigureCanvas) getHost().getViewer().getControl();
		final org.eclipse.draw2d.geometry.Point viewLocation = figureCanvas.getViewport().getViewLocation();
		location.x += viewLocation.x;
		location.y += viewLocation.y;
		return new Point(location.x, location.y).scale(1.0 / getZoom());
	}

}