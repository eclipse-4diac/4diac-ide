/*******************************************************************************
 * Copyright (c) 2008, 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.application.policies;

import java.util.Optional;

import org.eclipse.fordiac.ide.application.commands.ConnectThroughCommand;
import org.eclipse.fordiac.ide.application.editparts.UnfoldedSubappContentEditPart;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteFBNetworkElementCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

/**
 * An EditPolicy which returns a command for deleting a FB from a fbnetwork.
 */
public class DeleteFBNElementEditPolicy extends org.eclipse.gef.editpolicies.ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.
	 * eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(final GroupRequest request) {
		if (getHost().getModel() instanceof final FBNetworkElement fbne) {
			if (getHost().getParent() instanceof UnfoldedSubappContentEditPart) {
				final Command rerouteCommand = createRerouteConnectionCommand(fbne);
				if (rerouteCommand != null) {
					return rerouteCommand.chain(new DeleteFBNetworkElementCommand(fbne));
				}
			}
			return new DeleteFBNetworkElementCommand(fbne);
		}
		return null;
	}

	private static Command createRerouteConnectionCommand(final FBNetworkElement fbne) {
		if (fbne.getInterface() != null && !fbne.getInterface().getEventInputs().isEmpty()
				&& !fbne.getInterface().getEventOutputs().isEmpty()) {
			final Optional<IInterfaceElement> inputEvent = fbne.getInterface().getEventInputs().stream()
					.filter(e -> !e.getInputConnections().isEmpty()).map(e -> ((IInterfaceElement) e)).findFirst();
			final Optional<IInterfaceElement> outputEvent = fbne.getInterface().getEventOutputs().stream()
					.filter(e -> !e.getOutputConnections().isEmpty()).map(e -> (IInterfaceElement) e).findFirst();

			if (outputEvent.isPresent() && inputEvent.isPresent()) {
				return new ConnectThroughCommand(inputEvent.get(), outputEvent.get());
			}
		}
		return null;
	}

}
