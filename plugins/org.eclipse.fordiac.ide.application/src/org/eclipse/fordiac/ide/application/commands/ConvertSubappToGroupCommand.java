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
import java.util.Objects;
import java.util.Set;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.utilities.ElementSelector;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateGroupCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.Group;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ConvertSubappToGroupCommand extends Command implements ScopedCommand {
	private final SubApp sourceSubapp;
	private final FBNetwork fbNetwork;

	private CreateGroupCommand createGroupCmd;
	private final CompoundCommand convertToGroupCmd = new CompoundCommand();

	public ConvertSubappToGroupCommand(final SubApp source) {
		sourceSubapp = Objects.requireNonNull(source);
		fbNetwork = source.getFbNetwork();
		createConvertToGroupCommand();
	}

	@Override
	public void execute() {
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
		final List<?> subappContents = new ArrayList<>(sourceSubapp.getSubAppNetwork().getNetworkElements());
		// delete subapp, this absolutely has to be done first
		convertToGroupCmd.add(new FlattenSubAppCommand(sourceSubapp, false));
		// create new group from subapp network
		final Rectangle bounds = new Rectangle(sourceSubapp.getPosition().toScreenPoint(),
				new Dimension(sourceSubapp.getWidth(), sourceSubapp.getHeight()));
		createGroupCmd = new CreateGroupCommand(fbNetwork, subappContents, bounds);
		convertToGroupCmd.add(createGroupCmd);
	}

	@Override
	public boolean canExecute() {
		return isUntypedSubapp(sourceSubapp) && !sourceSubapp.isInGroup();
	}

	private static boolean isUntypedSubapp(final SubApp subapp) {
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

	@Override
	public Set<EObject> getAffectedObjects() {
		if (fbNetwork != null) {
			return Set.of(fbNetwork);
		}
		return Set.of();
	}
}
