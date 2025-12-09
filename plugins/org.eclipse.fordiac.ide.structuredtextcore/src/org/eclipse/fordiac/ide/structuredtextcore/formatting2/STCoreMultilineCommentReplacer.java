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

import java.util.Objects;
import java.util.regex.Pattern;

import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.MultilineCommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;

@SuppressWarnings({ "restriction", "nls" })
public class STCoreMultilineCommentReplacer extends MultilineCommentReplacer {
	/*
	 * This pattern joins all lines that end and start with a regular word character
	 * (avoiding empty lines and punctuation on either end).
	 */
	private static final Pattern JOIN_PATTERN = Pattern.compile("(?<=\\w)\\h*+\\R\\h*+(?=\\w)");

	private final char prefix;
	private final boolean multiline;

	protected STCoreMultilineCommentReplacer(final IComment comment, final char prefix) {
		super(comment, prefix);
		this.prefix = prefix;
		this.multiline = isMultiline(comment, prefix);
	}

	@Override
	public void configureWhitespace(final WhitespaceReplacer leading, final WhitespaceReplacer trailing) {
		FormatterUtil.configureCommentWhitespace(leading.getFormatting(), trailing.getFormatting());
		if (multiline) {
			enforceNewLine(leading);
			enforceNewLine(trailing);
		} else {
			if (!leading.getRegion().isMultiline()) {
				enforceSingleSpace(leading);
			}
			if (!trailing.getRegion().isMultiline()) {
				enforceSingleSpace(trailing);
			}
		}
	}

	@Override
	public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
		final String lineSeparator = context.getFormatter().getPreference(FormatterPreferenceKeys.lineSeparator);
		final Integer maxCommentWidth = context.getFormatter()
				.getPreference(STCoreFormatterPreferenceKeys.maxCommentWidth);
		final int lengthBeforeComment = context.getLeadingCharsInLineCount();
		final String indendationString;
		final int commentLineLength;
		if (multiline) {
			indendationString = context.getIndentationString();
			commentLineLength = maxCommentWidth.intValue() - 3; // subtract 3 for " * "
		} else {
			indendationString = context.getIndentationString()
					+ " ".repeat(Math.max(0, lengthBeforeComment - FormatterUtil.getIndendationChars(context)));
			commentLineLength = maxCommentWidth.intValue() - 6; // subtract 6 for "(* " and " *)"
		}
		if (commentLineLength < 1) {
			return context;
		}
		final String text = getComment().getText();
		// strip start and end tokens ('(*' and '*)')
		final String body = text.substring(2, text.length() - 2);
		// strip prefix and whitespace
		final String stripped = Pattern
				.compile("^\\h*+(?:" + Pattern.quote(Character.toString(prefix)) + " )?", Pattern.MULTILINE)
				.matcher(body).replaceAll("").strip();
		// join lines
		final String joined = JOIN_PATTERN.matcher(stripped).replaceAll(" ");
		// apply word wrap
		final String wrapped = FormatterUtil.wrapLines(joined, commentLineLength, lineSeparator);
		// add prefixes
		final String prefixed = FormatterUtil.prefixLines(wrapped, indendationString + " " + Character.toString(prefix),
				lineSeparator);
		// add back start and end tokens ('(*' and '*)')
		final String replacement;
		if (multiline) {
			replacement = "(" + Character.toString(prefix) + lineSeparator // add leading '(*'
					+ prefixed.stripTrailing() + lineSeparator // add comment body
					+ indendationString + " " + Character.toString(prefix) + ")"; // add trailing '*)'
		} else { // single-line
			replacement = "(" + prefixed.strip() + " " + Character.toString(prefix) + ")";
		}
		if (!Objects.equals(text, replacement)) {
			context.addReplacement(getComment().replaceWith(replacement));
		}
		return context;
	}

	protected static boolean isMultiline(final IComment comment, final char prefix) {
		return Pattern.compile(Pattern.quote(Character.toString(prefix)) + "*+\\h*+$", Pattern.MULTILINE)
				.matcher(comment.getText().substring(2)).lookingAt();
	}
}