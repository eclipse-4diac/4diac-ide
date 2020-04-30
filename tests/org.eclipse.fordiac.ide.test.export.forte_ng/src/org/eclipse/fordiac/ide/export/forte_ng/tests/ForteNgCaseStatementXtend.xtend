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

import org.junit.Test
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

abstract class ForteNgCaseStatementXtend extends ForteNgTestBase {

	static final String ALGORITHM_NAME = "algorithm" //$NON-NLS-1$
	static final String VARIABLE_NAME = "variable" //$NON-NLS-1$
	static final String VARIABLE2_NAME = "variable2" //$NON-NLS-1$
	static final String DINT = "DINT" //$NON-NLS-1$
	static final String BOOL = "BOOL" //$NON-NLS-1$

	@Test
	def void validCaseStatement() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		CASE variable OF
			0: «VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			1: «VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			2: «VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			255: «VARIABLE_NAME» := 0;
		END_CASE;'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)
				
		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («VARIABLE_NAME»()) {
			case 0:
				«VARIABLE_NAME»() = («VARIABLE_NAME»() + 1);
				break;
			case 1:
				«VARIABLE_NAME»() = («VARIABLE_NAME»() + 1);
				break;
			case 2:
				«VARIABLE_NAME»() = («VARIABLE_NAME»() + 1);
				break;
			case 255:
				«VARIABLE_NAME»() = 0;
				break;
		}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementWithList() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		CASE variable OF
			0, 1, 2: «VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			255: «VARIABLE_NAME» := 0;
		END_CASE;'''))
		
		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)
				
		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («VARIABLE_NAME»()) {
			case 0: case 1: case 2:
				«VARIABLE_NAME»() = («VARIABLE_NAME»() + 1);
				break;
			case 255:
				«VARIABLE_NAME»() = 0;
				break;
		}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementWithElse() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		CASE variable OF
			0: «VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			255: «VARIABLE_NAME» := 0;
		ELSE
			«VARIABLE_NAME» := 255;
		END_CASE;'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («VARIABLE_NAME»()) {
			case 0:
				«VARIABLE_NAME»() = («VARIABLE_NAME»() + 1);
				break;
			case 255:
				«VARIABLE_NAME»() = 0;
				break;
			default:
				«VARIABLE_NAME»() = 255;
				break;
		}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementWithIfInside() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		CASE variable OF
			0: 
				IF «VARIABLE_NAME» < 20 THEN
			 		«VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			 	ELSE
			 		«VARIABLE_NAME» := «VARIABLE_NAME» - 1;
			 	END_IF;
			255: «VARIABLE_NAME» := 0;
		ELSE
			«VARIABLE_NAME» := 255;
		END_CASE;'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («VARIABLE_NAME»()) {
			case 0:
				if((«VARIABLE_NAME»() < 20)) {
					«VARIABLE_NAME»() = («VARIABLE_NAME»() + 1);
				}
				else {
					«VARIABLE_NAME»() = («VARIABLE_NAME»() - 1);
				}
				break;
			case 255:
				«VARIABLE_NAME»() = 0;
				break;
			default:
				«VARIABLE_NAME»() = 255;
				break;
		}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementInsideIf() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE2_NAME, BOOL))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		IF «VARIABLE2_NAME» THEN
		CASE variable OF
			255: «VARIABLE_NAME» := 0;
		ELSE
			«VARIABLE_NAME» := 255;
		END_CASE;
		ELSE
			«VARIABLE_NAME» := 0;
		END_IF;'''))
		
		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		if(«VARIABLE2_NAME»()) {
			switch («VARIABLE_NAME»()) {
				case 255:
					«VARIABLE_NAME»() = 0;
					break;
				default:
					«VARIABLE_NAME»() = 255;
					break;
			}
		}
		else {
			«VARIABLE_NAME»() = 0;
		}
		'''.toString(), generatedCode.toString())
	}
}