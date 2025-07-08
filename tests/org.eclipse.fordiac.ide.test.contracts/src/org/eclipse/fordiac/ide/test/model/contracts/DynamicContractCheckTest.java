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
		assertIssues(sys, ContractIssue.Code.SINGLE_EVENT_TOO_EARLY);
	}

	@Test
	void singleEventTooLateTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(createEI(12)));
		assertIssues(sys, ContractIssue.Code.SINGLE_EVENT_MISSED, ContractIssue.Code.SINGLE_EVENT_TOO_LATE);
	}

	@Test
	void singleEventTooOftenTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(createEI(5), createEI(8)));
		assertIssues(sys, ContractIssue.Code.SINGLE_EVENT_MULTIPLE);
	}

	@Test
	void singleEventMissingTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of());
		assertIssues(sys, ContractIssue.Code.SINGLE_EVENT_MISSED);
	}

	// === test repetition
	// all repetition tests will always have 1 issue for missing the last interval,
	// since we cannot provide an infinite number of recorded events...
	@Test
	void repetitionMissedAfter4Test() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns");
		sys.performDynamicCheck(List.of(createEI(0), createEI(7), createEI(14), createEI(21)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooEarlyOffsetTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(createEI(3)));
		assertIssues(sys, ContractIssue.Code.REPETITION_TOO_EARLY, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooLateOffsetTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(createEI(8)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED, ContractIssue.Code.REPETITION_TOO_LATE,
				ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooEarlyIntervalTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(createEI(4), createEI(8)));
		assertIssues(sys, ContractIssue.Code.REPETITION_TOO_EARLY, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooLateIntervalTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(createEI(5), createEI(14)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED, ContractIssue.Code.REPETITION_TOO_LATE,
				ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionWithJitterTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns and jitter 1ns");
		sys.performDynamicCheck(List.of(createEI(3), createEI(10)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionWithJitterTooEarlyTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns and jitter 1ns");
		sys.performDynamicCheck(List.of(createEI(3), createEI(8)));
		assertIssues(sys, ContractIssue.Code.REPETITION_TOO_EARLY, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionWithJitterTooLateTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns and jitter 1ns");
		sys.performDynamicCheck(List.of(createEI(3), createEI(14)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED, ContractIssue.Code.REPETITION_TOO_LATE,
				ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionOverlappingIntervalTest() {
		// TODO: test repetition causing overlapping intervals
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

	private static void assertIssues(final ContractSystem sys, final ContractIssue.Code... code) {
		assertEquals(code.length, sys.getIssues().size());
		for (int i = 0; i < code.length; i++) {
			assertEquals(code[i], sys.getIssues().get(i).getCode());
		}
	}
}
