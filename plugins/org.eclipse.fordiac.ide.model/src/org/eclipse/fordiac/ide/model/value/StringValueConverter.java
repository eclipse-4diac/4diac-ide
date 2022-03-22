/**
 * Copyright (c) 2022 Martin Erich Jobst
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

import java.nio.charset.StandardCharsets;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public final class StringValueConverter implements ValueConverter<String> {
	public static final StringValueConverter INSTANCE = new StringValueConverter();

	static final Pattern STRING_ESCAPE_PATTERN = Pattern.compile("\\$([\\$'\\\"lLnNpPrRtT]|\\p{XDigit}{2})?"); //$NON-NLS-1$
	static final Pattern WSTRING_ESCAPE_PATTERN = Pattern.compile("\\$([\\$'\\\"lLnNpPrRtT]|\\p{XDigit}{4})?"); //$NON-NLS-1$

	private StringValueConverter() {
	}

	@Override
	public String toValue(final String string) throws IllegalArgumentException {
		// check length
		if (string.length() < 2) {
			throw new IllegalArgumentException("Illegal string literal", null); //$NON-NLS-1$
		}
		// process quotes
		final char quote = string.charAt(0);
		if (quote != '\'' && quote != '"') {
			throw new IllegalArgumentException("Illegal string literal", null); //$NON-NLS-1$
		} else if (quote != string.charAt(string.length() - 1)) {
			throw new IllegalArgumentException("Unevenly quoted string literal", null); //$NON-NLS-1$
		}
		final boolean wide = quote == '"';
		final String unquoted = string.substring(1, string.length() - 1);
		// process escapes
		Pattern pattern;
		if (wide) {
			pattern = WSTRING_ESCAPE_PATTERN;
		} else {
			pattern = STRING_ESCAPE_PATTERN;
		}
		String result;
		try {
			result = pattern.matcher(unquoted).replaceAll(StringValueConverter::unescape);
		} catch (final Exception e) {
			throw new IllegalArgumentException("Illegal string literal", e); //$NON-NLS-1$
		}
		return result;
	}

	protected static String unescape(final MatchResult result) {
		final String escape = result.group(1);
		if (escape == null) {
			throw new IllegalArgumentException("Illegal escape in string literal"); //$NON-NLS-1$
		}
		switch (escape.charAt(0)) {
		case '$':
			return "$"; //$NON-NLS-1$
		case '\'':
			return "'"; //$NON-NLS-1$
		case 'l':
		case 'L':
		case 'n':
		case 'N':
			return "\n"; //$NON-NLS-1$
		case 'p':
		case 'P':
			return "\f"; //$NON-NLS-1$
		case 'r':
		case 'R':
			return "\r"; //$NON-NLS-1$
		case 't':
		case 'T':
			return "\t"; //$NON-NLS-1$
		case '"':
			return "\""; //$NON-NLS-1$
		default: // hex escape
			return String.valueOf((char) Integer.parseInt(escape, 16));
		}
	}

	@Override
	public String toString(final String value) {
		return toString(value, true);
	}

	@SuppressWarnings("static-method")
	public String toString(final String value, final boolean wide) {
		final int length = value.length();
		final var result = new StringBuilder(length + 2);
		// append quote
		if (wide) {
			result.append('"');
		} else {
			result.append('\'');
		}
		// append escaped chars
		for (int i = 0; i < length; ++i) {
			escape(value.charAt(i), wide, result);
		}
		// append quote
		if (wide) {
			result.append('"');
		} else {
			result.append('\'');
		}
		return result.toString();
	}

	protected static void escape(final char value, final boolean wide, final StringBuilder result) {
		switch (value) {
		case '$':
			result.append("$$"); //$NON-NLS-1$
			break;
		case '\'':
			if (wide) {
				result.append(value); // $NON-NLS-1$
			} else {
				result.append("$'"); //$NON-NLS-1$
			}
			break;
		case '\n':
			result.append("$N"); //$NON-NLS-1$
			break;
		case '\f':
			result.append("$P"); //$NON-NLS-1$
			break;
		case '\r':
			result.append("$R"); //$NON-NLS-1$
			break;
		case '\t':
			result.append("$T"); //$NON-NLS-1$
			break;
		case '"':
			if (!wide) {
				result.append(value); // $NON-NLS-1$
			} else {
				result.append("$\""); //$NON-NLS-1$
			}
			break;
		default:
			if (value > 0x0020 && value < 0x007e) {
				result.append(value);
			} else if (wide) {
				result.append(String.format("$%04X", Integer.valueOf(value))); //$NON-NLS-1$
			} else {
				final byte[] bytes = String.valueOf(value).getBytes(StandardCharsets.UTF_8);
				for (final byte b : bytes) {
					result.append(String.format("$%02X", Byte.valueOf(b))); //$NON-NLS-1$
				}
			}
		}
	}
}
