/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2015 - 2017  Profactor GmbH, TU Wien ACIN
 *   			 2018, 2020 Johannes Kepler University
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
 *   Alois Zoitl - reworked connection routing and store routing results
 *   			   correctly in model
 *   Daniel Lindhuber - changed routing to take params from the model (if available)
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.policies.AdjustConnectionEditPolicy;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.EditPolicy;

public class MoveableRouter extends BendpointConnectionRouter implements BendpointPolicyRouter {

	public static final int MIN_CONNECTION_FB_DISTANCE = 12;
	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	@Override
	public void route(final Connection conn) {
		final PointList points = conn.getPoints();
		points.removeAllPoints();

		if (needsSwap(conn)) {
			END_POINT.setLocation(conn.getSourceAnchor().getLocation(conn.getSourceAnchor().getReferencePoint()));
			START_POINT.setLocation(conn.getTargetAnchor().getLocation(conn.getTargetAnchor().getReferencePoint()));
		} else {
			START_POINT.setLocation(conn.getSourceAnchor().getLocation(conn.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(conn.getTargetAnchor().getLocation(conn.getTargetAnchor().getReferencePoint()));
		}

		conn.translateToRelative(START_POINT);
		conn.translateToRelative(END_POINT);
		points.addPoint(START_POINT);

		if (conn instanceof HideableConnection) {
			setHideableConnectionBendPoints((HideableConnection) conn, START_POINT, END_POINT, points);
		} else {
			// During connection creation we don't have a right connection and no model but
			// we want
			// to provide the same routing as it would be one.
			setCreationBendPoints(START_POINT, END_POINT, points);
		}

		points.addPoint(END_POINT);
		conn.setPoints(points);
	}

	private static void setHideableConnectionBendPoints(final HideableConnection conn, final Point sourceP, final Point destP,
			final PointList points) {
		if (null != conn.getModel()) {
			final ConnectionRoutingData routingData = conn.getModel().getRoutingData();
			valdidateModelRoutingParams(routingData, sourceP, destP);
			createBendPointList(sourceP, destP, routingData, points);
		}
	}

	private static void valdidateModelRoutingParams(final ConnectionRoutingData routingData,
			final Point sourceP, final Point destP) {
		if ((sourceP.y != destP.y) && (routingData.getDx1() == 0) && (routingData.getDx2() == 0)
				&& (routingData.getDy() == 0)) {
			valdidateConnectionRoutingParams(routingData, sourceP, destP);
		}
	}

	private static void valdidateConnectionRoutingParams(final ConnectionRoutingData routingData,
			final Point sourceP, final Point destP) {
		if (0 == routingData.getDx1()) {
			if ((sourceP.y != destP.y) || requires5SegementConnection(sourceP, destP)) {
				// this is a new connection which is not a straight line or we need to
				// regenerate it
				if (requires5SegementConnection(sourceP, destP)) { // we need a 5 segment line
					generateInitial5SegmentParams(routingData, sourceP, destP);
				} else { // we need a 3 segment line
					generateInitial3SegmentParams(routingData, sourceP, destP);
				}
			}
		} else if ((sourceP.y == destP.y) && !requires5SegementConnection(sourceP, destP)) {
			// we now have a straight line
			routingData.setDx1(0);
		} else if (0 == routingData.getDx2()) {
			// we have three point connection
			if (requires5SegementConnection(sourceP, destP)) {
				generateInitial5SegmentParams(routingData, sourceP, destP);
			} else if ((sourceP.x + routingData.getDx1()) > (destP.x - MIN_CONNECTION_FB_DISTANCE)) {
				routingData.setDx1(destP.x - sourceP.x - MIN_CONNECTION_FB_DISTANCE);
			}
		} else {
			// we have a five point connection check if we should transform it into a 3
			// point
			if (!requires5SegementConnection(sourceP, destP)) {
				routingData.setDx2(0);
				routingData.setDy(0);
				generateInitial3SegmentParams(routingData, sourceP, destP);
			}
		}
	}

	private static void generateInitial3SegmentParams(final ConnectionRoutingData routingData,
			final Point sourceP, final Point destP) {
		routingData.setDx1((destP.x - sourceP.x) / 2);
	}

	private static void generateInitial5SegmentParams(final ConnectionRoutingData routingData,
			final Point sourceP, final Point destP) {
		routingData.setDx1(MIN_CONNECTION_FB_DISTANCE); // move it of the side of the fb
		routingData.setDx2(MIN_CONNECTION_FB_DISTANCE); // move it of the side of the fb
		routingData.setDy((destP.y - sourceP.y) / 2);
		if (0 == routingData.getDy()) {
			// if source and dest are on the same height add a bend to it to better show it
			routingData.setDy(2 * MIN_CONNECTION_FB_DISTANCE);
		}
	}

	private static boolean requires5SegementConnection(final Point sourceP, final Point destP) {
		return sourceP.x >= (destP.x - (2 * MIN_CONNECTION_FB_DISTANCE));
	}

	private static void createBendPointList(final Point sourceP, final Point destP,
			final ConnectionRoutingData routingData, final PointList points) {
		if (0 != routingData.getDx1()) {
			points.addPoint(sourceP.x + routingData.getDx1(), sourceP.y);
			if (0 == routingData.getDy()) {
				// we have a three segment connection
				points.addPoint(new Point(sourceP.x + routingData.getDx1(), destP.y));
			} else {
				// we have a five segment connection
				final int CORRECTION_FACTOR = 1; // is needed for correct bend point placement
				points.addPoint(new Point(sourceP.x + routingData.getDx1(), sourceP.y + routingData.getDy()));
				points.addPoint(
						new Point(destP.x - (routingData.getDx2() + CORRECTION_FACTOR),
								sourceP.y + routingData.getDy()));
				points.addPoint(new Point(destP.x - (routingData.getDx2() + CORRECTION_FACTOR), destP.y));
			}
		}
	}

	private static void setCreationBendPoints(final Point sourceP, final Point destP, final PointList points) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		valdidateConnectionRoutingParams(routingData, sourceP, destP);
		createBendPointList(sourceP, destP, routingData, points);
	}

	private static boolean needsSwap(final Connection conn) {
		//		if ((conn.getSourceAnchor() instanceof FixedAnchor)
		//				&& (((FixedAnchor) conn.getSourceAnchor()).getEditPart() instanceof InterfaceEditPart)) {
		//			InterfaceEditPart ep = (InterfaceEditPart) ((FixedAnchor) conn.getSourceAnchor()).getEditPart();
		//			return ep.isInput();
		//		}
		return false;
	}

	@Override
	public EditPolicy getBendpointPolicy(final Object modelObject) {
		if (modelObject instanceof org.eclipse.fordiac.ide.model.libraryElement.Connection) {
			return new AdjustConnectionEditPolicy(
					(org.eclipse.fordiac.ide.model.libraryElement.Connection) modelObject);
		}
		return null;
	}

}
