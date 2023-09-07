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

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.contracts.exceptions.ReactionExeption;
import org.eclipse.fordiac.ide.contracts.model.helpers.ContractUtils;

public class Reaction extends Guarantee {

	private static final int POS_MS = 4;
	private static final int POS_WITHIN = 3;
	private static final int POS_REACTION = 1;
	private static final int GUARANTEE_LENGTH = 5;
	private static final int POSITION_NO = 4;

	Reaction() {
		throw new ExceptionInInitializerError("Reaction not Implemented"); //$NON-NLS-1$
		// remove when class is correctly evaluated in contract
	}

	static Guarantee createReaction(final String line) {

		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectGuarantee(parts)) {
			throw new ReactionExeption("Error with Guarantee: " + line); //$NON-NLS-1$
		}
		final Reaction reaction = new Reaction();
		final String[] events = parts[2].split(ContractKeywords.COMMA);
		reaction.setOutputEvent(events[1].substring(0, events[1].length() - 1));
		reaction.setInputEvent(events[0].substring(1, events[0].length()));
		if (ContractUtils.isInterval(parts, POSITION_NO, ContractKeywords.INTERVAL_DIVIDER)) {
			reaction.setRangeFromInterval(parts, POSITION_NO);
			return reaction;
		}
		reaction.setMax(Integer.parseInt(
				parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - ContractKeywords.UNIT_OF_TIME.length())));
		reaction.setMin(0);
		return reaction;

	}

	private static boolean isCorrectGuarantee(final String[] parts) {
		if (parts.length != GUARANTEE_LENGTH) {
			return false;
		}
		if (!ContractKeywords.REACTION.equals(parts[POS_REACTION])) {
			return false;
		}
		if (!ContractKeywords.WITHIN.equals(parts[POS_WITHIN])) {
			return false;
		}
		return ContractKeywords.UNIT_OF_TIME.equals(
				parts[POS_MS].subSequence(ContractUtils.getStartPosition(parts, POS_MS), parts[POS_MS].length()));
	}

	@Override
	public String createComment() {
		final StringBuilder comment = new StringBuilder();
		if (getMin() == 0 || getMin() == getMax()) {
			comment.append(
					ContractUtils.createReactionString(getInputEvent(), getOutputEvent(), String.valueOf(getMax())));
		} else {
			comment.append(ContractUtils.createReactionString(getInputEvent(), getOutputEvent(),
					ContractUtils.createInterval(this)));
		}
		comment.append(System.lineSeparator());
		return comment.toString();
	}

	public static boolean isCompatibleWith(final Map<String, EList<Guarantee>> mapGuarantees,
			final Map<String, EList<Reaction>> mapReactions) {
		if (mapGuarantees.isEmpty() && mapReactions.size() == 1) {
			return true;
		}
		// TODO Implement the rest
		return false;

	}
}
