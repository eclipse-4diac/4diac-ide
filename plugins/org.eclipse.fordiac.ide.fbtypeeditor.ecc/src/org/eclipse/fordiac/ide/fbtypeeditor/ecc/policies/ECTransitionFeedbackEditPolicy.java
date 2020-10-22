/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - spline based selection feedback to be better harmonized with
 *                 other selections
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures.SplineConnection;
import org.eclipse.fordiac.ide.gef.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;

public class ECTransitionFeedbackEditPolicy extends FeedbackConnectionEndpointEditPolicy {
	@Override
	protected IFigure createSelectionFeedbackFigure(PolylineConnection connFigure) {
		SplineConnection figure = new SplineConnection();
		figure.setLineWidth(connFigure.getLineWidth() + 5);
		figure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		figure.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		figure.setPoints(connFigure.getPoints().getCopy());
		return figure;
	}
}