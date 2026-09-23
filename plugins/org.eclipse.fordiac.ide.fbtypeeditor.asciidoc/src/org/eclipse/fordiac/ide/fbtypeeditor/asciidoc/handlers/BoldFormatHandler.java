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

import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class BoldFormatHandler extends AbstractHandler {

	private static final String ASTERISK = "*"; //$NON-NLS-1$

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IEditorPart activeEditor = HandlerUtil.getActiveEditor(event);

		// Use helper to resolve the underlying Text Editor if we are inside a
		// MultiPageEditor
		final ITextEditor textEditor = getTextEditor(activeEditor);

		if (textEditor != null) {
			final IDocument document = textEditor.getDocumentProvider().getDocument(textEditor.getEditorInput());
			final ISelection selection = textEditor.getSelectionProvider().getSelection();

			if (selection instanceof final ITextSelection textSelection && document != null) {
				try {
					final int offset = textSelection.getOffset();
					final int length = textSelection.getLength();
					final String selectedText = textSelection.getText();

					// Wrap selected text with AsciiDoc bold markup (*)
					final String replacement = ASTERISK + selectedText + ASTERISK;
					document.replace(offset, length, replacement);

					// Keep the text selected (excluding the asterisks)
					textEditor.selectAndReveal(offset + 1, length);

				} catch (final BadLocationException e) {
					throw new ExecutionException("Failed to format text as bold", e); //$NON-NLS-1$
				}
			}
		}
		return null;
	}

	/**
	 * Safely resolves the underlying ITextEditor from the active editor.
	 */
	private static ITextEditor getTextEditor(final IEditorPart editor) {
		if (editor == null) {
			return null;
		}

		// If it already is a TextEditor, return it directly
		if (editor instanceof final ITextEditor textEditor) {
			return textEditor;
		}

		// Try standard Eclipse adapter (MultiPageEditorPart usually delegates this)
		final ITextEditor adapted = editor.getAdapter(ITextEditor.class);
		if (adapted != null) {
			return adapted;
		}
		// Fallback: Use reflection to access protected getActiveEditor() of
		// MultiPageEditorPart
		if (editor instanceof final MultiPageEditorPart multiPageEditor) {
			try {
				final Method getActiveEditorMethod = MultiPageEditorPart.class.getDeclaredMethod("getActiveEditor"); //$NON-NLS-1$
				// getActiveEditorMethod.setAccessible(true);
				final Object activePage = getActiveEditorMethod.invoke(multiPageEditor);

				if (activePage instanceof final ITextEditor textEditor) {
					return textEditor;
				}
				if (activePage instanceof final IEditorPart editorPart) {
					return editorPart.getAdapter(ITextEditor.class);
				}
			} catch (final Exception e) {
				// Fallback failed, ignore and return null
			}
		}
		return null;
	}
}
