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
	private static final String TITLE_PREFIX = "="; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		// Retrieve the parameter passed from the plugin.xml command definition
		final String levelPrefix = event.getParameter(PARAM_LEVEL);
		if (levelPrefix == null || levelPrefix.isEmpty()) {
			throw new ExecutionException("Missing heading level parameter."); //$NON-NLS-1$
		}

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final ITextEditor textEditor = EditorUtil.getTextEditor(activeEditor);

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

				if (TITLE_PREFIX.equals(levelPrefix)) {
					// Constraint 1: Must be on the first line (index 0)
					// Constraint 2: Only allowed once. Check if line 0 already has a title.
					final int lineLength = document.getLineLength(0);
					final String firstLineText = document.get(0, lineLength).trim();
					if (lineNum != 0 || isTitleLine(firstLineText)) {
						MessageDialog.openWarning(HandlerUtil.getActiveShell(event),
								"Invalid Position or Title Already Exists", //$NON-NLS-1$
								"The document title must only be placed once at the beginning of the document."); //$NON-NLS-1$
						return null;
					}
				}

				final String prefix = levelPrefix + WHITE_SPACE;

				// Add prefix at beginning of line
				document.replace(lineStartOffset, 0, prefix);

				// Adjust cursor position to keep text selected
				textEditor.selectAndReveal(offset + prefix.length(), textSelection.getLength());

			} catch (final BadLocationException e) {
				throw new ExecutionException("Failed to insert heading prefix", e); //$NON-NLS-1$
			}
		}

		return null;
	}

	/**
	 * Checks if the text starts with '=' (Title) but not '==' (Heading 1).
	 */
	private static boolean isTitleLine(final String lineText) {
		return lineText.startsWith(TITLE_PREFIX) && !lineText.startsWith("=="); //$NON-NLS-1$
	}
}
