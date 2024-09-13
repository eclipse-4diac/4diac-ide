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

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public abstract class AbstractStringValueConverter implements ValueConverter<String> {
	static final Pattern STRING_ESCAPE_PATTERN = Pattern.compile("\\$([\\$'\"lLnNpPrRtT]|\\p{XDigit}{2})?"); //$NON-NLS-1$
	static final Pattern WSTRING_ESCAPE_PATTERN = Pattern.compile("\\$([\\$'\"lLnNpPrRtT]|\\p{XDigit}{4})?"); //$NON-NLS-1$

	static final Pattern SCANNER_STRING_QUOTE_PATTERN = Pattern.compile("\\G'"); //$NON-NLS-1$
	static final Pattern SCANNER_WSTRING_QUOTE_PATTERN = Pattern.compile("\\G\""); //$NON-NLS-1$
	static final Pattern SCANNER_STRING_ESCAPE_PATTERN = Pattern.compile("\\G" + STRING_ESCAPE_PATTERN); //$NON-NLS-1$
	static final Pattern SCANNER_WSTRING_ESCAPE_PATTERN = Pattern.compile("\\G" + WSTRING_ESCAPE_PATTERN); //$NON-NLS-1$
	static final Pattern SCANNER_STRING_NON_ESCAPE_PATTERN = Pattern.compile("[^$']++"); //$NON-NLS-1$
	static final Pattern SCANNER_WSTRING_NON_ESCAPE_PATTERN = Pattern.compile("[^$\"]++"); //$NON-NLS-1$

	@Override
	public String toValue(final String string) throws IllegalArgumentException {
		// check length
		if (string.length() < 2) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, string));
		}
		// process quotes
		final char quote = string.charAt(0);
		if (quote != '\'' && quote != '"') {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, string));
		}
		if (quote != string.charAt(string.length() - 1)) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_UnevenlyQuotedStringLiteral, string));
		}
		final boolean wide = quote == '"';
		final String unquoted = string.substring(1, string.length() - 1);
		// process escapes
		final Pattern pattern = wide ? WSTRING_ESCAPE_PATTERN : STRING_ESCAPE_PATTERN;
		return pattern.matcher(unquoted).replaceAll(StringValueConverter::unescapeReplace);
	}

	@Override
	public String toValue(final Scanner scanner) throws IllegalArgumentException {
		if (scanner.findWithinHorizon(SCANNER_STRING_QUOTE_PATTERN, 0) != null) {
			return toValue(scanner, SCANNER_STRING_QUOTE_PATTERN, SCANNER_STRING_ESCAPE_PATTERN,
					SCANNER_STRING_NON_ESCAPE_PATTERN);
		}
		if (scanner.findWithinHorizon(SCANNER_WSTRING_QUOTE_PATTERN, 0) != null) {
			return toValue(scanner, SCANNER_WSTRING_QUOTE_PATTERN, SCANNER_WSTRING_ESCAPE_PATTERN,
					SCANNER_WSTRING_NON_ESCAPE_PATTERN);
		}
		throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, "<scanner>")); //$NON-NLS-1$
	}

	protected static String toValue(final Scanner scanner, final Pattern quotePattern, final Pattern escapePattern,
			final Pattern nonEscapePattern) throws IllegalArgumentException {
		final StringBuilder result = new StringBuilder();
		while (scanner.findWithinHorizon(quotePattern, 0) == null) {
			if (scanner.findWithinHorizon(escapePattern, 0) != null) {
				result.append(unescape(scanner.match()));
			} else if (scanner.findWithinHorizon(nonEscapePattern, 0) != null) {
				result.append(scanner.match().group());
			} else {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.VALIDATOR_IllegalStringLiteral, "<scanner>")); //$NON-NLS-1$
			}
		}
		return result.toString();
	}

	protected static String unescapeReplace(final MatchResult result) {
		final String replacement = unescape(result);
		if ("$".equals(replacement)) { //$NON-NLS-1$
			return "\\$"; //$NON-NLS-1$
		}
		return replacement;
	}

	protected static String unescape(final MatchResult result) {
		final String escape = result.group(1);
		if (escape == null) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_IllegalEscapeInStringLiteral, escape));
		}
		switch (escape.charAt(0)) {
		case '$':
			return "$"; //$NON-NLS-1$
		case '\'':
			return "'"; //$NON-NLS-1$
		case 'l', 'L', 'n', 'N':
			return "\n"; //$NON-NLS-1$
		case 'p', 'P':
			return "\f"; //$NON-NLS-1$
		case 'r', 'R':
			return "\r"; //$NON-NLS-1$
		case 't', 'T':
			return "\t"; //$NON-NLS-1$
		case '"':
			return "\""; //$NON-NLS-1$
		default: // hex escape
			try {
				return String.valueOf((char) Integer.parseInt(escape, 16));
			} catch (final NumberFormatException e) {
				throw new IllegalArgumentException(
						MessageFormat.format(Messages.VALIDATOR_IllegalEscapeInStringLiteral, escape), e);
			}
		}
	}

	public static String toString(final String value, final boolean wide) {
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
			if (value >= 0x0020 && value <= 0x007e) {
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

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
