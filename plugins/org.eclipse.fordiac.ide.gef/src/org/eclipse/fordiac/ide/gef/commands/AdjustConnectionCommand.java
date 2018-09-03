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

import java.text.MessageFormat;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.Activator;
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
		Point sourceP = getSourcePoint();
		Point destP = getDestinationPoint();
		switch (index) {
		case 1:
			int newDx1 = Math.max(point.x - sourceP.x, MoveableRouter.MIN_CONNECTION_FB_DISTANCE);
			if(0 == modelConnection.getDx2()) {	  
				//we have three segment connection check that we are not beyond the input
				newDx1 = Math.min(newDx1, destP.x - sourceP.x - MoveableRouter.MIN_CONNECTION_FB_DISTANCE);
			}
			modelConnection.setDx1(newDx1);
			break;
		case 2:
			modelConnection.setDy(point.y - sourceP.y);
			break;
		case 3:		
			modelConnection.setDx2(Math.max(destP.x - point.x, MoveableRouter.MIN_CONNECTION_FB_DISTANCE));
			break;
		default:
			Activator.getDefault().logError(MessageFormat.format("Wrong connection segment index ({0}) provided to AdjustConnectionCommand!", index));
			break;
		}
		connection.revalidate();
	}

	private Point getDestinationPoint() {
		Point destP = connection.getTargetAnchor().getLocation(connection.getTargetAnchor().getReferencePoint()).getCopy();
		connection.translateToRelative(destP);
		return destP;
	}

	private Point getSourcePoint() {
		Point sourceP = connection.getSourceAnchor().getLocation(connection.getSourceAnchor().getReferencePoint()).getCopy();
		connection.translateToRelative(sourceP);
		return sourceP;
	}

	@Override
	public void undo() {
	}

	@Override
	public void redo() {
	}
}
