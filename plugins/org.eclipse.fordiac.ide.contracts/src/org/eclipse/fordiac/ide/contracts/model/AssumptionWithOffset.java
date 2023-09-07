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
	private int minOffset;
	private int maxOffset;

	AssumptionWithOffset() {
		throw new ExceptionInInitializerError("AssumptionWithOffset not Implemented"); //$NON-NLS-1$
		// remove when class is correctly evaluated in contracts
	}

	static Assumption createAssumptionWithOffset(final String line) {
		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectAssumtion(parts)) {
			throw new AssumptionWithOffsetExeption("Error with Assumption: " + line); //$NON-NLS-1$

		}
		final AssumptionWithOffset assumption = new AssumptionWithOffset();
		assumption.setInputEvent(parts[1]);
		if (ContractUtils.isInterval(parts, POSITION_NO1, ContractKeywords.INTERVAL_DIVIDER)) {
			assumption.setRangeFromInterval(parts, POSITION_NO1);
		} else {
			assumption.setMax(-1);
			assumption.setMin(Integer.parseInt(parts[POSITION_NO1].substring(0,
					parts[POSITION_NO1].length() - ContractKeywords.UNIT_OF_TIME.length())));
		}
		if (ContractUtils.isInterval(parts, POSITION_NO2, ContractKeywords.INTERVAL_DIVIDER)) {
			String[] number = parts[POSITION_NO2].split(ContractKeywords.INTERVAL_DIVIDER);
			assumption.setMinOffset(Integer.parseInt(number[0].substring(1)));
			number = number[1].split(ContractKeywords.INTERVAL_CLOSE);
			assumption.setMaxOffset(Integer.parseInt(number[0]));
		} else {
			assumption.setMaxOffset(-1);
			assumption.setMinOffset(Integer.parseInt(parts[POSITION_NO2].substring(0,
					parts[POSITION_NO2].length() - ContractKeywords.UNIT_OF_TIME.length())));
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
		return minOffset;
	}

	public int getMaxOffset() {
		return maxOffset;
	}

	void setMinOffset(final int minOffset) {
		this.minOffset = minOffset;
	}

	void setMaxOffset(final int maxOffset) {
		this.maxOffset = maxOffset;
	}

	public static boolean isCompatibleWith(final Iterable<Assumption> assumptions) {
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

	private static void simplifyAssumptionWithOffset(final AssumptionWithOffset toRemove, final int mini,
			final int maxi, final int miniOffest, final int maxiOffset) {
		toRemove.getContract().getAssumptions().removeIf(
				a -> ((a.getInputEvent().equals(toRemove.getInputEvent())) && (a instanceof AssumptionWithOffset)));
		final AssumptionWithOffset toAdd = new AssumptionWithOffset();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setMax(maxi);
		toAdd.setMin(mini);
		toAdd.setMaxOffset(maxiOffset);
		toAdd.setMinOffset(miniOffest);
		toRemove.getContract().add(toAdd, toRemove.getContract());
	}

	@Override
	public String createComment() {
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
	List<AbstractTime> getTimestamp(final int number) {
		// TODO implement
		return Collections.emptyList();
	}

	@Override
	List<AbstractTime> getTimestamp(final Interval range) {
		// TODO implement
		return Collections.emptyList();
	}
}
