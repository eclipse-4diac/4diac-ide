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

import org.eclipse.fordiac.ide.application.editparts.SubAppForFBNetworkEditPart;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
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

	public AbstractReconnectConnectionCommand(String label, final ReconnectRequest request, final FBNetwork parent) {
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
		EditPart source = null;
		EditPart target = null;
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			source = request.getConnectionEditPart().getSource();
			target = request.getTarget();
		} else if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			source = request.getTarget();
			target = request.getConnectionEditPart().getTarget();
		}
		if ((source instanceof InterfaceEditPart) && (target instanceof InterfaceEditPart)) {
			IInterfaceElement sourceIE = ((InterfaceEditPart) source).getModel();
			IInterfaceElement targetIE = ((InterfaceEditPart) target).getModel();
			return checkSourceAndTarget(sourceIE, targetIE);
		}
		if (source instanceof SubAppForFBNetworkEditPart) {
			boolean unfoldedSource = ((SubAppForFBNetworkEditPart) source).getModel().isUnfolded();
			// TODO check for the specific connections in unfolded subapps (contained
			// elements with parent interface)
		}
		return false;
	}

	@Override
	public void execute() {
		Connection con = (Connection) request.getConnectionEditPart().getModel();
		deleteConnectionCmd = new DeleteConnectionCommand(con);
		connectionCreateCmd = createConnectionCreateCommand(parent);

		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			doReconnectTarget();
		}
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			doReconnectSource();
		}

		connectionCreateCmd.setArrangementConstraints(con.getDx1(), con.getDx2(), con.getDy());

		deleteConnectionCmd.execute();
		connectionCreateCmd.execute();
	}

	protected void doReconnectSource() {
		connectionCreateCmd.setSource(((InterfaceEditPart) request.getTarget()).getModel());
		connectionCreateCmd
		.setDestination(((InterfaceEditPart) request.getConnectionEditPart().getTarget()).getModel());
	}

	protected void doReconnectTarget() {
		connectionCreateCmd.setSource(((InterfaceEditPart) request.getConnectionEditPart().getSource()).getModel());
		connectionCreateCmd.setDestination(((InterfaceEditPart) request.getTarget()).getModel());
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