/*******************************************************************************
 * Copyright (c) 2011, 2013, 2016, 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo
 *               - removed duplicated code
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Messages;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * A command for reconnecting transition connections.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public class ReconnectTransitionCommand extends Command {

	/** The request. */
	private final ReconnectRequest request;

	/** The cmd. */
	private DeleteTransitionCommand cmd;

	/** The dccc. */
	private CreateTransitionCommand dccc;

	/**
	 * A command for reconnecting data connection.
	 *
	 * @param request the request
	 */
	public ReconnectTransitionCommand(final ReconnectRequest request) {
		super(Messages.ReconnectTransitionCommand_ReconnectTransition);
		this.request = request;
	}

	/**
	 * Can execute.
	 *
	 * @return <code>true</code> if the new connection is possible.
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/**
	 * sets the new Source our DestinationPoint.
	 */
	@Override
	public void execute() {
		final ECTransition transition = (ECTransition) request.getConnectionEditPart().getModel();
		cmd = new DeleteTransitionCommand(transition);
		dccc = new CreateTransitionCommand();
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			doReconnectTarget(transition);
		}
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			doReconnectSource(transition);
		}
		dccc.setConditionEvent(transition.getConditionEvent());
		dccc.setConditionExpression(transition.getConditionExpression());
		cmd.execute();
		dccc.execute();
	}

	/**
	 * Do reconnect source.
	 */
	protected void doReconnectSource(final ECTransition transition) {
		dccc.setSource((ECState) request.getTarget().getModel());
		dccc.setDestination(transition.getDestination());

		dccc.setDestinationLocation(dccc.getDestination().getPosition().toScreenPoint());
		dccc.setSourceLocation(request.getLocation());
	}

	/**
	 * Do reconnect target.
	 *
	 */
	protected void doReconnectTarget(final ECTransition transition) {
		dccc.setSource(transition.getSource());
		dccc.setDestination((ECState) request.getTarget().getModel());

		dccc.setDestinationLocation(request.getLocation());
		dccc.setSourceLocation(dccc.getSource().getPosition().toScreenPoint());
	}

	@Override
	public void redo() {
		cmd.redo();
		dccc.redo();

	}

	/**
	 * undo this command.
	 */
	@Override
	public void undo() {
		dccc.undo();
		cmd.undo();

	}

}
