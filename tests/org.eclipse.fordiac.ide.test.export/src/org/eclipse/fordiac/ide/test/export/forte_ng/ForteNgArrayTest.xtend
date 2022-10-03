/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Ernst Blecha - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.export.forte_ng

import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteNgArrayTest extends ExporterTestBasicFBTypeBase {

	protected static final boolean VALID_DECLARATION = true
	protected static final boolean INVALID_DECLARATION = !VALID_DECLARATION

	@Test
	def void generatedDWORDArrayDeclaration() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
		  «VARIABLE_NAME» : ARRAY [0..31] OF DWORD;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
			CIEC_ARRAY_FIXED<CIEC_DWORD, 0, 31> st_lv_variable = {};
			
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void generatedDWORDArrayDeclarationWithInitializer() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
		  «VARIABLE_NAME» : ARRAY [0..31] OF DWORD := [0, 1, 2, 3(7)];
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
			CIEC_ARRAY_FIXED<CIEC_DWORD, 0, 31> st_lv_variable = {CIEC_DWORD(0), CIEC_DWORD(1), CIEC_DWORD(2), CIEC_DWORD(7), CIEC_DWORD(7), CIEC_DWORD(7)};
			
		'''.toString(), generatedCode.toString())
	}
}
