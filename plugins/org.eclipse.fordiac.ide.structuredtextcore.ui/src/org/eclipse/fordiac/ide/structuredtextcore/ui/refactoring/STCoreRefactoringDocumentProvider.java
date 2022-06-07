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

import static org.eclipse.ltk.core.refactoring.RefactoringStatus.ERROR;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorExtension;
import org.eclipse.xtext.resource.IGlobalServiceProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.refactoring.impl.DefaultRefactoringDocumentProvider;
import org.eclipse.xtext.ui.refactoring.impl.IRefactoringDocument;
import org.eclipse.xtext.ui.refactoring.impl.StatusWrapper;
import org.eclipse.xtext.ui.refactoring.ui.RefactoringPreferences;
import org.eclipse.xtext.ui.util.DisplayRunnableWithResult;
import org.eclipse.xtext.util.internal.Nullable;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreRefactoringDocumentProvider extends DefaultRefactoringDocumentProvider {
	@Inject(optional = true)
	@Nullable
	private IWorkbench workbench;

	@Inject
	private RefactoringPreferences preferences;

	@Inject
	private IGlobalServiceProvider globalServiceProvider;

	@Override
	public IRefactoringDocument get(final URI uri, final StatusWrapper status) {
		final URI resourceURI = uri.trimFragment();
		final IFileEditorInput fileEditorInput = getEditorInput(resourceURI, status);
		if (fileEditorInput != null) {
			final IFile file = fileEditorInput.getFile();
			final IPath redirectedPath = getChangeRedirector().getRedirectedPath(file.getFullPath());
			final IFile redirectedFile = file.getWorkspace().getRoot().getFile(redirectedPath);
			if (redirectedFile.equals(file)) {
				if (workbench != null) {
					final ITextEditor editor = new DisplayRunnableWithResult<ITextEditor>() {
						@Override
						protected ITextEditor run() throws Exception {
							final IWorkbenchWindow activeWorkbenchWindow = workbench.getActiveWorkbenchWindow();
							final IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
							final ITextEditor editor = Adapters.adapt(activePage.findEditor(fileEditorInput),
									ITextEditor.class);
							if (editor != null) {
								if (editor instanceof ITextEditorExtension
										&& ((ITextEditorExtension) editor).isEditorInputReadOnly()) {
									status.add(ERROR, "Editor for {0} is read only", fileEditorInput.getName()); //$NON-NLS-1$
								}
								return (editor);
							}
							return null;
						}
					}.syncExec();
					if (editor != null) {
						final IDocument document = editor.getDocumentProvider().getDocument(fileEditorInput);
						if (document != null) {
							return new EditorDocument(resourceURI, editor, document,
									preferences.isSaveAllBeforeRefactoring() || !editor.isDirty());
						}
					}
				}
				final IDocumentProvider documentProvider = getDocumentProvider(resourceURI);
				if (documentProvider != null) {
					return new ProviderDocument(resourceURI, fileEditorInput, documentProvider);
				}
			} else {
				return new RedirectedFileDocument(resourceURI, file, redirectedFile, getEncodingProvider(resourceURI));
			}
		}
		return null;
	}

	protected IDocumentProvider getDocumentProvider(final URI resourceURI) {
		return globalServiceProvider.findService(resourceURI, XtextDocumentProvider.class);
	}

	public static class ProviderDocument extends AbstractRefactoringDocument {

		private final IFileEditorInput editorInput;
		private final IDocumentProvider documentProvider;

		public ProviderDocument(final URI resourceURI, final IFileEditorInput editorInput,
				final IDocumentProvider documentProvider) {
			super(resourceURI);
			this.editorInput = editorInput;
			this.documentProvider = documentProvider;
		}

		public IFileEditorInput getEditorInput() {
			return editorInput;
		}

		public IDocumentProvider getDocumentProvider() {
			return documentProvider;
		}

		@Override
		public Change createChange(final String name, final TextEdit textEdit) {
			final ProviderDocumentChange documentChange = new ProviderDocumentChange(name, editorInput,
					documentProvider);
			documentChange.setEdit(textEdit);
			documentChange.setTextType(getURI().fileExtension());
			return documentChange;
		}

		@Override
		public String getOriginalContents() {
			try {
				documentProvider.connect(editorInput);
				final IDocument document = documentProvider.getDocument(editorInput);
				if (document == null) {
					throw new IllegalStateException("Couldn't acquire document"); //$NON-NLS-1$
				}
				return document.get();
			} catch (final CoreException e) {
				throw new WrappedException(e);
			} finally {
				documentProvider.disconnect(editorInput);
			}
		}
	}
}
