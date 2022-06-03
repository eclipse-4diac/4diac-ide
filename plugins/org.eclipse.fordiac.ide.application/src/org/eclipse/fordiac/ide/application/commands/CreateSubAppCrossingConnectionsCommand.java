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
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.fordiac.ide.model.commands.create.AbstractConnectionCreateCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
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
	public void execute() {
		final IInterfaceElement left = buildPath(source, sourceNetworks, false);
		final IInterfaceElement right = buildPath(destination, destinationNetworks, true);
		createConnection(source, match, left, right);
	}

	// build left or right path as seen from the matching network
	private IInterfaceElement buildPath(final IInterfaceElement element, final List<FBNetwork> networks, final boolean isRightPath) {
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

	private IInterfaceElement createInterfaceElement(final boolean isRightPath, IInterfaceElement ie, final SubApp subapp) {
		final String pinName = createPinName(subapp, ie);
		final CreateSubAppInterfaceElementCommand pinCmd = new CreateSubAppInterfaceElementCommand(ie.getType(),
				pinName, subapp.getInterface(), isRightPath, -1);
		pinCmd.execute();
		commands.add(pinCmd);
		return subapp.getInterfaceElement(pinName);
	}
	
	private String createPinName(SubApp subapp, IInterfaceElement ie) {
		return subapp.getName() + "_" + ie.getName();
	}

	private void createConnection(final IInterfaceElement ie, final FBNetwork network, final IInterfaceElement connSource, final IInterfaceElement connDestination) {
		final AbstractConnectionCreateCommand connCmd = AbstractConnectionCreateCommand.createCommand(ie, network);
		connCmd.setSource(connSource);
		connCmd.setDestination(connDestination);
		connCmd.execute();
		commands.add(connCmd);
	}

	@Override
	public void undo() {
		// iterate backwards
		ListIterator<Command> iterator = commands.listIterator(commands.size());
		while (iterator.hasPrevious()) {
			iterator.previous().undo();
		}
	}

	@Override
	public void redo() {
		commands.forEach(Command::redo);
	}
	
}
