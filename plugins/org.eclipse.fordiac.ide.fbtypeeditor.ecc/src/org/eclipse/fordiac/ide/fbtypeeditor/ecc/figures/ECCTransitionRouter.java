/*******************************************************************************
 * Copyright (c) 2026 Vikash Kumar Sinha
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Vikash Kumar Sinha - initial implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.figures;

import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Vector;

public class ECCTransitionRouter extends BendpointConnectionRouter {

	private static final double CTRL_POINT_FACTOR = 0.3;
	private static final double MIN_LENGTH = 1.0;
	private static final double EPSILON = 0.001;
	private static final double MAX_HANDLE_DISTANCE = 150.0;
	private static final double SELF_LOOP_ARC_FACTOR = 1.1;

	@Override
	public void route(final Connection conn) {

		if (!(conn instanceof ECTransitionFigure) || !isValidAnchor(conn.getSourceAnchor())
				|| !isValidAnchor(conn.getTargetAnchor())) {
			super.route(conn);
			return;
		}

		final Object constraint = getConstraint(conn);
		if (!(constraint instanceof final List<?> bendpoints) || bendpoints.isEmpty()
				|| !(bendpoints.get(0) instanceof final Bendpoint bp)) {
			return;
		}

		final PrecisionPoint p4Model = new PrecisionPoint(bp.getLocation());
		conn.translateToAbsolute(p4Model);

		final PrecisionPoint p1 = new PrecisionPoint(conn.getSourceAnchor().getLocation(p4Model));
		final PrecisionPoint p7 = new PrecisionPoint(conn.getTargetAnchor().getLocation(p4Model));

		conn.translateToRelative(p1);
		final PrecisionPoint p4 = new PrecisionPoint(p4Model);
		conn.translateToRelative(p4);
		conn.translateToRelative(p7);

		conn.setPoints(isSelfLoop(conn) ? routeSelfLoop(conn, p1, p4, p7) : routeTransition(conn, p1, p4, p7));
	}

	private static PointList routeTransition(final Connection conn, final PrecisionPoint p1, final PrecisionPoint p4,
			final PrecisionPoint p7) {
		final Vector seg1 = new Vector(p1, p4);
		final Vector seg2 = new Vector(p4, p7);

		final double len1 = Math.max(seg1.getLength(), MIN_LENGTH);
		final double len2 = Math.max(seg2.getLength(), MIN_LENGTH);

		final double ctrlDist1 = Math.min(CTRL_POINT_FACTOR * len1, MAX_HANDLE_DISTANCE);
		final double ctrlDist2 = Math.min(CTRL_POINT_FACTOR * len2, MAX_HANDLE_DISTANCE);

		final PrecisionPoint p2 = calcOrthogonalControlPoint(p1, conn.getSourceAnchor().getOwner(), ctrlDist1, conn);
		final PrecisionPoint p6 = calcOrthogonalControlPoint(p7, conn.getTargetAnchor().getOwner(), ctrlDist2, conn);

		final Vector tangent = calcAverageTangent(seg1, seg2);

		final PrecisionPoint p3 = translate(p4, tangent, -ctrlDist1);
		final PrecisionPoint p5 = translate(p4, tangent, ctrlDist2);

		final PointList points = new PointList(7);
		points.addPoint(toPoint(p1));
		points.addPoint(toPoint(p2));
		points.addPoint(toPoint(p3));
		points.addPoint(toPoint(p4));
		points.addPoint(toPoint(p5));
		points.addPoint(toPoint(p6));
		points.addPoint(toPoint(p7));

		return points;
	}

	private static PointList routeSelfLoop(final Connection conn, final PrecisionPoint p1, final PrecisionPoint p4,
			final PrecisionPoint p7) {
		final double reach = Math.max(MIN_LENGTH,
				Math.max(new Vector(p1, p4).getLength(), new Vector(p7, p4).getLength()));
		final double handle = Math.min(SELF_LOOP_ARC_FACTOR * reach, MAX_HANDLE_DISTANCE);

		final Vector axis = getNormalized(new Vector(p1, p7));

		final PrecisionPoint p2 = calcOrthogonalControlPoint(p1, conn.getSourceAnchor().getOwner(), handle, conn);
		final PrecisionPoint p6 = calcOrthogonalControlPoint(p7, conn.getTargetAnchor().getOwner(), handle, conn);

		final PrecisionPoint p3 = translate(p4, axis, -handle);
		final PrecisionPoint p5 = translate(p4, axis, handle);

		final PointList points = new PointList(7);
		points.addPoint(toPoint(p1));
		points.addPoint(toPoint(p2));
		points.addPoint(toPoint(p3));
		points.addPoint(toPoint(p4));
		points.addPoint(toPoint(p5));
		points.addPoint(toPoint(p6));
		points.addPoint(toPoint(p7));

		return points;
	}

	private static boolean isSelfLoop(final Connection conn) {
		return conn.getSourceAnchor().getOwner() == conn.getTargetAnchor().getOwner();
	}

	private static PrecisionPoint calcOrthogonalControlPoint(final PrecisionPoint anchor, final IFigure owner,
			final double distance, final Connection conn) {
		final Rectangle bounds;
		if (owner instanceof final ECStateFigure stateFigure) {
			bounds = stateFigure.getNameLabel().getBounds().getCopy();
			stateFigure.getNameLabel().translateToAbsolute(bounds);
		} else {
			bounds = owner.getBounds().getCopy();
			owner.translateToAbsolute(bounds);
		}

		conn.translateToRelative(bounds);

		final Vector normal = EdgeDirection.of(toPoint(anchor), bounds).toNormal();
		return translate(anchor, normal, distance);
	}

	private static boolean isValidAnchor(final ConnectionAnchor anchor) {
		return anchor != null && anchor.getOwner() != null;
	}

	private static Vector getNormalized(final Vector v) {
		final double len = v.getLength();
		if (len < EPSILON) {
			return new Vector(0, 0);
		}
		return v.getDivided(len);
	}

	private static Vector calcAverageTangent(final Vector seg1, final Vector seg2) {

		final Vector tangent = getNormalized(seg1).getAdded(getNormalized(seg2));
		final double len = tangent.getLength();
		if (len < EPSILON) {
			return getNormalized(seg2);
		}
		return tangent.getDivided(len);
	}

	private static PrecisionPoint translate(final PrecisionPoint base, final Vector direction, final double distance) {
		return new PrecisionPoint(base.preciseX() + direction.x * distance, base.preciseY() + direction.y * distance);
	}

	private static Point toPoint(final PrecisionPoint p) {
		return new Point((int) Math.round(p.preciseX()), (int) Math.round(p.preciseY()));
	}
}