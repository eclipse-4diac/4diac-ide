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

import java.util.ArrayList;
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
	private static final int OBSTACLE_CLEARANCE = 6;
	/** The bendpoint is pushed away from the chord in steps of this size ... */
	private static final double BEND_STEP = 8.0;
	/**
	 * ... but never further than this, so a crowded chart cannot create huge arcs.
	 * Increased to handle transitions crossing multiple states.
	 */
	private static final double MAX_BEND_SHIFT = 1500.0;
	/** Line segments per half of the curve that are tested against the states. */
	private static final int SAMPLES_PER_HALF = 16;

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

		conn.setPoints(isSelfLoop(conn) ? routeSelfLoop(conn, p1, p4, p7) : routeAroundStates(conn, p1, p4, p7));
	}

	/**
	 * Routes the transition as usual, but if the curve runs through another state
	 * the bendpoint is moved away from the source-target line, in small steps,
	 * until the curve is clear. Checks both sides of the chord to avoid getting
	 * trapped.
	 */
	private static PointList routeAroundStates(final Connection conn, final PrecisionPoint p1, final PrecisionPoint p4,
			final PrecisionPoint p7) {
		final List<Rectangle> obstacles = collectObstacles(conn);
		final Vector chord = new Vector(p1, p7);
		if (obstacles.isEmpty() || chord.getLength() < EPSILON) {
			return routeTransition(conn, p1, p4, p7);
		}

		final Vector preferredAway = getBendSide(chord, p1, p4);
		final Vector oppositeAway = new Vector(-preferredAway.x, -preferredAway.y);

		for (double shift = 0; shift <= MAX_BEND_SHIFT; shift += BEND_STEP) {

			// 1. Check preferred side
			PointList curve = routeTransition(conn, p1, translate(p4, preferredAway, shift), p7);
			if (!hitsAny(curve, obstacles)) {
				return curve;
			}

			// 2. Check opposite side
			if (shift > 0) {
				curve = routeTransition(conn, p1, translate(p4, oppositeAway, shift), p7);
				if (!hitsAny(curve, obstacles)) {
					return curve;
				}
			}
		}
		return routeTransition(conn, p1, p4, p7);
	}

	/**
	 * Unit vector perpendicular to the chord, pointing to the side the bendpoint is
	 * on (fixed per transition).
	 */
	private static Vector getBendSide(final Vector chord, final PrecisionPoint p1, final PrecisionPoint p4) {
		final Vector unitChord = getNormalized(chord);
		final Vector normal = new Vector(-unitChord.y, unitChord.x);
		final double offset = ((p4.preciseX() - p1.preciseX()) * normal.x)
				+ ((p4.preciseY() - p1.preciseY()) * normal.y);
		return offset >= 0 ? normal : new Vector(unitChord.y, -unitChord.x);
	}

	private static List<Rectangle> collectObstacles(final Connection conn) {
		final IFigure source = conn.getSourceAnchor().getOwner();
		final IFigure target = conn.getTargetAnchor().getOwner();
		final List<Rectangle> obstacles = new ArrayList<>();
		if (source.getParent() == null) {
			return obstacles;
		}
		for (final Object child : source.getParent().getChildren()) {
			if (child instanceof final ECStateFigure state && state != source && state != target) {
				final Rectangle bounds = state.getBounds().getCopy();
				state.translateToAbsolute(bounds);
				conn.translateToRelative(bounds);
				obstacles.add(bounds.expand(OBSTACLE_CLEARANCE, OBSTACLE_CLEARANCE));
			}
		}
		return obstacles;
	}

	private static boolean hitsAny(final PointList curve, final List<Rectangle> obstacles) {
		final PointList path = flatten(curve);
		return obstacles.stream().anyMatch(path::intersects);
	}

	/**
	 * Turns the two cubic Beziers of the curve (points 0..3 and 3..6) into a
	 * polyline.
	 */
	private static PointList flatten(final PointList curve) {
		final PointList path = new PointList(2 * (SAMPLES_PER_HALF + 1));
		for (int start = 0; start <= 3; start += 3) {
			for (int i = 0; i <= SAMPLES_PER_HALF; i++) {
				path.addPoint(bezier(curve, start, (double) i / SAMPLES_PER_HALF));
			}
		}
		return path;
	}

	private static Point bezier(final PointList curve, final int start, final double t) {
		final double u = 1 - t;
		final double[] weights = { u * u * u, 3 * u * u * t, 3 * u * t * t, t * t * t };
		double x = 0;
		double y = 0;
		for (int i = 0; i < weights.length; i++) {
			final Point control = curve.getPoint(start + i);
			x += weights[i] * control.x;
			y += weights[i] * control.y;
		}
		return toPoint(new PrecisionPoint(x, y));
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
