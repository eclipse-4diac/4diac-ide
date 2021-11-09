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

import static org.eclipse.fordiac.ide.model.FordiacKeywords.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgForLoopTest extends ExporterTestBasicFBTypeBase {

	@Test
	def void validForLoop() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE2_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		FOR «VARIABLE_NAME»:=1 TO 5 DO
			«VARIABLE2_NAME» := 0;
		END_FOR;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);

		assertEquals('''
		const auto by_0 = 1;
		const auto to_0 = 5;
		for(«EXPORTED_VARIABLE_NAME»() = 1;
		    (by_0 >  0 && «EXPORTED_VARIABLE_NAME»() <= to_0) ||
		    (by_0 <= 0 && «EXPORTED_VARIABLE_NAME»() >= to_0);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by_0){
			«EXPORTED_VARIABLE2_NAME»() = 0;
		}
		'''.toString(), generatedCode.toString())
	}

	@Test
	def void validForLoopWithBy() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE2_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		FOR «VARIABLE_NAME»:=1 TO 5 BY 2 DO
			«VARIABLE2_NAME» := 0;
		END_FOR;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		assertNoErrors(errors);
		assertNotNull(generatedCode);

		assertEquals('''
		const auto by_0 = 2;
		const auto to_0 = 5;
		for(«EXPORTED_VARIABLE_NAME»() = 1;
		    (by_0 >  0 && «EXPORTED_VARIABLE_NAME»() <= to_0) ||
		    (by_0 <= 0 && «EXPORTED_VARIABLE_NAME»() >= to_0);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by_0){
			«EXPORTED_VARIABLE2_NAME»() = 0;
		}
		'''.toString(), generatedCode.toString())
	}
	
	@Test
	def void multipleForLoopsOnSameLevel() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE2_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
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
		const auto by_0 = 1;
		const auto to_0 = 5;
		for(«EXPORTED_VARIABLE_NAME»() = 1;
		    (by_0 >  0 && «EXPORTED_VARIABLE_NAME»() <= to_0) ||
		    (by_0 <= 0 && «EXPORTED_VARIABLE_NAME»() >= to_0);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by_0){
			«EXPORTED_VARIABLE2_NAME»() = 0;
		}
		const auto by_1 = 1;
		const auto to_1 = 10;
		for(«EXPORTED_VARIABLE_NAME»() = 6;
		    (by_1 >  0 && «EXPORTED_VARIABLE_NAME»() <= to_1) ||
		    (by_1 <= 0 && «EXPORTED_VARIABLE_NAME»() >= to_1);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by_1){
			«EXPORTED_VARIABLE2_NAME»() = 1;
		}
		'''.toString(), generatedCode.toString())
	}
	
	@Test
	def void multipleForLoopsContained() {
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE_NAME, DINT))
		functionBlock.getInternalVars().add(createVarDeclaration(VARIABLE2_NAME, DINT))
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
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
		const auto by_0 = 1;
		const auto to_0 = 5;
		for(«EXPORTED_VARIABLE_NAME»() = 1;
		    (by_0 >  0 && «EXPORTED_VARIABLE_NAME»() <= to_0) ||
		    (by_0 <= 0 && «EXPORTED_VARIABLE_NAME»() >= to_0);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by_0){
			«EXPORTED_VARIABLE2_NAME»() = 0;
			const auto by_0_0 = 1;
			const auto to_0_0 = 10;
			for(«EXPORTED_VARIABLE_NAME»() = 6;
			    (by_0_0 >  0 && «EXPORTED_VARIABLE_NAME»() <= to_0_0) ||
			    (by_0_0 <= 0 && «EXPORTED_VARIABLE_NAME»() >= to_0_0);
			    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by_0_0){
				«EXPORTED_VARIABLE2_NAME»() = 1;
			}
		}
		'''.toString(), generatedCode.toString())
	}
	

}