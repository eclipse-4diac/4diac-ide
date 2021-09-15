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
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.change.AttributeChangeCommand;
import org.eclipse.fordiac.ide.model.commands.create.AttributeCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class LayoutCommand extends Command {

	private final Map<FBNetworkElement, Position> positions;
	private final Map<Connection, PointList> connPoints;
	private final Map<FBNetworkElement, FBNetworkElementFigure> fbFigures;
	private final Map<IInterfaceElement, Integer> pins;

	private final Map<FBNetworkElement, Position> oldPositions = new HashMap<>();
	private final Map<Connection, ConnectionRoutingData> oldRoutingData = new HashMap<>();

	private final CompoundCommand pinPositionAttrCommand = new CompoundCommand();

	public LayoutCommand(final Map<FBNetworkElement, Position> positions, final Map<Connection, PointList> connPoints,
			final Map<FBNetworkElement, FBNetworkElementFigure> fbFigures, final Map<IInterfaceElement, Integer> pins) {
		super();
		this.positions = positions;
		this.connPoints = connPoints;
		this.fbFigures = fbFigures;
		this.pins = pins;
	}

	@Override
	public void execute() {
		saveDataForUndo();
		updateModelElements();
		updatePositionAttributes();
		updateFigures();
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.execute();
		}
	}
	@Override
	public void redo() {
		updateModelElements();
		updateFigures();
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.redo();
		}
	}

	@Override
	public void undo() {
		oldPositions.forEach(FBNetworkElement::setPosition);
		oldRoutingData.forEach(Connection::setRoutingData);
		fbFigures.forEach((elem, fig) -> {
			final Position pos = oldPositions.get(elem);
			fig.setLocation(new Point(pos.getX(), pos.getY()));
		});
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.undo();
		}
	}

	private void saveDataForUndo() {
		positions.keySet().forEach(elem -> oldPositions.put(elem, EcoreUtil.copy(elem.getPosition())));
		connPoints.keySet().forEach(conn -> oldRoutingData.put(conn, EcoreUtil.copy(conn.getRoutingData())));
	}

	private void updateModelElements() {
		positions.forEach(FBNetworkElement::setPosition);
		connPoints.forEach(LayoutCommand::updateModel);
	}

	private void updatePositionAttributes() {
		pins.forEach(this::updatePositionAttribute);
	}

	private void updatePositionAttribute(final IInterfaceElement ie, final Integer y) {
		final Command cmd;
		final String attrValue = y.toString();
		final Attribute attr = ie.getAttribute(FordiacKeywords.INTERFACE_Y_POSITION);
		if (attr == null) {
			cmd = new AttributeCreateCommand(ie, FordiacKeywords.INTERFACE_Y_POSITION, "", attrValue); //$NON-NLS-1$
		} else {
			cmd = new AttributeChangeCommand(attr, attrValue);
		}
		pinPositionAttrCommand.add(cmd);
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
