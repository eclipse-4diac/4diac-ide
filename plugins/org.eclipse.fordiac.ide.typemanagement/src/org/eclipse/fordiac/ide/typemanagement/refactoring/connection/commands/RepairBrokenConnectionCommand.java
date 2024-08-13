/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectEventConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.EventConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * A Command for Repairing a broken Connection. The command searches for the
 * first occurrence of the given Struct and reconnects the broken Connection. If
 * not already present, a Multiplexer or Demultiplexer is placed automatically
 * with
 * {@link org.eclipse.fordiac.ide.typemanagement.refactoring.connection.commands.InsertStructManipulatorCommand
 * InsertStructManipulatorCommand}.
 */
public class RepairBrokenConnectionCommand extends Command {

	private final Connection connection;
	final boolean isSourceReconnect;
	private final StructuredType structType;
	private final String varName;

	InsertStructManipulatorCommand insertMuxCommand;
	ReconnectDataConnectionCommand recon;
	CompoundCommand eventConnectionCommand;

	/**
	 * Creates a new Instance
	 *
	 * @param con               The broken Connection
	 * @param isSourceReconnect If the Source has to be reconnected (otherwise the
	 *                          Destination will be reconnected)
	 * @param structType        Type of the Struct to connect to
	 * @param varName           Name of the Variable within the Struct
	 */
	public RepairBrokenConnectionCommand(final Connection con, final boolean isSourceReconnect,
			final StructuredType structType, final String varName) {
		this.connection = Objects.requireNonNull(con);
		this.isSourceReconnect = isSourceReconnect;
		this.structType = Objects.requireNonNull(structType);
		this.varName = Objects.requireNonNull(varName);
	}

	private IInterfaceElement getPort() {
		Optional<IInterfaceElement> port;
		if (isSourceReconnect) {
			port = connection.getSourceElement().getInterface().getOutputs()
					.filter(output -> output.getType().equals(structType)).findAny();
		} else {
			port = connection.getDestinationElement().getInterface().getInputs()
					.filter(input -> input.getType().equals(structType)).findAny();
		}

		return port.orElse(null);
	}

	private FBNetworkElement getMux(final IInterfaceElement port) {
		final Optional<FBNetworkElement> optmux;
		if (isSourceReconnect) {
			optmux = port.getOutputConnections().stream().map(Connection::getDestinationElement)
					.filter(elem -> elem.getType().getTypeEntry()
							.equals(port.getFBNetworkElement().getTypeLibrary().getFBTypeEntry("STRUCT_DEMUX")))
					.findAny();
		} else {
			optmux = port.getInputConnections().stream().map(Connection::getSourceElement)
					.filter(elem -> elem.getType().getTypeEntry()
							.equals(port.getFBNetworkElement().getTypeLibrary().getFBTypeEntry("STRUCT_MUX")))
					.findAny();
		}

		return optmux.orElseGet(() -> {
			insertMuxCommand = new InsertStructManipulatorCommand(port);
			insertMuxCommand.execute();
			return insertMuxCommand.getNewElement();
		});
	}

	@Override
	public boolean canExecute() {
		final IInterfaceElement port = getPort();
		if (port != null) {
			final TypeLibrary lib = port.getFBNetworkElement().getTypeLibrary();
			return lib.getFBTypeEntry("STRUCT_DEMUX") != null && lib.getFBTypeEntry("STRUCT_MUX") != null;
		}
		return false;
	}

	@Override
	public void execute() {
		final FBNetwork fbn = connection.getFBNetwork();
		final List<IInterfaceElement> newConList = new ArrayList<>();
		eventConnectionCommand = new CompoundCommand();

		final IInterfaceElement port = getPort();
		if (port == null) {
			return;
		}
		final FBNetworkElement mux = getMux(port);

		final Stream<Event> eventStream = ((VarDeclaration) port).getWiths().stream().map(With::eContainer)
				.filter(Event.class::isInstance).map(Event.class::cast);
		final Stream<Connection> connectionStream;
		final IInterfaceElement eventReconTarget;
		final IInterfaceElement eventNewTarget;

		if (isSourceReconnect) {
			connectionStream = eventStream.map(Event::getOutputConnections).flatMap(EList::stream)
					.filter(con -> connection.getDestinationElement().equals(con.getDestinationElement()));
			eventReconTarget = mux.getInterface().getEventInputs().getFirst();
			eventNewTarget = mux.getInterface().getEventOutputs().getFirst();
		} else {
			connectionStream = eventStream.map(Event::getInputConnections).flatMap(EList::stream)
					.filter(con -> connection.getSourceElement().equals(con.getSourceElement()));
			eventReconTarget = mux.getInterface().getEventOutputs().getFirst();
			eventNewTarget = mux.getInterface().getEventInputs().getFirst();
		}

		connectionStream.forEach(con -> {
			if (!checkDuplicate(con, eventReconTarget)) {
				eventConnectionCommand
						.add(new ReconnectEventConnectionCommand(con, !isSourceReconnect, eventReconTarget, fbn));
			} else {
				eventConnectionCommand.add(new DeleteConnectionCommand(con));
			}

			final IInterfaceElement source = isSourceReconnect ? con.getDestination() : con.getSource();
			if (!checkDuplicate(connection, eventNewTarget) && !newConList.contains(source)) {
				final EventConnectionCreateCommand createECon = new EventConnectionCreateCommand(fbn);
				createECon.setSource(source);
				createECon.setDestination(eventNewTarget);
				eventConnectionCommand.add(createECon);
				newConList.add(source);
			}
		});

		eventConnectionCommand.add(new ReconnectDataConnectionCommand(connection, isSourceReconnect,
				isSourceReconnect ? mux.getInterface().getOutput(varName) : mux.getInterface().getInput(varName), fbn));
		eventConnectionCommand.execute();

	}

	private static boolean checkDuplicate(final Connection connection, final IInterfaceElement target) {
		if (target.isIsInput()) {
			return target.getInputConnections().stream()
					.anyMatch(con -> con.getSource().equals(connection.getSource()));
		}
		return target.getOutputConnections().stream()
				.anyMatch(con -> con.getDestination().equals(connection.getDestination()));
	}

	@Override
	public boolean canUndo() {
		return eventConnectionCommand.canUndo() && (insertMuxCommand == null || insertMuxCommand.canUndo());
	}

	@Override
	public void undo() {
		eventConnectionCommand.undo();
		if (insertMuxCommand != null) {
			insertMuxCommand.undo();
		}
	}

	@Override
	public boolean canRedo() {
		return eventConnectionCommand.canRedo() && (insertMuxCommand == null || insertMuxCommand.canRedo());
	}

	@Override
	public void redo() {
		if (insertMuxCommand != null) {
			insertMuxCommand.redo();
		}
		eventConnectionCommand.redo();
	}
}
