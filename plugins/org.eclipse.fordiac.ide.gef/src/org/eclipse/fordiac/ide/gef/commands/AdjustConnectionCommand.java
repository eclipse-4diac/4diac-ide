/*******************************************************************************
 * Copyright (c) 2009, 2025 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johanes Kepler University
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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.gef.router.MoveableRouter;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.gef.commands.Command;

public class AdjustConnectionCommand extends Command {

	private final Point connStart;
	private final Point connEnd;
	private final Point point;
	private final int index;
	private final Connection modelConnection;
	private final ConnectionRoutingData oldRoutingData;
	private ConnectionRoutingData newRoutingData;

	public AdjustConnectionCommand(final Connection modelConnection, final Point connStart, final Point connEnd,
			final Point p, final int index) {
		this.modelConnection = modelConnection;
		this.connStart = connStart;
		this.connEnd = connEnd;
		this.point = p;
		this.index = index;
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
	}

	private void updateNewRoutingData() {
		switch (index) {
		case 2:
			int newDx1 = Math.max(point.x - connStart.x, MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN);
			if (newRoutingData.is3SegementData()) {
				// we have three segment connection check that we are not beyond the input
				newDx1 = Math.min(newDx1, connEnd.x - connStart.x - MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN);
			}
			newRoutingData.setDx1(fromScreen(newDx1));
			break;
		case 4:
			newRoutingData.setDy(fromScreen(point.y - connStart.y));
			break;
		case 6:
			final int newDx2 = Math.max(connEnd.x - point.x, MoveableRouter.MIN_CONNECTION_FB_DISTANCE_SCREEN);
			newRoutingData.setDx2(fromScreen(newDx2));
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

	private static double fromScreen(final int val) {
		return CoordinateConverter.INSTANCE.screenToIEC61499(val);
	}
}
