/*******************************************************************************
 * Copyright (c) 2023 Fabio Gandolfi
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.EventManager;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBTransaction;
import org.eclipse.fordiac.ide.fb.interpreter.OpSem.Transaction;
import org.eclipse.fordiac.ide.fb.interpreter.api.EventManagerFactory;
import org.eclipse.fordiac.ide.fb.interpreter.mm.EventManagerUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.ServiceSequenceUtils;
import org.eclipse.fordiac.ide.fb.interpreter.mm.VariableUtils;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.commands.CreateServiceSequenceCommand;
import org.eclipse.fordiac.ide.fbtypeeditor.servicesequence.editparts.SequenceRootEditPart;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public class CreateAllServiceSequencesHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);

		FBType fbtype = ((BasicFBType) ((EditPart) selection.getFirstElement()).getModel());

		if ((selection.getFirstElement() instanceof SequenceRootEditPart) && (selection.toList().size() == 1)) {
			final CreateServiceSequenceCommand cmd = new CreateServiceSequenceCommand(
					((FBType) ((EditPart) selection.getFirstElement()).getModel()).getService());
			if (cmd.canExecute()) {
				cmd.execute();
			}
			final ServiceSequence seq = (ServiceSequence) cmd.getCreatedElement();

			final Set<Set<Object>> allCombinationsSimple = getAllCombinationsOfEvents(
					((FBType) ((EditPart) selection.getFirstElement()).getModel()).getInterfaceList().getEventInputs()
							.stream().toArray());

			// final Set<Set<Object>> allCombinationsQuadratic = getAllCombinationsOfEvents(
			// allCombinationsSimple.toArray());

			// for (final Set<Object> set2 :
			// allCombinationsQuadratic) {
			// System.out.println(set2.toString());
			// }

			final EList<ECState> states = ((BasicFBType) fbtype).getECC().getECState();

			final List<InputObject> combinations = getAllCombinationsWithStartStates(allCombinationsSimple, states);

			fbtype = seq.getService().getFBType();
			setParameters(fbtype, new ArrayList<>());

			final List<EventManager> eventManagers = createEventManagers(fbtype, combinations);

			for (final EventManager eventManager : eventManagers) {
				EventManagerUtils.process(eventManager);

				for (final Transaction transaction : eventManager.getTransactions()) {
					ServiceSequenceUtils.convertTransactionToServiceModel(seq, fbtype, (FBTransaction) transaction);
				}
			}

		}
		return null;
	}

	private static Set<Set<Object>> getAllCombinationsOfEvents(final Object[] events) {
		Set<Set<Object>> combinations = Sets.combinations(ImmutableSet.copyOf(events), 1);
		if (events.length > 1) {
			for (int i = 2; i < events.length; i++) {
				combinations = Sets.union(combinations, Sets.combinations(ImmutableSet.copyOf(events), i));
			}
		}
		return combinations;
	}

	private List<InputObject> getAllCombinationsWithStartStates(final Set<Set<Object>> eventCombinations,
			final EList<ECState> states) {

		final List<InputObject> combinations = new ArrayList<>();

		for (final Set<Object> events : eventCombinations) {
			for (final ECState state : states) {
				combinations.add(new InputObject(
						(ArrayList<Event>) events.stream().map(Event.class::cast).collect(Collectors.toList()), state));
			}
		}

		return combinations;

	}

	private List<EventManager> createEventManagers(final FBType fbtype, final List<InputObject> combinations) {
		final List<EventManager> eventmanagers = new ArrayList<>();
		for (final InputObject comb : combinations) {
			final FBType typeCopy = EcoreUtil.copy(fbtype);
			eventmanagers.add(
					EventManagerFactory.createEventManager(typeCopy, comb.events, true, comb.startState.getName()));
		}

		return eventmanagers;
	}

	static void setParameters(final FBType fbType, final List<String> parameters) {
		// parameter: format "VarName:=Value"
		for (final String param : parameters) {
			final String[] paramValues = param.split(":="); //$NON-NLS-1$
			if (paramValues.length == 2) {
				VariableUtils.setVariable(fbType, paramValues[0], paramValues[1]);
			}
		}
	}

	public static class InputObject {
		private final ArrayList<Event> events;
		private final ECState startState;

		public InputObject(final ArrayList<Event> events, final ECState startState) {
			this.events = events;
			this.startState = startState;
		}

		public ArrayList<Event> getEvents() {
			return events;
		}

		public ECState getStartState() {
			return startState;
		}
	}
}
