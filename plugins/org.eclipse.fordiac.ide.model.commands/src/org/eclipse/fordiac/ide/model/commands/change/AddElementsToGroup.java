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
import java.util.List;

import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

public class AddElementsToGroup extends Command {

	private final Group targetGroup;
	private final List<FBNetworkElement> elementsToAdd;
	private org.eclipse.swt.graphics.Point offset;

	public AddElementsToGroup(final Group targetGroup, final List<?> selection) {
		this.targetGroup = targetGroup;
		elementsToAdd = createElementList(selection);
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
		offset = FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		addElementsToGroup();
	}

	@Override
	public void redo() {
		FBNetworkHelper.removeXYOffsetForFBNetwork(elementsToAdd);
		addElementsToGroup();
	}

	@Override
	public void undo() {
		FBNetworkHelper.moveFBNetworkByOffset(elementsToAdd, getOriginalPositionX(), getOriginalPositionY());
		removeElementsFromGroup();
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
		return offset.x - FBNetworkHelper.X_OFFSET_FROM_TOP_LEFT_CORNER;
	}

	private int getOriginalPositionY() {
		return offset.y - FBNetworkHelper.Y_OFFSET_FROM_TOP_LEFT_CORNER;
	}

}
