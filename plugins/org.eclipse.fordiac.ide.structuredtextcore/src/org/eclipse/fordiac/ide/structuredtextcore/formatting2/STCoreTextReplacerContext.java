/*******************************************************************************
 * Copyright (c) 2014, 2024 itemis AG (http://www.itemis.eu) and others.
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - copied from TextReplacerContext and adapted for ST editor
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.formatting2;

import org.eclipse.xtext.formatting2.IFormattableDocument;
import org.eclipse.xtext.formatting2.ITextReplacer;
import org.eclipse.xtext.formatting2.ITextReplacerContext;
import org.eclipse.xtext.formatting2.internal.TextReplacerContext;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegion;
import org.eclipse.xtext.formatting2.regionaccess.IHiddenRegionPart;
import org.eclipse.xtext.formatting2.regionaccess.ITextRegionAccess;
import org.eclipse.xtext.formatting2.regionaccess.ITextReplacement;

import com.google.common.base.Preconditions;

@SuppressWarnings("restriction")
public class STCoreTextReplacerContext extends TextReplacerContext {

	private boolean nextReplacerIsChild;

	public STCoreTextReplacerContext(final IFormattableDocument document) {
		this(document, null, 0, null);
	}

	protected STCoreTextReplacerContext(final IFormattableDocument document, final ITextReplacerContext previous,
			final int indentation, final ITextReplacer replacer) {
		super(document, previous, indentation, replacer);
	}

	@Override
	public int getLeadingCharsInLineCount() {
		final ITextRegionAccess access = getDocument().getRequest().getTextRegionAccess();
		int lastOffset = getReplacer().getRegion().getOffset();
		ITextReplacerContext current = this;
		int count = 0;
		while (current != null) {
			for (final ITextReplacement rep : current.getLocalReplacementsReverse()) {
				final int endOffset = rep.getEndOffset();
				if (endOffset > lastOffset) {
					continue;
				}
				final String between = access.textForOffset(endOffset, lastOffset - endOffset);
				final int idx = between.lastIndexOf('\n');
				if (idx >= 0) {
					return count + logicalLength(between.substring(idx + 1));
				}
				count += logicalLength(between);
				final String text = rep.getReplacementText();
				final int idx2 = text.lastIndexOf('\n');
				if (idx2 >= 0) {
					return count + logicalLength(text.substring(idx2 + 1));
				}
				count += logicalLength(text);
				lastOffset = rep.getOffset();
			}
			final ITextReplacer replacer = current.getReplacer();
			if (replacer != null) {
				final int offset = replacer.getRegion().getOffset();
				if (offset < lastOffset) {
					final String text = access.textForOffset(offset, lastOffset - offset);
					final int idx = text.lastIndexOf('\n');
					if (idx >= 0) {
						return count + logicalLength(text.substring(idx + 1));
					}
					count += logicalLength(text);
					lastOffset = offset;
				}
			}
			current = current.getPreviousContext();
		}
		final String rest = access.textForOffset(0, lastOffset);
		final int idx = rest.lastIndexOf('\n');
		if (idx >= 0) {
			return count + logicalLength(rest.substring(idx + 1));
		}
		count += lastOffset;
		return count;
	}

	@Override
	protected boolean isInUndefinedRegion(final ITextReplacement replacement) {
		return switch (getReplacer().getRegion()) {
		case final IHiddenRegionPart hiddenRegionPart -> hiddenRegionPart.getHiddenRegion().isUndefined();
		case final IHiddenRegion hiddenRegion -> hiddenRegion.isUndefined();
		case null, default -> false;
		};
	}

	@Override
	public ITextReplacerContext withDocument(final IFormattableDocument document) {
		if (document == getDocument()) {
			return this;
		}
		final STCoreTextReplacerContext context = new STCoreTextReplacerContext(document, this, getIndentation(), null);
		if (nextReplacerIsChild) {
			context.setNextReplacerIsChild();
		}
		return context;
	}

	@Override
	public ITextReplacerContext withIndentation(final int indentation) {
		if (indentation == getIndentation()) {
			return this;
		}
		return new STCoreTextReplacerContext(getDocument(), this, indentation, null);
	}

	@Override
	public ITextReplacerContext withReplacer(final ITextReplacer replacer) {
		if (replacer == getReplacer()) {
			return this;
		}
		ITextReplacerContext current = this;
		while (current != null) {
			final ITextReplacer lastReplacer = current.getReplacer();
			if (lastReplacer != null) {
				if (nextReplacerIsChild) {
					Preconditions.checkArgument(lastReplacer.getRegion().contains(replacer.getRegion()));
				} else {
					Preconditions
							.checkArgument(lastReplacer.getRegion().getEndOffset() <= replacer.getRegion().getOffset());
				}
				break;
			}
			current = current.getPreviousContext();
		}
		return new STCoreTextReplacerContext(getDocument(), this, getIndentation(), replacer);
	}

	@Override
	public void setNextReplacerIsChild() {
		super.setNextReplacerIsChild();
		nextReplacerIsChild = true;
	}
}
