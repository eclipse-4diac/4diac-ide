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
import org.eclipse.fordiac.ide.model.data.AnyNumType
import org.eclipse.fordiac.ide.model.data.BoolType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.junit.jupiter.api.Assertions.*

class ValueTest {
	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testValueEquals(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: {
				assertTrue(type.defaultValue.equals(type.defaultValue))
				assertFalse(type.defaultValue.equals(1.wrapValue(type)))
				assertFalse(type.defaultValue.equals(null))
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testValueHashCode(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: {
				type.defaultValue.hashCode.assertEquals(type.defaultValue.hashCode)
				type.defaultValue.hashCode.assertNotEquals(1.wrapValue(type).hashCode)
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue]
		}
	}

	@ParameterizedTest(name="{index}: {0}")
	@MethodSource("typeArgumentsProvider")
	def void testValueStringConversion(String typeName) {
		switch (type : ElementaryTypes.getTypeByName(typeName)) {
			BoolType,
			AnyNumType: {
				type.defaultValue.assertEquals(type.defaultValue.toString.parseValue(type))
				1.wrapValue(type).assertEquals(1.wrapValue(type).toString.parseValue(type))
			}
			default:
				UnsupportedOperationException.assertThrows[type.defaultValue]
		}
	}

	def static Stream<String> typeArgumentsProvider() {
		DataTypeLibrary.nonUserDefinedDataTypes.stream.map[name]
	}
}
