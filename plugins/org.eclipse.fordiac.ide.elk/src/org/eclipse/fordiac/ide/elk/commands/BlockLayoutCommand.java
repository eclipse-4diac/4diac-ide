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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.elk.FordiacLayoutData;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.gef.commands.Command;

public class BlockLayoutCommand extends Command {

	private final FordiacLayoutData data;

	private final Map<FBNetworkElement, Position> oldPositions = new HashMap<>();
	private final Map<Group, Entry<Double, Double>> oldGroupSizes = new HashMap<>();

	public BlockLayoutCommand(final FordiacLayoutData data) {
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
		oldPositions.forEach(FBNetworkElement::setPosition);
		oldGroupSizes.forEach(BlockLayoutCommand::setGroupSize);
	}

	private void saveDataForUndo() {
		data.getPositions().keySet().forEach(elem -> oldPositions.put(elem, EcoreUtil.copy(elem.getPosition())));
		data.getGroups().keySet().forEach(group -> oldGroupSizes.put(group,
				new SimpleEntry<>(Double.valueOf(group.getHeight()), Double.valueOf(group.getWidth()))));
	}

	private void updateModelElements() {
		for (final var entry : data.getPositions().entrySet()) {
			final var pos = entry.getValue();
			entry.getKey().updatePositionFromScreenCoordinates((int) pos.getX(), (int) pos.getY());
		}
		data.getGroups().forEach(BlockLayoutCommand::setGroupSize);
	}

	private static void setGroupSize(final Group group, final Entry<Double, Double> size) {
		group.setHeight(size.getKey().intValue());
		group.setWidth(size.getValue().intValue());
	}

}
