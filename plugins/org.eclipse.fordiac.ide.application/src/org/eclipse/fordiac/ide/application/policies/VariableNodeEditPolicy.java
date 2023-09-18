/*******************************************************************************
 * Copyright (c) 2008, 2023 Profactor GmbH, fortiss GmbH,
 *                          Johannes Kepler University
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
 *   Prankur Agarwal - added handling for structs
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.change.ReconnectDataConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.DataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.StructDataConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

/** An EditPolicy which allows drawing Connections between VariableInterfaces. */
public class VariableNodeEditPolicy extends InterfaceElementEditPolicy {

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand() {
		final IInterfaceElement pin = (IInterfaceElement) getHost().getModel();
		if ((pin instanceof VarDeclaration) && (!LinkConstraints.isWithConstraintOK(pin))) {
			// Elements which are not connected by a with construct are not allowed to be connected
			return null;
		}

		final AbstractConnectionCreateCommand cmd = (AbstractConnectionCreateCommand.isStructManipulatorDefPin(pin))
				? new StructDataConnectionCreateCommand(getParentNetwork())
				: new DataConnectionCreateCommand(getParentNetwork());

		cmd.setSource(pin);
		return cmd;

	}

	@Override
	protected AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget) {
		return new ReconnectDataConnectionCommand(connection, isSourceReconnect, newTarget, getParentNetwork());
	}

	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		final AbstractConnectionCreateCommand command = (AbstractConnectionCreateCommand) request.getStartCommand();
		final IInterfaceElement pin = (IInterfaceElement) getHost().getModel();
		if (AbstractConnectionCreateCommand.isStructManipulatorDefPin(pin)
				&& !(command instanceof StructDataConnectionCreateCommand)) {
			// we are connecting to struct manipulator we need to switch to a StructDataConnectionCreatCommand
			final StructDataConnectionCreateCommand structCmd = new StructDataConnectionCreateCommand(
					command.getParent());
			structCmd.setSource(command.getSource());
			request.setStartCommand(structCmd);
		}

		return super.getConnectionCompleteCommand(request);
	}
}
