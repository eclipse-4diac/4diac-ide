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

import org.eclipse.fordiac.ide.model.Messages;
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

public class TargetLabelReconnectCommand extends Command {

	private IInterfaceElement source;
	private final IInterfaceElement oldSource;
	private final IInterfaceElement destination;

	private AggressiveDeleteConnectionCommand deleteCommand;
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
			deleteCommand = new AggressiveDeleteConnectionCommand(destination.getInputConnections().getFirst());
			deleteCommand.execute();
		}
		if (destination instanceof Event) {
			deleteCommand = new AggressiveDeleteConnectionCommand(getEventConnection());
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

	private Connection getEventConnection() {
		if (destination.getInputConnections().size() == 1) {
			return destination.getInputConnections().getFirst();
		}

		final var conns = destination.getInputConnections().stream().filter(this::traceConnection).toList();
		return conns.getFirst();
	}

	private boolean traceConnection(final Connection connection) {
		if (connection.getSource() == oldSource) {
			return true;
		}
		return connection.getSource().getInputConnections().stream().anyMatch(this::traceConnection);
	}

	public void setSource(final IInterfaceElement source) {
		this.source = source;
	}
}
