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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentRewriteSession;
import org.eclipse.jface.text.DocumentRewriteSessionType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension4;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.internal.core.refactoring.Changes;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.UndoEdit;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;

@SuppressWarnings("restriction")
public class ProviderDocumentUndoChange extends Change {

	private final String name;
	private final IFileEditorInput editorInput;
	private final IDocumentProvider documentProvider;
	private final UndoEdit undoEdit;
	private final boolean doSave;

	public ProviderDocumentUndoChange(final String name, final IFileEditorInput editorInput,
			final IDocumentProvider documentProvider, final UndoEdit undoEdit, final boolean doSave) {
		this.name = name;
		this.editorInput = editorInput;
		this.documentProvider = documentProvider;
		this.undoEdit = undoEdit;
		this.doSave = doSave;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getModifiedElement() {
		return editorInput.getFile();
	}

	@Override
	public Object[] getAffectedObjects() {
		return new Object[] { editorInput.getFile() };
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// empty
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		IDocument document = null;
		try {
			document = acquireDocument();
			final UndoEdit undo = performEdits(document);
			commit(document, pm);
			return createUndoChange(undo);
		} catch (final BadLocationException e) {
			throw Changes.asCoreException(e);
		} catch (final MalformedTreeException e) {
			throw Changes.asCoreException(e);
		} finally {
			releaseDocument();
		}
	}

	protected UndoEdit performEdits(final IDocument document) throws BadLocationException, MalformedTreeException {
		DocumentRewriteSession session = null;
		try {
			if (document instanceof IDocumentExtension4) {
				session = ((IDocumentExtension4) document).startRewriteSession(DocumentRewriteSessionType.UNRESTRICTED);
			}
			return undoEdit.apply(document);
		} finally {
			if (session != null) {
				((IDocumentExtension4) document).stopRewriteSession(session);
			}
		}
	}

	protected IDocument acquireDocument() throws CoreException {
		documentProvider.connect(editorInput);
		final IDocument document = documentProvider.getDocument(editorInput);
		if (document == null) {
			documentProvider.disconnect(editorInput);
		}
		return document;
	}

	protected void commit(final IDocument document, final IProgressMonitor pm) throws CoreException {
		if (doSave) {
			documentProvider.saveDocument(pm, editorInput, document, false);
		}
	}

	protected void releaseDocument() {
		documentProvider.disconnect(editorInput);
	}

	protected Change createUndoChange(final UndoEdit edit) {
		return new ProviderDocumentUndoChange(name, editorInput, documentProvider, edit, doSave);
	}
}
