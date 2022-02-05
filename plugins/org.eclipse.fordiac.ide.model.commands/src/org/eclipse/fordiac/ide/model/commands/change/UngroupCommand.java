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

import org.eclipse.fordiac.ide.model.commands.delete.DeleteGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.Command;

public class UngroupCommand extends Command {

	private final DeleteGroupCommand deleteCommand;
	private final RemoveElementsFromGroup removeCommand;

	public UngroupCommand(final Group group) {
		deleteCommand = new DeleteGroupCommand(group);
		removeCommand = new RemoveElementsFromGroup(group.getGroupElements());
	}

	@Override
	public boolean canExecute() {
		return removeCommand.canExecute() && deleteCommand.canExecute();
	}

	@Override
	public void execute() {
		removeCommand.execute();
		deleteCommand.execute();
	}

	@Override
	public void undo() {
		deleteCommand.undo();
		removeCommand.undo();
	}

	@Override
	public void redo() {
		removeCommand.redo();
		deleteCommand.redo();
	}
}
