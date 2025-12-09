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
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteGroupCommand extends DeleteFBNetworkElementCommand {

	private final CompoundCommand deleteGroupMembers = new CompoundCommand();

	public DeleteGroupCommand(final Group element) {
		super(element);
	}

	@Override
	public void execute() {
		collectGroupMembers();
		deleteGroupMembers.execute();
		super.execute();
	}

	@Override
	public void undo() {
		super.undo();
		deleteGroupMembers.undo();
	}

	@Override
	public void redo() {
		deleteGroupMembers.redo();
		super.redo();

	}

	private void collectGroupMembers() {
		getFBNetworkElement().getGroupElements()
		.forEach(el -> deleteGroupMembers.add(new DeleteFBNetworkElementCommand(el)));
	}

	@Override
	public Group getFBNetworkElement() {
		return (Group) super.getFBNetworkElement();
	}
}
