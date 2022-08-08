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
import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;

@SuppressWarnings("serial")
public class ValueRandom {

	private static final int MAX_BYTE = 256;
	private static final int MAXEXP_FLOAT = 38;
	private static final int MAXEXP_DOUBLE = 308;
	private static final int EXP = 10;
	private static final int INT_LENGTH = 32;
	private static final int WORD_LENGTH = 16;
	private static final int BYTE_LENGTH = 8;

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
		return "16#" + Integer.toBinaryString(random.nextbits(BYTE_LENGTH)); //$NON-NLS-1$
	}

	public String nextSint() {
		return "SINT#" + (random.nextbits(BYTE_LENGTH) - (MAX_BYTE / 2) - 1);//$NON-NLS-1$
	}

	public String nextUint() {
		return "UINT#" + random.nextbits(WORD_LENGTH);//$NON-NLS-1$
	}

	public String nextInteger() {
		return "INT#" + (random.nextbits(WORD_LENGTH) + Short.MIN_VALUE);//$NON-NLS-1$
	}

	public String nextWord() {
		return "16#" + Integer.toBinaryString(random.nextbits(WORD_LENGTH));//$NON-NLS-1$
	}

	public String nextDword() {
		return "16#" + Integer.toBinaryString(random.nextbits(INT_LENGTH));//$NON-NLS-1$
	}

	public String nextLword() {
		return "16#" + Integer.toBinaryString(random.nextbits(INT_LENGTH)) //$NON-NLS-1$
				+ Integer.toBinaryString(random.nextbits(INT_LENGTH));
	}

	public String nextChar() {
		return "CHAR#" + random.nextbits(BYTE_LENGTH); //$NON-NLS-1$
	}

	public String nextWchar() {
		return "WCHAR#" + random.nextbits(WORD_LENGTH); //$NON-NLS-1$
	}

	public String nextDint() {
		return "DINT#" + (random.nextbits(INT_LENGTH) - (Math.pow(2, 31) - 1)); //$NON-NLS-1$
	}

	public String nextLint() {
		return "LINT#" + (random.nextLong() - (Math.pow(2, 63) - 1)); //$NON-NLS-1$
	}

	public String nextUsint() {
		return "USINT#" + random.nextbits(BYTE_LENGTH); //$NON-NLS-1$
	}

	public String nextUdint() {
		return "UDINT#" + random.nextbits(INT_LENGTH); //$NON-NLS-1$
	}

	public String nextUlint() {
		return "ULINT#" + random.nextLong();//$NON-NLS-1$
	}

	public String nextReal() {
		if (random.nextBoolean()) {
			if (random.nextBoolean()) {
				return "REAL#" + random.nextFloat() * (Math.pow(EXP, random.nextInt(MAXEXP_FLOAT)) * -1);//$NON-NLS-1$
			}
			return "REAL#" + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_FLOAT)));//$NON-NLS-1$
		}
		if (random.nextBoolean()) {
			return "REAL#" + random.nextFloat() * Math.pow(EXP, random.nextInt(MAXEXP_FLOAT));//$NON-NLS-1$
		}
		return "REAL#" + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_FLOAT)) * -1);//$NON-NLS-1$
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
				return "LREAL#" + random.nextFloat() * (Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE)) * -1);//$NON-NLS-1$
			}
			return "LREAL#" + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE)));//$NON-NLS-1$
		}
		if (random.nextBoolean()) {
			return "LREAL#" + random.nextFloat() * Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE));//$NON-NLS-1$
		}
		return "LREAL#" + random.nextFloat() / (Math.pow(EXP, random.nextInt(MAXEXP_DOUBLE))) * -1;//$NON-NLS-1$
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

	public String getRandom(final DataType dataType) {
		if (dataType instanceof IntType) {
			return nextInteger();
		}
		if (dataType instanceof UintType) {
			return nextUint();
		}
		if (dataType instanceof BoolType) {
			return nextBool();
		}
		if (dataType instanceof SintType) {
			return nextSint();
		}
		if (dataType instanceof IntType) {
			return nextInteger();
		}
		if (dataType instanceof DintType) {
			return nextDint();
		}
		if (dataType instanceof LintType) {
			return nextLint();
		}
		if (dataType instanceof UsintType) {
			return nextUsint();
		}
		if (dataType instanceof UintType) {
			return nextUint();
		}
		if (dataType instanceof UdintType) {
			return nextUdint();
		}
		if (dataType instanceof UlintType) {
			return nextUlint();
		}
		if (dataType instanceof ByteType) {
			return nextByte();
		}
		if (dataType instanceof WordType) {
			return nextWord();
		}
		if (dataType instanceof DwordType) {
			return nextDword();
		}
		if (dataType instanceof LwordType) {
			return nextLword();
		}
		if (dataType instanceof CharType) {
			return nextChar();
		}
		if (dataType instanceof WcharType) {
			return nextWchar();
		}
		if (dataType instanceof RealType) {
			return nextReal();
		}
		if (dataType instanceof LrealType) {
			return nextLreal();
		}
		return "";//$NON-NLS-1$
	}
}
