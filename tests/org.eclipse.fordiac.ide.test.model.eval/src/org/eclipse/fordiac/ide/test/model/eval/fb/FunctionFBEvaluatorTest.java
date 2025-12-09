/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.test.model.eval.fb;

import static org.eclipse.fordiac.ide.model.eval.value.DIntValue.toDIntValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.FunctionFBEvaluator;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "squid:S5960", "nls" })
class FunctionFBEvaluatorTest extends AbstractFBEvaluatorTest {

	@Test
	void testFunctionFB() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21), evaluateFunctionFB("""
				FUNCTION TEST : DINT
				VAR_INPUT
					DI1 : DINT;
					DI2 : DINT;
				END_VAR
				TEST := DI1 + DI2;
				END_FUNCTION
				""", List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				List.of(newVariable(toDIntValue(0), ""))).getVariables().get("").getValue());
	}

	@Test
	void testFunctionFBOutput() throws EvaluatorException, InterruptedException {
		assertEquals(toDIntValue(21), evaluateFunctionFB("""
				FUNCTION TEST
				VAR_INPUT
					DI1 : DINT;
					DI2 : DINT;
				END_VAR
				VAR_OUTPUT
					DO1 : DINT;
				END_VAR
				DO1 := DI1 + DI2;
				END_FUNCTION
				""", List.of(newVariable(toDIntValue(17), "DI1"), newVariable(toDIntValue(4), "DI2")),
				List.of(newVariable(toDIntValue(0), "DO1"))).getVariables().get("DO1").getValue());
	}

	static FunctionFBEvaluator evaluateFunctionFB(final String text, final List<Variable<?>> inputs,
			final List<Variable<?>> outputs) throws EvaluatorException, InterruptedException {
		final Event inputEvent = newEvent("REQ", true);
		final Event outputEvent = newEvent("CNF", false);
		final FunctionFBType fbType = LibraryElementFactory.eINSTANCE.createFunctionFBType();
		fbType.setName("TEST");
		fbType.setInterfaceList(newInterfaceList(List.of(inputEvent, outputEvent), Stream.concat(
				inputs.stream()
						.map(variable -> newVarDeclaration(variable.getName(), (DataType) variable.getType(), true)),
				outputs.stream()
						.map(variable -> newVarDeclaration(variable.getName(), (DataType) variable.getType(), false)))
				.toList()));
		final STFunctionBody body = LibraryElementFactory.eINSTANCE.createSTFunctionBody();
		body.setText(text);
		fbType.setBody(body);
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(List.of(inputEvent));
		final FunctionFBEvaluator eval = new FunctionFBEvaluator(fbType, null,
				Stream.concat(inputs.stream(), outputs.stream()).toList(), null);
		eval.setEventQueue(queue);
		eval.evaluate();
		assertIterableEquals(List.of(outputEvent), queue.getOutputEvents());
		return eval;
	}
}
