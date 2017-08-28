/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2012, 2015 - 2017  Profactor GmbH, TU Wien ACIN,  
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.router;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.commands.AdjustConnectionCommand;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.requests.BendpointRequest;

public class MoveableRouter extends BendpointConnectionRouter implements
		BendpointPolicyRouter {

	private static final PrecisionPoint A_POINT = new PrecisionPoint();

	private static final Hashtable<Connection, Integer> deltasX1 = new Hashtable<Connection, Integer>();
	private static final Hashtable<Connection, Integer> deltasX2 = new Hashtable<Connection, Integer>();
	private static final Hashtable<Connection, Integer> deltasY = new Hashtable<Connection, Integer>();

	private final boolean invalidate = true;

	private final ArrayList<Connection> connections = new ArrayList<Connection>();

	public void setDeltaX2(Connection connection, int deltaX2) {
		deltasX2.put(connection, deltaX2);
	}

	public int getDeltaX2(Connection connection) {
		if (deltasX2.containsKey(connection)) {
			return deltasX2.get(connection);
		}
		return 0;
	}

	public void setDeltaX1(Connection connection, int deltaX1) {
		deltasX1.put(connection, deltaX1);
	}

	public int getDeltaX1(Connection connection) {
		if (deltasX1.containsKey(connection)) {
			return deltasX1.get(connection);
		}
		return 0;
	}

	public void setDeltaY(Connection connection, int deltaY) {
		deltasY.put(connection, deltaY);
	}

	public int getDeltaY(Connection connection) {
		if (deltasY.containsKey(connection)) {
			return deltasY.get(connection);
		}
		return 0;
	}

	@Override
	public void invalidate(Connection connection) {
		if (invalidate) {
			super.invalidate(connection);
			if (!connections.contains(connection)) {
				connections.add(connection);
			}
		}
	}

	@Override
	public void route(Connection conn) {
		PointList points = conn.getPoints();
		points.removeAllPoints();

		Point ref1, ref2;
		ConnectionAnchor source, dest;

		if(needsSwap(conn)){
			dest = conn.getSourceAnchor();
			source = conn.getTargetAnchor();
		}else{
			source = conn.getSourceAnchor();
			dest = conn.getTargetAnchor();
		}
		
		ref1 = dest.getReferencePoint();
		ref2 = source.getReferencePoint();

		Point checkYSource = dest.getLocation(ref2).getCopy();
		Point checkYDest = source.getLocation(ref1).getCopy();
		conn.translateToRelative(checkYSource);
		conn.translateToRelative(checkYDest);

		// Point 1 (directly at source anchor)
		A_POINT.setLocation(source.getLocation(ref1));
		conn.translateToRelative(A_POINT);

		points.addPoint(A_POINT);
		Point min = dest.getLocation(ref2).getCopy();
		Point p2 = source.getLocation(ref1).getCopy();
		int x = p2.x;

		// Point 2 (the first after the source anchor)
		int newx = 0;
		if (min.x - 20 > p2.x + 20) {
			newx = Math.min(min.x - 20, x + Math.max(20, getDeltaX1(conn)));
		} else {
			newx = x + Math.max(20, getDeltaX1(conn));
		}
		int deltaX = newx - p2.x;
		int relativeX = 0;

		// check on 3 or 5 segment connection
		Point p3 = dest.getLocation(ref2).getCopy();
		Point temp = source.getLocation(ref1).getCopy();
		if (p3.x - 40 <= temp.x) { // need 5 segment line

			// add point 2 only if necessary
			A_POINT.setLocation(p2);
			conn.translateToRelative(A_POINT);
			relativeX = A_POINT.x += deltaX;
			points.addPoint(A_POINT);

			int dif = Math.abs(p3.y - temp.y);
			int y = 0;
			if (p3.y < temp.y) {
				y = p3.y + dif / 2;
			} else {
				y = temp.y + dif / 2;
			}

			// endpoint of second segment - startpoint of third segment
			Point p5 = new Point(p2.x, y);
			A_POINT.setLocation(p5);
			conn.translateToRelative(A_POINT);
			A_POINT.x = relativeX;
			A_POINT.y += getDeltaY(conn);
			points.addPoint(A_POINT);

			// endpint of third segment - startpoint of fourth segment
			x = p3.x;
			Point p4 = new Point(x, y);
			A_POINT.setLocation(p4);
			conn.translateToRelative(A_POINT);
			A_POINT.x = Math.min(A_POINT.x + getDeltaX2(conn), A_POINT.x - 20);
			A_POINT.y += getDeltaY(conn);
			points.addPoint(A_POINT);

			// endpoint of fourth segment - startpoint of last segment
			p3.x = x;
			A_POINT.setLocation(p3);
			conn.translateToRelative(A_POINT);
			A_POINT.x = Math.min(A_POINT.x + getDeltaX2(conn), A_POINT.x - 20);
			points.addPoint(A_POINT);

		} else { // need 3 segment line - only if the y values are not identical

			if (checkYSource.y != checkYDest.y) { // 3 segment line is only necessary
				// if the y values are NOT identical

				// add point 2 only if necessary
				A_POINT.setLocation(p2);
				conn.translateToRelative(A_POINT);
				relativeX = A_POINT.x += deltaX;
				points.addPoint(A_POINT);

				A_POINT.setLocation(p3);
				conn.translateToRelative(A_POINT);
				A_POINT.x = relativeX;
				points.addPoint(A_POINT);
			}
		}
		A_POINT.setLocation(dest.getLocation(ref2));
		conn.translateToRelative(A_POINT);
		points.addPoint(A_POINT);

		conn.setPoints(points);
	}

	private boolean needsSwap(Connection conn) {
		if(conn.getSourceAnchor() instanceof FixedAnchor){
			if(((FixedAnchor)conn.getSourceAnchor()).getEditPart() instanceof InterfaceEditPart){
				InterfaceEditPart ep = (InterfaceEditPart)((FixedAnchor)conn.getSourceAnchor()).getEditPart();
				return ep.isInput();
			}
		}
		return false;
	}

	@Override
	public void remove(Connection connection) {
		super.remove(connection);
		connections.remove(connection);
	}

	@Override
	public EditPolicy getBendpointPolicy(final Object modelObject) {
		if (modelObject instanceof org.eclipse.fordiac.ide.model.libraryElement.Connection) {
			return new BendpointEditPolicy() {
				//private final List NULL_CONSTRAINT = new ArrayList();

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

				@SuppressWarnings("rawtypes")
				@Override
				protected List createSelectionHandles() {
					List<ConnectionHandle> list = new ArrayList<ConnectionHandle>();
					list = createHandlesForAutomaticBendpoints();
					return list;
				}

				private List<ConnectionHandle> createHandlesForAutomaticBendpoints() {
					List<ConnectionHandle> list = new ArrayList<ConnectionHandle>();
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
