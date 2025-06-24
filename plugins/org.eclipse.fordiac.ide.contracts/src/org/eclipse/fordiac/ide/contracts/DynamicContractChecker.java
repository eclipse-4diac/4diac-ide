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

public class DynamicContractChecker {

	private final ContractSystem system;
	private final Map<String, ContractRule> rules;
	private final PriorityQueue<EventOccurrence> queue;

	public DynamicContractChecker(final ContractSystem system, final List<ContractComponent> components,
			final List<EventOccurrence> eventOccurrences) {
		this.system = system;
		rules = new HashMap<>();

		for (final ContractComponent comp : components) {
			for (final ContractRule a : comp.getAssumptions().values()) {
				rules.put(a.getPortNameQualified(), a);
			}
			for (final ContractRule g : comp.getGuarantees().values()) {
				rules.put(g.getPortNameQualified(), g);
			}
		}
		queue = new PriorityQueue<>(eventOccurrences);
	}

	public void checkSystem() {
		initializeRuleStates();
		processEvents();
	}

	private void initializeRuleStates() {
		for (final ContractRule rule : rules.values()) {
			switch (rule.getType()) {
			case SINGLE_EVENT:
				initSingleEvent(rule);
				break;
			case REPETITION:
				initRepetition(rule);
				break;
			default:
				break;
			}
		}
	}

	private void processEvents() {
		while (!queue.isEmpty()) {
			final EventOccurrence eo = queue.poll();
			final ContractRule rule = rules.get(eo.eventName());

			if (rule == null) {
				continue; // no rule to check for this event
			}
			switch (rule.getType()) {
			case SINGLE_EVENT:
				checkSingleEvent(rule, eo);
				break;
			case REPETITION:
				checkRepetition(rule, eo);
				break;
			default:
				break;
			}
		}
	}

	private void initSingleEvent(final ContractRule rule) {
		createMissedMarker(rule.getPortNameQualified(), rule.getInterval().getUpperBound());
	}

	private void checkSingleEvent(final ContractRule rule, final EventOccurrence eo) {
		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			if (!rule.isFulFilled()) {
				system.error("Event \"%s\" did not arrive in time.".formatted(rule.getPortNameQualified()),
						ContractIssue.Code.SINGLE_EVENT_MATCH);
			}
			return;
		}

		if (rule.isFulFilled()) {
			system.error("Event \"%s\" occurred more than once.".formatted(rule.getPortNameQualified()),
					ContractIssue.Code.SINGLE_EVENT_MATCH);
		} else if (!rule.getInterval().contains(eo.timestampNs())) {
			system.error("Event \"%s\" occurred outside the specified interval.".formatted(rule.getPortNameQualified()),
					ContractIssue.Code.SINGLE_EVENT_MATCH);
		}
		rule.setFulFilled(true);
	}

	private void createMissedMarker(final String name, final double timestamp) {
		queue.offer(new EventOccurrence(name, timestamp, EventOccurrence.Type.MISSED_MARKER));
	}
}
