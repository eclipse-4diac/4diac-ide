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

public class FormatHandler extends AbstractHandler {

	// This parameter ID must match the parameter declared in plugin.xml
	private static final String PARAM_DELIMITER = "org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.commands.format.delimiter"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {

		// Retrieve the parameter passed from the plugin.xml command definition
		final String delimiter = event.getParameter(PARAM_DELIMITER);
		if (delimiter == null || delimiter.isEmpty()) {
			throw new ExecutionException("Missing delimiter parameter."); //$NON-NLS-1$
		}

		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);
		final ITextEditor textEditor = EditorUtil.getTextEditor(activeEditor);

		if (textEditor != null) {
			final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
			final ISelection selection = textEditor.getSelectionProvider().getSelection();

			if (selection instanceof final ITextSelection textSelection && document != null) {
				try {
					final int offset = textSelection.getOffset();
					final int length = textSelection.getLength();
					final String selectedText = textSelection.getText();

					// Wrap selected text with AsciiDoc delimiter mark-up
					final String replacement = delimiter + selectedText + delimiter;
					document.replace(offset, length, replacement);

					// Keep the text selected (excluding the delimiter)
					textEditor.selectAndReveal(offset + delimiter.length(), length);

				} catch (final BadLocationException e) {
					throw new ExecutionException("Failed to format text with: " + delimiter, e); //$NON-NLS-1$
				}
			}
		}
		return null;
	}

}
