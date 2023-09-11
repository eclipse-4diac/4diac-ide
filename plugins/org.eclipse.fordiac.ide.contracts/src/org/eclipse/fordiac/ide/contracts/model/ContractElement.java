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

	void setRangeFromInterval(String[] parts, final int position) {
		parts = parts[position].split(ContractKeywords.INTERVAL_DIVIDER);
		setMin(Integer.parseInt(parts[0].substring(1)));
		parts = parts[1].split(ContractKeywords.INTERVAL_CLOSE);
		setMax(Integer.parseInt(parts[0]));
	}
}
