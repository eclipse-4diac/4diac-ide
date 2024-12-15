/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Daniel Lindhuber
 *               - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added improved pin delete checks, especially need for
 *                 VAR_IN_OUTs, stop deleting connections for fan out source pins
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteConnectionCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInterfaceCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Connection;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.validation.LinkConstraints;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.CompoundCommand;

public class BorderCrossingReconnectCommand extends CompoundCommand {
	final IInterfaceElement source;
	final IInterfaceElement target;
	final Connection connection;
	final boolean isSourceReconnect;
	final Set<IInterfaceElement> affectedPins = new HashSet<>();

	public BorderCrossingReconnectCommand(final IInterfaceElement source, final IInterfaceElement target,
			final Connection connection, final boolean isSourceReconnect) {
		this.source = source;
		this.target = target;
		this.connection = connection;
		this.isSourceReconnect = isSourceReconnect;
		init();
	}

	@Override
	public void execute() {
		super.execute();

		affectedPins.stream().filter(BorderCrossingReconnectCommand::hasNoConnections).forEach(inOutPin -> {
			final DeleteInterfaceCommand cmd = new DeleteInterfaceCommand(inOutPin);
			cmd.execute();
			add(cmd); // add it for undo redo processing
		});

	}

	private static boolean hasNoConnections(final IInterfaceElement pin) {
		if (pin instanceof final VarDeclaration varDecl && varDecl.isInOutVar()) {
			return inOutHasNoConnections(varDecl);
		}
		return pin.getInputConnections().isEmpty() && pin.getOutputConnections().isEmpty();
	}

	private static boolean inOutHasNoConnections(final VarDeclaration inOutPin) {
		final VarDeclaration opposite = inOutPin.getInOutVarOpposite();
		return inOutPin.getInputConnections().isEmpty() && inOutPin.getOutputConnections().isEmpty()
				&& opposite.getInputConnections().isEmpty() && opposite.getOutputConnections().isEmpty();
	}

	private void init() {
		if (isSourceReconnect) {
			final var sinks = new ArrayList<IInterfaceElement>();
			collectSinksRec(connection, sinks);
			for (final var sink : sinks) {
				add(CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source, sink));
			}
		} else {
			final var sources = new ArrayList<IInterfaceElement>();
			collectSourcesRec(connection, sources, true);
			for (final var src : sources) {
				add(CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(src, target));
			}
		}
	}

	private void collectSinksRec(final Connection conn, final List<IInterfaceElement> sinks) {
		add(new DeleteConnectionCommand(conn));
		if (!conn.getDestination().getOutputConnections().isEmpty()) {
			final var destination = conn.getDestination();
			for (final var outConn : destination.getOutputConnections()) {
				collectSinksRec(outConn, sinks);
			}
			addPin(destination);
		} else {
			sinks.add(conn.getDestination());
		}
	}

	private void addPin(final IInterfaceElement pin) {
		if (pin instanceof final VarDeclaration varDecl && varDecl.isInOutVar()) {
			affectedPins.add((varDecl.isIsInput()) ? varDecl : varDecl.getInOutVarOpposite());
		} else {
			affectedPins.add(pin);
		}
	}

	private void collectSourcesRec(final Connection conn, final List<IInterfaceElement> sources, boolean deleteCon) {
		final var src = conn.getSource();
		if (deleteCon) {
			add(new DeleteConnectionCommand(conn));
			// if source has several outgoing connections we must not delete any further
			// connections
			deleteCon = src.getOutputConnections().size() == 1;
		}

		if (!src.getInputConnections().isEmpty()) {
			for (final var outConn : src.getInputConnections()) {
				collectSourcesRec(outConn, sources, deleteCon);
			}
			addPin(src);
		} else {
			sources.add(src);
		}
	}

	private static boolean isEpxandedSubapp(final IInterfaceElement ie) {
		return ie.getFBNetworkElement() instanceof final SubApp subapp && subapp.isUnfolded();
	}

	@Override
	public boolean canExecute() {
		// equal types
		if (!source.getClass().isAssignableFrom(target.getClass())
				&& !target.getClass().isAssignableFrom(source.getClass())) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		// source and dest check
		if (isSourceReconnect) {
			if (!(isEpxandedSubapp(source) || !source.isIsInput())) {
				ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
				return false;
			}
		} else if (!(isEpxandedSubapp(target) || target.isIsInput())) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (source instanceof Event) {
			return true;
		}

		if (source instanceof VarDeclaration) {
			return canExecuteDataCon();
		}

		if (source instanceof AdapterDeclaration) {
			return canExecuteAdapterCon();
		}

		return false;
	}

	private boolean canExecuteDataCon() {
		if (!isSourceReconnect) {
			return super.canExecute();
		}

		if (!(source.getType() instanceof StructuredType && target.getType() instanceof StructuredType)
				&& (!LinkConstraints.typeCheck(source, target))) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.NA,
					(null != target.getType()) ? target.getType().getName() : FordiacMessages.NA));
			return false;
		}

		return LinkConstraints.isWithConstraintOK(source) && LinkConstraints.isWithConstraintOK(target);
	}

	private boolean canExecuteAdapterCon() {
		if (isSourceReconnect) {
			if (!source.getOutputConnections().isEmpty()) {
				ErrorMessenger.popUpErrorMessage(MessageFormat
						.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection, source.getName()));
				return false;
			}
		} else if (!target.getInputConnections().isEmpty()) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, target.getName()));
			return false;
		}

		if (!LinkConstraints.adapaterTypeCompatibilityCheck(source, target)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.ND,
					(null != target.getType()) ? target.getType().getName() : FordiacMessages.ND));
			return false;
		}

		return true;
	}

}
