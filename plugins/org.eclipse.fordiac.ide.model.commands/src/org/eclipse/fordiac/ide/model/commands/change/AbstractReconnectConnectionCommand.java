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

import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerInterface;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;

public abstract class AbstractReconnectConnectionCommand extends Command {
	private FBNetwork parent;
	private final Connection connection;
	private final boolean isSourceReconnect;
	private final IInterfaceElement newTarget;
	private DeleteConnectionCommand deleteConnectionCmd;
	private AbstractConnectionCreateCommand connectionCreateCmd;

	protected AbstractReconnectConnectionCommand(final String label, final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget, final FBNetwork parent) {
		super(label);
		this.connection = connection;
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
		handleErrorConnection(con);
		connectionCreateCmd.setArrangementConstraints(con.getRoutingData());
		connectionCreateCmd.setVisible(con.isVisible());
		connectionCreateCmd.execute(); // perform adding the connection first to preserve any error markers
		deleteConnectionCmd.execute();
		copyAttributes(connectionCreateCmd.getConnection(), deleteConnectionCmd.getConnection());

	}

	public void handleErrorConnection(final Connection con) {
		if (con.hasError()) {
			final IInterfaceElement newSource = getNewSource();
			final IInterfaceElement newDestination = getNewDestination();
			if (newSource instanceof ErrorMarkerInterface && !(newDestination instanceof ErrorMarkerInterface)
					&& ((ErrorMarkerInterface) newSource).getRepairedEndpoint() != null) {
				connectionCreateCmd.setSource(((ErrorMarkerInterface) newSource).getRepairedEndpoint());
			}
			if (!(newSource instanceof ErrorMarkerInterface) && newDestination instanceof ErrorMarkerInterface
					&& ((ErrorMarkerInterface) newDestination).getRepairedEndpoint() != null) {
				connectionCreateCmd.setDestination(((ErrorMarkerInterface) newDestination).getRepairedEndpoint());
			}
		}
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

	protected abstract AbstractConnectionCreateCommand createConnectionCreateCommand(FBNetwork parent);

	protected abstract boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE);
}