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
import org.eclipse.fordiac.ide.contractSpec.ClockDefinition;

class Clock {
	final OptionalDouble resolution;
	final OptionalDouble skew;
	final CInterval drift;
	final OptionalDouble maxdiff;

	Clock(final ClockDefinition clockDef) {
		resolution = Utils.timeExpr2Ns(clockDef.getResolution());
		skew = Utils.timeExpr2Ns(clockDef.getSkew());
		drift = clockDef.getDrift() == null ? null : new CInterval(clockDef.getDrift());
		maxdiff = Utils.timeExpr2Ns(clockDef.getMaxdiff());
	}
}
