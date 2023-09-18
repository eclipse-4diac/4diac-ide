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

public class ValueRandomGauss extends BaseRandom {
	private double mean;
	private double stddev;

	public ValueRandomGauss(final double mean, final double stddev) {
		this.mean = mean;
		if (stddev < 0) {
			this.stddev = 0;
		}
		this.stddev = stddev;
	}

	public void setParameters(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
	}

	public String getSINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextSint();
	}

	public String getINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextInteger();
	}

	public String getDINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextDint();
	}

	public String getLINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextLint();
	}

	public String getUSINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextUsint();
	}

	public String getUINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextUint();
	}

	public String getUDINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextUdint();
	}

	public String getULINT(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextUlint();
	}

	public String getLREAL(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextLreal();

	}

	public String getREAL(final double mean, final double stddev) {
		this.mean = mean;
		this.stddev = stddev;
		return nextReal();

	}

	@Override
	public String nextSint() {
		int value = (int) Math.round((random.nextGaussian() * stddev + mean));
		if (value > MAX_BYTE / 2 - 1) {
			value = MAX_BYTE / 2 - 1;
		} else if (value < MAX_BYTE / 2 * -1) {
			value = MAX_BYTE / 2 * -1;
		}
		return "SINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextInteger() {
		int value = (int) Math.round((random.nextGaussian() * stddev + mean));
		if (value > Short.MAX_VALUE) {
			value = Short.MAX_VALUE;
		} else if (value < Short.MIN_VALUE) {
			value = Short.MIN_VALUE;
		}
		return "INT#" + value; //$NON-NLS-1$

	}

	@Override
	public String nextDint() {
		final int value = (int) Math.round((random.nextGaussian() * stddev + mean));
		return "DINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextLint() {
		final long value = Math.round((random.nextGaussian() * stddev + mean));
		return "LINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextUsint() {
		long value = Math.round((random.nextGaussian() * stddev + mean));
		if (value > MAX_BYTE) {
			value = MAX_BYTE;
		} else if (value < 0) {
			value = 0;
		}
		return "USINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextUint() {
		long value = Math.round((random.nextGaussian() * stddev + mean));
		final int max = (int) Math.pow(2, WORD_LENGTH) - 1;
		if (value > max) {
			value = max;
		} else if (value < 0) {
			value = 0;
		}
		return "UINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextUdint() {
		long value = Math.round((random.nextGaussian() * stddev + mean));
		final long max = (long) Math.pow(2, INT_LENGTH) - 1;
		if (value > max) {
			value = max;
		} else if (value < 0) {
			value = 0;
		}
		return "UDINT#" + value; //$NON-NLS-1$
	}

	@Override
	public String nextUlint() {
		final long value = Math.round((random.nextGaussian() * stddev + mean));

		return "ULINT#" + Long.toUnsignedString(value); //$NON-NLS-1$
	}

	@Override
	public String nextLreal() {
		return LREAL + (random.nextGaussian() * stddev + mean);
	}

	@Override
	public String nextReal() {
		double real = (random.nextGaussian() * stddev + mean);
		final double max = Math.pow(2, Float.MAX_VALUE);
		if (real > max) {
			real = max;
		} else if (real < (max * -1)) {
			real = max * -1;
		}
		return LREAL + real;
	}

}
