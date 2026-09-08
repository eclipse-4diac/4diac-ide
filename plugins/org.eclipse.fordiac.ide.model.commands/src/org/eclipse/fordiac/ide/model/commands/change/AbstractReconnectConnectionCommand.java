/*******************************************************************************
 * Copyright (c) 2016, 2025 fortiss GmbH, Johannes Keppler University Linz
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl, Monika Wenger
 *    - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *   Sebastian Hollersbacher - added merging of connections
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteSubAppInterfaceElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.gef.commands.Command;

public abstract class AbstractReconnectConnectionCommand extends Command implements ScopedCommand {
	private FBNetwork parent;
	private final Connection connection;
	protected final boolean isSourceReconnect;
	protected final IInterfaceElement newTarget;

	protected final Map<BlockFBNetworkElement, IInterfaceElement> usablePins = new HashMap<>();

	private final List<DeleteConnectionCommand> deleteConnectionCmd = new ArrayList<>();
	private final List<DeleteSubAppInterfaceElementCommand> deleteInterfaceCmd = new ArrayList<>();
	private final List<AbstractConnectionCreateCommand> connectionCreateCmd = new ArrayList<>();

	protected AbstractReconnectConnectionCommand(final String label, final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget, final FBNetwork parent) {
		super(label);
		this.connection = Objects.requireNonNull(connection);
		this.isSourceReconnect = isSourceReconnect;
		this.newTarget = newTarget;
		this.parent = parent;
	}

	public FBNetwork getParent() {
		return parent;
	}

	public void setParent(final FBNetwork parent) {
		this.parent = parent;
	}

	@Override
	public boolean canExecute() {
		final IInterfaceElement sourceIE = getNewSource();
		final IInterfaceElement targetIE = getNewDestination();
		if (sourceIE != null && targetIE != null) {
			return checkSourceAndTarget(sourceIE, targetIE);
		}
		return false;
	}

	public IInterfaceElement getNewSource() {
		if (!isSourceReconnect) {
			return getConnnection().getSource();
		}
		return newTarget;
	}

	public IInterfaceElement getNewDestination() {
		if (isSourceReconnect) {
			return getConnnection().getDestination();
		}
		return newTarget;
	}

	protected Connection getConnnection() {
		return connection;
	}

	@Override
	public boolean canRedo() {
		return true; // this should be always possible
	}

	@Override
	public void execute() {
		// collect usable pins of subapps that are connected to the new target
		collectUsablePins(newTarget);
		if (usablePins.isEmpty()) {
			// no path to follow (default reconnect)
			// always the case for destination reconnect of data connections
			reconnectConnection(connection, newTarget, true);
		} else {
			// recursively reconnect all connections to new usable pins
			reconnectRecursively(connection);
		}

		connectionCreateCmd.forEach(AbstractConnectionCreateCommand::execute);
		deleteConnectionCmd.forEach(deleteCommand -> {
			final var conn = deleteCommand.getConnection();
			final var ie = isSourceReconnect ? conn.getSource() : conn.getDestination();
			deleteCommand.execute();
			if (conn != this.connection && ie.getInputConnections().isEmpty() && ie.getOutputConnections().isEmpty()) {
				final var deletePinCmd = new DeleteSubAppInterfaceElementCommand(ie);
				deletePinCmd.execute();
				deleteInterfaceCmd.add(deletePinCmd);
			}
		});
	}

	protected void collectUsablePins(final IInterfaceElement curr) {
		final var connections = isSourceReconnect ? curr.getOutputConnections() : curr.getInputConnections();
		connections.forEach(conn -> {
			final var pin = isSourceReconnect ? conn.getDestination() : conn.getSource();
			if (pin.getBlockFBNetworkElement() instanceof final UntypedSubApp utsa) {
				final var pinConnections = isSourceReconnect ? pin.getInputConnections() : pin.getOutputConnections();
				if (pinConnections.size() == 1) {
					usablePins.put(utsa, pin);
					collectUsablePins(pin);
				}
			}
		});

	}

	protected void reconnectRecursively(final Connection curr) {
		final var traversalElement = isSourceReconnect ? curr.getDestinationElement() : curr.getSourceElement();
		final var nextConnections = isSourceReconnect ? curr.getDestination().getOutputConnections()
				: curr.getSource().getInputConnections();

		if (usablePins.containsKey(traversalElement) && !nextConnections.isEmpty()) {
			// continue traversing
			deleteConnectionCmd.add(new DeleteConnectionCommand(curr));
			nextConnections.forEach(this::reconnectRecursively);
		} else {
			// no usable pin at the far end — reconnect to the last usable source/dest
			final var lookupKey = isSourceReconnect ? curr.getSourceElement() : curr.getDestinationElement();
			final var pin = usablePins.get(lookupKey);

			if (pin != null) {
				reconnectConnection(curr, pin, true);
			}
		}
	}

	protected void reconnectConnection(final Connection conn, final IInterfaceElement newPin, final boolean delete) {
		final var opposite = isSourceReconnect ? conn.getDestination() : conn.getSource();
		final var src = isSourceReconnect ? newPin : opposite;
		final var dst = isSourceReconnect ? opposite : newPin;

		connectionCreateCmd.add(getCreateConnectionCommand(conn, src, dst));
		if (delete) {
			deleteConnectionCmd.add(new DeleteConnectionCommand(conn));
		}
	}

	@Override
	public void redo() {
		connectionCreateCmd.forEach(AbstractConnectionCreateCommand::redo);
		deleteConnectionCmd.forEach(DeleteConnectionCommand::redo);
		deleteInterfaceCmd.forEach(DeleteSubAppInterfaceElementCommand::redo);
	}

	@Override
	public void undo() {
		deleteInterfaceCmd.forEach(DeleteSubAppInterfaceElementCommand::undo);
		deleteConnectionCmd.forEach(DeleteConnectionCommand::undo);
		connectionCreateCmd.forEach(AbstractConnectionCreateCommand::undo);
	}

	private AbstractConnectionCreateCommand getCreateConnectionCommand(final Connection con,
			final IInterfaceElement source, final IInterfaceElement dest) {
		final AbstractConnectionCreateCommand cmd = createConnectionCreateCommand(con.getFBNetwork());
		cmd.setSource(source);
		cmd.setDestination(dest);
		cmd.setArrangementConstraints(con.getRoutingData());
		cmd.setAttributes(con.getAttributes());
		cmd.setElementIndex(con.getFBNetwork().getConnectionIndex(con));
		return cmd;
	}

	protected abstract AbstractConnectionCreateCommand createConnectionCreateCommand(FBNetwork parent);

	protected abstract boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE);

	@Override
	public Set<EObject> getAffectedObjects() {
		final Set<EObject> result = Stream.of(parent, connection).filter(Objects::nonNull)
				.collect(Collectors.toCollection(HashSet::new));

		result.addAll(connectionCreateCmd.stream().flatMap(cmd -> cmd.getAffectedObjects().stream()).toList());
		result.addAll(deleteConnectionCmd.stream().flatMap(cmd -> cmd.getAffectedObjects().stream()).toList());
		result.addAll(deleteInterfaceCmd.stream().flatMap(cmd -> cmd.getAffectedObjects().stream()).toList());

		return Set.copyOf(result);
	}
}
