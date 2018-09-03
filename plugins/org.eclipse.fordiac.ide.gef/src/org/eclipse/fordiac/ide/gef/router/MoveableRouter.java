/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2015 - 2017  Profactor GmbH, TU Wien ACIN
 *   			 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - reworked connection routing and store routing results 
 *   			   correctly in model  
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.commands.AdjustConnectionCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.gef.figures.HideableConnection;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.requests.BendpointRequest;

public class MoveableRouter extends BendpointConnectionRouter implements BendpointPolicyRouter {

	public static final int MIN_CONNECTION_FB_DISTANCE = 20;
	private static final PrecisionPoint A_POINT = new PrecisionPoint();

	@Override
	public void route(Connection conn) {
		PointList points = conn.getPoints();
		points.removeAllPoints();
		
		Point sourceP;
		Point destP;
		if(needsSwap(conn)){
			destP = conn.getSourceAnchor().getLocation(conn.getSourceAnchor().getReferencePoint()).getCopy();
			sourceP = conn.getTargetAnchor().getLocation(conn.getTargetAnchor().getReferencePoint()).getCopy();
		} else {
			sourceP = conn.getSourceAnchor().getLocation(conn.getSourceAnchor().getReferencePoint()).getCopy();
			destP = conn.getTargetAnchor().getLocation(conn.getTargetAnchor().getReferencePoint()).getCopy();
		}
		
		A_POINT.setLocation(sourceP);
		conn.translateToRelative(A_POINT);
		points.addPoint(A_POINT);
		
		if(conn instanceof HideableConnection) {
			setHideableConnectionBendPoints((HideableConnection)conn, sourceP, destP, points);
		} else {
			//During connection creation we don't have a right connection and no model but we want
			//to provide the same routing as it would be one.
			setCreationBendPoints(sourceP, destP, conn, points);
		}

		A_POINT.setLocation(destP);
		conn.translateToRelative(A_POINT);
		points.addPoint(A_POINT);
		conn.setPoints(points);		
	}
	
	private static void setHideableConnectionBendPoints(HideableConnection conn, Point sourceP, Point destP, PointList points) {		
		if(null != conn.getModel()) {
			valdidateConnectionRoutingParams(conn.getModel(), sourceP, destP);
			createBendPointList(sourceP, destP, conn, conn.getModel(), points);
		}		   
	}

	private static void valdidateConnectionRoutingParams(org.eclipse.fordiac.ide.model.libraryElement.Connection conn, Point sourceP, Point destP) {
		if(0 == conn.getDx1()) { 
			if(sourceP.y != destP.y || requires5SegementConnection(sourceP, destP)) {
				//this is a new connection which is not a straight line or we need to regenerate it
				if (requires5SegementConnection(sourceP, destP)) { // we need a 5 segment line
					generateInitial5SegmentParams(conn, sourceP, destP);					
				} else {  // we need a 3 segment line
					generateInitial3SegmentParams(conn, sourceP, destP);
				}
			}
		} else if(sourceP.y == destP.y && requires5SegementConnection(sourceP, destP)) {
			//we now have a straight line
			conn.setDx1(0);
		} else if(0 == conn.getDx2()) {
			//we have three point connection
			if(requires5SegementConnection(sourceP, destP)) {
				generateInitial5SegmentParams(conn, sourceP, destP);	
			} else if ( sourceP.x + conn.getDx1() > destP.x - MIN_CONNECTION_FB_DISTANCE){
				conn.setDx1(destP.x - MIN_CONNECTION_FB_DISTANCE);
			}
		} else {
			//we have a five point connection check if we should transform it into a 3 point
			if(!requires5SegementConnection(sourceP, destP)) {
				conn.setDx2(0);
				conn.setDy(0);
			}
		}
	}

	private static void generateInitial3SegmentParams(org.eclipse.fordiac.ide.model.libraryElement.Connection conn,
			Point sourceP, Point destP) {
		conn.setDx1((destP.x - sourceP.x)/2);
	}

