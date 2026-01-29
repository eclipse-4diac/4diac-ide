/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.commands.Command;

public class TargetLabelReconnectCommand extends Command {

	private IInterfaceElement source;
	private final IInterfaceElement destination;

	private AggressiveDeleteConnectionCommand deleteCommand;
	private CreateSubAppCrossingConnectionsCommand createCommand;

	public TargetLabelReconnectCommand(final IInterfaceElement source, final IInterfaceElement destination) {
		this.source = source;
		this.destination = destination;
	}

	@Override
	public void execute() {
		if (destination instanceof VarDeclaration) {
			deleteCommand = new AggressiveDeleteConnectionCommand(destination.getInputConnections().getFirst());
			deleteCommand.execute();
		}
		if (destination instanceof Event) {
			// TODO: Events: get correct input connection and delete it
		}
		createCommand = CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source,
				destination);
		createCommand.execute();
	}

	@Override
	public void redo() {
		deleteCommand.execute();
		createCommand.execute();
	}

	@Override
	public void undo() {
		createCommand.undo();
		deleteCommand.undo();
	}

	public void setSource(final IInterfaceElement source) {
		this.source = source;
	}
}
