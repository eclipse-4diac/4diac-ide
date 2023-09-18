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

public class ValueRandomFloatExponent extends BaseRandom {

	@Override
	public String nextReal() {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				return REAL + random.nextFloat() * (Math.pow(EXP, random.nextInt(MAXEXP_FLOAT)) * -1);
			}
			return REAL + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_FLOAT)));
		}
		if (random.nextBoolean()) {
			return REAL + random.nextFloat() * Math.pow(EXP, random.nextInt(MAXEXP_FLOAT));
		}
		return REAL + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_FLOAT)) * -1);
	}

	@Override
	public String nextLreal() {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				return LREAL + random.nextFloat() * (Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE)) * -1);
			}
			return LREAL + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE)));
		}
		if (random.nextBoolean()) {
			return LREAL + random.nextFloat() * Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE));
		}
		return LREAL + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE))) * -1;
	}

}
