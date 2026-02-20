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
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableMoveFB;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

/**
 * An EditPolicy which allows drawing Connections between VariableInterfaces.
 */
public class VariableNodeEditPolicy extends InterfaceElementEditPolicy {

	@Override
	protected AbstractConnectionCreateCommand createConnectionCreateCommand() {
		final IInterfaceElement pin = getHost().getModel();
		if ((pin instanceof VarDeclaration) && (!LinkConstraints.isWithConstraintOK(pin))) {
			// Elements which are not connected by a with construct are not allowed to be
			// connected
			return null;
		}

		if (getHost().getRoot() == null) {
			// we are in an intermediate configuration stage
			return null;
		}

		return (AbstractConnectionCreateCommand.isStructManipulatorDefPin(pin))
				? new StructDataConnectionCreateCommand(getParentNetwork())
				: new DataConnectionCreateCommand(getParentNetwork());
	}

	@Override
	protected AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget) {
		return new ReconnectDataConnectionCommand(connection, isSourceReconnect, newTarget, getParentNetwork());
	}

	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		if (!(request.getStartCommand() instanceof final AbstractConnectionCreateCommand connCreateCmd)) {
			return null;
		}

		final IInterfaceElement pin = getHost().getModel();

		if (connCreateCmd instanceof StructDataConnectionCreateCommand) {
			// if we drag from a struct manipulater but target is not a struct or
			// configureable F_MOVE pin use normal
			// data connection creation
			if (!AbstractConnectionCreateCommand.isSimpleStructPin(pin)
					&& !(pin.getBlockFBNetworkElement() instanceof ConfigurableMoveFB)) {
				final DataConnectionCreateCommand structCmd = new DataConnectionCreateCommand(
						connCreateCmd.getParent());
				structCmd.setSource(connCreateCmd.getSource());
				request.setStartCommand(structCmd);
			}
		} else if (AbstractConnectionCreateCommand.shouldStructDataConnCreationBeUsed(pin, connCreateCmd.getSource())) {
			final StructDataConnectionCreateCommand structCmd = new StructDataConnectionCreateCommand(
					connCreateCmd.getParent());
			structCmd.setSource(connCreateCmd.getSource());
			request.setStartCommand(structCmd);
		}
		return super.getConnectionCompleteCommand(request);
	}

}
