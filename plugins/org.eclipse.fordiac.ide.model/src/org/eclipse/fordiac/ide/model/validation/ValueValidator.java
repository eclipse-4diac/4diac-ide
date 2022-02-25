/********************************************************************************
 * Copyright (c) 2021, 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - filling in the implementation,
 *    fixes handling for ANY types, fixes handling of Arrays
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.validation;

import static java.util.Map.entry;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyCharsType;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnySignedType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.DateAndTimeType;
import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.data.LtimeType;
import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.data.TimeType;
import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;

public final class ValueValidator {

	private static final String ST_UNDERSCORE_DIGIT_SEPARATOR = "_"; //$NON-NLS-1$
	private static final String VALIDATOR_ST_ESCAPE_DOUBLE_QUOTES = "$\""; //$NON-NLS-1$
	private static final String BASE_SPECIFIER_HEXADECIMAL = "16"; //$NON-NLS-1$
	private static final String BASE_SPECIFIER_OCTAL = "8"; //$NON-NLS-1$
	private static final String BASE_SPECIFIER_BINARY = "2"; //$NON-NLS-1$
	private static final int MAX_SECONDS_OF_MINUTE = 60;
	private static final int MAX_MINUTES_OF_HOUR = 60;
	private static final int MAX_HOURS_OF_DAY = 24;
	private static final String DATE_SEPARATOR = "-"; //$NON-NLS-1$
	private static final String BOOL_FALSE = "FALSE"; //$NON-NLS-1$
	private static final String BOOL_TRUE = "TRUE"; //$NON-NLS-1$
	private static final String BOOL_ZERO = "0"; //$NON-NLS-1$
	private static final String BOOL_ONE = "1"; //$NON-NLS-1$
	private static final String ST_ESCAPE_SYMBOL = "$"; //$NON-NLS-1$
	private static final String ST_ESCAPE_WITH_SINGLE_QUOTE = "$\'"; //$NON-NLS-1$
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String REGEX_LITERAL_SPLITTER = "([a-zA-Z]*#)?([0-9]*#)?(.*)"; //$NON-NLS-1$
	private static final String REGEX_SIGNED = "[+-]?[0-9](?:_?[0-9])*+"; //$NON-NLS-1$
	private static final String REGEX_UNSIGNED = "[0-9](?:_?[0-9])*+"; //$NON-NLS-1$
	private static final String REGEX_HEX = "(?:_?[0-9a-fA-F])*+"; //$NON-NLS-1$
	private static final String REGEX_OCTAL = "(?:_?[0-7])*+"; //$NON-NLS-1$
	private static final String REGEX_BINARY = "(?:_?[0-1])*+"; //$NON-NLS-1$
	private static final String REGEX_REAL = REGEX_SIGNED + "." + REGEX_UNSIGNED + "(?:[Ee]" + REGEX_SIGNED + ")?"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final String REGEX_FIX_POINT = REGEX_UNSIGNED + "(?:\\." + REGEX_UNSIGNED + ")?"; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String REGEX_TIME_OF_DAY = REGEX_UNSIGNED + ":" + REGEX_UNSIGNED + ":" + REGEX_FIX_POINT; //$NON-NLS-1$ //$NON-NLS-2$
	private static final String REGEX_DATE = REGEX_UNSIGNED + DATE_SEPARATOR + REGEX_UNSIGNED + DATE_SEPARATOR + REGEX_UNSIGNED;

	private static final String H_REGEX_HEX_DIGIT = "[A-Fa-f0-9]"; //$NON-NLS-1$
	private static final String H_REGEX_SPECIAL_CHAR = "\\$\\$ | \\$L | \\$N | \\$P | \\$R | \\$T"; //$NON-NLS-1$
	private static final String H_REGEX_COMMON_CHAR = "[^ \" ' \\$ ] | " + H_REGEX_SPECIAL_CHAR; //$NON-NLS-1$

	private static final String H_REGEX_CHAR = H_REGEX_COMMON_CHAR + "|" + "\\$' | \"" + "|" + "\\$" + H_REGEX_HEX_DIGIT //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			+ "{2}"; //$NON-NLS-1$
	private static final String REGEX_CHAR = ("^' (?:" + H_REGEX_CHAR + ") \\' $").replace(" ", EMPTY_STRING); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final String REGEX_STRING = ("^' (?:" + H_REGEX_CHAR + ")* \\' $").replace(" ", EMPTY_STRING); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private static final String H_REGEX_WCHAR = H_REGEX_COMMON_CHAR + "|" + "\\$\" | '" + "|" + "\\$" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			+ H_REGEX_HEX_DIGIT + "{4}"; //$NON-NLS-1$
	private static final String REGEX_WCHAR = ("^\\\" (?:" + H_REGEX_WCHAR + ") \\\" $").replace(" ", EMPTY_STRING); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private static final String REGEX_WSTRING = ("^\\\" (?:" + H_REGEX_WCHAR + ")* \\\" $").replace(" ", EMPTY_STRING); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private static final String[] timeNames = { "d", "h", "m", "s", "ms", "us", "ns" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
	private static final int[] timesMaxs = { 365, 24, 60, 60, 1000, 1000, 1000 };

	private static final String REGEX_VIRTUAL_DNS_ENTRY = "(%)(.)*(%)" ;

	private static final String TIME_SHORT_FORM = "T"; //$NON-NLS-1$
	private static final String LONG_TIME_SHORT_FORM = "LT"; //$NON-NLS-1$
	private static final String DATE_AND_TIME_SHORT_FORM = "DT"; //$NON-NLS-1$

	private static final Map<String, String> SHORT_FORM_TRANSLATIONS = Map
			.ofEntries(entry(TIME_SHORT_FORM, FordiacKeywords.TIME), entry(LONG_TIME_SHORT_FORM, FordiacKeywords.LTIME),
					entry(FordiacKeywords.TOD, FordiacKeywords.TIME_OF_DAY),
					entry(FordiacKeywords.LTOD, FordiacKeywords.LTIME_OF_DAY),
					entry(DATE_AND_TIME_SHORT_FORM, FordiacKeywords.DATE_AND_TIME),
					entry(FordiacKeywords.LDT, FordiacKeywords.LDATE_AND_TIME));

	/**
	 * Returns {@code true} if the given STLiteral is a valid bool Literal. A valid
	 * bool Literal is either 0, 1, TRUE, or FALSE.
	 *
	 * @param literal String which should be tested.
	 * @return {@code true} if the given STLiteral is a valid bool Literal.
	 */
	private static boolean isBoolLiteralValid(final String literal) {
		return literal.equals(BOOL_ZERO) || literal.equals(BOOL_ONE) || literal.equalsIgnoreCase(BOOL_TRUE)
				|| literal.equalsIgnoreCase(BOOL_FALSE);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid signed Literal. Valid
	 * SIGNED literals start with either a sign (+,-) or a number and can be
	 * interrupted multiple times by a single _ character after a number.
	 *
	 * @param literal String which should be tested.
	 * @return {@code true} if the given STLiteral is a valid signed Literal.
	 */
	private static boolean isSignedLiteralValid(final String literal) {
		return literal.matches(REGEX_SIGNED);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid unsigned Literal.
	 * Valid UNSIGNED literals start with a number and can be interrupted multiple
	 * times by a single _ character after.
	 *
	 * @param literal String which should be tested.
	 * @return {@code true} if the given STLiteral is a valid unsigned Literal.
	 */
	private static boolean isUnsignedLiteralValid(final String literal) {
		return literal.matches(REGEX_UNSIGNED);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid hex Literal. Valid
	 * HEXADECIMAL literals consists of number from 0 to 9 and A to F and can be
	 * interrupted multiple times by a single _.
	 *
	 * @param literal String which should be tested.
	 * @return {@code true} if the given STLiteral is a valid hex Literal.
	 */
	private static boolean isHexBaseLiteralValid(final String literal) {
		return literal.matches(REGEX_HEX);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid octal Literal. Valid
	 * OCTAL literals consists of number from 0 to 7 and can be interrupted multiple
	 * times by a single _.
	 *
	 * @param literal String which should be tested.
	 * @return {@code true} if the given STLiteral is a valid octal Literal.
	 */
	private static boolean isOctalBaseLiteralValid(final String literal) {
		return literal.matches(REGEX_OCTAL);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid binary Literal. Valid
	 * BINARY literals consists of 0s and 1s and can be interrupted multiple times
	 * by a single _.
	 *
	 * @param literal STLiteral which should be tested.
	 * @return {@code true} if the given STLiteral is a valid binary Literal.
	 */
	private static boolean isBinaryBaseLiteralValid(final String literal) {
		return literal.matches(REGEX_BINARY);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid real Literal. Valid
	 * REAL literals start with either a sign (+,-) or a number, must have a decimal
	 * point . and may have an exponent marked with E, and can be interrupted
	 * multiple times by a single _ character after the first digit.
	 * <p>
	 * EBNF:
	 * <dl>
	 * <dt>Real</dt>
	 * <dd>('+'|'-')? Unsigned_Int '.' Unsigned_Int('E' ('+'|'-')
	 * Unsigned_Int)?</dd>
	 * <dt>Unsigned_Int</dt>
	 * <dd>(DIGIT)('_'? DIGIT)*</dd>
	 * <dt>DIGIT</dt>
	 * <dd>'0'...'9'</dd>
	 * </dl>
	 *
	 * @param literal STLiteral which should be tested.
	 * @return {@code true} if the given STLiteral is a valid binary Literal.
	 */
	private static boolean isRealLiteralValid(final String literal) {
		return literal.matches(REGEX_REAL);
	}

	/**
	 * Returns {@code true} if the given STLiteral is a valid FixPoint Literal.
	 * Valid FixPoint literals are decimal numbers, can have a fractional part
	 * separated by a single'.' and can be interrupted multiple times by a single _.
	 * A sign (+, -) is not allowed.
	 *
	 * @param literalValue
	 * @return {@code true} if the given STLiteral is a valid FixPoint literal
	 */
	private static boolean isFixPointStringValid(final String literalValue) {
		return literalValue.matches(REGEX_FIX_POINT);
	}

	private static String isTODStringValid(final String literalValue) {
		if (!literalValue.matches(REGEX_TIME_OF_DAY)) {
			return Messages.VALIDATOR_INVALID_FORMAT_TOD_LITERAL;
		}

		final String[] parts = literalValue.replace(ST_UNDERSCORE_DIGIT_SEPARATOR, EMPTY_STRING).split(":"); //$NON-NLS-1$
		var errorString = EMPTY_STRING;
		if (Integer.parseInt(parts[0]) >= MAX_HOURS_OF_DAY) {
			errorString += Messages.VALIDATOR_HOURS_VALUE_CANT_BE_24;
		}
		if (Integer.parseInt(parts[1]) >= MAX_MINUTES_OF_HOUR) {
			errorString += Messages.VALIDATOR_MINUTE_VALUE_CANT_BE_LARGER_THAN_60;
		}

		if (Float.parseFloat(parts[2]) >= MAX_SECONDS_OF_MINUTE) {
			errorString += Messages.VALIDATOR_SECONDS_VALUE_CANT_BE_LARGER_THAN_60;
		}

		return errorString;
	}

	public static String validateValue(final VarDeclaration varDeclaration) {
		final var varDeclarationValue = varDeclaration.getValue().getValue();
		return validateValue(varDeclaration, varDeclarationValue);
	}

	public static String validateValue(final VarDeclaration varDeclaration, final String valueString) {
		if (varDeclaration.isArray()) {
			final var trimmedString = valueString.trim();
			// Check if the Array is delimited by []
			if (!(trimmedString.charAt(0) == '[' && trimmedString.charAt(trimmedString.length() - 1) == ']')) {
				return Messages.VALIDATOR_ARRAY_MISSES_BRACKETS;
			}
			final var onlyValuesString = trimmedString.substring(1, trimmedString.length() - 1);
			if (onlyValuesString.isEmpty()) {
				return EMPTY_STRING;
			}
			if (onlyValuesString.charAt(0) == ',') {
				return Messages.VALIDATOR_ARRAY_STARTS_WITH_VALUE_DELIMITER;
			}
			// split array at "," but Strings can also include "," so String split does not work
			final var splitArrayLiteralString = splitArrayValueString(onlyValuesString, varDeclaration.getType());
			final StringBuilder stringBuilder = new StringBuilder();
			int index = 0;
			for(final var literalString : splitArrayLiteralString) {
				final var errorString = validateValue(varDeclaration.getType(), literalString);
				if (!errorString.isBlank()) {
					if (stringBuilder.length() != 0) {
						stringBuilder.append(", "); //$NON-NLS-1$
					}
					stringBuilder.append(Messages.VALIDATOR_INDEX);
					stringBuilder.append(" "); //$NON-NLS-1$
					stringBuilder.append(index);
					stringBuilder.append(": "); //$NON-NLS-1$
					stringBuilder.append(errorString);
				}
				index++;
			}
			return stringBuilder.toString();
		}
		return validateValue(varDeclaration.getType(), valueString);
	}

	private static List<String> splitArrayValueString(final String onlyValuesString, final DataType type) {

		if (!(type instanceof AnyCharsType)) {
			return Arrays.stream(onlyValuesString.split(",")).map(String::trim).collect(Collectors.toList()); //$NON-NLS-1$
		}
		return splitIECStringArray(onlyValuesString, type).stream().map(String::trim).collect(Collectors.toList());
	}

	private static List<String> splitIECStringArray(final String onlyValuesString, final DataType stringType) {
		final char arrayValueDelimiter = ',';
		char stringDelimiter = 0;
		final List<String> splitString = new ArrayList<>();
		if (stringType instanceof CharType || stringType instanceof StringType) {
			stringDelimiter = '\'';
		} else if (stringType instanceof WcharType || stringType instanceof WstringType) {
			stringDelimiter = '"';
		}

		int startIndex = 0;
		boolean insideString = (onlyValuesString.charAt(startIndex) == stringDelimiter);
		for (int i = 1; i < onlyValuesString.length(); i++) {
			if (onlyValuesString.charAt(i) == stringDelimiter && onlyValuesString.charAt(i - 1) != '$') {
				insideString = !insideString;
			}
			if (!insideString && onlyValuesString.charAt(i) == arrayValueDelimiter) {
				splitString.add(onlyValuesString.substring(startIndex, i));
				startIndex = i + 1; // We are at the ',' which delimits the values but is not part of them
			}
		}
		// The last entry is not delimited by a ","
		splitString.add(onlyValuesString.substring(startIndex, onlyValuesString.length()));
		return splitString;
	}

	/**
	 * Check the given value if it is valid literal for the data type
	 *
	 * @param type  data type for which the literal should be checked
	 * @param value the literal value to check
	 * @return empty string if the literal is valid, otherwise an error message what
	 *         is wrong with the literal
	 */
	public static String validateValue(final DataType type, final String value) {

		final String virtualDNSMessage = checkVirtualDNS(value);
		if (virtualDNSMessage != null) {
			return virtualDNSMessage;
		}

		final var pattern = Pattern.compile(REGEX_LITERAL_SPLITTER);
		final var matcher = pattern.matcher(value);
		// check if literal matches the general literal structure (<type>#)?(<radix>#)?<value>
		if (!matcher.find()) {
			return Messages.VALIDATOR_UNKOWN_LITERAL_ERROR_PLEASE_CHECK_SANENESS_OF_LITERAL;
		}

		// First group contains the type specifier if present
		var typeSpecifier = matcher.group(1);
		if (typeSpecifier != null) {
			// delete # from type name
			typeSpecifier = typeSpecifier.replace("#", EMPTY_STRING); //$NON-NLS-1$
		} else {
			// no type information available
			typeSpecifier = EMPTY_STRING;
		}

		// Second group contains base modifier if present
		var baseSpecifier = matcher.group(2);
		if (baseSpecifier != null) {
			// delete # from base
			baseSpecifier = baseSpecifier.replace("#", EMPTY_STRING); //$NON-NLS-1$
		} else {
			// no base information available, so base 10 is implicitly selected
			baseSpecifier = EMPTY_STRING;
		}


		DataType literalType = null;

		if (IecTypes.GenericTypes.isAnyType(type)) {
			if (typeSpecifier.isBlank() || null == IecTypes.ElementaryTypes.getTypeByName(typeSpecifier)) {
				return Messages.VALIDATOR_CONCRETE_TYPE_SPECIFIER_MANDATORY_FOR_ANYS;
			}
			literalType = IecTypes.ElementaryTypes.getTypeByName(typeSpecifier);
			if (null == literalType) {
				literalType = IecTypes.ElementaryTypes.getTypeByName(SHORT_FORM_TRANSLATIONS.get(typeSpecifier));
			}
		} else {
			if (!typeSpecifier.isBlank()) {
				literalType = IecTypes.ElementaryTypes.getTypeByName(typeSpecifier);
				if (null == literalType) {
					literalType = IecTypes.ElementaryTypes.getTypeByName(SHORT_FORM_TRANSLATIONS.get(typeSpecifier));
				}
			} else {
				literalType = type; // We assume that the literal type is the same as the data type
			}
		}

		if (null == literalType) {
			return Messages.VALIDATOR_UNKNOWN_LITERAL_TYPE;
		}

		if (!literalType.isCompatibleWith(type)) {
			return MessageFormat.format(Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE, literalType.getName(),
					type.getName());
		}

		final var literalValue = matcher.group(3);
		if (literalType instanceof AnyBitType) {
			return checkAnyBitLiteral(literalType, baseSpecifier, literalValue);
		} else if (literalType instanceof AnyIntType) {
			return checkAnyIntLiteral(literalType, baseSpecifier, literalValue);
		} else if (literalType instanceof AnyRealType) {
			return checkAnyRealLiteral(literalType, typeSpecifier, baseSpecifier, literalValue);
		} else if (literalType instanceof AnyDurationType) {
			return checkDurationLiteral(literalType, baseSpecifier, literalValue);
		} else if (literalType instanceof AnyDateType) {
			return checkAnyDateLiteral(literalType, literalValue);
		} else if (literalType instanceof AnyCharsType) {
			return checkAnyCharsLiteral(literalType, baseSpecifier, literalValue);
		}

		return EMPTY_STRING;
	}

	private static String checkVirtualDNS(final String value) {
		final var virtualDNSPattern = Pattern.compile(REGEX_VIRTUAL_DNS_ENTRY);
		final var virtualDNSmatcher = virtualDNSPattern.matcher(value);
		// check if literal is a virtual DNS entry
		if (virtualDNSmatcher.find()) {
			final boolean outbound = !(value.charAt(0) == '%' && value.charAt(value.length() - 1) == '%');
			boolean inbound = false;

			int counter = 0;
			// true if % is followed by %
			boolean followUp = false;

			for (int i = 0; i < value.length(); ++i) {
				if (value.charAt(i) == '%') {
					counter++;
					if (i < value.length() - 1 && counter % 2 == 0) {
						followUp = value.charAt(i + 1) == '%';
					}
				}

			}

			// true if literal is a single virtual DNS entry

			inbound = counter % 2 == 1;

			if(outbound && inbound) {
				return Messages.VALIDATOR_VIRTUAL_DNS_ILLEGAL_FORMAT;
			}

			if(inbound && !outbound) {
				return Messages.VALIDATOR_VIRTUAL_DNS_MULTIPLE_BOUNDING_CHARACTERS;
			}

			final boolean single = (counter == 2);
			if(outbound && !inbound || (!followUp && !single)) {
				return Messages.VALIDATOR_VIRTUAL_DNS_CHARACTERS_OUT_OF_BOUNDS;
			}
			return EMPTY_STRING;
		}
		return null;
	}

	private static String checkAnyCharsLiteral(final DataType type, final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;
		if (!baseSpecifier.isBlank()) {
			errorString += MessageFormat.format(Messages.VALIDATOR_BASE_SPECIFIER_IS_INVALID_FOR, type.getName());
		}
		if (type instanceof CharType) {
			errorString += checkCharLiteral(type, literalValue);
		} else if (type instanceof WcharType) {
			errorString += checkWcharLiteral(type, literalValue);
		} else if (type instanceof StringType) {
			errorString += checkStringLiteral(type, literalValue);
		} else if (type instanceof WstringType) {
			errorString += checkWstringLiteral(type, literalValue);
		}
		return errorString;
	}

	private static String checkWstringLiteral(final DataType type, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (!literalValue.matches(REGEX_WSTRING)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_INVALID_WSTRING_WCHAR_LITERAL, type.getName(), type.getName());
			if (literalValue.contains(ST_ESCAPE_WITH_SINGLE_QUOTE)) {
				errorString += Messages.VALIDATOR_STRING_QUOTE_DOES_NOT_NEED_ESCAPE_SYMBOL;
			}
			if (literalValue.contains(ST_ESCAPE_SYMBOL)) { // correctly escaped characters do not trigger this error
				// because of the if
				errorString += Messages.VALIDATOR_W_STRING_DOLLAR_IS_ESCAPE_SYMBOL;
			}
		}

		return errorString;
	}

	private static String checkWcharLiteral(final DataType type, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (!literalValue.matches(REGEX_WCHAR)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_INVALID_WSTRING_WCHAR_LITERAL, type.getName(), type.getName());
			if (literalValue.contains("$'")) { //$NON-NLS-1$
				errorString += Messages.VALIDATOR_STRING_QUOTE_DOES_NOT_NEED_ESCAPE_SYMBOL;
			}
			if (literalValue.contains(ST_ESCAPE_SYMBOL)) { // correctly escaped characters do not trigger this error
				// because of the if.
				errorString += Messages.VALIDATOR_W_STRING_DOLLAR_IS_ESCAPE_SYMBOL;
			}
			if (literalValue.length() == 2) { // correctly escaped characters do not trigger this error because of the
				// if.
				errorString += Messages.VALIDATOR_EMPTY_CHARACTERS_ARE_NOT_ALLOWED;
			}
		}

		return errorString;
	}

	private static String checkCharLiteral(final DataType type, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (!literalValue.matches(REGEX_CHAR)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_INVALID_STRING_CHAR_LITERAL, type.getName(), type.getName());
			if (literalValue.contains(VALIDATOR_ST_ESCAPE_DOUBLE_QUOTES)) {
				errorString += Messages.VALIDATOR_WSTRING_QUOTES_HAVE_NOT_TO_BE_ESCAPED;
			}
			if (literalValue.contains(ST_ESCAPE_SYMBOL)) { // correctly escaped characters do not trigger this error
				// because of the if.
				errorString += Messages.VALIDATOR_W_STRING_DOLLAR_IS_ESCAPE_SYMBOL;
			}
			if (literalValue.length() == 2) { // correctly escaped characters don't trigger this error because of the
				// if.
				errorString += Messages.VALIDATOR_EMPTY_CHARACTERS_ARE_NOT_ALLOWED;
			}
		}
		return errorString;
	}

	private static String checkStringLiteral(final DataType type, final String literalValue) {
		var errorString = EMPTY_STRING;
		if (!literalValue.matches(REGEX_STRING)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_INVALID_STRING_CHAR_LITERAL, type.getName(), type.getName());
			if (literalValue.contains(VALIDATOR_ST_ESCAPE_DOUBLE_QUOTES)) {
				errorString += Messages.VALIDATOR_WSTRING_QUOTES_HAVE_NOT_TO_BE_ESCAPED;
			}
			if (literalValue.contains(ST_ESCAPE_SYMBOL)) { // correctly escaped characters do not trigger this error
				// because of the if.
				errorString += Messages.VALIDATOR_W_STRING_DOLLAR_IS_ESCAPE_SYMBOL;
			}
		}
		return errorString;
	}

	private static String checkAnyDateLiteral(final DataType type, final String literalValue) {
		var errorString = EMPTY_STRING;
		if (type instanceof DateType || type instanceof LdateType) {
			errorString += checkDateLiteral(literalValue);
		} else if (type instanceof DateAndTimeType || type instanceof LdtType) {
			errorString += checkDateAndTimeLiteral(literalValue);
		}
		else if (type instanceof TimeOfDayType || type instanceof LtodType) {
			errorString += isTODStringValid(literalValue);
		}
		return errorString;
	}

	private static boolean isDateStringValid(final String literalValue) {
		final var dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
		dateFormat.setLenient(false);

		if (!literalValue.matches(REGEX_DATE)) {
			return false;
		}

		try {
			if (dateFormat.parse(literalValue.replace(ST_UNDERSCORE_DIGIT_SEPARATOR, EMPTY_STRING)) == null) {
				return false;
			}
		} catch (final ParseException e) {
			return false;
		}
		return true;
	}

	private static String checkDateLiteral(final String literalValue) {
		var errorString = EMPTY_STRING;
		if (!isDateStringValid(literalValue)) {
			errorString += Messages.VALIDATOR_INVALID_DATE_FORMAT;
		}
		return errorString;
	}

	private static boolean isDaTLiteralValid(final String literalValue) {
		final int index = literalValue.lastIndexOf(DATE_SEPARATOR);
		if (index == -1) {
			return false;
		}

		final var date = literalValue.substring(0, index);
		final var time = literalValue.substring(index + 1);

		return isDateStringValid(date) && isTODStringValid(time).isBlank();

	}

	private static String checkDateAndTimeLiteral(final String literalValue) {
		var errorString = EMPTY_STRING;
		if (!isDaTLiteralValid(literalValue)) {
			errorString += Messages.VALIDATOR_INVALID_DATE_AND_TIME_FORMAT;
		}
		return errorString;
	}

	private static String checkDurationLiteral(final DataType type, final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;
		if (!baseSpecifier.isBlank()) {
			errorString += MessageFormat.format(Messages.VALIDATOR_BASE_SPECIFIER_INVALID_FOR_TYPE, type.getName());
		}
		if (type instanceof TimeType || type instanceof LtimeType) {
			errorString += isTimeIntervalLiteralValid(literalValue);
			if (!errorString.isBlank()) {
				errorString = Messages.VALIDATOR_INVALID_TIME_LITERAL + errorString;
			}
		}
		return errorString;
	}

	private static String isTimeIntervalLiteralValid(String literalValue) {
		literalValue = literalValue.replaceFirst("^[+-]", EMPTY_STRING); // remove starting sign //$NON-NLS-1$
		// character
		return isTimeIntervalStringValid(literalValue, 0, true);
	}

	@SuppressWarnings("boxing")
	private static String isTimeIntervalStringValid(final String literalValue, final int timeNamePos, final boolean isMSV) {

		if (timeNamePos >= timeNames.length) { // no more time definition found
			return Messages.VALIDATOR_TOO_MANY_TIME_DEFINITIONS_FOUND;
		}

		// regex for checking if searched time definition is in the value: before and
		// after can either be a number or "_".
		// this is needed to differentiate between "s" and "ms", "ns" ...
		// The first part ".*?" searches as few character as possible, thus the first
		// unit is matched.
		// The last part ".*" matches as many times as possible, thus consuming
		// recurring names.
		final String regex = ".*?(?:_|[0-9])(?<unit>" + timeNames[timeNamePos] + ")(?:(_|[0-9]).*)?"; //$NON-NLS-1$ //$NON-NLS-2$
		final var pattern = Pattern.compile(regex);
		final String lowerCaseLiteralValue = literalValue.toLowerCase();
		final var matcher = pattern.matcher(lowerCaseLiteralValue);

		if (!matcher.matches()) { // if not found check next smaller time definition
			return isTimeIntervalStringValid(lowerCaseLiteralValue, timeNamePos + 1, isMSV);
		}

		final var biggestTime = lowerCaseLiteralValue.substring(0, matcher.start("unit")); //$NON-NLS-1$

		if (matcher.end(1) + 1 > lowerCaseLiteralValue.length()) { // last/smallest definition
			if (!(biggestTime.matches(REGEX_UNSIGNED) || isFixPointStringValid(biggestTime))) {
				return Messages.VALIDATOR_INVALID_TIME_LITERAL;
			}

			if (!(isMSV || Float.parseFloat(biggestTime.replace(ST_UNDERSCORE_DIGIT_SEPARATOR,
					EMPTY_STRING)) < timesMaxs[timeNamePos])) {
				return MessageFormat.format(Messages.VALIDATOR_ST_TIME_LITERAL_OVERFLOW_ERROR, biggestTime,
						timeNames[timeNamePos],
						timesMaxs[timeNamePos]);
			}

			return EMPTY_STRING; // No error

		}
		if (!biggestTime.matches(REGEX_UNSIGNED)) {
			final var errSTr = Messages.VALIDATOR_ST_TIME_LITERAL_ERROR_UNSIGNED_EXPECTED;
			return MessageFormat.format(errSTr, biggestTime, timeNames[timeNamePos]);
		}
		if (!(isMSV || Integer.parseInt(
				biggestTime.replace(ST_UNDERSCORE_DIGIT_SEPARATOR,
						EMPTY_STRING)) <= timesMaxs[timeNamePos])) {
			return MessageFormat.format(Messages.VALIDATOR_ST_TIME_LITERAL_OVERFLOW_ERROR, biggestTime, timeNames[timeNamePos],
					timesMaxs[timeNamePos]);
		}

		var subTime = lowerCaseLiteralValue.substring(matcher.end("unit"), lowerCaseLiteralValue.length()); //$NON-NLS-1$
		// delete preceding "_": EBNF: ( Unsigned_Int 'd' '_' ? )
		subTime = subTime.replaceFirst("^_", EMPTY_STRING); //$NON-NLS-1$
		// check smaller
		return isTimeIntervalStringValid(subTime, timeNamePos + 1, false);
	}

	private static String checkAnyBitLiteral(final DataType type, final String baseSpecifier, final String literalValue) {
		if (type instanceof BoolType) {
			return checkBookLiteral(baseSpecifier, literalValue);
		}
		var errorString = EMPTY_STRING;
		if (baseSpecifier.isBlank() && !isUnsignedLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_UNSIGNED_VALUE_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_BINARY) && !isBinaryBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_BINARY_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_OCTAL) && !isOctalBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_OCTAL_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_HEXADECIMAL) && !isHexBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_HEXADECIMAL_NUMBER_CLAUSE, type.getName());
		}
		return errorString;
	}

	private static String checkAnyRealLiteral(final DataType type, final String typeSpecifier,
			final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (!baseSpecifier.isBlank()) {
			errorString += MessageFormat.format(Messages.VALIDATOR_BASE_SPECIFIER_IS_INVALID_FOR, type.getName());
		}

		if (typeSpecifier.isBlank()) { // literal type was not explicitly given, so it could be a implicit castable
			// value too
			if (!isRealLiteralValid(literalValue) && !isSignedLiteralValid(literalValue)
					&& !isBinaryBaseLiteralValid(literalValue) && !isOctalBaseLiteralValid(literalValue)
					&& !isHexBaseLiteralValid(literalValue)) {
				errorString += MessageFormat.format(Messages.VALIDATOR_VALID_REAL_VALUE, type.getName());
			}
		} else {
			if (!isRealLiteralValid(literalValue)) {
				errorString += MessageFormat.format(Messages.VALIDATOR_VALID_REAL_VALUE, type.getName());
			}
		}

		return errorString;
	}

	private static String checkAnyIntLiteral(final DataType type, final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (!baseSpecifier.isBlank()
				&& (!(baseSpecifier.equals(BASE_SPECIFIER_BINARY) || baseSpecifier.equals(BASE_SPECIFIER_OCTAL) || baseSpecifier.equals(BASE_SPECIFIER_HEXADECIMAL)))) {
			errorString += Messages.VALIDATOR_INVALID_BASE_SPECIFIER;
		}
		if (type instanceof AnySignedType) {
			errorString += checkAnySignedLiteral(type, baseSpecifier, literalValue);
		}
		if (type instanceof AnyUnsignedType) {
			errorString += checkAnyUnsignedLiteral(type, baseSpecifier, literalValue);
		}

		return errorString;
	}

	private static String checkAnyUnsignedLiteral(final DataType type, final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (baseSpecifier.isBlank() && !isUnsignedLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_UNSIGNED_VALUE_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_BINARY) && !isBinaryBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_BINARY_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_OCTAL) && !isOctalBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_OCTAL_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_HEXADECIMAL) && !isHexBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_HEXADECIMAL_NUMBER_CLAUSE, type.getName());
		}

		return errorString;
	}

	private static String checkAnySignedLiteral(final DataType type, final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (baseSpecifier.isBlank() && !isSignedLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_SIGNED_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_BINARY) && !isBinaryBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_BINARY_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_OCTAL) && !isOctalBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_OCTAL_NUMBER_CLAUSE, type.getName());
		} else if (baseSpecifier.equals(BASE_SPECIFIER_HEXADECIMAL) && !isHexBaseLiteralValid(literalValue)) {
			errorString += MessageFormat.format(Messages.VALIDATOR_VALID_HEXADECIMAL_NUMBER_CLAUSE, type.getName());
		}

		return errorString;
	}

	private static String checkBookLiteral(final String baseSpecifier, final String literalValue) {
		var errorString = EMPTY_STRING;

		if (!baseSpecifier.isBlank()) {
			errorString += Messages.VALIDATOR_NO_BASE_SPECIFIER_FOR_BOOL;
		}
		if (!isBoolLiteralValid(literalValue)) {
			errorString += Messages.VALIDATOR_INVALID_BOOL_LITERAL;
		}
		return errorString;
	}

	private ValueValidator() {
		throw new UnsupportedOperationException("helper class ValueValidator should not be instantiated"); //$NON-NLS-1$
	}
}
