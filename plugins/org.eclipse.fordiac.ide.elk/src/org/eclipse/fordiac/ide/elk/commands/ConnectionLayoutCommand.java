/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.elk.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.gef.commands.Command;

// simplified version of the LayoutCommand
public class ConnectionLayoutCommand extends Command {

	private final FordiacLayoutData data;
	private final Map<Connection, ConnectionRoutingData> oldRoutingData = new HashMap<>();

	public ConnectionLayoutCommand(final FordiacLayoutData data) {
		this.data = data;
	}

	@Override
	public void execute() {
		saveDataForUndo();
		updateModelElements();

	}
	@Override
	public void redo() {
		updateModelElements();
	}

	@Override
	public void undo() {
		oldRoutingData.forEach(Connection::setRoutingData);
	}

	private void saveDataForUndo() {
		data.getConnectionPoints().keySet().forEach(conn -> oldRoutingData.put(conn, EcoreUtil.copy(conn.getRoutingData())));
	}

	private void updateModelElements() {
		data.getConnectionPoints().forEach(ConnectionLayoutCommand::updateModel);
	}

	private static void updateModel(final org.eclipse.fordiac.ide.model.libraryElement.Connection connModel, final PointList pointList) {
		final ConnectionRoutingData routingData = LibraryElementFactory.eINSTANCE.createConnectionRoutingData();
		if (pointList.size() > 2) {
			// 3 segments
			routingData.setDx1(pointList.getPoint(1).x() - pointList.getFirstPoint().x());
			if (pointList.size() > 4) {
				// 5 segments
				routingData.setDy(pointList.getPoint(2).y() - pointList.getFirstPoint().y());
				routingData.setDx2(pointList.getLastPoint().x() - pointList.getPoint(pointList.size() - 2).x());
			}
		}
		connModel.setRoutingData(routingData);
	}

}
