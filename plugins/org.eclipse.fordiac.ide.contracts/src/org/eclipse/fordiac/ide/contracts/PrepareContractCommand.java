/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Kepler Universität Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *   Felix Schmid
 *     - add connections from selected pins to SubApp
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.application.commands.NewSubAppCommand;
import org.eclipse.fordiac.ide.application.editparts.InstanceContract;
import org.eclipse.fordiac.ide.model.CoordinateConverter;
import org.eclipse.fordiac.ide.model.commands.change.ChangeContractCommand;
import org.eclipse.fordiac.ide.model.commands.change.MapToCommand;
import org.eclipse.fordiac.ide.model.commands.change.ToggleSubAppRepresentationCommand;
import org.eclipse.fordiac.ide.model.commands.create.CreateConnectionAtSubappInterfaceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class PrepareContractCommand extends Command {

	private static final String ATTRIBUTE_NAME = InstanceContract.CONTRACT_ATTRIBUTE_NAME;
	private static final int NEW_SUBAPP_WIDTH = 900;
	private static final int NEW_SUBAPP_HEIGHT = 400;

	private final FBNetworkElement fbNetworkElement;
	private final List<Event> iPins;
	private final List<Event> oPins;
	private final String contract;

	private NewSubAppCommand subappcmd;
	private ChangeContractCommand contractcmd;
	private ToggleSubAppRepresentationCommand togglecmd;
	private Command mappingcmd;
	private List<Command> connectioncmds;

	public PrepareContractCommand(final FBNetworkElement fbNetworkElement, final List<Event> iPins,
			final List<Event> oPins, final String contract) {
		this.fbNetworkElement = fbNetworkElement;
		this.iPins = iPins;
		this.oPins = oPins;
		this.contract = contract;
	}

	@Override
	public void execute() {
		final SubApp subapp = createNewSubapp();

		final String oldContract = subapp.getAttributeValue(ATTRIBUTE_NAME);
		String ctrct = contract;

		if (oldContract != null && !oldContract.isEmpty()) {
			final StringBuilder sb = new StringBuilder(oldContract);
			sb.append(System.lineSeparator());
			sb.append(ctrct);
			ctrct = sb.toString();
		}

		contractcmd = new ChangeContractCommand(subapp, ctrct);
		if (contractcmd.canExecute()) {
			contractcmd.execute();
		}
	}

	@Override
	public void undo() {
		contractcmd.undo();
		if (mappingcmd != null) {
			mappingcmd.undo();
		}
		for (final var cmd : connectioncmds) {
			cmd.undo();
		}
		if (togglecmd != null) {
			togglecmd.undo();
		}
		if (subappcmd != null) {
			subappcmd.undo();
		}
	}

	@Override
	public void redo() {
		if (subappcmd != null) {
			subappcmd.redo();
		}
		if (togglecmd != null) {
			togglecmd.redo();
		}
		for (final var cmd : connectioncmds) {
			cmd.redo();
		}
		if (mappingcmd != null) {
			mappingcmd.redo();
		}
		contractcmd.redo();
	}

	private SubApp createNewSubapp() {
		final var resource = fbNetworkElement.isMapped() ? fbNetworkElement.getResource() : null;
		SubApp subapp = null;

		if (fbNetworkElement instanceof final SubApp s) {
			subapp = s;
		} else if (fbNetworkElement.isNestedInSubApp()) {
			subapp = (SubApp) fbNetworkElement.eContainer().eContainer();
			createConnectionsToSubApp(subapp);
		} else {
			final FBNetwork network = fbNetworkElement.getFbNetwork();
			final Position pos = fbNetworkElement.getPosition();
			final List<FBNetworkElement> list = new ArrayList<>();
			list.add(fbNetworkElement);
			subappcmd = new NewSubAppCommand(network, list, pos);
			if (subappcmd.canExecute()) {
				subappcmd.execute();
			}

			subapp = subappcmd.getElement();
			subapp.setWidth(CoordinateConverter.INSTANCE.screenToIEC61499(NEW_SUBAPP_WIDTH));
			subapp.setHeight(CoordinateConverter.INSTANCE.screenToIEC61499(NEW_SUBAPP_HEIGHT));
			createConnectionsToSubApp(subapp);
		}

		if (!subapp.isUnfolded()) {
			togglecmd = new ToggleSubAppRepresentationCommand(subapp);
			if (togglecmd.canExecute()) {
				togglecmd.execute();
			}
		}

		if (resource != null) {
			mappingcmd = MapToCommand.createMapToCommand(subapp, resource);
			if (mappingcmd.canExecute()) {
				mappingcmd.execute();
			}
		}

		return subapp;
	}

	private void createConnectionsToSubApp(final SubApp subapp) {
		connectioncmds = new ArrayList<>();
		createConnectionsHelper(iPins, subapp);
		createConnectionsHelper(oPins, subapp);
	}

	private void createConnectionsHelper(final List<Event> events, final SubApp subapp) {
		for (final var event : events) {
			if (subapp.getInterface().getEvent(event.getName()) == null) {
				final var ccc = new CreateConnectionAtSubappInterfaceCommand(event, subapp);
				if (ccc.canExecute()) {
					ccc.execute();
				}
				connectioncmds.add(ccc);
			}
		}
	}
}
