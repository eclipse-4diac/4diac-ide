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
import org.eclipse.xtext.ui.editor.XtextEditor;

public class ExtractMethodRefactoring extends ExtractCallableRefactoring {

	@Override
	public void initialize(final XtextEditor editor, final ITextSelection selection) {
		super.initialize(editor, selection);
		setCallableType("METHOD"); //$NON-NLS-1$
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final ProviderDocumentChange change = new ProviderDocumentChange(getName(),
				(IFileEditorInput) getEditor().getEditorInput(), getEditor().getDocumentProvider());
		change.setEdit(createTextEdit());
		change.setTextType("stalg"); //$NON-NLS-1$
		return change;
	}
}
