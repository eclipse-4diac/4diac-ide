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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contracts.ContractIssue.Code;
import org.eclipse.fordiac.ide.contracts.DynamicCheckResult.RuleData;
import org.eclipse.fordiac.ide.contracts.DynamicCheckResult.RuleData.SearchResult;

public class DynamicContractChecker {

	private final ContractSystem system;
	private final Map<String, List<RuleData>> rules;
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
				addRule(createKey(a, a.getInputs().getFirst()), d);
				result.rules().add(d);
			}
			for (final ContractRule g : comp.getGuarantees().values()) {
				final RuleData d = new RuleData(g);
				addRule(createKey(g, g.getOutputs().getFirst()), d);
				result.rules().add(d);
			}
			for (final ContractRule r : comp.getReactions()) {
				final RuleData d = new RuleData(r);
				for (final String port : r.getInputs()) {
					addRule(createKey(r, port), d);
				}
				for (final String port : r.getOutputs()) {
					addRule(createKey(r, port), d);
				}
				result.rules().add(d);
			}
		}
	}

	private static String createKey(final ContractRule rule, final String port) {
		return rule.getOwner().getName() + "." + port; //$NON-NLS-1$
	}

	private void addRule(final String key, final RuleData ruleData) {
		// sequences can have the same port multiple times, but ruleData should only be
		// added once for each port
		final List<RuleData> list = rules.get(key);
		if (list == null) {
			final List<RuleData> l = new ArrayList<>();
			l.add(ruleData);
			rules.put(key, l);
		} else if (!list.contains(ruleData)) {
			list.add(ruleData);
		}
	}

	public DynamicCheckResult checkSystem() {
		initializeRuleStates();
		processEvents();
		return result;
	}

	private void initializeRuleStates() {
		for (final List<RuleData> dataList : rules.values()) {
			int ruleIdx = 0;
			for (final RuleData ruleData : dataList) {
				final ContractRule rule = ruleData.rule();
				switch (rule.getType()) {
				case SINGLE_EVENT -> initSingleEvent(ruleData, rule, ruleIdx);
				case REPETITION -> initRepetition(ruleData, rule, ruleIdx);
				default -> { // nothing to init for reactions
				}
				}
				ruleIdx++;
			}
		}
	}

	private void processEvents() {
		while (!queue.isEmpty()) {
			final EventOccurrence eo = queue.poll();
			final List<RuleData> dataList = rules.get(eo.eventName());
			if (dataList == null) {
				continue; // no rule to check for this event
			}

			if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
				processEvent(dataList.get(eo.ruleIndex()), eo, eo.ruleIndex());
			} else {
				int ruleIdx = 0;
				for (final RuleData ruleData : dataList) {
					processEvent(ruleData, eo, ruleIdx);
					ruleIdx++;
				}
			}
		}
	}

	private void processEvent(final RuleData ruleData, final EventOccurrence eo, final int ruleIdx) {
		final ContractRule rule = ruleData.rule();
		switch (rule.getType()) {
		case SINGLE_EVENT -> checkSingleEvent(ruleData, eo);
		case REPETITION -> checkRepetition(ruleData, eo, ruleIdx);
		case REACTION -> checkReaction(ruleData, eo, ruleIdx);
		case AGE -> checkAge(ruleData, eo);
		case CAUSAL_REACTION -> checkCausalReaction(ruleData, eo, ruleIdx);
		case CAUSAL_AGE -> checkCausalAge(ruleData, eo);
		default -> { // no more types
		}
		}
	}

	private void initSingleEvent(final RuleData ruleData, final ContractRule rule, final int ruleIdx) {
		createMissedMarker(rule, rule.getSinglePort(), rule.getInterval().getUpperBound(), ruleIdx);
		ruleData.intervals().add(rule.getInterval());
	}

	private void checkSingleEvent(final RuleData ruleData, final EventOccurrence eo) {
		final ContractRule rule = ruleData.rule();
		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			if (!rule.isFulFilled()) {
				eventMissedError(eo, Code.SINGLE_EVENT_MISSED);
				ruleData.markers().add(eo);
			}
			return;
		}

		if (rule.isFulFilled()) {
			system.error("\"%s\" occurred more than once.".formatted(eo.eventName()), Code.SINGLE_EVENT_MULTIPLE);
			ruleData.markers().add(issueMarker(eo));
		} else if (!rule.getInterval().contains(eo.timestampNs())) {
			eventOutsideIntervalError(rule.getInterval(), eo, Code.SINGLE_EVENT_TOO_EARLY, Code.SINGLE_EVENT_TOO_LATE);
			ruleData.markers().add(issueMarker(eo));
		} else {
			ruleData.markers().add(fulfillMarker(eo));
		}
		rule.setFulFilled(true);
	}

	private void initRepetition(final RuleData ruleData, final ContractRule rule, final int ruleIdx) {
		createMissedMarker(rule, rule.getSinglePort(), rule.getOffset().getUpperBound() + rule.getJitter(), ruleIdx);
		ruleData.intervals().add(rule.getOffset());
	}

	private void checkRepetition(final RuleData ruleData, final EventOccurrence eo, final int ruleIdx) {
		final ContractRule rule = ruleData.rule();
		final CInterval interval = ruleData.intervals().getLast();
		final CInterval intervalJitter = interval.addJitter(rule.getJitter());

		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			if (eo.timestampNs() >= intervalJitter.getUpperBound()) {
				eventMissedError(eo, Code.REPETITION_MISSED);
				ruleData.markers().add(eo);
			}
			return;
		}

		final double shift;
		if (!intervalJitter.contains(eo.timestampNs())) {
			eventOutsideIntervalError(intervalJitter, eo, Code.REPETITION_TOO_EARLY, Code.REPETITION_TOO_LATE);
			ruleData.markers().add(issueMarker(eo));
			shift = eo.timestampNs();
		} else {
			ruleData.markers().add(fulfillMarker(eo));
			// jitter does not affect next occurrence
			shift = Math.clamp(eo.timestampNs(), interval.getLowerBound(), interval.getUpperBound());
		}
		final CInterval next = rule.getInterval().translate(shift);
		createMissedMarker(rule, rule.getSinglePort(), next.getUpperBound() + rule.getJitter(), ruleIdx);
		ruleData.intervals().add(next);
	}

	private void checkReaction(final RuleData ruleData, final EventOccurrence eo, final int ruleIdx) {
		final ContractRule rule = ruleData.rule();

		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			final CInterval interval = rule.getInterval().translate(
					eo.timestampNs() - rule.getInterval().getDiameter() - rule.getInterval().getLowerBound());
			final var searchResult = ruleData.searchInterval(interval);
			final boolean fulfill = searchResult == SearchResult.VALID || searchResult == SearchResult.TOO_OFTEN;

			if (searchResult == SearchResult.TOO_OFTEN) {
				reactionAgeTooOftenError(eo, true);
			}
			if (ruleData.hasSlidingWindow()) {
				if (!ruleData.add2SlidingWindow(fulfill)) {
					reactionAgeMissedSlidingWindowError(eo, rule.getNOutOfM(), true);
					ruleData.markers().add(eo);
				}
			} else if (!fulfill) {
				reactionAgeMissedError(eo, true);
				ruleData.markers().add(eo);
			}
			return;
		}

		ruleData.markers().add(normalMarker(eo));
		for (int i = 0; i < rule.getInputs().size(); i++) {
			if (eo.eventName().endsWith(rule.getInputs().get(i))) {
				if (ruleData.triggerOccurred(eo, i)) {
					final CInterval next = rule.getInterval().translate(eo.timestampNs());
					createMissedMarker(eo.eventName(), next.getUpperBound(), ruleIdx);
					ruleData.intervals().add(next);
				}
				return;
			}
		}
	}

	private void checkAge(final RuleData ruleData, final EventOccurrence eo) {
		final ContractRule rule = ruleData.rule();
		ruleData.markers().add(normalMarker(eo));
		for (int i = 0; i < rule.getOutputs().size(); i++) {
			if (eo.eventName().endsWith(rule.getOutputs().get(i))) {
				if (ruleData.triggerOccurred(eo, i)) {
					final CInterval inter = rule.getInterval();
					final CInterval next = inter
							.translate(eo.timestampNs() - inter.getLowerBound() * 2 - inter.getDiameter());
					ruleData.intervals().add(next);
					checkAgeHelper(ruleData, next, eo);
				}
				return;
			}
		}
	}

	private void checkAgeHelper(final RuleData ruleData, final CInterval interval, final EventOccurrence eo) {
		final var searchResult = ruleData.searchInterval(interval);
		final boolean fulfill = searchResult == SearchResult.VALID || searchResult == SearchResult.TOO_OFTEN;

		if (searchResult == SearchResult.TOO_OFTEN) {
			reactionAgeTooOftenError(eo, false);
		}
		if (ruleData.hasSlidingWindow()) {
			if (!ruleData.add2SlidingWindow(fulfill)) {
				final EventOccurrence mm = missedMarker(eo.eventName(), interval.getUpperBound());
				reactionAgeMissedSlidingWindowError(mm, ruleData.rule().getNOutOfM(), false);
				insertSorted(ruleData.markers(), mm);
			}
		} else if (!fulfill) {
			final EventOccurrence mm = missedMarker(eo.eventName(), interval.getUpperBound());
			reactionAgeMissedError(mm, false);
			insertSorted(ruleData.markers(), mm);
		}
	}

	private void checkCausalReaction(final RuleData ruleData, final EventOccurrence eo, final int ruleIdx) {
		final ContractRule rule = ruleData.rule();
		if (eo.type() == EventOccurrence.Type.MISSED_MARKER) {
			if (eo.state() == EventOccurrence.State.ISSUE) {
				eventMissedError(eo, Code.CAUSAL_REACTION_MISSED);
				ruleData.markers().add(eo);
			}
			return;
		}

		final String input = rule.getInputs().getFirst();
		if (eo.eventName().endsWith(input)) { // input occurred -> setup interval
			final CInterval interval = rule.getInterval().translate(eo.timestampNs());
			final EventOccurrence mm = createMissedMarker(rule, input, interval.getUpperBound(), ruleIdx, eo.eventID());
			if (!ruleData.rememberCausalEvent(mm)) {
				duplicateIDError(mm);
			}
			ruleData.intervals().add(interval);
			ruleData.markers().add(normalMarker(eo));
		} else { // output occurred -> perform check
			final EventOccurrence missedM = ruleData.getAssociatedCausalEvent(eo);
			if (missedM == null) {
				ruleData.markers().add(normalMarker(eo));
				return;
			}

			final CInterval inter = rule.getInterval();
			final CInterval checkI = inter
					.translate(missedM.timestampNs() - inter.getDiameter() - inter.getLowerBound());
			if (!checkI.contains(eo.timestampNs())) {
				eventOutsideIntervalError(checkI, eo, Code.CAUSAL_REACTION_TOO_EARLY, Code.CAUSAL_REACTION_TOO_LATE);
				ruleData.markers().add(issueMarker(eo));

				if (eo.timestampNs() <= checkI.getLowerBound()) {
					// do not raise an "event missed" issue when it is too early
					missedM.setState(EventOccurrence.State.FULFILLING);
				}
			} else {
				ruleData.markers().add(fulfillMarker(eo));
				missedM.setState(EventOccurrence.State.FULFILLING);
			}
		}
	}

	private void checkCausalAge(final RuleData ruleData, final EventOccurrence eo) {
		final ContractRule rule = ruleData.rule();

		final String input = rule.getInputs().getFirst();
		if (eo.eventName().endsWith(input)) { // input occurred -> setup queue/stack
			final EventOccurrence marker = normalMarker(eo);
			if (!ruleData.rememberCausalEvent(marker)) {
				duplicateIDError(marker);
			}
			ruleData.markers().add(marker);
		} else { // output occurred -> perform check
			final CInterval inter = rule.getInterval();
			final CInterval checkI = inter
					.translate(eo.timestampNs() - inter.getLowerBound() * 2 - inter.getDiameter());
			ruleData.markers().add(normalMarker(eo));
			ruleData.intervals().add(checkI);

			final EventOccurrence age = ruleData.getAssociatedCausalEvent(eo);
			if (age == null) {
				final String missedEventName = createKey(rule, rule.getInputs().getFirst());
				final EventOccurrence mm = missedMarker(missedEventName, checkI.getUpperBound());
				eventMissedError(mm, Code.CAUSAL_AGE_MISSED);
				insertSorted(ruleData.markers(), mm);
				return;
			}

			if (!checkI.contains(age.timestampNs())) {
				// also raise a missed issue when too late (for consistency with other rules)
				if (age.timestampNs() >= checkI.getUpperBound()) {
					final EventOccurrence mm = missedMarker(age.eventName(), checkI.getUpperBound());
					eventMissedError(mm, Code.CAUSAL_AGE_MISSED);
					insertSorted(ruleData.markers(), mm);
				}

				eventOutsideIntervalError(checkI, age, Code.CAUSAL_AGE_TOO_EARLY, Code.CAUSAL_AGE_TOO_LATE);
				age.setState(EventOccurrence.State.ISSUE);
			} else {
				age.setState(EventOccurrence.State.FULFILLING);
			}
		}
	}

	private static void insertSorted(final List<EventOccurrence> list, final EventOccurrence element) {
		int index = Collections.binarySearch(list, element);
		if (index < 0) {
			index = -index - 1;
		}
		list.add(index, element);
	}

	private void eventMissedError(final EventOccurrence eo, final Code code) {
		system.error(
				"\"%s\" did not arrive in time at %s.".formatted(eo.eventName(), Utils.nsToString(eo.timestampNs())),
				code);
	}

	private void reactionAgeMissedError(final EventOccurrence eo, final boolean isReaction) {
		final ContractRule.Type type = isReaction ? ContractRule.Type.REACTION : ContractRule.Type.AGE;
		final Code code = isReaction ? Code.REACTION_MISSED : Code.AGE_MISSED;
		system.error("%s for \"%s\" did not arrive in time at %s.".formatted(type, eo.eventName(),
				Utils.nsToString(eo.timestampNs())), code);
	}

	private void reactionAgeTooOftenError(final EventOccurrence eo, final boolean isReaction) {
		final ContractRule.Type type = isReaction ? ContractRule.Type.REACTION : ContractRule.Type.AGE;
		final Code code = isReaction ? Code.REACTION_TOO_OFTEN : Code.AGE_TOO_OFTEN;
		system.error("%s for \"%s\" occurred more than once until %s.".formatted(type, eo.eventName(),
				Utils.nsToString(eo.timestampNs())), code);
	}

	@SuppressWarnings("boxing")
	private void reactionAgeMissedSlidingWindowError(final EventOccurrence eo, final ContractRule.SlidingWindow window,
			final boolean isReaction) {
		final ContractRule.Type type = isReaction ? ContractRule.Type.REACTION : ContractRule.Type.AGE;
		final Code code = isReaction ? Code.REACTION_MISSED : Code.AGE_MISSED;
		system.error("%s for \"%s\" did not arrive %d out of %d times at %s.".formatted(type, eo.eventName(),
				window.n(), window.outOf(), Utils.nsToString(eo.timestampNs())), code);
	}

	private void eventOutsideIntervalError(final CInterval interval, final EventOccurrence eo, final Code tooEarly,
			final Code tooLate) {
		if (eo.timestampNs() <= interval.getLowerBound()) {
			system.error("\"%s\" occurred %s too early.".formatted(eo.eventName(),
					Utils.nsToString(interval.getLowerBound() - eo.timestampNs())), tooEarly);
		} else {
			system.error("\"%s\" occurred %s too late.".formatted(eo.eventName(),
					Utils.nsToString(eo.timestampNs() - interval.getUpperBound())), tooLate);
		}
	}

	private void duplicateIDError(final EventOccurrence eo) {
		system.error("The event ID \"%s\" was already in use for \"%s\"".formatted(eo.eventID(), eo.eventName()),
				ContractIssue.Code.DUPLICATE_CAUSAL_ID);
	}

	private EventOccurrence createMissedMarker(final ContractRule rule, final String port, final double time,
			final int ruleIdx) {
		return createMissedMarker(createKey(rule, port), time, ruleIdx);
	}

	private EventOccurrence createMissedMarker(final ContractRule rule, final String port, final double time,
			final int ruleIdx, final String eventID) {
		return createMissedMarker(createKey(rule, port), time, ruleIdx, eventID);
	}

	private EventOccurrence createMissedMarker(final String key, final double time, final int ruleIdx) {
		return createMissedMarker(key, time, ruleIdx, null);
	}

	private EventOccurrence createMissedMarker(final String key, final double time, final int ruleIdx,
			final String eventID) {
		final EventOccurrence eo = new EventOccurrence(key, time, EventOccurrence.Type.MISSED_MARKER,
				EventOccurrence.State.ISSUE, ruleIdx, eventID);
		queue.offer(eo);
		return eo;
	}

	private static EventOccurrence missedMarker(final String name, final double time) {
		return new EventOccurrence(name, time, EventOccurrence.Type.MISSED_MARKER, EventOccurrence.State.ISSUE, 0);
	}

	private static EventOccurrence normalMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), eo.type(), EventOccurrence.State.NOT_SET, 0,
				eo.eventID());
	}

	private static EventOccurrence issueMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), eo.type(), EventOccurrence.State.ISSUE, 0,
				eo.eventID());
	}

	private static EventOccurrence fulfillMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), eo.type(), EventOccurrence.State.FULFILLING, 0,
				eo.eventID());
	}
}
