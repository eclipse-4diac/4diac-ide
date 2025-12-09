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

import org.eclipse.xtext.formatting2.IHiddenRegionFormatting;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.WhitespaceReplacer;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.ITextSegment;

@SuppressWarnings("restriction")
public class STCoreWhitespaceReplacer extends WhitespaceReplacer {

	public STCoreWhitespaceReplacer(final ITextSegment whitespace, final IHiddenRegionFormatting formatting) {
		super(whitespace, formatting);
	}

	@Override
	protected int computeNewLineCount(final ITextReplacerContext context) {
		final Integer newLineDefault = getFormatting().getNewLineDefault();
		final Integer newLineMin = getFormatting().getNewLineMin();
		final Integer newLineMax = getFormatting().getNewLineMax();
		if (newLineMin != null || newLineDefault != null || newLineMax != null) {
			if (getRegion() instanceof final IHiddenRegion hiddenRegion && hiddenRegion.isUndefined()) {
				if (newLineDefault != null) {
					return newLineDefault.intValue();
				}
				if (newLineMin != null) {
					return newLineMin.intValue();
				}
				return newLineMax.intValue();
			}
			int lineCount = getRegion().getLineCount() - 1 + trailingNewLinesOfPreviousRegion();
			if (newLineMin != null && newLineMin.intValue() > lineCount) {
				lineCount = newLineMin.intValue();
			}
			if (newLineMax != null && newLineMax.intValue() < lineCount) {
				lineCount = newLineMax.intValue();
			}
			return lineCount;
		}
		return 0;
	}
}
