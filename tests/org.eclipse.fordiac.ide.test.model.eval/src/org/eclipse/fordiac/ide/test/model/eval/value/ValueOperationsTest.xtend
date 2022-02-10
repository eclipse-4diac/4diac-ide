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
import org.eclipse.fordiac.ide.model.data.AnyIntType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyRealType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
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
		"0".parseValue(null).assertNull
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testAbs(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: type.defaultValue.assertEquals(+type.defaultValue)
			default: UnsupportedOperationException.assertThrows[+type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testNegate(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: type.defaultValue.assertEquals(-type.defaultValue)
			default: UnsupportedOperationException.assertThrows[-type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testBitwiseNot(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyIntType: type.defaultValue.assertNotEquals(type.defaultValue.bitwiseNot)
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseNot]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testAdd(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: type.defaultValue.assertEquals(type.defaultValue + type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue + type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testSubtract(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: type.defaultValue.assertEquals(type.defaultValue - type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue - type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testMultiply(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: type.defaultValue.assertEquals(type.defaultValue * type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue * type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testDivide(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyRealType:
				type.defaultValue.assertNotEquals(type.defaultValue / type.defaultValue)
			AnyIntType: {
				type.defaultValue.assertEquals(type.defaultValue / 1.wrapValue(type))
				ArithmeticException.assertThrows[type.defaultValue / type.defaultValue]
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue / type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testRemainder(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyRealType:
				type.defaultValue.assertNotEquals(type.defaultValue / type.defaultValue)
			AnyIntType: {
				type.defaultValue.assertEquals(type.defaultValue % 1.wrapValue(type))
				ArithmeticException.assertThrows[type.defaultValue % type.defaultValue]
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue % type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testPower(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyRealType:
				1.wrapValue(type).assertEquals(type.defaultValue ** type.defaultValue)
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue ** type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testAnd(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyIntType: type.defaultValue.assertEquals(type.defaultValue.bitwiseAnd(type.defaultValue))
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseAnd(type.defaultValue)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testOr(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyIntType: type.defaultValue.assertEquals(type.defaultValue.bitwiseOr(type.defaultValue))
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseOr(type.defaultValue)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testXor(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyIntType: type.defaultValue.assertEquals(type.defaultValue.bitwiseXor(type.defaultValue))
			default: UnsupportedOperationException.assertThrows[type.defaultValue.bitwiseXor(type.defaultValue)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testEquals(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: true.assertEquals(type.defaultValue == type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testNotEquals(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: false.assertEquals(type.defaultValue != type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testLessThan(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: false.assertEquals(type.defaultValue < type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue < type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testLessEquals(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: true.assertEquals(type.defaultValue <= type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue <= type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testGreaterThan(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: false.assertEquals(type.defaultValue > type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue > type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testGreaterEquals(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			AnyNumType: true.assertEquals(type.defaultValue >= type.defaultValue)
			default: UnsupportedOperationException.assertThrows[type.defaultValue >= type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {1} as {0}")
	@MethodSource("typeArgumentsCartesianProvider")
	def void testCastValue(String typeName, String castTypeName) {
		val castType = ElementaryTypes.getTypeByName(castTypeName)
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType: {
				if (castType instanceof BoolType)
					castType.defaultValue.assertEquals(type.defaultValue.castValue(castType))
				else
					ClassCastException.assertThrows[type.defaultValue.castValue(castType)]
				type.defaultValue.castValue(null).type.defaultValue
			}
			AnyNumType: {
				if (castType instanceof AnyNumType)
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

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testWrapValue(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: type.defaultValue.assertEquals(0.wrapValue(type))
			default: UnsupportedOperationException.assertThrows[0.wrapValue(type)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testParseValue(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: type.defaultValue.assertEquals("0".parseValue(type))
			default: UnsupportedOperationException.assertThrows["0".parseValue(type)]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testResultType(String typeName) {
		val type = ElementaryTypes.getTypeByName(typeName)
		type.assertEquals(type.resultType(type))
	}

	def static Stream<String> typeArgumentsProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.map[name]
	}

	def static Stream<Arguments> typeArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ first |
			DataTypeLibrary.nonUserDefinedDataTypes.stream.map[second|arguments(first.name, second.name)]
		]
	}
}
