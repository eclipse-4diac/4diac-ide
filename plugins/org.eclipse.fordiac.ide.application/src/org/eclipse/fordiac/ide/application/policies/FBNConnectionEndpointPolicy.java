/*******************************************************************************
 * Copyright (c) 2020, 2021 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.handles.FBNConnectionEndPointHandle;
import org.eclipse.fordiac.ide.application.handles.HiddenFBNConnectionEndPointHandle;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection.ConnectionLabel;
import org.eclipse.fordiac.ide.gef.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.gef.handles.ConnectionEndpointHandle;

public class FBNConnectionEndpointPolicy extends FeedbackConnectionEndpointEditPolicy {

	@Override
	protected ConnectionEndpointHandle createConnectionEndPointHandle(
			final org.eclipse.gef.ConnectionEditPart connectionEditPart, final int connectionLocator) {
		final HideableConnection con = (HideableConnection) connectionEditPart.getFigure();

		if (!con.isHidden()) {
			return new FBNConnectionEndPointHandle(connectionEditPart, connectionLocator);
		}
		return new HiddenFBNConnectionEndPointHandle(connectionEditPart, connectionLocator);
	}

	@Override
	protected IFigure createSelectionFeedbackFigure(final PolylineConnection connFigure) {
		// in the FBN we always have a HideableConnection
		final HideableConnection con = (HideableConnection) connFigure;
		if (!con.isHidden()) {
			return super.createSelectionFeedbackFigure(connFigure);
		}
		return createHiddenConnectionSelectionFeedbackFigure(con);

	}

	private static Figure createHiddenConnectionSelectionFeedbackFigure(final HideableConnection con) {
		final Figure figure = new Figure() {
			@Override
			public Rectangle getBounds() {
				if (bounds == null) {
					bounds = new Rectangle();
					getChildren().forEach(el -> bounds.union(((IFigure) el).getBounds()));
				}
				return bounds;
			}

			@Override
			public void layout() {
				// on layout request just recalculate the bounds and draw it
				bounds = null;
				getBounds();
				// repaint();
				fireFigureMoved();
			}
		};
		figure.add(createHidenSelectionFeedbackEndpoint(con.getSourceDecoration()));
		figure.add(createHidenSelectionFeedbackEndpoint(con.getTargetDecoration()));
		return figure;
	}

	private static IFigure createHidenSelectionFeedbackEndpoint(final ConnectionLabel label) {
		final RoundedRectangle newSelFeedbackFigure = new RoundedRectangle();
		newSelFeedbackFigure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		newSelFeedbackFigure.setOutline(false);
		newSelFeedbackFigure.setBounds(getSelectableFigureBounds(label));
		newSelFeedbackFigure
		.setCornerDimensions(new Dimension(DiagramPreferences.CORNER_DIM, DiagramPreferences.CORNER_DIM));
		newSelFeedbackFigure.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		newSelFeedbackFigure.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
		return newSelFeedbackFigure;
	}

	private static Rectangle getSelectableFigureBounds(final IFigure figure) {
		return figure.getBounds().getExpanded(2, 2);
	}
}