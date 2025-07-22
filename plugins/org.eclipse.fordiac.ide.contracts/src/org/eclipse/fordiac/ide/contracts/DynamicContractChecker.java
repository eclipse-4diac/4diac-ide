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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contracts.DynamicCheckResult.RuleData;

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
		case CAUSAL_REACTION -> checkCausalReaction(ruleData, eo);
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
				eventMissedError(eo, ContractIssue.Code.SINGLE_EVENT_MISSED);
				ruleData.markers().add(eo);
			}
			return;
		}

		if (rule.isFulFilled()) {
			system.error("\"%s\" occurred more than once.".formatted(eo.eventName()),
					ContractIssue.Code.SINGLE_EVENT_MULTIPLE);
			ruleData.markers().add(issueMarker(eo));
		} else if (!rule.getInterval().contains(eo.timestampNs())) {
			eventOutsideIntervalError(rule.getInterval(), eo, ContractIssue.Code.SINGLE_EVENT_TOO_EARLY,
					ContractIssue.Code.SINGLE_EVENT_TOO_LATE);
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
				eventMissedError(eo, ContractIssue.Code.REPETITION_MISSED);
				ruleData.markers().add(eo);
			}
			return;
		}

		final double shift;
		if (!intervalJitter.contains(eo.timestampNs())) {
			eventOutsideIntervalError(intervalJitter, eo, ContractIssue.Code.REPETITION_TOO_EARLY,
					ContractIssue.Code.REPETITION_TOO_LATE);
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
			final double until = eo.timestampNs() - rule.getInterval().getDiameter();
			final boolean fulfill = searchFor(ruleData, rule.outputIsSequence(), until, rule.isOnce());

			if (ruleData.hasSlidingWindow()) {
				if (!ruleData.add2SlidingWindow(fulfill)) {
					reactionMissedSlidingWindowError(eo, rule.getNOutOfM(), ContractIssue.Code.REACTION_MISSED);
					ruleData.markers().add(eo);
				}
			} else if (!fulfill) {
				reactionMissedError(eo, ContractIssue.Code.REACTION_MISSED);
				ruleData.markers().add(eo);
			}
			return;
		}

		ruleData.markers().add(eo);
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

	private static boolean searchFor(final RuleData ruleData, final boolean isSequence, final double until,
			final boolean once) {
		if (isSequence) {
			return searchForSequence(ruleData, until, once);
		}
		return searchForSet(ruleData, until, once);
	}

	private static boolean searchForSequence(final RuleData ruleData, final double until, final boolean once) {
		// TODO: once - sequence only occurs once
		final List<String> seq = ruleData.rule().getOutputs();
		int sequenceIdx = seq.size() - 1;

		// search backwards for event sequence
		for (int i = ruleData.markers().size() - 1; i > 0; i--) {
			final EventOccurrence eo = ruleData.markers().get(i);
			if (eo.type() != EventOccurrence.Type.RECORDED) {
				continue;
			}
			if (eo.timestampNs() < until) {
				return false;
			}
			if (eo.eventName().endsWith(seq.get(sequenceIdx))) {
				sequenceIdx--;
				if (sequenceIdx < 0) {
					// update state of occurrences in sequence
					for (int j = 0; j < seq.size(); j++) {
						final EventOccurrence eoSeq = ruleData.markers().get(i + j);
						eoSeq.setState(EventOccurrence.State.FULFILLING);
					}
					return true;
				}
			}
		}
		return false;
	}

	private static boolean searchForSet(final RuleData ruleData, final double until, final boolean once) {
		// TODO: once - set only occurs once
		final Set<String> set = new HashSet<>(ruleData.rule().getOutputs());
		final List<EventOccurrence> fulfill = new ArrayList<>(set.size());

		// search backwards for event set
		for (int i = ruleData.markers().size() - 1; i > 0; i--) {
			final EventOccurrence eo = ruleData.markers().get(i);
			if (eo.type() != EventOccurrence.Type.RECORDED) {
				continue;
			}
			if (eo.timestampNs() < until) {
				return false;
			}
			if (set.remove(eo.getShortName())) {
				fulfill.add(eo);
				if (set.isEmpty()) {
					// update state of occurrences in set
					for (final EventOccurrence eoSet : fulfill) {
						eoSet.setState(EventOccurrence.State.FULFILLING);
					}
					return true;
				}
			}
		}
		return false;
	}

	private void checkAge(final RuleData ruleData, final EventOccurrence eo) {
		// TODO
	}

	private void checkCausalReaction(final RuleData ruleData, final EventOccurrence eo) {
		// TODO
	}

	private void checkCausalAge(final RuleData ruleData, final EventOccurrence eo) {
		// TODO
	}

	private void eventMissedError(final EventOccurrence eo, final ContractIssue.Code code) {
		system.error(
				"\"%s\" did not arrive in time at %s.".formatted(eo.eventName(), Utils.nsToString(eo.timestampNs())),
				code);
	}

	private void reactionMissedError(final EventOccurrence eo, final ContractIssue.Code code) {
		system.error("Reaction for \"%s\" did not arrive in time at %s.".formatted(eo.eventName(),
				Utils.nsToString(eo.timestampNs())), code);
	}

	@SuppressWarnings("boxing")
	private void reactionMissedSlidingWindowError(final EventOccurrence eo, final ContractRule.SlidingWindow window,
			final ContractIssue.Code code) {
		system.error("Reaction for \"%s\" did not arrive %d out of %d times at %s.".formatted(eo.eventName(),
				window.n(), window.outOf(), Utils.nsToString(eo.timestampNs())), code);
	}

	private void eventOutsideIntervalError(final CInterval interval, final EventOccurrence eo,
			final ContractIssue.Code tooEarly, final ContractIssue.Code tooLate) {
		if (eo.timestampNs() <= interval.getLowerBound()) {
			system.error("\"%s\" occurred %s too early.".formatted(eo.eventName(),
					Utils.nsToString(interval.getLowerBound() - eo.timestampNs())), tooEarly);
		} else {
			system.error("\"%s\" occurred %s too late.".formatted(eo.eventName(),
					Utils.nsToString(eo.timestampNs() - interval.getUpperBound())), tooLate);
		}
	}

	private void createMissedMarker(final ContractRule rule, final String port, final double time, final int i) {
		createMissedMarker(createKey(rule, port), time, i);
	}

	private void createMissedMarker(final String key, final double time, final int i) {
		queue.offer(new EventOccurrence(key, time, EventOccurrence.Type.MISSED_MARKER, EventOccurrence.State.ISSUE, i));
	}

	private static EventOccurrence issueMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), eo.type(), EventOccurrence.State.ISSUE, 0);
	}

	private static EventOccurrence fulfillMarker(final EventOccurrence eo) {
		return new EventOccurrence(eo.eventName(), eo.timestampNs(), eo.type(), EventOccurrence.State.FULFILLING, 0);
	}
}
