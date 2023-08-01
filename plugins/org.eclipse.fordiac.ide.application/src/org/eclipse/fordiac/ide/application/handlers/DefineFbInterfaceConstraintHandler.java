/*******************************************************************************
 * Copyright (c) 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    - initial API and implementation and/or initial documentation
 *  Paul Pavlicek
 *    - - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.Messages;
import org.eclipse.fordiac.ide.application.commands.UpdateConstraintCommand;
import org.eclipse.fordiac.ide.application.commands.UpdateReactionCommand;
import org.eclipse.fordiac.ide.application.utilities.DefineFBReactionOnePinDialog;
import org.eclipse.fordiac.ide.application.utilities.DefineFBReactionTwoPinDialog;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class DefineFbInterfaceConstraintHandler extends AbstractHandler {

	private static final int PIN_TO = 1;
	private static final int PIN_FROM = 0;
	private static final int CANCEL = -1;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final List<Event> eventPins = getSelectedPins(event);

		if (eventPins.size() == 2) {
			makeTwoPInReaction(event, eventPins);

		} else if (eventPins.size() == 1) {
			makeOnePinConstraint(event, eventPins);

		} else {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					Messages.DefineFbInterfaceConstraintHandler_Title,
					Messages.DefineFbInterfaceConstraintHandler_Info);
			return Status.CANCEL_STATUS;
		}

		eventPins.clear();
		return Status.OK_STATUS;

	}

	private static void makeTwoPInReaction(final ExecutionEvent event, final List<Event> eventPins) {
		if (eventPins.get(PIN_TO).isIsInput()) {
			eventPins.add(PIN_FROM, eventPins.get(PIN_TO));
			eventPins.remove(2);

		}
		final DefineFBReactionTwoPinDialog dialog = new DefineFBReactionTwoPinDialog(HandlerUtil.getActiveShell(event),
				eventPins.get(0), eventPins.get(1));

		String time = ""; //$NON-NLS-1$
		if (dialog.open() != CANCEL) {
			time = dialog.getTime();
		}
		final UpdateReactionCommand urcmd = new UpdateReactionCommand(eventPins, time);
		if (urcmd.canExecute()) {
			urcmd.execute();
		}
	}

	private static void makeOnePinConstraint(final ExecutionEvent event, final List<Event> eventPins) {
		final DefineFBReactionOnePinDialog dialog = new DefineFBReactionOnePinDialog(HandlerUtil.getActiveShell(event),
				eventPins.get(0));
		String time = ""; //$NON-NLS-1$
		String offsetText = null;
		String state = ""; //$NON-NLS-1$
		if (dialog.open() != CANCEL) {

			time = dialog.getTime();
			state = dialog.getState();
			if (dialog.hasOffset()) {
				offsetText = dialog.getOffsetText();
			}
		}
		final UpdateConstraintCommand uccmd = new UpdateConstraintCommand(eventPins, time, offsetText, state);
		if (uccmd.canExecute()) {
			uccmd.execute();
		}
	}

	private static List<Event> getSelectedPins(final ExecutionEvent event) {
		final ArrayList<Event> pins = new ArrayList<>();
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			Object obj = selected;
			if (selected instanceof final EditPart selectedEP) {
				obj = selectedEP.getModel();
			}
			if (obj instanceof final Event eventPin) {
				pins.add(eventPin);
			}
		}

		final boolean sameFb = pins.stream().filter(ev -> ev.getFBNetworkElement() != null)
				.allMatch(ev -> ev.getFBNetworkElement().equals(pins.get(0).getFBNetworkElement()));
		if (sameFb) {
			return pins;
		}
		return Collections.emptyList();

	}

}