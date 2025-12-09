/*******************************************************************************
 * Copyright (c) 2008, 2012 Profactor GmbH, TU Wien ACIN
 * 				 2019 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added scrolling support to connection dragging
 *               - reworked connection selection and hover feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.policies;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Polyline;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.fordiac.ide.gef.handles.ScrollingConnectionEndpointHandle;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.requests.SelectionRequest;

/**
 * An EditPolicy for showing feedback when selected.
 *
 */
public class FeedbackConnectionEndpointEditPolicy extends ConnectionEndpointEditPolicy
		implements PropertyChangeListener {

	// the number of pixels the selection feedback should be wider then the
	// connection, needs to be an even number so
	// that the selection feedback is symmetric around the connection line
	private static final int SELECTION_FEEDBACK_SIZE_DELTA = 6;
	private IFigure selectionFeedback;
	private IFigure hoverFeedback;

	@Override
	public void activate() {
		super.activate();
		getConnection().addPropertyChangeListener(Connection.PROPERTY_POINTS, this);
	}

	@Override
	public void deactivate() {
		getConnection().removePropertyChangeListener(Connection.PROPERTY_POINTS, this);
		removeHoverFigure();
		removeSelectionFigure();
		super.deactivate();
	}

	@Override
	public void showSelection() {
		super.showSelection();
		getConnectionFigure().setLineWidth(ConnectionPreferenceValues.SELECTED_LINE_WIDTH);
		removeHoverFigure();
		if (null == selectionFeedback) {
			addSelectionFeedbackFigure();
		}
	}

	@Override
	public void hideSelection() {
		removeSelectionFigure();
		getConnectionFigure().setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
		super.hideSelection();
	}

	/**
	 * Gets the connection figure.
	 *
	 * @return the connection figure
	 */
	protected PolylineConnection getConnectionFigure() {
		return (PolylineConnection) getHost().getFigure();
	}

	@Override
	protected List<? extends ConnectionEndpointHandle> createSelectionHandles() {
		final List<ConnectionEndpointHandle> list = new ArrayList<>();
		list.add(createConnectionEndPointHandle((ConnectionEditPart) getHost(), ConnectionLocator.SOURCE));
		list.add(createConnectionEndPointHandle((ConnectionEditPart) getHost(), ConnectionLocator.TARGET));
		return list;
	}

	@SuppressWarnings("static-method") // allow subclasses to provide special versions of connection handles
	protected ConnectionEndpointHandle createConnectionEndPointHandle(final ConnectionEditPart connectionEditPart,
			final int connectionLocator) {
		return new ScrollingConnectionEndpointHandle(connectionEditPart, connectionLocator);
	}

	@Override
	public void showTargetFeedback(final Request request) {
		if ((request instanceof SelectionRequest) && (null == hoverFeedback) && (null == handles)) {
			final IFigure connFigure = getHostFigure();
			if ((connFigure instanceof final PolylineConnection plc) && (null == hoverFeedback)) {
				hoverFeedback = createSelectionFeedbackFigure(plc);
				addFeedback(hoverFeedback);
			}
		}
	}

	@Override
	public void eraseTargetFeedback(final Request request) {
		removeHoverFigure();
	}

	private void addSelectionFeedbackFigure() {
		selectionFeedback = createSelectionFeedbackFigure(getConnectionFigure());
		addFeedback(selectionFeedback);
	}

	protected IFigure getConnectionLayer() {
		return getLayer(LayerConstants.CONNECTION_LAYER);
	}

	private void removeHoverFigure() {
		if (null != hoverFeedback) {
			removeFeedback(hoverFeedback);
			hoverFeedback = null;
		}
	}

	private void removeSelectionFigure() {
		if (null != selectionFeedback) {
			removeFeedback(selectionFeedback);
			selectionFeedback = null;
		}
	}

	@SuppressWarnings("static-method") // allow sub-classe to provide their own selection feedback figure
	protected IFigure createSelectionFeedbackFigure(final PolylineConnection connFigure) {
		final Polyline figure = new Polyline();
		figure.setLineWidth(connFigure.getLineWidth() + SELECTION_FEEDBACK_SIZE_DELTA);
		figure.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		figure.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		figure.setPoints(connFigure.getPoints().getCopy());
		return figure;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if ((getHost().getSelected() != EditPart.SELECTED_NONE) && (null != selectionFeedback)) {
			removeSelectionFigure();
			// update the selection feedback figure to new points
			addSelectionFeedbackFigure();
		}

	}

	public boolean isSelectionFeedbackShowing() {
		return selectionFeedback != null;
	}

}
