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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.structuredtextcore.ui.editor.STCoreNestedEditor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.UndoEdit;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class EditorDocumentChange extends ProviderDocumentChange {

	private final IEditorPart editorPart;

	public EditorDocumentChange(final String name, final IEditorPart editorPart,
			final IDocumentProvider documentProvider) {
		super(name, editorPart.getEditorInput(), documentProvider);
		this.editorPart = editorPart;
	}

	public EditorDocumentChange(final String name, final IEditorPart editorPart,
			final IDocumentProvider documentProvider, final boolean doSave) {
		super(name, editorPart.getEditorInput(), documentProvider, doSave);
		this.editorPart = editorPart;
	}

	@Override
	protected void commit(final IDocument document, final IProgressMonitor pm) throws CoreException {
		super.commit(document, pm);
		if (isDoSave() && editorPart instanceof final STCoreNestedEditor nestedEditor) {
			Display.getDefault().syncExec(() -> nestedEditor.doSaveOuterEditor(pm));
		}
	}

	@Override
	protected Change createUndoChange(final UndoEdit edit) {
		return new EditorDocumentUndoChange(getName(), editorPart, getDocumentProvider(), edit, isDoSave());
	}
}
