/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2015 - 2017  Profactor GmbH, TU Wien ACIN
 *   			 2018, 2020 Johannes Kepler University
 *   			 2021 Primetals Technologies Austria GmbH
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
 *   Daniel Lindhuber - take existing routing data into account
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.gef.policies.AdjustConnectionEditPolicy;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.EditPolicy;

public class MoveableRouter extends BendpointConnectionRouter implements BendpointPolicyRouter {

	public static final int MIN_CONNECTION_FB_DISTANCE_SCREEN = 12;
	public static final double MIN_CONNECTION_FB_DISTANCE_IEC61499 = fromScreen(MIN_CONNECTION_FB_DISTANCE_SCREEN);

	private static final int DOUBLE_MIN_CONNECTION_FB_DISTANCE_SCREEN = 2 * MIN_CONNECTION_FB_DISTANCE_SCREEN;
	private static final double DOUBLE_MIN_CONNECTION_FB_DISTANCE_IEC61499 = fromScreen(
			DOUBLE_MIN_CONNECTION_FB_DISTANCE_SCREEN);

	private static final PrecisionPoint START_POINT = new PrecisionPoint();
	private static final PrecisionPoint END_POINT = new PrecisionPoint();

	@Override
	public void route(final Connection conn) {
		final PointList points = conn.getPoints();
		points.removeAllPoints();

		if (conn instanceof final HideableConnection hconn && hconn.getModel() != null) {
			handleHideableConnection(hconn, points);
		} else {
			// During connection creation we don't have a right connection and no model but
			// we want to provide the same routing as it would be one.
			setStartEndPoints(conn, needsSwap(conn));
			points.addPoint(START_POINT);
			setCreationBendPoints(START_POINT, END_POINT, points);
		}

		points.addPoint(END_POINT);
		conn.setPoints(points);
	}

	private static void handleHideableConnection(final HideableConnection conn, final PointList points) {
		setStartEndPoints(conn, false);
		points.addPoint(START_POINT);
		final org.eclipse.fordiac.ide.model.libraryElement.Connection model = conn.getModel();
		if (invalidOneSegmentConnection(model.getRoutingData())) {
			final ConnectionRoutingData newRoutingData = setCreationBendPoints(START_POINT, END_POINT, points);
			model.setRoutingData(newRoutingData);
		} else {
			if (model.getRoutingData().isNeedsValidation()) {
				valdidateConnectionRoutingParams(model.getRoutingData(), START_POINT, END_POINT);
				model.getRoutingData().setNeedsValidation(false);
			}
			createBendPointList(START_POINT, END_POINT, model.getRoutingData(), points);
		}
	}

	private static boolean invalidOneSegmentConnection(final ConnectionRoutingData routingData) {
		// check if we have a 1 line that should be something else (e.g., after
		// connection creation, problems with automatic layout generation)
		return routingData.is1SegementData() && (START_POINT.y != END_POINT.y || (START_POINT.x > END_POINT.x));
	}

	private static boolean needsSwap(final Connection conn) {
		if (conn.getSourceAnchor() instanceof final FixedAnchor fixedAnchor) {
			return fixedAnchor.isInput();
		}
		return false;
	}

	private static void setStartEndPoints(final Connection conn, final boolean needsSwap) {
		if (needsSwap) {
			END_POINT.setLocation(conn.getSourceAnchor().getLocation(conn.getSourceAnchor().getReferencePoint()));
			START_POINT.setLocation(conn.getTargetAnchor().getLocation(conn.getTargetAnchor().getReferencePoint()));
		} else {
			START_POINT.setLocation(conn.getSourceAnchor().getLocation(conn.getSourceAnchor().getReferencePoint()));
			END_POINT.setLocation(conn.getTargetAnchor().getLocation(conn.getTargetAnchor().getReferencePoint()));
		}
		conn.translateToRelative(START_POINT);
		conn.translateToRelative(END_POINT);
	}

