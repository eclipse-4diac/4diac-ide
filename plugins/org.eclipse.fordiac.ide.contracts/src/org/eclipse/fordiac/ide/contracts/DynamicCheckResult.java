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
import java.util.Arrays;
import java.util.List;

public record DynamicCheckResult(ContractSystem system, List<RuleData> rules) {

	public static class RuleData {

		private final ContractRule rule;
		private final List<CInterval> intervals;
		private final List<EventOccurrence> markers;

		private final boolean[] slidingWindow;
		private final boolean[] triggerSet;
		private int triggerSequence;

		public RuleData(final ContractRule rule) {
			this.rule = rule;
			intervals = new ArrayList<>();
			markers = new ArrayList<>();

			final ContractRule.SlidingWindow nOutOfM = rule.getNOutOfM();
			if (nOutOfM != null && nOutOfM.n() != nOutOfM.outOf()) {
				slidingWindow = new boolean[nOutOfM.outOf()];
				Arrays.fill(slidingWindow, true); // all true at first
			} else {
				slidingWindow = null;
			}

			if (rule.getType() == ContractRule.Type.REACTION) {
				triggerSet = new boolean[rule.getInputs().size()];
			} else {
				triggerSet = null;
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
			if (rule.getType() == ContractRule.Type.REACTION ? rule.inputIsSequence() : rule.outputIsSequence()) {
				return sequenceTrigger(eo);
			}
			return setTrigger(index);
		}

		private boolean sequenceTrigger(final EventOccurrence eo) {
			final List<String> seq = rule.getType() == ContractRule.Type.REACTION ? rule.getInputs()
					: rule.getOutputs();
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
