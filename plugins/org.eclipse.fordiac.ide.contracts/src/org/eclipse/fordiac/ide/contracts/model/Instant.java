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

public class Instant extends AbstractTime {
	private final int time;

	public Instant(final int time) {
		this.time = time;
	}

	private Instant add(final Instant other) {
		return new Instant(this.getMin() + other.getMin());
	}

	@Override
	public AbstractTime add(final AbstractTime other) {
		if (other instanceof final Instant otherInstant) {
			return this.add(otherInstant);
		}
		if (other instanceof final Interval otherInterval) {
			return otherInterval.add(this);
		}
		return null;
	}

	@Override
	public int getMin() {
		return time;
	}

	@Override
	public int getMax() {
		return getMin();
	}

	@Override
	public Instant getCopy() {
		return new Instant(time);
	}
}
