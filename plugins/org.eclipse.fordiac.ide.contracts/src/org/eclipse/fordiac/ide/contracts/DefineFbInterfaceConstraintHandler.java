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
package org.eclipse.fordiac.ide.contracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class DefineFbInterfaceConstraintHandler extends AbstractHandler {

	private static final int MAX_NUMBER_PINS = 3;
	private static final int PIN_TO = 1;
	private static final int PIN_FROM = 0;
	private static final int CANCEL = -1;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final List<Event> eventPins = getSelectedPins(event);
		if (eventPins.isEmpty() || eventPins.size() > MAX_NUMBER_PINS) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					Messages.DefineFbInterfaceConstraintHandler_Title,
					Messages.DefineFbInterfaceConstraintHandler_Info);
			return Status.CANCEL_STATUS;
		}
		if (eventPins.size() == MAX_NUMBER_PINS) {
			makeThreePinReaction(event, eventPins);
		} else if (eventPins.size() == 2) {
			final DefineFBDecisionTwoPinDialog dialog = new DefineFBDecisionTwoPinDialog(
					HandlerUtil.getActiveShell(event));

			dialog.open();
			if (dialog.isReaction()) {
				makeTwoPinReaction(event, eventPins);
			} else if (dialog.isGuarantee()) {
				makeTwoPinGuarantee(event, eventPins);
			}

		} else {
			makeOnePinConstraint(event, eventPins);
		}

		eventPins.clear();
		return Status.OK_STATUS;

	}

	private static void makeTwoPinGuarantee(final ExecutionEvent event, final List<Event> eventPins) {
		if (eventPins.get(PIN_FROM).isIsInput() && eventPins.get(PIN_TO).isIsInput()
				|| (!eventPins.get(PIN_FROM).isIsInput() && !eventPins.get(PIN_TO).isIsInput())) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					Messages.DefineFbInterfaceConstraintHandler_Title,
					Messages.DefineFbInterfaceConstraintHandler_InfoErrorGuarantee);
		} else {
			if (eventPins.get(1).isIsInput()) {
				eventPins.add(0, eventPins.get(1));
				eventPins.remove(2);

			}
			final DefineFBGuaranteeTwoPinDialog dialog = new DefineFBGuaranteeTwoPinDialog(
					HandlerUtil.getActiveShell(event), eventPins.get(PIN_FROM), eventPins.get(PIN_TO));

			String time = ""; //$NON-NLS-1$
			if (dialog.open() != CANCEL) {
				time = dialog.getTime();

				final UpdateContractCommand uccmd = UpdateContractCommand.createContractGuarantee(eventPins, time);
				if (uccmd.canExecute()) {
					uccmd.execute();
				}
			}
		}

	}

	private static void makeThreePinReaction(final ExecutionEvent event, final List<Event> eventPins) {
		final List<Event> inputEvent = eventPins.stream().filter(Event::isIsInput).toList();
		final List<Event> outputEvents = eventPins.stream().filter(e -> !e.isIsInput()).toList();
		if (inputEvent.size() == 1 && outputEvents.size() == 2) {
			final DefineFBReactionThreePinDialog dialog = new DefineFBReactionThreePinDialog(
					HandlerUtil.getActiveShell(event), inputEvent.get(0), outputEvents);
			String time = ""; //$NON-NLS-1$
			if (dialog.open() != CANCEL) {
				time = dialog.getTime();
				final UpdateContractCommand uccmd = UpdateContractCommand.createContractGuarantee(inputEvent.get(0),
						outputEvents, time);
				if (uccmd.canExecute()) {
					uccmd.execute();
				}
			}
		} else {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					Messages.DefineFbInterfaceConstraintHandler_Title,
					Messages.DefineFbInterfaceConstraintHandler_ThreePinErrorMessage);
		}
	}

	private static void makeTwoPinReaction(final ExecutionEvent event, final List<Event> eventPins) {
		if (eventPins.get(PIN_FROM).isIsInput() && eventPins.get(PIN_TO).isIsInput()
				|| (!eventPins.get(PIN_FROM).isIsInput() && !eventPins.get(PIN_TO).isIsInput())) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					Messages.DefineFbInterfaceConstraintHandler_Title,
					Messages.DefineFbInterfaceConstraintHandler_InfoErrorGuarantee);
		} else {
			if (eventPins.get(1).isIsInput()) {
				eventPins.add(0, eventPins.get(1));
				eventPins.remove(2);

			}
			final DefineFBReactionTwoPinDialog dialog = new DefineFBReactionTwoPinDialog(
					HandlerUtil.getActiveShell(event), eventPins.get(PIN_FROM), eventPins.get(PIN_TO));

			String time = ""; //$NON-NLS-1$
			if (dialog.open() != CANCEL) {
				time = dialog.getTime();

				final UpdateContractCommand uccmd = UpdateContractCommand.createContractReaction(eventPins, time);
				if (uccmd.canExecute()) {
					uccmd.execute();
				}
			}
		}
	}

	private static void makeOnePinConstraint(final ExecutionEvent event, final List<Event> eventPins) {
		if (eventPins.get(0).isIsInput()) {
			final DefineFBReactionOnePinDialog dialog = new DefineFBReactionOnePinDialog(
					HandlerUtil.getActiveShell(event), eventPins.get(0));
			String time = ""; //$NON-NLS-1$
			String offsetText = null;
			if (dialog.open() != CANCEL) {

				time = dialog.getTime();
				if (dialog.hasOffset()) {
					offsetText = dialog.getOffsetText();
				}

				final UpdateContractCommand uccmd = UpdateContractCommand.createContractAssumption(eventPins, time,
						offsetText);
				if (uccmd.canExecute()) {
					uccmd.execute();
				}
			}
		} else {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					Messages.DefineFbInterfaceConstraintHandler_Error,
					Messages.DefineFbInterfaceConstraintHandler_ErrorText);
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