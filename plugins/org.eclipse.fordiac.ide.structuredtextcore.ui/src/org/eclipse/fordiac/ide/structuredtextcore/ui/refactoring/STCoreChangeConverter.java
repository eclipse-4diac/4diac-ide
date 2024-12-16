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

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.errormarker.FordiacMarkerHelper;
import org.eclipse.fordiac.ide.model.libraryElement.ArraySize;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.Import;
import org.eclipse.fordiac.ide.model.libraryElement.Value;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.structuredtextcore.resource.LibraryElementXtextResource;
import org.eclipse.fordiac.ide.structuredtextcore.resource.STCoreResource;
import org.eclipse.fordiac.ide.structuredtextcore.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AttributeValueChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.DataTypeChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ImportChange;
import org.eclipse.fordiac.ide.typemanagement.refactoring.InitialValueChange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.TextChange;
import org.eclipse.ltk.core.refactoring.TextFileChange;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor;
import org.eclipse.xtext.ide.serializer.IEmfResourceChange;
import org.eclipse.xtext.ide.serializer.ITextDocumentChange;
import org.eclipse.xtext.resource.IGlobalServiceProvider;
import org.eclipse.xtext.ui.MarkerTypes;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;
import org.eclipse.xtext.ui.refactoring2.ChangeConverter;
import org.eclipse.xtext.ui.refactoring2.ResourceURIConverter;
import org.eclipse.xtext.ui.util.DisplayRunnableWithResult;
import org.eclipse.xtext.validation.Issue;

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

	private static final Logger LOG = Logger.getLogger(ChangeConverter.class);

	private final RefactoringIssueAcceptor issues;

	private final ResourceURIConverter resourceUriConverter;

	private final IWorkbench workbench;

	private final IGlobalServiceProvider globalServiceProvider;

	protected STCoreChangeConverter(final String name, final Predicate<Change> changeFilter,
			final RefactoringIssueAcceptor issues, final ResourceURIConverter uriConverter, final IWorkbench workbench,
			final IGlobalServiceProvider globalServiceProvider) {
		super(name, changeFilter, issues, uriConverter, workbench);
		this.issues = issues;
		this.resourceUriConverter = uriConverter;
		this.workbench = workbench;
		this.globalServiceProvider = globalServiceProvider;
	}

	@Override
	protected void _handleReplacements(final IEmfResourceChange change) {
		// ignore pure-EMF changes
		// those are handled via a separate participant
		if (change instanceof final ImportedNamespaceChange importedNamespaceChange) {
			final Import imp = importedNamespaceChange.getImport();
			addChange(new ImportChange(getSourceElementName(imp), LibraryElementXtextResource.getExternalURI(imp),
					importedNamespaceChange.getImportedNamespace()));
		}
	}

	@Override
	protected void _handleReplacements(final ITextDocumentChange change) {
		if (change.getReplacements().isEmpty()) {
			return;
		}
		final IFile file = resourceUriConverter.toFile(change.getOldURI());
		if (!canWrite(file)) {
			issues.add(RefactoringIssueAcceptor.Severity.FATAL,
					MessageFormat.format(Messages.STCoreChangeConverter_ReadOnly, file.getFullPath()));
		}
		checkDerived(file);
		checkErrors(file);
		final List<ReplaceEdit> textEdits = change.getReplacements().stream()
				.map(replacement -> new ReplaceEdit(replacement.getOffset(), replacement.getLength(),
						replacement.getReplacementText()))
				.toList();

		final MultiTextEdit textEdit = new MultiTextEdit();
		textEdit.addChildren(textEdits.toArray(new TextEdit[textEdits.size()]));
		handleReplacements(change, textEdit);
	}

	protected void handleReplacements(final ITextDocumentChange change, final TextEdit textEdit) {
		if (change.getResource() instanceof final STCoreResource coreResource && coreResource.getURI().hasQuery()) {
			final EObject sourceElement = coreResource.getSourceElement();
			final String sourceElementName = getSourceElementName(sourceElement);
			if (sourceElement instanceof final Attribute attribute) {
				addChange(new AttributeValueChange(sourceElementName,
						LibraryElementXtextResource.getExternalURI(attribute),
						getEditedText(attribute.getValue(), textEdit)));
			} else if (sourceElement instanceof final ArraySize arraySize
					&& arraySize.eContainer() instanceof final VarDeclaration varDeclaration) {
				addChange(new DataTypeChange(sourceElementName,
						LibraryElementXtextResource.getExternalURI(varDeclaration),
						getEditedText(varDeclaration.getFullTypeName(), textEdit)));
			} else if (sourceElement instanceof final Value value
					&& value.eContainer() instanceof final VarDeclaration varDeclaration) {
				addChange(new InitialValueChange(sourceElementName,
						LibraryElementXtextResource.getExternalURI(varDeclaration),
						getEditedText(value.getValue(), textEdit)));
			} else {
				issues.add(RefactoringIssueAcceptor.Severity.FATAL,
						"Invalid query in ST resource: " + change.getOldURI().query()); //$NON-NLS-1$
			}
		} else {
			final TextChange ltkChange = createDocumentChange(change);
			ltkChange.setEdit(textEdit);
			ltkChange.setTextType(change.getOldURI().fileExtension());
			addChange(ltkChange);
		}
	}

	protected TextChange createDocumentChange(final ITextDocumentChange change) {
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

	protected static String getSourceElementName(final EObject element) {
		if (element != null) {
			return element.eResource().getURI().lastSegment() + ": " + FordiacMarkerHelper.getLocation(element); //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	protected String getEditedText(final String original, final TextEdit textEdit) {
		final IDocument document = new Document(original);
		try {
			textEdit.apply(document);
		} catch (MalformedTreeException | BadLocationException e) {
			issues.add(RefactoringIssueAcceptor.Severity.FATAL, e.getMessage());
			return null;
		}
		return document.get();
	}

	protected void checkErrors(final IFile file) {
		final IMarker[] markers = findMarkers(file);
		boolean hasSyntaxErrors = false;
		boolean hasLinkingErrors = false;
		for (final IMarker marker : markers) {
			final int severity = marker.getAttribute(IMarker.SEVERITY, -1);
			if (severity != IMarker.SEVERITY_ERROR) {
				continue;
			}
			final String code = marker.getAttribute(Issue.CODE_KEY, null);
			switch (code) {
			case Diagnostic.SYNTAX_DIAGNOSTIC, Diagnostic.SYNTAX_DIAGNOSTIC_WITH_RANGE -> hasSyntaxErrors = true;
			case Diagnostic.LINKING_DIAGNOSTIC -> hasLinkingErrors = true;
			default -> {// ignore
			}
			}
		}
		if (hasSyntaxErrors) {
			issues.add(RefactoringIssueAcceptor.Severity.ERROR,
					MessageFormat.format(Messages.STCoreChangeConverter_SyntaxErrors, file.getFullPath()));
		} else if (hasLinkingErrors) {
			issues.add(RefactoringIssueAcceptor.Severity.WARNING,
					MessageFormat.format(Messages.STCoreChangeConverter_LinkingErrors, file.getFullPath()));
		}
	}

	private IMarker[] findMarkers(final IFile file) {
		try {
			return file.findMarkers(MarkerTypes.ANY_VALIDATION, true, 0);
		} catch (final CoreException e) {
			issues.add(RefactoringIssueAcceptor.Severity.FATAL, "Cannot retrieve markers for " + file.getFullPath(), e, //$NON-NLS-1$
					LOG);
			return new IMarker[0];
		}
	}
}
