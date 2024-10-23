/*******************************************************************************
 * Copyright (c) 2016, 2017 fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.change;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.commands.ScopedCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;

public abstract class AbstractReconnectConnectionCommand extends Command implements ScopedCommand {
	private FBNetwork parent;
	private final Connection connection;
	private final boolean isSourceReconnect;
	private final IInterfaceElement newTarget;
	private DeleteConnectionCommand deleteConnectionCmd;
	private AbstractConnectionCreateCommand connectionCreateCmd;

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
		if ((sourceIE != null) && (targetIE != null)) {
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
		// this should be always possible
		return true;
	}

	@Override
	public void execute() {
		final Connection con = getConnnection();
		deleteConnectionCmd = new DeleteConnectionCommand(con);
		connectionCreateCmd = createConnectionCreateCommand(parent);
		connectionCreateCmd.setSource(getNewSource());
		connectionCreateCmd.setDestination(getNewDestination());
		connectionCreateCmd.setArrangementConstraints(con.getRoutingData());
		connectionCreateCmd.setVisible(con.isVisible());
		connectionCreateCmd.setElementIndex(parent.getConnectionIndex(con));
		connectionCreateCmd.execute(); // perform adding the connection first to preserve any error markers
		deleteConnectionCmd.execute();
		copyAttributes(connectionCreateCmd.getConnection(), deleteConnectionCmd.getConnection());

	}

	@Override
	public void redo() {
		connectionCreateCmd.redo();
		deleteConnectionCmd.redo();
	}

	@Override
	public void undo() {
		deleteConnectionCmd.undo();
		connectionCreateCmd.undo();
	}

	private static void copyAttributes(final Connection dstCon, final Connection srcCon) {
		srcCon.getAttributes().forEach(
				attr -> dstCon.setAttribute(attr.getName(), attr.getType(), attr.getValue(), attr.getComment()));
	}

	@Override
	public Set<EObject> getAffectedObjects() {
		final Set<EObject> result = Stream.of(parent, connection).filter(Objects::nonNull)
				.collect(Collectors.toCollection(HashSet::new));
		if (connectionCreateCmd != null) {
			result.addAll(connectionCreateCmd.getAffectedObjects());
		}
		if (deleteConnectionCmd != null) {
			result.addAll(deleteConnectionCmd.getAffectedObjects());
		}
		return Set.copyOf(result);
	}

	protected abstract AbstractConnectionCreateCommand createConnectionCreateCommand(FBNetwork parent);

	protected abstract boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE);
}