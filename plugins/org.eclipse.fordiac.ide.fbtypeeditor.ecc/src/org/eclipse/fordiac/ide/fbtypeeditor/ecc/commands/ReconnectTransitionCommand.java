/*******************************************************************************
 * Copyright (c) 2011, 2013, 2016, 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2019 Johannes Keppler University Linz
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - removed editor check from canUndo 
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands;

import org.eclipse.draw2d.geometry.Point;
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
		super("Reconnect Transition");
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
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_TARGET)) {
			doReconnectTarget();
		}
		if (request.getType().equals(RequestConstants.REQ_RECONNECT_SOURCE)) {
			doReconnectSource();
		}

	}

	/**
	 * Do reconnect source.
	 */
	protected void doReconnectSource() {
		ECTransition transition = (ECTransition) request.getConnectionEditPart().getModel();
		cmd = new DeleteTransitionCommand(transition);
		dccc = new CreateTransitionCommand();
		dccc.setSource((ECState) request.getTarget().getModel());
		dccc.setDestination((ECState) request.getConnectionEditPart().getTarget().getModel());

		dccc.setDestinationLocation(new Point(dccc.getDestination().getX(), dccc.getDestination().getY()));
		dccc.setSourceLocation(request.getLocation());

		dccc.setConditionEvent(transition.getConditionEvent());
		dccc.setConditionExpression(transition.getConditionExpression());
		cmd.execute();
		dccc.execute();
	}

	/**
	 * Do reconnect target.
	 */
	protected void doReconnectTarget() {
		ECTransition transition = (ECTransition) request.getConnectionEditPart().getModel();
		cmd = new DeleteTransitionCommand(transition);
		dccc = new CreateTransitionCommand();
		dccc.setSource((ECState) request.getConnectionEditPart().getSource().getModel());
		dccc.setDestination((ECState) request.getTarget().getModel());
		dccc.setDestinationLocation(request.getLocation());
		dccc.setSourceLocation(new Point(dccc.getSource().getX(), dccc.getSource().getY()));
		dccc.setConditionEvent(transition.getConditionEvent());
		dccc.setConditionExpression(transition.getConditionExpression());

		cmd.execute();
		dccc.execute();

	}

	/**
	 * Redo.
	 * 
	 * @see ReconnectTransitionCommand#execute()
	 */
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
