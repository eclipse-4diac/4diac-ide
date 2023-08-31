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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

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
			throw new IllegalArgumentException("Error with Assumption: " + line); //$NON-NLS-1$
		}
		final AssumptionWithOffset assumption = new AssumptionWithOffset();
		assumption.setInputEvent(parts[1]);
		if (parts[POSITION_NO1].contains(",")) { //$NON-NLS-1$
			String[] number = parts[POSITION_NO1].split(","); //$NON-NLS-1$
			assumption.setMin(Integer.parseInt(number[0].substring(1)));
			number = number[1].split("]"); //$NON-NLS-1$
			assumption.setMax(Integer.parseInt(number[0]));
		} else {
			assumption.setMax(-1);
			assumption.setMin(Integer.parseInt(parts[POSITION_NO1].substring(0, parts[POSITION_NO1].length() - 2)));
		}
		if (parts[POSITION_NO2].contains(",")) { //$NON-NLS-1$
			String[] number = parts[POSITION_NO2].split(","); //$NON-NLS-1$
			assumption.setMinOffset(Integer.parseInt(number[0].substring(1)));
			number = number[1].split("]"); //$NON-NLS-1$
			assumption.setMaxOffset(Integer.parseInt(number[0]));
		} else {
			assumption.setMaxOffset(-1);
			assumption
					.setMinOffset(Integer.parseInt(parts[POSITION_NO2].substring(0, parts[POSITION_NO2].length() - 2)));
		}

		return assumption;
	}

	private static boolean isCorrectAssumtion(final String[] parts) {
		if (parts.length != ASSUMPTION_LENGTH) {
			return false;
		}
		if (!"occurs".equals(parts[POS_OCCURS])) { //$NON-NLS-1$
			return false;
		}
		if (!"every".equals(parts[POS_EVERY])) { //$NON-NLS-1$
			return false;
		}
		if (!"ms".equals(parts[POS_TIME].substring(parts[POS_TIME].length() - 2, parts[POS_TIME].length()))) { //$NON-NLS-1$
			return false;
		}
		if (!"with".equals(parts[POS_WITH])) { //$NON-NLS-1$
			return false;
		}
		if (!"ms".equals(parts[POS_TIME2].substring(parts[POS_TIME2].length() - 2, parts[POS_TIME2].length()))) { //$NON-NLS-1$
			return false;
		}
		return "offset".equals(parts[POS_OFFSET]); //$NON-NLS-1$
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

	public static boolean isCompatibleWith(final EList<Assumption> assumptions) {
		final EList<Assumption> normal = new BasicEList<>();
		final EList<AssumptionWithOffset> withOffset = new BasicEList<>();
		assumptions.parallelStream().forEach(a -> {
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
			final Assumption newAssumption = normal.get(0).getOwner().getAssumptionWith(normal.get(0).getInputEvent());
			normal.clear();
			normal.add(newAssumption);
		}
		if (withOffset.size() != 1) {
			if (!AssumptionWithOffset.isCompatibleWithOffset(withOffset)) {
				return false;
			}
			final AssumptionWithOffset newAssumption = (AssumptionWithOffset) withOffset.get(0).getOwner()
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
		if (assumption.getMax() == -1 && assumptionWithOffset.getMax() == -1
				&& assumptionWithOffset.getMaxOffset() == -1) {

		}
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
		toRemove.getOwner().getAssumptions().removeIf(
				a -> ((a.getInputEvent().equals(toRemove.getInputEvent())) && (a instanceof AssumptionWithOffset)));
		final AssumptionWithOffset toAdd = new AssumptionWithOffset();
		toAdd.setInputEvent(toRemove.getInputEvent());
		toAdd.setMax(maxi);
		toAdd.setMin(mini);
		toAdd.setMaxOffset(maxiOffset);
		toAdd.setMinOffset(miniOffest);
		toAdd.setOwner(toRemove.getOwner());
		toRemove.getOwner().add(toAdd);
	}

	@Override
	public String createComment() {
		final StringBuilder comment = new StringBuilder();
		comment.append("ASSUMPTION "); //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(" occurs every "); //$NON-NLS-1$
		if (getMax() == -1 || getMax() == getMin()) {
			comment.append(getMin());
		} else {
			comment.append("["); //$NON-NLS-1$
			comment.append(getMin());
			comment.append(","); //$NON-NLS-1$
			comment.append(getMax());
			comment.append("]"); //$NON-NLS-1$
		}
		comment.append("ms with "); //$NON-NLS-1$
		if (getMaxOffset() == -1 || getMaxOffset() == getMinOffset()) {
			comment.append(getMinOffset());
		} else {
			comment.append("["); //$NON-NLS-1$
			comment.append(getMinOffset());
			comment.append(","); //$NON-NLS-1$
			comment.append(getMaxOffset());
			comment.append("]ms offset"); //$NON-NLS-1$
		}
		return comment.toString();
	}
}
