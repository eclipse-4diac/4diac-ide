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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.figures.InstanceNameFigure;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ToggleSubAppRepresentationCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class ConvertGroupToSubappCommand extends Command implements ScopedCommand {
	private final Group sourceGroup;
	private final FBNetwork fbNetwork;

	private final NewSubAppCommand createSubappCmd;
	private ChangeNameCommand copyNameCmd;
	private ChangeCommentCommand copyCommentCmd;
	private ToggleSubAppRepresentationCommand expandCommand;
	private DeleteGroupCommand deleteGroupCmd;

	public ConvertGroupToSubappCommand(final Group source) {
		sourceGroup = Objects.requireNonNull(source);
		fbNetwork = sourceGroup.getFbNetwork();
		createSubappCmd = new NewSubAppCommand(fbNetwork, sourceGroup.getGroupElements(),
				sourceGroup.getPosition().getX(), sourceGroup.getPosition().getY());
	}

	@Override
	public void execute() {
		// create new subapp
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
		return createSubappCmd.canExecute();
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

	@Override
	public Set<EObject> getAffectedObjects() {
		if (fbNetwork != null) {
			return Set.of(fbNetwork);
		}
		return Set.of();
	}
}
