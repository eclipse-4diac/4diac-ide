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

public class Reaction extends Guarantee {
	private static final int POSITION_NO = 3;

	Reaction() {
	}

	static Guarantee createReaction(final String line) {

		final Reaction reaction = new Reaction();
		String[] parts = line.split(" "); //$NON-NLS-1$
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
		reaction.setMax(-1);
		reaction.setMin(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		return reaction;

	}
}
