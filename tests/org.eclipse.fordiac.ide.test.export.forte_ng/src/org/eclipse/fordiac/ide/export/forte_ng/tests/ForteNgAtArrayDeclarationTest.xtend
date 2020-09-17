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

import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.eclipse.fordiac.ide.model.FordiacKeywords.*
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ForteNgAtArrayDeclarationTest extends ForteNgTestBasicFBTypeBase {

	val static VALID_DECLARATION = true
	val static INVALID_DECLARATION = !VALID_DECLARATION

	def static Stream<Arguments> testCases()  {
		return Stream.of(
				Arguments.of( LWORD, DWORD, INDEX_START, indexStop(LWORD, DWORD), VALID_DECLARATION ), //
				Arguments.of( LWORD, WORD, INDEX_START, indexStop(LWORD, WORD), VALID_DECLARATION ), //
				Arguments.of( LWORD, BYTE, INDEX_START, indexStop(LWORD, BYTE), VALID_DECLARATION ), //
				Arguments.of( LWORD, BOOL, INDEX_START, indexStop(LWORD, BOOL), VALID_DECLARATION ), //
				Arguments.of( LWORD, DWORD, INDEX_START, indexStop(LWORD, DWORD) + 1, INVALID_DECLARATION ), //
				Arguments.of( LWORD, WORD, INDEX_START, indexStop(LWORD, WORD) + 1, INVALID_DECLARATION ), //
				Arguments.of( LWORD, BYTE, INDEX_START, indexStop(LWORD, BYTE) + 1, INVALID_DECLARATION ), //
				Arguments.of( LWORD, BOOL, INDEX_START, indexStop(LWORD, BOOL) + 1, INVALID_DECLARATION ), //
				Arguments.of( DWORD, WORD, INDEX_START, indexStop(DWORD, WORD), VALID_DECLARATION ), //
				Arguments.of( DWORD, BYTE, INDEX_START, indexStop(DWORD, BYTE), VALID_DECLARATION ), //
				Arguments.of( DWORD, BOOL, INDEX_START, indexStop(DWORD, BOOL), VALID_DECLARATION ), //
				Arguments.of( DWORD, WORD, INDEX_START, indexStop(DWORD, WORD) + 1, INVALID_DECLARATION ), //
				Arguments.of( DWORD, BYTE, INDEX_START, indexStop(DWORD, BYTE) + 1, INVALID_DECLARATION ), //
				Arguments.of( DWORD, BOOL, INDEX_START, indexStop(DWORD, BOOL) + 1, INVALID_DECLARATION ), //
				Arguments.of( WORD, BYTE, INDEX_START, indexStop(WORD, BYTE), VALID_DECLARATION ), //
				Arguments.of( WORD, BOOL, INDEX_START, indexStop(WORD, BOOL), VALID_DECLARATION ), //
				Arguments.of( WORD, BYTE, INDEX_START, indexStop(WORD, BYTE) + 1, INVALID_DECLARATION ), //
				Arguments.of( WORD, BOOL, INDEX_START, indexStop(WORD, BOOL) + 1, INVALID_DECLARATION ), //
				Arguments.of( BYTE, BOOL, INDEX_START, indexStop(BYTE, BOOL), VALID_DECLARATION ), //
				Arguments.of( BYTE, BOOL, INDEX_START, indexStop(BYTE, BOOL) + 1, INVALID_DECLARATION ), //
				Arguments.of( LINT, BOOL, INDEX_START, 8, INVALID_DECLARATION ), //
				Arguments.of( DINT, BOOL, INDEX_START, 8, INVALID_DECLARATION ), //
				Arguments.of( INT, BOOL, INDEX_START, 8, INVALID_DECLARATION ), //
				Arguments.of( SINT, BOOL, INDEX_START, 8, INVALID_DECLARATION ), //
				Arguments.of( REAL, BOOL, INDEX_START, 8, INVALID_DECLARATION )
			)
		}

	@ParameterizedTest(name = "{index}: {0}->{1}[{2}..{3}]")
	@MethodSource("testCases")
	def LocatedArrayDeclaration(String sourceType, String accessType, int arrayStart, int arrayStop, boolean isValid) {
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
