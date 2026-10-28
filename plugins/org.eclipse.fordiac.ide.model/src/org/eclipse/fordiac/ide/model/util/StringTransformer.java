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
					i = skipMultiLineComment(str, i, '/', result);
				} else if (i + 1 < str.length() && str.charAt(i + 1) == '/') {
					i = skipSingleLineComment(str, i, result);
				} else {
					result.append(c);
				}
			}
			case '(' -> {
				if (i + 1 < str.length() && str.charAt(i + 1) == '*') {
					i = skipMultiLineComment(str, i, ')', result);
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
		while (i < str.length() && Character.isWhitespace(str.charAt(i))) {
			i++;
		}
		appendSpaceBetweenIdentifiersIfNeeded(str, i, result);
		return i;
	}

	private static int skipMultiLineComment(final String str, int i, final char delimiter, final StringBuilder result) {
		i += 2; // Skip the opening chars
		while (i < str.length() - 1 && (str.charAt(i) != '*' || str.charAt(i + 1) != delimiter)) {
			i++;
		}
		appendSpaceBetweenIdentifiersIfNeeded(str, i + 2, result);
		return i + 1;
	}

	private static int skipSingleLineComment(final String str, int i, final StringBuilder result) {
		i += 2; // Skip the opening chars
		while (i < str.length() && str.charAt(i) != '\n' && str.charAt(i) != '\r') {
			i++;
		}
		appendSpaceBetweenIdentifiersIfNeeded(str, i + 1, result);
		return i;
	}

	private static void appendSpaceBetweenIdentifiersIfNeeded(final String str, final int i,
			final StringBuilder result) {
		if (i < str.length() && isIdentifierChar(str.charAt(i)) && !result.isEmpty()
				&& isIdentifierChar(result.charAt(result.length() - 1))) {
			// if we have whitespaces between two identifier characters insert a single
			// space
			result.append(' ');
		}
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
