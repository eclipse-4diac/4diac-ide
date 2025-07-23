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
		sys.performDynamicCheck(List.of(eo("EI", 5)));
		assertIssues(sys);
	}

	@Test
	void singleEventTooEarlyTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 2)));
		assertIssues(sys, ContractIssue.Code.SINGLE_EVENT_TOO_EARLY);
	}

	@Test
	void singleEventTooLateTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 12)));
		assertIssues(sys, ContractIssue.Code.SINGLE_EVENT_MISSED, ContractIssue.Code.SINGLE_EVENT_TOO_LATE);
	}

	@Test
	void singleEventTooOftenTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 5), eo("EI", 8)));
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
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI", 7), eo("EI", 14), eo("EI", 21)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooEarlyOffsetTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(eo("EI", 3)));
		assertIssues(sys, ContractIssue.Code.REPETITION_TOO_EARLY, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooLateOffsetTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(eo("EI", 8)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED, ContractIssue.Code.REPETITION_TOO_LATE,
				ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooEarlyIntervalTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(eo("EI", 4), eo("EI", 8)));
		assertIssues(sys, ContractIssue.Code.REPETITION_TOO_EARLY, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionTooLateIntervalTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns");
		sys.performDynamicCheck(List.of(eo("EI", 5), eo("EI", 14)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED, ContractIssue.Code.REPETITION_TOO_LATE,
				ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionWithJitterTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns and jitter 1ns");
		sys.performDynamicCheck(List.of(eo("EI", 3), eo("EI", 10)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionWithJitterTooEarlyTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns and jitter 1ns");
		sys.performDynamicCheck(List.of(eo("EI", 3), eo("EI", 8)));
		assertIssues(sys, ContractIssue.Code.REPETITION_TOO_EARLY, ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionWithJitterTooLateTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [6, 8]ns with offset [4, 5]ns and jitter 1ns");
		sys.performDynamicCheck(List.of(eo("EI", 3), eo("EI", 14)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED, ContractIssue.Code.REPETITION_TOO_LATE,
				ContractIssue.Code.REPETITION_MISSED);
	}

	@Test
	void repetitionOverlappingIntervalTest() {
		final ContractSystem sys = createSimpleSystem("EI occurs every [1, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI", 2), eo("EI", 4), eo("EI", 6)));
		assertIssues(sys, ContractIssue.Code.REPETITION_MISSED);
	}

	// === test reaction
	@Test
	void reactionEmptyTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within 5ns");
		sys.performDynamicCheck(List.of());
		assertIssues(sys);
	}

	@Test
	void reactionFulfilledTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within 5ns");
		sys.performDynamicCheck(List.of(eo("EI", 2), eo("EO", 7), eo("EI", 13), eo("EO", 18)));
		assertIssues(sys);
	}

	@Test
	void reactionMissedTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within 5ns");
		sys.performDynamicCheck(List.of(eo("EI", 2), eo("EI", 13)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void reactionOverlapMultiFulfillTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 1), eo("EI", 2), eo("EO", 8)));
		assertIssues(sys);
	}

	@Test
	void reactionOverlapEarlyTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 1), eo("EI", 2), eo("EO", 6)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void reactionOverlapLateTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 1), eo("EI", 2), eo("EO", 12)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void reactionOverlapBothTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 1), eo("EI", 2), eo("EO", 6), eo("EO", 12)));
		assertIssues(sys);
	}

	@Test
	void slidingWindow12CorrectTest() {
		final ContractSystem sys = createSimpleSystem(
				"whenever EI occurs then EO occurs within [5, 10]ns 1 out of 2 times");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI", 10), eo("EO", 18)));
		assertIssues(sys);
	}

	@Test
	void slidingWindow12IssueTest() {
		final ContractSystem sys = createSimpleSystem(
				"whenever EI occurs then EO occurs within [5, 10]ns 1 out of 2 times");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI", 10)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void slidingWindow23CorrectTest() {
		final ContractSystem sys = createSimpleSystem(
				"whenever EI occurs then EO occurs within [5, 10]ns 2 out of 3 times");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 8), eo("EI", 10), eo("EI", 20), eo("EO", 28)));
		assertIssues(sys);
	}

	@Test
	void slidingWindow23IssueTest() {
		final ContractSystem sys = createSimpleSystem(
				"whenever EI occurs then EO occurs within [5, 10]ns 2 out of 3 times");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 8), eo("EI", 10), eo("EI", 20)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void slidingWindow23IssuesTest() {
		final ContractSystem sys = createSimpleSystem(
				"whenever EI occurs then EO occurs within [5, 10]ns 2 out of 3 times");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI", 10), eo("EI", 20)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void inputSequenceIncompleteTest() {
		final ContractSystem sys = createSimpleSystem("whenever (EI, EI2, EI3) occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI2", 10)));
		assertIssues(sys);
	}

	@Test
	void inputSequenceWrongOrderTest() {
		final ContractSystem sys = createSimpleSystem("whenever (EI, EI2, EI3) occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI3", 10), eo("EI2", 20)));
		assertIssues(sys);
	}

	@Test
	void inputSequenceCompleteTest() {
		final ContractSystem sys = createSimpleSystem("whenever (EI, EI2, EI3) occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI2", 10), eo("EI3", 20)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void inputSetIncompleteTest() {
		final ContractSystem sys = createSimpleSystem("whenever {EI, EI2, EI3} occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI2", 10)));
		assertIssues(sys);
	}

	@Test
	void inputSetCompleteTest() {
		final ContractSystem sys = createSimpleSystem("whenever {EI, EI2, EI3} occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EI2", 10), eo("EI3", 10)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void inputSetComplete2Test() {
		final ContractSystem sys = createSimpleSystem("whenever {EI, EI2, EI3} occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI3", 0), eo("EI", 10), eo("EI2", 10)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void reactionIntervalBoundaryLeftClosedTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 5)));
		assertIssues(sys);
	}

	@Test
	void reactionIntervalBoundaryLeftOpenTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within ]5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 5)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void reactionIntervalBoundaryRightClosedTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 10)));
		assertIssues(sys);
	}

	@Test
	void reactionIntervalBoundaryRightOpenTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then EO occurs within [5, 10[ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 10)));
		assertIssues(sys, ContractIssue.Code.REACTION_MISSED);
	}

	@Test
	void reactionSetOnceValidTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then {EO, EO2} occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 6), eo("EO2", 7), eo("EO2", 8), eo("EO", 9)));
		assertIssues(sys);
	}

	@Test
	void reactionSetOnceInvalidTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then {EO, EO2} occurs within [5, 10]ns once");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 6), eo("EO2", 7), eo("EO2", 8), eo("EO", 9)));
		assertIssues(sys, ContractIssue.Code.REACTION_TOO_OFTEN);
	}

	@Test
	void reactionSequenceValidTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then (EO, EO2) occurs within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 6), eo("EO2", 7), eo("EO", 8), eo("EO2", 9)));
		assertIssues(sys);
	}

	@Test
	void reactionSequenceInvalidTest() {
		final ContractSystem sys = createSimpleSystem("whenever EI occurs then (EO, EO2) occurs within [5, 10]ns once");
		sys.performDynamicCheck(List.of(eo("EI", 0), eo("EO", 6), eo("EO2", 7), eo("EO", 8), eo("EO2", 9)));
		assertIssues(sys, ContractIssue.Code.REACTION_TOO_OFTEN);
	}

	// === test age
	// age is symmetric to reaction above in many aspects -> less extensive tests
	@Test
	void ageEmptyTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within 5ns");
		sys.performDynamicCheck(List.of());
		assertIssues(sys);
	}

	@Test
	void ageFulfilledTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within 5ns");
		sys.performDynamicCheck(List.of(eo("EI", 2), eo("EO", 7), eo("EI", 13), eo("EO", 18)));
		assertIssues(sys);
	}

	@Test
	void ageMissedTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within 5ns");
		sys.performDynamicCheck(List.of(eo("EO", 2), eo("EO", 13)));
		assertIssues(sys, ContractIssue.Code.AGE_MISSED, ContractIssue.Code.AGE_MISSED);
	}

	@Test
	void ageOverlapMultiFulfillTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 6), eo("EO", 12), eo("EO", 14)));
		assertIssues(sys);
	}

	@Test
	void ageOverlapEarlyTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 3), eo("EO", 12), eo("EO", 14)));
		assertIssues(sys, ContractIssue.Code.AGE_MISSED);
	}

	@Test
	void ageOverlapLateTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 8), eo("EO", 12), eo("EO", 14)));
		assertIssues(sys, ContractIssue.Code.AGE_MISSED);
	}

	@Test
	void ageOverlapBothTest() {
		final ContractSystem sys = createSimpleSystem("whenever EO occurs then EI has occurred within [5, 10]ns");
		sys.performDynamicCheck(List.of(eo("EI", 3), eo("EI", 8), eo("EO", 12), eo("EO", 14)));
		assertIssues(sys);
	}

	// === test causal reaction

	// === test causal age

	// === helper methods
	/**
	 * creates a system with a single component with inputs ("EI", "EI2", "EI3"),
	 * outputs ("EO", "EO1", "EO2"), and the specified contract
	 */
	private static ContractSystem createSimpleSystem(final String contract) {
		final ContractSystem sys = new ContractSystem();
		final ContractComponent comp = new ContractComponent("component");
		sys.addComponent(comp, contract, List.of("EI", "EI2", "EI3"), List.of("EO", "EO2", "EO3"));
		return sys;
	}

	private EventOccurrence eo(final String shortName, final double timestamp) {
		return new EventOccurrence("component." + shortName, timestamp);
	}

	private static void assertIssues(final ContractSystem sys, final ContractIssue.Code... code) {
		assertEquals(code.length, sys.getIssues().size());
		for (int i = 0; i < code.length; i++) {
			assertEquals(code[i], sys.getIssues().get(i).getCode());
		}
	}
}
