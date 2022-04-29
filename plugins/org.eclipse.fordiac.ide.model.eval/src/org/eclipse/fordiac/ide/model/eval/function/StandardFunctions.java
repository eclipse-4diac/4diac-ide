/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - added some functions
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.function;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.AnyBitValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyCharsValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyDurationValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyIntValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyMagnitudeValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyNumValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyRealValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.ByteValue;
import org.eclipse.fordiac.ide.model.eval.value.DIntValue;
import org.eclipse.fordiac.ide.model.eval.value.DWordValue;
import org.eclipse.fordiac.ide.model.eval.value.IntValue;
import org.eclipse.fordiac.ide.model.eval.value.LIntValue;
import org.eclipse.fordiac.ide.model.eval.value.LRealValue;
import org.eclipse.fordiac.ide.model.eval.value.RealValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeValue;
import org.eclipse.fordiac.ide.model.eval.value.ULIntValue;
import org.eclipse.fordiac.ide.model.eval.value.USIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.value.WordValue;

@SuppressWarnings("squid:S100") // ST Name conventions must be used here
public interface StandardFunctions extends Functions {

	// Math functions
	@SuppressWarnings("unchecked")
	static <T extends AnyMagnitudeValue> T ADD(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.add(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyMagnitudeValue> T MUL(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.multiply(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyMagnitudeValue> T SUB(final T first, final T second) {
		return (T) ValueOperations.subtract(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyMagnitudeValue> T DIV(final T first, final T second) {
		return (T) ValueOperations.divideBy(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyMagnitudeValue> T MOD(final T first, final T second) {
		return (T) ValueOperations.remainderBy(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T EXPT(final T first, final T second) {
		return (T) ValueOperations.power(first, second);
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyNumValue> T ABS(final T value) {
		return (T) ValueOperations.abs(value);
	}

	// Selection Functions
	static <T extends AnyValue> T MOVE(final T value) {
		return value;
	}

	static <T extends AnyValue> T SEL(final BoolValue g, final T in0, final T in1) {
		return g.boolValue() ? in1 : in0;
	}

	// TODO: shift and rotation

	static <T extends AnyBitValue, U extends AnyIntValue> T SHR(final T value, final U moveby) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static <T extends AnyBitValue, U extends AnyIntValue> T SHL(final T value, final U moveby) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static <T extends AnyBitValue, U extends AnyIntValue> T ROR(final T value, final U moveby) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static <T extends AnyBitValue, U extends AnyIntValue> T ROL(final T value, final U moveby) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyValue> T AND(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.bitwiseAnd(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue> T OR(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.bitwiseOr(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue> T XOR(final T... values) {
		return Stream.of(values).reduce((a, b) -> (T) ValueOperations.bitwiseXor(a, b)).orElseThrow();
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyBitValue> T NOT(final T first) {
		return (T) ValueOperations.bitwiseNot(first);
	}

	// TODO: selection functions

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue GT(final T... values) {
		T val = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (!ValueOperations.operator_greaterThan(val, values[i])) {
				return BoolValue.FALSE;
			}
			val = values[i];
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue GE(final T... values) {
		T val = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (!ValueOperations.operator_greaterEqualsThan(val, values[i])) {
				return BoolValue.FALSE;
			}
			val = values[i];
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue EQ(final T... values) {
		T val = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (!ValueOperations.equals(val, values[i])) {
				return BoolValue.FALSE;
			}
			val = values[i];
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue LT(final T... values) {
		T val = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (!ValueOperations.operator_lessThan(val, values[i])) {
				return BoolValue.FALSE;
			}
			val = values[i];
		}
		return BoolValue.TRUE;
	}

	@SuppressWarnings("unchecked")
	static <T extends AnyElementaryValue> BoolValue LE(final T... values) {
		T val = values[0];
		for (int i = 1; i < values.length; ++i) {
			if (!ValueOperations.operator_lessEqualsThan(val, values[i])) {
				return BoolValue.FALSE;
			}
			val = values[i];
		}
		return BoolValue.TRUE;
	}

	static <T extends AnyElementaryValue> BoolValue NE(final T first, final T second) {
		return ValueOperations.equals(first, second) ? BoolValue.FALSE : BoolValue.TRUE;
	}

	static <T extends AnyCharsValue> ULIntValue LEN(final T string) {
		return ULIntValue.toULIntValue(string.length());
	}

	static <T extends AnyRealValue> T SQRT(final T value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	// TODO: conversion functions
	static RealValue LREAL_TO_REAL(final LRealValue value) {
		return RealValue.toRealValue(value);
	}

	static DIntValue REAL_TO_DINT(final RealValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue REAL_TO_INT(final RealValue value) {
		return IntValue.toIntValue(value);
	}

	static DWordValue REAL_TO_DWORD(final RealValue value) {
		return DWordValue.toDWordValue(Float.floatToRawIntBits(value.floatValue()));
	}

	static DIntValue LINT_TO_DINT(final LIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static RealValue DINT_TO_REAL(final DIntValue value) {
		return RealValue.toRealValue(value);
	}

	static IntValue DINT_TO_INT(final DIntValue value) {
		return IntValue.toIntValue(value);
	}

	static DWordValue DINT_TO_DWORD(final DIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static RealValue INT_TO_REAL(final IntValue value) {
		return RealValue.toRealValue(value);
	}

	static DIntValue INT_TO_DINT(final IntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static USIntValue INT_TO_USINT(final IntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static DWordValue INT_TO_DWORD(final IntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue INT_TO_WORD(final IntValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	static ByteValue INT_TO_BYTE(final IntValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	static DIntValue DWORD_TO_DINT(final DWordValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue DWORD_TO_INT(final DWordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static IntValue WORD_TO_INT(final WordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static IntValue BYTE_TO_INT(final ByteValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static IntValue TRUNC(final RealValue value) {
		return IntValue.toIntValue(value);
	}

	static TimeValue NOW_MONOTONIC() {
		return TimeValue.toTimeValue(System.nanoTime());
	}

	static LIntValue TIME_IN_MS_TO_LINT(final AnyDurationValue value) {
		return LIntValue.toLIntValue(value.toDuration().toMillis());
	}

	static LRealValue TIME_IN_MS_TO_LREAL(final AnyDurationValue value) {
		return LRealValue.toLRealValue(value.toDuration().toNanos() / 1000000.0);
	}

	static LRealValue TIME_IN_US_TO_LREAL(final AnyDurationValue value) {
		return LRealValue.toLRealValue(value.toDuration().toNanos() / 1000.0);
	}

	// TODO: rest of string functions

	// TODO: date and duration functions


}
