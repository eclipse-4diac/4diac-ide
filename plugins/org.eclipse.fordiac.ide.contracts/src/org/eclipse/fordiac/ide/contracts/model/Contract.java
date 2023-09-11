/*******************************************************************************
 * Copyright (c) 2023 Paul Pavlicek
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Paul Pavlicek
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.Messages;
import org.eclipse.fordiac.ide.contracts.model.ContractConstants.ContractState;
import org.eclipse.fordiac.ide.contracts.model.helpers.ContractUtils;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public class Contract {

	private final EList<Assumption> assumptions = new BasicEList<>();
	private final EList<Guarantee> guarantees = new BasicEList<>();
	private FBNetworkElement owner;
	private Optional<String> error;
	private ContractState state = ContractState.UNKNOWN;

	Contract() {
		// reduce visibility to package
	}

	public static Contract getContractFromComment(final String comment) {
		final Contract contract = new Contract();
		final String[] lines = comment.split(System.lineSeparator());
		for (final String line : lines) {
			if (line.startsWith(ContractKeywords.ASSUMPTION)) {
				final Assumption toAdd = Assumption.createAssumption(line);
				contract.add(toAdd, contract);
			} else if (line.startsWith(ContractKeywords.GUARANTEE)) {
				final Guarantee toAdd = Guarantee.createGuarantee(line);
				contract.add(toAdd, contract);
			}
		}
		return contract;
	}

	public String getError() {
		return error.orElse(""); //$NON-NLS-1$
	}

	void addError(final String error) {
		this.error = Optional.ofNullable(error);
	}

	public void setOwner(final FBNetworkElement owner) {
		state = ContractState.UNKNOWN;
		this.owner = owner;
	}

	public FBNetworkElement getOwner() {
		return owner;
	}

	public EList<Assumption> getAssumptions() {
		return assumptions;
	}

	public EList<Guarantee> getGuarantees() {
		return guarantees;
	}

	void add(final Assumption assumption, final Contract owner) {
		assumption.setContract(owner);
		state = ContractState.UNKNOWN;
		assumptions.add(assumption);

	}

	void add(final Guarantee guarantee, final Contract owner) {
		guarantee.setContract(owner);
		state = ContractState.UNKNOWN;
		guarantees.add(guarantee);
	}

	void removeAssumption(final int index) {
		state = ContractState.UNKNOWN;
		final Assumption removedAssumption = assumptions.remove(index);
		removedAssumption.setContract(null);
	}

	void removeGuarantee(final int index) {
		state = ContractState.UNKNOWN;
		final Guarantee removedGuarantee = guarantees.remove(index);
		removedGuarantee.setContract(null);
	}

	public boolean isValid() {
		if (state == ContractState.UNKNOWN) {
			checkValidity();
		}
		return state == ContractState.VALID;
	}

	public Guarantee getGuaranteeWith(final String inputEvent) {
		return guarantees.stream().filter(g -> g.getInputEvent().equals(inputEvent)).findAny().orElse(null);
	}

	public Assumption getAssumptionWith(final String InputEvent) {
		return assumptions.stream().filter(a -> a.getInputEvent().equals(InputEvent)).findAny().orElse(null);
	}

	public void writeToOwner() {
		if (owner instanceof final SubApp subapp) {
			if (!subapp.getName().startsWith(ContractKeywords.CONTRACT)) {
				subapp.setName(
						NameRepository.createUniqueName(subapp, ContractKeywords.CONTRACT + "_" + subapp.getName())); //$NON-NLS-1$
			}

			subapp.setComment(this.getAsString());
		}
	}

	public String getAsString() {
		final StringBuilder comment = new StringBuilder();
		for (final Assumption assumption : assumptions) {
			comment.append(assumption.asString());
		}
		for (final Guarantee guarantee : guarantees) {
			comment.append(guarantee.asString());
		}
		return comment.toString();
	}

	private void checkValidity() {
		if (!ContractUtils.isContractSubapp(owner)) {
			addError(Messages.Contract_ErrorName);
			state = ContractState.INVALID;
			return;
		}
		if (assumptions.isEmpty() && guarantees.isEmpty()) {
			addError(Messages.Contract_ErrorElements);
			state = ContractState.INVALID;
			return;
		}

		for (final Assumption assumption : assumptions) {
			if (!assumption.isValid()) {
				addError(Messages.Contract_ErrorAssumption + assumption.asString());
				state = ContractState.INVALID;
				return;
			}
		}
		for (final Guarantee guarantee : guarantees) {
			if (!guarantee.isValid()) {
				addError(Messages.Contract_ErrorGuarantee + guarantee.asString());
				state = ContractState.INVALID;
				return;
			}
		}
		if (!hasConsistentAssumptions()) {
			addError(Messages.Contract_ErrorIncosistentAssumptions);
			state = ContractState.INVALID;
			return;
		}
		if (!hasConsistentGuarantees()) {
			addError(Messages.Contract_ErrorIncosistentGuarantees);
			state = ContractState.INVALID;
			return;
		}
		if (!hasConsistentContract()) {
			addError(Messages.Contract_ErrorAssumptionsGuarantees);
			state = ContractState.INVALID;
			return;
		}
		state = ContractState.VALID;
	}

	private boolean hasConsistentContract() {
		final Map<String, EList<ContractElement>> mapContractElements = new HashMap<>();
		fillContractElementMap(mapContractElements);
		for (final Map.Entry<String, EList<ContractElement>> entry : mapContractElements.entrySet()) {
			if (!isAssumptionGuaranteePair(entry)) {
				return false;
			}

			final int assumptionMin = entry.getValue().get(0).getMin();
			final int guaranteeMax = entry.getValue().get(1).getMax();
			if (assumptionMin > guaranteeMax) {
				return false;
			}
		}
		return true;
	}

	private static boolean isAssumptionGuaranteePair(final Map.Entry<String, EList<ContractElement>> entry) {
		return entry.getValue().size() == 2;
	}

	private void fillContractElementMap(final Map<String, EList<ContractElement>> mapContractElements) {
		for (final Assumption assumption : assumptions) {
			if (mapContractElements.containsKey(assumption.getInputEvent())) {
				mapContractElements.get(assumption.getInputEvent()).add(assumption);
			} else {
				final EList<ContractElement> toAdd = new BasicEList<>();
				toAdd.add(assumption);
				mapContractElements.put(assumption.getInputEvent(), toAdd);
			}
		}
		for (final Guarantee guarantee : guarantees) {
			if (mapContractElements.containsKey(guarantee.getInputEvent())) {
				mapContractElements.get(guarantee.getInputEvent()).add(guarantee);
			} else {
				final EList<ContractElement> toAdd = new BasicEList<>();
				toAdd.add(guarantee);
				mapContractElements.put(guarantee.getInputEvent(), toAdd);
			}
		}
	}

	private boolean hasConsistentGuarantees() {
		final Map<String, EList<Guarantee>> mapGuarantees = new HashMap<>();
		for (final Guarantee guarantee : guarantees) {
			if (mapGuarantees.containsKey(guarantee.getInputEvent())) {
				mapGuarantees.get(guarantee.getInputEvent()).add(guarantee);
			} else {
				final EList<Guarantee> toAdd = new BasicEList<>();
				toAdd.add(guarantee);
				mapGuarantees.put(guarantee.getInputEvent(), toAdd);
			}
		}
		for (final Map.Entry<String, EList<Guarantee>> entry : mapGuarantees.entrySet()) {
			if (entry.getValue().size() != 1 && !Guarantee.isCompatibleWith(entry.getValue())) {
				return false;
			}
		}
		return true;
	}

	private boolean hasConsistentAssumptions() {
		final Map<String, EList<Assumption>> mapAssumptions = new HashMap<>();
		for (final Assumption assumption : assumptions) {
			if (mapAssumptions.containsKey(assumption.getInputEvent())) {
				mapAssumptions.get(assumption.getInputEvent()).add(assumption);
			} else {
				final EList<Assumption> toAdd = new BasicEList<>();
				toAdd.add(assumption);
				mapAssumptions.put(assumption.getInputEvent(), toAdd);
			}
		}
		for (final Map.Entry<String, EList<Assumption>> entry : mapAssumptions.entrySet()) {
			if (entry.getValue().size() != 1 && !Assumption.isCompatibleWith(entry.getValue())) {
				return false;
			}
		}
		return true;
	}

}
