/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.AdapterConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class ConnectThroughCommand extends Command {

	final IInterfaceElement input;
	final IInterfaceElement output;

	final CompoundCommand deleteConnectionCommands = new CompoundCommand();
	final CompoundCommand createConnectionCommands = new CompoundCommand();

	public ConnectThroughCommand(IInterfaceElement input, IInterfaceElement output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void execute() {
		createDeleteCommands(input.getInputConnections());
		createDeleteCommands(output.getOutputConnections());

		createCreateCommands();

		deleteConnectionCommands.execute();
		createConnectionCommands.execute();
	}

	@Override
	public void undo() {
		createConnectionCommands.undo();
		deleteConnectionCommands.undo();
	}

	@Override
	public void redo() {
		deleteConnectionCommands.redo();
		createConnectionCommands.redo();
	}

	private void createDeleteCommands(EList<Connection> connectionList) {
		connectionList.forEach(con -> deleteConnectionCommands.add(new DeleteConnectionCommand(con)));
	}

	private void createCreateCommands() {
		for (Connection con : input.getInputConnections()) {
			output.getOutputConnections().forEach(outconn -> {
				AbstractConnectionCreateCommand cmd = getConnectionCreateCommand(input);
				cmd.setSource(con.getSource());
				cmd.setDestination(outconn.getDestination());
				createConnectionCommands.add(cmd);
			});
		}
	}

	private static AbstractConnectionCreateCommand getConnectionCreateCommand(IInterfaceElement port) {
		FBNetwork parent = port.getFBNetworkElement().getFbNetwork();
		if (port instanceof Event) {
			return new EventConnectionCreateCommand(parent);
		}
		if (port instanceof AdapterDeclaration) {
			return new AdapterConnectionCreateCommand(parent);
		}
		return new DataConnectionCreateCommand(parent);
	}
}
