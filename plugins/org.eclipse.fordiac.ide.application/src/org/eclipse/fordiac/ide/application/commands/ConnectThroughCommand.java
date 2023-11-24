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

import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
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

public class ConnectThroughCommand extends Command implements ScopedCommand {

	private final IInterfaceElement input;
	private final IInterfaceElement output;
	private final FBNetwork parent;

	private final CompoundCommand deleteConnectionCommands = new CompoundCommand();
	private final CompoundCommand createConnectionCommands = new CompoundCommand();

	public ConnectThroughCommand(final IInterfaceElement input, final IInterfaceElement output) {
		this.input = Objects.requireNonNull(input);
		this.output = Objects.requireNonNull(output);
		parent = Objects.requireNonNull(input.getFBNetworkElement().getFbNetwork());
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

	private void createDeleteCommands(final EList<Connection> connectionList) {
		connectionList.forEach(con -> deleteConnectionCommands.add(new DeleteConnectionCommand(con)));
	}

	private void createCreateCommands() {
		for (final Connection con : input.getInputConnections()) {
			output.getOutputConnections().forEach(outconn -> {
				final AbstractConnectionCreateCommand cmd = getConnectionCreateCommand();
				cmd.setSource(con.getSource());
				cmd.setDestination(outconn.getDestination());
				createConnectionCommands.add(cmd);
			});
		}
	}

	private AbstractConnectionCreateCommand getConnectionCreateCommand() {
		if (input instanceof Event) {
			return new EventConnectionCreateCommand(parent);
		}
		if (input instanceof AdapterDeclaration) {
			return new AdapterConnectionCreateCommand(parent);
		}
		return new DataConnectionCreateCommand(parent);
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		return Set.of(parent);
	}
}
