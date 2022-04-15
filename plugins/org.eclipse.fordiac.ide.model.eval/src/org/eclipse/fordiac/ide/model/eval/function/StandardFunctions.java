/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.function;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.eval.value.AnyBitValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyCharsValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyElementaryValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyMagnitudeValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyNumValue;
import org.eclipse.fordiac.ide.model.eval.value.AnyValue;
import org.eclipse.fordiac.ide.model.eval.value.BoolValue;
import org.eclipse.fordiac.ide.model.eval.value.ULIntValue;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;

public interface StandardFunctions extends Functions {

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

	static <T extends AnyValue> T MOVE(final T first) {
		return first;
	}

	// TODO: shift and rotation

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

	// TODO: rest of string functions

	// TODO: date and duration functions

	// TODO: conversion functions
}
