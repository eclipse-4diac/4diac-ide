/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.policies;

import org.eclipse.fordiac.ide.fbtypeeditor.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.create.WithCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public class WithNodeEditPolicy extends GraphicalNodeEditPolicy implements
		EditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(
			final CreateConnectionRequest request) {
		if (request.getStartCommand() instanceof WithCreateCommand) {
			WithCreateCommand command = (WithCreateCommand) request
					.getStartCommand();
			if (command.isForwardCreation()) {
				if (((InterfaceEditPart) getHost()).isEvent()) {
					command.setVarDeclaration(null);
				} else {
					command
							.setVarDeclaration((VarDeclaration) ((InterfaceEditPart) getHost())
									.getCastedModel());
				}
			} else {
				if (((InterfaceEditPart) getHost()).isEvent()) {
					command.setEvent((Event) ((InterfaceEditPart) getHost())
							.getCastedModel());
				} else {
					command.setEvent(null);
				}
			}
			return command;
		}
		return null;

	}

	@Override
	protected Command getConnectionCreateCommand(
			final CreateConnectionRequest request) {

		WithCreateCommand cmd = new WithCreateCommand();
		if (getHost() instanceof InterfaceEditPart) {
			if (((InterfaceEditPart) getHost()).isEvent()) {
				cmd.setEvent((Event) ((InterfaceEditPart) getHost())
						.getCastedModel());
				cmd.setForwardCreation(true);
			} else {
				cmd
						.setVarDeclaration((VarDeclaration) ((InterfaceEditPart) getHost())
								.getCastedModel());
				cmd.setForwardCreation(false);
			}

		}
		request.setStartCommand(cmd);
		return cmd;
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return null;
	}
}
