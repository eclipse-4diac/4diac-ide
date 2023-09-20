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
import org.eclipse.fordiac.ide.contracts.exceptions.ContractExeption;
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
		try {
			for (final String line : lines) {

				if (line.startsWith(ContractKeywords.ASSUMPTION)) {
					final Assumption toAdd = Assumption.createAssumption(line);
					contract.add(toAdd, contract);
				} else if (line.startsWith(ContractKeywords.GUARANTEE)) {
					final Guarantee toAdd = Guarantee.createGuarantee(line);
					contract.add(toAdd, contract);
				}
			}
		} catch (final ContractExeption e) {
			contract.state = ContractState.INVALID;
			contract.addError(e.getMessage());
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
			if (assumptionMin < guaranteeMax) {
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

	/** Checks is all {@link Guarantee}, {@link Reaction} and {@link GuaranteeTwoEvents} are consistent. If they are
	 * they are replaced with a single one. After this call returns true only one of each exists per Event in the
	 * contract.
	 *
	 * @return true if Guarantees are consistent else false */
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

	/** Checks is all {@link Assumption} and {@link AssumptionWithOffset} are consistent. If they are they are replaced
	 * with a single one. After this call returns true only one {@link Assumption} or one {@link AssumptionWithOffset}
	 * per Event exist in the contract.
	 *
	 * @return true if Assumptions are consistent else false */
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

	/** Checks for overlapping times in the given List an calls simplifyContract if there is overlapping
	 *
	 * @param contractElements a list of Element that extend ContractElemnt an share an InputEvent
	 * @return true if the Elements have a TimeOverlap else false */
	static boolean isTimeConsistent(final EList<? extends ContractElement> contractElements) {
		if (contractElements.get(0).getMax() == -1) {
			return isSingleAssumption(contractElements, 0);
		}
		int maximum = contractElements.get(0).getMax();
		int minimum = contractElements.get(0).getMin();
		// TODO
		// for (final ContractElement contractEl : contractElement.subList(1, contractElement.size())) {
		//
		// }

		for (int i = 1; contractElements.size() > i; i++) {
			if (contractElements.get(i).getMax() == -1) {
				return isSingleAssumption(contractElements, i);
			}
			minimum = Math.max(minimum, contractElements.get(i).getMin());
			maximum = Math.min(maximum, contractElements.get(i).getMax());
			if (maximum < minimum) {
				return false;
			}

		}

		simplifyContract(contractElements, minimum, maximum);
		return true;
	}

	// checks if there is only one assumption of the type: x occurs every y ms
	// if true simplifies Assumption and returns true
	private static boolean isSingleAssumption(final EList<? extends ContractElement> contractElements, final int pos) {
		for (final ContractElement contractEl : contractElements.subList(pos + 1, contractElements.size())) {
			if (contractEl.getMax() == -1) {
				return false;
			}
		}
		simplifyAssumption(contractElements.get(pos).getMin(), -1, contractElements.get(pos).getContract(),
				(Assumption) contractElements.get(pos));
		return true;
	}

	/** Replaces two or more ContractElements with a common InputEvent an a time overlap given by minimum and maximum
	 * with an single new ContractElement in the contract.
	 *
	 * @param contractElements
	 * @param minimum
	 * @param maximum */
	private static void simplifyContract(final EList<? extends ContractElement> contractElements, final int minimum,
			final int maximum) {
		final Contract contract = contractElements.get(0).getContract();
		if (contractElements.get(0) instanceof final Assumption toRemove) {
			simplifyAssumption(minimum, maximum, contract, toRemove);
		} else if (contractElements.get(0) instanceof final Guarantee toRemove) {
			simplifyGuarantee(minimum, maximum, contract, toRemove);
		}

	}

	private static void simplifyGuarantee(final int minimum, final int maximum, final Contract contract,
			final Guarantee toRemove) {
		contract.getGuarantees().removeIf(g -> (g.getInputEvent().equals(toRemove.getInputEvent())));
		final Guarantee toAdd = new Guarantee(toRemove.getInputEvent(), toRemove.getOutputEvent(),
				new Interval(minimum, maximum));
		contract.add(toAdd, contract);
	}

	private static void simplifyAssumption(final int minimum, final int maximum, final Contract contract,
			final Assumption toRemove) {
		contract.getAssumptions().removeIf(a -> (a.getInputEvent().equals(toRemove.getInputEvent())));
		final Assumption toAdd = new Assumption(toRemove.getInputEvent(), new Interval(minimum, maximum));
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setTime(new Interval(minimum, maximum));
		contract.add(toAdd, contract);
	}

}
