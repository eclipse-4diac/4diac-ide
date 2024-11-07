/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.test.model.eval.value;

import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.abs;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.add;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.bitwiseAnd;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.bitwiseNot;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.bitwiseOr;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.bitwiseXor;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.castValue;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.compareTo;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.defaultValue;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.divideBy;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.greaterEquals;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.greaterThan;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.lessEquals;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.lessThan;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.multiply;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.negate;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.parseValue;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.partial;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.power;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.remainderBy;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.resultType;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.reverseBytes;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.subtract;
import static org.eclipse.fordiac.ide.model.eval.value.ValueOperations.wrapValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyCharType;
import org.eclipse.fordiac.ide.model.data.AnyCharsType;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LtimeType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;
import org.eclipse.fordiac.ide.model.eval.value.LTimeValue;
import org.eclipse.fordiac.ide.model.eval.value.TimeValue;
import org.eclipse.fordiac.ide.model.eval.value.Value;
import org.eclipse.fordiac.ide.model.eval.value.ValueOperations;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method", "nls" })
class ValueOperationsTest {
	@Test
	void testNull() {
		assertThrows(UnsupportedOperationException.class, () -> abs(null));
		assertThrows(UnsupportedOperationException.class, () -> negate(null));
		assertThrows(UnsupportedOperationException.class, () -> bitwiseNot(null));
		assertThrows(UnsupportedOperationException.class, () -> add(null, null));
		assertThrows(UnsupportedOperationException.class, () -> subtract(null, null));
		assertThrows(UnsupportedOperationException.class, () -> multiply(null, null));
		assertThrows(UnsupportedOperationException.class, () -> divideBy(null, null));
		assertThrows(UnsupportedOperationException.class, () -> remainderBy(null, null));
		assertThrows(UnsupportedOperationException.class, () -> power(null, null));
		assertThrows(UnsupportedOperationException.class, () -> bitwiseAnd(null, null));
		assertThrows(UnsupportedOperationException.class, () -> bitwiseOr(null, null));
		assertThrows(UnsupportedOperationException.class, () -> bitwiseXor(null, null));
		assertEquals(Boolean.valueOf(true), Boolean.valueOf(ValueOperations.equals(null, null)));
		assertThrows(UnsupportedOperationException.class, () -> lessThan(null, null));
		assertThrows(UnsupportedOperationException.class, () -> lessEquals(null, null));
		assertThrows(UnsupportedOperationException.class, () -> greaterThan(null, null));
		assertThrows(UnsupportedOperationException.class, () -> greaterEquals(null, null));
		assertThrows(UnsupportedOperationException.class, () -> compareTo(null, null));
		assertNull(defaultValue(null));
		assertNull(wrapValue(null, null));
		assertNull(wrapValue(Integer.valueOf(0), null));
		assertThrows(UnsupportedOperationException.class, () -> parseValue("0", null));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testAbs(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyMagnitudeType) {
			assertEquals(defaultValue, abs(defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> abs(defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testNegate(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyMagnitudeType) {
			assertEquals(defaultValue, negate(defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> negate(defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testBitwiseNot(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyBitType) {
			assertNotEquals(defaultValue, bitwiseNot(defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> bitwiseNot(defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testReverseBytes(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertEquals(defaultValue, reverseBytes(defaultValue));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testAdd(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyMagnitudeType) {
			assertEquals(defaultValue, add(defaultValue, defaultValue));
		} else if (type instanceof TimeOfDayType || type instanceof LtodType || type instanceof DateAndTimeType
				|| type instanceof LdtType) {
			assertEquals(defaultValue, add(defaultValue, TimeValue.DEFAULT));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> add(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testSubtract(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyMagnitudeType) {
			assertEquals(defaultValue, subtract(defaultValue, defaultValue));
		} else if (type instanceof TimeOfDayType || type instanceof DateAndTimeType) {
			assertEquals(defaultValue, subtract(defaultValue, TimeValue.DEFAULT));
			assertEquals(TimeValue.DEFAULT, subtract(defaultValue, defaultValue));
		} else if (type instanceof LtodType || type instanceof LdtType) {
			assertEquals(defaultValue, subtract(defaultValue, TimeValue.DEFAULT));
			assertEquals(LTimeValue.DEFAULT, subtract(defaultValue, defaultValue));
		} else if (type instanceof DateType) {
			assertEquals(TimeValue.DEFAULT, subtract(defaultValue, defaultValue));
		} else if (type instanceof LdateType) {
			assertEquals(LTimeValue.DEFAULT, subtract(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> subtract(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testMultiply(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyMagnitudeType) {
			assertEquals(defaultValue, multiply(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> multiply(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testMultiplyTime(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyNumType) {
			assertEquals(TimeValue.DEFAULT, multiply(TimeValue.DEFAULT, defaultValue));
			assertEquals(LTimeValue.DEFAULT, multiply(LTimeValue.DEFAULT, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testDivide(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyRealType) {
			assertNotEquals(defaultValue, divideBy(defaultValue, defaultValue));
		} else if (type instanceof AnyIntType || type instanceof AnyDurationType) {
			assertEquals(defaultValue, divideBy(defaultValue, wrapValue(Integer.valueOf(1), type)));
			assertThrows(ArithmeticException.class, () -> divideBy(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> divideBy(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testDivideTime(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyNumType) {
			assertEquals(TimeValue.DEFAULT, divideBy(TimeValue.DEFAULT, wrapValue(Integer.valueOf(1), type)));
			assertEquals(LTimeValue.DEFAULT, divideBy(LTimeValue.DEFAULT, wrapValue(Integer.valueOf(1), type)));
			assertThrows(ArithmeticException.class, () -> divideBy(TimeValue.DEFAULT, defaultValue));
			assertThrows(ArithmeticException.class, () -> divideBy(TimeValue.DEFAULT, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testRemainder(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyIntType) {
			assertEquals(defaultValue, remainderBy(defaultValue, wrapValue(Integer.valueOf(1), type)));
			assertEquals(defaultValue, ValueOperations.remainderBy(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> remainderBy(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testPower(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyRealType) {
			assertEquals(wrapValue(Integer.valueOf(1), type), power(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> power(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testAnd(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyBitType) {
			assertEquals(defaultValue, bitwiseAnd(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> bitwiseAnd(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testOr(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyBitType) {
			assertEquals(defaultValue, bitwiseOr(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> bitwiseOr(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testXor(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyBitType) {
			assertEquals(defaultValue, bitwiseXor(defaultValue, defaultValue));
		} else {
			assertThrows(UnsupportedOperationException.class, () -> bitwiseXor(defaultValue, defaultValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testEquals(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertEquals(Boolean.valueOf(true), Boolean.valueOf(ValueOperations.equals(defaultValue, defaultValue)));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testLessThan(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertFalse(lessThan(defaultValue, defaultValue));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testLessEquals(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertTrue(lessEquals(defaultValue, defaultValue));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testGreaterThan(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertFalse(greaterThan(defaultValue, defaultValue));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testGreaterEquals(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertTrue(greaterEquals(defaultValue, defaultValue));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testCompareTo(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		assertEquals(0, compareTo(defaultValue, defaultValue));
	}

	@ParameterizedTest(name = "{index}: {0} partial {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	void testPartial(final String typeName, final String partialTypeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final DataType partialType = ValueOperationsTest.getTypeByName(partialTypeName);
		final Value defaultValue = defaultValue(type);
		final Value defaultPartialValue = defaultValue(partialType);
		if (type instanceof final AnyBitType bitType && partialType instanceof final AnyBitType partialBitType
				&& type != partialType && type.isAssignableFrom(partialType)) {
			IntStream.range(0, bitType.getBitSize() / partialBitType.getBitSize()).forEach(index -> {
				assertEquals(defaultValue(partialType), ValueOperations.partial(defaultValue, partialType, index));
				assertEquals(
						wrapValue(Integer.valueOf((0xffffffff >>> (32 - partialBitType.getBitSize()))), partialType),
						partial(wrapValue(Long.valueOf(0xffffffffffffffffL), type), partialType, index));
				assertEquals(defaultValue, partial(defaultValue, partialType, index, defaultValue(partialType)));
				assertEquals(wrapValue(Long.valueOf((0x1L << (index * partialBitType.getBitSize()))), type),
						partial(defaultValue, partialType, index, wrapValue(Integer.valueOf(1), partialType)));

			});
		} else {
			assertThrows(UnsupportedOperationException.class, () -> partial(defaultValue, partialType, 0));
			assertThrows(UnsupportedOperationException.class,
					() -> partial(defaultValue, partialType, 0, defaultPartialValue));
		}
	}

	@ParameterizedTest(name = "{index}: {0} as {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	void testCastValue(final String typeName, final String castTypeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final DataType castType = ValueOperationsTest.getTypeByName(castTypeName);
		final Value defaultValue = defaultValue(type);
		final Value defaultCastValue = defaultValue(castType);
		switch (type) {
		case final BoolType unused -> {
			if (castType instanceof AnyBitType) {
				assertEquals(defaultCastValue, castValue(defaultValue, castType));
			} else {
				assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
			}
		}
		case final AnyMagnitudeType unused -> {
			if (castType instanceof AnyMagnitudeType
					|| (castType instanceof AnyBitType && !(castType instanceof BoolType))) {
				assertEquals(defaultCastValue, castValue(defaultValue, castType));
			} else {
				assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
			}
		}
		case final AnyBitType unused -> {
			if (castType instanceof AnyNumType || castType instanceof AnyBitType) {
				assertEquals(defaultCastValue, castValue(defaultValue, castType));
			} else {
				assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
			}
		}
		case final AnyCharType unused -> {
			if (castType instanceof AnyCharType) {
				assertEquals(defaultCastValue, castValue(defaultValue, castType));
			} else if (castType instanceof AnyStringType) {
				assertEquals(wrapValue(" ", castType), castValue(defaultValue, castType));
			} else {
				assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
			}
		}
		case final AnyStringType unused -> {
			if (castType instanceof AnyStringType) {
				assertEquals(defaultCastValue, castValue(defaultValue, castType));
			} else if ((castType instanceof AnyCharType)) {
				assertEquals(wrapValue("\u0000", castType), castValue(defaultValue, castType));
			} else {
				assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
			}
		}
		case final AnyDateType unused -> {
			if (castType instanceof AnyDateType) {
				assertEquals(defaultCastValue, castValue(defaultValue, castType));
			} else {
				assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
			}
		}
		default -> assertThrows(UnsupportedOperationException.class, () -> castValue(defaultValue, castType));
		}
		assertNull(castValue(null, castType));
	}

	@ParameterizedTest(name = "{index}: {0} as {1}")
	@MethodSource("typeArgumentsElementaryAndGenericsCartesianProvider")
	void testCastValueWithGenerics(final String typeName, final String castTypeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final DataType castType = ValueOperationsTest.getTypeByName(castTypeName);
		final Value defaultValue = defaultValue(type);
		if (castType.isAssignableFrom(type)) {
			assertTrue(castType.isAssignableFrom((DataType) castValue(defaultValue, castType).getType()));
		} else {
			assertThrows(ClassCastException.class, () -> castValue(defaultValue, castType));
		}
		assertNull(castValue(null, castType));
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsWithGenericsProvider")
	void testWrapValue(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		if (type instanceof AnyCharType) {
			assertEquals(defaultValue, wrapValue("\u0000", type));
		} else if (type instanceof AnyCharsType) {
			assertEquals(defaultValue, wrapValue("", type));
		} else if (type instanceof StructuredType) {
			assertEquals(defaultValue, wrapValue(Map.of(), type));
		} else {
			assertEquals(defaultValue, wrapValue(Integer.valueOf(0), type));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	void testParseValue(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		final Value defaultValue = defaultValue(type);
		switch (type) {
		case final AnyNumType unused -> assertEquals(defaultValue, parseValue("0", type));
		case final AnyBitType unused -> assertEquals(defaultValue, parseValue("0", type));
		case final TimeType unused -> assertEquals(defaultValue, parseValue("T#0s", type));
		case final LtimeType unused -> assertEquals(defaultValue, parseValue("LT#0s", type));
		case final AnyCharsType unused -> assertEquals(defaultValue, parseValue("", type));
		case final DateType unused -> assertEquals(defaultValue, parseValue("D#1970-01-01", type));
		case final LdateType unused -> assertEquals(defaultValue, parseValue("LD#1970-01-01", type));
		case final TimeOfDayType unused -> assertEquals(defaultValue, parseValue("TOD#00:00:00", type));
		case final LtodType unused -> assertEquals(defaultValue, parseValue("LTOD#00:00:00", type));
		case final DateAndTimeType unused -> assertEquals(defaultValue, parseValue("DT#1970-01-01-00:00:00", type));
		case final LdtType unused -> assertEquals(defaultValue, parseValue("LDT#1970-01-01-00:00:00", type));
		default -> assertThrows(UnsupportedOperationException.class, () -> parseValue("0", type));
		}
	}

	@ParameterizedTest(name = "{index}: {0}")
	@MethodSource("typeArgumentsWithGenericsProvider")
	void testResultType(final String typeName) {
		final DataType type = ValueOperationsTest.getTypeByName(typeName);
		assertEquals(type, resultType(type, type));
	}

	static DataType getTypeByName(final String typeName) {
		return Stream.of(ElementaryTypes.getAllElementaryType(), GenericTypes.getAllGenericTypes())
				.flatMap(Collection::stream).filter(type -> typeName.equals(type.getName())).findFirst().orElseThrow();
	}

	static Stream<String> typeArgumentsProvider() {
		return DataTypeLibrary.getNonUserDefinedDataTypes().stream().map(DataType::getName);
	}

	static Stream<String> typeArgumentsGenericProvider() {
		return GenericTypes.getAllGenericTypes().stream().map(DataType::getName);
	}

	static Stream<String> typeArgumentsWithGenericsProvider() {
		return Stream.concat(typeArgumentsProvider(), typeArgumentsGenericProvider());
	}

	static Stream<Arguments> typeArgumentsCartesianProvider() {
		return typeArgumentsProvider()
				.flatMap(first -> typeArgumentsProvider().map(second -> Arguments.arguments(first, second)));
	}

	static Stream<Arguments> typeArgumentsElementaryAndGenericsCartesianProvider() {
		return typeArgumentsProvider()
				.flatMap(first -> typeArgumentsGenericProvider().map(second -> Arguments.arguments(first, second)));
	}
}
