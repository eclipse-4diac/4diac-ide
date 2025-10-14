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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.fordiac.ide.contracts.ContractRule.Type;

/**
 * Data structure that is created by the dynamic checker. Used to store various
 * internal states of rules during the dynamic check. Afterwards provided to the
 * result dialog for visualization.
 */
public record DynamicCheckResult(ContractSystem system, List<RuleData> rules) {

	public static class RuleData {

		public enum SearchResult {
			VALID, MISSED, TOO_OFTEN
		}

		private final ContractRule rule;
		private final List<CInterval> intervals;
		private final List<EventOccurrence> markers;

		private Deque<EventOccurrence> causalFIFO;
		private Deque<EventOccurrence> causalLIFO;
		private Map<String, EventOccurrence> causalID;
		private final boolean[] slidingWindow;
		private boolean[] triggerSet;
		private int triggerSequence;

		public RuleData(final ContractRule rule) {
			this.rule = rule;
			intervals = new ArrayList<>();
			markers = new ArrayList<>();

			final ContractRule.SlidingWindow nOutOfM = rule.getNOutOfM();
			if (nOutOfM != null && nOutOfM.n() != nOutOfM.outOf()) {
				slidingWindow = new boolean[nOutOfM.outOf()];
				Arrays.fill(slidingWindow, true); // sliding window fulfilled at start
			} else {
				slidingWindow = null;
			}

			if (rule.getType() == Type.REACTION || rule.getType() == Type.AGE) {
				triggerSet = new boolean[rule.getInputs().size()];
			}

			if (rule.getType() == Type.CAUSAL_REACTION || rule.getType() == Type.CAUSAL_AGE) {
				switch (rule.getCausalRelation()) {
				case FIFO:
					causalFIFO = new ArrayDeque<>();
					break;
				case LIFO:
					causalLIFO = new ArrayDeque<>();
					break;
				case ID:
					causalID = new HashMap<>();
					break;
				default: // no other types yet
					break;
				}
			}
		}

		public boolean hasSlidingWindow() {
			return slidingWindow != null;
		}

		/**
		 * adds a new entry to the sliding window
		 *
		 * @param fulfill whether the new entry is fulfilled
		 * @return whether the resulting sliding window is fulfilled
		 */
		public boolean add2SlidingWindow(final boolean fulfill) {
			int fulfillCount = 0;
			// shift previous entries to make space for new
			for (int i = 0; i < slidingWindow.length - 1; i++) {
				slidingWindow[i] = slidingWindow[i + 1];
				if (slidingWindow[i]) {
					fulfillCount++;
				}
			}
			// add new entry as last
			slidingWindow[slidingWindow.length - 1] = fulfill;
			if (fulfill) {
				fulfillCount++;
			}
			// return whether new sliding window is fulfilled
			return fulfillCount >= rule.getNOutOfM().n();
		}

		/**
		 * report that a new trigger event has occurred (e.g. an input for a reaction or
		 * an output for an age)
		 *
		 * @param eo    the trigger event that occurred
		 * @param index the index of the event in the rule port list
		 * @return whether this completes the sequence/set causing a reaction/age
		 */
		public boolean triggerOccurred(final EventOccurrence eo, final int index) {
			if (rule.getType() == Type.REACTION ? rule.inputIsSequence() : rule.outputIsSequence()) {
				return sequenceTrigger(eo);
			}
			return setTrigger(index);
		}

		private boolean sequenceTrigger(final EventOccurrence eo) {
			final List<String> seq = rule.getType() == Type.REACTION ? rule.getInputs() : rule.getOutputs();
			if (eo.eventName().endsWith(seq.get(triggerSequence))) {
				triggerSequence++;
				if (triggerSequence >= seq.size()) {
					triggerSequence = 0; // reset for next
					return true;
				}
			}
			return false;
		}

		private boolean setTrigger(final int index) {
			triggerSet[index] = true;

			for (final boolean entry : triggerSet) {
				if (!entry) {
					return false;
				}
			}
			Arrays.fill(triggerSet, false); // reset for next
			return true;
		}

		/**
		 * report that a new causal event has occurred (e.g. an input for a causal
		 * reaction or an output for a causal age)
		 *
		 * @param eo the event that occurred which should be remembered
		 * @return false if an event with the same ID is already in use, otherwise true
		 */
		public boolean rememberCausalEvent(final EventOccurrence eo) {
			if (causalFIFO != null) {
				causalFIFO.offer(eo);
			} else if (causalLIFO != null) {
				causalLIFO.push(eo);
			} else if (causalID != null) {
				return causalID.put(eo.eventID(), eo) == null;
			}
			return true;
		}

		/**
		 * returns the associated causal event which has been reported via
		 * <code>rememberCausalEvent()</code>
		 *
		 * @param eo the event for which the associated event should be returned
		 * @return the associated causal event or null if there is none
		 */
		public EventOccurrence getAssociatedCausalEvent(final EventOccurrence eo) {
			if (causalFIFO != null) {
				return causalFIFO.isEmpty() ? null : causalFIFO.poll();
			}
			if (causalLIFO != null) {
				return causalLIFO.isEmpty() ? null : causalLIFO.pop();
			}
			if (causalID != null) {
				return causalID.remove(eo.eventID());
			}
			return null;
		}

