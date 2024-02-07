/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.text.edits.UndoEdit;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

public class ProviderDocumentChange extends TextChange {

	private final IFileEditorInput editorInput;
	private final IDocumentProvider documentProvider;
	private long modificationStamp = -1L;

	public ProviderDocumentChange(final String name, final IFileEditorInput editorInput,
			final IDocumentProvider documentProvider) {
		super(name);
		this.editorInput = editorInput;
		this.documentProvider = documentProvider;
	}

	@Override
	protected IDocument acquireDocument(final IProgressMonitor pm) throws CoreException {
		documentProvider.connect(editorInput);
		final IDocument document = documentProvider.getDocument(editorInput);
		if (document == null) {
			documentProvider.disconnect(editorInput);
		}
		return document;
	}

	@Override
	protected void commit(final IDocument document, final IProgressMonitor pm) throws CoreException {
		documentProvider.saveDocument(pm, editorInput, document, false);
	}

	@Override
	protected void releaseDocument(final IDocument document, final IProgressMonitor pm) throws CoreException {
		documentProvider.disconnect(editorInput);
	}

	@Override
	protected Change createUndoChange(final UndoEdit edit) {
		return new ProviderDocumentUndoChange(getName(), editorInput, documentProvider, edit);
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		modificationStamp = documentProvider.getModificationStamp(editorInput);
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus refactoringStatus = new RefactoringStatus();
		if (modificationStamp != documentProvider.getModificationStamp(editorInput)) {
			refactoringStatus.addFatalError("The content of the document has changed."); //$NON-NLS-1$
		}
		return refactoringStatus;
	}

	@Override
	public Object getModifiedElement() {
		// Return null here to hide the actual resource from callers. This prevents
		// callers from inferring the content type based on the resource file extension
		// (e.g., *.fbt) and subsequently using EMF compare. EMF compare would not work
		// in this case, since we are only creating a text change. This forces callers
		// to rely on the text type (e.g., "stalg") set on the change, which causes them
		// to use the correct compare editor based on Xtext.
		return null;
	}

	@Override
	public Object[] getAffectedObjects() {
		return new Object[] { editorInput.getFile() };
	}
}
