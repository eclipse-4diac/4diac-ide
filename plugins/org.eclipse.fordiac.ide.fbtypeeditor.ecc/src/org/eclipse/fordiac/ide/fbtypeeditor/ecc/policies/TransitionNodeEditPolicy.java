/*******************************************************************************
 * Copyright (c) 2008, 2011, 2013 Profactor GmbH, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.CreateTransitionCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.ecc.commands.ReconnectTransitionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

/**
 * The Class TransitionNodeEditPolicy.
 */
public class TransitionNodeEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		if (request.getStartCommand() instanceof CreateTransitionCommand) {
			final CreateTransitionCommand command = (CreateTransitionCommand) request.getStartCommand();
			if (getHost().getModel() instanceof ECState) {
				command.setDestination((ECState) getHost().getModel());
				return command;
			}
		}
		return null;

	}

	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {

		final CreateTransitionCommand cmd = new CreateTransitionCommand();
		if (getHost().getModel() instanceof ECState) {
			cmd.setSource((ECState) getHost().getModel());
		}
		request.setStartCommand(cmd);
		return cmd;
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		final ECTransition transition = (ECTransition) request.getConnectionEditPart().getModel();
		// check if the source has changed
		return (transition.getSource().equals(request.getTarget().getModel())) ? null
				: new ReconnectTransitionCommand(request);
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		final ECTransition transition = (ECTransition) request.getConnectionEditPart().getModel();
		// check if the source has changed
		return (transition.getDestination().equals(request.getTarget().getModel())) ? null
				: new ReconnectTransitionCommand(request);
	}

}
