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
package org.eclipse.fordiac.ide.application.commands;

import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

public abstract class AbstractReconnectConnectionCommand extends Command {
	private final FBNetwork parent;
	private final ReconnectRequest request;
	private DeleteConnectionCommand deleteConnectionCmd;
	private AbstractConnectionCreateCommand connectionCreateCmd;

	protected AbstractReconnectConnectionCommand(final String label, final ReconnectRequest request,
			final FBNetwork parent) {
		super(label);
		this.request = request;
		this.parent = parent;
	}

	public ReconnectRequest getRequest() {
		return request;
	}

	protected FBNetwork getParent() {
		return parent;
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
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			return getConnnection().getSource();
		}
		return getRequestTarget();
	}

	public IInterfaceElement getNewDestination() {
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			return getConnnection().getDestination();
		}
		return getRequestTarget();
	}

	private Connection getConnnection() {
		return (Connection) request.getConnectionEditPart().getModel();
	}

	private IInterfaceElement getRequestTarget() {
		final EditPart target = request.getTarget();
		if (target.getModel() instanceof IInterfaceElement) {
			return (IInterfaceElement) target.getModel();
		}
		return null;
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

		connectionCreateCmd.execute();  // perform adding the connection first to preserve any error markers
		deleteConnectionCmd.execute();
	}

	@Override
	public void redo() {
		deleteConnectionCmd.redo();
		connectionCreateCmd.redo();
	}

	@Override
	public void undo() {
		connectionCreateCmd.undo();
		deleteConnectionCmd.undo();
	}

	protected abstract AbstractConnectionCreateCommand createConnectionCreateCommand(FBNetwork parent);

	protected abstract boolean checkSourceAndTarget(IInterfaceElement sourceIE, IInterfaceElement targetIE);
}