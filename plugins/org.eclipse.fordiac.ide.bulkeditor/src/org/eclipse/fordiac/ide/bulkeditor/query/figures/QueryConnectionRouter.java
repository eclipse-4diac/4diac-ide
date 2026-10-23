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
package org.eclipse.fordiac.ide.bulkeditor.query.figures;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/** Routes the connections from query nodes to their children. */
public class QueryConnectionRouter extends AbstractRouter {

	@Override
	public void route(final Connection connection) {
		final IFigure source = connection.getSourceAnchor().getOwner();
		final Rectangle sourceBounds = getAbsoluteBounds(source);
		final Rectangle targetBounds = getAbsoluteBounds(connection.getTargetAnchor().getOwner());

		final PointList points = connection.getPoints();
		points.removeAllPoints();
		if (source instanceof QueryPlaceNodeFigure) {
			// right, then down to the child
			final Point start = sourceBounds.getRight();
			final Point end = targetBounds.getTop();
			addPoints(connection, points, start, new Point(end.x, start.y), end);
		} else if (targetBounds.x > sourceBounds.x && targetBounds.y > sourceBounds.bottom()) {
			// down, then right to the child
			final Point start = sourceBounds.getBottom();
			final Point end = targetBounds.getLeft();
			addPoints(connection, points, start, new Point(start.x, end.y), end);
		} else {
			addPoints(connection, points, getStartPoint(connection).getCopy(), getEndPoint(connection).getCopy());
		}
		connection.setPoints(points);
	}

	private static Rectangle getAbsoluteBounds(final IFigure figure) {
		final Rectangle bounds = figure.getBounds().getCopy();
		figure.translateToAbsolute(bounds);
		return bounds;
	}

	private static void addPoints(final Connection connection, final PointList points, final Point... absolutePoints) {
		for (final Point point : absolutePoints) {
			connection.translateToRelative(point);
			points.addPoint(point);
		}
	}
}
