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

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.SimpleFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.StructValue;
import org.eclipse.fordiac.ide.model.eval.value.WordValue;
import org.eclipse.fordiac.ide.model.eval.variable.StructVariable;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.ICallable;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleECState;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({ "static-method", "squid:S5960", "nls" })
class SimpleFBEvaluatorTest extends AbstractFBEvaluatorTest {
	@Test
	void testSimpleFB() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := DI1 + DI2;", "REQ")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testSimpleFBMultiAlgorithm() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("DO1 := DI1 - DI2;", "INIT"),
								newSTAlgorithm("DO1 := DI1 + DI2;", "REQ")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testSimpleFBMultiState() throws EvaluatorException, InterruptedException {
		final SimpleFBType fbType = newSimpleFBType("Test",
				List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true, "17"),
						newVarDeclaration("DI2", ElementaryTypes.DINT, true, "4"),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
		final Event initInputEvent = newEvent("INIT", true);
		final Event initOutputEvent = newEvent("INITO", true);
		fbType.getInterfaceList().getEventInputs().add(initInputEvent);
		fbType.getInterfaceList().getEventOutputs().add(initOutputEvent);
		final SimpleECState initState = newSimpleECState(initInputEvent);
		initState.getSimpleECActions().add(newSimpleECAction("INIT", initOutputEvent));
		fbType.getSimpleECStates().add(initState);
		fbType.getCallables().add(newSTAlgorithm("DO1 := DI1 - DI2;", "REQ"));
		fbType.getCallables().add(newSTAlgorithm("DO1 := DI1 + DI2;", "INIT"));
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(List.of(initInputEvent));
		final SimpleFBEvaluator eval = new SimpleFBEvaluator(fbType, null, List.of(), null);
		eval.setEventQueue(queue);
		eval.evaluate();
		assertIterableEquals(List.of(initOutputEvent), queue.getOutputEvents());
		assertEquals(toDIntValue(21), eval.getVariables().get("DO1").getValue());
	}

