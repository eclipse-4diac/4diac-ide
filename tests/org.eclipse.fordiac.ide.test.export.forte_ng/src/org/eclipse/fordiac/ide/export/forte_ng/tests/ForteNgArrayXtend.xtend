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

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgArrayXtend extends ForteNgTestBase {

	static final String ALGORITHM_NAME = "algorithm" //$NON-NLS-1$
	protected static final boolean INVALID_DECLARATION = false
	protected static final boolean VALID_DECLARATION = true

	@Test
	def void generatedDWORDArrayDeclaration() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  dwordarray : ARRAY [0..31] OF DWORD;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		CIEC_DWORD dwordarray[32];
		'''.toString(), generatedCode.toString())
	}
	
	@Test
	def void generatedDWORDArrayDeclarationWithInitializer() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  dwordarray : ARRAY [0..31] OF DWORD := 0;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertErrors(errors);
		assertNull(generatedCode);
		
		assertErrorMessages(errors, "Local arrays can not be initialized.");
	}
	
	@Test
	def void generatedDWORDLocatedArrayDeclarationWithInitializer() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  variable : DWORD;
		  dwordarray AT variable : ARRAY [0..31] OF DWORD := 0;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertErrors(errors);
		assertNull(generatedCode);
		
		assertErrorMessages(errors, "Located variables can not be initialized.");
	}
	
	@Test
	def void generatedDWORDArrayDeclarationStartNot0() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  dwordarray : ARRAY [5..31] OF DWORD;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertErrors(errors);
		assertNull(generatedCode);
		
		assertErrorMessages(errors, "Only arrays with a start index of 0 are supported.");
	}
	
	@Test
	def void generatedDWORDArrayDeclarationNotIncrementing() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  dwordarray : ARRAY [0..0] OF DWORD;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertErrors(errors);
		assertNull(generatedCode);
		
		assertErrorMessages(errors, "Only arrays with incrementing index are supported.");
	}
	
	@Test
	def void generatedDWORDArrayDeclarationAtDWORD() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  mydword : DWORD;
		  dwordarray AT mydword : ARRAY [0..31] OF DWORD;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertErrors(errors);
		assertNull(generatedCode);
		
		assertErrorMessages(errors, "Piecewise located variables cannot access more bits than are available in the destination");
	}
	
	@Test
	def void generatedRelocatedDWORD() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  variable : DWORD;
		  variable2 AT variable : DWORD;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		CIEC_DWORD variable;
		// replacing all instances of DWORD:variable2 with variable
		'''.toString(), generatedCode.toString())
	}
	
	@Test
	def void generatedRelocatedDINT() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  variable : DINT;
		  variable2 AT variable : DINT;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		CIEC_DINT variable;
		// replacing all instances of DINT:variable2 with variable
		'''.toString(), generatedCode.toString())
	}
	
	@Test
	def void generatedRelocatedDINTincorrectTypes() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  variable : DINT;
		  variable2 AT variable : INT;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertErrors(errors);
		assertNull(generatedCode);
		
		assertErrorMessages(errors, "General located variables must have matching types");
	}
	
}
