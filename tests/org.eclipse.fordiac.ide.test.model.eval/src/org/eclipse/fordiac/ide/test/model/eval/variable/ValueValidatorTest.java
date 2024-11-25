/*******************************************************************************
 * Copyright (c) 2021 - 2022 Primetals Technologies Austria GmbH
 *               2022 - 2023 Martin Erich Jobst
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
 *   Martin Jobst
 *     - migrated to typed value converter
 *     - migrated to ST parser
 *******************************************************************************/

package org.eclipse.fordiac.ide.test.model.eval.variable;

import static org.eclipse.fordiac.ide.model.helpers.ArraySizeHelper.setArraySize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.globalconstantseditor.GlobalConstantsStandaloneSetup;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluatorFactory;
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.structuredtextalgorithm.STAlgorithmStandaloneSetup;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.STFunctionStandaloneSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({ "static-method", "squid:S5960" })
class ValueValidatorTest {

	private static final String PI = "3.1415"; //$NON-NLS-1$
	private static final String AFFE = "16#AFFE"; //$NON-NLS-1$
	private static final String NO_ERROR = ""; //$NON-NLS-1$

	@BeforeAll
	@SuppressWarnings("unused")
	static void setupXtext() {
		new DataTypeLibrary();
		GlobalConstantsStandaloneSetup.doSetup();
		STFunctionStandaloneSetup.doSetup();
		STAlgorithmStandaloneSetup.doSetup();
		StructuredTextEvaluatorFactory.register();
	}

