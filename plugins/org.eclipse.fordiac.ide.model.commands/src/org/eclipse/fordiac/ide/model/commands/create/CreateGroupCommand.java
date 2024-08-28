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
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class CreateGroupCommand extends AbstractCreateFBNetworkElementCommand {

	private static final int GROUP_TOP_BORDER = 20;
	private static final int GROUP_BORDER = 2;
	private static final String INITIAL_GROUP_NAME = "Group01"; //$NON-NLS-1$

	private final AddElementsToGroup addElements;
	private final Rectangle posSizeRef;

	public CreateGroupCommand(final FBNetwork fbNetwork, final List<?> selection, final Rectangle posSizeRef) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createGroup(), posSizeRef.x, posSizeRef.y);
		addElements = new AddElementsToGroup(getElement(), selection, posSizeRef.getTopLeft());
		this.posSizeRef = checkPosSizeRef(posSizeRef);
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && allElementsInSameFBnetwork();
	}

	@Override
	public void execute() {
		updateCreatePosition(posSizeRef.getTopLeft());
		getElement().setWidth(posSizeRef.width);
		getElement().setHeight(posSizeRef.height);
		super.execute();
		addElements.execute();
	}

	@Override
	public void redo() {
		super.redo();
		addElements.redo();
	}

	@Override
	public void undo() {
		addElements.undo();
		super.undo();
	}

	@Override
	protected final InterfaceList createInterfaceList() {
		// groups do not have an interface list
		return null;
	}

	@Override
	protected String getInitialInstanceName() {
		return INITIAL_GROUP_NAME;
	}

	@Override
	public Group getElement() {
		return (Group) super.getElement();
	}

	private final Rectangle checkPosSizeRef(final Rectangle posSizeRef) {
		if (!addElements.getElementsToAdd().isEmpty()) {
			// if we do not have an empty group move it a bit to the left and up so that the
			// FBs stay at their positions
			posSizeRef.x -= GROUP_BORDER;
			posSizeRef.y -= GROUP_TOP_BORDER;
			posSizeRef.width += 2 * GROUP_BORDER;
			posSizeRef.height += GROUP_TOP_BORDER + GROUP_BORDER;
			// ensure that in the beginning the group has at least our default size
			posSizeRef.width = Math.max(posSizeRef.width,
					CoordinateConverter.INSTANCE.iec61499ToScreen(getElement().getWidth()));
			posSizeRef.height = Math.max(posSizeRef.height,
					CoordinateConverter.INSTANCE.iec61499ToScreen(getElement().getHeight()));
		}
		return posSizeRef;
	}

	private boolean allElementsInSameFBnetwork() {
		final List<FBNetworkElement> elementsToAdd = addElements.getElementsToAdd();
		if (!elementsToAdd.isEmpty()) {
			return elementsToAdd.stream().allMatch(el -> getFBNetwork().equals(el.getFbNetwork()));
		}
		return true;
	}

}
