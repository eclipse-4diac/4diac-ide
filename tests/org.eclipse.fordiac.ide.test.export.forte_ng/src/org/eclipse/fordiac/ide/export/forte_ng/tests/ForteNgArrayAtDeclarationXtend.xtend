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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

abstract class ForteNgArrayAtDeclarationXtend extends ForteNgTestBase {

	static final String ALGORITHM_NAME = "algorithm" //$NON-NLS-1$
	protected static final boolean VALID_DECLARATION = true
	protected static final boolean INVALID_DECLARATION = !VALID_DECLARATION

	def protected void testLocatedArrayDeclaration(String sourceType, String accessType, int arrayStart, int arrayStop, boolean isValid) {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  variable : «sourceType»;
		  atLocation AT variable : ARRAY [«arrayStart»..«arrayStop»] OF «accessType»;
		END_VAR'''))

		var generatedCode = stAlgorithmFilter
				.generate(castAlgorithm(functionBlock.getAlgorithmNamed(ALGORITHM_NAME)), errors)

		if (isValid == VALID_DECLARATION) {
			assertNoErrors(errors);
			assertNotNull(generatedCode);
			assertEquals('''
			CIEC_«sourceType» variable;
			ARRAY_AT<CIEC_«accessType», CIEC_«sourceType», «arrayStart», «arrayStop»> atLocation(variable);
			'''.toString(), generatedCode.toString())
		} else {
			assertErrors(errors);
			assertNull(generatedCode);			
		}
	}

}
