/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.emf.common.util.URI;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor;
import org.eclipse.xtext.ide.serializer.IEmfResourceChange;
import org.eclipse.xtext.ide.serializer.ITextDocumentChange;
import org.eclipse.xtext.resource.IGlobalServiceProvider;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.ResourceURIConverter;
import org.eclipse.xtext.ui.util.DisplayRunnableWithResult;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class STCoreChangeConverter extends ChangeConverter {

	public static class Factory extends ChangeConverter.Factory {

		@Inject
		private ResourceURIConverter resourceURIConverter;

		@Inject(optional = true)
		private IWorkbench workbench;

		@Inject
		private IGlobalServiceProvider globalServiceProvider;

		@Override
		public STCoreChangeConverter create(final String name, final Predicate<Change> changeFilter,
				final RefactoringIssueAcceptor issues) {
			return new STCoreChangeConverter(name, changeFilter, issues, resourceURIConverter, workbench,
					globalServiceProvider);
		}
	}

	private final String name;

	private final RefactoringIssueAcceptor issues;

	private final ResourceURIConverter resourceUriConverter;

	private final IWorkbench workbench;

	private final IGlobalServiceProvider globalServiceProvider;

	protected STCoreChangeConverter(final String name, final Predicate<Change> changeFilter,
			final RefactoringIssueAcceptor issues, final ResourceURIConverter uriConverter, final IWorkbench workbench,
			final IGlobalServiceProvider globalServiceProvider) {
		super(name, changeFilter, issues, uriConverter, workbench);
		this.name = name;
		this.issues = issues;
		this.resourceUriConverter = uriConverter;
		this.workbench = workbench;
		this.globalServiceProvider = globalServiceProvider;
	}

	@Override
	protected void _handleReplacements(final IEmfResourceChange change) {
		// ignore pure-EMF changes
		// those are handled via a separate participant
	}

	@Override
	protected void _handleReplacements(final ITextDocumentChange change) {
		if (change.getReplacements().isEmpty()) {
			return;
		}
		final IFile file = resourceUriConverter.toFile(change.getOldURI());
		if (!canWrite(file)) {
			issues.add(RefactoringIssueAcceptor.Severity.FATAL,
					"Affected file '" + file.getFullPath() + "' is read-only"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		checkDerived(file);
		final List<ReplaceEdit> textEdits = change.getReplacements().stream()
				.map(replacement -> new ReplaceEdit(replacement.getOffset(), replacement.getLength(),
						replacement.getReplacementText()))
				.toList();

		final MultiTextEdit textEdit = new MultiTextEdit();
		textEdit.addChildren(textEdits.toArray(new TextEdit[textEdits.size()]));
		final TextChange ltkChange = createChange(change);
		ltkChange.setEdit(textEdit);
		ltkChange.setTextType(change.getOldURI().fileExtension());
		addChange(ltkChange);
	}

	protected TextChange createChange(final ITextDocumentChange change) {
		final IFile file = resourceUriConverter.toFile(change.getOldURI());
		final FileEditorInput editorInput = new FileEditorInput(file);
		final ITextEditor editor = findOpenEditor(editorInput);
		if (editor != null) {
			final IDocumentProvider documentProvider = editor.getDocumentProvider();
			if (documentProvider != null) {
				return new ProviderDocumentChange(change.getOldURI().lastSegment(), editorInput, documentProvider);
			}
		}
		final IDocumentProvider documentProvider = getDocumentProvider(change.getOldURI());
		if (documentProvider != null) {
			return new ProviderDocumentChange(change.getOldURI().lastSegment(), editorInput, documentProvider);
		}
		final TextFileChange textFileChange = new TextFileChange(change.getOldURI().lastSegment(), file);
		textFileChange.setSaveMode(TextFileChange.FORCE_SAVE);
		return textFileChange;
	}

	protected IDocumentProvider getDocumentProvider(final URI resourceURI) {
		return globalServiceProvider.findService(resourceURI, XtextDocumentProvider.class);
	}

	@Override
	protected ITextEditor findOpenEditor(final IFile file) {
		return findOpenEditor(new FileEditorInput(file));
	}

	protected ITextEditor findOpenEditor(final IEditorInput editorInput) {
		if (workbench == null) {
			return null;
		}
		return new DisplayRunnableWithResult<ITextEditor>() {

			@Override
			protected ITextEditor run() throws Exception {
				final IEditorPart editorPart = workbench.getActiveWorkbenchWindow().getActivePage()
						.findEditor(editorInput);
				return Adapters.adapt(editorPart, ITextEditor.class);
			}
		}.syncExec();
	}
}
