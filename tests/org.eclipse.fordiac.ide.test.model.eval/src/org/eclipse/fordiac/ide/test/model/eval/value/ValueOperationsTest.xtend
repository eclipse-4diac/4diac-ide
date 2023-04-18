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
package org.eclipse.fordiac.ide.test.model.eval.value

import java.util.stream.Stream
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyCharType
import org.eclipse.fordiac.ide.model.data.AnyCharsType
import org.eclipse.fordiac.ide.model.data.AnyDateType
import org.eclipse.fordiac.ide.model.data.AnyDurationType
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.eval.value.LTimeValue
import org.eclipse.fordiac.ide.model.eval.value.TimeValue
import org.eclipse.fordiac.ide.model.eval.value.Value
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.params.provider.Arguments.*

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.junit.jupiter.api.Assertions.*

class ValueOperationsTest {

	@Test
	def void testNull() {
		NullPointerException.assertThrows[+(null as Value)]
		NullPointerException.assertThrows[-(null as Value)]
		NullPointerException.assertThrows[(null as Value).bitwiseNot]
		IllegalArgumentException.assertThrows[(null as Value) + (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) - (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) * (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) / (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) % (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) ** (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value).bitwiseAnd(null as Value)]
		IllegalArgumentException.assertThrows[(null as Value).bitwiseOr(null as Value)]
		IllegalArgumentException.assertThrows[(null as Value).bitwiseXor(null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) == (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) != (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) <= (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) <= (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) > (null as Value)]
		IllegalArgumentException.assertThrows[(null as Value) >= (null as Value)]
		null.defaultValue.assertNull
		null.wrapValue(null).assertNull
		0.wrapValue(null).assertNull
		UnsupportedOperationException.assertThrows["0".parseValue(null)]
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testAbs(String typeName) {
		switch (type : typeName.typeByName) {
			AnyMagnitudeType: type.defaultValue.assertEquals(+type.defaultValue)
			default: UnsupportedOperationException.assertThrows[+type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testNegate(String typeName) {
		switch (type : typeName.typeByName) {
			AnyMagnitudeType: type.defaultValue.assertEquals(-type.defaultValue)
			default: UnsupportedOperationException.assertThrows[-type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testBitwiseNot(String typeName) {
		switch (type : typeName.typeByName) {
			AnyBitType: type.defaultValue.assertNotEquals(type.defaultValue.bitwiseNot)
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseNot]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testReverseBytes(String typeName) {
		val type = typeName.typeByName
		type.defaultValue.assertEquals(type.defaultValue.reverseBytes)
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testAdd(String typeName) {
		switch (type : typeName.typeByName) {
			AnyMagnitudeType:
				type.defaultValue.assertEquals(type.defaultValue + type.defaultValue)
			TimeOfDayType,
			LtodType,
			DateAndTimeType,
			LdtType:
				type.defaultValue.assertEquals(type.defaultValue + TimeValue.DEFAULT)
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue + type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testSubtract(String typeName) {
		switch (type : typeName.typeByName) {
			AnyMagnitudeType:
				type.defaultValue.assertEquals(type.defaultValue - type.defaultValue)
			TimeOfDayType,
			DateAndTimeType: {
				type.defaultValue.assertEquals(type.defaultValue - TimeValue.DEFAULT)
				TimeValue.DEFAULT.assertEquals(type.defaultValue - type.defaultValue)
			}
			LtodType,
			LdtType: {
				type.defaultValue.assertEquals(type.defaultValue - TimeValue.DEFAULT)
				LTimeValue.DEFAULT.assertEquals(type.defaultValue - type.defaultValue)
			}
			DateType:
				TimeValue.DEFAULT.assertEquals(type.defaultValue - type.defaultValue)
			LdateType:
				LTimeValue.DEFAULT.assertEquals(type.defaultValue - type.defaultValue)
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue - type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testMultiply(String typeName) {
		switch (type : typeName.typeByName) {
			AnyMagnitudeType: type.defaultValue.assertEquals(type.defaultValue * type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue * type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testMultiplyTime(String typeName) {
		switch (type : typeName.typeByName) {
			AnyNumType: {
				TimeValue.DEFAULT.assertEquals(TimeValue.DEFAULT * type.defaultValue)
				LTimeValue.DEFAULT.assertEquals(LTimeValue.DEFAULT * type.defaultValue)
			}
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testDivide(String typeName) {
		switch (type : typeName.typeByName) {
			AnyRealType:
				type.defaultValue.assertNotEquals(type.defaultValue / type.defaultValue)
			AnyIntType,
			AnyDurationType: {
				type.defaultValue.assertEquals(type.defaultValue / 1.wrapValue(type))
				ArithmeticException.assertThrows[type.defaultValue / type.defaultValue]
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue / type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testDivideTime(String typeName) {
		switch (type : typeName.typeByName) {
			AnyNumType: {
				TimeValue.DEFAULT.assertEquals(TimeValue.DEFAULT / 1.wrapValue(type))
				LTimeValue.DEFAULT.assertEquals(LTimeValue.DEFAULT / 1.wrapValue(type))
				ArithmeticException.assertThrows[TimeValue.DEFAULT / type.defaultValue]
				ArithmeticException.assertThrows[TimeValue.DEFAULT / type.defaultValue]
			}
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testRemainder(String typeName) {
		switch (type : typeName.typeByName) {
			AnyIntType: {
				type.defaultValue.assertEquals(type.defaultValue % 1.wrapValue(type))
				type.defaultValue.assertEquals(type.defaultValue % type.defaultValue) // MOD by 0 defined to return 0
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue % type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testPower(String typeName) {
		switch (type : typeName.typeByName) {
			AnyRealType:
				1.wrapValue(type).assertEquals(type.defaultValue ** type.defaultValue)
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue ** type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testAnd(String typeName) {
		switch (type : typeName.typeByName) {
			AnyBitType: type.defaultValue.assertEquals(type.defaultValue.bitwiseAnd(type.defaultValue))
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseAnd(type.defaultValue)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testOr(String typeName) {
		switch (type : typeName.typeByName) {
			AnyBitType: type.defaultValue.assertEquals(type.defaultValue.bitwiseOr(type.defaultValue))
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseOr(type.defaultValue)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testXor(String typeName) {
		switch (type : typeName.typeByName) {
			AnyBitType: type.defaultValue.assertEquals(type.defaultValue.bitwiseXor(type.defaultValue))
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseXor(type.defaultValue)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testEquals(String typeName) {
		val type = typeName.typeByName
		true.assertEquals(type.defaultValue == type.defaultValue)
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testNotEquals(String typeName) {
		val type = typeName.typeByName
		false.assertEquals(type.defaultValue != type.defaultValue)
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testLessThan(String typeName) {
		val type = typeName.typeByName
		false.assertEquals(type.defaultValue < type.defaultValue)
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testLessEquals(String typeName) {
		val type = typeName.typeByName
		true.assertEquals(type.defaultValue <= type.defaultValue)
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testGreaterThan(String typeName) {
		val type = typeName.typeByName
		false.assertEquals(type.defaultValue > type.defaultValue)
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testGreaterEquals(String typeName) {
		val type = typeName.typeByName
		true.assertEquals(type.defaultValue >= type.defaultValue)
	}

	@ParameterizedTest(name="{index}: {0} partial {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	def void testPartial(String typeName, String partialTypeName) {
		val type = typeName.typeByName
		val partialType = partialTypeName.typeByName
		// both type and partialType must be ANY_BIT types and the partialType must be smaller than type
		if (type instanceof AnyBitType && partialType instanceof AnyBitType && type != partialType &&
			type.isAssignableFrom(partialType)) {
			for (index : 0 ..< (type as AnyBitType).bitSize / (partialType as AnyBitType).bitSize) {
				partialType.defaultValue.assertEquals(type.defaultValue.partial(partialType, index))
				(0xffffffff >>> (32 - (partialType as AnyBitType).bitSize)).wrapValue(partialType).assertEquals(
					0xffffffffffffffff#L.wrapValue(type).partial(partialType, index))
				type.defaultValue.assertEquals(type.defaultValue.partial(partialType, index, partialType.defaultValue))
				(0x1#L << (index * (partialType as AnyBitType).bitSize)).wrapValue(type).assertEquals(
					type.defaultValue.partial(partialType, index, 1.wrapValue(partialType)))
			}
		} else {
			UnsupportedOperationException.assertThrows[type.defaultValue.partial(partialType, 0)]
			UnsupportedOperationException.assertThrows [
				type.defaultValue.partial(partialType, 0, partialType.defaultValue)
			]
		}
	}

	@ParameterizedTest(name="{index}: {0} as {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	def void testCastValue(String typeName, String castTypeName) {
		val castType = castTypeName.typeByName
		switch (type : typeName.typeByName) {
			BoolType: {
				if (castType instanceof AnyBitType)
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			AnyMagnitudeType: {
				if (castType instanceof AnyMagnitudeType ||
					(castType instanceof AnyBitType && !(castType instanceof BoolType)))
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			AnyBitType: {
				if (castType instanceof AnyNumType ||
					(castType instanceof AnyBitType && !(castType instanceof BoolType)))
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			AnyCharType: {
				if (castType instanceof AnyCharType)
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else if (castType instanceof AnyStringType)
					"\u0000".wrapValue(castType).assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			AnyStringType: {
				if (castType instanceof AnyStringType)
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else if (castType instanceof AnyCharType)
					"\u0000".wrapValue(castType).assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			AnyDateType: {
				if (castType instanceof AnyDateType)
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue.castValue(castType)]
		}
		null.castValue(castType).assertNull
	}

	@ParameterizedTest(name="{index}: {0} as {1}")
	@MethodSource("typeArgumentsElementaryAndGenericsCartesianProvider")
	def void testCastValueWithGenerics(String typeName, String castTypeName) {
		val type = typeName.typeByName
		val castType = castTypeName.typeByName
		if (castType.isAssignableFrom(type)) {
			assertTrue(castType.isAssignableFrom(type.defaultValue.castValue(castType).type as DataType))
		} else {
			ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
		}
		null.castValue(castType).assertNull
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsWithGenericsProvider")
	def void testWrapValue(String typeName) {
		switch (type : typeName.typeByName) {
			AnyCharType: type.defaultValue.assertEquals("\u0000".wrapValue(type))
			AnyCharsType: type.defaultValue.assertEquals("".wrapValue(type))
			default: type.defaultValue.assertEquals(0.wrapValue(type))
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testParseValue(String typeName) {
		switch (type : typeName.typeByName) {
			AnyNumType,
			AnyBitType: type.defaultValue.assertEquals("0".parseValue(type))
			TimeType: type.defaultValue.assertEquals("T#0s".parseValue(type))
			LtimeType: type.defaultValue.assertEquals("LT#0s".parseValue(type))
			AnyCharsType: type.defaultValue.assertEquals("".parseValue(type))
			DateType: type.defaultValue.assertEquals("D#1970-01-01".parseValue(type))
			LdateType: type.defaultValue.assertEquals("LD#1970-01-01".parseValue(type))
			TimeOfDayType: type.defaultValue.assertEquals("TOD#00:00:00".parseValue(type))
			LtodType: type.defaultValue.assertEquals("LTOD#00:00:00".parseValue(type))
			DateAndTimeType: type.defaultValue.assertEquals("DT#1970-01-01-00:00:00".parseValue(type))
			LdtType: type.defaultValue.assertEquals("LDT#1970-01-01-00:00:00".parseValue(type))
			default: UnsupportedOperationException.assertThrows["0".parseValue(type)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsWithGenericsProvider")
	def void testResultType(String typeName) {
		val type = typeName.typeByName
		type.assertEquals(type.resultType(type))
	}

	def static DataType getTypeByName(String typeName) {
		return (ElementaryTypes.allElementaryType + GenericTypes.allGenericTypes).findFirst[name == typeName]
	}

	def static Stream<String> typeArgumentsProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.map[name]
	}

	def static Stream<Arguments> typeArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ first |
			DataTypeLibrary.nonUserDefinedDataTypes.stream.map[second|arguments(first.name, second.name)]
		]
	}

	def static Stream<String> typeArgumentsWithGenericsProvider() {
		Stream.concat(ElementaryTypes.allElementaryType.stream, GenericTypes.allGenericTypes.stream).map[name]
	}

	def static Stream<Arguments> typeArgumentsElementaryAndGenericsCartesianProvider() {
		ElementaryTypes.allElementaryType.stream.flatMap [ first |
			GenericTypes.allGenericTypes.stream.map [ second |
				arguments(first.name, second.name)
			]
		]
	}
}
