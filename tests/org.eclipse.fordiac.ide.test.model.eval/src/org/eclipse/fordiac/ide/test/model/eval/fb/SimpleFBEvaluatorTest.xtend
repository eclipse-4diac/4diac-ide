/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
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

import org.eclipse.emf.ecore.resource.impl.ResourceImpl
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.fb.SimpleFBEvaluator
import org.eclipse.fordiac.ide.model.eval.value.StructValue
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.test.model.typelibrary.AttributeTypeEntryMock
import org.eclipse.fordiac.ide.test.model.typelibrary.DataTypeEntryMock
import org.eclipse.fordiac.ide.test.model.typelibrary.FBTypeEntryMock
import org.junit.jupiter.api.Test

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.value.BoolValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WordValue.*
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
	def void testSimpleFBReset() {
		val evaluator = #[
			'''DO1 := DI1 + DI2;'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false))
		21.toIntValue.assertEquals(evaluator.variables.get("DO1").value)
		evaluator.reset(emptySet)
		0.toIntValue.assertEquals(evaluator.variables.get("DO1").value)
	}

	@Test
	def void testSimpleFBInternalVar() {
		42.toIntValue.assertEquals(#[
			'''DO1 := (DI1 + DI2) * INTERNALVAR1;'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false),
			#["INTERNALVAR1".newVarDeclaration(ElementaryTypes.INT, false, "2")], emptyList, emptyList).variables.get(
			"DO1").value)
	}

	@Test
	def void testSimpleFBInternalConst() {
		42.toIntValue.assertEquals(#[
			'''DO1 := (DI1 + DI2) * INTERNALCONST1;'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false), emptyList,
			#["INTERNALCONST1".newVarDeclaration(ElementaryTypes.INT, false, "2")], emptyList).variables.get("DO1").
			value)
	}

	@Test
	def void testMethodCall() {
		21.toIntValue.assertEquals(#[
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
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
	def void testMethodCallWithNotBool() {
		true.toBoolValue.assertEquals(#[
			'''THIS.TEST_METHOD(A := DI1, B := DI2, NOT C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: BOOL;
					B: BOOL;
				END_VAR
				VAR_OUTPUT
					C: BOOL;
				END_VAR
				C := A AND B;
				END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[false.toBoolValue.newVariable("DI1"), true.toBoolValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.BOOL, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallWithNotWord() {
		4.bitwiseNot.toWordValue.assertEquals(#[
			'''THIS.TEST_METHOD(A := DI1, B := DI2, NOT C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: WORD;
					B: WORD;
				END_VAR
				VAR_OUTPUT
					C: WORD;
				END_VAR
				C := A AND B;
				END_METHOD
			'''.newSTMethod("TEST_METHOD")
		].evaluateSimpleFB("REQ", #[21.toWordValue.newVariable("DI1"), 4.toWordValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.WORD, false)).variables.get("DO1").value)
	}

	@Test
	def void testMethodCallVariableAccess() {
		21.toIntValue.assertEquals(#[
			'''THIS.TEST_METHOD(A := DI1, B := DI2);'''.newSTAlgorithm("REQ"),
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
			'''THIS.TEST_METHOD(A := DI1, C => DO1);'''.newSTAlgorithm("REQ"),
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
			'''THIS.TEST_METHOD(A := DI1, C => DO1);'''.newSTAlgorithm("REQ"),
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
			'''THIS.TEST_METHOD(DI1, DI2, DO1);'''.newSTAlgorithm("REQ"),
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
			'''DO1 := THIS.TEST_METHOD(DI1, DI2);'''.newSTAlgorithm("REQ"),
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
			'''DO1 := THIS.TEST_METHOD();'''.newSTAlgorithm("REQ"),
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
			'''
			VAR_TEMP
				tempVar : INT;
			END_VAR
			tempVar := DI1;
			THIS.TEST_METHOD(X := tempVar, A := DI2, O => DO1); DO1 := DO1 + tempVar;'''.newSTAlgorithm("REQ"),
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
			'''
			VAR_TEMP
				tempVar : INT;
			END_VAR
			tempVar := DI1;
			THIS.TEST_METHOD(DI2, tempVar, DO1); DO1 := DO1 + tempVar;'''.newSTAlgorithm("REQ"),
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				VAR_OUTPUT
					C: INT;
				END_VAR
				THIS.TEST_METHOD2(A := A, B := B, C => C);
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				THIS.TEST_METHOD2(A := A, B := B);
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
			'''DO1 := THIS.TEST_METHOD(DI1, DI2);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD : INT
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				THIS.TEST_METHOD2(A := A, B := B, C => TEST_METHOD);
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				VAR_OUTPUT
					C: INT;
				END_VAR
				THIS.TEST_METHOD2(A := A, C => C);
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				VAR_OUTPUT
					C: INT;
				END_VAR
				THIS.TEST_METHOD2(A := A, C => C);
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				VAR_OUTPUT
					C: INT;
				END_VAR
				THIS.TEST_METHOD2(A, B, C);
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				VAR_OUTPUT
					C: INT;
				END_VAR
				C := THIS.TEST_METHOD2(A, B);
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
			'''THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
					B: INT;
				END_VAR
				VAR_OUTPUT
					C: INT;
				END_VAR
				C := THIS.TEST_METHOD2();
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
			'''
				ALGORITHM REQ
				VAR_TEMP
					tempVar : INT;
				END_VAR
				tempVar := DI1;
				THIS.TEST_METHOD(X := tempVar, A := DI2, O => DO1); DO1 := DO1 + tempVar;
				END_ALGORITHM
			'''.newSTAlgorithm("REQ"),
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
				THIS.TEST_METHOD2(A := A, X := X, O => O);
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
			'''
			VAR_TEMP
				tempVar : INT;
			END_VAR
			tempVar := DI1;
			THIS.TEST_METHOD(DI2, tempVar); DO1 := tempVar;'''.newSTAlgorithm("REQ"),
			'''
				METHOD TEST_METHOD
				VAR_INPUT
					A: INT;
				END_VAR
				VAR_IN_OUT
					X: INT;
				END_VAR
				THIS.TEST_METHOD2(A, X);
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
	def void testSimpleWithLegacyArray() {
		val inputVarDecl = newVarDeclaration("DI1", ElementaryTypes.INT, true, "[1,2,3,4,5,6]")
		ArraySizeHelper.setArraySize(inputVarDecl, "6")
		val inputVar = newVariable(inputVarDecl)
		val outputVarDecl = newVarDeclaration("DO1", ElementaryTypes.INT, false)
		21.toIntValue.assertEquals(#[
			'''
				DO1 := DI1[0] + DI1[1] + DI1[2] + DI1[3] + DI1[4] + DI1[5];
			'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[inputVar], outputVarDecl).variables.get("DO1").value)
	}

	@Test
	def void testSimpleWithSimpleArray() {
		val inputVarDecl = newVarDeclaration("DI1", ElementaryTypes.INT, true, "[1,2,3,4,5,6]")
		ArraySizeHelper.setArraySize(inputVarDecl, "0..5")
		val inputVar = newVariable(inputVarDecl)
		val outputVarDecl = newVarDeclaration("DO1", ElementaryTypes.INT, false)
		21.toIntValue.assertEquals(#[
			'''
				DO1 := DI1[0] + DI1[1] + DI1[2] + DI1[3] + DI1[4] + DI1[5];
			'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[inputVar], outputVarDecl).variables.get("DO1").value)
	}

	@Test
	def void testSimpleWithComplexArray() {
		val inputVarDecl = newVarDeclaration("DI1", ElementaryTypes.INT, true, "[1,2,3,4,5,6]")
		ArraySizeHelper.setArraySize(inputVarDecl, "0..21-17+2")
		val inputVar = newVariable(inputVarDecl)
		val outputVarDecl = newVarDeclaration("DO1", ElementaryTypes.INT, false)
		21.toIntValue.assertEquals(#[
			'''
				DO1 := DI1[0] + DI1[1] + DI1[2] + DI1[3] + DI1[4] + DI1[5];
			'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[inputVar], outputVarDecl).variables.get("DO1").value)
	}

	@Test
	def void testSimpleWithConstantArray() {
		val internalConstVarDecl = newVarDeclaration("MAXLEN", ElementaryTypes.DINT, false, "6");
		val inputVarDecl = newVarDeclaration("DI1", ElementaryTypes.INT, true, "[1,2,3,4,5,6]")
		ArraySizeHelper.setArraySize(inputVarDecl, "0..MAXLEN-1")
		val outputVarDecl = newVarDeclaration("DO1", ElementaryTypes.INT, false)
		21.toIntValue.assertEquals(#[
			'''
				DO1 := DI1[0] + DI1[1] + DI1[2] + DI1[3] + DI1[4] + DI1[5];
			'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[inputVarDecl, outputVarDecl], emptyList, #[internalConstVarDecl], emptyList).
			variables.get("DO1").value)
	}

	@Test
	def void testSimpleWithStruct() {
		val structType = DataFactory.eINSTANCE.createStructuredType => [
			name = "TestStruct"
			memberVariables += newVarDeclaration("a", ElementaryTypes.INT, false)
			memberVariables += newVarDeclaration("b", ElementaryTypes.INT, false)
		]
		typeLib.addTypeEntry(new DataTypeEntryMock(structType, typeLib, null))
		val structResource = new ResourceImpl
		structResource.contents.add(structType)
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

	@Test
	def void testStructAttribute() {
		val structType = DataFactory.eINSTANCE.createStructuredType => [
			name = "TestStructAttribute"
			memberVariables += newVarDeclaration("a", ElementaryTypes.INT, false, "17")
			memberVariables += newVarDeclaration("b", ElementaryTypes.INT, false, "4")
		]
		val attributeDeclaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration => [
			name = structType.name
			type = structType
		]
		typeLib.addTypeEntry(new AttributeTypeEntryMock(attributeDeclaration, typeLib, null))
		val attributeDeclarationResource = new ResourceImpl
		attributeDeclarationResource.contents.add(attributeDeclaration)
		val attr1 = newAttribute(attributeDeclaration)
		#[17.toIntValue, 4.toIntValue].assertIterableEquals(newVariable(attr1).value as StructValue)
		val attr2 = newAttribute(attributeDeclaration, "(a:=21, b:=42)")
		#[21.toIntValue, 42.toIntValue].assertIterableEquals(newVariable(attr2).value as StructValue)
	}

	@Test
	def void testDirectlyDerivedAttribute() {
		val derivedType = DataFactory.eINSTANCE.createDirectlyDerivedType => [
			name = "TestDerivedAttribute"
			baseType = ElementaryTypes.INT
			initialValue = "17"
		]
		val attributeDeclaration = LibraryElementFactory.eINSTANCE.createAttributeDeclaration => [
			name = derivedType.name
			type = derivedType
		]
		typeLib.addTypeEntry(new AttributeTypeEntryMock(attributeDeclaration, typeLib, null))
		val attributeDeclarationResource = new ResourceImpl
		attributeDeclarationResource.contents.add(attributeDeclaration)
		val attr1 = newAttribute(attributeDeclaration)
		17.toIntValue.assertEquals(newVariable(attr1).value)
		val attr2 = newAttribute(attributeDeclaration, "4")
		4.toIntValue.assertEquals(newVariable(attr2).value)
	}

	@Test
	def void testSimpleFBCall() {
		val internalFB = newFB("FB1", newTestSimpleFBType)
		42.toIntValue.assertEquals(#[
			'''
				FB1(DI1 := DI1);
				FB1(DI1 := DI2);
				FB1.REQ(DI1 := FB1.DO1, DO1 => DO1);
			'''.newSTAlgorithm("REQ")
		].evaluateSimpleFB("REQ", #[17.toIntValue.newVariable("DI1"), 4.toIntValue.newVariable("DI2")],
			"DO1".newVarDeclaration(ElementaryTypes.INT, false), emptyList, emptyList, #[internalFB]).variables.get(
			"DO1").value)
	}

	def FBType newTestSimpleFBType() {
		val inputEvent = newEvent("REQ", true)
		val outputEvent = newEvent("CNF", false)
		val simpleType = LibraryElementFactory.eINSTANCE.createSimpleFBType => [
			name = "TestSimple"
			interfaceList = newInterfaceList(#[inputEvent, outputEvent], #[
				newVarDeclaration("DI1", ElementaryTypes.INT, true),
				newVarDeclaration("DO1", ElementaryTypes.INT, false)
			])
			callables += '''DO1 := DO1 + DI1;'''.newSTAlgorithm("REQ")
		]
		val typeEntry = new FBTypeEntryMock(simpleType, typeLib, null)
		simpleType.typeEntry = typeEntry
		typeLib.addTypeEntry(typeEntry)
		(new ResourceImpl).contents.add(simpleType)
		return simpleType
	}

	def static evaluateSimpleFB(Iterable<? extends ICallable> callables, String inputEventName,
		Iterable<Variable<?>> variables, VarDeclaration output) {
		evaluateSimpleFB(callables, inputEventName, variables, output, emptyList, emptyList, emptyList)
	}

	def static evaluateSimpleFB(Iterable<? extends ICallable> callables, String inputEventName,
		Iterable<Variable<?>> variables, VarDeclaration output, Iterable<VarDeclaration> internalVars,
		Iterable<VarDeclaration> internalConstVars, Iterable<FB> internalFBs) {
		val inputEvent = inputEventName.newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType
		fbType.name = "Test"
		fbType.interfaceList = newInterfaceList(#[inputEvent, outputEvent], variables.map [
			newVarDeclaration(name, type as DataType, true)
		] + #[output])
		fbType.callables.addAll(callables)
		fbType.internalVars.addAll(internalVars)
		fbType.internalConstVars.addAll(internalConstVars)
		fbType.internalFbs.addAll(internalFBs)
		val queue = new TracingFBEvaluatorEventQueue(#[inputEvent])
		val eval = new SimpleFBEvaluator(fbType, null, variables, null)
		eval.eventQueue = queue
		eval.evaluate
		#[outputEvent].assertIterableEquals(queue.outputEvents)
		return eval
	}

	def static evaluateSimpleFB(Iterable<? extends ICallable> callables, String inputEventName,
		Iterable<VarDeclaration> variables, Iterable<VarDeclaration> internalVars,
		Iterable<VarDeclaration> internalConstVars, Iterable<FB> internalFBs) {
		val inputEvent = inputEventName.newEvent(true)
		val outputEvent = "CNF".newEvent(false)
		val fbType = LibraryElementFactory.eINSTANCE.createSimpleFBType
		fbType.name = "Test"
		fbType.interfaceList = newInterfaceList(#[inputEvent, outputEvent], variables)
		fbType.callables.addAll(callables)
		fbType.internalVars.addAll(internalVars)
		fbType.internalConstVars.addAll(internalConstVars)
		fbType.internalFbs.addAll(internalFBs)
		val queue = new TracingFBEvaluatorEventQueue(#[inputEvent])
		val eval = new SimpleFBEvaluator(fbType, null, emptyList, null)
		eval.eventQueue = queue
		eval.evaluate
		#[outputEvent].assertIterableEquals(queue.outputEvents)
		return eval
	}
}
