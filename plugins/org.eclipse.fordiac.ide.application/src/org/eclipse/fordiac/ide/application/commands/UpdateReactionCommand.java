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

import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ToggleSubAppRepresentationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.gef.commands.Command;

public class UpdateReactionCommand extends Command {
	private static final int PIN_TO = 1;
	private static final int PIN_FROM = 0;
	private ChangeCommentCommand cccmd;
	private NewSubAppCommand subappcmd;
	private ToggleSubAppRepresentationCommand toggle;
	private ChangeNameCommand cncmd;
	private final String time;
	private List<Event> eventPins = new ArrayList<>();
	private final Event event;

	public UpdateReactionCommand(final List<Event> eventPins, final String time) {
		this.eventPins = eventPins;
		this.time = time;
		this.event = null;

	}

	public UpdateReactionCommand(final Event event, final List<Event> outputEvents, final String time) {
		this.eventPins = outputEvents;
		this.time = time;
		this.event = event;
	}

	@Override
	public void execute() {
		if (cccmd == null) {

			final SubApp subapp = createNewSubapp();
			final StringBuilder comment = new StringBuilder();
			final String oldcomment = subapp.getComment();
			if (oldcomment.indexOf("ASSUMPTION ") == 0) { //$NON-NLS-1$
				comment.append(oldcomment + "\n"); //$NON-NLS-1$
			}
			if (event == null) {
				comment.append("GUARANTEE Reaction(" + eventPins.get(PIN_FROM).getName() + ","   //$NON-NLS-1$//$NON-NLS-2$
						+ eventPins.get(PIN_TO).getName() + ")");  //$NON-NLS-1$
			} else {
				comment.append("GUARANTEE Whenever event " + event.getName() + " occurs, then events(" //$NON-NLS-1$ //$NON-NLS-2$
						+ eventPins.get(PIN_FROM).getName() + "," + eventPins.get(PIN_TO).getTypeName() + ") occur"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			comment.append(" within " + time + "ms ");   //$NON-NLS-1$//$NON-NLS-2$

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
		cncmd = new ChangeNameCommand(subapp, NameRepository.createUniqueName(subapp, "_CONTRACT_" + fb.getName())); //$NON-NLS-1$
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