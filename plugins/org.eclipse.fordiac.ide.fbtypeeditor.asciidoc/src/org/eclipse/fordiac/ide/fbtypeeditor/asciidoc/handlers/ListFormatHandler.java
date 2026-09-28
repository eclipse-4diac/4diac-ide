/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Andrea Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class ListFormatHandler extends AbstractHandler {

	// This parameter ID must match the parameter declared in plugin.xml
	private static final String PARAM_PREFIX = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.commands.list.prefix"; //$NON-NLS-1$
	private static final String WHITE_SPACE = " "; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final String prefix = extractPrefixParameter(event);

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final ITextEditor textEditor = Adapters.adapt(activeEditor, ITextEditor.class);

		if (textEditor == null) {
			return null;
		}

		final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		final ISelection selection = textEditor.getSelectionProvider().getSelection();

		if (selection instanceof final ITextSelection textSelection && document != null) {
			insertListPrefix(prefix, textEditor, document, textSelection);
		}

		return null;
	}

	private static void insertListPrefix(final String prefix, final ITextEditor textEditor, final IDocument document,
			final ITextSelection textSelection) throws ExecutionException {
		try {
			final int offset = textSelection.getOffset();
			final int length = textSelection.getLength();
			final String selectedText = textSelection.getText();
			final String newLine = TextUtilities.getDefaultLineDelimiter(document);

			// Automatically determine smart formatting gaps before the cursor
			final String precedingDelimiter = getPrecedingDelimiter(document, offset, newLine);

			if (length == 0) {
				// Case 1: Nothing selected, insert prefix (with smart newline spacing)
				final String textToInsert = precedingDelimiter + prefix;
				document.replace(offset, 0, textToInsert);
				textEditor.selectAndReveal(offset + textToInsert.length(), 0);
			} else {
				// Case 2: Text selected and handles cross-platform line endings safely
				final String[] lines = selectedText.split("(?<=\n)|(?<=\r)(?!\n)"); //$NON-NLS-1$
				final StringBuilder replacement = new StringBuilder(precedingDelimiter);

				for (final String line : lines) {
					replacement.append(prefix).append(line);
				}

				replacement.append(getSucceedingDelimiter(document, offset + length, newLine));

				document.replace(offset, length, replacement.toString());
				textEditor.selectAndReveal(offset, replacement.length());
			}
		} catch (final BadLocationException e) {
			throw new ExecutionException("Failed to format list", e); //$NON-NLS-1$
		}
	}

	/**
	 * Detects what layout separator (nothing, single newline, or blank line) is
	 * needed before inserting a list item based on the preceding text.
	 */
	private static String getPrecedingDelimiter(final IDocument document, final int offset, final String newLine)
			throws BadLocationException {
		if (offset == 0) {
			return ""; // Start of document, no separator needed //$NON-NLS-1$
		}

		final int currentLineIndex = document.getLineOfOffset(offset);
		final IRegion currentLineInfo = document.getLineInformation(currentLineIndex);
		final String currentLineText = document.get(currentLineInfo.getOffset(), offset - currentLineInfo.getOffset())
				.trim();

		// If there's text on the current line before the cursor, we must break to a new
		// line
		if (!currentLineText.isEmpty()) {
			return isListLine(currentLineText) ? newLine : newLine + newLine;
		}

		// Cursor is at the start of a line. Inspect the line above it.
		if (currentLineIndex > 0) {
			final IRegion previousLineInfo = document.getLineInformation(currentLineIndex - 1);
			final String previousLineText = document.get(previousLineInfo.getOffset(), previousLineInfo.getLength())
					.trim();

			if (previousLineText.isEmpty() || isListLine(previousLineText)) {
				return ""; // Already separated safely or part of an existing list //$NON-NLS-1$
			}
			// Preceding line has paragraph text, insert a newline to form a blank line
			// separator
			return newLine;
		}

		return ""; //$NON-NLS-1$
	}

	/**
	 * Detects if a trailing blank line is needed after formatting a list selection
	 * to prevent grabbing subsequent paragraphs into the list.
	 */
	private static String getSucceedingDelimiter(final IDocument document, final int endOffset, final String newLine)
			throws BadLocationException {
		if (endOffset >= document.getLength()) {
			return ""; // End of document //$NON-NLS-1$
		}

		final int currentLineIndex = document.getLineOfOffset(endOffset);
		final IRegion lineInfo = document.getLineInformation(currentLineIndex);
		final String remainingLineText = document
				.get(endOffset, lineInfo.getOffset() + lineInfo.getLength() - endOffset).trim();

		// If there is text immediately following the selection on the same line
		if (!remainingLineText.isEmpty()) {
			return newLine + newLine;
		}
		// Check the next line down
		if (currentLineIndex + 1 < document.getNumberOfLines()) {
			final IRegion nextLineInfo = document.getLineInformation(currentLineIndex + 1);
			final String nextLineText = document.get(nextLineInfo.getOffset(), nextLineInfo.getLength()).trim();
			if (!nextLineText.isEmpty() && !isListLine(nextLineText)) {
				return newLine + newLine; // Next line has prose, separate it with a blank line
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Checks whether the given text starts with an AsciiDoc list marker (an
	 * asterisk '*' for unordered lists or a period '.' for ordered lists)
	 */
	private static boolean isListLine(final String text) {
		return text.startsWith("*") || text.startsWith("."); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Extracts and prepares the prefix parameter passed from plugin.xml.
	 */
	private static String extractPrefixParameter(final ExecutionEvent event) throws ExecutionException {
		// Retrieve the parameter passed from the plugin.xml command definition
		final String rawPrefix = event.getParameter(PARAM_PREFIX);
		if (rawPrefix == null || rawPrefix.isEmpty()) {
			throw new ExecutionException("Missing prefix parameter."); //$NON-NLS-1$
		}
		// Since Eclipse automatically trims leading and trailing white spaces in the
		// plugin.xml we need to add it here for a correct prefix
		return rawPrefix + WHITE_SPACE;
	}

}
