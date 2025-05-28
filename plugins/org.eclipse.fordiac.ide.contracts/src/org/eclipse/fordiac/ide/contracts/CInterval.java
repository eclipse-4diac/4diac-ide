/*******************************************************************************
 * Copyright (c) 2025 Felix Schmid
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Schmid
 *     - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.contracts;

import java.util.OptionalDouble;

import org.eclipse.fordiac.ide.Utils;
import org.eclipse.fordiac.ide.contractSpec.Interval;

class CInterval {
	private final double lb;
	private final double ub;
	private final boolean lbOpen;
	private final boolean ubOpen;

	CInterval(final Interval interval) {
		final OptionalDouble d = Utils.timeExpr2Ns(interval.getTime());

		if (d.isPresent()) {
			lb = d.getAsDouble();
			ub = lb;
			lbOpen = false;
			ubOpen = false;
		} else {
			lb = Utils.getInNs(interval.getLbValue(), interval.getUnit());
			ub = Utils.getInNs(interval.getUbValue(), interval.getUnit());
			lbOpen = interval.getLBound().equals("]"); //$NON-NLS-1$
			ubOpen = interval.getUBound().equals("["); //$NON-NLS-1$
		}
	}

	CInterval(final char lbBracket, final double lb, final double ub, final char ubBracket) {
		this(lbBracket != '[', lb, ub, ubBracket != ']');
	}

	CInterval(final boolean lbOpen, final double lb, final double ub, final boolean ubOpen) {
		this.lbOpen = lbOpen;
		this.lb = lb;
		this.ub = ub;
		this.ubOpen = ubOpen;
	}

	CInterval add(final CInterval other) {
		return new CInterval(lbOpen || other.lbOpen, lb + other.lb, ub + other.ub, ubOpen || other.ubOpen);
	}

	CInterval addJitter(final double jitter) {
		// make sure that lower bound is >= 0
		return new CInterval(lbOpen, Math.max(0, lb - jitter), ub + jitter, ubOpen);
	}

	boolean contains(final CInterval other) {
		return !(other.lb < lb || other.ub > ub || (other.lb == lb && !other.lbOpen && lbOpen)
				|| (other.ub == ub && !other.ubOpen && ubOpen));
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(lbOpen ? ']' : '[');
		sb.append(lb);
		sb.append(", "); //$NON-NLS-1$
		sb.append(ub);
		sb.append(ubOpen ? '[' : ']');
		sb.append("ns"); //$NON-NLS-1$
		return sb.toString();
	}
}
