/*******************************************************************************
 * Copyright (c) 2009, 2011, 2012, 2016, 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
package org.eclipse.fordiac.ide.gef.commands;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.gef.commands.Command;

public class AdjustConnectionCommand extends Command {

	private final Connection connection;
	private final Point point;
	private final int index;
	private final org.eclipse.fordiac.ide.model.libraryElement.Connection modelConnection;

	public AdjustConnectionCommand(Connection connection, Point p, int index,
			org.eclipse.fordiac.ide.model.libraryElement.Connection modelConnection) {
		super();
		this.connection = connection;
		this.point = p;
		this.index = index;
		this.modelConnection = modelConnection;
		connection.translateToRelative(point);
	}

	@Override
	public void execute() {
		ConnectionRouter router = connection.getConnectionRouter();
		if (router instanceof MoveableRouter) {
			MoveableRouter mr = (MoveableRouter) router;
			switch (index) {
			case 1:
				Point sourceP = connection.getSourceAnchor().getLocation(
						connection.getSourceAnchor().getReferencePoint());
				connection.translateToRelative(sourceP);
				int newX = point.x;
				int delta = newX - sourceP.x;
				mr.setDeltaX1(connection, delta);
				modelConnection.setDx1(delta);
				break;
			case 2:
				Point p3 = connection.getTargetAnchor().getLocation(
						connection.getTargetAnchor().getReferencePoint()).getCopy();
				Point temp = connection.getSourceAnchor().getLocation(
						connection.getSourceAnchor().getReferencePoint()).getCopy();
				connection.translateToRelative(p3);
				connection.translateToRelative(temp);
				int dif = Math.abs(p3.y - temp.y);
				int y = 0;
				if (p3.y < temp.y) {
					y = p3.y + dif / 2;
				} else {
					y = temp.y + dif / 2;
				}
				int deltaY = point.y - y;
				mr.setDeltaY(connection, deltaY);
				modelConnection.setDy(deltaY);
				break;
			case 3:
				sourceP = connection.getTargetAnchor().getLocation(
						connection.getTargetAnchor().getReferencePoint());
				connection.translateToRelative(sourceP);
				newX = point.x;
				delta = newX - sourceP.x;
				mr.setDeltaX2(connection, delta);
				modelConnection.setDx2(delta);
				break;
			default:
				break;
			}
			connection.revalidate();
		}
	}

	@Override
	public boolean canExecute() {
		Point ref1 = connection.getTargetAnchor().getReferencePoint();
		Point ref2 = connection.getSourceAnchor().getReferencePoint();
		Point min = connection.getTargetAnchor().getLocation(ref2).getCopy();
		Point p2 = connection.getSourceAnchor().getLocation(ref1).getCopy();
		connection.translateToRelative(min);
		connection.translateToRelative(p2);
		int x = p2.x;
		int newX = point.x;
		int delta = newX - p2.x;
		// Point 2 (the first after the source anchor)
		if (min.x - 20 > p2.x + 20) {
			return (x + delta < min.x - 20);
		}
		return true;
	}

	@Override
	public void undo() {
	}

	@Override
	public void redo() {
	}
}
