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

import org.eclipse.fordiac.ide.application.figures.InstanceNameFigure;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ToggleSubAppRepresentationCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class ConvertGroupToSubappCommand extends Command {

	private final Group sourceGroup;

	private NewSubAppCommand createSubappCmd;
	private ChangeNameCommand copyNameCmd;
	private ChangeCommentCommand copyCommentCmd;
	private ToggleSubAppRepresentationCommand expandCommand;
	private DeleteGroupCommand deleteGroupCmd;

	public ConvertGroupToSubappCommand(final Group source) {
		this.sourceGroup = source;
	}

	@Override
	public void execute() {
		// create new subapp
		createSubappCmd = new NewSubAppCommand(sourceGroup.getFbNetwork(), sourceGroup.getGroupElements(),
				sourceGroup.getPosition().getX(), sourceGroup.getPosition().getY());
		createSubappCmd.execute();
		final SubApp destinationSubapp = createSubappCmd.getElement();
		destinationSubapp.setWidth(sourceGroup.getWidth());
		destinationSubapp.setHeight((int) (sourceGroup.getHeight() + CoordinateConverter.INSTANCE.getLineHeight()
		+ InstanceNameFigure.INSTANCE_LABEL_MARGIN));

		// transfer group name
		copyNameCmd = ChangeNameCommand.forName(destinationSubapp, sourceGroup.getName());
		copyNameCmd.execute();

		// copy instance comment of group
		copyCommentCmd = new ChangeCommentCommand(destinationSubapp, sourceGroup.getComment());
		copyCommentCmd.execute();

		// expand subapp to mimick group representation
		expandCommand = new ToggleSubAppRepresentationCommand(destinationSubapp);
		expandCommand.execute();

		// delete group
		deleteGroupCmd = new DeleteGroupCommand(sourceGroup);
		deleteGroupCmd.execute();
	}

	@Override
	public boolean canExecute() {
		return sourceGroup != null;
	}

	@Override
	public void undo() {
		deleteGroupCmd.undo();
		copyNameCmd.undo();
		copyCommentCmd.undo();
		expandCommand.undo();
		createSubappCmd.undo();
	}

	@Override
	public void redo() {
		createSubappCmd.redo();
		copyNameCmd.redo();
		copyCommentCmd.redo();
		expandCommand.redo();
		deleteGroupCmd.redo();
	}

	public SubApp getCreatedElement() {
		return createSubappCmd.getElement();
	}
}
