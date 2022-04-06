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

import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.junit.jupiter.api.Test

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.StringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
import static extension org.junit.jupiter.api.Assertions.*

class StandardFunctionsTest {

	@Test
	def void testNotFound() {
		NoSuchMethodError.assertThrows[StandardFunctions.findMethodFromDataTypes("ILLEGAL_NAME", ElementaryTypes.INT)]
		NoSuchMethodError.assertThrows[StandardFunctions.findMethodFromDataTypes("LEN", ElementaryTypes.INT)]
		NoSuchMethodError.assertThrows [
			StandardFunctions.findMethodFromDataTypes("ADD", ElementaryTypes.INT, ElementaryTypes.STRING)
		]
	}

	@Test
	def void testAdd() {
		StandardFunctions.inferReturnTypeFromDataTypes("ADD").assertNull
		ElementaryTypes.INT.assertEquals(
			StandardFunctions.inferReturnTypeFromDataTypes("ADD", ElementaryTypes.SINT, ElementaryTypes.INT))
		StandardFunctions.findMethodFromDataTypes("ADD", ElementaryTypes.INT, ElementaryTypes.INT).assertNotNull
		StandardFunctions.findMethodFromDataTypes("ADD", ElementaryTypes.INT, ElementaryTypes.INT, ElementaryTypes.INT).
			assertNotNull
		4.toIntValue.assertEquals(StandardFunctions.invoke("ADD", "2".toIntValue, "2".toIntValue))
	}

	@Test
	def void testLen() {
		ElementaryTypes.ULINT.assertEquals(
			StandardFunctions.inferReturnTypeFromDataTypes("LEN", ElementaryTypes.STRING))
		StandardFunctions.findMethodFromDataTypes("LEN", ElementaryTypes.STRING).assertNotNull
		4.toULIntValue.assertEquals(StandardFunctions.invoke("LEN", "abcd".toStringValue))
	}
}
