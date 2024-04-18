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

import java.util.List;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.ConnectionEditPart;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnection;
import org.eclipse.fordiac.ide.application.figures.FBNetworkConnectionLabel;
import org.eclipse.fordiac.ide.application.figures.GroupInterfaceConnectionLabel;
import org.eclipse.fordiac.ide.application.handles.FBNConnectionEndPointHandle;
import org.eclipse.fordiac.ide.application.handles.HiddenFBNConnectionEndPointHandle;
import org.eclipse.fordiac.ide.gef.policies.FeedbackConnectionEndpointEditPolicy;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.preferences.DiagramPreferences;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.handles.ConnectionEndpointHandle;

public class FBNConnectionEndpointPolicy extends FeedbackConnectionEndpointEditPolicy {

	@Override
	protected ConnectionEndpointHandle createConnectionEndPointHandle(
			final org.eclipse.gef.ConnectionEditPart connectionEditPart, final int connectionLocator) {
		final FBNetworkConnection con = (FBNetworkConnection) connectionEditPart.getFigure();

		if (!con.isHidden() || isGroupCrossingEndPoint((ConnectionEditPart) connectionEditPart, connectionLocator)
				|| (connectionLocator == ConnectionLocator.SOURCE && con.getSourceDecoration() == null)
				|| (connectionLocator == ConnectionLocator.TARGET && con.getTargetDecoration() == null)) {
			return new FBNConnectionEndPointHandle(connectionEditPart, connectionLocator);
		}
		return new HiddenFBNConnectionEndPointHandle(connectionEditPart, connectionLocator);
	}

	@Override
	protected IFigure createSelectionFeedbackFigure(final PolylineConnection connFigure) {
		// in the FBN we always have a FBNetworkConnection
		final FBNetworkConnection con = (FBNetworkConnection) connFigure;
		if (!con.isHidden()) {
			return super.createSelectionFeedbackFigure(connFigure);
		}
		return createHiddenConnectionSelectionFeedbackFigure(con);

	}

	@Override
	public ConnectionEditPart getHost() {
		return (ConnectionEditPart) super.getHost();
	}

	@Override
	public void showSelection() {
		super.showSelection();
		setAssociatedConnectionsWidth(ConnectionPreferenceValues.HIGHLIGTHED_LINE_WIDTH);
	}

	@Override
	public void hideSelection() {
		super.hideSelection();
		setAssociatedConnectionsWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
	}

	private void setAssociatedConnectionsWidth(final int width) {
		final EditPart source = getHost().getSource();
		if (source instanceof AbstractGraphicalEditPart) {
			setConnectionsWidth(((AbstractGraphicalEditPart) source).getSourceConnections(), width);
		}
		final EditPart destination = getHost().getTarget();
		if (destination instanceof AbstractGraphicalEditPart) {
			setConnectionsWidth(((AbstractGraphicalEditPart) destination).getTargetConnections(), width);
		}
	}

	private void setConnectionsWidth(final List<? extends org.eclipse.gef.ConnectionEditPart> list, final int width) {
		list.forEach(ep -> {
			setConnectionWidth((ConnectionEditPart) ep, width);
		});
	}

	private void setConnectionWidth(final ConnectionEditPart ep, final int width) {
		if (ep != getHost() && !ep.isSelectionShown()) {
			ep.getConnectionFigure().setLineWidth(width);
		}
	}

	private static Figure createHiddenConnectionSelectionFeedbackFigure(final FBNetworkConnection con) {
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
				fireFigureMoved();
			}
		};
		if (con.getSourceDecoration() != null) {
			figure.add(createHidenSelectionFeedbackEndpoint(con.getSourceDecoration()));
		}
		if (con.getTargetDecoration() != null) {
			figure.add(createHidenSelectionFeedbackEndpoint(con.getTargetDecoration()));
		}
		return figure;
	}

	private static IFigure createHidenSelectionFeedbackEndpoint(final FBNetworkConnectionLabel label) {
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

	private static boolean isGroupCrossingEndPoint(final ConnectionEditPart connectionEditPart,
			final int connectionLocator) {
		return switch (connectionLocator) {
		case ConnectionLocator.SOURCE ->
			connectionEditPart.getFigure().getSourceDecoration() instanceof GroupInterfaceConnectionLabel;
		case ConnectionLocator.TARGET ->
			connectionEditPart.getFigure().getTargetDecoration() instanceof GroupInterfaceConnectionLabel;
		default -> false;
		};
	}

}