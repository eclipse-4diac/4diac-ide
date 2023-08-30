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

public class Guarantee {
	private static final int POS_OUTPUT_EVENT = 7;
	private static final int POS_INPUT_EVENT = 3;
	private static final int POSITION_NO = 10;
	private String inputEvent;
	private String outputEvent;
	private int min;
	private int max;
	private Contract owner;

	Guarantee() {

	}

	String getInputEvent() {
		return inputEvent;
	}

	String getOutputEvent() {
		return outputEvent;
	}

	int getMin() {
		return min;
	}

	int getMax() {
		return max;
	}

	Contract getOwner() {
		return owner;
	}

	void setInputEvent(final String inputEvent) {
		this.inputEvent = inputEvent;
	}

	void setOutputEvent(final String outputEvent) {
		this.outputEvent = outputEvent;
	}

	void setMin(final int min) {
		this.min = min;
	}

	void setMax(final int max) {
		this.max = max;
	}

	void setOwner(final Contract owner) {
		this.owner = owner;
	}

	public static Guarantee createGuarantee(final String line) {
		if (line.contains("Reaction")) { //$NON-NLS-1$
			return Reaction.createReaction(line);
		}
		if (line.contains("events")) { //$NON-NLS-1$
			return GuaranteeTwoEvents.createGuaranteeTwoEvents(line);
		}
		final Guarantee guarantee = new Guarantee();
		String[] parts = line.split(" "); //$NON-NLS-1$
		guarantee.setInputEvent(parts[POS_INPUT_EVENT]);
		guarantee.setOutputEvent(parts[POS_OUTPUT_EVENT]);
		if (parts[POSITION_NO].contains(",")) { //$NON-NLS-1$
			parts = parts[POSITION_NO].split(","); //$NON-NLS-1$
			guarantee.setMin(Integer.parseInt(parts[0].substring(1)));
			parts = parts[1].split("]"); //$NON-NLS-1$
			guarantee.setMax(Integer.parseInt(parts[0]));
			return guarantee;
		}
		guarantee.setMax(-1);
		guarantee.setMin(Integer.parseInt(parts[POSITION_NO].substring(0, parts[POSITION_NO].length() - 2)));
		return guarantee;
	}

	boolean isValid() {
		return false;
	}
}
