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
package org.eclipse.fordiac.ide;

import java.util.OptionalDouble;

import org.eclipse.fordiac.ide.contractSpec.TimeExpr;
import org.eclipse.fordiac.ide.contractSpec.Unit;

public class Utils {
	public static OptionalDouble timeExpr2Ns(final TimeExpr expr) {
		if (expr == null) {
			return OptionalDouble.empty();
		}
		return OptionalDouble.of(getInNs(expr.getValue(), expr.getUnit()));
	}

	public static double getInNs(final double value, final Unit unit) {
		return switch (unit) {
		case S -> value * 1e9;
		case MS -> value * 1e6;
		case US -> value * 1e3;
		case NS -> value;
		};
	}

	@SuppressWarnings("boxing")
	public static String nsToString(final double ns) {
		final Unit unit = getFittingUnit(ns);
		final double div = Utils.getInNs(1, unit);
		final double v = ns / div;
		return "%f%s".formatted(v, unit); //$NON-NLS-1$
	}

	public static Unit getFittingUnit(final double value) {
		if (value > 1e9) {
			return Unit.S;
		}
		if (value > 1e6) {
			return Unit.MS;
		}
		if (value > 1e3) {
			return Unit.US;
		}
		return Unit.NS;
	}
}
