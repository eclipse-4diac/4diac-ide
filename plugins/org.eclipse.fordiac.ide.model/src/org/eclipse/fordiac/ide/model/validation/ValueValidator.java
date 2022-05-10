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
 *                             fixes handling for ANY types,
 *                             fixes handling of Arrays
 *   Alois Zoitl - migrated to value converters
 ********************************************************************************/
package org.eclipse.fordiac.ide.model.validation;

import static java.util.Map.entry;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.FordiacKeywords;
import org.eclipse.fordiac.ide.model.Messages;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyDurationType;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.value.ArrayValueConverter;
import org.eclipse.fordiac.ide.model.value.ValueConverter;
import org.eclipse.fordiac.ide.model.value.ValueConverterFactory;

public final class ValueValidator {

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	private static final String REGEX_VIRTUAL_DNS_ENTRY = "(%)(.)*(%)";  //$NON-NLS-1$
	private static final Pattern VIRTUAL_DNS_PATTERN = Pattern.compile(REGEX_VIRTUAL_DNS_ENTRY);
	private static final String REGEX_TYPE_PREFIX = "([a-zA-Z]+#)?(\\S+)"; //$NON-NLS-1$
	private static final Pattern TYPE_PREFIX_PATTERN = Pattern.compile(REGEX_TYPE_PREFIX);

	private static final String TIME_SHORT_FORM = "T"; //$NON-NLS-1$
	private static final String LONG_TIME_SHORT_FORM = "LT"; //$NON-NLS-1$
	private static final String DATE_AND_TIME_SHORT_FORM = "DT"; //$NON-NLS-1$

	private static final Map<String, String> SHORT_FORM_TRANSLATIONS = Map
			.ofEntries(entry(TIME_SHORT_FORM, FordiacKeywords.TIME), entry(LONG_TIME_SHORT_FORM, FordiacKeywords.LTIME),
					entry(FordiacKeywords.TOD, FordiacKeywords.TIME_OF_DAY),
					entry(FordiacKeywords.LTOD, FordiacKeywords.LTIME_OF_DAY),
					entry(DATE_AND_TIME_SHORT_FORM, FordiacKeywords.DATE_AND_TIME),
					entry(FordiacKeywords.LDT, FordiacKeywords.LDATE_AND_TIME));


	public static String validateValue(final VarDeclaration varDeclaration) {
		return validateValue(varDeclaration, varDeclaration.getValue().getValue());
	}

	public static String validateValue(final VarDeclaration varDeclaration, final String valueString) {
		if (varDeclaration.isArray()) {
			return validateArray(varDeclaration, valueString);
		}
		return validateValue(varDeclaration.getType(), valueString);
	}

	private static String validateArray(final VarDeclaration varDeclaration, final String valueString) {
		try {
			final ValueConverter<?> valueConverter = getValueConverter(varDeclaration.getType());
			final ArrayValueConverter<?> arrayVC = new ArrayValueConverter<>(valueConverter);
			arrayVC.toValue(valueString);
		} catch (final IllegalArgumentException e) {
			return e.getMessage();
		}
		return EMPTY_STRING;
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
		try {
			if(!checkVirtualDNS(value)) {
				final var matcher = TYPE_PREFIX_PATTERN.matcher(value);
				var valueToCheck = value;
				var typeToCheck = type;
				if (matcher.find() && null != matcher.group(1)) {
					// we have type specifier
					typeToCheck = getLiteralType(matcher.group(1), type);
					valueToCheck = matcher.group(2);
				} else if (IecTypes.GenericTypes.isAnyType(type)) {
					throw new IllegalArgumentException(Messages.VALIDATOR_CONCRETE_TYPE_SPECIFIER_MANDATORY_FOR_ANYS);
				} else if (type instanceof AnyDurationType || type instanceof AnyDateType) {
					throw new IllegalArgumentException(MessageFormat
							.format(Messages.VALIDATOR_DatatypeRequiresTypeSpecifier, type.getName()));
				}
				final ValueConverter<?> valueConverter = getValueConverter(typeToCheck);
				valueConverter.toValue(valueToCheck);
			}
		} catch (final IllegalArgumentException e) {
			return e.getMessage();
		}
		return EMPTY_STRING;
	}

	private static DataType getLiteralType(String typeSpecifier, final DataType type) {
		typeSpecifier = typeSpecifier.replace("#", EMPTY_STRING); //$NON-NLS-1$
		DataType literalType = IecTypes.ElementaryTypes.getTypeByName(typeSpecifier);
		if (null == literalType) {
			literalType = IecTypes.ElementaryTypes.getTypeByName(SHORT_FORM_TRANSLATIONS.get(typeSpecifier));
		}
		if (null == literalType) {
			throw new IllegalArgumentException(Messages.VALIDATOR_UNKNOWN_LITERAL_TYPE);
		}
		if (!literalType.isCompatibleWith(type)) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_LITERAL_TYPE_INCOMPATIBLE_WITH_INPUT_TYPE,
							literalType.getName(), type.getName()));
		}
		return literalType;
	}

	private static ValueConverter<?> getValueConverter(final DataType type) throws IllegalArgumentException {
		final ValueConverter<?> valueConverter = ValueConverterFactory.createValueConverter(type);
		if (valueConverter == null) {
			throw new IllegalArgumentException(Messages.VALIDATOR_TypeNotSupported);
		}
		return valueConverter;
	}

	private static boolean checkVirtualDNS(final String value) throws IllegalArgumentException {
		final var virtualDNSmatcher = VIRTUAL_DNS_PATTERN.matcher(value);
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
				throw new IllegalArgumentException(Messages.VALIDATOR_VIRTUAL_DNS_ILLEGAL_FORMAT);
			}

			if (inbound) {
				throw new IllegalArgumentException(Messages.VALIDATOR_VIRTUAL_DNS_MULTIPLE_BOUNDING_CHARACTERS);
			}

			final boolean single = (counter == 2);
			if (outbound || (!followUp && !single)) {
				throw new IllegalArgumentException(Messages.VALIDATOR_VIRTUAL_DNS_CHARACTERS_OUT_OF_BOUNDS);
			}
			return true;
		}
		return false;
	}

	private ValueValidator() {
		throw new UnsupportedOperationException("helper class ValueValidator should not be instantiated"); //$NON-NLS-1$
	}
}
