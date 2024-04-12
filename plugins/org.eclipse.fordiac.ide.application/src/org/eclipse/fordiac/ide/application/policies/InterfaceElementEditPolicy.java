/*******************************************************************************
 * Copyright (c) 2016, 2024 fortiss GmbH, Johannes Kepler University Linz,
 *                          Primetals Technologies Germany GmbH,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Daniel Lindhuber, Bianca Wiesmayr
 *               - connections across subapp borders
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.policies;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.application.commands.BorderCrossingReconnectCommand;
import org.eclipse.fordiac.ide.application.commands.CreateSubAppCrossingConnectionsCommand;
import org.eclipse.fordiac.ide.gef.FixedAnchor;
import org.eclipse.fordiac.ide.gef.editparts.InterfaceEditPart;
import org.eclipse.fordiac.ide.model.commands.change.AbstractReconnectConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkElementHelper;
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

public abstract class InterfaceElementEditPolicy extends GraphicalNodeEditPolicy {

	@Override
	protected final Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		final AbstractConnectionCreateCommand command = createConnectionCreateCommand();
		request.setStartCommand(command);
		return command;
	}

	protected abstract AbstractConnectionCreateCommand createConnectionCreateCommand();

	@Override
	protected Command getConnectionCompleteCommand(final CreateConnectionRequest request) {
		final AbstractConnectionCreateCommand command = (AbstractConnectionCreateCommand) request.getStartCommand();
		command.setDestination(((InterfaceEditPart) getHost()).getModel());

		final FBNetwork newParent = checkConnectionParent(command.getSource(), command.getDestination(),
				command.getParent());
		if (newParent != null) {
			command.setParent(newParent);
			return command;
		}
		// if we are here it is not a direct connection try border crossing command
		return CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(command.getSource(),
				command.getDestination());
	}

	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		return createReconnectCommand(request, false);
	}

	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
		return createReconnectCommand(request, true);
	}

	private Command createReconnectCommand(final ReconnectRequest request, final boolean isSourceReconnect) {
		final var conn = (Connection) request.getConnectionEditPart().getModel();
		final var sourcePin = conn.getSource();
		final var targetPin = conn.getDestination();
		final var newPin = (IInterfaceElement) request.getTarget().getModel();

		// border crossing source reconnect
		if (isSourceReconnect && isBorderCrossing(sourcePin, newPin)) {
			return new BorderCrossingReconnectCommand(newPin, targetPin, conn, true);
		}

		// border crossing destination reconnect
		if (!isSourceReconnect && isBorderCrossing(targetPin, newPin)) {
			return new BorderCrossingReconnectCommand(sourcePin, newPin, conn, false);
		}

		// local reconnect
		final AbstractReconnectConnectionCommand cmd = createReconnectCommand(conn, isSourceReconnect,
				getRequestTarget(request));
		final FBNetwork newParent = checkConnectionParent(cmd.getNewSource(), cmd.getNewDestination(), cmd.getParent());
		if (newParent != null) {
			cmd.setParent(newParent);
			return cmd;
		}
		return null;
	}

	private static boolean isBorderCrossing(final IInterfaceElement ie1, final IInterfaceElement ie2) {
		return getContainer(ie1.getFBNetworkElement()) != getContainer(ie2.getFBNetworkElement());
	}

	private static EObject getContainer(final FBNetworkElement elem) {
		if (elem instanceof final SubApp subapp) {
			return subapp;
		}
		return FBNetworkElementHelper.getContainerSubappOfFB((FB) elem);
	}

	protected FBNetwork getParentNetwork() {
		return getHost().getRoot().getAdapter(FBNetwork.class);
	}

	protected abstract AbstractReconnectConnectionCommand createReconnectCommand(final Connection connection,
			final boolean isSourceReconnect, final IInterfaceElement newTarget);

	private static FBNetwork checkConnectionParent(final IInterfaceElement source, final IInterfaceElement destination,
			final FBNetwork parent) {
		final FBNetwork srcParent = getFBNetwork4Pin(source);
		final FBNetwork dstParent = getFBNetwork4Pin(destination);

		if ((srcParent != null) && (dstParent != null)) {
			if (srcParent == dstParent) {
				return checkParentInSameNetwork(source, destination, parent, srcParent);
			}
			if ((source.getFBNetworkElement() instanceof final SubApp subapp)
					&& (subapp.getSubAppNetwork() == dstParent)) {
				// we have a connection from a subapp pin to an internal FB
				return dstParent;
			}
			if ((destination.getFBNetworkElement() instanceof final SubApp subapp)
					&& (subapp.getSubAppNetwork() == srcParent)) {
				// we have a connection from a subapp pin to an internal FB
				return srcParent;
			}
		}
		return null;
	}

	private static FBNetwork checkParentInSameNetwork(final IInterfaceElement src, final IInterfaceElement dest,
			final FBNetwork parent, final FBNetwork srcParent) {
		if (src.getFBNetworkElement() instanceof final SubApp srcSubApp
				&& dest.getFBNetworkElement() instanceof final SubApp destSubApp && srcSubApp == destSubApp
				&& !srcSubApp.isTyped() && srcSubApp.getSubAppNetwork() != parent) {
			// we have a connection request for a pin to pin untyped expanded subapp
			// connection
			if (src.isIsInput()) {
				// for input to input connections use the inner network
				return srcSubApp.getSubAppNetwork();
			}
			// for output to input connections use the outer network
			return srcParent;
		}

		if (srcParent == parent) {
			return parent;
		}
		if ((src.getFBNetworkElement() instanceof final SubApp subApp) && (subApp.getSubAppNetwork() == parent)) {
			// we have a subapp pin to pin connection inside of a subapp
			return parent;
		}
		// we have a connection in an unfolded subapp
		return srcParent;
	}

	private static FBNetwork getFBNetwork4Pin(final IInterfaceElement pin) {
		if (pin != null) {
			if (pin.getFBNetworkElement() != null) {
				return pin.getFBNetworkElement().getFbNetwork();
			}
			if (pin.eContainer().eContainer() instanceof final CompositeFBType cfbType) {
				return cfbType.getFBNetwork();
			}
		}
		return null;
	}

	private static IInterfaceElement getRequestTarget(final ReconnectRequest request) {
		final EditPart target = request.getTarget();
		if (target.getModel() instanceof final IInterfaceElement ie) {
			return ie;
		}
		return null;
	}

	@Override
	protected ConnectionAnchor getSourceConnectionAnchor(final CreateConnectionRequest request) {
		if (getHost().getModel() instanceof final IInterfaceElement ie && ie.isIsInput()
				&& ie.getFBNetworkElement() instanceof final SubApp subapp && subapp.isUnfolded()) {
			// we are unfolded and this is an internal connection
			return new FixedAnchor(getHostFigure(), false);
		}
		return super.getSourceConnectionAnchor(request);
	}
}
