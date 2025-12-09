/*******************************************************************************
 * Copyright (c) 2021, 2024 Johannes Kepler University Linz and others
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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.fordiac.ide.fb.interpreter.inputgenerator.InputGenerator;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.Messages;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateRecordedServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.widgets.RecordSequenceDialog;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.gef.commands.CommandStack;
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
		final ServiceSequence seq = ServiceHandlerUtils.getFirstSelectedSequence(selection);
		final FBType type = ServiceHandlerUtils.getSelectedFbType(seq, selection);

		final List<String> events = new ArrayList<>();
		final List<List<String>> parameters = new ArrayList<>();
		parameters.add(new ArrayList<>());
		final RecordSequenceDialog dialog = new RecordSequenceDialog(HandlerUtil.getActiveShell(event), events,
				parameters.get(0), type);
		final int returnCode = dialog.open();
		if (returnCode != CANCEL) {

			try {
				addRandomElements(type, events, parameters, dialog);

				final CreateRecordedServiceSequenceCommand recordCmd = new CreateRecordedServiceSequenceCommand(type,
						seq, events, parameters, !dialog.isAppend());
				recordCmd.setStartState(dialog.getStartState());
				final CommandStack cmdstack = HandlerUtil.getActiveEditor(event).getAdapter(CommandStack.class);
				if (recordCmd.canExecute()) {
					cmdstack.execute(recordCmd);
				}
			} catch (final Exception e) {
				FordiacLogHelper.logError(e.getMessage(), e);
				MessageDialog.openError(HandlerUtil.getActiveShell(event),
						Messages.RecordServiceSequenceHandler_PROBLEM,
						Messages.RecordServiceSequenceHandler_CHECK_VARIABLE_NAMES);
			}
		}
		return Status.OK_STATUS;
	}

	private static void addRandomElements(final FBType type, final List<String> eventNames,
			final List<List<String>> parameterStrings, final RecordSequenceDialog dialog) {
		// append a specified number of random events
		if (dialog.isRandomEventBoxChecked()) {
			final List<Event> randomEvents = InputGenerator.getRandomEventsSequence(type, dialog.getCount());
			eventNames.addAll(
					randomEvents.stream().map(event -> event.getName()).toList());
		}
		// apply random data values
		if (dialog.isRandomParameterBoxChecked() && dialog.getCount() > 0) {
			final List<Event> allEvents = ServiceSequenceUtils.getEvents(type, eventNames);
			// also for first event
			if (parameterStrings.get(0).isEmpty()) {
				parameterStrings.get(0).addAll(getRandomParameterStrings(allEvents.get(0)));
			}
			// for the remaining events
			for (final Event e : allEvents.subList(1, allEvents.size())) {
				parameterStrings.add(getRandomParameterStrings(e));
			}
		}
	}

	private static List<String> getRandomParameterStrings(final Event e) {
		final List<VarDeclaration> randomData = InputGenerator.getRandomData(e);
		final String parameter = ServiceSequenceUtils.summarizeParameters(randomData);
		return ServiceSequenceUtils.splitList(parameter);
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final StructuredSelection structuredSelection) {
			setBaseEnabled(structuredSelection.size() <= 1);
		}
	}
}