	private static ConnectionRoutingData setCreationBendPoints(final Point sourceP, final Point destP,
			final PointList points) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		valdidateConnectionRoutingParams(routingData, sourceP, destP);
		createBendPointList(sourceP, destP, routingData, points);
		return routingData;
	}

	private static void createBendPointList(final Point sourceP, final Point destP,
			final ConnectionRoutingData routingData, final PointList points) {
		if (!routingData.is1SegementData()) {
			points.addPoint(sourceP.x + toScreen(routingData.getDx1()), sourceP.y);
			if (routingData.is3SegementData()) {
				// we have a three segment connection
				points.addPoint(sourceP.x + toScreen(routingData.getDx1()), destP.y);
			} else {
				// we have a five segment connection
				points.addPoint(sourceP.x + toScreen(routingData.getDx1()), sourceP.y + toScreen(routingData.getDy()));
				points.addPoint(destP.x - toScreen(routingData.getDx2()), sourceP.y + toScreen(routingData.getDy()));
				points.addPoint(destP.x - toScreen(routingData.getDx2()), destP.y);
			}
		}
	}

	private static void valdidateConnectionRoutingParams(final ConnectionRoutingData routingData, final Point sourceP,
			final Point destP) {
		if (routingData.is1SegementData()) {
			validate1SegmentConn(routingData, sourceP, destP);
		} else if ((sourceP.y == destP.y) && !requires5SegementConnection(sourceP, destP)) {
			// we now have a straight line
			routingData.setDx1(0);
		} else if (routingData.is3SegementData()) {
			validate3SegmentConn(routingData, sourceP, destP);
		} else {
			validate5SegmentConn(routingData, sourceP, destP);
		}
	}

	private static void validate1SegmentConn(final ConnectionRoutingData routingData, final Point sourceP,
			final Point destP) {
		if ((sourceP.y != destP.y) || requires5SegementConnection(sourceP, destP)) {
			// this is a new connection which is not a straight line or we need to
			// regenerate it
			if (requires5SegementConnection(sourceP, destP)) { // we need a 5 segment line
				generateInitial5SegmentParams(routingData, sourceP, destP);
			} else { // we need a 3 segment line
				generateInitial3SegmentParams(routingData, sourceP, destP);
			}
		}
	}

	private static void validate3SegmentConn(final ConnectionRoutingData routingData, final Point sourceP,
			final Point destP) {
		if (requires5SegementConnection(sourceP, destP)) {
			generateInitial5SegmentParams(routingData, sourceP, destP);
		} else if ((sourceP.x + toScreen(routingData.getDx1())) > (destP.x - MIN_CONNECTION_FB_DISTANCE_SCREEN)) {
			routingData.setDx1(fromScreen(destP.x - sourceP.x - MIN_CONNECTION_FB_DISTANCE_SCREEN));
		}
	}

	private static void validate5SegmentConn(final ConnectionRoutingData routingData, final Point sourceP,
			final Point destP) {
		// we have a five point connection check if we should transform it into a 3
		// point
		if (!requires5SegementConnection(sourceP, destP)) {
			routingData.setDx2(0);
			routingData.setDy(0);
			generateInitial3SegmentParams(routingData, sourceP, destP);
		}
	}

	private static void generateInitial3SegmentParams(final ConnectionRoutingData routingData, final Point sourceP,
			final Point destP) {
		routingData.setDx1(fromScreen((destP.x - sourceP.x) / 2));
	}

	private static void generateInitial5SegmentParams(final ConnectionRoutingData routingData, final Point sourceP,
			final Point destP) {
		routingData.setDx1(MIN_CONNECTION_FB_DISTANCE_IEC61499); // move it of the side of the fb
		routingData.setDx2(MIN_CONNECTION_FB_DISTANCE_IEC61499); // move it of the side of the fb
		routingData.setDy(fromScreen((destP.y - sourceP.y) / 2));
		if (Math.abs(routingData.getDy()) < 0.01) {
			// if source and dest are on the same height add a bend to it to better show it
			routingData.setDy(DOUBLE_MIN_CONNECTION_FB_DISTANCE_IEC61499);
		}
	}

	private static boolean requires5SegementConnection(final Point sourceP, final Point destP) {
		return sourceP.x >= (destP.x - DOUBLE_MIN_CONNECTION_FB_DISTANCE_SCREEN);
	}

	@Override
	public EditPolicy getBendpointPolicy(final Object modelObject) {
		if (modelObject instanceof final org.eclipse.fordiac.ide.model.libraryElement.Connection conn) {
			return new AdjustConnectionEditPolicy(conn);
		}
		return null;
	}

	private static int toScreen(final double val) {
		return CoordinateConverter.INSTANCE.iec61499ToScreen(val);
	}

	private static double fromScreen(final int val) {
		return CoordinateConverter.INSTANCE.screenToIEC61499(val);
	}
}
