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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;

public abstract class ContractElement {

	private int min;
	private int max;
	private String inputEvent;
	private Contract owner;

	public String getInputEvent() {
		return inputEvent;
	}

	void setInputEvent(final String inputEvent) {
		this.inputEvent = inputEvent;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public AbstractTime getBounds() {
		if (max == -1) {
			return new Instant(min);
		}
		return new Interval(min, max);
	}

	public Contract getContract() {
		return owner;
	}

	void setMin(final int min) {
		this.min = min;
	}

	void setMax(final int max) {
		this.max = max;
	}

	void setContract(final Contract owner) {
		this.owner = owner;
	}

	boolean hasValidOwner() {
		return getContract().getOwner() instanceof SubApp;
	}

	List<AbstractTime> getOccurrences() {
		return getOccurrences(ContractConstants.DEFAULT_NUMBER_TIMES);
	}

	List<AbstractTime> getOccurrences(final int number) {
		final List<AbstractTime> timestamps = new ArrayList<>();
		final AbstractTime timeOccurrenc = getBounds();
		AbstractTime toAdd = timeOccurrenc;
		int count = 0;
		while (count < number) {
			timestamps.add(toAdd);
			toAdd = toAdd.add(timeOccurrenc);
			count++;
		}
		return timestamps;
	}

	List<AbstractTime> getOccurrences(final Interval range) {
		final List<AbstractTime> timestamps = new ArrayList<>();
		if (getMax() == -1) {
			int time = getMin();
			while (time <= range.getMaxTime()) {
				if (time >= range.getMinTime()) {
					timestamps.add(new Instant(time));
				}
				time += getMin();
			}
		} else {
			int minTime = getMin();
			int maxTime = getMax();
			while (minTime <= range.getMaxTime()) {
				if (maxTime >= range.getMinTime()) {
					timestamps.add(new Interval(minTime, maxTime));
				}
				minTime += getMin();
				maxTime += getMax();
			}
		}
		return timestamps;
	}

	abstract boolean isValid();

	abstract String asString();

	// TODO move methods
	// method returns boolean, but secretly alters the contract. very bad.
	static boolean isTimeConsistent(final EList<? extends ContractElement> contractElement) { // TODO all lists as
																								 // plural
		if (contractElement.get(0).getMax() == -1) {
			return isSingleAssumption(contractElement, 0);
		}
		int maximum = contractElement.get(0).getMax();
		int minimum = contractElement.get(0).getMin();
		// TODO
		// for (final ContractElement contractEl : contractElement.subList(1, contractElement.size())) {
		//
		// }

		for (int i = 1; contractElement.size() > i; i++) {
			if (contractElement.get(i).getMax() == -1) {
				return isSingleAssumption(contractElement, i);
			}
			if (minimum < contractElement.get(i).getMin()) {
				minimum = contractElement.get(i).getMin();
			}
			if (maximum > contractElement.get(i).getMax()) {
				maximum = contractElement.get(i).getMax();
			}
			if (maximum < minimum) {
				return false;
			}

		}

		simplifyContract(contractElement, minimum, maximum);
		return true;
	}

	// checks if there is only one assumption of the type: x occurs every y ms
	// if true simplifies Assumption and returns true
	private static boolean isSingleAssumption(final EList<? extends ContractElement> contractElement, final int pos) {
		for (int i = pos + 1; i < contractElement.size(); i++) { // TODO sublist
			if (contractElement.get(i).getMax() == -1) {
				return false;
			}
		}
		simplifyAssumption(contractElement.get(pos).getMin(), -1, contractElement.get(pos).getContract(),
				(Assumption) contractElement.get(pos));
		return true;
	}

	private static void simplifyContract(final EList<? extends ContractElement> contractElement, final int minimum,
			final int maximum) {
		final Contract contract = contractElement.get(0).getContract();
		if (contractElement.get(0) instanceof final Assumption toRemove) {
			simplifyAssumption(minimum, maximum, contract, toRemove);
		} else if (contractElement.get(0) instanceof final Guarantee toRemove) {
			simplifyGuarantee(minimum, maximum, contract, toRemove);
		}

	}

	private static void simplifyGuarantee(final int minimum, final int maximum, final Contract contract,
			final Guarantee toRemove) {
		contract.getGuarantees().removeIf(g -> (g.getInputEvent().equals(toRemove.getInputEvent())));
		final Guarantee toAdd = new Guarantee();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setOutputEvent(toRemove.getOutputEvent());
		toAdd.setMin(minimum);
		toAdd.setMax(maximum);
		contract.add(toAdd, contract);
	}

	// why is a method editing a contract in the contractelement class? TODO
	private static void simplifyAssumption(final int minimum, final int maximum, final Contract contract,
			final Assumption toRemove) {
		contract.getAssumptions().removeIf(a -> (a.getInputEvent().equals(toRemove.getInputEvent())));
		final Assumption toAdd = new Assumption();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setMin(minimum);
		toAdd.setMax(maximum);
		contract.add(toAdd, contract);
	}
	// TODO move to contract, e.g., as contract.mergeAssumptionsForEvent(String eventName);

	void setRangeFromInterval(String[] parts, final int position) {
		parts = parts[position].split(ContractKeywords.INTERVAL_DIVIDER);
		setMin(Integer.parseInt(parts[0].substring(1)));
		parts = parts[1].split(ContractKeywords.INTERVAL_CLOSE);
		setMax(Integer.parseInt(parts[0]));
	}
}
