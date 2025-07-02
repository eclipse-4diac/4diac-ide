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
package org.eclipse.fordiac.ide.contracts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.eclipse.fordiac.ide.contracts.DynamicCheckResult.RuleData;

public class DynamicContractChecker {

	private final ContractSystem system;
	private final Map<String, RuleData> rules;
	private final PriorityQueue<EventOccurrence> queue;
	private final DynamicCheckResult result;

	public DynamicContractChecker(final ContractSystem system, final List<ContractComponent> components,
			final List<EventOccurrence> eventOccurrences) {
		this.system = system;
		rules = new HashMap<>();
		queue = new PriorityQueue<>(eventOccurrences);
		result = new DynamicCheckResult(system);

		for (final ContractComponent comp : components) {
			for (final ContractRule a : comp.getAssumptions().values()) {
				final RuleData d = new RuleData(a);
				rules.put(a.getPortNameQualified(), d);
				result.rules().add(d);
			}
			for (final ContractRule g : comp.getGuarantees().values()) {
				final RuleData d = new RuleData(g);
				rules.put(g.getPortNameQualified(), d);
				result.rules().add(d);
			}
		}
	}

	public DynamicCheckResult checkSystem() {
		initializeRuleStates();
		processEvents();
	}

	private void initializeRuleStates() {
		for (final RuleData ruleData : rules.values()) {
			final ContractRule rule = ruleData.rule();
			switch (rule.getType()) {
			case SINGLE_EVENT:
				initSingleEvent(ruleData, rule);
				break;
			case REPETITION:
				initRepetition(ruleData, rule);
				break;
			default:
				break;
			}
		}
	}

	private void processEvents() {
		while (!queue.isEmpty()) {
			final EventOccurrence eo = queue.poll();
			final RuleData ruleData = rules.get(eo.eventName());

			if (eo.type() == EventOccurrence.Type.RECORDED) {
				result.eventOccurrences().add(eo);
			}
			if (ruleData == null) {
				continue; // no rule to check for this event
			}
			final ContractRule rule = ruleData.rule();
			switch (rule.getType()) {
			case SINGLE_EVENT:
				checkSingleEvent(ruleData, eo);
				break;
			case REPETITION:
				checkRepetition(rule, eo);
				break;
			default:
				break;
			}
		}
	}

	private void initSingleEvent(final RuleData ruleData, final ContractRule rule) {
		createMissedMarker(rule.getPortNameQualified(), rule.getInterval().getUpperBound());
		ruleData.intervals().add(rule.getInterval());
	}

	private void checkSingleEvent(final RuleData ruleData, final EventOccurrence eo) {
		final ContractRule rule = ruleData.rule();
		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			if (!rule.isFulFilled()) {
				system.error("\"%s\" did not arrive in time.".formatted(rule.getPortNameQualified()),
						ContractIssue.Code.SINGLE_EVENT_MISSED);
				ruleData.markers().add(issueMarker(eo));
			}
			return;
		}

		if (rule.isFulFilled()) {
			system.error("\"%s\" occurred more than once.".formatted(rule.getPortNameQualified()),
					ContractIssue.Code.SINGLE_EVENT_MULTIPLE);
			ruleData.markers().add(issueMarker(eo));
		} else if (!rule.getInterval().contains(eo.timestampNs())) {
			eventOutsideIntervalError(rule, eo, ContractIssue.Code.SINGLE_EVENT_TOO_EARLY,
					ContractIssue.Code.SINGLE_EVENT_TOO_LATE);
			ruleData.markers().add(issueMarker(eo));
		} else {
			ruleData.markers().add(fulfillMarker(eo));
		}
		rule.setFulFilled(true);
	}

	private void initRepetition(final RuleData ruleData, final ContractRule rule) {
		createMissedMarker(rule.getPortNameQualified(), rule.getOffset().getUpperBound());
		ruleData.intervals().add(rule.getOffset());
	}

	private void checkRepetition(final ContractRule rule, final EventOccurrence eo) {

		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			final CInterval interval = rule.isFulFilled() ? rule.getInterval() : rule.getOffset();
			if (eo.timestampNs() >= interval.getUpperBound()) {
				system.error("\"%s\" did not arrive in time.".formatted(rule.getPortNameQualified()),
						ContractIssue.Code.REPETITION_MISSED);
			}
			return;
		}

		if (!rule.isFulFilled()) { // first occurrence respects offset
			if (!rule.getOffset().contains(eo.timestampNs())) {
				eventOutsideIntervalError(rule, eo, ContractIssue.Code.REPETITION_TOO_EARLY,
						ContractIssue.Code.REPETITION_TOO_LATE);
			}
			rule.setInterval(rule.getInterval().translate(eo.timestampNs()));
			rule.setFulFilled(true);
		} else {
			if (!rule.getInterval().contains(eo.timestampNs())) {
				eventOutsideIntervalError(rule, eo, ContractIssue.Code.REPETITION_TOO_EARLY,
						ContractIssue.Code.REPETITION_TOO_LATE);
			}
			rule.setInterval(rule.getInterval().translate(eo.timestampNs()));
		}
	}

	@SuppressWarnings("boxing")
	private void eventOutsideIntervalError(final ContractRule rule, final EventOccurrence eo,
			final ContractIssue.Code tooEarly, final ContractIssue.Code tooLate) {
		if (eo.timestampNs() <= rule.getInterval().getLowerBound()) {
			system.error("\"%s\" occurred %fns too early.".formatted(rule.getPortNameQualified(),
					rule.getInterval().getLowerBound() - eo.timestampNs()), tooEarly);
		} else {
			system.error("\"%s\" occurred %fns too late.".formatted(rule.getPortNameQualified(),
					eo.timestampNs() - rule.getInterval().getUpperBound()), tooLate);
		}
	}

	private void createMissedMarker(final String name, final double timestamp) {
		queue.offer(new EventOccurrence(name, timestamp, EventOccurrence.Type.MISSED_MARKER));
	}

	private static EventOccurrence issueMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), EventOccurrence.Type.ISSUE_MARKER);
	}

	private static EventOccurrence fulfillMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), EventOccurrence.Type.FULFILL_MARKER);
	}
}