	private static void generateInitial5SegmentParams(org.eclipse.fordiac.ide.model.libraryElement.Connection conn,
			Point sourceP, Point destP) {
		conn.setDx1(MIN_CONNECTION_FB_DISTANCE);  // move it of the side of the fb
		conn.setDx2(MIN_CONNECTION_FB_DISTANCE);  // move it of the side of the fb
		conn.setDy((destP.y - sourceP.y)/2);
	}

	private static boolean requires5SegementConnection(Point sourceP, Point destP) {
		return destP.x - sourceP.x <= 2 * MIN_CONNECTION_FB_DISTANCE;
	}

	private static void createBendPointList(Point sourceP, Point destP, Connection conn, 
			org.eclipse.fordiac.ide.model.libraryElement.Connection modelConn, PointList points) {
		if(0 != modelConn.getDx1()) {
			addBendPoint(conn, points, new Point(sourceP.x + modelConn.getDx1(), sourceP.y));
			if (0 == modelConn.getDx2()) {
				//we have a three segment connection			
				addBendPoint(conn, points, new Point(sourceP.x + modelConn.getDx1(), destP.y));				
			}else{
				//we have a five segment connection
				addBendPoint(conn, points, new Point(sourceP.x + modelConn.getDx1(), sourceP.y + modelConn.getDy()));
				addBendPoint(conn, points, new Point(destP.x - modelConn.getDx2(), sourceP.y + modelConn.getDy()));
				addBendPoint(conn, points, new Point(destP.x - modelConn.getDx2(), destP.y));
			}			
		}
	}
	
	private static void setCreationBendPoints(Point sourceP, Point destP, Connection conn, PointList points) {
		org.eclipse.fordiac.ide.model.libraryElement.Connection modelConn = LibraryElementFactory.eINSTANCE.createEventConnection();
		valdidateConnectionRoutingParams(modelConn, sourceP, destP);
		createBendPointList(sourceP, destP, conn, modelConn, points);
	}
	
	private static void addBendPoint(Connection conn, PointList points, Point p) {
		conn.translateToRelative(p);
		points.addPoint(p);
	}

	private static boolean needsSwap(Connection conn) {
		if((conn.getSourceAnchor() instanceof FixedAnchor) && (((FixedAnchor)conn.getSourceAnchor()).getEditPart() instanceof InterfaceEditPart)){
			InterfaceEditPart ep = (InterfaceEditPart)((FixedAnchor)conn.getSourceAnchor()).getEditPart();
			return ep.isInput();
		}
		return false;
	}

	@Override
	public EditPolicy getBendpointPolicy(final Object modelObject) {
		if (modelObject instanceof org.eclipse.fordiac.ide.model.libraryElement.Connection) {
			return new BendpointEditPolicy() {

				@Override
				protected Command getMoveBendpointCommand(final BendpointRequest request) {
					return null;
				}

				@Override
				protected Command getDeleteBendpointCommand(
						final BendpointRequest request) {
					return null;
				}

				@Override
				protected Command getCreateBendpointCommand(
						final BendpointRequest request) {
					Point p = request.getLocation().getCopy();
					getConnection().translateToAbsolute(p);
					return new AdjustConnectionCommand(getConnection(), p, request
							.getIndex(), (org.eclipse.fordiac.ide.model.libraryElement.Connection) modelObject);
				}

				@Override
				protected void showCreateBendpointFeedback(BendpointRequest request) {
					AdjustConnectionCommand cmd = new AdjustConnectionCommand(
							getConnection(), request.getLocation(), request.getIndex(),
							(org.eclipse.fordiac.ide.model.libraryElement.Connection) modelObject);
					if (cmd.canExecute()) {
						cmd.execute();
					}
				}

				@Override
				protected List<ConnectionHandle> createSelectionHandles() {
					List<ConnectionHandle> list = new ArrayList<>();
					AbstractConnectionEditPart connEP = (AbstractConnectionEditPart) getHost();
					PointList points = getConnection().getPoints();
					for (int i = 1; i < points.size() - 2; i++) {
						list.add(new LineSegmentHandle(connEP, i));
					}
					return list;
				}
			};
		}
		return null;
	}
}
