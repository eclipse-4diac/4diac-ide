/*******************************************************************************
 * Copyright (c) 2024, 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Felix Roithmayr - initial API and implementation and/or initial documentation
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.util;

public class StringTransformer implements ITransformer {
	@Override
	public Object transform(final Object obj) {
		if (obj instanceof final String str) {
			return transformString(str);
		}
		return obj;
	}

	private static String transformString(final String str) {
		final StringBuilder result = new StringBuilder();
		int i = 0;

		while (i < str.length()) {
			final char c = str.charAt(i);

			if (Character.isWhitespace(c)) {
				i = skipWhitespace(str, i, result);
				continue;
			}
			switch (c) {
			case '/' -> {
				if (i + 1 < str.length() && str.charAt(i + 1) == '*') {
					i = skipMultiLineComment(str, i, '/');
				} else if (i + 1 < str.length() && str.charAt(i + 1) == '/') {
					i = skipSingleLineComment(str, i);
				} else {
					result.append(c);
				}
			}
			case '(' -> {
				if (i + 1 < str.length() && str.charAt(i + 1) == '*') {
					i = skipMultiLineComment(str, i, ')');
				} else {
					result.append(c);
				}
			}
			case '"' -> i = processStringLiteral(str, i, result, '"');
			case '\'' -> i = processStringLiteral(str, i, result, '\'');

			default -> result.append(c);
			}
			i++;
		}

		return result.toString();
	}

	private static int skipWhitespace(final String str, int i, final StringBuilder result) {
		final boolean startsWithIdentifier = (i > 0 && isIdentifierChar(str.charAt(i - 1)));
		while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
			i++;
		}
		if (i < str.length() && isIdentifierChar(str.charAt(i)) && startsWithIdentifier) {
			// if we have whitespaces between two identifier characters insert a single
			// space
			result.append(' ');
		}
		return i;
	}

	private static int skipMultiLineComment(final String str, int i, final char delimiter) {
		i += 2; // Skip the opening chars
		while (i < str.length() - 1) {
			if (str.charAt(i) == '*' && str.charAt(i + 1) == delimiter) {
				return i + 1;
			}
			i++;
		}
		return i;
	}

	private static int skipSingleLineComment(final String str, int i) {
		i += 2; // Skip the opening chars
		while (i < str.length() && str.charAt(i) != '\n' && str.charAt(i) != '\r') {
			i++;
		}
		return i;
	}

	private static int processStringLiteral(final String str, int i, final StringBuilder result, final char delimiter) {
		result.append(delimiter);
		i++;

		while (i < str.length()) {
			final char c = str.charAt(i);
			result.append(c);

			if (c == delimiter && str.charAt(i - 1) != '$') {
				// we have not a dollar escaped delimiter so we are at the end of the string
				// literal
				return i;
			}
			i++;
		}

		return i;
	}

	private static boolean isIdentifierChar(final char c) {
		return Character.isLetterOrDigit(c) || c == '_';
	}

}
