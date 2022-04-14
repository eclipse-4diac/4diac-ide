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

import org.eclipse.fordiac.ide.model.eval.function.StandardFunctions
import org.junit.jupiter.api.Test

import static extension org.eclipse.fordiac.ide.model.eval.function.Functions.*
import static extension org.eclipse.fordiac.ide.model.eval.value.IntValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.StringValue.*
import static extension org.eclipse.fordiac.ide.model.eval.value.ULIntValue.*
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
}
