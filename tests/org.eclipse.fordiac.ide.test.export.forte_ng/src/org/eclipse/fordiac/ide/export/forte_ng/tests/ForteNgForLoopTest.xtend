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
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.eclipse.fordiac.ide.model.FordiacKeywords.*

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgForLoopTest extends ForteNgTestBasicFBTypeBase {

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
		// as it is done in lua: https://www.lua.org/manual/5.1/manual.html#2.4.5
		auto by = 1;
		auto to = 5;
		for(«EXPORTED_VARIABLE_NAME»() = 1;
		    (by >  0 && «EXPORTED_VARIABLE_NAME»() <= to) ||
		    (by <= 0 && «EXPORTED_VARIABLE_NAME»() >= to);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by){
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
		// as it is done in lua: https://www.lua.org/manual/5.1/manual.html#2.4.5
		auto by = 2;
		auto to = 5;
		for(«EXPORTED_VARIABLE_NAME»() = 1;
		    (by >  0 && «EXPORTED_VARIABLE_NAME»() <= to) ||
		    (by <= 0 && «EXPORTED_VARIABLE_NAME»() >= to);
		    «EXPORTED_VARIABLE_NAME»() = «EXPORTED_VARIABLE_NAME»() + by){
			«EXPORTED_VARIABLE2_NAME»() = 0;
		}
		'''.toString(), generatedCode.toString())
	}

}