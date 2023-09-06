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
import java.util.Arrays;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ConvertSubappToGroupCommand extends Command {
	private final SubApp sourceSubapp;

	private CreateGroupCommand createGroupCmd;
	private final CompoundCommand convertToGroupCmd = new CompoundCommand();

	public ConvertSubappToGroupCommand(final SubApp source) {
		this.sourceSubapp = source;
	}

	@Override
	public void execute() {
		createConvertToGroupCommand();
		convertToGroupCmd.execute();

		// force same size as subapp
		final Group destination = createGroupCmd.getElement();
		destination.setWidth(sourceSubapp.getWidth());
		destination.setHeight(sourceSubapp.getHeight());

		// copy subapp name to group
		final ChangeNameCommand copyNameCmd = ChangeNameCommand.forName(destination, sourceSubapp.getName());
		copyNameCmd.execute();
		convertToGroupCmd.add(copyNameCmd);

		// copy instance comment of subapp
		final ChangeCommentCommand copyCommentCmd = new ChangeCommentCommand(destination, sourceSubapp.getComment());
		copyCommentCmd.execute();
		convertToGroupCmd.add(copyCommentCmd);

		ElementSelector.selectViewObjects(Arrays.asList(destination));
	}

	private void createConvertToGroupCommand() {
		final FBNetwork appNetwork = sourceSubapp.getFbNetwork();
		final List<?> subappContents = new ArrayList<>(sourceSubapp.getSubAppNetwork().getNetworkElements());
		// delete subapp
		convertToGroupCmd.add(new FlattenSubAppCommand(sourceSubapp, false));
		// create new group from subapp network
		final Rectangle bounds = new Rectangle(sourceSubapp.getPosition().getX(), sourceSubapp.getPosition().getY(),
				sourceSubapp.getWidth(), sourceSubapp.getHeight());
		createGroupCmd = new CreateGroupCommand(appNetwork, subappContents, bounds);
		convertToGroupCmd.add(createGroupCmd);
	}

	@Override
	public boolean canExecute() {
		return (sourceSubapp != null) && isUntypedSubapp(sourceSubapp);
	}

	private boolean isUntypedSubapp(final SubApp subapp) {
		return !(subapp.isTyped() || subapp.isContainedInTypedInstance());
	}

	@Override
	public void undo() {
		convertToGroupCmd.undo();
		ElementSelector.selectViewObjects(Arrays.asList(sourceSubapp));
	}

	@Override
	public void redo() {
		convertToGroupCmd.redo();
		ElementSelector.selectViewObjects(Arrays.asList(createGroupCmd.getElement()));
	}

	public Group getCreatedElement() {
		return createGroupCmd.getElement();
	}
}
