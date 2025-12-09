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
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.AddElementsToGroup;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.Command;

public class CopyElementsToGroupCommand extends Command implements ScopedCommand {

	private final Group targetGroup;
	private final PasteCommand pasteCommand;
	private AddElementsToGroup addElements;
	private final List<FBNetworkElement> elementsToAdd = new ArrayList<>();
	private final Point offset;

	public CopyElementsToGroupCommand(final Group targetGroup, final PasteCommand pasteCommand, final Point offset) {
		this.targetGroup = Objects.requireNonNull(targetGroup);
		this.pasteCommand = Objects.requireNonNull(pasteCommand);
		this.offset = Objects.requireNonNull(offset);
	}

	@Override
	public void execute() {
		pasteCommand.execute();
		elementsToAdd.addAll(pasteCommand.getCopiedFBs());
		addElements = new AddElementsToGroup(targetGroup, elementsToAdd, offset);
		if (addElements.canExecute()) {
			addElements.execute();
		} else {
			addElements = null;
		}
		ElementSelector.selectViewObjects(elementsToAdd);
	}

	@Override
	public void undo() {
		if (addElements != null) {
			addElements.undo();
		}
		pasteCommand.undo();
	}

	@Override
	public void redo() {
		pasteCommand.redo();
		if (addElements != null) {
			addElements.redo();
		}
	}

	public Point getOffset() {
		return offset;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(targetGroup);
	}
}
