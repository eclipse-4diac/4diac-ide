/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.eval.fb

import java.util.List
import java.util.Queue
import java.util.Set
import java.util.concurrent.ArrayBlockingQueue
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.fb.BasicFBEvaluator
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECAction
import org.eclipse.fordiac.ide.model.libraryElement.ECC
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors
import org.junit.jupiter.api.Test

import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.junit.jupiter.api.Assertions.*

class BasicFBEvaluatorTest extends FBEvaluatorTest {

	@Test
	def void testBasicFB() {
		val inputEvent = "REQ".newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val alg = '''DO1 := DI1 + DI2;'''.newSTAlgorithm("REQ")
		val init = newState("INIT")
		val state = newState("STATE", newAction(alg, outputEvent))
		val ecc = newECC(#[init, state], #[newTransition(init, state, inputEvent, null)])
		21.toIntValue.assertTrace(#[outputEvent], #[state],
			evaluateBasicFB(ecc, #[inputEvent], #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
	}

	@Test
	def void testBasicFBInitialState() {
		val inputEvent = "REQ".newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val alg = '''DO1 := 0;'''.newSTAlgorithm("REQ")
		val alg2 = '''DO1 := DI1 + DI2;'''.newSTAlgorithm("REQ2")
		val init = newState("INIT")
		val state = newState("STATE", newAction(alg, outputEvent))
		val state2 = newState("STATE2", newAction(alg2, outputEvent))
		val ecc = newECC(#[init, state, state2],
			#[newTransition(init, state, inputEvent, null), newTransition(state, state2, inputEvent, null)])
		0.toIntValue.assertTrace(#[outputEvent], #[state],
			evaluateBasicFB(ecc, #[inputEvent], #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
		21.toIntValue.assertTrace(#[outputEvent], #[state2],
			evaluateBasicFB(ecc, #[inputEvent],
				#[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2"), new ECStateVariable(state)],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
	}

	@Test
	def void testBasicFBMultiEvent() {
		val inputEvent = "REQ".newEvent(true)
		val inputEvent2 = "REQ2".newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val alg = '''DO1 := DI1;'''.newSTAlgorithm("REQ")
		val alg2 = '''DO1 := DO1 + DI2;'''.newSTAlgorithm("REQ2")
		val init = newState("INIT")
		val state = newState("STATE", newAction(alg, outputEvent))
		val state2 = newState("STATE2", newAction(alg2, outputEvent))
		val ecc = newECC(#[init, state, state2],
			#[newTransition(init, state, inputEvent, null), newTransition(state, state2, inputEvent2, null)])
		21.toIntValue.assertTrace(#[outputEvent, outputEvent], #[state, state2],
			evaluateBasicFB(ecc, #[inputEvent, inputEvent2],
				#[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
	}

	@Test
	def void testBasicFBMultiAction() {
		val inputEvent = "REQ".newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val alg = '''DO1 := DI1;'''.newSTAlgorithm("REQ")
		val alg2 = '''DO1 := DO1 + DI2;'''.newSTAlgorithm("REQ2")
		val init = newState("INIT")
		val state = newState("STATE", newAction(alg, outputEvent), newAction(alg2, outputEvent))
		val ecc = newECC(#[init, state], #[newTransition(init, state, inputEvent, null)])
		21.toIntValue.assertTrace(#[outputEvent, outputEvent], #[state],
			evaluateBasicFB(ecc, #[inputEvent], #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
	}

	@Test
	def void testBasicFBStateLoop() {
		val inputEvent = "REQ".newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val alg = '''DI1 := DI1 - 1; DI2 := DI2 + 1; DO1 := DI2;'''.newSTAlgorithm("REQ")
		val init = newState("INIT")
		val state = newState("STATE", newAction(alg, outputEvent))
		val ecc = newECC(#[init, state],
			#[newTransition(init, state, inputEvent, "DI1 > 0"), newTransition(state, state, null, "DI1 > 0")])
		21.toIntValue.assertTrace(outputEvent.repeatEvent(17), state.repeatState(17),
			evaluateBasicFB(ecc, #[inputEvent], #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
	}

	@Test
	def void testBasicFBMultiStateLoop() {
		val inputEvent = "REQ".newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val alg = '''DI1 := DI1 - 1;'''.newSTAlgorithm("REQ")
		val alg2 = '''DI2 := DI2 + 1; DO1 := DI2;'''.newSTAlgorithm("REQ2")
		val init = newState("INIT")
		val state = newState("STATE", newAction(alg, outputEvent))
		val state2 = newState("STATE2", newAction(alg2, outputEvent))
		val ecc = newECC(#[init, state, state2],
			#[newTransition(init, state, inputEvent, "DI1 > 0"), newTransition(state, state2, null, null),
				newTransition(state2, state, null, "DI1 > 0")])
		21.toIntValue.assertTrace(outputEvent.repeatEvent(17 * 2), #[state, state2].repeatStates(17),
			evaluateBasicFB(ecc, #[inputEvent], #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
				"DO1".newVarDeclaration(ElementaryTypes.INT, false)))
	}

	def static evaluateBasicFB(ECC ecc, Iterable<Event> inputEvents, Iterable<Variable> variables,
		VarDeclaration output) {
		val fbType = LibraryElementFactory.eINSTANCE.createBasicFBType
		fbType.name = "Test"
		fbType.interfaceList = newInterfaceList(ecc.containedEvents, variables.filter[type instanceof DataType].map [
			newVarDeclaration(name, type as DataType, true)
		] + #[output])
		fbType.ECC = ecc
		fbType.callables.addAll(ecc.containedCallables)
		val queue = new ArrayBlockingQueue(1000)
		val eval = new TracingBasicFBEvaluator(fbType, null, variables, queue, null)
		queue.addAll(inputEvents)
		inputEvents.forEach[eval.evaluate]
		return eval
	}

	def static void assertTrace(Object expectedResult, Iterable<? extends Event> expectedEvents,
		Iterable<? extends ECState> expectedTrace, TracingBasicFBEvaluator actual) {
		expectedResult.assertEquals(actual.variables.get("DO1").value)
		expectedEvents.assertIterableEquals(actual.queue)
		expectedTrace.assertIterableEquals(actual.trace)
	}

	def static Iterable<Event> repeatEvent(Event event, int repeat) {
		(0 ..< repeat).map[event]
	}

	def static Iterable<ECState> repeatState(ECState state, int repeat) {
		(0 ..< repeat).map[state]
	}

	def static Iterable<Event> repeatEvents(Iterable<Event> event, int repeat) {
		(0 ..< repeat).map[event].flatten
	}

	def static Iterable<ECState> repeatStates(Iterable<ECState> state, int repeat) {
		(0 ..< repeat).map[state].flatten
	}

	def static newECC(Iterable<ECState> states, Iterable<ECTransition> transitions) {
		LibraryElementFactory.eINSTANCE.createECC => [
			ECState.addAll(states)
			ECTransition.addAll(transitions)
			start = ECState.head
		]
	}

	def static newTransition(ECState src, ECState dest, Event event, String expression) {
		LibraryElementFactory.eINSTANCE.createECTransition => [
			source = src
			destination = dest
			conditionEvent = event
			conditionExpression = expression
		]
	}

	def static newState(String stateName, ECAction... actions) {
		newState(stateName, List.of(actions))
	}

	def static newState(String stateName, Iterable<ECAction> actions) {
		LibraryElementFactory.eINSTANCE.createECState => [
			name = stateName
			ECAction.addAll(actions)
		]
	}

	def static newAction(Algorithm actionAlgorithm, Event actionEvent) {
		LibraryElementFactory.eINSTANCE.createECAction => [
			algorithm = actionAlgorithm
			output = actionEvent
		]
	}

	def static Set<? extends ICallable> getContainedCallables(ECC ecc) {
		ecc.ECState.flatMap[ECAction].map[algorithm].filterNull.toSet
	}

	def static Set<Event> getContainedEvents(ECC ecc) {
		(ecc.ECState.flatMap[ECAction].map[output] + ecc.ECTransition.map[conditionEvent]).filterNull.toSet
	}

	static class TracingBasicFBEvaluator extends BasicFBEvaluator {
		@Accessors final Queue<ECState> trace = new ArrayBlockingQueue(1000)

		new(BasicFBType type, Variable context, Iterable<Variable> variables, Queue<Event> queue, Evaluator parent) {
			super(type, context, variables, queue, parent)
		}

		override protected <T> T trap(T context) {
			if (context instanceof ECState) {
				trace.add(context)
			}
			return super.trap(context)
		}
	}
}
