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
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

	public BorderCrossingReconnectCommand(final IInterfaceElement source, final IInterfaceElement target,
			final Connection connection, final boolean isSourceReconnect) {
		this.source = source;
		this.target = target;
		this.connection = connection;
		this.isSourceReconnect = isSourceReconnect;

		init();
	}

	private void init() {
		final var sinks = new ArrayList<IInterfaceElement>();
		final var sources = new ArrayList<IInterfaceElement>();
		collectSinksRec(connection, this, sinks);
		collectSourcesRec(connection, this, sources);
		sources.add(source);

		if (isSourceReconnect) {
			for (final var sink : sinks) {
				add(CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source, sink));
			}
		} else {
			for (final var source : sources) {
				add(CreateSubAppCrossingConnectionsCommand.createProcessBorderCrossingConnection(source, target));
			}
		}
	}

	private static void collectSinksRec(final Connection conn, final CompoundCommand cmd,
			final List<IInterfaceElement> sinks) {
		cmd.add(new DeleteConnectionCommand(conn));
		if (isEpxandedSubapp(conn.getDestination())) {
			final var destination = conn.getDestination();
			if (destination.getInputConnections().size() == 1) {
				for (final var outConn : destination.getOutputConnections()) {
					collectSinksRec(outConn, cmd, sinks);
				}
				cmd.add(new DeleteInterfaceCommand(destination));
			}
		} else {
			sinks.add(conn.getDestination());
		}
	}

	private static void collectSourcesRec(final Connection conn, final CompoundCommand cmd,
			final List<IInterfaceElement> sources) {
		if (isEpxandedSubapp(conn.getSource())) {
			final var source = conn.getSource();
			if (source.getOutputConnections().size() == 1) {
				for (final var outConn : source.getInputConnections()) {
					collectSourcesRec(outConn, cmd, sources);
				}
				cmd.add(new DeleteInterfaceCommand(source));
			}
		} else {
			sources.add(conn.getSource());
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
			if (!isSourceReconnect) {
				return super.canExecute();
			}

			if (!(source.getType() instanceof StructuredType && target.getType() instanceof StructuredType)
					&& (!LinkConstraints.typeCheck(source, target))) {
				ErrorMessenger
						.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
								(null != source.getType()) ? source.getType().getName() : FordiacMessages.NA,
								(null != target.getType()) ? target.getType().getName() : FordiacMessages.NA));
				return false;

			}

			return LinkConstraints.isWithConstraintOK(source) && LinkConstraints.isWithConstraintOK(target);
		}

		if (source instanceof AdapterDeclaration) {
			if (isSourceReconnect) {
				if (!source.getOutputConnections().isEmpty()) {
					ErrorMessenger.popUpErrorMessage(MessageFormat.format(
							Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection, source.getName()));
					return false;
				}
			} else if (!target.getInputConnections().isEmpty()) {
				ErrorMessenger.popUpErrorMessage(MessageFormat
						.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, target.getName()));
				return false;
			}

			if (!LinkConstraints.adapaterTypeCompatibilityCheck(source, target)) {
				ErrorMessenger
						.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
								(null != source.getType()) ? source.getType().getName() : FordiacMessages.ND,
								(null != target.getType()) ? target.getType().getName() : FordiacMessages.ND));
				return false;
			}

			return true;
		}

		return false;
	}

}
