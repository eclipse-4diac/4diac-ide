/*******************************************************************************
 * Copyright (c) 2026 Jose Cabral
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.debug.replaydebugging.ui.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.fordiac.ide.debug.replaydebugging.ui.model.TimelineConnection;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * @brief EditPart for the connections in the timeline view.
 *
 *        It listens to changes in the TimelineConnection model and updates the
 *        connection's color accordingly. The connection is drawn as a vertical
 *        line with a horizontal segment connecting the source and target.
 */
public class TimelineConnectionEditPart extends AbstractConnectionEditPart implements PropertyChangeListener {

	public class VerticalTreeRouter extends AbstractRouter {
		@Override
		public void route(final Connection conn) {
			final Point start = getStartPoint(conn);
			final Point end = getEndPoint(conn);

			conn.translateToRelative(start);
			conn.translateToRelative(end);

			final PointList points = new PointList();

			points.addPoint(start);

			points.addPoint(new Point(start.x, end.y));
			points.addPoint(end);

			conn.setPoints(points);
		}
	}

	@Override
	protected IFigure createFigure() {
		final PolylineConnection conn = new PolylineConnection();
		conn.setTargetDecoration(null);
		conn.setSourceDecoration(null);
		conn.setLineWidth(4);
		conn.setConnectionRouter(new VerticalTreeRouter());
		conn.setAntialias(SWT.ON);
		return conn;
	}

	@Override
	public void activate() {
		if (!isActive()) {
			super.activate();
			((TimelineConnection) getModel()).addPropertyChangeListener(this);
		}
	}

	@Override
	public void deactivate() {
		if (isActive()) {
			((TimelineConnection) getModel()).removePropertyChangeListener(this);
			super.deactivate();
		}
	}

	@Override
	protected void refreshVisuals() {
		final Connection connection = getConnectionFigure();
		// Assuming your model has a getColor() method
		final Color newColor = ((TimelineConnection) getModel()).isInCurrentPosition() ? ColorConstants.black
				: ColorConstants.gray;
		connection.setForegroundColor(newColor);
	}

	@Override
	protected void createEditPolicies() {
		// no policy needed for now
	}

	private void safeRefresh() {
		final Display display = getViewer().getControl().getDisplay();
		if (display.getThread() == Thread.currentThread()) {
			refreshChildren();
			return;
		}

		display.asyncExec(() -> {
			if (isActive()) {
				refreshChildren();
			}
		});
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(TimelineConnection.PROPERTY_TIMELINECONNECTION_CHANGED)) {
			safeRefresh();
		}
	}

}
