/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
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
 *   Bianca Wiesmayr
 *     - implemented special cases, handle existing pins gracefully
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.fordiac.ide.model.commands.Messages;
import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.commands.create.LinkConstraints;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.errormessages.ErrorMessenger;
import org.eclipse.gef.commands.Command;

public class CreateSubAppCrossingConnectionsCommand extends Command {

	final IInterfaceElement source;
	final IInterfaceElement destination;
	final FBNetwork match;
	final List<FBNetwork> sourceNetworks;
	final List<FBNetwork> destinationNetworks;

	List<Command> commands = new ArrayList<>();

	public CreateSubAppCrossingConnectionsCommand(final IInterfaceElement source, final IInterfaceElement destination,
			final List<FBNetwork> sourceNetworks, final List<FBNetwork> destinationNetworks, final FBNetwork match) {
		this.source = source;
		this.destination = destination;
		this.sourceNetworks = sourceNetworks;
		this.destinationNetworks = destinationNetworks;
		this.match = match;
	}

	@Override
	public boolean canExecute() {
		// as not all checks of the createconnection command are valid we need to do our own checks
		// first the generic checks
		if (source == null || destination == null) {
			return false;
		}

		// equal types for source and dest
		if (!source.getClass().equals(destination.getClass())) {
			ErrorMessenger.popUpErrorMessage(Messages.ConnectingIncompatibleInterfaceTypes);
			return false;
		}

		// source and dest check
		if (!LinkConstraints.isValidConnSource(source, sourceNetworks.get(0))
				|| !LinkConstraints.isValidConnDestination(destination, destinationNetworks.get(0))) {
			ErrorMessenger.popUpErrorMessage(Messages.LinkConstraints_STATUSMessage_IN_IN_OUT_OUT_notAllowed);
			return false;
		}

		if (source instanceof Event) {
			// the only event specific connection we currently have is duplicate connection which makes no sense here so
			// we can return true
			return true;
		}

		if (source instanceof VarDeclaration) {
			return dataConnectionChecks();
		}

		if (source instanceof AdapterDeclaration) {
			return adapterConnectionChecks();
		}

		return false;
	}


	@Override
	public void execute() {
		final IInterfaceElement left = buildPath(source, sourceNetworks, false);
		final IInterfaceElement right = buildPath(destination, destinationNetworks, true);
		createConnection(source, match, left, right);
	}

	// build left or right path as seen from the matching network
	private IInterfaceElement buildPath(final IInterfaceElement element, final List<FBNetwork> networks,
			final boolean isRightPath) {
		IInterfaceElement ie = element;
		FBNetwork network = networks.get(0);
		int i = 0;

		while (network != match) {
			final SubApp subapp = (SubApp) network.eContainer();
			final IInterfaceElement createdPin = createInterfaceElement(isRightPath, ie, subapp);

			if (isRightPath) {
				createConnection(ie, network, createdPin, ie);
			} else {
				createConnection(ie, network, ie, createdPin);
			}

			ie = createdPin;
			i++;
			network = networks.get(i);
		}
		return ie;
	}

	private IInterfaceElement createInterfaceElement(final boolean isRightPath, final IInterfaceElement ie,
			final SubApp subapp) {

		if (emptyPinAlreadyExists(subapp, ie, isRightPath)) {
			return subapp.getInterfaceElement(ie.getName());
		}

		final CreateSubAppInterfaceElementCommand pinCmd = new CreateSubAppInterfaceElementCommand(ie.getType(),
				source.getName(), subapp.getInterface(), isRightPath, -1);
		pinCmd.execute();
		commands.add(pinCmd);
		return pinCmd.getCreatedElement();
	}

	private boolean emptyPinAlreadyExists(final SubApp subapp, final IInterfaceElement ie, final boolean isRightPath) {
		final IInterfaceElement pin = subapp.getInterfaceElement(ie.getName());
		if ((pin != null) && pin.getInputConnections().isEmpty() && pin.getOutputConnections().isEmpty()
				&& compatibleTypes(pin)) {
			return (pin.isIsInput() && isRightPath) || (!pin.isIsInput() && !isRightPath);
		}
		return false;
	}

	private boolean compatibleTypes(final IInterfaceElement pin) {
		if (pin.getType() == null) {
			return false;
		}
		return LinkConstraints.typeCheck(source, pin);
	}


	private void createConnection(final IInterfaceElement ie, final FBNetwork network,
			final IInterfaceElement connSource, final IInterfaceElement connDestination) {
		final AbstractConnectionCreateCommand connCmd = AbstractConnectionCreateCommand.createCommand(ie, network);
		connCmd.setSource(connSource);
		connCmd.setDestination(connDestination);
		if (connCmd.canExecute()) {
			connCmd.execute();
			commands.add(connCmd);
		}
	}

	@Override
	public void undo() {
		// iterate backwards
		final ListIterator<Command> iterator = commands.listIterator(commands.size());
		while (iterator.hasPrevious()) {
			iterator.previous().undo();
		}
	}

	@Override
	public void redo() {
		commands.forEach(Command::redo);
	}

	private boolean dataConnectionChecks() {
		if (!LinkConstraints.hasAlreadyInputConnectionsCheck(source, destination, null)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, destination.getName()));
			return false;
		}

		if (!LinkConstraints.typeCheck(source, destination)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.NA,
							(null != destination.getType()) ? destination.getType().getName() : FordiacMessages.NA));
			return false;
		}

		return LinkConstraints.isWithConstraintOK(source) && LinkConstraints.isWithConstraintOK(destination);
	}

	private boolean adapterConnectionChecks() {
		if (!LinkConstraints.hasAlreadyInputConnectionsCheck(source, destination, null)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyInputConnection, destination.getName()));
			return false;
		}

		if (LinkConstraints.hasAlreadyOutputConnectionsCheck(source, null)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat
					.format(Messages.LinkConstraints_STATUSMessage_hasAlreadyOutputConnection, source.getName()));
			return false;
		}

		if (!LinkConstraints.adapaterTypeCompatibilityCheck(source, destination)) {
			ErrorMessenger.popUpErrorMessage(MessageFormat.format(Messages.LinkConstraints_STATUSMessage_NotCompatible,
					(null != source.getType()) ? source.getType().getName() : FordiacMessages.ND,
							(null != destination.getType()) ? destination.getType().getName() : FordiacMessages.ND));
			return false;
		}

		return true;
	}

}
