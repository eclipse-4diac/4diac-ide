/*******************************************************************************
 * Copyright (c) 2016, 2018 fortiss GmbH, Johannes Kepler Universtiy
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber
 *     - reworked getParentNetwork()
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public abstract class InterfaceElementEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		AbstractConnectionCreateCommand command = (AbstractConnectionCreateCommand) request.getStartCommand();
		command.setDestination(((InterfaceEditPart) getHost()).getModel());
		return command;
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return createReconnectCommand(request);
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return createReconnectCommand(request);
	}

	protected FBNetwork getParentNetwork() {
		EObject obj = (EObject) getHost().getModel();
		while ((obj != null) && !(obj instanceof FBNetworkElement || obj instanceof CompositeFBType)) {
			obj = obj.eContainer();
		}
		if (obj instanceof CompositeFBType) {
			return ((CompositeFBType) obj).getFBNetwork();
		}
		if (obj instanceof FBNetworkElement) {
			return ((FBNetworkElement) obj).getFbNetwork();
		}
		return null;
	}

	protected abstract Command createReconnectCommand(ReconnectRequest request);

}