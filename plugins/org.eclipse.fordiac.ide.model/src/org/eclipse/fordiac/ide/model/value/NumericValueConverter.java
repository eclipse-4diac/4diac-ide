/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.value;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public final class NumericValueConverter implements ValueConverter<Object> {
	public static final NumericValueConverter INSTANCE = new NumericValueConverter();
	public static final NumericValueConverter INSTANCE_BYTE = new NumericValueConverter("16#%02X"); //$NON-NLS-1$
	public static final NumericValueConverter INSTANCE_WORD = new NumericValueConverter("16#%04X"); //$NON-NLS-1$
	public static final NumericValueConverter INSTANCE_DWORD = new NumericValueConverter("16#%08X"); //$NON-NLS-1$
	public static final NumericValueConverter INSTANCE_LWORD = new NumericValueConverter("16#%016X"); //$NON-NLS-1$

	private static final String TRUE = "TRUE"; //$NON-NLS-1$
	private static final String FALSE = "FALSE"; //$NON-NLS-1$
	private static final Pattern DECIMAL = Pattern
			.compile("[+-]?\\d[_\\d]*+(?:\\.\\d[_\\d]*+(?:[eE][+-]?\\d[_\\d]*+)?)?"); //$NON-NLS-1$
	private static final Pattern NON_DECIMAL = Pattern.compile("(\\d++)#(\\p{XDigit}[_\\p{XDigit}]*+)"); //$NON-NLS-1$
	private static final Pattern SCANNER_PATTERN = Pattern
			.compile("\\G(?:TRUE|FALSE|" + NON_DECIMAL + "|" + DECIMAL + ")", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	private final String format;

	private NumericValueConverter() {
		this(null);
	}

	private NumericValueConverter(final String format) {
		this.format = format;
	}

	@Override
	public Object toValue(final String string) throws IllegalArgumentException {
		try {
			if (string.indexOf("__") != -1) { //$NON-NLS-1$
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE, string));
			}
			final Matcher matcher = NON_DECIMAL.matcher(string);
			if (TRUE.equalsIgnoreCase(string)) {
				return Boolean.TRUE;
			}
			if (FALSE.equalsIgnoreCase(string)) {
				return Boolean.FALSE;
			}
			if (matcher.matches()) {
				final var radixString = matcher.group(1);
				final var numberString = matcher.group(2).replace("_", ""); //$NON-NLS-1$ //$NON-NLS-2$
				final var radix = Integer.parseInt(radixString);
				return new BigInteger(numberString, radix);
			}
			if (string.contains(".")) { //$NON-NLS-1$
				return new BigDecimal(string.replace("_", "")); //$NON-NLS-1$ //$NON-NLS-2$
			}
			return new BigInteger(string.replace("_", "")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (final Exception e) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_INVALID_NUMBER_LITERAL, string),
					e);
		}
	}

	@Override
	public Object toValue(final Scanner scanner) throws IllegalArgumentException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString(final Object value) {
		if (format != null) {
			return String.format(format, value);
		}
		return ValueConverter.super.toString(value);
	}

	@Override
	public String toString() {
		if (format != null) {
			return String.format("%s [format=%s]", getClass().getSimpleName(), format); //$NON-NLS-1$
		}
		return getClass().getSimpleName();
	}
}
