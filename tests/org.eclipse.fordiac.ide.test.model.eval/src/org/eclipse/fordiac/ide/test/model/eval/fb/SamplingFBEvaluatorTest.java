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

import java.util.List;

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.eval.EvaluatorException;
import org.eclipse.fordiac.ide.model.eval.fb.SamplingFBEvaluator;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.With;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "squid:S5960", "nls" })
class SamplingFBEvaluatorTest extends AbstractFBEvaluatorTest {
	@Test
	void testSimpleFB() throws EvaluatorException, InterruptedException {
		final SimpleFBType fbType = newTestSimpleFBType();
		final SamplingFBEvaluator eval = new SamplingFBEvaluator(fbType, null, List.of(), null);
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(
				List.of(fbType.getInterfaceList().getEvent("REQ")));
		eval.setEventQueue(queue);
		// initial values
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO2").getValue());
		// evaluate for REQ
		eval.evaluate();
		assertIterableEquals(List.of(fbType.getInterfaceList().getEvent("CNF")), queue.getOutputEvents());
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(21), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(21), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(42), eval.getDelegate().getVariables().get("DO2").getValue());
	}

	@Test
	void testSimpleFBInputChange() throws EvaluatorException, InterruptedException {
		final SimpleFBType fbType = newTestSimpleFBType();
		final SamplingFBEvaluator eval = new SamplingFBEvaluator(fbType, null, List.of(), null);
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(
				List.of(fbType.getInterfaceList().getEvent("REQ")));
		eval.setEventQueue(queue);
		// initial values
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO2").getValue());
		// evaluate for REQ
		eval.evaluate();
		assertIterableEquals(List.of(fbType.getInterfaceList().getEvent("CNF")), queue.getOutputEvents());
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(21), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(21), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(42), eval.getDelegate().getVariables().get("DO2").getValue());
		// change inputs and trigger REQ again
		eval.getVariables().get("DI1").setValue("1");
		eval.getVariables().get("DI2").setValue("2");
		queue.triggerInputEvent(fbType.getInterfaceList().getEvent("REQ"));
		eval.evaluate();
		assertIterableEquals(
				List.of(fbType.getInterfaceList().getEvent("CNF"), fbType.getInterfaceList().getEvent("CNF")),
				queue.getOutputEvents());
		assertEquals(toDIntValue(1), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(2), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(5), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(1), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(5), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(10), eval.getDelegate().getVariables().get("DO2").getValue());
	}

	@Test
	void testSimpleFBReset() throws EvaluatorException, InterruptedException {
		final SimpleFBType fbType = newTestSimpleFBType();
		final SamplingFBEvaluator eval = new SamplingFBEvaluator(fbType, null, List.of(), null);
		final TracingFBEvaluatorEventQueue queue = new TracingFBEvaluatorEventQueue(
				List.of(fbType.getInterfaceList().getEvent("REQ")));
		eval.setEventQueue(queue);
		// initial values
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO2").getValue());
		// evaluate for REQ
		eval.evaluate();
		assertIterableEquals(List.of(fbType.getInterfaceList().getEvent("CNF")), queue.getOutputEvents());
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(21), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(21), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(42), eval.getDelegate().getVariables().get("DO2").getValue());
		// reset
		eval.reset(List.of());
		assertEquals(toDIntValue(17), eval.getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getVariables().get("DO2").getValue());
		assertEquals(toDIntValue(17), eval.getDelegate().getVariables().get("DI1").getValue());
		assertEquals(toDIntValue(4), eval.getDelegate().getVariables().get("DI2").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO1").getValue());
		assertEquals(toDIntValue(0), eval.getDelegate().getVariables().get("DO2").getValue());
	}

	SimpleFBType newTestSimpleFBType() {
		final SimpleFBType simpleType = newSimpleFBType("TestSimple",
				List.of(newVarDeclaration("DI1", ElementaryTypes.DINT, true, "17"),
						newVarDeclaration("DI2", ElementaryTypes.DINT, true, "4"),
						newVarDeclaration("DO1", ElementaryTypes.DINT, false),
						newVarDeclaration("DO2", ElementaryTypes.DINT, false)));
		createWith(simpleType, "REQ", "DI1");
		// no with for REQ and DI2
		createWith(simpleType, "CNF", "DO1");
		// no with for CNF and DO2
		simpleType.getCallables().add(newSTAlgorithm("""
				DO1 := DI1 + DI2;
				DO2 := DO1 * 2;
				""", "REQ"));
		return simpleType;
	}

	void createWith(final FBType fbType, final String event, final String varDeclaration) {
		createWith(fbType.getInterfaceList().getEvent(event), fbType.getInterfaceList().getVariable(varDeclaration));
	}

	void createWith(final Event event, final VarDeclaration varDeclaration) {
		final With with = LibraryElementFactory.eINSTANCE.createWith();
		with.setVariables(varDeclaration);
		event.getWith().add(with);
	}
}
