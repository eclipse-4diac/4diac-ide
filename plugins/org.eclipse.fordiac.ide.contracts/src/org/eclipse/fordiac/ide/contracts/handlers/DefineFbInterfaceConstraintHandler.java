/*******************************************************************************
 * Copyright (c) 2023, 2025 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Paul Pavlicek - initial API and implementation and/or initial documentation
 *  Felix Schmid  - redesign to only use a general purpose dialog
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.contractSpec.CausalFuncName;
import org.eclipse.fordiac.ide.contractSpec.CausalRelation;
import org.eclipse.fordiac.ide.contracts.Messages;
import org.eclipse.fordiac.ide.contracts.commands.PrepareContractCommand;
import org.eclipse.fordiac.ide.contracts.dialogs.DefineContractDecisionDialog;
import org.eclipse.fordiac.ide.contracts.helpers.ContractUtils;
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

	private static final String DEFAULT_TIME = "10ms"; //$NON-NLS-1$
	private static final int CANCEL = -1;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		// get input and output pins from event
		final List<Event> eventPins = getSelectedPins(event);
		final List<Event> iPins = new ArrayList<>();
		final List<String> iPinN = new ArrayList<>();
		final List<Event> oPins = new ArrayList<>();
		final List<String> oPinN = new ArrayList<>();

		for (final Event pin : eventPins) {
			if (pin.isIsInput()) {
				iPins.add(pin);
				iPinN.add(pin.getName());
			} else {
				oPins.add(pin);
				oPinN.add(pin.getName());
			}
		}

		// error when no pins selected
		final Shell shell = HandlerUtil.getActiveShell(event);
		if (iPins.isEmpty() && oPins.isEmpty()) {
			MessageDialog.openError(shell, Messages.NoPinSelectedErrorDialog_Title,
					Messages.NoPinSelectedErrorDialog_Info);
			return Status.CANCEL_STATUS;
		}

		// create suggested contract templates based on selected pins
		final List<String> names = new ArrayList<>();
		final List<String> templates = new ArrayList<>();
		final List<Event> pins = iPins.isEmpty() ? oPins : iPins;
		final List<String> pinNames = iPinN.isEmpty() ? oPinN : iPinN;

		if (iPins.size() == 1 && oPins.size() == 1) {
			names.add(Messages.ContractRuleCausalReaction);
			templates.add(ContractUtils.createCausalReaction(iPinN.get(0), oPinN.get(0), DEFAULT_TIME));
			names.add(Messages.ContractRuleCausalReaction);
			templates.add(ContractUtils.createCausalFuncDecl(CausalFuncName.REACTION, iPinN.get(0), oPinN.get(0),
					CausalRelation.FIFO));

			names.add(Messages.ContractRuleCausalAge);
			templates.add(ContractUtils.createCausalAge(iPinN.get(0), oPinN.get(0), DEFAULT_TIME));
			names.add(Messages.ContractRuleCausalReaction);
			templates.add(ContractUtils.createCausalFuncDecl(CausalFuncName.AGE, oPinN.get(0), iPinN.get(0),
					CausalRelation.FIFO));
		} else if (iPins.isEmpty() || oPins.isEmpty()) {
			names.add(Messages.ContractRuleSingleEvent);
			templates.add(ContractUtils.createSingleEvent(pinNames, DEFAULT_TIME));

			names.add(Messages.ContractRuleRepetition);
			templates.add(ContractUtils.createRepetition(pinNames, DEFAULT_TIME, null, null));
			names.add(Messages.ContractRuleRepetition);
			templates.add(ContractUtils.createRepetition(pinNames, DEFAULT_TIME, DEFAULT_TIME, null));
			names.add(Messages.ContractRuleRepetition);
			templates.add(ContractUtils.createRepetition(pinNames, DEFAULT_TIME, null, DEFAULT_TIME));
			names.add(Messages.ContractRuleRepetition);
			templates.add(ContractUtils.createRepetition(pinNames, DEFAULT_TIME, DEFAULT_TIME, DEFAULT_TIME));
		}
		if (!iPins.isEmpty() && !oPins.isEmpty()) {
			names.add(Messages.ContractRuleReaction);
			templates.add(ContractUtils.createReaction(iPinN, oPinN, false, false, DEFAULT_TIME, false, 0, 0));

			names.add(Messages.ContractRuleAge);
			templates.add(ContractUtils.createAge(iPinN, oPinN, false, false, DEFAULT_TIME, false, 0, 0));

			if (iPins.size() > 1 || oPins.size() > 1) { // add alternative reaction/age with sequence(s)
				names.add(Messages.ContractRuleReaction);
				templates.add(ContractUtils.createReaction(iPinN, oPinN, true, true, DEFAULT_TIME, false, 0, 0));

				names.add(Messages.ContractRuleAge);
				templates.add(ContractUtils.createAge(iPinN, oPinN, true, true, DEFAULT_TIME, false, 0, 0));
			}
		}
		// add an empty suggestion in case that the others don't fit well
		names.add(Messages.ContractRuleEmpty);
		templates.add("// TODO"); //$NON-NLS-1$

		final DefineContractDecisionDialog dialog = new DefineContractDecisionDialog(shell, names, templates);
		if (dialog.open() != CANCEL) {
			final FBNetworkElement fbElem = pins.get(0).getBlockFBNetworkElement();
			final IEditorPart editor = HandlerUtil.getActiveEditor(event);
			final CommandStack cmdStack = editor.getAdapter(CommandStack.class);
			cmdStack.execute(new PrepareContractCommand(fbElem, iPins, oPins, dialog.getTemplate()));
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private static List<Event> getSelectedPins(final ExecutionEvent event) {
		final ArrayList<Event> pins = new ArrayList<>();
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection) {
			Object obj = selected;
			if (selected instanceof final EditPart selectedEP) {
				obj = selectedEP.getModel();
			}
			if (obj instanceof final Event eventPin) {
				pins.add(eventPin);
			}
		}
		final boolean sameFb = pins.stream().filter(ev -> ev.getBlockFBNetworkElement() != null)
				.allMatch(ev -> ev.getBlockFBNetworkElement().equals(pins.get(0).getBlockFBNetworkElement()));
		if (sameFb) {
			return pins;
		}
		return Collections.emptyList();
	}
}
