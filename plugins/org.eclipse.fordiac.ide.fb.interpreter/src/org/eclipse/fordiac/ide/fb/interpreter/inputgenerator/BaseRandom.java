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

import java.util.Random;
import java.util.stream.Collectors;

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
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WordType;

public abstract class BaseRandom implements BaseRandomNextValue {
	protected static final int MAX_DINT_POSITIVE = 31;
	protected static final int MAX_LINT_POSITIVE = 63;
	protected static final int MAX_BYTE = 256;
	protected static final int MAXEXP_FLOAT = 38;
	protected static final int MAXEXP_DOUBLE = 308;
	protected static final int EXP = 10;
	protected static final int INT_LENGTH = 32;
	protected static final int WORD_LENGTH = 16;
	protected static final int BYTE_LENGTH = 8;
	protected static final String REAL = "REAL#"; //$NON-NLS-1$
	protected static final String LREAL = "LREAL#"; //$NON-NLS-1$

	protected static final class LocalRandom extends Random {
		private static final long serialVersionUID = 136710794835658751L;

		int nextbits(final int bits) {
			return super.next(bits);
		}
	}

	protected final LocalRandom random = new LocalRandom();

	protected BaseRandom() {
	}

	public void setSeed(final long seed) {
		random.setSeed(seed);
	}

	public String getRandom(final DataType dataType) {
		final String randInt = isInt(dataType);
		if (!randInt.isBlank()) {
			return randInt;
		}
		if (dataType instanceof BoolType) {
			return nextBool();
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
		if (dataType instanceof final StructuredType structuredType) {
			return genStruct(structuredType);
		}
		throw new UnsupportedOperationException();

	}

	protected String isInt(final DataType dataType) {
		if (dataType instanceof IntType) {
			return nextInteger();
		}
		if (dataType instanceof UintType) {
			return nextUint();
		}
		if (dataType instanceof SintType) {
			return nextSint();
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
		if (dataType instanceof UdintType) {
			return nextUdint();
		}
		if (dataType instanceof UlintType) {
			return nextUlint();
		}
		return ""; //$NON-NLS-1$

	}

	protected String genStruct(final StructuredType structType) {
		return "(" + structType.getMemberVariables().stream().map(x -> x.getName() + ":=" + getRandom(x.getType())) //$NON-NLS-1$ //$NON-NLS-2$
				.collect(Collectors.joining(",")) //$NON-NLS-1$
				+ ")"; //$NON-NLS-1$
	}

}