	static Stream<Arguments> validateValidBoolLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.BOOL, "0", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "1", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "FALSE", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "TRUE", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "BOOL#0", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "BOOL#1", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "BOOL#FALSE", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "BOOL#TRUE", NO_ERROR)); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for valid BOOL literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateValidBoolLiteralsTestCases")
	void validateValidBoolLiterals(final DataType type, final String value, final String expectedResult) {
		final String resultString = VariableOperations.validateValue(type, value);
		assertEquals(expectedResult, resultString);
	}

	static Stream<Arguments> validateInvalidBoolLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"0\"", //$NON-NLS-1$
				"Cannot convert from WCHAR to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"1\"", //$NON-NLS-1$
						"Cannot convert from WCHAR to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"FALSE\"", //$NON-NLS-1$
						"Cannot convert from WSTRING to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"TRUE\"", //$NON-NLS-1$
						"Cannot convert from WSTRING to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#0\"", //$NON-NLS-1$
						"Cannot convert from WSTRING to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#1\"", //$NON-NLS-1$
						"Cannot convert from WSTRING to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#FALSE\"", //$NON-NLS-1$
						"Cannot convert from WSTRING to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#TRUE\"", //$NON-NLS-1$
						"Cannot convert from WSTRING to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "2", //$NON-NLS-1$
						"Cannot convert from BYTE to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, PI, "Cannot convert from LREAL to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "1970-01-30", //$NON-NLS-1$
						"Cannot convert from INT to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, AFFE, "Cannot convert from WORD to BOOL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "12:00:00", //$NON-NLS-1$
						"missing EOF at ':', Cannot convert from BYTE to BOOL")); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for invalid BOOL literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateInvalidBoolLiteralsTestCases")
	void validateInvalidBoolLiterals(final DataType type, final String value, final String expectedString) {
		final String resultString = VariableOperations.validateValue(type, value);
		assertEquals(expectedString, resultString);
	}

	static Stream<Arguments> validateValidNumberLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.SINT, "0", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.SINT, "1", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.SINT, "17", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.INT, "1701", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.REAL, "3.14", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.REAL, "3.14e5", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.REAL, "3.14e-5", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.LREAL, "3.14", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.LREAL, "3.14e5", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.LREAL, "3.14e-5", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.WORD, AFFE, NO_ERROR),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, "16#FFFFFFFFFFFFFFFF", NO_ERROR)); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for valid numeric literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateValidNumberLiteralsTestCases")
	void validateValidNumberLiterals(final DataType type, final String value, final String expectedResult) {
		final String resultString = VariableOperations.validateValue(type, value);
		assertEquals(expectedResult, resultString);
	}

	static Stream<Arguments> validateInvalidNumberLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.SINT, "\"0\"", //$NON-NLS-1$
				"Cannot convert from WCHAR to SINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.SINT, "\"1\"", //$NON-NLS-1$
						"Cannot convert from WCHAR to SINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.SINT, "FALSE", //$NON-NLS-1$
						"Cannot convert from BOOL to SINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.SINT, "TRUE", //$NON-NLS-1$
						"Cannot convert from BOOL to SINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.SINT, "129", //$NON-NLS-1$
						"Cannot convert from USINT to SINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.INT, "123456", //$NON-NLS-1$
						"Cannot convert from DINT to INT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.INT, "3.14", //$NON-NLS-1$
						"Cannot convert from LREAL to INT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.UINT, "-1", //$NON-NLS-1$
						"Cannot convert from SINT to UINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.UINT, "-17", //$NON-NLS-1$
						"Cannot convert from SINT to UINT"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BYTE, AFFE, "Cannot convert from WORD to BYTE"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.REAL, "3.14e120", //$NON-NLS-1$
						"Cannot convert from LREAL to REAL"), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.LREAL, "3.14e1200", //$NON-NLS-1$
						"Invalid LREAL literal: 3.14E+1200")); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for invalid numeric literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateInvalidNumberLiteralsTestCases")
	void validateInvalidNumberLiterals(final DataType type, final String value, final String expectedString) {
		final String resultString = VariableOperations.validateValue(type, value);
		assertEquals(expectedString, resultString);
	}

	static Stream<Arguments> validateValidDateLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#2000-01-01", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#2000-12-31", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1815-12-10", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1852-11-27", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1906-12-09", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1992-01-01", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1878-11-07", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1968-10-27", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "2022-05-10", //$NON-NLS-1$
						"Cannot convert from INT to DATE")); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for valid DATE literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateValidDateLiteralsTestCases")
	void validateValidDateLiterals(final DataType type, final String value, final String expectedString) {
		final String resultString = VariableOperations.validateValue(type, value);
		assertEquals(expectedString, resultString);
	}

	static Stream<Arguments> validateTypeSpecifierMandatoryForAnyLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.GenericTypes.ANY, "0", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_BIT, "-0", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_CHAR, "'a'", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_CHARS, "'a'", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_ELEMENTARY, PI,
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_INT, "17", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_MAGNITUDE, PI, Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_NUM, PI, Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_REAL, PI, Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_SIGNED, "4", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_STRING, "'abc'", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier));
	}

	@DisplayName("Validator tests for mandatory type specifier for ANYs")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateTypeSpecifierMandatoryForAnyLiteralsTestCases")
	void validateTypeSpecifierMandatoryForAnyLiterals(final DataType type, final String value,
			final String expectedFormatString) {
		final String expectedString = MessageFormat.format(expectedFormatString, type.getName());
		final String resultString = VariableOperations.validateValue(type, value);
		assertEquals(expectedString, resultString);
	}

	@DisplayName("Validator tests for removed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = { "%sgsgs%", "%Hello%", "%moduleValue%", "%sign%" })
	void validateSingularValidDNSEntries(final String value) {
		final String resultString = VariableOperations.validateValue(IecTypes.GenericTypes.ANY, value);
		assertNotEquals(NO_ERROR, resultString);
	}

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
	void validateValidArrayLiterals(final String value, final DataType dataType, final String expectedResult) {
		final var varDeclaration = LibraryElementFactory.eINSTANCE.createVarDeclaration();
		varDeclaration.setName("ArrayTest"); //$NON-NLS-1$
		varDeclaration.setType(dataType);
		setArraySize(varDeclaration, "10"); //$NON-NLS-1$

		final String resultString = VariableOperations.validateValue(varDeclaration, value);
		assertEquals(expectedResult, resultString);
	}
}
