/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.create;

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.data.EventType;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class CreateConnectionAtSubappInterfaceCommand extends Command implements ScopedCommand {

	CreateInterfaceElementCommand createIECmdCommand;
	AbstractConnectionCreateCommand createConCommand;
	IInterfaceElement ie;
	SubApp subApp;

	public CreateConnectionAtSubappInterfaceCommand(final IInterfaceElement ie, final SubApp subApp) {
		this.ie = Objects.requireNonNull(ie);
		this.subApp = Objects.requireNonNull(subApp);
	}

	@Override
	public void execute() {
		createIECmdCommand = new CreateInterfaceElementCommand(ie.getType(), ie.getName(), subApp.getInterface(),
				ie.isIsInput(), ArraySizeHelper.getArraySize(ie), -1);

		if (ie.getType() instanceof EventType) {
			createConCommand = new EventConnectionCreateCommand(subApp.getSubAppNetwork());
		} else if (ie.getType() instanceof AdapterType) {
			createConCommand = new AdapterConnectionCreateCommand(subApp.getSubAppNetwork());
		} else {
			createConCommand = new DataConnectionCreateCommand(subApp.getSubAppNetwork());
		}

		if (createIECmdCommand.canExecute()) {
			createIECmdCommand.execute();

			if (ie.isIsInput()) {
				createConCommand.setSource(createIECmdCommand.getCreatedElement());
				createConCommand.setDestination(ie);
			} else {
				createConCommand.setDestination(createIECmdCommand.getCreatedElement());
				createConCommand.setSource(ie);
			}

			if (createConCommand.canExecute()) {
				createConCommand.execute();
			} else {
				createIECmdCommand.canUndo();
			}
		}
	}

	@Override
	public void undo() {
		createConCommand.undo();
		createIECmdCommand.undo();
	}

	@Override
	public void redo() {
		createIECmdCommand.redo();
		createConCommand.redo();
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(subApp);
	}
}
