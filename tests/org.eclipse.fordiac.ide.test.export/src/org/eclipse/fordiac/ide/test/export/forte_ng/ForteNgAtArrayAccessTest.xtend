/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
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
class ForteNgAtArrayAccessTest extends ExporterTestBasicFBTypeBase {

	protected static final boolean VALID_ACCESS = true
	protected static final boolean INVALID_ACCESS = !VALID_ACCESS

	static final String BOOLACCESS_SHORT = "" //$NON-NLS-1$
	static final String BOOLACCESS = "%X" //$NON-NLS-1$
	static final String BYTEACCESS = "%B" //$NON-NLS-1$
	static final String WORDACCESS = "%W" //$NON-NLS-1$
	static final String DWORDACCESS = "%D" //$NON-NLS-1$

	static final String VALUE_TRUE = "true" //$NON-NLS-1$
	static final String VALUE_FALSE = "false" //$NON-NLS-1$

	static final String VALUE_42 = "42" //$NON-NLS-1$

	@Parameter(0)
	public String sourceType
	@Parameter(1)
	public String accessType
	@Parameter(2)
	public String accessor
	@Parameter(3)
	public int arrayStart
	@Parameter(4)
	public int arrayStop
	@Parameter(5)
	public int index
	@Parameter(6)
	public String value
	@Parameter(7)
	public boolean isValid

	@Parameterized.Parameters(name = "{index}: {0}.{2}{5}={6}")
	def static Collection<Object[]> testCases()  {
		return #[
				testCase( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1,
						VALUE_TRUE, INVALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1, VALUE_TRUE,
						INVALID_ACCESS ), //

				testCase( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1,
						VALUE_FALSE, INVALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), indexStop(DWORD, BOOL) + 1, VALUE_FALSE,
						INVALID_ACCESS ), //

				testCase( DWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(DWORD, BYTE), 0, VALUE_42, VALID_ACCESS ), //
				testCase( DWORD, BYTE, BYTEACCESS, INDEX_START, 1, 2, VALUE_42, INVALID_ACCESS ), //
				testCase( DWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(DWORD, BYTE), indexStop(DWORD, BYTE) + 1, VALUE_42,
						INVALID_ACCESS ), //

				testCase( DWORD, WORD, WORDACCESS, INDEX_START, indexStop(DWORD, WORD), 0, VALUE_42, VALID_ACCESS ), //
				testCase( DWORD, WORD, WORDACCESS, INDEX_START, indexStop(DWORD, WORD), 2, VALUE_42, INVALID_ACCESS ), //

				testCase( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1,
						VALUE_TRUE, INVALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1, VALUE_TRUE,
						INVALID_ACCESS ), //

				testCase( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1,
						VALUE_FALSE, INVALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //
				testCase( LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), indexStop(LWORD, BOOL) + 1, VALUE_FALSE,
						INVALID_ACCESS ), //

				testCase( LWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(LWORD, BYTE), 0, VALUE_42, VALID_ACCESS ), //
				testCase( LWORD, BYTE, BYTEACCESS, INDEX_START, 1, 2, VALUE_42, INVALID_ACCESS ), //
				testCase( LWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(LWORD, BYTE), indexStop(LWORD, BYTE) + 1, VALUE_42,
						INVALID_ACCESS ), //

				testCase( LWORD, WORD, WORDACCESS, INDEX_START, indexStop(LWORD, WORD), 0, VALUE_42, VALID_ACCESS ), //
				testCase( LWORD, WORD, WORDACCESS, INDEX_START, indexStop(LWORD, WORD), indexStop(LWORD, WORD) + 1, VALUE_42,
						INVALID_ACCESS ), //

				testCase( LWORD, DWORD, DWORDACCESS, INDEX_START, indexStop(LWORD, DWORD), 0, VALUE_42, VALID_ACCESS ), //
				testCase( LWORD, DWORD, DWORDACCESS, INDEX_START, indexStop(LWORD, DWORD), indexStop(LWORD, DWORD) + 1,
						VALUE_42, INVALID_ACCESS ) //
			]
		}

	@Test
	def locatedArrayAtAccess() {
		getFunctionBlock.getAlgorithm().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : «sourceType»;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : ARRAY [«arrayStart»..«arrayStop»] OF «accessType»;
		END_VAR

		«VARIABLE2_NAME».«accessor»«index» := «value»;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		if (isValid == VALID_ACCESS) {
			assertNoErrors(errors)
			assertNotNull(generatedCode)
			assertEquals('''
			CIEC_«sourceType» «EXPORTED_VARIABLE_NAME»;
			ARRAY_AT<CIEC_«accessType», CIEC_«sourceType», «arrayStart», «arrayStop»> «EXPORTED_VARIABLE2_NAME»(«EXPORTED_VARIABLE_NAME»);
			«EXPORTED_VARIABLE2_NAME».partial<CIEC_«accessType»,«index»>() = «value»;
			'''.toString(), generatedCode.toString())
		} else {
			assertErrors(errors)
			assertNull(generatedCode)

			assertErrorMessages(errors, "Incorrect partial access: index not within limits.") //$NON-NLS-1$
		}
	}

}
