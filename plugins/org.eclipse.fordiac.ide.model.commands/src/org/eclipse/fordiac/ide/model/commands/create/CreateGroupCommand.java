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

import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;

public class CreateGroupCommand extends AbstractCreateFBNetworkElementCommand {

	private static final String INITIAL_GROUP_NAME = "__Group01"; //$NON-NLS-1$

	private final AddElementsToGroup addElements;

	public CreateGroupCommand(final FBNetwork fbNetwork, final List<?> selection, final int x, final int y) {
		super(fbNetwork, LibraryElementFactory.eINSTANCE.createGroup(), x, y);
		addElements = new AddElementsToGroup(getElement(), selection);
	}

	@Override
	public boolean canExecute() {
		return super.canExecute() && allElementsInSameFBnetwork();
	}

	@Override
	public void execute() {
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
	protected final InterfaceList getTypeInterfaceList() {
		return LibraryElementFactory.eINSTANCE.createInterfaceList();
	}

	@Override
	protected String getInitalInstanceName() {
		return INITIAL_GROUP_NAME;
	}

	@Override
	public Group getElement() {
		return (Group) super.getElement();
	}

	private boolean allElementsInSameFBnetwork() {
		final List<FBNetworkElement> elementsToAdd = addElements.getElementsToAdd();
		if (!elementsToAdd.isEmpty()) {
			return elementsToAdd.stream().allMatch(el -> getFBNetwork().equals(el.getFbNetwork()));
		}
		return true;
	}

}
