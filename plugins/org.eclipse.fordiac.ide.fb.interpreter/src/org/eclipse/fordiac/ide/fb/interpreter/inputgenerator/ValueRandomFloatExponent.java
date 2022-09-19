/*******************************************************************************
 * Copyright (c) 2022 Paul Pavlicek
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

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.RealType;

public class ValueRandomFloatExponent extends AbstractValueRandom {

	@Override
	public String getRandom(final DataType dataType) {
		if (dataType instanceof RealType) {
			return nextReal();
		}
		if (dataType instanceof LrealType) {
			return nextLreal();
		}
		throw new UnsupportedOperationException();

	}

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

	@Override
	public String nextBool() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextByte() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextSint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextUint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextInteger() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextWord() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextDword() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextLword() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextChar() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextWchar() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextDint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextLint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextUsint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextUdint() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String nextUlint() {
		throw new UnsupportedOperationException();
	}

}

