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
 *   Alois Zoitl - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.Command;

public class CreateFBElementInGroupCommand extends Command implements ScopedCommand {

	private final Group group;
	private final AbstractCreateFBNetworkElementCommand elementCreateCmd;

	public CreateFBElementInGroupCommand(final TypeEntry typeEntry, final Group group, final int x, final int y) {
		this.group = Objects.requireNonNull(group);
		elementCreateCmd = AbstractCreateFBNetworkElementCommand.createCreateCommand(typeEntry, group.getFbNetwork(), x,
				y);
	}

	@Override
	public boolean canExecute() {
		return getElementCreateCmd() != null && getElementCreateCmd().canExecute();
	}

	@Override
	public void execute() {
		elementCreateCmd.execute();
		group.getGroupElements().add(elementCreateCmd.getElement());
	}

	@Override
	public void undo() {
		getGroup().getGroupElements().remove(getElementCreateCmd().getElement());
		getElementCreateCmd().undo();
	}

	@Override
	public void redo() {
		getElementCreateCmd().redo();
		getGroup().getGroupElements().add(getElementCreateCmd().getElement());
	}

	public AbstractCreateFBNetworkElementCommand getElementCreateCmd() {
		return elementCreateCmd;
	}

	public Group getGroup() {
		return group;
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(group);
	}
}
