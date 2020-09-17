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

package org.eclipse.fordiac.ide.export.forte_ng.tests

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgArrayTest extends ForteNgTestBasicFBTypeBase {

	protected static final boolean VALID_DECLARATION = true
	protected static final boolean INVALID_DECLARATION = !VALID_DECLARATION

	@Test
	def void generatedDWORDArrayDeclaration() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : ARRAY [0..31] OF DWORD;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		CIEC_DWORD «EXPORTED_VARIABLE_NAME»[32];
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void generatedDWORDArrayDeclarationWithInitializer() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : ARRAY [0..31] OF DWORD := 0;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertNull(generatedCode);

		assertErrorMessages(errors, "Local arrays can not be initialized."); //$NON-NLS-1$
	}

	@Test
	def void generatedDWORDLocatedArrayDeclarationWithInitializer() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : DWORD;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : ARRAY [0..31] OF DWORD := 0;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertNull(generatedCode);

		assertErrorMessages(errors, "Located variables can not be initialized."); //$NON-NLS-1$
	}

	@Test
	def void generatedDWORDArrayDeclarationStartNot0() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : ARRAY [5..31] OF DWORD;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertNull(generatedCode);

		assertErrorMessages(errors, "Only arrays with a start index of 0 are supported."); //$NON-NLS-1$
	}

	@Test
	def void generatedDWORDArrayDeclarationNotIncrementing() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : ARRAY [0..0] OF DWORD;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertNull(generatedCode);

		assertErrorMessages(errors, "Only arrays with incrementing index are supported."); //$NON-NLS-1$
	}

	@Test
	def void generatedDWORDArrayDeclarationAtDWORD() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : DWORD;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : ARRAY [0..31] OF DWORD;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertNull(generatedCode);

		assertErrorMessages(errors, "Piecewise located variables cannot access more bits than are available in the destination"); //$NON-NLS-1$
	}

	@Test
	def void generatedRelocatedDWORD() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : DWORD;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : DWORD;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		CIEC_DWORD «EXPORTED_VARIABLE_NAME»;
		// replacing all instances of DWORD:«EXPORTED_VARIABLE2_NAME» with «EXPORTED_VARIABLE_NAME»
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void generatedRelocatedDINT() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : DINT;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : DINT;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		CIEC_DINT «EXPORTED_VARIABLE_NAME»;
		// replacing all instances of DINT:«EXPORTED_VARIABLE2_NAME» with «EXPORTED_VARIABLE_NAME»
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void generatedRelocatedDINTincorrectTypes() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : DINT;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : INT;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertNull(generatedCode);

		assertErrorMessages(errors, "General located variables must have matching types"); //$NON-NLS-1$
	}

}
