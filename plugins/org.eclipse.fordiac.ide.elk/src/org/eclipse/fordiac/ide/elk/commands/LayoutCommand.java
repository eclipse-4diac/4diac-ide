/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021, 2022 Primetals Technologies Austria GmbH
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

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.change.AttributeChangeCommand;
import org.eclipse.fordiac.ide.model.commands.create.AttributeCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ConnectionRoutingData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class LayoutCommand extends Command {
	
	private final FordiacLayoutData data;

	private final Map<FBNetworkElement, Position> oldPositions = new HashMap<>();
	private final Map<Connection, ConnectionRoutingData> oldRoutingData = new HashMap<>();
	private final Map<Group, Entry<Integer, Integer>> oldGroupSizes = new HashMap<>();

	private final CompoundCommand pinPositionAttrCommand = new CompoundCommand();

	public LayoutCommand(final FordiacLayoutData data) {
		this.data = data;
	}

	@Override
	public void execute() {
		saveDataForUndo();
		updateModelElements();
		updatePositionAttributes();
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.execute();
		}
	}
	@Override
	public void redo() {
		updateModelElements();
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.redo();
		}
	}

	@Override
	public void undo() {
		oldPositions.forEach(FBNetworkElement::setPosition);
		oldRoutingData.forEach(Connection::setRoutingData);
		oldGroupSizes.forEach(LayoutCommand::setGroupSize);
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.undo();
		}
	}

	private void saveDataForUndo() {
		data.getPositions().keySet().forEach(elem -> oldPositions.put(elem, EcoreUtil.copy(elem.getPosition())));
		data.getConnectionPoints().keySet().forEach(conn -> oldRoutingData.put(conn, EcoreUtil.copy(conn.getRoutingData())));
		data.getGroups().keySet().forEach(group -> oldGroupSizes.put(group, new SimpleEntry<>(Integer.valueOf(group.getHeight()), Integer.valueOf(group.getWidth()))));
	}

	private void updateModelElements() {
		data.getPositions().forEach(FBNetworkElement::setPosition);
		data.getConnectionPoints().forEach(LayoutCommand::updateModel);
		data.getGroups().forEach(LayoutCommand::setGroupSize);
	}

	private void updatePositionAttributes() {
		data.getPins().forEach(this::updatePositionAttribute);
	}
	
	private static void setGroupSize(final Group group, final Entry<Integer, Integer> size) {
		group.setHeight(size.getKey().intValue());
		group.setWidth(size.getValue().intValue());
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
