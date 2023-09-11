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

public class Interval extends AbstractTime {
	private final int minTime;
	private final int maxTime;

	public Interval(final int minTime, final int maxTime) {
		this.minTime = minTime;
		this.maxTime = maxTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public int getMinTime() {
		return minTime;
	}

	public boolean isValid() {
		return minTime <= maxTime;
	}

	public Interval calculateOverlap(final Interval other) {
		int min;
		if (other.getMinTime() < this.getMinTime()) {
			min = this.getMinTime();
		} else {
			min = other.getMinTime();
		}
		int max;
		if (other.getMaxTime() > this.getMaxTime()) {
			max = this.getMaxTime();
		} else {
			max = other.getMaxTime();
		}
		return new Interval(min, max);
	}

	public Interval merge(final Interval other) {
		if ((other.getMaxTime() < this.getMinTime()) && (other.getMinTime() > this.getMaxTime())) {
			return new Interval(0, -1);
		}
		int min = this.getMinTime();
		if (min > other.getMinTime()) {
			min = other.getMinTime();
		}
		int max = this.getMaxTime();
		if (max < other.maxTime) {
			max = other.getMaxTime();
		}
		return new Interval(min, max);

	}

	public Interval add(final Interval other) {
		return new Interval(this.getMinTime() + other.getMinTime(), this.getMaxTime() + other.getMaxTime());
	}

	public Interval add(final Instant other) {
		return new Interval(this.getMinTime() + other.getTime(), this.getMaxTime() + other.getTime());
	}

	@Override
	public Interval add(final AbstractTime other) {
		if (other instanceof final Instant otherInstant) {
			return this.add(otherInstant);
		}
		if (other instanceof final Interval otherInterval) {
			return this.add(otherInterval);
		}
		return null;
	}
}
