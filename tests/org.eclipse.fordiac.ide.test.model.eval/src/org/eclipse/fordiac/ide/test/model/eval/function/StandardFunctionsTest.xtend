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
import org.eclipse.fordiac.ide.model.data.AnyBitType
import org.eclipse.fordiac.ide.model.data.AnyCharType
import org.eclipse.fordiac.ide.model.data.AnyDateType
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.junit.jupiter.params.provider.Arguments.*

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.StringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
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
