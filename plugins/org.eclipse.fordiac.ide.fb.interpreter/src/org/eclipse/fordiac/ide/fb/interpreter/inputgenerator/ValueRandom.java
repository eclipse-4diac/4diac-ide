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

public class ValueRandom extends BaseRandom {

	@Override
	public String nextBool() {
		return "BOOL#" + (random.nextbits(1) != 0); //$NON-NLS-1$
	}

	@Override
	public String nextByte() {
		return "16#" + Integer.toBinaryString(random.nextbits(BYTE_LENGTH)); //$NON-NLS-1$
	}

	@Override
	public String nextSint() {
		return "SINT#" + (random.nextbits(BYTE_LENGTH) - (MAX_BYTE / 2) - 1);//$NON-NLS-1$
	}

	@Override
	public String nextUint() {
		return "UINT#" + random.nextbits(WORD_LENGTH);//$NON-NLS-1$
	}

	@Override
	public String nextInteger() {
		return "INT#" + (random.nextbits(WORD_LENGTH) + Short.MIN_VALUE);//$NON-NLS-1$
	}

	@Override
	public String nextWord() {
		return "16#" + Integer.toBinaryString(random.nextbits(WORD_LENGTH));//$NON-NLS-1$
	}

	@Override
	public String nextDword() {
		return "16#" + Integer.toBinaryString(random.nextbits(INT_LENGTH));//$NON-NLS-1$
	}

	@Override
	public String nextLword() {
		return "16#" + Integer.toBinaryString(random.nextbits(INT_LENGTH)) //$NON-NLS-1$
				+ Integer.toBinaryString(random.nextbits(INT_LENGTH));
	}

	@Override
	public String nextChar() {
		return "CHAR#" + random.nextbits(BYTE_LENGTH); //$NON-NLS-1$
	}

	@Override
	public String nextWchar() {
		return "WCHAR#" + random.nextbits(WORD_LENGTH); //$NON-NLS-1$
	}

	@Override
	public String nextDint() {
		return "DINT#" + (random.nextbits(INT_LENGTH) - (Math.pow(2, MAX_DINT_POSITIVE) - 1)); //$NON-NLS-1$
	}

	@Override
	public String nextLint() {
		return "LINT#" + (random.nextLong() - (Math.pow(2, MAX_LINT_POSITIVE) - 1)); //$NON-NLS-1$
	}

	@Override
	public String nextUsint() {
		return "USINT#" + random.nextbits(BYTE_LENGTH); //$NON-NLS-1$
	}

	@Override
	public String nextUdint() {
		return "UDINT#" + random.nextbits(INT_LENGTH); //$NON-NLS-1$
	}

	@Override
	public String nextUlint() {
		return "ULINT#" + random.nextLong();//$NON-NLS-1$
	}

	@Override
	public String nextReal() {
		if (random.nextBoolean()) {
			return REAL + random.nextFloat() * Float.MAX_VALUE;
		}
		return REAL + random.nextFloat() * Float.MAX_VALUE * -1;
	}

	@Override
	public String nextLreal() {
		if (random.nextBoolean()) {
			return LREAL + Double.MAX_VALUE * random.nextDouble();// -308 +308
		}
		return LREAL + Double.MAX_VALUE * -1 * random.nextDouble();
	}

}