		/**
		 * searches if the rules reaction or age is fulfilled within the given interval
		 * based on the recorded event occurrence markers
		 *
		 * @param interval the interval to search within
		 * @return whether the searched interval is valid or has an issue
		 */
		public SearchResult searchInterval(final CInterval interval) {
			final List<String> ports;
			final boolean isSequence;
			if (rule.getType() == Type.REACTION) {
				ports = rule.getOutputs();
				isSequence = rule.outputIsSequence();
			} else {
				ports = rule.getInputs();
				isSequence = rule.inputIsSequence();
			}

			if (isSequence) {
				return searchForSequence(interval, ports, rule.isOnce());
			}
			return searchForSet(interval, ports, rule.isOnce());
		}

		private SearchResult searchForSequence(final CInterval inter, final List<String> ports, boolean once) {
			int sequenceIdx = 0;
			int eventIdx = firstIndex(markers, inter);
			boolean checkingOnce = false;

			for (final EventOccurrence eo : iterateInterval(markers, inter)) {
				eventIdx++;
				if (eo.type() != EventOccurrence.Type.RECORDED) {
					continue;
				}
				if (eo.eventName().endsWith(ports.get(sequenceIdx))) {
					sequenceIdx++;
					if (sequenceIdx >= ports.size()) {
						// update state of occurrences in sequence
						final EventOccurrence.State state = checkingOnce ? EventOccurrence.State.ISSUE
								: EventOccurrence.State.FULFILLING;
						for (int j = 0; j < ports.size(); j++) {
							final EventOccurrence eoSeq = markers.get(eventIdx - ports.size() + j);
							eoSeq.setState(state);
						}
						if (once) { // reset and continue to check for once violations
							sequenceIdx = 0;
							once = false;
							checkingOnce = true;
						} else if (checkingOnce) { // we reached this again, violating "once"
							return SearchResult.TOO_OFTEN;
						} else {
							return SearchResult.VALID;
						}
					}
				}
			}
			return checkingOnce ? SearchResult.VALID : SearchResult.MISSED;
		}

		private SearchResult searchForSet(final CInterval inter, final List<String> ports, boolean once) {
			final Set<String> set = new HashSet<>(ports);
			final List<EventOccurrence> fulfill = new ArrayList<>(set.size());
			boolean checkingOnce = false;

			for (final EventOccurrence eo : iterateInterval(markers, inter)) {
				if (eo.type() != EventOccurrence.Type.RECORDED) {
					continue;
				}
				if (set.remove(eo.getShortName())) {
					fulfill.add(eo);
					if (set.isEmpty()) {
						// update state of occurrences in set
						final EventOccurrence.State state = checkingOnce ? EventOccurrence.State.ISSUE
								: EventOccurrence.State.FULFILLING;
						for (final EventOccurrence eoSet : fulfill) {
							eoSet.setState(state);
						}
						if (once) { // reset and continue to check for once violations
							set.addAll(ports);
							fulfill.clear();
							once = false;
							checkingOnce = true;
						} else if (checkingOnce) { // we reached this again, violating "once"
							return SearchResult.TOO_OFTEN;
						} else {
							return SearchResult.VALID;
						}
					}
				}
			}
			return checkingOnce ? SearchResult.VALID : SearchResult.MISSED;
		}

		private static Iterable<EventOccurrence> iterateInterval(final List<EventOccurrence> list,
				final CInterval interval) {
			return () -> new Iterator<>() {

				int index = firstIndex(list, interval);

				@Override
				public boolean hasNext() {
					return index < list.size() && interval.contains(list.get(index).timestampNs());
				}

				@Override
				public EventOccurrence next() {
					if (!hasNext()) {
						throw new NoSuchElementException();
					}
					final int i = index;
					index++;
					return list.get(i);
				}
			};
		}

		private static int firstIndex(final List<EventOccurrence> list, final CInterval interval) {
			final EventOccurrence key = new EventOccurrence("", interval.getLowerBound()); //$NON-NLS-1$

			int index = Collections.binarySearch(list, key);
			if (index < 0) {
				// if result <0, binarySearch returns the insertion point (see documentation)
				index = Math.abs(index + 1);
			}
			if (index < list.size() && !interval.contains(list.get(index).timestampNs())) {
				index++; // can happen because lower bound is open in interval
			}
			return index;
		}

		public ContractRule rule() {
			return rule;
		}

		public List<CInterval> intervals() {
			return intervals;
		}

		public List<EventOccurrence> markers() {
			return markers;
		}
	}

	public DynamicCheckResult(final ContractSystem system) {
		this(system, new ArrayList<>());
	}

	public List<ContractIssue> issues() {
		return system.getIssues();
	}
}
