/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber, Bianca Wiesmayr, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.elk.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.application.figures.FBNetworkElementFigure;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;

public class LayoutCommand extends Command {
	
	final Map<FBNetworkElement, Position> positions;
	final Map<Connection, PointList> connPoints;
	private final Map<FBNetworkElement, FBNetworkElementFigure> fbFigures;

	private final Map<FBNetworkElement, Position> oldPositions = new HashMap<>();
	private final Map<Connection, ConnectionRoutingData> oldRoutingData = new HashMap<>();

	public LayoutCommand(Map<FBNetworkElement, Position> positions, Map<Connection, PointList> connPoints,
			Map<FBNetworkElement, FBNetworkElementFigure> fbFigures) {
		super();
		this.positions = positions;
		this.connPoints = connPoints;
		this.fbFigures = fbFigures;
	}

	@Override
	public void execute() {
		saveDataForUndo();
		updateModelElements();
		updateFigures();
	}

	@Override
	public void redo() {
		updateModelElements();
		updateFigures();
	}

	@Override
	public void undo() {
		oldPositions.forEach(FBNetworkElement::setPosition);
		oldRoutingData.forEach(Connection::setRoutingData);
		fbFigures.forEach((elem, fig) -> {
			final Position pos = oldPositions.get(elem);
			fig.setLocation(new Point(pos.getX(), pos.getY()));
		});
	}

	private void saveDataForUndo() {
		positions.keySet().forEach(elem -> oldPositions.put(elem, EcoreUtil.copy(elem.getPosition())));
		connPoints.keySet().forEach(conn -> oldRoutingData.put(conn, EcoreUtil.copy(conn.getRoutingData())));
	}

	private void updateModelElements() {
		positions.forEach(FBNetworkElement::setPosition);
		connPoints.forEach(LayoutCommand::updateModel);
	}

	private void updateFigures() {
		fbFigures.forEach((elem, fig) -> {
			final Position pos = positions.get(elem);
			fig.setLocation(new Point(pos.getX(), pos.getY()));
		});
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
