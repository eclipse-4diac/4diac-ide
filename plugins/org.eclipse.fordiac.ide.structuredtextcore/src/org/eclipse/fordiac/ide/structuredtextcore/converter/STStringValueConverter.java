/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.converter;

import java.nio.charset.StandardCharsets;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.structuredtextcore.stcore.STString;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.conversion.impl.AbstractLexerBasedConverter;
import org.eclipse.xtext.nodemodel.INode;

public class STStringValueConverter extends AbstractLexerBasedConverter<STString> {

	private static final Pattern STRING_ESCAPE_PATTERN = Pattern.compile("\\$([\\$'\\\"lLnNpPrRtT]|\\p{XDigit}{2})?"); //$NON-NLS-1$
	private static final Pattern WSTRING_ESCAPE_PATTERN = Pattern.compile("\\$([\\$'\\\"lLnNpPrRtT]|\\p{XDigit}{4})?"); //$NON-NLS-1$

	@Override
	public STString toValue(final String string, final INode node) throws ValueConverterException {
		// check length
		if (string.length() < 2) {
			throw new ValueConverterException("Illegal string literal", node, null); //$NON-NLS-1$
		}
		// process quotes
		final char quote = string.charAt(0);
		if (quote != '\'' && quote != '"') {
			throw new ValueConverterException("Illegal string literal", node, null); //$NON-NLS-1$
		} else if (quote != string.charAt(string.length() - 1)) {
			throw new ValueConverterException("Unevenly quoted string literal", node, null); //$NON-NLS-1$
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
			result = pattern.matcher(unquoted).replaceAll(STStringValueConverter::unescape);
		} catch (final Exception e) {
			throw new ValueConverterException("Illegal string literal", node, e); //$NON-NLS-1$
		}
		return new STString(result, wide);
	}

	protected static String unescape(final MatchResult result) {
		final String escape = result.group(1);
		if (escape == null) {
			throw new ValueConverterException("Illegal escape in string literal", null, null); //$NON-NLS-1$
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
	protected String toEscapedString(final STString value) {
		final int length = value.length();
		final var result = new StringBuilder(length + 2);
		// append quote
		if (value.isWide()) {
			result.append('"');
		} else {
			result.append('\'');
		}
		// append escaped chars
		for (int i = 0; i < length; ++i) {
			escape(value.charAt(i), value.isWide(), result);
		}
		// append quote
		if (value.isWide()) {
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
				result.append(String.format("$%04X", Character.valueOf(value))); //$NON-NLS-1$
			} else {
				final byte[] bytes = String.valueOf(value).getBytes(StandardCharsets.UTF_8);
				for (final byte b : bytes) {
					result.append(String.format("$%02X", Byte.valueOf(b))); //$NON-NLS-1$
				}
			}
		}
	}
}
