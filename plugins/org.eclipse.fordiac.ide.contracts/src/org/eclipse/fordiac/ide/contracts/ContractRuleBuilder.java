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

import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.CausalReaction;
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.Reaction;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.SingleEvent;

class ContractRuleBuilder {

	ContractSystem system;
	ContractComponent component;

	ContractRuleBuilder(final ContractSystem system, final ContractComponent component) {
		this.system = system;
		this.component = component;
	}

	void addSingleEvent(final SingleEvent se) {
		// decompose a single event with multiple events into multiple rules
		for (final EventSpec e : se.getEvents().getEvents()) {
			addRule(new ContractRule(e, se.getInterval()));
		}
	}

	void addRepetition(final Repetition re) {
		// decompose a repetition with multiple events into multiple rules
		for (final EventSpec e : re.getEvents().getEvents()) {
			addRule(new ContractRule(e, re.getInterval(), re.getRepetitionOptions()));
		}
	}

	void addReaction(final Reaction re) {
		addRule(ContractRule.Type.REACTION, re.getInput(), re.getOutput(), re.getInterval(), re.isOnce(), re.getN(),
				re.getOutOf());
	}

	void addAge(final Age age) {
		addRule(ContractRule.Type.AGE, age.getInput(), age.getOutput(), age.getInterval(), age.isOnce(), age.getN(),
				age.getOutOf());
	}

	void addCausalReaction(final CausalReaction cRe) {
		addRule(new ContractRule(ContractRule.Type.CAUSAL_REACTION, cRe.getInput(), cRe.getOutput(),
				cRe.getInterval()));
	}

	void addCausalAge(final CausalAge cAge) {
		addRule(new ContractRule(ContractRule.Type.CAUSAL_AGE, cAge.getInput(), cAge.getOutput(), cAge.getInterval()));
	}

	private void addRule(final ContractRule.Type type, final EventExpr input, final EventExpr output,
			final Interval interval, final boolean once, final int n, final int m) {
		final ContractRule rule = new ContractRule(type, input, output, interval);
		rule.setOnce(once);
		rule.setNOutOfM(new ContractRule.SlidingWindow(n, m));
		addRule(rule);
	}

	private void addRule(final ContractRule rule) {
		component.addRule(rule, system);
	}
}
