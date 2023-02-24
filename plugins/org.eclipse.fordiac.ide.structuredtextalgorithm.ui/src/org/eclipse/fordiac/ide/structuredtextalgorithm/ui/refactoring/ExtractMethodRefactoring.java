/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.structuredtextalgorithm.ui.refactoring;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.ExtractCallableRefactoring;
import org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring.ProviderDocumentChange;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class ExtractMethodRefactoring extends ExtractCallableRefactoring {

	@Override
	public void initialize(final XtextEditor editor, final ITextSelection selection) {
		super.initialize(editor, selection);
		setCallableType("METHOD"); //$NON-NLS-1$
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final ProviderDocumentChange change = new ExtractMethodRefactoringChange(getName(),
				(IFileEditorInput) getEditor().getEditorInput(), getEditor().getDocumentProvider());
		change.setEdit(createTextEdit());
		change.setTextType("stalg"); //$NON-NLS-1$
		return change;
	}

	protected static class ExtractMethodRefactoringChange extends ProviderDocumentChange {

		public ExtractMethodRefactoringChange(final String name, final IFileEditorInput editorInput,
				final IDocumentProvider documentProvider) {
			super(name, editorInput, documentProvider);
		}

		@Override
		public Object getModifiedElement() {
			// Return null here to hide the actual resource from callers. This prevents callers from inferring the
			// content type based on the resource file extension (*.fbt) and subsequently using EMF compare. EMF compare
			// would not work in this case, since we are only creating a text change for the algorithm part. This forces
			// callers to rely on the text type ("stalg") set on the change above, which causes them to use the correct
			// compare editor based on Xtext with ST algorithm as language.
			return null;
		}
	}
}
