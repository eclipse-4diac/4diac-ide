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
package org.eclipse.fordiac.ide.application.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ToggleSubAppRepresentationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class UpdateConstraintCommand extends Command {

	private ChangeCommentCommand cccmd;
	private NewSubAppCommand subappcmd;
	private ToggleSubAppRepresentationCommand toggle;
	private ChangeNameCommand cncmd;
	private final String time;
	private final String offset;
	private final String state;
	private List<Event> eventPins = new ArrayList<>();

	public UpdateConstraintCommand(final List<Event> eventPins, final String time, final String offset,
			final String state) {
		this.eventPins = eventPins;
		this.time = time;
		this.offset = offset;
		this.state = state;
	}

	@Override
	public void execute() {
		if (cccmd == null) {

			final SubApp subapp = createNewSubapp();
			final StringBuilder comment = new StringBuilder();

			comment.append("ASSUMPTION " + eventPins.get(0).getName()); //$NON-NLS-1$
			comment.append(" occurs " + state + " ");   //$NON-NLS-1$//$NON-NLS-2$
			comment.append(time + "ms "); //$NON-NLS-1$
			if (offset != null) {
				comment.append("with " + offset + "ms" + " offset");   //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
			}
			cccmd = new ChangeCommentCommand(subapp, comment.toString());
			if (cccmd.canExecute()) {
				cccmd.execute();
			}

		}
	}

	@Override
	public void undo() {
		cccmd.undo();
		if (toggle != null) {
			toggle.undo();
		}
		cncmd.undo();
		subappcmd.undo();
		super.undo();
	}

	@Override
	public void redo() {
		subappcmd.redo();
		cncmd.redo();
		if (toggle != null) {
			toggle.redo();
		}
		cccmd.redo();
		super.redo();
	}

	private SubApp createNewSubapp() {
		final FBNetworkElement fb = eventPins.get(0).getFBNetworkElement();
		if (fb.isNestedInSubApp()) {
			final SubApp subapp = (SubApp) fb.eContainer().eContainer();
			if (!subapp.isUnfolded()) {
				toggle = new ToggleSubAppRepresentationCommand(subapp);
				if (toggle.canExecute()) {
					toggle.execute();
				}
			}
			return subapp;
		}
		final FBNetwork network = fb.getFbNetwork();
		final Position pos = fb.getPosition();
		final List<FBNetworkElement> list = new ArrayList<>();
		list.add(fb);
		subappcmd = new NewSubAppCommand(network, list, pos.getX(), pos.getY());
		if (subappcmd.canExecute()) {
			subappcmd.execute();
		}
		final SubApp subapp = subappcmd.getElement();
		cncmd = new ChangeNameCommand(subapp, "_CONTRACT_" + fb.getName()); //$NON-NLS-1$
		if (cncmd.canExecute()) {
			cncmd.execute();
		}
		toggle = new ToggleSubAppRepresentationCommand(subapp);
		if (toggle.canExecute()) {
			toggle.execute();
		}
		return subapp;
	}
}