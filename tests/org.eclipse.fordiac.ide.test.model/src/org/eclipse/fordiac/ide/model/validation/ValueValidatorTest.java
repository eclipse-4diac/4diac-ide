/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("squid:S5960")
class ValueValidatorTest {

	private static final String NO_ERROR = ""; //$NON-NLS-1$
	private static final String INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE = "Invalid BOOL Literal! Valid BOOL literals are 0, 1, FALSE and TRUE"; //$NON-NLS-1$
	private static final String BASE_SPECIFIER_INVALID_FOR_BOOL = "Base specifiers are not allowed for BOOL literals!"; //$NON-NLS-1$
	private static final String TYPE_SPECIFIER_MANDATORY_FOR_ANYS = "Concrete type specifier is mandatory for ANY types!"; //$NON-NLS-1$
	private static final String INVALID_VIRTUAL_DNS_ENTRY_FORMAT_1 = "Characters used outside boundaries!"; //$NON-NLS-1$
	private static final String INVALID_VIRTUAL_DNS_ENTRY_FORMAT_2 = "\'%\' symbols used inside boundaries!"; //$NON-NLS-1$
	private static final String INVALID_VIRTUAL_DNS_ENTRY_FORMAT_3 = "Characters outside boundaries, \'%\' symbols used inside boundaries!"; //$NON-NLS-1$

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
	@SuppressWarnings("static-method")
	void validateValidBoolLiterals(final DataType type, final String value, final String expectedResult) {
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedResult, resultString);
	}

	static Stream<Arguments> validateInvalidBoolLiteralsTestCases() {
		return Stream.of(
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"0\"", //$NON-NLS-1$
						INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"1\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"FALSE\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"TRUE\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#0\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#1\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#FALSE\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#TRUE\"", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE),//$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "2", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "3.1415", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE),//$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "1970-01-30", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "16#AFFE", //$NON-NLS-1$
						BASE_SPECIFIER_INVALID_FOR_BOOL
						+ INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "12:00:00", INVALID_BOOL_LITERAL_VALID_BOOL_LITERALS_ARE_0_1_FALSE_AND_TRUE)); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for invalid BOOL literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateInvalidBoolLiteralsTestCases")
	@SuppressWarnings("static-method")
	void validateInvalidBoolLiterals(final DataType type, final String value, final String expectedErrorString) {
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedErrorString, resultString);
	}

	static Stream<Arguments> validateValidDateLiteralsTestCases() {
		return Stream.of(
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#2000-01-01", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#2000-12-31", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1815-12-10", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1852-11-27", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1906-12-09", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1992-01-01", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1878-11-07", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.DATE, "DATE#1968-10-27", NO_ERROR)); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for valid DATE literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateValidDateLiteralsTestCases")
	@SuppressWarnings("static-method")
	void validateValidDateLiterals(final DataType type, final String value, final String expectedResult) {
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedResult, resultString);
	}

	static Stream<Arguments> validateTypeSpecifierMandatoryForAnyLiteralsTestCases() {
		return Stream.of(
				Arguments.of(IecTypes.GenericTypes.ANY, "0", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_BIT, "-0", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_CHAR, "-342434", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_CHARS, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_DATE, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_DERIVED, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_DURATION, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_ELEMENTARY, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_INT, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_MAGNITUDE, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_NUM, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_REAL, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_SIGNED, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_STRING, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_STRUCT, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_UNSIGNED, "3.1415", TYPE_SPECIFIER_MANDATORY_FOR_ANYS)); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for mandatory type specifier for ANYs")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateTypeSpecifierMandatoryForAnyLiteralsTestCases")
	@SuppressWarnings("static-method")
	void validateTypeSpecifierMandatoryForAnyLiterals(final DataType type, final String value,
			final String expectedErrorString) {
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedErrorString, resultString);
	}


	@DisplayName("Validator tests for single correctly typed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = {"%sgsgs%", "%Hello%", "%moduleValue%", "%sign%"})
	@SuppressWarnings("static-method")
	void validateSingularValidDNSEntries(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(NO_ERROR, resultString);
	}


	@DisplayName("Validator tests for incorrect virtual DNS entries with outbound characters")
	@ParameterizedTest
	@ValueSource(strings = {"2%sgsgs%2", "33%Hello%5", "ee%moduleValue%hello", "rr%r%"})
	@SuppressWarnings("static-method")
	void validateInvalidDNSEntries_1(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_1, resultString);
	}


	@DisplayName("Validator tests for incorrect virtual DNS entries with inbound % symbols")
	@ParameterizedTest
	@ValueSource(strings = {"%sg%sgs%", "%Hel%lo%", "%module%Value%", "%%%"})
	@SuppressWarnings("static-method")
	void validateInvalidDNSEntries_2(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_2, resultString);
	}


	@DisplayName("Validator tests for incorrect virtual DNS entries with inbound and outbound % symbols")
	@ParameterizedTest
	@ValueSource(strings = {"333%sg%sgs%222", "@me%Hel%lo%hi", "%module%Value%rain", "tt%s%ww%5 "})
	@SuppressWarnings("static-method")
	void validateInvalidDNSEntries_3(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_3, resultString);
	}


	@DisplayName("Validator tests for several correctly typed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = {"%sgsgs%%sigsev%", "%Hello%%milsev%", "%moduleValue%%what%%my%", "%sign%%vile%%style%"})
	@SuppressWarnings("static-method")
	void validateMultipleValidDNSEntries(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(NO_ERROR, resultString);
	}


	@DisplayName("Validator tests for several incorrectly typed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = {"%sgsgs%name%sigsev%", "%Hello%sign%milsev%", "a%moduleValue%cnt%what%b", "e%sign%i%vile%f"})
	@SuppressWarnings("static-method")
	void validateMultipleInvalidDNSEntries_1(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_1, resultString);
	}

}
