/*******************************************************************************
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import java.time.LocalDate
import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup
import org.eclipse.fordiac.ide.model.data.AnyCharType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

import static org.eclipse.fordiac.ide.model.eval.variable.ArrayVariable.*

import static extension org.eclipse.fordiac.ide.model.eval.value.DIntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.junit.jupiter.api.Assertions.*

class ValueTest {
	@BeforeAll
	def static void setupXtext() {
		GlobalConstantsStandaloneSetup.doSetup
		STFunctionStandaloneSetup.doSetup
		STAlgorithmStandaloneSetup.doSetup
		StructuredTextEvaluatorFactory.register
	}

	@Test
	def void testValueEquals() {
		DataTypeLibrary.nonUserDefinedDataTypes.forEach [ type |
			assertTrue(type.defaultValue.equals(type.defaultValue))
			assertFalse(type.defaultValue.equals(null))
			switch (type) {
				AnyCharType,
				AnyStringType: assertFalse(type.defaultValue.equals("a".wrapValue(type)))
				default: assertFalse(type.defaultValue.equals(1.wrapValue(type)))
			}
		]
	}

	@Test
	def void testValueHashCode() {
		DataTypeLibrary.nonUserDefinedDataTypes.forEach [ type |
			type.defaultValue.hashCode.assertEquals(type.defaultValue.hashCode)
			switch (type) {
				AnyCharType,
				AnyStringType: type.defaultValue.hashCode.assertNotEquals("a".wrapValue(type).hashCode)
				default: type.defaultValue.hashCode.assertNotEquals(1.wrapValue(type).hashCode)
			}
		]
	}

	@Test
	def void testValueStringConversion() {
		DataTypeLibrary.nonUserDefinedDataTypes.forEach [ type |
			type.defaultValue.assertEquals(type.defaultValue.toString.parseValue(type))
			switch (type) {
				AnyCharType,
				AnyStringType:
					"a".wrapValue(type).assertEquals("a".wrapValue(type).toString.parseValue(type))
				DateType,
				LdateType:
					LocalDate.of(1970, 01, 02).wrapValue(type).assertEquals(
						LocalDate.of(1970, 01, 02).wrapValue(type).toString.parseValue(type))
				default:
					1.wrapValue(type).assertEquals(1.wrapValue(type).toString.parseValue(type))
			}
		]
	}

	@Test
	def void testArrayValueStringConversion() {
		val smallArray = new ArrayVariable("TEST", newArrayType(ElementaryTypes.DINT, newSubrange(0, 4)))
		"[0, 0, 0, 0, 0]".assertEquals(smallArray.toString)
		val largeArray = new ArrayVariable("TEST", newArrayType(ElementaryTypes.DINT, newSubrange(0, 255)))
		"[256(0)]".assertEquals(largeArray.toString)
		val largeDistinctArray = new ArrayVariable("TEST", newArrayType(ElementaryTypes.DINT, newSubrange(0, 255)));
		(0 .. 255).forEach[largeDistinctArray.get(it).value = it.toDIntValue]
		(0 .. 255).join('[', ", ", ']')[toString].assertEquals(largeDistinctArray.toString)
		smallArray.value = "[1, 2, 3, 4, 5]"
		assertIterableEquals((1 .. 5).map[toDIntValue], smallArray.value)
		largeArray.value = "[256(1)]"
		assertIterableEquals((0 .. 255).map[1.toDIntValue], largeArray.value)
		largeDistinctArray.value = "[17(4)]"
		assertIterableEquals((0 .. 255).map[(it < 17 ? 4 : 0).toDIntValue], largeDistinctArray.value)
	}
}
