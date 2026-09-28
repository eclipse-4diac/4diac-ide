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

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

public class EditorUtil {

	private EditorUtil() {
		/* This utility class should not be instantiated */
	}

	/**
	 * Safely resolves the underlying ITextEditor from the active editor.
	 */
	static ITextEditor getTextEditor(final IEditorPart editor) {
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

		return editor.getAdapter(ITextEditor.class);
	}

}
