/*******************************************************************************
 * Copyright (c) 2009, 2011, 2012, 2016, 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2018 Johanes Kepler University
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
 *   Alois Zoitl - fixed connection adjusting when zoomed
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.commands;

import java.text.MessageFormat;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public class AdjustConnectionCommand extends Command {

	private final Connection connection;
	private final Point point;
	private final int index;
	private final org.eclipse.fordiac.ide.model.libraryElement.Connection modelConnection;
	private final double zoom;
	private final ConnectionRoutingData oldRoutingData;
	private ConnectionRoutingData newRoutingData;

	public AdjustConnectionCommand(final Connection connection, final Point p, final int index,
			final org.eclipse.fordiac.ide.model.libraryElement.Connection modelConnection, final double zoom) {
		this.connection = connection;
		this.point = p;
		this.index = index;
		this.modelConnection = modelConnection;
		this.zoom = zoom;
		this.oldRoutingData = modelConnection.getRoutingData();
	}

	@Override
	public void execute() {
		createInitalNewRoutingData();
		updateNewRoutingData();
		updateRoutingData(newRoutingData);
	}

	@Override
	public void undo() {
		updateRoutingData(oldRoutingData);
	}

	@Override
	public void redo() {
		updateRoutingData(newRoutingData);
	}

	private void updateRoutingData(final ConnectionRoutingData routingData) {
		modelConnection.setRoutingData(routingData);
		connection.revalidate();
	}

	private void updateNewRoutingData() {
		final Point sourceP = getSourcePoint();
		final Point destP = getDestinationPoint();
		final int scaledMinDistance = (int) Math.floor(MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN * zoom);

		switch (index) {
		case 2:
			int newDx1 = Math.max(point.x - sourceP.x, scaledMinDistance);
			if (newRoutingData.is3SegementData()) {
				// we have three segment connection check that we are not beyond the input
				newDx1 = Math.min(newDx1, destP.x - sourceP.x - scaledMinDistance);
			}
			newRoutingData.setDx1(fromScreen((int) Math.floor(newDx1 / zoom)));
			break;
		case 4:
			newRoutingData.setDy(fromScreen((int) Math.floor((point.y - sourceP.y) / zoom)));
			break;
		case 6:
			final int newDx2 = Math.max(destP.x - point.x, scaledMinDistance);
			newRoutingData.setDx2(fromScreen((int) Math.floor(newDx2 / zoom)));
			break;
		default:
			FordiacLogHelper.logError(MessageFormat.format(Messages.AdjustConnectionCommand_WrongConnectionSegmentIndex,
					Integer.valueOf(index)));
			break;
		}
	}

	private void createInitalNewRoutingData() {
		newRoutingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		newRoutingData.setDx1(oldRoutingData.getDx1());
		newRoutingData.setDx2(oldRoutingData.getDx2());
		newRoutingData.setDy(oldRoutingData.getDy());
	}

	private Point getDestinationPoint() {
		return connection.getTargetAnchor().getLocation(connection.getTargetAnchor().getReferencePoint()).getCopy();
	}

	private Point getSourcePoint() {
		return connection.getSourceAnchor().getLocation(connection.getSourceAnchor().getReferencePoint()).getCopy();
	}

	private static double fromScreen(final int val) {
		return CoordinateConverter.INSTANCE.screenToIEC61499(val);
	}
}
