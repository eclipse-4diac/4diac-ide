/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.test.model.eval.fb;

import static org.eclipse.fordiac.ide.model.eval.value.DIntValue.toDIntValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.BasicFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm;
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ECAction;
import org.eclipse.fordiac.ide.model.libraryElement.ECC;
import org.eclipse.fordiac.ide.model.libraryElement.ECState;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "squid:S5960", "nls" })
class BasicFBEvaluatorTest extends AbstractFBEvaluatorTest {
	@Test
	void testBasicFB() throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event outputEvent = newEvent("CNF", false);
		final STAlgorithm alg = newSTAlgorithm("DO1 := DI1 + DI2;", "REQ");
		final ECState init = newState("INIT");
		final ECState state = newState("STATE", newAction(alg, outputEvent));
		final ECC ecc = newECC(List.of(init, state), List.of(newTransition(init, state, inputEvent, null)));
		assertTrace(toDIntValue(21), List.of(outputEvent), List.of(state),
				evaluateBasicFB(ecc, List.of(inputEvent),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
	}

	@Test
	void testBasicFBInitialState() throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event outputEvent = newEvent("CNF", false);
		final STAlgorithm alg = newSTAlgorithm("DO1 := 0;", "REQ");
		final STAlgorithm alg2 = newSTAlgorithm("DO1 := DI1 + DI2;", "REQ2");
		final ECState init = newState("INIT");
		final ECState state = newState("STATE", newAction(alg, outputEvent));
		final ECState state2 = newState("STATE2", newAction(alg2, outputEvent));
		final ECC ecc = newECC(List.of(init, state, state2),
				List.of(newTransition(init, state, inputEvent, null), newTransition(state, state2, inputEvent, null)));
		final BasicFBType fb = newBasicFB(ecc,
				List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
						newVarDeclaration("DI2", ElementaryTypes.DINT, true),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
		assertTrace(toDIntValue(0), List.of(outputEvent), List.of(state), evaluateBasicFB(fb, List.of(inputEvent),
				List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))));
		assertTrace(toDIntValue(21), List.of(outputEvent), List.of(state2),
				evaluateBasicFB(fb, List.of(inputEvent), List.of(newVariable(toDIntValue(17), "DI1"),
						newVariable(toDIntValue(4), "DI2"), new ECStateVariable(state))));
	}

	@Test
	void testBasicFBMultiEvent() throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event inputEvent2 = newEvent("REQ2", true);
		final Event outputEvent = newEvent("CNF", false);
		final STAlgorithm alg = newSTAlgorithm("DO1 := DI1;", "REQ");
		final STAlgorithm alg2 = newSTAlgorithm("DO1 := DO1 + DI2;", "REQ2");
		final ECState init = newState("INIT");
		final ECState state = newState("STATE", newAction(alg, outputEvent));
		final ECState state2 = newState("STATE2", newAction(alg2, outputEvent));
		final ECC ecc = newECC(List.of(init, state, state2),
				List.of(newTransition(init, state, inputEvent, null), newTransition(state, state2, inputEvent2, null)));
		assertTrace(toDIntValue(21), List.of(outputEvent, outputEvent), List.of(state, state2),
				evaluateBasicFB(ecc, List.of(inputEvent, inputEvent2),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
	}

	@Test
	void testBasicFBMultiAction() throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event outputEvent = newEvent("CNF", false);
		final STAlgorithm alg = newSTAlgorithm("DO1 := DI1;", "REQ");
		final STAlgorithm alg2 = newSTAlgorithm("DO1 := DO1 + DI2;", "REQ2");
		final ECState init = newState("INIT");
		final ECState state = newState("STATE", newAction(alg, outputEvent), newAction(alg2, outputEvent));
		final ECC ecc = newECC(List.of(init, state), List.of(newTransition(init, state, inputEvent, null)));
		assertTrace(toDIntValue(21), List.of(outputEvent, outputEvent), List.of(state),
				evaluateBasicFB(ecc, List.of(inputEvent),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
	}

	@Test
	void testBasicFBStateLoop() throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event outputEvent = newEvent("CNF", false);
		final STAlgorithm alg = newSTAlgorithm("""
					DI1 := DI1 - 1;
					DI2 := DI2 + 1;
					DO1 := DI2;
				""", "REQ");
		final ECState init = newState("INIT");
		final ECState state = newState("STATE", newAction(alg, outputEvent));
		final ECC ecc = newECC(List.of(init, state), List.of(newTransition(init, state, inputEvent, "DI1 > 0"),
				newTransition(state, state, null, "DI1 > 0")));
		assertTrace(toDIntValue(21), repeatEvent(outputEvent, 17), repeatState(state, 17),
				evaluateBasicFB(ecc, List.of(inputEvent),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
	}

	@Test
	void testBasicFBMultiStateLoop() throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event outputEvent = newEvent("CNF", false);
		final STAlgorithm alg = newSTAlgorithm("DI1 := DI1 - 1;", "REQ");
		final STAlgorithm alg2 = newSTAlgorithm("DI2 := DI2 + 1; DO1 := DI2;", "REQ2");
		final ECState init = newState("INIT");
		final ECState state = newState("STATE", newAction(alg, outputEvent));
		final ECState state2 = newState("STATE2", newAction(alg2, outputEvent));
		final ECC ecc = newECC(List.of(init, state, state2), List.of(newTransition(init, state, inputEvent, "DI1 > 0"),
				newTransition(state, state2, null, null), newTransition(state2, state, null, "DI1 > 0")));
		assertTrace(toDIntValue(21), repeatEvent(outputEvent, (17 * 2)), repeatStates(List.of(state, state2), 17),
				evaluateBasicFB(ecc, List.of(inputEvent),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
	}

	static TracingBasicFBEvaluator evaluateBasicFB(final ECC ecc, final List<Event> inputEvents,
			final List<Variable<?>> variables, final VarDeclaration output)
			throws EvaluatorException, InterruptedException {
		return evaluateBasicFB(
				newBasicFB(ecc,
						Stream.concat(variables.stream().map(
								variable -> newVarDeclaration(variable.getName(), (DataType) variable.getType(), true)),
								Stream.of(output)).toList()),
				inputEvents, variables);
	}

	static TracingBasicFBEvaluator evaluateBasicFB(final BasicFBType fbType, final List<Event> inputEvents,
			final List<Variable<?>> variables) throws EvaluatorException, InterruptedException {
		final TracingBasicFBEvaluator eval = new TracingBasicFBEvaluator(fbType, null, variables, null);
		eval.setEventQueue(new TracingFBEvaluatorEventQueue(inputEvents));
		eval.evaluate();
		return eval;
	}

	static void assertTrace(final Object expectedResult, final Iterable<? extends Event> expectedEvents,
			final Iterable<? extends ECState> expectedTrace, final TracingBasicFBEvaluator actual) {
		assertEquals(expectedResult, actual.getVariables().get("DO1").getValue());
		assertIterableEquals(expectedEvents, ((TracingFBEvaluatorEventQueue) actual.getEventQueue()).getOutputEvents());
		assertIterableEquals(expectedTrace, actual.trace);
	}

	static List<Event> repeatEvent(final Event event, final int repeat) {
		return IntStream.range(0, repeat).mapToObj(unused -> event).toList();
	}

	static List<ECState> repeatState(final ECState state, final int repeat) {
		return IntStream.range(0, repeat).mapToObj(unused -> state).toList();
	}

	static List<Event> repeatEvents(final List<Event> event, final int repeat) {
		return IntStream.range(0, repeat).mapToObj(unused -> event).flatMap(Collection::stream).toList();
	}

	static List<ECState> repeatStates(final List<ECState> state, final int repeat) {
		return IntStream.range(0, repeat).mapToObj(unused -> state).flatMap(Collection::stream).toList();
	}

	static BasicFBType newBasicFB(final ECC ecc, final List<VarDeclaration> variables) {
		final BasicFBType type = LibraryElementFactory.eINSTANCE.createBasicFBType();
		type.setName("Test");
		type.setInterfaceList(newInterfaceList(getContainedEvents(ecc), variables));
		type.setECC(ecc);
		type.getCallables().addAll(getContainedCallables(ecc));
		return type;
	}

	static ECC newECC(final List<ECState> states, final List<ECTransition> transitions) {
		final ECC ecc = LibraryElementFactory.eINSTANCE.createECC();
		ECollections.setEList(ecc.getECState(), states);
		ECollections.setEList(ecc.getECTransition(), transitions);
		ecc.setStart(states.getFirst());
		return ecc;
	}

	static ECTransition newTransition(final ECState src, final ECState dest, final Event event,
			final String expression) {
		final ECTransition transition = LibraryElementFactory.eINSTANCE.createECTransition();
		transition.setSource(src);
		transition.setDestination(dest);
		transition.setConditionEvent(event);
		transition.setConditionExpression(expression);
		return transition;
	}

	static ECState newState(final String stateName, final ECAction... actions) {
		return newState(stateName, List.of(actions));
	}

	static ECState newState(final String stateName, final List<ECAction> actions) {
		final ECState state = LibraryElementFactory.eINSTANCE.createECState();
		state.setName(stateName);
		ECollections.setEList(state.getECAction(), actions);
		return state;
	}

	static ECAction newAction(final Algorithm actionAlgorithm, final Event actionEvent) {
		final ECAction action = LibraryElementFactory.eINSTANCE.createECAction();
		action.setAlgorithm(actionAlgorithm);
		action.setOutput(actionEvent);
		return action;
	}

	static Set<? extends ICallable> getContainedCallables(final ECC ecc) {
		return ecc.getECState().stream().map(ECState::getECAction).flatMap(List::stream).map(ECAction::getAlgorithm)
				.filter(Objects::nonNull).collect(Collectors.toSet());
	}

	static Set<Event> getContainedEvents(final ECC ecc) {
		return Stream
				.concat(ecc.getECState().stream().map(ECState::getECAction).flatMap(List::stream)
						.map(ECAction::getOutput), ecc.getECTransition().stream().map(ECTransition::getConditionEvent))
				.filter(Objects::nonNull).collect(Collectors.toSet());
	}

	static class TracingBasicFBEvaluator extends BasicFBEvaluator {
		private final Queue<ECState> trace = new ArrayBlockingQueue<>(1000);

		public TracingBasicFBEvaluator(final BasicFBType type, final Variable<?> context,
				final Iterable<Variable<?>> variables, final Evaluator parent) {
			super(type, context, variables, parent);
		}

		@Override
		protected <T extends Object> T trap(final T context) throws InterruptedException {
			if (context instanceof final ECState state) {
				trace.add(state);
			}
			return super.trap(context);
		}

		public Queue<ECState> getTrace() {
			return trace;
		}
	}
}
