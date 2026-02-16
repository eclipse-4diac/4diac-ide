/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.application.handles;

import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.application.editparts.TargetInterfaceElementEditPart;
import org.eclipse.fordiac.ide.application.tools.MultiTargetLabelConnectionDragTool;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.policies.ModifiedMoveHandle;
import org.eclipse.fordiac.ide.ui.preferences.ConnectionPreferenceValues;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.requests.CreateConnectionRequest;

public class TargetLabelConnectionHandle extends ConnectionHandle {
	private Connection connection;
	private InterfaceEditPart originalSourceEP;
	private FeedbackHelper feedbackHelper;

	public TargetLabelConnectionHandle(final GraphicalEditPart host, final InterfaceEditPart source) {
		setOwner(host);
		this.originalSourceEP = source;
		this.connection = createConnection(source);

		this.feedbackHelper = new FeedbackHelper();
		this.feedbackHelper.setMovingStartAnchor(true);
		this.feedbackHelper.setConnection(this.connection);

		setLocator(new ConnectionLocator(connection, ConnectionLocator.SOURCE) {
			@Override
			protected Point getLocation(final PointList points) {
				final Point p = super.getLocation(points);
				// Offset for Source connection endpoint
				p.x += getPreferredSize().width / 2;
				return p;
			}
		});
	}

	private Connection createConnection(final InterfaceEditPart originalSource) {
		final PolylineConnection newCon = new PolylineConnection();
		newCon.setLineWidth(ConnectionPreferenceValues.NORMAL_LINE_WIDTH);
		newCon.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		newCon.setTargetAnchor(new FixedAnchor(getOwnerFigure(), true));
		newCon.setSourceAnchor(originalSource.getSourceConnectionAnchor(new Request()));
		newCon.layout();
		return newCon;
	}

	public void update(final CreateConnectionRequest request) {
		final NodeEditPart node = request.getTargetEditPart() instanceof final NodeEditPart nodeEP ? nodeEP : null;
		final ConnectionAnchor anchor = node == null ? null : node.getSourceConnectionAnchor(request);
		feedbackHelper.update(anchor, request.getLocation());
	}

	public void reset() {
		feedbackHelper.update(originalSourceEP.getSourceConnectionAnchor(new Request()), null);
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

	@Override
	protected DragTracker createDragTracker() {
		// TODO: check for selection on different Interfaces
		final List<TargetInterfaceElementEditPart> selections = getOwner().getViewer().getSelectedEditParts().stream()
				.filter(TargetInterfaceElementEditPart.class::isInstance)
				.map(TargetInterfaceElementEditPart.class::cast).toList();

		return new MultiTargetLabelConnectionDragTool(selections, originalSourceEP);
	}

	@Override
	protected void init() {
		super.init();
		setPreferredSize((ConnectionPreferenceValues.HANDLE_SIZE * 4) / 3, ConnectionPreferenceValues.HANDLE_SIZE);
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

	@Override
	public void paintFigure(final Graphics g) {
		g.setLineStyle(Graphics.LINE_SOLID);
		g.setLineWidth((ModifiedMoveHandle.SELECTION_BORDER_WIDTH));
		g.setXORMode(false);
		g.setForegroundColor(ModifiedMoveHandle.getSelectionColor());
		g.setBackgroundColor(ModifiedMoveHandle.getSelectionColor());
		final int radius = (int) (ConnectionPreferenceValues.HANDLE_SIZE * 0.45);
		final Rectangle r = Rectangle.SINGLETON.setBounds(getBounds()).shrink(1, 1);
		g.drawRoundRectangle(r, radius, radius);
		g.setAlpha(ModifiedMoveHandle.SELECTION_FILL_ALPHA);
		g.fillRoundRectangle(r, radius, radius);
		g.setAlpha(255);

		paintInnerFigure(g, r);
	}

	private void paintInnerFigure(final Graphics g, final Rectangle r) {
		final int shrink = getInnerShrinkVal();
		r.shrink(new Insets(shrink + 1, 0, shrink + 1, shrink * 2));
		g.fillRoundRectangle(r, r.height / 2, r.height / 2);
	}

	private double getZoomFactor() {
		return ((ScalableFreeformRootEditPart) getOwner().getRoot()).getZoomManager().getZoom();
	}

	private int getInnerShrinkVal() {
		int shrinkVal = 2;
		if (shrinkVal * getZoomFactor() < 2) {
			shrinkVal = (int) (2 / getZoomFactor());
		}
		return shrinkVal;
	}
}