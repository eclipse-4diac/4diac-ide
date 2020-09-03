/*******************************************************************************
 * Copyright (c) 2020 Primetals Technology Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class ConnectionHoverEditPolicy extends GraphicalEditPolicy {

	@Override
	public void eraseTargetFeedback(Request request) {
		getHostFigure().setCursor(null);
		if (null != selectionFeedback) {
			removeFeedback(selectionFeedback);
			selectionFeedback = null;
		}
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		return request.getType().equals(RequestConstants.REQ_SELECTION_HOVER) ? getHost() : null;
	}

	private IFigure selectionFeedback;

	@Override
	public void showTargetFeedback(Request request) {
		if (request instanceof SelectionRequest) {
			IFigure connFigure = getHostFigure();
			connFigure.setCursor(Display.getDefault().getSystemCursor(SWT.CURSOR_HAND));
			if ((connFigure instanceof PolylineConnection) && (null == selectionFeedback)) {
				selectionFeedback = createSelectionFeedbackFigure((PolylineConnection) connFigure);
				addFeedback(selectionFeedback);
			}
		}
	}

	private IFigure createSelectionFeedbackFigure(PolylineConnection connFigure) {
		Polyline figure = new Polyline();
		double zoomFactor = getZoomFactor();
		figure.setLineWidth((int) ((connFigure.getLineWidth() + 5) * zoomFactor));
		figure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		figure.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		figure.setPoints(getConnPoints(connFigure, zoomFactor));
		return figure;
	}

	private double getZoomFactor() {
		return ((ScalableFreeformRootEditPart) getHost().getRoot()).getZoomManager().getZoom();
	}

	private static PointList getConnPoints(PolylineConnection connFigure, double zoomFactor) {
		PointList pointList = connFigure.getPoints().getCopy();
		pointList.performScale(zoomFactor);
		return (pointList);
	}
}
