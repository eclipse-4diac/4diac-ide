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

import java.util.Random;

import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;

@SuppressWarnings("serial")
public class ValueRandom {

	private static final class LocalRandom extends Random {
		int nextbits(final int bits) {
			return super.next(bits);
		}
	}

	private final LocalRandom random = new LocalRandom();

	public String nextBool() {
		return "BOOL#" + (random.nextbits(1) != 0); //$NON-NLS-1$
	}

	public String nextByte() {
		return "16#" + Integer.toBinaryString(random.nextbits(8)); //$NON-NLS-1$
	}

	public String nextUint() {
		return "UINT#" + random.nextbits(16);//$NON-NLS-1$
	}

	public String nextInteger() {
		return "INT#" + (random.nextbits(16) + Short.MIN_VALUE);//$NON-NLS-1$
	}

	public String nextWord() {
		return "16#" + Integer.toBinaryString(random.nextbits(16));//$NON-NLS-1$
	}

	public String nextDword() {
		return "16#" + Integer.toBinaryString(random.nextbits(32));//$NON-NLS-1$
	}

	public String nextLword() {
		return "16#" + Integer.toBinaryString(random.nextbits(32)) + Integer.toBinaryString(random.nextbits(32));//$NON-NLS-1$
	}

	public String nextChar() {
		return "CHAR#" + String.valueOf(random.nextbits(8)); //$NON-NLS-1$
	}

	public String nextWchar() {
		return "WCHAR#" + String.valueOf(random.nextbits(16)); //$NON-NLS-1$
	}

	public String nextReal() {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				return "REAL#" + random.nextFloat() * (Math.pow(10, random.nextInt(38)) * -1);//$NON-NLS-1$
			}
			return "REAL#" + random.nextFloat() / (Math.pow(10, random.nextInt(38))) * -1;//$NON-NLS-1$
		}
		if (random.nextBoolean()) {
			return "REAL#" + random.nextFloat() * (Math.pow(10, random.nextInt(38)));//$NON-NLS-1$
		}
		return "REAL#" + random.nextFloat() * (Math.pow(10, random.nextInt(38))) * -1;//$NON-NLS-1$
	}

	public String nextRealEqui() {
		if (random.nextBoolean()) {
			return "REAL#" + random.nextFloat() * Float.MAX_VALUE;//$NON-NLS-1$
		}
		return "REAL#" + random.nextFloat() * Float.MAX_VALUE * -1;//$NON-NLS-1$
	}

	public String nextLreal() {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				return "LREAL#" + random.nextFloat() * (Math.pow(10, random.nextInt(308)) * -1);//$NON-NLS-1$
			}
			return "LREAL#" + random.nextFloat() / (Math.pow(10, random.nextInt(308))) * -1;//$NON-NLS-1$
		}
		if (random.nextBoolean()) {
			return "LREAL#" + random.nextFloat() * (Math.pow(10, random.nextInt(308)));//$NON-NLS-1$
		}
		return "LREAL#" + random.nextFloat() * (Math.pow(10, random.nextInt(308))) * -1;//$NON-NLS-1$
	}

	public String nextLrealEqui() {
		if (random.nextBoolean()) {
			return "LREAL#" + Double.MAX_VALUE * random.nextDouble();//$NON-NLS-1$ -308 +308
		}
		return "LREAL#" + Double.MAX_VALUE * -1 * random.nextDouble();//$NON-NLS-1$
	}

	public void setSeed(final long seed) {
		random.setSeed(seed);
	}

	public String getRandom(final Class<? extends DataType> clazz) {
		if (clazz == IntType.class) {
			return nextInteger();
		}
		if (clazz == UintType.class) {
			return nextUint();
		}
		if (clazz == BoolType.class) {
			return nextBool();
		}
		if (clazz == ByteType.class) {
			return nextByte();
		}
		if (clazz == WordType.class) {
			return nextWord();
		}
		if (clazz == DwordType.class) {
			return nextDword();
		}
		if (clazz == LwordType.class) {
			return nextLword();
		}
		if (clazz == CharType.class) {
			return nextChar();
		}
		if (clazz == WcharType.class) {
			return nextWchar();
		}
		if (clazz == RealType.class) {
			return nextReal();
		}
		if (clazz == LrealType.class) {
			return nextLreal();
		}
		return "";//$NON-NLS-1$
	}
}
