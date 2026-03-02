/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.policies.DeleteTargetInterfaceElementPolicy;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubAppType;
import org.eclipse.fordiac.ide.model.libraryElement.UntypedSubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

public class TargetLabelReconnectCommand extends Command {

	private IInterfaceElement source;
	private final IInterfaceElement oldSource;
	private final IInterfaceElement destination;

	private Command deleteCommand;
	private CreateSubAppCrossingConnectionsCommand createCommand;

	public TargetLabelReconnectCommand(final IInterfaceElement oldSource, final IInterfaceElement newSource,
			final IInterfaceElement destination) {
		this.oldSource = oldSource;
		this.destination = destination;
		this.source = newSource;
	}

	@Override
	public boolean canExecute() {
		if (source == null || destination == null) {
			return false;
		}

		// equal types for source and dest
		if (!source.getClass().isAssignableFrom(destination.getClass())
				&& !destination.getClass().isAssignableFrom(source.getClass())) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		// valid Input-Output combination
		if (source.isIsInput() && !supportsSwitchedPins(source)
				|| !destination.isIsInput() && !supportsSwitchedPins(destination)) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (source instanceof Event) {
			return true;
		}

		if (source instanceof VarDeclaration) {
			return dataConnectionChecks();
		}

		return false;
	}

	private static boolean supportsSwitchedPins(final IInterfaceElement pin) {
		if (pin.getBlockFBNetworkElement() instanceof final UntypedSubApp usa && usa.isUnfolded()) {
			return true;
		}
		return pin.getFBType() instanceof SubAppType;
	}

	private boolean dataConnectionChecks() {
		if (!(source.getType() instanceof StructuredType && destination.getType() instanceof StructuredType)
				&& !LinkConstraints.typeCheck(source, destination)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					null != source.getType() ? source.getType().getName() : FordiacMessages.NA,
					null != destination.getType() ? destination.getType().getName() : FordiacMessages.NA));
			return false;

		}

		if (destination instanceof final VarDeclaration targetData && targetData.isInOutVar()
				&& source instanceof final VarDeclaration sourceData && !sourceData.isInOutVar()) {
			// a non inout is connected to an inout
			ErrorMessenger.popUpErrorMessage(Messages.ConnectionValidator_OutputsCannotBeConnectedToVarInOuts);
			return false;
		}

		return LinkConstraints.isWithConstraintOK(source) && LinkConstraints.isWithConstraintOK(destination);
	}

	@Override
	public void execute() {
		if (destination instanceof VarDeclaration) {
			deleteCommand = DeleteTargetInterfaceElementPolicy.createOutputSideDeleteCommand(destination);
			deleteCommand.execute();
		}
		if (destination instanceof Event) {
			deleteCommand = getEventConnection();
			deleteCommand.execute();
		}
		createCommand = CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source,
				destination, false);
		createCommand.execute();
	}

	@Override
	public void redo() {
		deleteCommand.execute();
		createCommand.execute();
	}

	@Override
	public void undo() {
		createCommand.undo();
		deleteCommand.undo();
	}

	private Command getEventConnection() {
		final List<Connection> path = findPathToOldSource(destination);
		return deleteConnections(path, oldSource, destination);
	}

	private static CompoundCommand deleteConnections(final List<Connection> path, final IInterfaceElement source,
			final IInterfaceElement destination) {
		final List<Connection> remainingPath = new ArrayList<>(path);
		final CompoundCommand forwardCmd = new CompoundCommand();

		for (final Connection conn : path) {
			remainingPath.remove(conn);
			forwardCmd.add(new DeleteConnectionCommand(conn));
			if (conn.getSource() != source) {
				forwardCmd.add(new DeleteInterfaceCommand(conn.getSource()));
			}

			if (conn.getDestination().getInputConnections().size() > 1) {
				// delete until merge of connections
				break;
			}

			if (conn.getDestination().getOutputConnections().size() > 1) {
				// fan-out, switch to the deletion from destination
				return deleteConnectionsBackwards(path, remainingPath, destination, conn.getDestination());
			}
		}

		return forwardCmd;
	}

	private static CompoundCommand deleteConnectionsBackwards(final List<Connection> fullPath,
			final List<Connection> remainingPath, final IInterfaceElement originalDest,
			final IInterfaceElement forwardFanOutNode) {
		final CompoundCommand backwardCmd = new CompoundCommand();

		for (final Connection revConn : fullPath.reversed()) {
			remainingPath.remove(revConn);
			backwardCmd.add(new DeleteConnectionCommand(revConn));
			if (revConn.getDestination() != originalDest) {
				backwardCmd.add(new DeleteInterfaceCommand(revConn.getDestination()));
			}

			if (revConn.getSource().getOutputConnections().size() > 1) {
				break;
			}

			if (revConn.getSource().getInputConnections().size() > 1) {
				// fan-out, recursively handle middle section
				return deleteConnections(remainingPath, forwardFanOutNode, revConn.getSource());
			}
		}

		return backwardCmd;
	}

	private List<Connection> findPathToOldSource(final IInterfaceElement target) {
		for (final Connection conn : target.getInputConnections()) {
			if (conn.getSource() == oldSource) {
				final List<Connection> path = new ArrayList<>();
				path.add(conn);
				return path;
			}

			final List<Connection> path = findPathToOldSource(conn.getSource());
			if (!path.isEmpty()) {
				path.add(conn);
				return path;
			}
		}

		return new ArrayList<>();
	}

	public void setSource(final IInterfaceElement source) {
		this.source = source;
	}
}
