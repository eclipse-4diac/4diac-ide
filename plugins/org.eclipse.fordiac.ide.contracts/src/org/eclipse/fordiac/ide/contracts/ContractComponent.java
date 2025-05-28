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

import org.eclipse.xtext.diagnostics.Severity;

public class ContractComponent {
	private final List<CConnection> inputs;
	private final Map<String, ContractRule> assumptions;
	private final Map<String, ContractRule> guarantees;
	private final String name;

	public ContractComponent(final String name) {
		this.name = name;
		assumptions = new HashMap<>();
		guarantees = new HashMap<>();
		inputs = new ArrayList<>();
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

	public String getName() {
		return name;
	}

	public ContractIssue addRule(final ContractRule rule) {
		rule.setOwner(this);
		if (rule.isAssumption()) {
			if (assumptions.containsKey(rule.getInput())) {
				return new ContractIssue(Messages.ContractConflictingAssumptionsError
						.formatted(rule.getOwner().getName(), rule.getInput()),
						ContractIssue.Code.CONFLICTING_ASSUMPTIONS, Severity.ERROR);
			}
			assumptions.put(rule.getInput(), rule);
		} else if (guarantees.containsKey(rule.getOutput())) {
			return new ContractIssue(
					Messages.ContractConflictingGuaranteesError.formatted(rule.getOwner().getName(), rule.getOutput()),
					ContractIssue.Code.CONFLICTING_GUARANTEES, Severity.ERROR);
		} else {
			guarantees.put(rule.getOutput(), rule);
		}
		return null;
	}
}
