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
 *   Martin Jobst
 *     - migrated to typed value converter
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({ "static-method", "squid:S5960" })
class ValueValidatorTest {

	private static final String NO_ERROR = ""; //$NON-NLS-1$
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
	void validateValidBoolLiterals(final DataType type, final String value, final String expectedResult) {
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedResult, resultString);
	}

	static Stream<Arguments> validateInvalidBoolLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"0\"", //$NON-NLS-1$
				Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"1\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"FALSE\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"TRUE\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#0\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#1\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#FALSE\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "\"BOOL#TRUE\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "2", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "1970-01-30", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "16#AFFE", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, "12:00:00", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_BOOL_LITERAL));
	}

	@DisplayName("Validator tests for invalid BOOL literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateInvalidBoolLiteralsTestCases")
	void validateInvalidBoolLiterals(final DataType type, final String value, final String expectedFormatString) {
		final String expectedString = MessageFormat.format(expectedFormatString, type.getName());
		final String resultString = ValueValidator.validateValue(type, value);
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
				Arguments.of(IecTypes.ElementaryTypes.WORD, "16#AFFE", NO_ERROR), //$NON-NLS-1$
				Arguments.of(IecTypes.ElementaryTypes.LWORD, "16#FFFFFFFFFFFFFFFF", NO_ERROR)); //$NON-NLS-1$
	}

	@DisplayName("Validator tests for valid numeric literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateValidNumberLiteralsTestCases")
	void validateValidNumberLiterals(final DataType type, final String value, final String expectedResult) {
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedResult, resultString);
	}

	static Stream<Arguments> validateInvalidNumberLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.SINT, "\"0\"", //$NON-NLS-1$
				Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.SINT, "\"1\"", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.SINT, "FALSE", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.SINT, "TRUE", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.SINT, "129", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.INT, "123456", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.INT, "3.14", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.UINT, "-1", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.UINT, "-17", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, "16#AFFE", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.REAL, "3.14e120", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL),
				Arguments.of(IecTypes.ElementaryTypes.LREAL, "3.14e1200", //$NON-NLS-1$
						Messages.VALIDATOR_INVALID_NUMBER_LITERAL));
	}

	@DisplayName("Validator tests for invalid numeric literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateInvalidNumberLiteralsTestCases")
	void validateInvalidNumberLiterals(final DataType type, final String value, final String expectedFormatString) {
		final String expectedString = MessageFormat.format(expectedFormatString, type.getName());
		final String resultString = ValueValidator.validateValue(type, value);
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
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier));
	}

	@DisplayName("Validator tests for valid DATE literals")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateValidDateLiteralsTestCases")

	void validateValidDateLiterals(final DataType type, final String value, final String expectedFormatString) {
		final String expectedString = MessageFormat.format(expectedFormatString, type.getName());
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedString, resultString);
	}

	static Stream<Arguments> validateTypeSpecifierMandatoryForAnyLiteralsTestCases() {
		return Stream.of(Arguments.of(IecTypes.GenericTypes.ANY, "0", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_BIT, "-0", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_CHAR, "-342434", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_CHARS, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_DATE, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_DERIVED, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_DURATION, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_ELEMENTARY, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_INT, "3.1415", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_MAGNITUDE, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_NUM, "3.1415", Messages.VALIDATOR_DatatypeRequiresTypeSpecifier), //$NON-NLS-1$
				Arguments.of(IecTypes.GenericTypes.ANY_REAL, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_SIGNED, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_STRING, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_STRUCT, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier),
				Arguments.of(IecTypes.GenericTypes.ANY_UNSIGNED, "3.1415", //$NON-NLS-1$
						Messages.VALIDATOR_DatatypeRequiresTypeSpecifier));
	}

	@DisplayName("Validator tests for mandatory type specifier for ANYs")
	@ParameterizedTest(name = "{index}: Literal: {1}")
	@MethodSource("validateTypeSpecifierMandatoryForAnyLiteralsTestCases")

	void validateTypeSpecifierMandatoryForAnyLiterals(final DataType type, final String value,
			final String expectedFormatString) {
		final String expectedString = MessageFormat.format(expectedFormatString, type.getName());
		final String resultString = ValueValidator.validateValue(type, value);
		assertEquals(expectedString, resultString);
	}

	@DisplayName("Validator tests for single correctly typed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = { "%sgsgs%", "%Hello%", "%moduleValue%", "%sign%" })

	void validateSingularValidDNSEntries(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(NO_ERROR, resultString);
	}

	@DisplayName("Validator tests for incorrect virtual DNS entries with outbound characters")
	@ParameterizedTest
	@ValueSource(strings = { "2%sgsgs%2", "33%Hello%5", "ee%moduleValue%hello", "rr%r%" })

	void validateInvalidDNSEntries1(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_1, resultString);
	}

	@DisplayName("Validator tests for incorrect virtual DNS entries with inbound % symbols")
	@ParameterizedTest
	@ValueSource(strings = { "%sg%sgs%", "%Hel%lo%", "%module%Value%", "%%%" })

	void validateInvalidDNSEntries2(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_2, resultString);
	}

	@DisplayName("Validator tests for incorrect virtual DNS entries with inbound and outbound % symbols")
	@ParameterizedTest
	@ValueSource(strings = { "333%sg%sgs%222", "@me%Hel%lo%hi", "%module%Value%rain", "tt%s%ww%5 " })

	void validateInvalidDNSEntries3(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_3, resultString);
	}

	@DisplayName("Validator tests for several correctly typed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = { "%sgsgs%%sigsev%", "%Hello%%milsev%", "%moduleValue%%what%%my%", "%sign%%vile%%style%" })

	void validateMultipleValidDNSEntries(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(NO_ERROR, resultString);
	}

	@DisplayName("Validator tests for several incorrectly typed virtual DNS entries")
	@ParameterizedTest
	@ValueSource(strings = { "%sgsgs%name%sigsev%", "%Hello%sign%milsev%", "a%moduleValue%cnt%what%b",
	"e%sign%i%vile%f" })

	void validateMultipleInvalidDNSEntries_1(final String value) {
		final String resultString = ValueValidator.validateValue(IecTypes.GenericTypes.ANY, value);
		assertEquals(INVALID_VIRTUAL_DNS_ENTRY_FORMAT_1, resultString);
	}

}
