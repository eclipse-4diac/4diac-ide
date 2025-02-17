/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextcore.formatting2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacerContext;

public final class FormatterUtil {

	public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\R"); //$NON-NLS-1$

	public static int getIndendationChars(final ITextReplacerContext context) {
		final String indentation = context.getFormatter().getPreference(FormatterPreferenceKeys.indentation);
		if ("\t".equals(indentation)) { //$NON-NLS-1$
			return context.getFormatter().getPreference(FormatterPreferenceKeys.tabWidth).intValue()
					* context.getIndentation();
		}
		return context.getIndentation();
	}

	public static String wrapLines(final String lines, final int wrapLength, final String lineSeparator) {
		final Matcher matcher = Pattern.compile("(.{0," + wrapLength + "})(?:\\s|$)").matcher(lines); //$NON-NLS-1$ //$NON-NLS-2$
		final StringBuilder result = new StringBuilder(lines.length() + 4); // reserve some space for added line breaks
		int last = 0;
		while (matcher.find()) {
			result.append(lines, last, matcher.start());
			if (matcher.end() > matcher.start()) { // non-empty match
				result.append(lines, matcher.start(1), matcher.end(1));
				result.append(lineSeparator);
			}
			last = matcher.end();
		}
		result.append(lines, last, lines.length());
		return result.toString();
	}

	public static String prefixLines(final String lines, final String prefix, final String lineSeparator) {
		if (lines.isEmpty()) {
			return prefix;
		}
		return lines.lines().map(line -> line.isEmpty() ? prefix + line : prefix + " " + line) //$NON-NLS-1$
				.collect(Collectors.joining(lineSeparator));
	}

	public static void configureCommentWhitespace(final IHiddenRegionFormatting leading,
			final IHiddenRegionFormatting trailing) {
		// transfer indentation increase before comment
		if (trailing.getIndentationDecrease() != null) {
			leading.setIndentationDecrease(merge(leading.getIndentationDecrease(), trailing.getIndentationDecrease()));
			trailing.setIndentationDecrease(null);
		}
		// transfer indentation decrease after comment
		if (leading.getIndentationDecrease() != null) {
			trailing.setIndentationDecrease(merge(leading.getIndentationDecrease(), trailing.getIndentationDecrease()));
			leading.setIndentationDecrease(null);
		}
	}

	private static Integer merge(final Integer value1, final Integer value2) {
		if (value1 != null && value2 != null) {
			return Integer.valueOf(value1.intValue() + value2.intValue());
		}
		return value1 != null ? value1 : value2;
	}

	private FormatterUtil() {
		throw new UnsupportedOperationException();
	}
}
