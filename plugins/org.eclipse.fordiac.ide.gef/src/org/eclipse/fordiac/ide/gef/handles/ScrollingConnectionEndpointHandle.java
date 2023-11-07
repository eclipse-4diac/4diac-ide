/*******************************************************************************
 * Copyright (c) 2019, 2022 Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - added scrolling support to connection dragging
 *               - reworked connection selection and hover feedback
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.handles;

import java.util.List;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.gef.tools.ScrollingConnectionEndpointTracker;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.tools.ConnectionEndpointTracker;

public class ScrollingConnectionEndpointHandle extends ConnectionEndpointHandle {

	public ScrollingConnectionEndpointHandle(final ConnectionEditPart owner, final int endPoint) {
		super(owner, endPoint);
	}

	@Override
	protected void init() {
		super.init();
		setPreferredSize(ConnectionPreferenceValues.HANDLE_SIZE, ConnectionPreferenceValues.HANDLE_SIZE);
	}

	@Override
	public Dimension getPreferredSize(final int wHint, final int hHint) {
		final Dimension dim = super.getPreferredSize(wHint, hHint).getCopy();

		final double zoomFactor = getZoomFactor();
		if (dim.height * zoomFactor < ConnectionPreferenceValues.MIN_HANDLE_SIZE) {
			dim.height = (int) (ConnectionPreferenceValues.MIN_HANDLE_SIZE / zoomFactor);
		}
		if (dim.width * zoomFactor < ConnectionPreferenceValues.MIN_HANDLE_SIZE) {
			dim.width = (int) (ConnectionPreferenceValues.MIN_HANDLE_SIZE / zoomFactor);
		}
		return dim;
	}

	protected double getZoomFactor() {
		return ((ScalableFreeformRootEditPart) getOwner().getRoot()).getZoomManager().getZoom();
	}

	@Override
	protected ConnectionEditPart getOwner() {
		return (ConnectionEditPart) super.getOwner();
	}

	@Override
	protected DragTracker createDragTracker() {
		if (isFixed()) {
			return null;
		}
		final ConnectionEndpointTracker tracker = createConnectionEndPointTracker(getCoSelectedConnections());
		configureConnTracker(tracker);
		return tracker;
	}

	private void configureConnTracker(final ConnectionEndpointTracker tracker) {
		if (getEndPoint() == ConnectionLocator.SOURCE) {
			tracker.setCommandName(RequestConstants.REQ_RECONNECT_SOURCE);
		} else {
			tracker.setCommandName(RequestConstants.REQ_RECONNECT_TARGET);
		}
		tracker.setDefaultCursor(getCursor());
	}

	@SuppressWarnings("static-method") // allow sub-classes to provide special versions
	protected ConnectionEndpointTracker createConnectionEndPointTracker(
			final List<ConnectionEditPart> coSelectedConnections) {
		return new ScrollingConnectionEndpointTracker(coSelectedConnections.get(0));
	}

	@Override
	public void paintFigure(final Graphics g) {
		final Rectangle r = getBounds().getShrinked(1, 1);
		g.setLineStyle(Graphics.LINE_SOLID);
		g.setLineWidth((ModifiedMoveHandle.SELECTION_BORDER_WIDTH));
		g.setXORMode(false);
		g.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		g.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
		final int radius = getCornerRadius();
		if (isPrimary()) {
			// only draw the border for the primary connection selection
			g.drawRoundRectangle(r, radius, radius);
		}
		g.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		g.fillRoundRectangle(r, radius, radius);
		g.setAlpha(255);

		paintHandleCenter(g, r);
	}

	@SuppressWarnings("static-method") // allow sub-classes to provide a different radius
	protected int getCornerRadius() {
		return (int) (ConnectionPreferenceValues.HANDLE_SIZE * 0.45);
	}

	protected void paintHandleCenter(final Graphics g, final Rectangle r) {
		final int shrinkVal = getInnerShrinkVal();
		r.shrink(shrinkVal, shrinkVal);
		g.fillRoundRectangle(r, r.height / 2, r.height / 2);
	}

	protected int getInnerShrinkVal() {
		int shrinkVal = 2;
		if (shrinkVal * getZoomFactor() < 2) {
			shrinkVal = (int) (2 / getZoomFactor());
		}
		return shrinkVal;
	}

	private List<ConnectionEditPart> getCoSelectedConnections() {
		final EditPart refEndPoint = getEndPoint(getOwner());
		final List<Object> selectedEditParts = getOwner().getViewer().getSelectedEditParts();
		return selectedEditParts.stream().filter(ConnectionEditPart.class::isInstance)
				.map(ConnectionEditPart.class::cast).filter(ep -> sameTarget(ep, refEndPoint)).toList();

	}

	private boolean sameTarget(final ConnectionEditPart ep, final EditPart refEndPoint) {
		final EditPart endPoint = getEndPoint(ep);
		return endPoint == refEndPoint;
	}

	private EditPart getEndPoint(final ConnectionEditPart ep) {
		return (getEndPoint() == ConnectionLocator.SOURCE) ? ep.getSource() : ep.getTarget();
	}
}