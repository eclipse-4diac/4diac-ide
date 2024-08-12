/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi, Bianca Wiesmayr
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fabio Gandolfi
 *     - initial API and implementation and/or initial documentation
 *   Bianca Wiesmayr - cleanup, use command
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.fb.interpreter.mm.Permutations;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateRecordedServiceSequenceCommand;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class CreateAllServiceSequencesHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		final FBType fbtype = ServiceHandlerUtils.getSelectedFbType(null, selection);
		final CompoundCommand createSequences = createAllSequences(fbtype);
		if (createSequences.canExecute()) {
			final CommandStack cmdstack = HandlerUtil.getActiveEditor(event).getAdapter(CommandStack.class);
			cmdstack.execute(createSequences);
		}
		return Status.OK_STATUS;
	}

	static CompoundCommand createAllSequences(final FBType fbtype) {
		final Event[] events = fbtype.getInterfaceList().getEventInputs().toArray(new Event[0]);

		final List<List<Event>> allCombinationsSimple = Permutations.permute(events);
		final CompoundCommand createSequences = new CompoundCommand();
		if (fbtype instanceof final BasicFBType basic) {
			final List<InputObject> combinations = getAllCombinationsWithStartStates(allCombinationsSimple,
					basic.getECC().getECState());
			for (final InputObject obj : combinations) {
				final CreateRecordedServiceSequenceCommand cmd = new CreateRecordedServiceSequenceCommand(fbtype,
						obj.getEventNames(), Collections.emptyList());
				cmd.setStartState(obj.getStartState().getName());
				createSequences.add(cmd);
			}
		} else {
			allCombinationsSimple.forEach(eventList -> {
				final CreateRecordedServiceSequenceCommand cmd = new CreateRecordedServiceSequenceCommand(fbtype,
						eventList.stream().map(Event::getName).toList(), Collections.emptyList());
				createSequences.add(cmd);
			});
		}
		return createSequences;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection selection = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof final StructuredSelection structuredSelection) {
			setBaseEnabled(ServiceHandlerUtils.getSelectedFbType(null, structuredSelection) != null);
		}
	}

	private static List<InputObject> getAllCombinationsWithStartStates(final List<List<Event>> allCombinationsSimple,
			final EList<ECState> states) {
		final List<InputObject> combinations = new ArrayList<>();
		for (final List<Event> events : allCombinationsSimple) {
			for (final ECState state : states) {
				combinations.add(new InputObject(events.stream().map(Event.class::cast).toList(), state));
			}
		}
		return combinations;
	}

	static class InputObject {
		private final List<Event> events;
		private final ECState startState;

		public InputObject(final List<Event> events, final ECState startState) {
			this.events = events;
			this.startState = startState;
		}

		public List<Event> getEvents() {
			return events;
		}

		public ECState getStartState() {
			return startState;
		}

		public List<String> getEventNames() {
			return events.stream().map(Event::getName).toList();
		}
	}
}
