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

	@Override
	public void route(final Connection conn) {

		if (!(conn instanceof ECTransitionFigure)) {
			super.route(conn);
			return;
		}

		final Object constraint = getConstraint(conn);
		if (!(constraint instanceof final List<?> bendpoints) || bendpoints.isEmpty()
				|| !(bendpoints.get(0) instanceof final Bendpoint bp)) {
			// Creation feedback: Nayi line bante waqt straight line feedback dikhao
			if (conn.getPoints().size() == 0) {
				super.route(conn);
			}
			return;
		}

		final PrecisionPoint p4Model = new PrecisionPoint(bp.getLocation());
		conn.translateToAbsolute(p4Model);

		final PrecisionPoint p1 = new PrecisionPoint(conn.getSourceAnchor().getLocation(p4Model));
		final PrecisionPoint p7 = new PrecisionPoint(conn.getTargetAnchor().getLocation(p4Model));

		// 1. DETECT SELF-LOOP: Check karo kya anchors same state box ke hain
		final boolean isSelfLoop = conn.getSourceAnchor() != null && conn.getTargetAnchor() != null
				&& conn.getSourceAnchor().getOwner() == conn.getTargetAnchor().getOwner();

		conn.translateToRelative(p1);
		final PrecisionPoint p4 = new PrecisionPoint(p4Model);
		conn.translateToRelative(p4);
		conn.translateToRelative(p7);

		final Vector seg1 = new Vector(p1, p4);
		final Vector seg2 = new Vector(p4, p7);

		final double len1 = Math.max(seg1.getLength(), MIN_LENGTH);
		final double len2 = Math.max(seg2.getLength(), MIN_LENGTH);

		// 2. DYNAMIC CONTROL DISTANCE: Supercharge the roundness for self-loops
		double ctrlDist1;
		double ctrlDist2;

		if (isSelfLoop) {
			// Agar self-loop hai, toh control points ko bada push do taaki ek bada round
			// dome/arch bane
			final double baseDist = Math.max(len1, len2) * 0.65;
			ctrlDist1 = Math.max(baseDist, 65.0);
			ctrlDist2 = Math.max(baseDist, 65.0);
		} else {
			// Normal transitions ke liye default scaling
			ctrlDist1 = Math.min(CTRL_POINT_FACTOR * len1, MAX_HANDLE_DISTANCE);
			ctrlDist2 = Math.min(CTRL_POINT_FACTOR * len2, MAX_HANDLE_DISTANCE);
		}

		// 3. GENERATE ORTHOGONAL EXIT/ENTRY: P2 shoots right, P6 shoots left perfectly
		final PrecisionPoint p2 = calcOrthogonalControlPoint(p1, conn.getSourceAnchor().getOwner(), ctrlDist1, conn);
		final PrecisionPoint p6 = calcOrthogonalControlPoint(p7, conn.getTargetAnchor().getOwner(), ctrlDist2, conn);

		// 4. NATURAL TANGENT FLOW: Standard geometric connection path without inversion
		final Vector tangent = calcAverageTangent(seg1, seg2);

		final PrecisionPoint p3 = translate(p4, tangent, -ctrlDist1);
		final PrecisionPoint p5 = translate(p4, tangent, ctrlDist2);

		// 5. ASSEMBLE 7-POINT SPLINE
		final PointList points = new PointList(7);
		points.addPoint(toPoint(p1));
		points.addPoint(toPoint(p2));
		points.addPoint(toPoint(p3));
		points.addPoint(toPoint(p4));
		points.addPoint(toPoint(p5));
		points.addPoint(toPoint(p6));
		points.addPoint(toPoint(p7));

		conn.setPoints(points);
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
