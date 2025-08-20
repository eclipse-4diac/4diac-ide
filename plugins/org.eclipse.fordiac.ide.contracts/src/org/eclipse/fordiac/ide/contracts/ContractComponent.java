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
import java.util.List;
import java.util.Map;

/**
 * Used for the ContractSystem data structure and represents a sub application.
 * Holds a set of ContractRules and connections to other ContractComponents.
 */
public class ContractComponent {

	private final List<CConnection> inputs;
	private final Map<String, ContractRule> assumptions;
	private final Map<String, ContractRule> guarantees;
	private final List<ContractRule> reactions;
	private final String name;

	public ContractComponent(final String name) {
		this.name = name;
		assumptions = new HashMap<>();
		guarantees = new HashMap<>();
		inputs = new ArrayList<>();
		reactions = new ArrayList<>();
	}

	public List<CConnection> getInputs() {
		return inputs;
	}

	public void addInput(final ContractComponent from, final String fromPort, final String toPort) {
		addInput(from, fromPort, toPort, CConnection.Type.NORMAL);
	}

	public void addInput(final ContractComponent from, final String fromPort, final String toPort,
			final CConnection.Type type) {
		inputs.add(new CConnection(from, fromPort, toPort, type));
	}

	public Map<String, ContractRule> getAssumptions() {
		return assumptions;
	}

	public Map<String, ContractRule> getGuarantees() {
		return guarantees;
	}

	public List<ContractRule> getReactions() {
		return reactions;
	}

	public String getName() {
		return name;
	}

	public void addRule(final ContractRule rule, final ContractSystem system) {
		rule.setOwner(this);
		if (rule.getType() == ContractRule.Type.SINGLE_EVENT || rule.getType() == ContractRule.Type.REPETITION) {
			addSingleOrRepetition(rule, system);
		} else {
			reactions.add(rule);
		}
	}

	private void addSingleOrRepetition(final ContractRule rule, final ContractSystem system) {
		if (rule.isAssumption()) {
			final String input = rule.getInputs().getFirst();
			if (assumptions.put(input, rule) != null) {
				system.error(Messages.ContractConflictingAssumptionsError.formatted(rule.getOwner().getName(), input),
						ContractIssue.Code.CONFLICTING_ASSUMPTIONS);
			}
		} else {
			final String output = rule.getOutputs().getFirst();
			if (guarantees.put(output, rule) != null) {
				system.error(Messages.ContractConflictingGuaranteesError.formatted(rule.getOwner().getName(), output),
						ContractIssue.Code.CONFLICTING_GUARANTEES);
			}
		}
	}
}
