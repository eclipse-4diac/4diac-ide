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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData.ConnectionLayoutData;
import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeValueCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateAttributeCommand;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class LayoutCommand extends AbstractLayoutCommand {

	private final FordiacLayoutData data;

	private final Map<FBNetworkElement, Position> oldPositions = new HashMap<>();
	private final List<ConnectionLayoutData> oldRoutingData;
	private final Map<Group, Entry<Double, Double>> oldGroupSizes = new HashMap<>();

	private final CompoundCommand pinPositionAttrCommand = new CompoundCommand();

	public LayoutCommand(final FordiacLayoutData data) {
		this.data = data;
		this.oldRoutingData = new ArrayList<>(data.getConnectionPoints().size());
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
		setRoutingData(oldRoutingData);
		oldGroupSizes.forEach(LayoutCommand::setGroupSize);
		if (pinPositionAttrCommand.canExecute()) {
			pinPositionAttrCommand.undo();
		}
	}

	private void saveDataForUndo() {
		data.getPositions().keySet().forEach(elem -> oldPositions.put(elem, EcoreUtil.copy(elem.getPosition())));
		saveRoutingDataForUndo(data.getConnectionPoints(), oldRoutingData);
		data.getGroups().keySet().forEach(group -> oldGroupSizes.put(group,
				new SimpleEntry<>(Double.valueOf(group.getHeight()), Double.valueOf(group.getWidth()))));
	}

	private void updateModelElements() {
		for (final var entry : data.getPositions().entrySet()) {
			final var pos = entry.getValue();
			entry.getKey().updatePositionFromScreenCoordinates((int) pos.getX(), (int) pos.getY());
		}
		setRoutingData(data.getConnectionPoints());
		data.getGroups().forEach(LayoutCommand::setGroupSize);
	}

	private void updatePositionAttributes() {
		data.getPins().forEach(this::updatePositionAttribute);
	}

	private static void setGroupSize(final Group group, final Entry<Double, Double> size) {
		group.setHeight(size.getKey().intValue());
		group.setWidth(size.getValue().intValue());
	}

	private void updatePositionAttribute(final IInterfaceElement ie, final Integer y) {
		final Command cmd;
		final String attrValue = y.toString();
		final Attribute attr = ie.getAttribute(FordiacKeywords.INTERFACE_Y_POSITION);
		if (attr == null) {
			cmd = CreateAttributeCommand.forValues(ie, FordiacKeywords.INTERFACE_Y_POSITION, "", //$NON-NLS-1$
					IecTypes.ElementaryTypes.DINT, attrValue, -1);
		} else {
			cmd = new ChangeAttributeValueCommand(attr, attrValue);
		}
		pinPositionAttrCommand.add(cmd);
	}

}
