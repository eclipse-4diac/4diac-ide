/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings("squid:S5960")
class ArrayValueValidatorTest {

	private static final String NO_ERROR = ""; //$NON-NLS-1$

	static Stream<Arguments> validateValidCharArrayLiteralsTestCases() {
		return Stream.of(Arguments.of("['a']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[',']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['$'']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['a','b']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['a', 'b']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[ 'a', 'b']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['a', 'b' ]", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['a',',']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[',',',']", IecTypes.ElementaryTypes.CHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['Sepp']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[',']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['Sepp','Hubert']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['Sepp', 'Hubert']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[ 'Sepp', 'Hubert']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['Sepp', 'Hubert' ]", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("['Sepp',',']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[',',',']", IecTypes.ElementaryTypes.STRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"a\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\",\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"$\"\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"a\",\"b\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"a\", \"b\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[ \"a\", \"b\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"a\", \"b\" ]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"a\",\",\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\",\",\",\"]", IecTypes.ElementaryTypes.WCHAR, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"Sepp\",\"Hubert\"]", IecTypes.ElementaryTypes.WSTRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"Sepp\", \"Hubert\"]", IecTypes.ElementaryTypes.WSTRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[ \"Sepp\", \"Hubert\"]", IecTypes.ElementaryTypes.WSTRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"Sepp\", \"Hubert\" ]", IecTypes.ElementaryTypes.WSTRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\"Sepp\",\",\"]", IecTypes.ElementaryTypes.WSTRING, NO_ERROR), //$NON-NLS-1$
				Arguments.of("[\",\",\",\"]", IecTypes.ElementaryTypes.WSTRING, NO_ERROR) //$NON-NLS-1$
				);
	}

	@DisplayName("Validator tests for valid CHAR array literals")
	@ParameterizedTest(name = "{index}: Literal: {0}")
	@MethodSource("validateValidCharArrayLiteralsTestCases")
	@SuppressWarnings("static-method")
	void validateValidArrayLiterals(final String value, final DataType dataType, final String expectedResult) {
		final var varDeclaration = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDeclaration.setName("ArrayTest");
		varDeclaration.setType(dataType);
		varDeclaration.setArraySize(10);

		final String resultString = ValueValidator.validateValue(varDeclaration, value);
		assertEquals(expectedResult, resultString);
	}
}
