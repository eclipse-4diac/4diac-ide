/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
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
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class UpdateContractCommand extends Command {

	private static final String ATTRIBUTE_NAME = InstanceContract.CONTRACT_ATTRIBUTE_NAME;
	private static final int NEW_SUBAPP_WIDTH = 900;
	private static final int NEW_SUBAPP_HEIGHT = 400;

	private final FBNetworkElement fbNetworkElement;
	private NewSubAppCommand subappcmd;
	private ChangeContractCommand ccc;
	private ToggleSubAppRepresentationCommand toggle;
	private Command mappingcmd;
	private String contract;
	private SubApp subapp;

	public UpdateContractCommand(final FBNetworkElement fbNetworkElement, final String contract) {
		this.contract = contract;
		this.fbNetworkElement = fbNetworkElement;
	}

	@Override
	public void execute() {
		subapp = createNewSubapp();

		final String oldContract = subapp.getAttributeValue(ATTRIBUTE_NAME);

		if (oldContract != null && !oldContract.isEmpty()) {
			final StringBuilder sb = new StringBuilder(oldContract);
			sb.append(System.lineSeparator());
			sb.append(contract);
			contract = sb.toString();
		}

		ccc = new ChangeContractCommand(subapp, contract);
		if (ccc.canExecute()) {
			ccc.execute();
		}
	}

	@Override
	public void undo() {
		ccc.undo();
		if (mappingcmd != null) {
			mappingcmd.undo();
		}
		if (toggle != null) {
			toggle.undo();
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
		if (toggle != null) {
			toggle.redo();
		}
		if (mappingcmd != null) {
			mappingcmd.redo();
		}
		ccc.redo();
	}

	private SubApp createNewSubapp() {
		final var resource = fbNetworkElement.isMapped() ? fbNetworkElement.getResource() : null;
		SubApp subapp = null;

		if (fbNetworkElement instanceof final SubApp s) {
			subapp = s;
		} else if (fbNetworkElement.isNestedInSubApp()) {
			subapp = (SubApp) fbNetworkElement.eContainer().eContainer();
		}
		if (subapp != null) {
			if (!subapp.isUnfolded()) {
				toggle = new ToggleSubAppRepresentationCommand(subapp);
				if (toggle.canExecute()) {
					toggle.execute();
				}
			}
			return subapp;
		}

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
		toggle = new ToggleSubAppRepresentationCommand(subapp);
		if (toggle.canExecute()) {
			toggle.execute();
		}

		if (resource != null) {
			mappingcmd = MapToCommand.createMapToCommand(subapp, resource);
			if (mappingcmd.canExecute()) {
				mappingcmd.execute();
			}
		}

		return subapp;
	}
}
