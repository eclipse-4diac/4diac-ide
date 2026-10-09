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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class HeadingHandler extends AbstractHandler {

	private static final String PARAM_LEVEL = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.commands.heading.level"; //$NON-NLS-1$
	private static final String WHITE_SPACE = " "; //$NON-NLS-1$
	private static final char TITLE_CHAR = '=';
	private static final String TITLE_PREFIX = String.valueOf(TITLE_CHAR);

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		// Retrieve the parameter passed from the plugin.xml command definition
		final String levelPrefix = event.getParameter(PARAM_LEVEL);
		if (levelPrefix == null || levelPrefix.isEmpty()) {
			throw new ExecutionException("Missing heading level parameter."); //$NON-NLS-1$
		}

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final ITextEditor textEditor = Adapters.adapt(activeEditor, ITextEditor.class);

		if (textEditor == null) {
			return null;
		}

		final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
		final ISelection selection = textEditor.getSelectionProvider().getSelection();

		if (selection instanceof final ITextSelection textSelection && document != null) {
			try {
				final int offset = textSelection.getOffset();
				final int lineNum = document.getLineOfOffset(offset);
				final int lineStartOffset = document.getLineOffset(lineNum);

				if (TITLE_PREFIX.equals(levelPrefix) && lineNum != 0) {
					MessageDialog.openWarning(HandlerUtil.getActiveShell(event),
							"Invalid Position or Title Already Exists", //$NON-NLS-1$
							"The document title must only be placed once at the beginning of the document."); //$NON-NLS-1$
					return null;
				}

				final int lineLength = document.getLineLength(lineNum);
				final String lineText = document.get(lineStartOffset, lineLength);
				final int existingPrefixLength = getExistingHeadingPrefixLength(lineText);

				final String prefix = levelPrefix + WHITE_SPACE;

				// Replace existing heading prefix if present, otherwise insert the new one
				document.replace(lineStartOffset, existingPrefixLength, prefix);

				// Adjust selection offset safely to account for prefix length changes
				final int netShift = prefix.length() - existingPrefixLength;
				final int newOffset = Math.max(lineStartOffset + prefix.length(), offset + netShift);
				textEditor.selectAndReveal(newOffset, textSelection.getLength());

			} catch (final BadLocationException e) {
				throw new ExecutionException("Failed to insert heading prefix", e); //$NON-NLS-1$
			}
		}

		return null;
	}

	/**
	 * Identifies the character length of any existing AsciiDoc heading or title
	 * prefix (e.g., "== " or "= ") at the start of the line text.
	 */
	private static int getExistingHeadingPrefixLength(final String lineText) {
		int i = 0;
		while (i < lineText.length() && lineText.charAt(i) == TITLE_CHAR) {
			i++;
		}
		// An AsciiDoc heading prefix must be followed by at least one whitespace
		// character
		if (i > 0 && i < lineText.length() && Character.isWhitespace(lineText.charAt(i))) {
			// Consume any additional whitespaces to clean up formatting completely
			while (i < lineText.length() && Character.isWhitespace(lineText.charAt(i))) {
				i++;
			}
			return i;
		}
		return 0;
	}
}
