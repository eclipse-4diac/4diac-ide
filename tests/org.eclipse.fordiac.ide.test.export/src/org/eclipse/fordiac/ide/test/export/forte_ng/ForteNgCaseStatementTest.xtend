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

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals

import static org.eclipse.fordiac.ide.model.FordiacKeywords.*

import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests
class ForteNgCaseStatementTest extends ExporterTestBasicFBTypeBase {

	@Test
	def void validCaseStatement() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
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
			
			if (auto st_lv_synthetic_0 = «EXPORTED_VARIABLE_NAME»(); false) {
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(0))) {
			  «EXPORTED_VARIABLE_NAME»() = func_ADD<CIEC_DINT>(«EXPORTED_VARIABLE_NAME»(), CIEC_DINT(1));
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(1))) {
			  «EXPORTED_VARIABLE_NAME»() = func_ADD<CIEC_DINT>(«EXPORTED_VARIABLE_NAME»(), CIEC_DINT(1));
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(2))) {
			  «EXPORTED_VARIABLE_NAME»() = func_ADD<CIEC_DINT>(«EXPORTED_VARIABLE_NAME»(), CIEC_DINT(1));
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(255))) {
			  «EXPORTED_VARIABLE_NAME»() = CIEC_DINT(0);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementWithList() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		CASE variable OF
			0, 1, 2: «VARIABLE_NAME» := «VARIABLE_NAME» + 1;
			255: «VARIABLE_NAME» := 0;
		END_CASE;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);
		assertEquals('''
			
			if (auto st_lv_synthetic_0 = «EXPORTED_VARIABLE_NAME»(); false) {
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(0)) || func_EQ(st_lv_synthetic_0, CIEC_DINT(1)) || func_EQ(st_lv_synthetic_0, CIEC_DINT(2))) {
			  «EXPORTED_VARIABLE_NAME»() = func_ADD<CIEC_DINT>(st_variable(), CIEC_DINT(1));
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(255))) {
			  «EXPORTED_VARIABLE_NAME»() = CIEC_DINT(0);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementWithElse() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
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
			
			if (auto st_lv_synthetic_0 = «EXPORTED_VARIABLE_NAME»(); false) {
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(0))) {
			  «EXPORTED_VARIABLE_NAME»() = func_ADD<CIEC_DINT>(st_variable(), CIEC_DINT(1));
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(255))) {
			  «EXPORTED_VARIABLE_NAME»() = CIEC_DINT(0);
			} else {
			  «EXPORTED_VARIABLE_NAME»() = CIEC_DINT(255);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validCaseStatementWithIfInside() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
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
			
			if (auto st_lv_synthetic_0 = «EXPORTED_VARIABLE_NAME»(); false) {
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(0))) {
			  if (func_LT(«EXPORTED_VARIABLE_NAME»(), CIEC_DINT(20))) {
			    «EXPORTED_VARIABLE_NAME»() = func_ADD<CIEC_DINT>(«EXPORTED_VARIABLE_NAME»(), CIEC_DINT(1));
			  }
			  else {
			    «EXPORTED_VARIABLE_NAME»() = func_SUB<CIEC_DINT>(«EXPORTED_VARIABLE_NAME»(), CIEC_DINT(1));
			  }
			} else if (func_EQ(st_lv_synthetic_0, CIEC_DINT(255))) {
			  «EXPORTED_VARIABLE_NAME»() = CIEC_DINT(0);
			} else {
			  «EXPORTED_VARIABLE_NAME»() = CIEC_DINT(255);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void invalidCaseStatementNoEnd() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
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
		assertNull(generatedCode);
	}

}
