/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.value;

import java.text.MessageFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public final class WStringValueConverter implements ValueConverter<String> {
	public static final WStringValueConverter INSTANCE = new WStringValueConverter();

	private static final Pattern SCANNER_PATTERN = Pattern.compile("\"(?:\\$\"|[^\"])*\""); //$NON-NLS-1$

	private WStringValueConverter() {
	}

	@Override
	public String toValue(final String string) throws IllegalArgumentException {
		final int length = string.length();
		// check length
		if (length < 2) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, string));
		}
		// process quotes
		final char quote = string.charAt(0);
		if (quote != '"') {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, string));
		}
		if (string.charAt(length - 1) != '"') {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_UnevenlyQuotedStringLiteral, string));
		}
		// process escapes
		final StringBuilder result = new StringBuilder(length - 2);
		int index = 1;
		while (index < length - 1) {
			final char c = string.charAt(index);
			switch (c) {
			case '$' -> index = unescape(string, index + 1, result);
			case '"' -> throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, string));
			default -> {
				result.append(c);
				index++;
			}
			}
		}
		return result.toString();
	}

	private static int unescape(final CharSequence string, final int index, final StringBuilder result) {
		if (index >= string.length()) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_IllegalEscapeInStringLiteral, string));
		}
		switch (string.charAt(index)) {
		case '$' -> result.append('$');
		case '"' -> result.append('"');
		case 'l', 'L', 'n', 'N' -> result.append('\n');
		case 'p', 'P' -> result.append('\f');
		case 'r', 'R' -> result.append('\r');
		case 't', 'T' -> result.append('\t');
		default -> {
			return unescapeHexValue(string, index, result);
		}
		}
		return index + 1;
	}

	private static int unescapeHexValue(final CharSequence string, final int index, final StringBuilder result) {
		if (index + 4 > string.length()) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_IllegalEscapeInStringLiteral, string));
		}
		final int digit1 = Character.digit(string.charAt(index), 16);
		final int digit2 = Character.digit(string.charAt(index + 1), 16);
		final int digit3 = Character.digit(string.charAt(index + 2), 16);
		final int digit4 = Character.digit(string.charAt(index + 3), 16);
		if (digit1 < 0 || digit2 < 0 || digit3 < 0 || digit4 < 0) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_IllegalEscapeInStringLiteral, string));
		}
		result.append((char) (digit1 << 12 | digit2 << 8 | digit3 << 4 | digit4));
		return index + 4;
	}

	@Override
	public String toValue(final Scanner scanner)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString(final String value) {
		final int length = value.length();
		final var result = new StringBuilder(length + 2);
		// append quote
		result.append('"');
		// append escaped chars
		value.chars().forEachOrdered(c -> escape(c, result));
		// append quote
		result.append('"');
		return result.toString();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	protected static void escape(final int value, final StringBuilder result) {
		switch (value) {
		case '$' -> result.append("$$"); //$NON-NLS-1$
		case '"' -> result.append("$\""); //$NON-NLS-1$
		case '\n' -> result.append("$L"); //$NON-NLS-1$
		case '\f' -> result.append("$P"); //$NON-NLS-1$
		case '\r' -> result.append("$R"); //$NON-NLS-1$
		case '\t' -> result.append("$T"); //$NON-NLS-1$
		default -> {
			if (value >= 0x0020 && value <= 0x007e) {
				result.append((char) value);
			} else {
				escapeHexValue(value, result);
			}
		}
		}
	}

	private static void escapeHexValue(final int value, final StringBuilder result) {
		result.append(String.format("$%04X", Integer.valueOf(value))); //$NON-NLS-1$
	}
}
