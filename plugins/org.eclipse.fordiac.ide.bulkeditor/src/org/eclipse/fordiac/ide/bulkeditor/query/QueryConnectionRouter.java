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
package org.eclipse.fordiac.ide.bulkeditor.query;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

public class QueryConnectionRouter extends AbstractRouter {

	@Override
	public void route(final Connection conn) {
		final PointList points = conn.getPoints();
		points.removeAllPoints();

		final IFigure sourceFig = conn.getSourceAnchor().getOwner();
		final IFigure targetFig = conn.getTargetAnchor().getOwner();

		final Rectangle sourceBounds = getAbsoluteBounds(sourceFig);
		final Rectangle targetBounds = getAbsoluteBounds(targetFig);

		if (isPlaceFigure(sourceFig)) {
			// right then down
			routePlaceConnection(conn, points, sourceBounds, targetBounds);
		} else if (isTargetBelowAndRight(sourceBounds, targetBounds)) {
			// down then right
			routeRightAngleBend(conn, points, sourceBounds, targetBounds);
		} else {
			routeStraightLine(conn, points);
		}

		conn.setPoints(points);
	}

	private static void routePlaceConnection(final Connection conn, final PointList points, final Rectangle src,
			final Rectangle tgt) {
		addRelativePoint(conn, points, src.right(), src.y + src.height / 2);
		addRelativePoint(conn, points, tgt.x + tgt.width / 2, src.y + src.height / 2);
		addRelativePoint(conn, points, tgt.x + tgt.width / 2, tgt.y);
	}

	private static void routeRightAngleBend(final Connection conn, final PointList points, final Rectangle src,
			final Rectangle tgt) {
		addRelativePoint(conn, points, src.x + src.width / 2, src.bottom());
		addRelativePoint(conn, points, src.x + src.width / 2, tgt.y + tgt.height / 2);
		addRelativePoint(conn, points, tgt.x, tgt.y + tgt.height / 2);
	}

	private void routeStraightLine(final Connection conn, final PointList points) {
		Point p = getStartPoint(conn);
		conn.translateToRelative(p);
		points.addPoint(p);
		p = getEndPoint(conn);
		conn.translateToRelative(p);
		points.addPoint(p);
	}

	private static boolean isPlaceFigure(final IFigure fig) {
		return fig instanceof final QueryNodeFigure qnf && qnf.isPlaceNode();
	}

	private static boolean isTargetBelowAndRight(final Rectangle src, final Rectangle tgt) {
		return tgt.getTopLeft().x > src.getBottomLeft().x && tgt.getTopLeft().y > src.getBottomLeft().y;
	}

	private static Rectangle getAbsoluteBounds(final IFigure fig) {
		final Rectangle bounds = fig.getBounds().getCopy();
		fig.translateToAbsolute(bounds);
		return bounds;
	}

	private static void addRelativePoint(final Connection conn, final PointList points, final int x, final int y) {
		final Point p = new Point(x, y);
		conn.translateToRelative(p);
		points.addPoint(p);
	}
}
