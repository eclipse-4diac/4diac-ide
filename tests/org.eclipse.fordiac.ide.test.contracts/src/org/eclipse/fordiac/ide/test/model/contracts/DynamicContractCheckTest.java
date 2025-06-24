/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.model.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.eclipse.fordiac.ide.contracts.ContractComponent;
import org.eclipse.fordiac.ide.contracts.ContractIssue;
import org.eclipse.fordiac.ide.contracts.ContractSystem;
import org.eclipse.fordiac.ide.contracts.EventOccurrence;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method", "nls" }) // translating doesn't make sense here
class DynamicContractCheckTest {

	// === test single event
	@Test
	void singleEventCorrectTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(createEI(5)));
		assertTrue(sys.getIssues().isEmpty());
	}

	@Test
	void singleEventTooEarlyTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(createEI(2)));
		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	@Test
	void singleEventTooLateTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(createEI(12)));
		assertEquals(2, sys.getIssues().size());
		// first error because event did not arrive in time
		assertEquals(ContractIssue.Code.SINGLE_EVENT_MATCH, sys.getIssues().get(0).getCode());
		// second error because event arrived outside of interval
		assertEquals(ContractIssue.Code.SINGLE_EVENT_MATCH, sys.getIssues().get(1).getCode());
	}

	@Test
	void singleEventTooOftenTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(createEI(5), createEI(8)));
		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	@Test
	void singleEventMissingTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of());
		assertOneIssue(ContractIssue.Code.SINGLE_EVENT_MATCH, sys);
	}

	// === test repetition
	@Test
	void repetitionFailAfter4Test() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns");
		sys.performDynamicCheck(List.of(createEI(0), createEI(7), createEI(14), createEI(21)));
		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void repetitionTooEarlyOffsetTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(createEI(3)));
		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	@Test
	void repetitionTooLateOffsetTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(createEI(8)));
		assertOneIssue(ContractIssue.Code.REPETITION_MATCH, sys);
	}

	// === helper methods
	/**
	 * creates a system with a single component which has an input "EI", an output
	 * "EO" and the specified contract
	 */
	private static ContractSystem createSimpleSystem(final String contract) {
		final ContractSystem sys = new ContractSystem();
		final ContractComponent comp = new ContractComponent("component");
		sys.addComponent(comp, contract, List.of("EI"), List.of("EO"));
		return sys;
	}

	private EventOccurrence createEI(final double timestamp) {
		return new EventOccurrence("component.EI", timestamp);
	}

	private static void assertOneIssue(final ContractIssue.Code code, final ContractSystem sys) {
		assertEquals(1, sys.getIssues().size());
		assertEquals(code, sys.getIssues().get(0).getCode());
	}
}
