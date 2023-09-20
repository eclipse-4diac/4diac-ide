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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.exceptions.AssumptionWithOffsetExeption;
import org.eclipse.fordiac.ide.contracts.model.helpers.ContractUtils;

public class AssumptionWithOffset extends Assumption {

	private static final int POS_OFFSET = 7;
	private static final int POS_WITH = 5;
	private static final int ASSUMPTION_LENGTH = 8;
	private static final int POS_OCCURS = 2;
	private static final int POS_TIME = 4;
	private static final int POS_TIME2 = 6;
	private static final int POS_EVERY = 3;
	private static final int POSITION_NO2 = 6;
	private static final int POSITION_NO1 = 4;
	private AbstractTime offSet;

	AssumptionWithOffset() {
		throw new UnsupportedOperationException("AssumptionWithOffset not Implemented"); //$NON-NLS-1$
		// TODO remove when class is correctly evaluated in contracts
	}

	AbstractTime getOffSet() {
		return offSet;
	}

	void setOffSet(final AbstractTime offSet) {
		this.offSet = offSet;
	}

	static Assumption createAssumptionWithOffset(final String line) throws AssumptionWithOffsetExeption {
		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectAssumtion(parts)) {
			throw new AssumptionWithOffsetExeption("Error with Assumption: " + line); //$NON-NLS-1$

		}
		final AssumptionWithOffset assumption = new AssumptionWithOffset();
		assumption.setInputEvent(parts[1]);
		if (ContractUtils.isInterval(parts, POSITION_NO1, ContractKeywords.INTERVAL_DIVIDER)) {
			assumption.setRangeFromInterval(parts, POSITION_NO1);
		} else {
			assumption.setTime(new Instant(Integer.parseInt(parts[POSITION_NO1].substring(0,
					parts[POSITION_NO1].length() - ContractKeywords.UNIT_OF_TIME.length()))));
		}
		if (ContractUtils.isInterval(parts, POSITION_NO2, ContractKeywords.INTERVAL_DIVIDER)) {
			String[] number = parts[POSITION_NO2].split(ContractKeywords.INTERVAL_DIVIDER);
			final int minOffset = Integer.parseInt(number[0].substring(1));
			number = number[1].split(ContractKeywords.INTERVAL_CLOSE);
			final int maxOffset = Integer.parseInt(number[0]);
			assumption.setOffSet(new Interval(minOffset, maxOffset));
		} else {
			assumption.setOffSet(new Instant(Integer.parseInt(parts[POSITION_NO2].substring(0,
					parts[POSITION_NO2].length() - ContractKeywords.UNIT_OF_TIME.length()))));
		}

