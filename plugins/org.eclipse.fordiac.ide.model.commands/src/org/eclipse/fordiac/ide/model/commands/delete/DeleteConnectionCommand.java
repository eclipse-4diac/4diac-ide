/*******************************************************************************
 * Copyright (c) 2008, 2021 Profactor GmbH, fortiss GmbH, AIT,
 *                          Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Filip Pröstl Andren, Monika Wenger
 *   	- initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *   Michael Oberlehner - added support for error marker handling
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.commands.delete;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteConnectionCommand extends Command {
	private IInterfaceElement source;
	private IInterfaceElement destination;
	private final Connection connection;
	private final FBNetwork connectionParent;
	private boolean performMappingCheck;
	private DeleteConnectionCommand deleteMapped = null;
	private final CompoundCommand deleteErrorMarkers = new CompoundCommand();
	private FBNetworkElement errorFb;

	public DeleteConnectionCommand(final Connection connection) {
		this(connection, null);
		this.errorFb = null;
	}

	public DeleteConnectionCommand(final Connection connection, final FBNetworkElement errorFb) {
		super(Messages.DeleteConnectionCommand_DeleteConnection);
		this.connection = connection;
		if (this.connection.eContainer() instanceof FBNetwork) {
			connectionParent = (FBNetwork) this.connection.eContainer();
		} else {
			connectionParent = null;
		}
		performMappingCheck = true;
		this.errorFb = errorFb;
	}

	public Connection getConnectionView() {
		return connection;
	}

	@Override
	public void execute() {
		source = connection.getSource();
		destination = connection.getDestination();

		if (performMappingCheck) {
			deleteMapped = checkAndDeleteMirroredConnection();
			if (null != deleteMapped) {
				deleteMapped.execute();
			}
		}
		deleteConnection();
		handleErrorMarker();
	}

	private void handleErrorMarker() {
		checkErrorMarker();
		deleteErrorMarkers.execute();
	}

	@Override
	public void redo() {
		deleteConnection();
		deleteErrorMarkers.redo();
	}

	private void deleteConnection() {
		connection.setSource(null);
		connection.setDestination(null);
		if (null != deleteMapped) {
			deleteMapped.redo();
		}
		if (connectionParent != null) {
			connectionParent.removeConnection(connection);
		}
	}

	@Override
	public void undo() {
		deleteErrorMarkers.undo();
		connection.setSource(source);
		connection.setDestination(destination);
		if (connectionParent != null) {
			connectionParent.addConnection(connection);
		}
		if (null != deleteMapped) {
			deleteMapped.undo();
		}

	}

	private DeleteConnectionCommand checkAndDeleteMirroredConnection() {
		if (null != source && null != source.getFBNetworkElement() && null != destination
				&& null != destination.getFBNetworkElement()) {
			final FBNetworkElement opSource = source.getFBNetworkElement().getOpposite();
			final FBNetworkElement opDestination = destination.getFBNetworkElement().getOpposite();
			if (null != opSource && null != opDestination && opSource.getFbNetwork() == opDestination.getFbNetwork()) {
				final Connection con = findConnection(opSource.getInterfaceElement(source.getName()),
						opDestination.getInterfaceElement(destination.getName()));
				if (null != con) {
					final DeleteConnectionCommand cmd = new DeleteConnectionCommand(con);
					cmd.setPerformMappingCheck(false); // as this is the command for the mirrored connection we don't
					// want again to check
					return (cmd.canExecute()) ? cmd : null;
				}
			}
		}
		return null;
	}

	private static Connection findConnection(final IInterfaceElement source, final IInterfaceElement destination) {
		for (final Connection con : source.getOutputConnections()) {
			if (con.getDestination() == destination) {
				return con;
			}
		}
		return null;
	}

	private void setPerformMappingCheck(final boolean performMappingCheck) {
		this.performMappingCheck = performMappingCheck;
	}

	private void checkErrorMarker() {
		if (source instanceof ErrorMarkerInterface
				&& getConnectionCount(source) == 0) {
			deleteErrorMarkers.add(new DeleteErrorMarkerCommand((ErrorMarkerInterface) source, errorFb));
		}
		if (destination instanceof ErrorMarkerInterface
				&& getConnectionCount(destination) == 0) {
			deleteErrorMarkers.add(new DeleteErrorMarkerCommand((ErrorMarkerInterface) destination, errorFb));
		}
	}

	private static int getConnectionCount(final IInterfaceElement ie) {
		return ie.isIsInput() ? ie.getInputConnections().size() : ie.getOutputConnections().size();
	}

}
