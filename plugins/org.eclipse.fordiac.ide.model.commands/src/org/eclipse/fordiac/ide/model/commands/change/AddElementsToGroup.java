/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.swt.graphics.Point;

public class AddElementsToGroup extends Command {

	private final Group targetGroup;
	private final List<FBNetworkElement> elementsToAdd;
	private final CompoundCommand removeFromOtherGroups = new CompoundCommand();
	private org.eclipse.swt.graphics.Point offset;

	public AddElementsToGroup(final Group targetGroup, final List<?> selection) {
		this.targetGroup = targetGroup;
		elementsToAdd = createElementList(selection);
		identifyRemoveElements();
	}

	private void identifyRemoveElements() {
		final Map<Group, List<FBNetworkElement>> groupMap = new HashMap<>();
		// collect all entries that are in a group and store them by group
		elementsToAdd.stream().filter(FBNetworkElement::isInGroup).forEach(el -> {
			final List<FBNetworkElement> entry = groupMap.computeIfAbsent(el.getGroup(), group -> new ArrayList<>());
			entry.add(el);
		});
		// for each entry in the map create one RemoveFromGroupCommand
		groupMap.forEach(
				(group, list) -> removeFromOtherGroups.add(new RemoveElementsFromGroup(list,
						new Point(group.getPosition().getX(), group.getPosition().getY()))));
	}

	public List<FBNetworkElement> getElementsToAdd() {
		return elementsToAdd;
	}

	@Override
	public boolean canExecute() {
		return !elementsToAdd.isEmpty() && targetIsInSameFbNetwork();
	}

	@Override
	public void execute() {
		removeFromOtherGroups.execute();
		offset = FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		addElementsToGroup();
	}

	@Override
	public void redo() {
		removeFromOtherGroups.redo();
		FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		addElementsToGroup();
	}

	@Override
	public void undo() {
		FBNetworkHelper.moveFBNetworkByOffset(elementsToAdd, getOriginalPositionX(), getOriginalPositionY());
		removeElementsFromGroup();
		removeFromOtherGroups.undo();
	}

	private void addElementsToGroup() {
		elementsToAdd.forEach(el -> targetGroup.getGroupElements().add(el));
	}

	private void removeElementsFromGroup() {
		elementsToAdd.forEach(el -> targetGroup.getGroupElements().remove(el));
	}

	private boolean targetIsInSameFbNetwork() {
		final FBNetwork fbNetwork = targetGroup.getFbNetwork();
		// if any of the elements is not in the same fbNetwork do not allow to add it to the group
		return elementsToAdd.stream().allMatch(el -> fbNetwork.equals(el.getFbNetwork()));
	}

	private static List<FBNetworkElement> createElementList(final List<?> selection) {
		final List<FBNetworkElement> elements = new ArrayList<>();
		for (final Object fbNetworkElement : selection) {
			if ((fbNetworkElement instanceof EditPart)
					&& (((EditPart) fbNetworkElement).getModel() instanceof FBNetworkElement)) {
				elements.add((FBNetworkElement) ((EditPart) fbNetworkElement).getModel());
			} else if (fbNetworkElement instanceof FBNetworkElement) {
				elements.add((FBNetworkElement) fbNetworkElement);
			}
		}
		return elements;
	}

	private int getOriginalPositionX() {
		return offset.x;
	}

	private int getOriginalPositionY() {
		return offset.y;
	}

}
