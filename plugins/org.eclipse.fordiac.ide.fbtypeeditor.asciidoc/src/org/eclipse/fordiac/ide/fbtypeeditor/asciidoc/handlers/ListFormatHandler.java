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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class ListFormatHandler extends AbstractHandler {

	// This parameter ID must match the parameter declared in plugin.xml
	private static final String PARAM_PREFIX = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.commands.list.prefix"; //$NON-NLS-1$
	private static final String WHITE_SPACE = " "; //$NON-NLS-1$
	private static final String NEW_LINE = System.lineSeparator();
	private static final char LINE_FEED = '\n';
	private static final char CARRIAGE_RETURN = '\r';

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		final String prefix = extractPrefixParameter(event);
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final ITextEditor textEditor = EditorUtil.getTextEditor(activeEditor);

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

			final boolean startOfLine = isStartOfLine(document, offset);

			if (length == 0) {
				// Case 1: Nothing selected, insert prefix (with newline if needed)
				final String textToInsert = startOfLine ? prefix : NEW_LINE + prefix;
				document.replace(offset, 0, textToInsert);
				textEditor.selectAndReveal(offset + textToInsert.length(), 0);
			} else {
				// Case 2: Text selected, split lines at beginning of selection. Insert prefix
				// in next line, followed with remaining text
				final String[] lines = selectedText.split("(?<=\n)"); //$NON-NLS-1$
				final StringBuilder replacement = new StringBuilder();

				if (!startOfLine) {
					replacement.append(NEW_LINE);
				}

				for (final String line : lines) {
					replacement.append(prefix).append(line);
				}

				document.replace(offset, length, replacement.toString());
				// Reselect the newly formatted text
				textEditor.selectAndReveal(offset, replacement.length());
			}
		} catch (final BadLocationException e) {
			throw new ExecutionException("Failed to format list", e); //$NON-NLS-1$
		}
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
		// Since Eclipse automatically trims leading and trailing whitespaces in the
		// plugin.xml we need to add it here for a correct prefix
		return rawPrefix + WHITE_SPACE;
	}

	/**
	 * Checks whether the given offset is at the start of a line or document.
	 */
	private static boolean isStartOfLine(final IDocument document, final int offset) throws BadLocationException {
		if (offset == 0) {
			return true;
		}
		final char prevChar = document.getChar(offset - 1);
		return prevChar == LINE_FEED || prevChar == CARRIAGE_RETURN;
	}

}
