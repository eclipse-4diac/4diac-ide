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
package org.eclipse.fordiac.ide.fb.interpreter.inputgenerator;

public class ValueRandomRange extends BaseRandom {
	private double max;
	private double min;

	public ValueRandomRange(final double max, final double min) {
		this.max = max;
		this.min = min;
	}

	public void setParameters(final double max, final double min) {
		this.max = max;
		this.min = min;
	}

	@Override
	public String nextSint() {
		final int value = (int) (random.nextDouble() * (max - min) + min);
		if ((value < (MAX_BYTE / 2)) || (value > (MAX_BYTE / 2) - 1)) {
			throw new IllegalArgumentException("min or max value is outside of the range of Sint"); //$NON-NLS-1$
		}
		return "SINT#" + value;//$NON-NLS-1$

	}

	@Override
	public String nextUint() {
		final int value = (int) (random.nextDouble() * (max - min) + min);
		final int maxvalue = (int) Math.pow(2, WORD_LENGTH) - 1;
		if ((value > maxvalue) || (value < 0)) {
			throw new IllegalArgumentException("min or max value is outside of the range of Uint"); //$NON-NLS-1$
		}
		return "UINT#" + value; //$NON-NLS-1$

	}

	@Override
	public String nextInteger() {
		final int value = (int) (random.nextDouble() * (max - min) + min);
		if ((value > Short.MAX_VALUE) || (value < Short.MIN_VALUE)) {
			throw new IllegalArgumentException("min or max value is outside of the range of Int"); //$NON-NLS-1$
		}
		return "INT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextDint() {
		return "DINT#" + ((int) (random.nextDouble() * (max - min) + min)); //$NON-NLS-1$

	}

	@Override
	public String nextLint() {
		final long value = (long) (random.nextDouble() * (max - min) + min);
		return "LINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextUsint() {
		final long value = (long) (random.nextDouble() * (max - min) + min);
		if ((value > MAX_BYTE) || (value < 0)) {
			throw new IllegalArgumentException("min or max value is outside of the range of Usint"); //$NON-NLS-1$
		}
		return "USINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextUdint() {
		final long value = (long) (random.nextDouble() * (max - min) + min);
		final long maxvalue = (long) Math.pow(2, INT_LENGTH) - 1;
		if (value > maxvalue || value < 0) {
			throw new IllegalArgumentException("min or max value is outside of the range of Udint"); //$NON-NLS-1$
		}
		return "UDINT#" + value; //$NON-NLS-1$

	}

	@Override
	public String nextUlint() {
		final long value = (long) (random.nextDouble() * (max - min) + min);
		return "ULINT#" + Long.toUnsignedString(value); //$NON-NLS-1$

	}

	@Override
	public String nextReal() {
		final double maxrange = Math.pow(2, Float.MAX_VALUE);
		if ((max > maxrange) || (min < max * -1)) {
			throw new IllegalArgumentException("min or max value is outside of the range of Real"); //$NON-NLS-1$
		}
		return REAL + (random.nextDouble() * (max - min) + min);
	}

	@Override
	public String nextLreal() {
		return LREAL + (random.nextDouble() * (max - min) + min);
	}

}
