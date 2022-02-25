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

import java.util.concurrent.ArrayBlockingQueue
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.fb.SimpleFBEvaluator
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.junit.jupiter.api.Test

import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.junit.jupiter.api.Assertions.*

class SimpleFBEvaluatorTest extends FBEvaluatorTest {

	@Test
	def void testSimpleFB() {
		21.toIntValue.assertEquals(#[
			'''DO1 := DI1 + DI2;'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testSimpleFBMultiAlgorithm() {
		21.toIntValue.assertEquals(#[
			'''DO1 := DI1 - DI2;'''.newSTAlgorithm("REQ1"),
			'''DO1 := DI1 + DI2;'''.newSTAlgorithm("REQ2")
		].evaluateSimpleFB("REQ2", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	def static evaluateSimpleFB(Iterable<STAlgorithm> algorithm, String inputEventName, Iterable<Variable> variables,
		VarDeclaration output) {
		val inputEvent = inputEventName.newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType
		fbType.name = "Test"
		fbType.interfaceList = newInterfaceList(#[inputEvent, outputEvent], variables.map [
			newVarDeclaration(name, type, true)
		] + #[output])
		fbType.callables.addAll(algorithm)
		val queue = new ArrayBlockingQueue(1000)
		val eval = new SimpleFBEvaluator(fbType, queue, variables, null)
		queue.add(inputEvent)
		eval.evaluate
		#[outputEvent].assertIterableEquals(queue)
		return eval
	}
}
