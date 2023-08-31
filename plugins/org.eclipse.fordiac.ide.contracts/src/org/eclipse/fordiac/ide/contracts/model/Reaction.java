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

import org.eclipse.emf.common.util.EList;

public class Reaction extends Guarantee {
	private static final int POS_MS = 4;
	private static final int POS_WITHIN = 3;
	private static final int POS_REACTION = 1;
	private static final int GUARANTEE_LENGTH = 5;
	private static final int POSITION_NO = 3;

	Reaction() {
		throw new ExceptionInInitializerError("Reaction not Implemented"); //$NON-NLS-1$
		// remove when class is correctly evaluated in contract
	}

	static Guarantee createReaction(final String line) {

		String[] parts = line.split(" "); //$NON-NLS-1$
		if (!isCorrectGuarantee(parts)) {
			throw new IllegalArgumentException("Error with Guarantee: " + line); //$NON-NLS-1$
		}
		final Reaction reaction = new Reaction();
		final String[] events = parts[1].split(","); //$NON-NLS-1$
		reaction.setOutputEvent(events[1].substring(0, events[1].length() - 1));
		reaction.setInputEvent(events[0].substring(1, events[0].length()));
		if (parts[POSITION_NO].contains(",")) { //$NON-NLS-1$
			parts = parts[POSITION_NO].split(","); //$NON-NLS-1$
			reaction.setMin(Integer.parseInt(parts[0].substring(1)));
			parts = parts[1].split("]"); //$NON-NLS-1$
			reaction.setMax(Integer.parseInt(parts[0]));
			return reaction;
		}
		reaction.setMax(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		reaction.setMin(0);
		return reaction;

	}

	public static boolean isCompatibleWith(final EList<Guarantee> guarantees) {
		// TODO implement
		return false;

	}

	private static boolean isCorrectGuarantee(final String[] parts) {
		if (parts.length != GUARANTEE_LENGTH) {
			return false;
		}
		if (!"Reaction".equals(parts[POS_REACTION])) { //$NON-NLS-1$
			return false;
		}
		if (!"within".equals(parts[POS_WITHIN])) { //$NON-NLS-1$
			return false;
		}
		return "ms".equals(parts[POS_MS].subSequence(parts[POS_MS].length() - 2, parts[POS_MS].length())); //$NON-NLS-1$
	}

	@Override
	public String createComment() {
		final StringBuilder comment = new StringBuilder();
		comment.append("GUARANTEE Reaction (");  //$NON-NLS-1$
		comment.append(getInputEvent());
		comment.append(","); //$NON-NLS-1$
		comment.append(getOutputEvent());
		comment.append(") within ");	 //$NON-NLS-1$
		if (getMin() == 0 || getMin() == getMax()) {
			comment.append(getMax());
		} else {
			comment.append("["); //$NON-NLS-1$
			comment.append(getMin());
			comment.append(","); //$NON-NLS-1$
			comment.append(getMax());
			comment.append("]"); //$NON-NLS-1$
		}
		comment.append("ms \n"); //$NON-NLS-1$
		return comment.toString();
	}
}
