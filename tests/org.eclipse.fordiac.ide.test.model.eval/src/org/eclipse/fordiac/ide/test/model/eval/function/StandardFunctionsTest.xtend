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
package org.eclipse.fordiac.ide.test.model.eval.function

import java.util.stream.Stream
import org.eclipse.fordiac.ide.model.data.AnyCharType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.params.provider.Arguments.*

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ByteValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.DWordValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LRealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.LWordValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.RealValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.StringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.model.eval.value.WordValue.*
import static extension org.junit.jupiter.api.Assertions.*

class StandardFunctionsTest {

	@Test
	def void testAdd() {
		4.toIntValue.assertEquals(StandardFunctions.invoke("ADD", "2".toIntValue, "2".toIntValue))
	}

	@Test
	def void testLen() {
		4.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "Test".toStringValue))
	}

	@Test
	def void testShiftLeft() {
		6.toByteValue.assertEquals(StandardFunctions.invoke("SHL", 3.toByteValue, 1.toIntValue))
		6.toWordValue.assertEquals(StandardFunctions.invoke("SHL", 3.toWordValue, 1.toIntValue))
		6.toDWordValue.assertEquals(StandardFunctions.invoke("SHL", 3.toDWordValue, 1.toIntValue))
		6.toLWordValue.assertEquals(StandardFunctions.invoke("SHL", 3.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testShiftRight() {
		3.toByteValue.assertEquals(StandardFunctions.invoke("SHR", 6.toByteValue, 1.toIntValue))
		3.toWordValue.assertEquals(StandardFunctions.invoke("SHR", 6.toWordValue, 1.toIntValue))
		3.toDWordValue.assertEquals(StandardFunctions.invoke("SHR", 6.toDWordValue, 1.toIntValue))
		3.toLWordValue.assertEquals(StandardFunctions.invoke("SHR", 6.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testRotateLeft() {
		3.toByteValue.assertEquals(StandardFunctions.invoke("ROL", 0x81.toByteValue, 1.toIntValue))
		3.toWordValue.assertEquals(StandardFunctions.invoke("ROL", 0x8001.toWordValue, 1.toIntValue))
		3.toDWordValue.assertEquals(StandardFunctions.invoke("ROL", 0x80000001.toDWordValue, 1.toIntValue))
		3.toLWordValue.assertEquals(StandardFunctions.invoke("ROL", 0x8000000000000001#L.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testRotateRight() {
		0x81.toByteValue.assertEquals(StandardFunctions.invoke("ROR", 3.toByteValue, 1.toIntValue))
		0x8001.toWordValue.assertEquals(StandardFunctions.invoke("ROR", 3.toWordValue, 1.toIntValue))
		0x80000001.toDWordValue.assertEquals(StandardFunctions.invoke("ROR", 3.toDWordValue, 1.toIntValue))
		0x8000000000000001#L.toLWordValue.assertEquals(StandardFunctions.invoke("ROR", 3.toLWordValue, 1.toIntValue))
	}

	@Test
	def void testSqrt() {
		1.toLRealValue.assertEquals(StandardFunctions.invoke("SQRT", 1.toLRealValue))
		2.toLRealValue.assertEquals(StandardFunctions.invoke("SQRT", 4.toLRealValue))
		3.toLRealValue.assertEquals(StandardFunctions.invoke("SQRT", 9.toLRealValue))
		1.toRealValue.assertEquals(StandardFunctions.invoke("SQRT", 1.toRealValue))
		2.toRealValue.assertEquals(StandardFunctions.invoke("SQRT", 4.toRealValue))
		3.toRealValue.assertEquals(StandardFunctions.invoke("SQRT", 9.toRealValue))
	}

	@ParameterizedTest(name="{index}: {0} as {1}")
	@MethodSource("typeArgumentsCartesianProvider")
	def void testConversions(String typeName, String castTypeName) {
		val type = ElementaryTypes.getTypeByName(typeName)
		val castType = ElementaryTypes.getTypeByName(castTypeName)
		val exists = try {
				StandardFunctions.findMethodFromDataTypes('''«typeName»_TO_«castTypeName»''', type)
				true
			} catch (NoSuchMethodError e) {
				false
			}
		if (exists) {
			(if (castType instanceof AnyStringType && type instanceof AnyCharType)
				"\u0000".wrapValue(castType)
			else
				castType.defaultValue
			).assertEquals(StandardFunctions.invoke('''«typeName»_TO_«castTypeName»''', type.defaultValue))
		}
	}

	def static Stream<Arguments> typeArgumentsCartesianProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.flatMap [ first |
			DataTypeLibrary.nonUserDefinedDataTypes.stream.map[second|arguments(first.name, second.name)]
		]
	}
}
