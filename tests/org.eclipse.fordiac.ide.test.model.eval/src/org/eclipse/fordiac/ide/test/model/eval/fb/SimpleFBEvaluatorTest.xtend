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
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.fb.SimpleFBEvaluator
import org.eclipse.fordiac.ide.model.eval.value.StructValue
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.junit.jupiter.api.Test

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

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

	@Test
	def void testMethodCall() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallVariableAccess() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			DO1 := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallWithDefaultInput() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallWithDefaultOutput() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			VAR_OUTPUT
				C: INT := 21;
			END_VAR
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallNonFormal() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(DI1, DI2, DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallWithReturnValue() {
		21.toIntValue.assertEquals(#[
			'''DO1 := TEST_METHOD(DI1, DI2);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD : INT
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			TEST_METHOD := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallWithReturnValueAndDefaults() {
		21.toIntValue.assertEquals(#[
			'''DO1 := TEST_METHOD();'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD : INT
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			TEST_METHOD := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallWithInOut() {
		42.toIntValue.assertEquals(#[
			'''TEST_METHOD(X := DI1, A := DI2, O => DO1); DO1 := DO1 + DI1;'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
			END_VAR
			VAR_OUTPUT
				O: INT := 21;
			END_VAR
			VAR_IN_OUT
				X: INT;
			END_VAR
			X := X + A;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallNonFormalWithInOut() {
		42.toIntValue.assertEquals(#[
			'''TEST_METHOD(DI2, DI1, DO1); DO1 := DO1 + DI1;'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
			END_VAR
			VAR_OUTPUT
				O: INT := 21;
			END_VAR
			VAR_IN_OUT
				X: INT;
			END_VAR
			X := X + A;
			END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCall() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			TEST_METHOD2(A := A, B := B, C => C);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallVariableAccess() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			TEST_METHOD2(A := A, B := B);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			DO1 := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallForwardReturn() {
		21.toIntValue.assertEquals(#[
			'''DO1 := TEST_METHOD(DI1, DI2);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD : INT
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			TEST_METHOD2(A := A, B := B, C => TEST_METHOD);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallWithDefaultInput() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			TEST_METHOD2(A := A, C => C);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallWithDefaultOutput() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			TEST_METHOD2(A := A, C => C);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			VAR_OUTPUT
				C: INT := 21;
			END_VAR
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallNonFormal() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			TEST_METHOD2(A, B, C);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallWithReturnValue() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := TEST_METHOD2(A, B);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2 : INT
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			TEST_METHOD2 := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallWithReturnValueAndDefaults() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := TEST_METHOD2();
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2 : INT
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			TEST_METHOD2 := A + B;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallWithInOut() {
		42.toIntValue.assertEquals(#[
			'''TEST_METHOD(X := DI1, A := DI2, O => DO1); DO1 := DO1 + DI1;'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
			END_VAR
			VAR_OUTPUT
				O: INT;
			END_VAR
			VAR_IN_OUT
				X: INT;
			END_VAR
			TEST_METHOD2(A := A, X := X, O => O);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT;
			END_VAR
			VAR_OUTPUT
				O: INT := 21;
			END_VAR
			VAR_IN_OUT
				X: INT;
			END_VAR
			X := X + A;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethod2MethodCallNonFormalWithInOut() {
		21.toIntValue.assertEquals(#[
			'''TEST_METHOD(DI2, DI1); DO1 := DI1;'''.newSTAlgorithm("REQ"),
			'''
			METHOD TEST_METHOD
			VAR_INPUT
				A: INT;
			END_VAR
			VAR_IN_OUT
				X: INT;
			END_VAR
			TEST_METHOD2(A, X);
			END_METHOD
			'''.newSTMethod("TEST_METHOD"),
			'''
			METHOD TEST_METHOD2
			VAR_INPUT
				A: INT;
			END_VAR
			VAR_IN_OUT
				X: INT;
			END_VAR
			X := X + A;
			END_METHOD
			'''.newSTMethod("TEST_METHOD2")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false)).variables.get("DO1").value)
	}

	@Test
	def void testSimpleWithStruct() {
		val structType = DataFactory.eINSTANCE.createStructuredType => [
			name = "TestStruct"
			memberVariables += newVarDeclaration("a", ElementaryTypes.INT, false)
			memberVariables += newVarDeclaration("b", ElementaryTypes.INT, false)
		]
		val inputVarDecl = newVarDeclaration("DI1", structType, true)
		val inputVar = newVariable(inputVarDecl) as StructVariable
		inputVar.members.get("a").value = 17.toIntValue
		inputVar.members.get("b").value = 4.toIntValue
		val outputVarDecl = newVarDeclaration("DO1", structType, false)
		#[21.toIntValue, 42.toIntValue].assertIterableEquals(#[
			'''
				DO1.a := DI1.a + DI1.b;
				DO1.b := 42;
			'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[inputVar], outputVarDecl).variables.get("DO1").value as StructValue)
	}

	def static evaluateSimpleFB(Iterable<? extends ICallable> callables, String inputEventName, Iterable<Variable> variables,
		VarDeclaration output) {
		val inputEvent = inputEventName.newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType
		fbType.name = "Test"
		fbType.interfaceList = newInterfaceList(#[inputEvent, outputEvent], variables.map [
			newVarDeclaration(name, type as DataType, true)
		] + #[output])
		fbType.callables.addAll(callables)
		val queue = new ArrayBlockingQueue(1000)
		val eval = new SimpleFBEvaluator(fbType, null, variables, queue, null)
		queue.add(inputEvent)
		eval.evaluate
		#[outputEvent].assertIterableEquals(queue)
		return eval
	}
}
