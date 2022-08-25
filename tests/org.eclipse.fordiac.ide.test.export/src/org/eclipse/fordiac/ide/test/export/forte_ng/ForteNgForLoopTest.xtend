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
class ForteNgForLoopTest extends ExporterTestBasicFBTypeBase {

	@Test
	def void validForLoop() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
			«VARIABLE_NAME»: INT;
			«VARIABLE2_NAME»: INT;
		END_VAR
		FOR «VARIABLE_NAME»:=1 TO 5 DO
			«VARIABLE2_NAME» := 0;
		END_FOR;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);

		assertEquals('''
			CIEC_INT st_lv_variable = CIEC_INT(0);
			CIEC_INT st_lv_variable2 = CIEC_INT(0);
			
			for (auto st_lv_synthetic_0 : ST_FOR_ITER<CIEC_INT>(st_lv_variable, CIEC_INT(1), CIEC_INT(5))) {
			  st_lv_variable2 = CIEC_INT(0);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validForLoopWithBy() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
			«VARIABLE_NAME»: INT;
			«VARIABLE2_NAME»: INT;
		END_VAR
		FOR «VARIABLE_NAME»:=1 TO 5 BY 2 DO
			«VARIABLE2_NAME» := 0;
		END_FOR;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);

		assertEquals('''
			CIEC_INT st_lv_variable = CIEC_INT(0);
			CIEC_INT st_lv_variable2 = CIEC_INT(0);
			
			for (auto st_lv_synthetic_0 : ST_FOR_ITER<CIEC_INT, CIEC_INT>(st_lv_variable, CIEC_INT(1), CIEC_INT(5), CIEC_INT(2))) {
			  st_lv_variable2 = CIEC_INT(0);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void multipleForLoopsOnSameLevel() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
			«VARIABLE_NAME»: INT;
			«VARIABLE2_NAME»: INT;
		END_VAR
		FOR «VARIABLE_NAME»:=1 TO 5 DO
			«VARIABLE2_NAME» := 0;
		END_FOR;
		FOR «VARIABLE_NAME»:=6 TO 10 DO
			«VARIABLE2_NAME» := 1;
		END_FOR;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);

		assertEquals('''
			CIEC_INT st_lv_variable = CIEC_INT(0);
			CIEC_INT st_lv_variable2 = CIEC_INT(0);
			
			for (auto st_lv_synthetic_0 : ST_FOR_ITER<CIEC_INT>(st_lv_variable, CIEC_INT(1), CIEC_INT(5))) {
			  st_lv_variable2 = CIEC_INT(0);
			}
			for (auto st_lv_synthetic_1 : ST_FOR_ITER<CIEC_INT>(st_lv_variable, CIEC_INT(6), CIEC_INT(10))) {
			  st_lv_variable2 = CIEC_INT(1);
			}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void multipleForLoopsContained() {
		functionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR_TEMP
			«VARIABLE_NAME»: INT;
			«VARIABLE2_NAME»: INT;
		END_VAR
		FOR «VARIABLE_NAME»:=1 TO 5 DO
			«VARIABLE2_NAME» := 0;
			FOR «VARIABLE_NAME»:=6 TO 10 DO
				«VARIABLE2_NAME» := 1;
			END_FOR;
		END_FOR;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);

		assertEquals('''
			CIEC_INT st_lv_variable = CIEC_INT(0);
			CIEC_INT st_lv_variable2 = CIEC_INT(0);
			
			for (auto st_lv_synthetic_0 : ST_FOR_ITER<CIEC_INT>(st_lv_variable, CIEC_INT(1), CIEC_INT(5))) {
			  st_lv_variable2 = CIEC_INT(0);
			  for (auto st_lv_synthetic_1 : ST_FOR_ITER<CIEC_INT>(st_lv_variable, CIEC_INT(6), CIEC_INT(10))) {
			    st_lv_variable2 = CIEC_INT(1);
			  }
			}
		'''.toString(), generatedCode.toString())
	}

}
