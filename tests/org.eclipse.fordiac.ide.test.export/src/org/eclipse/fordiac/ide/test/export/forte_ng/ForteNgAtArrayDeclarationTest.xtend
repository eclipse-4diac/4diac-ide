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

import java.util.Collection
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter

import static org.eclipse.fordiac.ide.model.FordiacKeywords.*
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertNull
import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

@RunWith(Parameterized)
class ForteNgAtArrayDeclarationTest extends ExporterTestBasicFBTypeBase {

	val static VALID_DECLARATION = true
	val static INVALID_DECLARATION = !VALID_DECLARATION

	@Parameter(0)
	public String sourceType
	@Parameter(1)
	public String accessType
	@Parameter(2)
	public int arrayStart
	@Parameter(3)
	public int arrayStop
	@Parameter(4)
	public boolean isValid

	@Parameterized.Parameters(name = "{index}: {0}->{1}[{2}..{3}]")
	def static Collection<Object[]> testCases()  {
		return #[
				testCase( LWORD, DWORD, INDEX_START, indexStop(LWORD, DWORD), VALID_DECLARATION ), //
				testCase( LWORD, WORD, INDEX_START, indexStop(LWORD, WORD), VALID_DECLARATION ), //
				testCase( LWORD, BYTE, INDEX_START, indexStop(LWORD, BYTE), VALID_DECLARATION ), //
				testCase( LWORD, BOOL, INDEX_START, indexStop(LWORD, BOOL), VALID_DECLARATION ), //
				testCase( LWORD, DWORD, INDEX_START, indexStop(LWORD, DWORD) + 1, INVALID_DECLARATION ), //
				testCase( LWORD, WORD, INDEX_START, indexStop(LWORD, WORD) + 1, INVALID_DECLARATION ), //
				testCase( LWORD, BYTE, INDEX_START, indexStop(LWORD, BYTE) + 1, INVALID_DECLARATION ), //
				testCase( LWORD, BOOL, INDEX_START, indexStop(LWORD, BOOL) + 1, INVALID_DECLARATION ), //
				testCase( DWORD, WORD, INDEX_START, indexStop(DWORD, WORD), VALID_DECLARATION ), //
				testCase( DWORD, BYTE, INDEX_START, indexStop(DWORD, BYTE), VALID_DECLARATION ), //
				testCase( DWORD, BOOL, INDEX_START, indexStop(DWORD, BOOL), VALID_DECLARATION ), //
				testCase( DWORD, WORD, INDEX_START, indexStop(DWORD, WORD) + 1, INVALID_DECLARATION ), //
				testCase( DWORD, BYTE, INDEX_START, indexStop(DWORD, BYTE) + 1, INVALID_DECLARATION ), //
				testCase( DWORD, BOOL, INDEX_START, indexStop(DWORD, BOOL) + 1, INVALID_DECLARATION ), //
				testCase( WORD, BYTE, INDEX_START, indexStop(WORD, BYTE), VALID_DECLARATION ), //
				testCase( WORD, BOOL, INDEX_START, indexStop(WORD, BOOL), VALID_DECLARATION ), //
				testCase( WORD, BYTE, INDEX_START, indexStop(WORD, BYTE) + 1, INVALID_DECLARATION ), //
				testCase( WORD, BOOL, INDEX_START, indexStop(WORD, BOOL) + 1, INVALID_DECLARATION ), //
				testCase( BYTE, BOOL, INDEX_START, indexStop(BYTE, BOOL), VALID_DECLARATION ), //
				testCase( BYTE, BOOL, INDEX_START, indexStop(BYTE, BOOL) + 1, INVALID_DECLARATION ), //
				testCase( "LINT", BOOL, INDEX_START, 8, INVALID_DECLARATION ), //$NON-NLS-1$
				testCase( DINT, BOOL, INDEX_START, 8, INVALID_DECLARATION ), //
				testCase( "INT", BOOL, INDEX_START, 8, INVALID_DECLARATION ), //$NON-NLS-1$
				testCase( "SINT", BOOL, INDEX_START, 8, INVALID_DECLARATION ), //$NON-NLS-1$
				testCase( REAL, BOOL, INDEX_START, 8, INVALID_DECLARATION )
			]
		}

	@Test
	def LocatedArrayDeclaration() {
		functionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : «sourceType»;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : ARRAY [«arrayStart»..«arrayStop»] OF «accessType»;
		END_VAR'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		if (isValid == VALID_DECLARATION) {
			assertNoErrors(errors)
			assertNotNull(generatedCode)
			assertEquals('''
			CIEC_«sourceType» «EXPORTED_VARIABLE_NAME»;
			ARRAY_AT<CIEC_«accessType», CIEC_«sourceType», «arrayStart», «arrayStop»> «EXPORTED_VARIABLE2_NAME»(«EXPORTED_VARIABLE_NAME»);
			'''.toString(), generatedCode.toString())
		} else {
			assertErrors(errors)
			assertNull(generatedCode)
		}
	}

}
