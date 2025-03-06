/*******************************************************************************
 * Copyright (c) 2023, 2024 Johannes Kepler University Linz
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
 *  Felix Schmid
 *    - redesign to only use a general purpose dialog
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.application.widgets.ContractEditorDialog;
import org.eclipse.fordiac.ide.contracts.model.helpers.ContractUtils;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class DefineFbInterfaceConstraintHandler extends AbstractHandler {

	public static final int DEFAULT_TIME = 10;
	private static final int DEFAULT_OFFSET = 0;
	private static final int CANCEL = -1;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		// get input and output pins from event
		final List<Event> eventPins = getSelectedPins(event);
		final List<Event> iPins = new ArrayList<>();
		final List<Event> oPins = new ArrayList<>();

		for (final Event pin : eventPins) {
			if (pin.isIsInput()) {
				iPins.add(pin);
			} else {
				oPins.add(pin);
			}
		}
		final Shell shell = HandlerUtil.getActiveShell(event);

		// check if exactly 1 input pin and optionally up to 2 output pins
		if (iPins.size() != 1 || oPins.size() > 2) {
			MessageDialog.openError(shell, Messages.DefineFbInterfaceConstraintHandler_Title,
					Messages.DefineFbInterfaceConstraintHandler_Info);
			return Status.CANCEL_STATUS;
		}
		// create new contract rule based on selected pins
		final Event iPin = iPins.get(0);

		if (oPins.size() == 2) {
			makeThreePinReaction(event, iPin, oPins);
		} else if (oPins.size() == 1) {
			final DefineFBDecisionTwoPinDialog dialog = new DefineFBDecisionTwoPinDialog(shell);
			dialog.open();

			if (dialog.isReaction()) {
				makeTwoPinReaction(event, iPin, oPins.get(0));
			} else if (dialog.isGuarantee()) {
				makeTwoPinGuarantee(event, iPin, oPins.get(0));
			}
		} else {
			makeOnePinConstraint(event, iPin);
		}

		return Status.OK_STATUS;
	}

	private static void makeThreePinReaction(final ExecutionEvent event, final Event iPin, final List<Event> oPins) {
		final String suggestion = ContractUtils.createGuaranteeTwoEvents(iPin.getName(), oPins.get(0).getName(),
				oPins.get(1).getName(), String.valueOf(DEFAULT_TIME));
		openDialog(event, iPin, suggestion);
	}

	private static void makeTwoPinReaction(final ExecutionEvent event, final Event iPin, final Event oPin) {
		final String suggestion = ContractUtils.createReactionString(iPin.getName(), oPin.getName(),
				String.valueOf(DEFAULT_TIME));
		openDialog(event, iPin, suggestion);
	}

	private static void makeTwoPinGuarantee(final ExecutionEvent event, final Event iPin, final Event oPin) {
		final String suggestion = ContractUtils.createGuaranteeString(iPin.getName(), oPin.getName(),
				String.valueOf(DEFAULT_TIME));
		openDialog(event, iPin, suggestion);
	}

	private static void makeOnePinConstraint(final ExecutionEvent event, final Event iPin) {
		final StringBuilder suggestion = new StringBuilder();
		suggestion.append(ContractUtils.createAssumptionString(iPin.getName(), String.valueOf(DEFAULT_TIME)));
		suggestion.append(" "); //$NON-NLS-1$
		suggestion.append(ContractUtils.createOffsetString(String.valueOf(DEFAULT_OFFSET)));

		openDialog(event, iPin, suggestion.toString());
	}

	private static void openDialog(final ExecutionEvent event, final Event pin, final String suggestion) {
		final Shell shell = HandlerUtil.getActiveShell(event);
		final FBNetworkElement element = pin.getFBNetworkElement();
		final ContractEditorDialog dialog = new ContractEditorDialog(shell, element, suggestion,
				Messages.ContractElementDialog_Info, String.valueOf(DefineFbInterfaceConstraintHandler.DEFAULT_TIME));

		if (dialog.open() != CANCEL) {
			final String rule = dialog.getSimplifiedContractRule();

			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack cmdStack = editor.getAdapter(CommandStack.class);

			cmdStack.execute(new UpdateContractCommand(element, rule));
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
