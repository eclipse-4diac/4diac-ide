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
import org.eclipse.fordiac.ide.model.eval.value.CharValue;
import org.eclipse.fordiac.ide.model.eval.value.DIntValue;
import org.eclipse.fordiac.ide.model.eval.value.DWordValue;
import org.eclipse.fordiac.ide.model.eval.value.DateAndTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.DateValue;
import org.eclipse.fordiac.ide.model.eval.value.IntValue;
import org.eclipse.fordiac.ide.model.eval.value.LDateAndTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.LIntValue;
import org.eclipse.fordiac.ide.model.eval.value.LRealValue;
import org.eclipse.fordiac.ide.model.eval.value.LTimeOfDayValue;
import org.eclipse.fordiac.ide.model.eval.value.LTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.LWordValue;
import org.eclipse.fordiac.ide.model.eval.value.RealValue;
import org.eclipse.fordiac.ide.model.eval.value.SIntValue;
import org.eclipse.fordiac.ide.model.eval.value.StringValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeOfDayValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeValue;
import org.eclipse.fordiac.ide.model.eval.value.UDIntValue;
import org.eclipse.fordiac.ide.model.eval.value.UIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ULIntValue;
import org.eclipse.fordiac.ide.model.eval.value.USIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.eval.value.WCharValue;
import org.eclipse.fordiac.ide.model.eval.value.WStringValue;
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

	/* LREAL_TO */
	static RealValue LREAL_TO_REAL(final LRealValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue LREAL_TO_LINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue LREAL_TO_DINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue LREAL_TO_INT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue LREAL_TO_SINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue LREAL_TO_ULINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue LREAL_TO_UDINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue LREAL_TO_UINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue LREAL_TO_USINT(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue LREAL_TO_LWORD(final LRealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* REAL_TO */
	static LRealValue REAL_TO_LREAL(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue REAL_TO_LINT(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue REAL_TO_DINT(final RealValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue REAL_TO_INT(final RealValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue REAL_TO_SINT(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue REAL_TO_ULINT(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue REAL_TO_UDINT(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue REAL_TO_UINT(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue REAL_TO_USINT(final RealValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue REAL_TO_DWORD(final RealValue value) {
		return DWordValue.toDWordValue(Float.floatToRawIntBits(value.floatValue()));
	}

	/* LINT_TO */
	static LRealValue LINT_TO_LREAL(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue LINT_TO_REAL(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue LINT_TO_DINT(final LIntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static IntValue LINT_TO_INT(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue LINT_TO_SINT(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue LINT_TO_ULINT(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue LINT_TO_UDINT(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue LINT_TO_UINT(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue LINT_TO_USINT(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue LINT_TO_LWORD(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue LINT_TO_DWORD(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue LINT_TO_WORD(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue LINT_TO_BYTE(final LIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* DINT_TO */
	static RealValue DINT_TO_REAL(final DIntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue DINT_TO_LINT(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue DINT_TO_INT(final DIntValue value) {
		return IntValue.toIntValue(value);
	}

	static SIntValue DINT_TO_SINT(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue DINT_TO_ULINT(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue DINT_TO_UDINT(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue DINT_TO_UINT(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue DINT_TO_USINT(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue DINT_TO_LWORD(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue DINT_TO_DWORD(final DIntValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	static WordValue DINT_TO_WORD(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue DINT_TO_BYTE(final DIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* INT_TO */
	static LRealValue INT_TO_LREAL(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue INT_TO_REAL(final IntValue value) {
		return RealValue.toRealValue(value);
	}

	static LIntValue INT_TO_LINT(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue INT_TO_DINT(final IntValue value) {
		return DIntValue.toDIntValue(value);
	}

	static SIntValue INT_TO_SINT(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue INT_TO_ULINT(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue INT_TO_UDINT(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue INT_TO_UINT(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue INT_TO_USINT(final IntValue value) {
		return USIntValue.toUSIntValue(value);
	}

	static LWordValue INT_TO_LWORD(final IntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
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

	/* SINT_TO */
	static LRealValue SINT_TO_LREAL(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue SINT_TO_REAL(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue SINT_TO_LINT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue SINT_TO_DINT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue SINT_TO_INT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue SINT_TO_ULINT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue SINT_TO_UDINT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue SINT_TO_UINT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue SINT_TO_USINT(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue SINT_TO_LWORD(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue SINT_TO_DWORD(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue SINT_TO_WORD(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue SINT_TO_BYTE(final SIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* ULINT_TO */

	static LRealValue ULINT_TO_LREAL(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue ULINT_TO_REAL(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue ULINT_TO_LINT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue ULINT_TO_DINT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue ULINT_TO_INT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue ULINT_TO_SINT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue ULINT_TO_UDINT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue ULINT_TO_UINT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue ULINT_TO_USINT(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue ULINT_TO_LWORD(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue ULINT_TO_DWORD(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue ULINT_TO_WORD(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue ULINT_TO_BYTE(final ULIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* UDINT_TO */

	static LRealValue UDINT_TO_LREAL(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue UDINT_TO_REAL(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue UDINT_TO_LINT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue UDINT_TO_DINT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue UDINT_TO_INT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue UDINT_TO_SINT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue UDINT_TO_ULINT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue UDINT_TO_UINT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue UDINT_TO_USINT(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue UDINT_TO_LWORD(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue UDINT_TO_DWORD(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue UDINT_TO_WORD(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue UDINT_TO_BYTE(final UDIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* UINT_TO */

	static LRealValue UINT_TO_LREAL(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue UINT_TO_REAL(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue UINT_TO_LINT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue UINT_TO_DINT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue UINT_TO_INT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue UINT_TO_SINT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue UINT_TO_ULINT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue UINT_TO_UDINT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue UINT_TO_USINT(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue UINT_TO_LWORD(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue UINT_TO_DWORD(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue UINT_TO_WORD(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue UINT_TO_BYTE(final UIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* USINT_TO */

	static LRealValue USINT_TO_LREAL(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static RealValue USINT_TO_REAL(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue USINT_TO_LINT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue USINT_TO_DINT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue USINT_TO_INT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue USINT_TO_SINT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue USINT_TO_ULINT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue USINT_TO_UDINT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue USINT_TO_UINT(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue USINT_TO_LWORD(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue USINT_TO_DWORD(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue USINT_TO_WORD(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue USINT_TO_BYTE(final USIntValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* LWORD_TO */
	static LRealValue LWORD_TO_LREAL(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue LWORD_TO_LINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue LWORD_TO_DINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue LWORD_TO_INT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue LWORD_TO_SINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue LWORD_TO_ULINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue LWORD_TO_UDINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue LWORD_TO_UINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue LWORD_TO_USINT(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue LWORD_TO_DWORD(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue LWORD_TO_WORD(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue LWORD_TO_BYTE(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static BoolValue LWORD_TO_BOOL(final LWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* DWORD_TO */
	static RealValue DWORD_TO_REAL(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LIntValue DWORD_TO_LINT(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue DWORD_TO_DINT(final DWordValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	static IntValue DWORD_TO_INT(final DWordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue DWORD_TO_SINT(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue DWORD_TO_ULINT(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue DWORD_TO_UDINT(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue DWORD_TO_UINT(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue DWORD_TO_USINT(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue DWORD_TO_LWORD(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue DWORD_TO_WORD(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue DWORD_TO_BYTE(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static BoolValue DWORD_TO_BOOL(final DWordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* WORD_TO */
	static LIntValue WORD_TO_LINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue WORD_TO_DINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue WORD_TO_INT(final WordValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue WORD_TO_SINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue WORD_TO_ULINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue WORD_TO_UDINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue WORD_TO_UINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue WORD_TO_USINT(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue WORD_TO_LWORD(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue WORD_TO_DWORD(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue WORD_TO_BYTE(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static BoolValue WORD_TO_BOOL(final WordValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* BYTE_TO */
	static LIntValue BYTE_TO_LINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue BYTE_TO_DINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue BYTE_TO_INT(final ByteValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	static SIntValue BYTE_TO_SINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue BYTE_TO_ULINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue BYTE_TO_UDINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue BYTE_TO_UINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue BYTE_TO_USINT(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue BYTE_TO_LWORD(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue BYTE_TO_DWORD(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue BYTE_TO_WORD(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static BoolValue BYTE_TO_BOOL(final ByteValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* BOOL_TO */
	static LIntValue BOOL_TO_LINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DIntValue BOOL_TO_DINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static IntValue BOOL_TO_INT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static SIntValue BOOL_TO_SINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ULIntValue BOOL_TO_ULINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UDIntValue BOOL_TO_UDINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static UIntValue BOOL_TO_UINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static USIntValue BOOL_TO_USINT(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LWordValue BOOL_TO_LWORD(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue BOOL_TO_DWORD(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue BOOL_TO_WORD(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue BOOL_TO_BYTE(final BoolValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* CHAR_TO */

	static LWordValue CHAR_TO_LWORD(final CharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue CHAR_TO_DWORD(final CharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue CHAR_TO_WORD(final CharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static ByteValue CHAR_TO_BYTE(final CharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/* WCHAR_TO */

	static LWordValue WCHAR_TO_LWORD(final WCharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DWordValue WCHAR_TO_DWORD(final WCharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WordValue CWHAR_TO_WORD(final WCharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/***************************************/

	static IntValue TRUNC(final RealValue value) {
		return IntValue.toIntValue(value);
	}

	static TimeValue NOW_MONOTONIC() {
		return TimeValue.toTimeValue(System.nanoTime());
	}

	/* Date and Time conversions */

	static TimeValue LTIME_TO_TIME(final LTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LTimeValue TIME_TO_LTIME(final TimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DateAndTimeValue LDT_TO_DT(final LDateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DateValue LDT_TO_DATE(final LDateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LTimeOfDayValue LDT_TO_LTOD(final LDateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static TimeOfDayValue LDT_TO_TOD(final LDateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LDateAndTimeValue DT_TO_LDT(final DateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static DateValue DT_TO_DATE(final DateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LTimeOfDayValue DT_TO_LTOD(final DateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static TimeOfDayValue DT_TO_TOD(final DateAndTimeValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static TimeOfDayValue LTOD_TO_TOD(final LTimeOfDayValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static LTimeOfDayValue TOD_TO_LTOD(final TimeOfDayValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
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

	/* ANY_CHARS conversions */

	static StringValue WSTRING_TO_STRING(final WStringValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WCharValue WSTRING_TO_WCHAR(final WStringValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WStringValue STRING_TO_WSTRING(final StringValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static CharValue STRING_TO_CHAR(final StringValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WStringValue WCHAR_TO_WSTRING(final WCharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static CharValue WCHAR_TO_CHAR(final WCharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static StringValue CHAR_TO_STRING(final CharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	static WCharValue CHAR_TO_WCHAR(final CharValue value) {
		throw new UnsupportedOperationException("Not implemented yet!");
	}


	// TODO: rest of string functions

	// TODO: date and duration functions


}
