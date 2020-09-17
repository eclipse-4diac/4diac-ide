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
import static org.eclipse.fordiac.ide.model.FordiacKeywords.*

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgCaseStatementTest extends ForteNgTestBasicFBTypeBase {

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

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («EXPORTED_VARIABLE_NAME»()) {
			case 0:
				«EXPORTED_VARIABLE_NAME»() = ADD(«EXPORTED_VARIABLE_NAME»(), 1);
				break;
			case 1:
				«EXPORTED_VARIABLE_NAME»() = ADD(«EXPORTED_VARIABLE_NAME»(), 1);
				break;
			case 2:
				«EXPORTED_VARIABLE_NAME»() = ADD(«EXPORTED_VARIABLE_NAME»(), 1);
				break;
			case 255:
				«EXPORTED_VARIABLE_NAME»() = 0;
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

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («EXPORTED_VARIABLE_NAME»()) {
			case 0: case 1: case 2:
				«EXPORTED_VARIABLE_NAME»() = ADD(«EXPORTED_VARIABLE_NAME»(), 1);
				break;
			case 255:
				«EXPORTED_VARIABLE_NAME»() = 0;
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

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («EXPORTED_VARIABLE_NAME»()) {
			case 0:
				«EXPORTED_VARIABLE_NAME»() = ADD(«EXPORTED_VARIABLE_NAME»(), 1);
				break;
			case 255:
				«EXPORTED_VARIABLE_NAME»() = 0;
				break;
			default:
				«EXPORTED_VARIABLE_NAME»() = 255;
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

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
		switch («EXPORTED_VARIABLE_NAME»()) {
			case 0:
				if((«EXPORTED_VARIABLE_NAME»() < 20)) {
					«EXPORTED_VARIABLE_NAME»() = ADD(«EXPORTED_VARIABLE_NAME»(), 1);
				}
				else {
					«EXPORTED_VARIABLE_NAME»() = SUB(«EXPORTED_VARIABLE_NAME»(), 1);
				}
				break;
			case 255:
				«EXPORTED_VARIABLE_NAME»() = 0;
				break;
			default:
				«EXPORTED_VARIABLE_NAME»() = 255;
				break;
		}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void invalidCaseStatementNoEnd() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		CASE «VARIABLE_NAME» OF
			0:
				IF «VARIABLE_NAME» < 20 THEN
			 		«VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			 	ELSE
			 		«VARIABLE_NAME» := «VARIABLE_NAME» - 1;
			 	END_IF;
			255: «VARIABLE_NAME» := 0;
		'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertErrors(errors);
		assertErrorMessages(errors, "expecting 'END_CASE'");
		assertNull(generatedCode);
	}

}