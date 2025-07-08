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

import java.util.List;

import org.eclipse.fordiac.ide.contractSpec.Age;
import org.eclipse.fordiac.ide.contractSpec.CausalAge;
import org.eclipse.fordiac.ide.contractSpec.CausalReaction;
import org.eclipse.fordiac.ide.contractSpec.EventExpr;
import org.eclipse.fordiac.ide.contractSpec.EventSpec;
import org.eclipse.fordiac.ide.contractSpec.Interval;
import org.eclipse.fordiac.ide.contractSpec.Reaction;
import org.eclipse.fordiac.ide.contractSpec.Repetition;
import org.eclipse.fordiac.ide.contractSpec.SingleEvent;
import org.eclipse.xtext.diagnostics.Severity;

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
		addRules(ContractRule.Type.REACTION, re.getInput(), re.getOutput(), re.getInterval());
	}

	void addAge(final Age age) {
		addRules(ContractRule.Type.AGE, age.getInput(), age.getOutput(), age.getInterval());
	}

	void addCausalReaction(final CausalReaction cRe) {
		addRule(new ContractRule(ContractRule.Type.CAUSAL_REACTION, cRe.getInput(), cRe.getOutput(),
				cRe.getInterval()));
	}

	void addCausalAge(final CausalAge cAge) {
		addRule(new ContractRule(ContractRule.Type.CAUSAL_AGE, cAge.getInput(), cAge.getOutput(), cAge.getInterval()));
	}

	private void addRules(final ContractRule.Type type, final EventExpr input, final EventExpr output,
			final Interval interval) {
		final List<EventSpec> inputs = input.getEvent() != null ? List.of(input.getEvent())
				: input.getEvents().getEvents();
		final List<EventSpec> outputs = output.getEvent() != null ? List.of(output.getEvent())
				: output.getEvents().getEvents();

		// TODO: temporary issues until implemented...
		if (inputs.size() > 1) {
			system.addIssue(new ContractIssue("Reaction with multiple inputs not yet supported.", //$NON-NLS-1$
					ContractIssue.Code.UNKOWN, Severity.WARNING));
			return;
		}
		if (outputs.size() > 1) {
			system.addIssue(new ContractIssue("Reaction with multiple outputs not yet supported.", //$NON-NLS-1$
					ContractIssue.Code.UNKOWN, Severity.WARNING));
			return;
		}
		addRule(new ContractRule(type, inputs.get(0), outputs.get(0), interval));
	}

	private void addRule(final ContractRule rule) {
		final ContractIssue issue = component.addRule(rule);
		if (issue != null) {
			system.addIssue(issue);
		}
	}
}
