/*******************************************************************************
 * Copyright (c) 2021, 2023 Johannes Kepler University Linz and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Bianca Wiesmayr
 *     - initial API and implementation and/or initial documentation
 *  Paul Pavlicek
 *     - cleanup and extracting code, added random generation
 *  Felix Roithmayr
 *     - added extra support for context menu entry
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.CoverageCalculator;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.api.TransactionFactory;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.InputGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.ServiceSequenceEditPart;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.helpers.ServiceSequenceSaveAndLoadHelper;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets.RecordSequenceDialog;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceTransaction;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RecordServiceSequenceHandler extends AbstractHandler {

	private static final int CANCEL = -1;

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		for (final Object selected : selection.toList()) {
			ServiceSequence seq = getSequence(selected);
			if ((seq == null) && (selected instanceof SequenceRootEditPart) && (selection.toList().size() == 1)) {
				final CreateServiceSequenceCommand cmd = new CreateServiceSequenceCommand(
						((FBType) ((EditPart) selected).getModel()).getService());
				if (cmd.canExecute()) {
					cmd.execute();
				}
				seq = getSequence(cmd.getCreatedElement());
			}

			if (seq != null) {
				final List<String> events = new ArrayList<>();
				final List<String> parameters = new ArrayList<>();
				final RecordSequenceDialog dialog = new RecordSequenceDialog(HandlerUtil.getActiveShell(event), events,
						parameters, seq);
				final int returnCode = dialog.open();
				if (returnCode != CANCEL) {
					try {
						final FBType fbType = seq.getService().getFBType();
						setParameters(fbType, parameters);
						runInterpreter(seq, events, parameters, fbType, dialog);
					} catch (final Exception e) {
						FordiacLogHelper.logError(e.getMessage(), e);
						MessageDialog.openError(HandlerUtil.getActiveShell(event),
								Messages.RecordServiceSequenceHandler_PROBLEM,
								Messages.RecordServiceSequenceHandler_CHECK_VARIABLE_NAMES);
					}
				}
			}
		}
		return Status.OK_STATUS;
	}

	private static void runInterpreter(final ServiceSequence seq, final List<String> eventNames,
			final List<String> parameters, final FBType fbType, final RecordSequenceDialog dialog) {
		final FBType typeCopy = EcoreUtil.copy(fbType);

		EventManager eventManager = null;
		List<Event> events = new ArrayList<>();
		// random events, random parameters
		if (dialog.isRandomEventBoxChecked() && dialog.isRandomParameterBoxChecked() && (dialog.getCount() > 0)) {
			events.addAll(InputGenerator.getRandomEventsSequence(typeCopy, dialog.getCount()));
			eventManager = addToGenSequence(fbType, seq, events, true, dialog.isAppend());
		}
		// random events, not random parameters
		else if (dialog.isRandomEventBoxChecked() && !dialog.isRandomParameterBoxChecked() && (dialog.getCount() > 0)) {
			events.addAll(InputGenerator.getRandomEventsSequence(typeCopy, dialog.getCount()));
			eventManager = addToGenSequence(fbType, seq, events, false, dialog.isAppend());
		}
		// not random events, random parameters
		else if (!dialog.isRandomEventBoxChecked() && dialog.isRandomParameterBoxChecked() && parameters.isEmpty()
				&& (dialog.getCount() > 0)) {
			events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
					.filter(Objects::nonNull).toList();
			eventManager = addToGenSequence(fbType, seq, events, true, dialog.isAppend());
		}
		// not random events, no parameters
		else if (!dialog.isRandomEventBoxChecked() && !dialog.isRandomParameterBoxChecked() && parameters.isEmpty()
				&& (dialog.getCount() > 0)) {
			events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
					.filter(Objects::nonNull).toList();
			eventManager = addToGenSequence(fbType, seq, events, false, dialog.isAppend());
		}
		// not random events, not random parameters
		else if (!dialog.isRandomEventBoxChecked() && !dialog.isRandomParameterBoxChecked() && !parameters.isEmpty()
				&& (dialog.getCount() > 0)) {
			events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
					.filter(Objects::nonNull).toList();
			eventManager = addToGenSequence(fbType, seq, events, false, dialog.isAppend());
			for (final ServiceTransaction transaction : seq.getServiceTransaction()) {
				transaction.getInputPrimitive().setParameters(formatInputParameter(parameters));
			}
		}

		// not random events, for first event not random parameter but for the following
		else if (!dialog.isRandomEventBoxChecked() && dialog.isRandomParameterBoxChecked() && !parameters.isEmpty()
				&& (dialog.getCount() > 0)) {
			events = eventNames.stream().filter(s -> !s.isBlank()).map(name -> findEvent(typeCopy, name))
					.filter(Objects::nonNull).toList();
			eventManager = addToGenSequence(fbType, seq, events.subList(0, 1), false, dialog.isAppend());
			eventManager.getTransactions().addAll(
					(addToGenSequence(fbType, seq, events.subList(1, events.size()), true, true)).getTransactions());

			seq.getServiceTransaction().get(0).getInputPrimitive().setParameters(formatInputParameter(parameters));
		}

		seq.setStartState(dialog.getStartState());

		seq.setComment(
				"Coverage: " + CoverageCalculator.calculateCoverageOfSequence(eventManager.getTransactions(), fbType)); //$NON-NLS-1$
		seq.setEventManager(eventManager);
		ServiceSequenceSaveAndLoadHelper.saveServiceSequence(fbType, seq);
	}

	private static String formatInputParameter(final List<String> list) {
		final StringBuilder sb = new StringBuilder();

		for (final String s : list) {
			sb.append(s);
			sb.append("; \n"); //$NON-NLS-1$
		}

		return sb.toString();
	}

	private static EventManager addToGenSequence(final FBType fbType, final ServiceSequence seq,
			final List<Event> eventList, final boolean isRandom, final boolean isAppend) {
		final FBType typeCopy = EcoreUtil.copy(fbType);
		final EventManager eventManager = EventManagerFactory.createEventManager(typeCopy, eventList, isRandom,
				Messages.ServiceSequenceAssignView_START);
		if (!isRandom) {
			TransactionFactory.addTraceInfoTo(eventManager.getTransactions());
		}
		EventManagerUtils.process(eventManager);
		if (!isAppend) {
			seq.getServiceTransaction().clear();
		}
		for (final Transaction transaction : eventManager.getTransactions()) {
			ServiceSequenceUtils.convertTransactionToServiceModel(seq, fbType, (FBTransaction) transaction);
		}
		return eventManager;
	}

	static void setParameters(final FBType fbType, final List<String> parameters) {
		// parameter: format "VarName:=Value"
		for (final String param : parameters) {
			final String[] paramValues = param.split(":=", 2); //$NON-NLS-1$
			if (paramValues.length == 2) {
				VariableUtils.setVariable(fbType, paramValues[0], paramValues[1]);
			}
		}
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final StructuredSelection structuredSelection) {
			setBaseEnabled(structuredSelection.size() <= 1);
		}
	}

	private static Event findEvent(final FBType fbType, final String eventName) {
		final Event event = (Event) fbType.getInterfaceList().getInterfaceElement(eventName);
		if ((event == null) || !event.isIsInput()) {
			throw new IllegalArgumentException("input primitive: event " + eventName + " does not exist"); //$NON-NLS-1$//$NON-NLS-2$
		}
		return event;
	}

	private static ServiceSequence getSequence(final Object selected) {
		if (selected instanceof final ServiceSequenceEditPart selectedSSEP) {
			return selectedSSEP.getModel();
		}
		if (selected instanceof final ServiceSequence selectedSS) {
			return selectedSS;
		}
		return null;
	}
}
