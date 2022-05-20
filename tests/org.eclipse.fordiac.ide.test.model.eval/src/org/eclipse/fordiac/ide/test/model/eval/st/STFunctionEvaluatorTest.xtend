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
package org.eclipse.fordiac.ide.test.model.eval.st

import org.eclipse.fordiac.ide.model.eval.st.STFunctionEvaluator
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil.*
import static extension org.junit.jupiter.api.Assertions.*

class STFunctionEvaluatorTest {
	@BeforeAll
	def static void setupXtext() {
		new DataTypeLibrary
		STFunctionStandaloneSetup.doSetup
		StructuredTextEvaluatorFactory.register
	}

	@Test
	def void testEmpty() {
		'''
			FUNCTION TEST
			END_FUNCTION
		'''.evaluateFunction(emptyList)
	}

	@Test
	def void testReturn() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST := 17 + 4;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallFormal() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST_CALL(A := 17, B := 4, C => TEST);
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallNonFormal() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST_CALL(17, 4, TEST);
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallFormalDefaultInput() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST_CALL(A := 17, C => TEST);
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			VAR_OUTPUT
				C: INT;
			END_VAR
			C := A + B;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallFormalDefaultOutput() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST_CALL(A := 17, C => TEST);
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			VAR_OUTPUT
				C: INT := 21;
			END_VAR
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallReturn() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST := TEST_CALL(17, 4);
			END_FUNCTION
			
			FUNCTION TEST_CALL : INT
			VAR_INPUT
				A: INT;
				B: INT;
			END_VAR
			TEST_CALL := A + B;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallReturnDefaults() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST := TEST_CALL();
			END_FUNCTION
			
			FUNCTION TEST_CALL : INT
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			TEST_CALL := A + B;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallRecursive() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			TEST := TEST_CALL();
			END_FUNCTION
			
			FUNCTION TEST_CALL : INT
			VAR_INPUT
				A: INT := 17;
				B: INT := 4;
			END_VAR
			IF B > 0 THEN
				TEST_CALL := TEST_CALL(A + 1, B - 1);
			ELSE
				TEST_CALL := A;
			END_IF;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallInOutParam() {
		21.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			VAR_TEMP
				X: INT := 17;
			END_VAR
			TEST_CALL(4, X);
			TEST := X;
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_IN_OUT
				X: INT;
			END_VAR
			VAR_INPUT
				A: INT := 4;
			END_VAR
			X := X + A;
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallArray() {
		42.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			VAR_TEMP
				X: ARRAY [0..2] OF INT := [17, 4, 21];
			END_VAR
			TEST := TEST_CALL(X);
			END_FUNCTION
			
			FUNCTION TEST_CALL : INT
			VAR_INPUT
				X: ARRAY [0..2] OF INT;
			END_VAR
			TEST_CALL := ADD(X[0], X[1], X[2]);
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallArrayOut() {
		42.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			VAR_TEMP
				X: ARRAY [0..2] OF INT := [17, 4, 21];
				Y: ARRAY [0..2] OF INT;
			END_VAR
			TEST_CALL(X, Y);
			TEST := ADD(X[0], X[1], X[2]);
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_INPUT
				X: ARRAY [0..2] OF INT;
			END_VAR
			VAR_OUTPUT
				Y: ARRAY [0..2] OF INT;
			END_VAR
			Y[0] := X[2];
			Y[1] := X[1];
			Y[2] := X[0];
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	@Test
	def void testCallArrayVariable() {
		42.toIntValue.assertEquals('''
			FUNCTION TEST : INT
			VAR_TEMP
				X: ARRAY [0..2] OF INT := [17, 4, 21];
				Y: ARRAY [0..2] OF INT;
			END_VAR
			TEST_CALL(X, Y);
			TEST := ADD(X[0], X[1], X[2]);
			END_FUNCTION
			
			FUNCTION TEST_CALL
			VAR_INPUT
				X: ARRAY [*] OF INT;
			END_VAR
			VAR_OUTPUT
				Y: ARRAY [0..2] OF INT;
			END_VAR
			Y[0] := X[2];
			Y[1] := X[1];
			Y[2] := X[0];
			END_FUNCTION
		'''.evaluateFunction(emptyList))
	}

	def static evaluateFunction(CharSequence text, Iterable<Variable<?>> variables) {
		val errors = newArrayList
		val source = text.toString.parse("anonymous", errors, null, null)
		source.assertNotNull("Parse error: " + errors.join(", "))
		source.functions.head.assertNotNull("Must be at least one function")
		val eval = new STFunctionEvaluator(source.functions.head, null, variables, null)
		return eval.evaluate
	}
}
