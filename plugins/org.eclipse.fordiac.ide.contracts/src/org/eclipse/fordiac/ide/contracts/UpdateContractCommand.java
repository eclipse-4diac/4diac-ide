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

public class UpdateContractCommand extends Command {

	private ChangeCommentCommand cccmd;
	private NewSubAppCommand subappcmd;
	private ToggleSubAppRepresentationCommand toggle;
	private ChangeNameCommand cncmd;
	private final String comment;
	private final FBNetworkElement fbNetworkElement;

	public UpdateContractCommand(final FBNetworkElement fbNetworkElement, final String comment) {
		this.comment = comment;
		this.fbNetworkElement = fbNetworkElement;
	}

	public static UpdateContractCommand createContractAssumption(final List<Event> eventPins, final String time,
			final String offset, final String state) {
		final StringBuilder comment = new StringBuilder();
		comment.append("ASSUMPTION " + eventPins.get(0).getName()); //$NON-NLS-1$
		comment.append(" occurs " + state + " ");   //$NON-NLS-1$//$NON-NLS-2$
		comment.append(time + "ms "); //$NON-NLS-1$
		if (offset != null) {
			comment.append("with " + offset + "ms" + " offset");   //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
		}

		return new UpdateContractCommand(eventPins.get(0).getFBNetworkElement(), comment.toString());
	}

	public static UpdateContractCommand createContractReaction(final List<Event> eventPins, final String time) {
		final StringBuilder comment = new StringBuilder();
		comment.append("GUARANTEE Reaction (" + eventPins.get(0).getName() + ","   //$NON-NLS-1$//$NON-NLS-2$
				+ eventPins.get(1).getName() + ")");  //$NON-NLS-1$
		comment.append(" within " + time + "ms ");   //$NON-NLS-1$//$NON-NLS-2$
		return new UpdateContractCommand(eventPins.get(0).getFBNetworkElement(), comment.toString());
	}

	public static UpdateContractCommand createContractReaction(final List<Event> eventPins, final String time,
			final boolean dummy) {
		final StringBuilder comment = new StringBuilder();
		comment.append("GUARANTEE Whenever event " + eventPins.get(0).getName() + " occurs, then event "   //$NON-NLS-1$//$NON-NLS-2$
				+ eventPins.get(1).getName());
		comment.append(" occur within " + time + "ms ");   //$NON-NLS-1$//$NON-NLS-2$
		return new UpdateContractCommand(eventPins.get(0).getFBNetworkElement(), comment.toString());
	}

	public static UpdateContractCommand createContractGuarantee(final Event event, final List<Event> outputEvents,
			final String time) {
		final StringBuilder comment = new StringBuilder();
		comment.append("GUARANTEE Whenever event " + event.getName() + " occurs, then events (" //$NON-NLS-1$ //$NON-NLS-2$
				+ outputEvents.get(0).getName() + "," + outputEvents.get(1).getName() + ") occur"); //$NON-NLS-1$ //$NON-NLS-2$
		comment.append(" within " + time + "ms ");   //$NON-NLS-1$//$NON-NLS-2$
		return new UpdateContractCommand(outputEvents.get(0).getFBNetworkElement(), comment.toString());
	}

	@Override
	public void execute() {
		if (cccmd == null) {

			final SubApp subapp = createNewSubapp();
			final StringBuilder finalcomment = new StringBuilder();
			final String oldcomment = subapp.getComment();
			if (oldcomment.indexOf("ASSUMPTION ") == 0) { //$NON-NLS-1$
				finalcomment.append(oldcomment + "\n"); //$NON-NLS-1$
			}
			finalcomment.append(this.comment);
			cccmd = new ChangeCommentCommand(subapp, finalcomment.toString());
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
		if (fbNetworkElement.isNestedInSubApp()) {
			final SubApp subapp = (SubApp) fbNetworkElement.eContainer().eContainer();
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
		subappcmd = new NewSubAppCommand(network, list, pos.getX(), pos.getY());
		if (subappcmd.canExecute()) {
			subappcmd.execute();
		}
		final SubApp subapp = subappcmd.getElement();
		cncmd = new ChangeNameCommand(subapp,
				NameRepository.createUniqueName(subapp, "_CONTRACT_" + fbNetworkElement.getName())); //$NON-NLS-1$
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
