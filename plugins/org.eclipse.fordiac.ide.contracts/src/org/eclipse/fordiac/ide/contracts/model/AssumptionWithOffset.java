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

public class AssumptionWithOffset extends Assumption {
	private static final int POSITION_NO2 = 6;
	private static final int POSITION_NO1 = 4;
	private int minOffset;
	private int maxOffset;

	static Assumption createAssumptionWithOffset(final String line) {
		final AssumptionWithOffset assumption = new AssumptionWithOffset();
		final String[] parts = line.split(" "); //$NON-NLS-1$
		if (parts[POSITION_NO1].contains(",")) { //$NON-NLS-1$
			String[] number = parts[POSITION_NO1].split(","); //$NON-NLS-1$
			assumption.setMin(Integer.parseInt(number[0].substring(1)));
			number = number[1].split("]"); //$NON-NLS-1$
			assumption.setMax(Integer.parseInt(number[0]));
		} else {
			assumption.setMax(-1);
			assumption.setMin(Integer.parseInt(parts[POSITION_NO1].substring(0, parts[POSITION_NO1].length() - 2)));
			assumption.setMin(0);
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

	public int getMinOffset() {
		return minOffset;
	}

	public int getMaxOffset() { // Public for testing
		return maxOffset;
	}

	void setMinOffset(final int minOffset) {
		this.minOffset = minOffset;
	}

	void setMaxOffset(final int maxOffset) {
		this.maxOffset = maxOffset;
	}
}
