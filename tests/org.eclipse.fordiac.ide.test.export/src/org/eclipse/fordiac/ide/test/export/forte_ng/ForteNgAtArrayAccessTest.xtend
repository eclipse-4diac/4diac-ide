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

import java.util.stream.Stream
import org.eclipse.fordiac.ide.test.export.ExporterTestBasicFBTypeBase
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import static org.eclipse.fordiac.ide.model.FordiacKeywords.*
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertNull

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

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

	def static Stream<Arguments> testCases()  {
		return Stream.of(
				Arguments.of( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				Arguments.of( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //

				Arguments.of( DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				Arguments.of( DWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //


				Arguments.of( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				Arguments.of( DWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //

				Arguments.of( DWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(DWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				Arguments.of( DWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //


				Arguments.of( DWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(DWORD, BYTE), 0, VALUE_42, VALID_ACCESS ), //

				Arguments.of( DWORD, WORD, WORDACCESS, INDEX_START, indexStop(DWORD, WORD), 0, VALUE_42, VALID_ACCESS ), //

				Arguments.of( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				Arguments.of( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //

				Arguments.of( LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_TRUE, VALID_ACCESS ), //
				Arguments.of( LWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_TRUE, VALID_ACCESS ), //

				Arguments.of( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				Arguments.of( LWORD, BOOL, BOOLACCESS_SHORT, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //

				Arguments.of( LWORD, BOOL, BOOLACCESS, INDEX_START, indexStop(LWORD, BOOL), 0, VALUE_FALSE, VALID_ACCESS ), //
				Arguments.of( LWORD, BOOL, BOOLACCESS, INDEX_START, 1, 0, VALUE_FALSE, VALID_ACCESS ), //

				Arguments.of( LWORD, BYTE, BYTEACCESS, INDEX_START, indexStop(LWORD, BYTE), 0, VALUE_42, VALID_ACCESS ), //

				Arguments.of( LWORD, WORD, WORDACCESS, INDEX_START, indexStop(LWORD, WORD), 0, VALUE_42, VALID_ACCESS ), //

				Arguments.of( LWORD, DWORD, DWORDACCESS, INDEX_START, indexStop(LWORD, DWORD), 0, VALUE_42, VALID_ACCESS ) //
			)
		}

	@ParameterizedTest(name = "{index}: {0}->{1}[{5}]={6} valid->{7}")
	@MethodSource("testCases")
	def locatedArrayAtAccess(String sourceType, String accessType, String accessor, int arrayStart, int arrayStop, int index, String value, boolean isValid) {
		getFunctionBlock.getCallables().add(createSTAlgorithm(ALGORITHM_NAME, '''
		VAR
		  «VARIABLE_NAME» : «sourceType»;
		  «VARIABLE2_NAME» AT «VARIABLE_NAME» : ARRAY [«arrayStart»..«arrayStop»] OF «accessType»;
		END_VAR

		«VARIABLE2_NAME»[«index»] := «value»;'''))

		var generatedCode = generateAlgorithm(functionBlock, ALGORITHM_NAME, errors)

		if (isValid == VALID_ACCESS) {
			assertNoErrors(errors)
			assertNotNull(generatedCode)
			assertEquals('''
			CIEC_«sourceType» «EXPORTED_VARIABLE_NAME»;
			ARRAY_AT<CIEC_«accessType», CIEC_«sourceType», «arrayStart», «arrayStop»> «EXPORTED_VARIABLE2_NAME»(«EXPORTED_VARIABLE_NAME»);
			«EXPORTED_VARIABLE2_NAME»[«index»]
			 = «value»;
			'''.toString(), generatedCode.toString())
		} else {
			assertErrors(errors)
			assertNull(generatedCode)

			assertErrorMessages(errors, "Incorrect partial access: index not within limits.") //$NON-NLS-1$
		}
	}

}
