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
 *   Bianca Wiesmayr - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class ConvertSubappToGroupCommand extends Command {
	private final SubApp sourceSubapp;

	private CreateGroupCommand createGroupCmd;
	private ChangeCommentCommand copyCommentCmd;
	private FlattenSubAppCommand deleteSubappCmd;

	public ConvertSubappToGroupCommand(SubApp source) {
		this.sourceSubapp = source;
	}

	@Override
	public void execute() {
		// create new group from subapp network
		List<?> subappContents = new ArrayList<>(sourceSubapp.getSubAppNetwork().getNetworkElements());
		Rectangle bounds = new Rectangle(sourceSubapp.getPosition().getX(), sourceSubapp.getPosition().getY(), sourceSubapp.getWidth(), sourceSubapp.getHeight());
		createGroupCmd = new CreateGroupCommand(sourceSubapp.getFbNetwork(), subappContents, bounds);
		createGroupCmd.execute();
		Group destination = createGroupCmd.getElement();
		destination.setWidth(sourceSubapp.getWidth());
		destination.setHeight(sourceSubapp.getHeight());

		// copy instance comment of subapp
		copyCommentCmd = new ChangeCommentCommand(destination, sourceSubapp.getComment());
		copyCommentCmd.execute();

		// delete subapp
		deleteSubappCmd = new FlattenSubAppCommand(sourceSubapp);
		deleteSubappCmd.execute();
	}

	@Override
	public boolean canExecute() {
		return (sourceSubapp != null) && isUntypedSubapp(sourceSubapp);
	}

	private boolean isUntypedSubapp(SubApp subapp) {
		return !(subapp.isTyped() || subapp.isContainedInTypedInstance());
	}

	@Override
	public void undo() {
		deleteSubappCmd.undo();
		copyCommentCmd.undo();
		createGroupCmd.undo();
	}

	@Override
	public void redo() {
		createGroupCmd.redo();
		copyCommentCmd.redo();
		deleteSubappCmd.redo();
	}

	public Group getCreatedElement() {
		return createGroupCmd.getElement();
	}
}
