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

	private AbstractTime time;
	private String inputEvent;
	private Contract owner;

	ContractElement() {
		// reduced visibility
	}

	ContractElement(final String inputEvent, final AbstractTime time) {
		this.inputEvent = inputEvent;
		this.time = time;
	}

	AbstractTime getTime() {
		return time;
	}

	void setTime(final AbstractTime time) {
		this.time = time;
	}

	public String getInputEvent() {
		return inputEvent;
	}

	void setInputEvent(final String inputEvent) {
		this.inputEvent = inputEvent;
	}

	public int getMin() {
		return time.getMin();
	}

	public int getMax() {
		return time.getMax();
	}

	public AbstractTime getBounds() {
		if (time.getMax() == -1) {
			return new Instant(time.getMin());
		}
		return new Interval(time.getMin(), time.getMax());
	}

	public Contract getContract() {
		return owner;
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
			int timeMin = getMin();
			while (timeMin <= range.getMaxTime()) {
				if (timeMin >= range.getMinTime()) {
					timestamps.add(new Instant(timeMin));
				}
				timeMin += getMin();
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
		final int min = Integer.parseInt(parts[0].substring(1));
		parts = parts[1].split(ContractKeywords.INTERVAL_CLOSE);
		final int max = Integer.parseInt(parts[0]);
		setTime(new Interval(min, max));
	}
}