	@Test
	void testSimpleFBReset() throws EvaluatorException, InterruptedException {
		final SimpleFBEvaluator evaluator = evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := DI1 + DI2;", "REQ")),
				List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.DINT, false));
		assertEquals(toDIntValue(21), evaluator.getVariables().get("DO1").getValue());
		evaluator.reset(List.of());
		assertEquals(toDIntValue(0), evaluator.getVariables().get("DO1").getValue());
	}

	@Test
	void testSimpleFBInternalVar() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(42),
				evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := (DI1 + DI2) * INTERNALVAR1;", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false),
						List.of(newVarDeclaration("INTERNALVAR1", ElementaryTypes.DINT, false, "2")), List.of(),
						List.of()).getVariables().get("DO1").getValue());
	}

	@Test
	void testSimpleFBInternalConst() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(42),
				evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := (DI1 + DI2) * INTERNALCONST1;", "REQ")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(),
						List.of(newVarDeclaration("INTERNALCONST1", ElementaryTypes.DINT, false, "2")), List.of())
						.getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCall() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										C := A + B;
										END_METHOD
										""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithNotBool() throws EvaluatorException, InterruptedException {
		assertEquals(BoolValue.toBoolValue(true), evaluateSimpleFB(
				List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, NOT C => DO1);", "REQ"), newSTMethod("""
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
						""", "TEST_METHOD")),
				List.of(newVariable(BoolValue.toBoolValue(false), "DI1"),
						newVariable(BoolValue.toBoolValue(true), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.BOOL, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithNotWord() throws EvaluatorException, InterruptedException {
		assertEquals(WordValue.toWordValue(Integer.valueOf((~4))), evaluateSimpleFB(
				List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, NOT C => DO1);", "REQ"), newSTMethod("""
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
						""", "TEST_METHOD")),
				List.of(newVariable(WordValue.toWordValue(Integer.valueOf(21)), "DI1"),
						newVariable(WordValue.toWordValue(Integer.valueOf(4)), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.WORD, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallVariableAccess() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						DO1 := A + B;
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallThisVariableAccess() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						THIS.DO1 := A + B;
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithDefaultInput() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, C => DO1);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD
						VAR_INPUT
							A: DINT := 17;
							B: DINT := 4;
						END_VAR
						VAR_OUTPUT
							C: DINT;
						END_VAR
						C := A + B;
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithDefaultOutput() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, C => DO1);", "REQ"), newSTMethod("""
										METHOD TEST_METHOD
						VAR_INPUT
							A: DINT := 17;
							B: DINT := 4;
						END_VAR
						VAR_OUTPUT
							C: DINT := 21;
						END_VAR
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallNonFormal() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("THIS.TEST_METHOD(DI1, DI2, DO1);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						VAR_OUTPUT
							C: DINT;
						END_VAR
						C := A + B;
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithReturnValue() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := THIS.TEST_METHOD(DI1, DI2);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD : DINT
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						TEST_METHOD := A + B;
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithReturnValueAndDefaults() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := THIS.TEST_METHOD();", "REQ"), newSTMethod("""
						METHOD TEST_METHOD : DINT
						VAR_INPUT
							A: DINT := 17;
							B: DINT := 4;
						END_VAR
						TEST_METHOD := A + B;
						END_METHOD
						""", "TEST_METHOD")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallWithInOut() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(42), evaluateSimpleFB(List.of(newSTAlgorithm("""
				VAR_TEMP
					tempVar : DINT;
				END_VAR
				tempVar := DI1;
				THIS.TEST_METHOD(X := tempVar, A := DI2, O => DO1); DO1 := DO1 + tempVar;
				""", "REQ"), newSTMethod("""
				METHOD TEST_METHOD
				VAR_INPUT
					A: DINT;
				END_VAR
				VAR_OUTPUT
					O: DINT := 21;
				END_VAR
				VAR_IN_OUT
					X: DINT;
				END_VAR
				X := X + A;
				END_METHOD
				""", "TEST_METHOD")), List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethodCallNonFormalWithInOut() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(42), evaluateSimpleFB(List.of(newSTAlgorithm("""
				VAR_TEMP
					tempVar : DINT;
				END_VAR
				tempVar := DI1;
				THIS.TEST_METHOD(DI2, tempVar, DO1); DO1 := DO1 + tempVar;
				""", "REQ"), newSTMethod("""
				METHOD TEST_METHOD
				VAR_INPUT
					A: DINT;
				END_VAR
				VAR_OUTPUT
					O: DINT := 21;
				END_VAR
				VAR_IN_OUT
					X: DINT;
				END_VAR
				X := X + A;
				END_METHOD
				""", "TEST_METHOD")), List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCall() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										THIS.TEST_METHOD2(A := A, B := B, C => C);
										END_METHOD
										""", "TEST_METHOD"), newSTMethod("""
										METHOD TEST_METHOD2
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										C := A + B;
										END_METHOD
										""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallVariableAccess() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						THIS.TEST_METHOD2(A := A, B := B);
						END_METHOD
						""", "TEST_METHOD"), newSTMethod("""
						METHOD TEST_METHOD2
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						DO1 := A + B;
						END_METHOD
						""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallForwardReturn() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("DO1 := THIS.TEST_METHOD(DI1, DI2);", "REQ"), newSTMethod("""
						METHOD TEST_METHOD : DINT
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						THIS.TEST_METHOD2(A := A, B := B, C => TEST_METHOD);
						END_METHOD
						""", "TEST_METHOD"), newSTMethod("""
						METHOD TEST_METHOD2
						VAR_INPUT
							A: DINT;
							B: DINT;
						END_VAR
						VAR_OUTPUT
							C: DINT;
						END_VAR
						C := A + B;
						END_METHOD
						""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallWithDefaultInput() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										THIS.TEST_METHOD2(A := A, C => C);
										END_METHOD
										""", "TEST_METHOD"), newSTMethod("""
										METHOD TEST_METHOD2
										VAR_INPUT
											A: DINT := 17;
											B: DINT := 4;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										C := A + B;
										END_METHOD
										""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallWithDefaultOutput() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										THIS.TEST_METHOD2(A := A, C => C);
										END_METHOD
										""", "TEST_METHOD"), newSTMethod("""
										METHOD TEST_METHOD2
										VAR_INPUT
											A: DINT := 17;
											B: DINT := 4;
										END_VAR
										VAR_OUTPUT
											C: DINT := 21;
										END_VAR
										END_METHOD
										""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallNonFormal() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										THIS.TEST_METHOD2(A, B, C);
										END_METHOD
										""", "TEST_METHOD"), newSTMethod("""
										METHOD TEST_METHOD2
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										C := A + B;
										END_METHOD
										""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallWithReturnValue() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										C := THIS.TEST_METHOD2(A, B);
										END_METHOD
										""", "TEST_METHOD"), newSTMethod("""
										METHOD TEST_METHOD2 : DINT
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										TEST_METHOD2 := A + B;
										END_METHOD
										""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallWithReturnValueAndDefaults() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("THIS.TEST_METHOD(A := DI1, B := DI2, C => DO1);", "REQ"),
								newSTMethod("""
										METHOD TEST_METHOD
										VAR_INPUT
											A: DINT;
											B: DINT;
										END_VAR
										VAR_OUTPUT
											C: DINT;
										END_VAR
										C := THIS.TEST_METHOD2();
										END_METHOD
										""", "TEST_METHOD"), newSTMethod("""
										METHOD TEST_METHOD2 : DINT
										VAR_INPUT
											A: DINT := 17;
											B: DINT := 4;
										END_VAR
										TEST_METHOD2 := A + B;
										END_METHOD
										""", "TEST_METHOD2")),
						List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallWithInOut() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(42), evaluateSimpleFB(List.of(newSTAlgorithm("""
				ALGORITHM REQ
				VAR_TEMP
					tempVar : DINT;
				END_VAR
				tempVar := DI1;
				THIS.TEST_METHOD(X := tempVar, A := DI2, O => DO1); DO1 := DO1 + tempVar;
				END_ALGORITHM
				""", "REQ"), newSTMethod("""
				METHOD TEST_METHOD
				VAR_INPUT
					A: DINT;
				END_VAR
				VAR_OUTPUT
					O: DINT;
				END_VAR
				VAR_IN_OUT
					X: DINT;
				END_VAR
				THIS.TEST_METHOD2(A := A, X := X, O => O);
				END_METHOD
				""", "TEST_METHOD"), newSTMethod("""
				METHOD TEST_METHOD2
				VAR_INPUT
					A: DINT;
				END_VAR
				VAR_OUTPUT
					O: DINT := 21;
				END_VAR
				VAR_IN_OUT
					X: DINT;
				END_VAR
				X := X + A;
				END_METHOD
				""", "TEST_METHOD2")), List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@Test
	void testMethod2MethodCallNonFormalWithInOut() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21), evaluateSimpleFB(List.of(newSTAlgorithm("""
				VAR_TEMP
					tempVar : DINT;
				END_VAR
				tempVar := DI1;
				THIS.TEST_METHOD(DI2, tempVar); DO1 := tempVar;
				""", "REQ"), newSTMethod("""
				METHOD TEST_METHOD
				VAR_INPUT
					A: DINT;
				END_VAR
				VAR_IN_OUT
					X: DINT;
				END_VAR
				THIS.TEST_METHOD2(A, X);
				END_METHOD
				""", "TEST_METHOD"), newSTMethod("""
				METHOD TEST_METHOD2
				VAR_INPUT
					A: DINT;
				END_VAR
				VAR_IN_OUT
					X: DINT;
				END_VAR
				X := X + A;
				END_METHOD
				""", "TEST_METHOD2")), List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				newVarDeclaration("DO1", ElementaryTypes.DINT, false)).getVariables().get("DO1").getValue());
	}

	@ParameterizedTest
	@ValueSource(strings = { "6", "0..5", "0..21-17+2", "0..MAXLEN-1" })
	void testSimpleWithArray(final String arraySize) throws EvaluatorException, InterruptedException {
		final VarDeclaration inputVarDecl = newVarDeclaration("DI1", ElementaryTypes.DINT, true, "[1,2,3,4,5,6]");
		ArraySizeHelper.setArraySize(inputVarDecl, arraySize);
		final VarDeclaration outputVarDecl = newVarDeclaration("DO1", ElementaryTypes.DINT, false);
		final VarDeclaration internalConstVarDecl = newVarDeclaration("MAXLEN", ElementaryTypes.DINT, false, "6");
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(
						List.of(newSTAlgorithm("DO1 := DI1[0] + DI1[1] + DI1[2] + DI1[3] + DI1[4] + DI1[5];", "REQ")),
						List.of(inputVarDecl, outputVarDecl), List.of(), List.of(internalConstVarDecl), List.of())
						.getVariables().get("DO1").getValue());
	}

	@Test
	void testSimpleWithStruct() throws EvaluatorException, InterruptedException {
		final StructuredType structType = newStructuredType("TestStruct",
				List.of(newVarDeclaration("a", ElementaryTypes.DINT, false),
						newVarDeclaration("b", ElementaryTypes.DINT, false)));
		final VarDeclaration inputVarDecl = newVarDeclaration("DI1", structType, true);
		final StructVariable inputVar = ((StructVariable) VariableOperations.newVariable(inputVarDecl));
		inputVar.getMembers().get("a").setValue(toDIntValue(17));
		inputVar.getMembers().get("b").setValue(toDIntValue(4));
		final VarDeclaration outputVarDecl = newVarDeclaration("DO1", structType, false);
		assertIterableEquals(List.of(toDIntValue(21), toDIntValue(42)),
				((StructValue) evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1.a := DI1.a + DI1.b;
						DO1.b := 42;
						""", "REQ")), List.of(inputVar), outputVarDecl).getVariables().get("DO1").getValue()));
	}

	@Test
	void testSimpleFBCall() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1", this.newTestSimpleFBType());
		assertEquals(toDIntValue(42),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						FB1(DI1 := DI1);
						FB1(DI1 := DI2);
						FB1.REQ(DI1 := FB1.DO1, DO1 => DO1);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCall() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1 := FB1(DI1, DI2);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCallFormal() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1 := FB1(DI1 := 17, DI2 := 4);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCallEvent() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1 := FB1.REQ(DI1, DI2);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCallEventFormal() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(21),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1 := FB1.REQ(DI1 := DI1, DI2 := DI2);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCallNestedExpression() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(42),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1 := FB1(DI1, DI2) * 2;
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCallNestedCall() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(42),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						DO1 := MUL(FB1(DI1, DI2), 2);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	@Test
	void testFunctionFBCallVariables() throws EvaluatorException, InterruptedException {
		final FB internalFB = newFB("FB1",
				newFunctionFBType("TEST_FUNC",
						List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
								newVarDeclaration("DI2", ElementaryTypes.DINT, true),
								newVarDeclaration("", ElementaryTypes.DINT, false)),
						"""
								FUNCTION TEST_FUNC : DINT
								VAR_INPUT
									DI1 : DINT;
									DI2 : DINT;
								END_VAR
								TEST_FUNC := DI1 + DI2;
								END_FUNCTION
								"""));
		assertEquals(toDIntValue(42),
				evaluateSimpleFB(List.of(newSTAlgorithm("""
						FB1.DI1 := DI1;
						FB1.DI2 := DI2;
						DO1 := MUL(FB1(), 2);
						""", "REQ")),
						Collections.<Variable<?>>unmodifiableList(CollectionLiterals.<Variable<?>>newArrayList(
								newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2"))),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false), List.of(), List.of(),
						List.of(internalFB)).getVariables().get("DO1").getValue());
	}

	FBType newTestSimpleFBType() {
		final SimpleFBType simpleType = newSimpleFBType("TestSimple",
				List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false)));
		simpleType.getCallables().add(newSTAlgorithm("DO1 := DO1 + DI1;", "REQ"));
		return simpleType;
	}

	static SimpleFBEvaluator evaluateSimpleFB(final List<? extends ICallable> callables,
			final List<Variable<?>> variables, final VarDeclaration output)
			throws EvaluatorException, InterruptedException {
		return evaluateSimpleFB(callables, variables, output, List.of(), List.of(), List.of());
	}

	static SimpleFBEvaluator evaluateSimpleFB(final List<? extends ICallable> callables,
			final List<Variable<?>> variables, final VarDeclaration output, final List<VarDeclaration> internalVars,
			final List<VarDeclaration> internalConstVars, final List<FB> internalFBs)
			throws EvaluatorException, InterruptedException {
		final SimpleFBType fbType = newSimpleFBType("Test",
				Stream.concat(
						variables.stream().map(
								variable -> newVarDeclaration(variable.getName(), (DataType) variable.getType(), true)),
						Stream.of(output)).toList());
		ECollections.setEList(fbType.getCallables(), callables);
		ECollections.setEList(fbType.getInternalVars(), internalVars);
		ECollections.setEList(fbType.getInternalConstVars(), internalConstVars);
		ECollections.setEList(fbType.getInternalFbs(), internalFBs);
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(
				fbType.getInterfaceList().getEventInputs());
		final SimpleFBEvaluator eval = new SimpleFBEvaluator(fbType, null, variables, null);
		eval.setEventQueue(queue);
		eval.evaluate();
		assertIterableEquals(fbType.getInterfaceList().getEventOutputs(), queue.getOutputEvents());
		return eval;
	}

	static SimpleFBEvaluator evaluateSimpleFB(final List<? extends ICallable> callables,
			final List<VarDeclaration> variables, final List<VarDeclaration> internalVars,
			final List<VarDeclaration> internalConstVars, final List<FB> internalFBs)
			throws EvaluatorException, InterruptedException {
		final SimpleFBType fbType = newSimpleFBType("Test", variables);
		ECollections.setEList(fbType.getCallables(), callables);
		ECollections.setEList(fbType.getInternalVars(), internalVars);
		ECollections.setEList(fbType.getInternalConstVars(), internalConstVars);
		ECollections.setEList(fbType.getInternalFbs(), internalFBs);
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(
				fbType.getInterfaceList().getEventInputs());
		final SimpleFBEvaluator eval = new SimpleFBEvaluator(fbType, null, List.of(), null);
		eval.setEventQueue(queue);
		eval.evaluate();
		assertIterableEquals(fbType.getInterfaceList().getEventOutputs(), queue.getOutputEvents());
		return eval;
	}
}
