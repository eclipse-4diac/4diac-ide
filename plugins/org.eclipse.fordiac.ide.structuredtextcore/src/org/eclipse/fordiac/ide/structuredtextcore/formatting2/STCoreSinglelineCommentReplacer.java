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

import org.eclipse.xtext.formatting2.FormatterPreferenceKeys;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.SinglelineCommentReplacer;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IComment;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegionPart;

@SuppressWarnings({ "restriction", "nls" })
public class STCoreSinglelineCommentReplacer extends SinglelineCommentReplacer {

	protected STCoreSinglelineCommentReplacer(final IComment comment, final String prefix) {
		super(comment, prefix);
	}

	@Override
	public void configureWhitespace(final WhitespaceReplacer leading, final WhitespaceReplacer trailing) {
		FormatterUtil.configureCommentWhitespace(leading.getFormatting(), trailing.getFormatting());
		leading.getFormatting().setSpace(" ");
	}

	@Override
	public ITextReplacerContext createReplacements(final ITextReplacerContext context) {
		final String lineSeparator = context.getFormatter().getPreference(FormatterPreferenceKeys.lineSeparator);
		final Integer maxCommentWidth = context.getFormatter()
				.getPreference(STCoreFormatterPreferenceKeys.maxCommentWidth);
		final int consecutiveIndendationChars = Math.max(0, getConsecutiveIndendationChars(context));
		final String consecutiveIndendation = " ".repeat(consecutiveIndendationChars);
		final int lengthBeforeComment = context.getLeadingCharsInLineCount() + consecutiveIndendationChars;
		final String indendationString = context.getIndentationString()
				+ " ".repeat(Math.max(0, lengthBeforeComment - FormatterUtil.getIndendationChars(context)));
		final int commentLineLength = maxCommentWidth.intValue() - getPrefix().length() - 1;
		if (commentLineLength < 1) {
			return context;
		}
		final String text = getComment().getText();
		// strip prefix
		final String body = text.substring(getPrefix().length());
		// strip whitespace
		final String stripped = body.strip();
		// apply word wrap
		final String wrapped = FormatterUtil.wrapLines(stripped, commentLineLength, lineSeparator);
		// add prefixes
		final String prefixed = FormatterUtil.prefixLines(wrapped, indendationString + getPrefix(), lineSeparator);
		// strip and enforce trailing newline
		final String replacement = consecutiveIndendation + prefixed.strip() + lineSeparator;
		if (!Objects.equals(text, replacement)) {
			context.addReplacement(getComment().replaceWith(replacement));
		}
		return context;
	}

	protected int getConsecutiveIndendationChars(final ITextReplacerContext context) {
		final ITextReplacerContext first = getFirstSinglelineCommentReplacerContext(context);
		if (first != null && first.getReplacer() != this) {
			return first.getLeadingCharsInLineCount() - FormatterUtil.getIndendationChars(first);
		}
		return 0;
	}

	protected ITextReplacerContext getFirstSinglelineCommentReplacerContext(ITextReplacerContext context) {
		ITextReplacerContext result = null;
		while (isSameHiddenRegion(context)) {
			if (context.getReplacer() instanceof SinglelineCommentReplacer) {
				result = context;
			}
			if (context.getReplacer() instanceof final WhitespaceReplacer replacer
					&& replacer.getRegion().isMultiline()) {
				break; // stop at empty lines
			}
			context = context.getPreviousContext();
		}
		return result;
	}

	protected boolean isSameHiddenRegion(final ITextReplacerContext context) {
		return context.getReplacer() != null
				&& context.getReplacer().getRegion() instanceof final IHiddenRegionPart part
				&& part.getHiddenRegion() == getComment().getHiddenRegion();
	}
}