		return assumption;
	}

	private static boolean isCorrectAssumtion(final String[] parts) {
		if (parts.length != ASSUMPTION_LENGTH) {
			return false;
		}
		if (!ContractKeywords.OCCURS.equals(parts[POS_OCCURS])) {
			return false;
		}
		if (!ContractKeywords.EVERY.equals(parts[POS_EVERY])) {
			return false;
		}
		if (!ContractKeywords.UNIT_OF_TIME.equals(
				parts[POS_TIME].substring(ContractUtils.getStartPosition(parts, POS_TIME), parts[POS_TIME].length()))) {
			return false;
		}
		if (!ContractKeywords.WITH.equals(parts[POS_WITH])) {
			return false;
		}
		if (!ContractKeywords.UNIT_OF_TIME.equals(parts[POS_TIME2]
				.substring(ContractUtils.getStartPosition(parts, POS_TIME2), parts[POS_TIME2].length()))) {
			return false;
		}
		return ContractKeywords.OFFSET.equals(parts[POS_OFFSET]);
	}

	public int getMinOffset() {
		if (getOffSet() instanceof final Instant instant) {
			return instant.getMin();
		}
		if (getOffSet() instanceof final Interval interval) {
			return interval.getMinTime();
		}
		return Integer.MIN_VALUE;
	}

	public int getMaxOffset() {
		if (getOffSet() instanceof final Instant instant) {
			return instant.getMin();
		}
		if (getOffSet() instanceof final Interval interval) {
			return interval.getMaxTime();
		}
		return Integer.MIN_VALUE;
	}

	public static boolean isCompatibleWithOffset(final Iterable<Assumption> assumptions) {
		final EList<Assumption> normal = new BasicEList<>();
		final EList<AssumptionWithOffset> withOffset = new BasicEList<>();
		((Collection<Assumption>) assumptions).parallelStream().forEach(a -> {
			if (a instanceof final AssumptionWithOffset toAdd) {
				withOffset.add(toAdd);
			} else {
				normal.add(a);
			}
		});
		if (normal.isEmpty() && withOffset.size() == 1) {
			return true;
		}
		if (normal.size() > 1) {
			if (!Assumption.isCompatibleWith(normal)) {
				return false;
			}
			final Assumption newAssumption = normal.get(0).getContract()
					.getAssumptionWith(normal.get(0).getInputEvent());
			normal.clear();
			normal.add(newAssumption);
		}
		if (withOffset.size() != 1) {
			if (!AssumptionWithOffset.isCompatibleWithOffset(withOffset)) {
				return false;
			}
			final AssumptionWithOffset newAssumption = (AssumptionWithOffset) withOffset.get(0).getContract()
					.getAssumptionWith(withOffset.get(0).getInputEvent());
			withOffset.clear();
			withOffset.add(newAssumption);
		}
		// here exactly one Assumption and one AssumptionnWithOffset exist
		return compatibleWithAndWithout(normal.get(0), withOffset.get(0));

	}

	private static boolean compatibleWithAndWithout(final Assumption assumption,
			final AssumptionWithOffset assumptionWithOffset) {
		// TODO Implement
		return false;
	}

	private static boolean isCompatibleWithOffset(final EList<AssumptionWithOffset> withOffset) {
		int mini;
		int miniOffest;
		int maxi;
		int maxiOffset;
		mini = withOffset.get(0).getMin();
		maxi = withOffset.get(0).getMax();
		miniOffest = withOffset.get(0).getMinOffset();
		maxiOffset = withOffset.get(0).getMaxOffset();
		if (maxi == -1) {
			maxi = mini;
		}
		if (maxiOffset == -1) {
			maxiOffset = miniOffest;
		}
		for (int i = 1; withOffset.size() > i; i++) {
			if (mini < withOffset.get(i).getMin()) {
				mini = withOffset.get(i).getMin();
			}
			if (maxi > withOffset.get(i).getMax()) {
				maxi = withOffset.get(i).getMax();
			}
			if (miniOffest < withOffset.get(i).getMinOffset()) {
				miniOffest = withOffset.get(i).getMinOffset();
			}
			if (maxiOffset > withOffset.get(i).getMaxOffset()) {
				maxiOffset = withOffset.get(i).getMaxOffset();
			}

		}
		if (mini > maxi || miniOffest > maxiOffset) {
			return false;
		}
		simplifyAssumptionWithOffset(withOffset.get(0), mini, maxi, miniOffest, maxiOffset);

		return true;
	}

	private static void simplifyAssumptionWithOffset(final AssumptionWithOffset toRemove, final int min, final int max,
			final int minOffest, final int maxOffset) {
		toRemove.getContract().getAssumptions().removeIf(
				a -> ((a.getInputEvent().equals(toRemove.getInputEvent())) && (a instanceof AssumptionWithOffset)));
		final AssumptionWithOffset toAdd = new AssumptionWithOffset();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setTime(new Interval(min, max));
		toAdd.setOffSet(new Interval(minOffest, maxOffset));

		toRemove.getContract().add(toAdd, toRemove.getContract());
	}

	@Override
	public String asString() {
		final StringBuilder comment = new StringBuilder();
		if (getMax() == -1 || getMax() == getMin()) {
			comment.append(ContractUtils.createAssumptionString(getInputEvent(), String.valueOf(getMin())));

		} else {
			comment.append(ContractUtils.createAssumptionString(getInputEvent(), ContractUtils.createInterval(this)));
		}
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.WITH);
		comment.append(" "); //$NON-NLS-1$
		if (getMaxOffset() == -1 || getMaxOffset() == getMinOffset()) {
			comment.append(getMinOffset());
		} else {
			comment.append(ContractKeywords.INTERVAL_OPEN);
			comment.append(getMinOffset());
			comment.append(ContractKeywords.INTERVAL_DIVIDER);
			comment.append(getMaxOffset());
			comment.append(ContractKeywords.INTERVAL_CLOSE);
		}
		comment.append(ContractKeywords.UNIT_OF_TIME);
		comment.append(" "); //$NON-NLS-1$
		comment.append(ContractKeywords.OFFSET);
		comment.append(System.lineSeparator());
		return comment.toString();
	}

	@Override
	List<AbstractTime> getOccurrences(final int number) {
		if (number < 1) {
			return Collections.emptyList();
		}
		final List<AbstractTime> timestamps = new ArrayList<>();
		timestamps.add(getFirstTime());
		for (int i = 0; i < number - 1; i++) {
			timestamps.add(timestamps.get(i).add(getBounds()));
		}
		return timestamps;
	}

	@Override
	List<AbstractTime> getOccurrences(final Interval range) {
		final List<AbstractTime> timestamps = new ArrayList<>();
		final AbstractTime toAdd = getFirstTime();
		if (toAdd instanceof Instant instant) {
			while (instant.getMin() <= range.getMaxTime()) {
				if (instant.getMin() >= range.getMinTime()) {
					timestamps.add(instant);
				}
				instant = new Instant(instant.getMin() + getMin());
			}
		} else {
			Interval interval = (Interval) toAdd;
			while (interval.getMaxTime() <= range.getMaxTime()) {
				if (interval.getMinTime() >= range.getMinTime()) {
					timestamps.add(interval);
				}
				interval = interval.add(getBounds());
			}
		}
		return timestamps;
	}

	private AbstractTime getFirstTime() {
		if (getMax() == -1 && getMaxOffset() == -1) {
			return new Instant(getMin() + getMinOffset());
		}
		final int min = getMin() + getMinOffset();
		int max = 0;
		if (getMax() == -1) {
			max += getMin();
		} else {
			max += getMax();
		}
		if (getMaxOffset() == -1) {
			max += getMinOffset();
		} else {
			max += getMaxOffset();
		}
		return new Interval(min, max);

	}

}
