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
package org.eclipse.fordiac.ide.globalconstantseditor.ui.document;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.globalconstantseditor.resource.GlobalConstantsResource;
import org.eclipse.fordiac.ide.globalconstantseditor.util.GlobalConstantsReconciler;
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants;
import org.eclipse.fordiac.ide.model.typelibrary.GlobalConstantsEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.eclipse.xtext.ui.editor.model.XtextDocument;
import org.eclipse.xtext.ui.editor.model.XtextDocumentProvider;

import com.google.inject.Inject;

public class GlobalConstantsDocumentProvider extends XtextDocumentProvider {

	@Inject
	private GlobalConstantsDocumentPartitioner partitioner;

	@Override
	protected boolean setDocumentContent(final IDocument document, final IEditorInput editorInput,
			final String encoding) throws CoreException {
		boolean result = false;
		if (editorInput instanceof final IFileEditorInput fileEditorInput) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry instanceof final GlobalConstantsEntry globalConstantsEntry) {
				setDocumentContent(document, globalConstantsEntry.getTypeEditable());
				result = true;

			} else {
				result = super.setDocumentContent(document, editorInput, encoding);
			}
		}
		if (result) {
			setDocumentResource((XtextDocument) document, editorInput, encoding);
		}
		return result;
	}

	protected void setDocumentContent(final IDocument document, final GlobalConstants element) {
		document.set(partitioner.combine(element));
	}

	@Override
	protected void doSaveDocument(final IProgressMonitor monitor, final Object element, final IDocument document,
			final boolean overwrite) throws CoreException {
		if (element instanceof final IFileEditorInput fileEditorInput) {
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry instanceof final GlobalConstantsEntry globalConstantsEntry) {
				doSaveDocument(monitor, fileEditorInput, globalConstantsEntry.getTypeEditable(),
						(XtextDocument) document);
			}
			if (getElementInfo(element) instanceof final FileInfo info) {
				if (info.fModel instanceof final ResourceMarkerAnnotationModel model) {
					model.updateMarkers(info.fDocument);
				}
				info.fModificationStamp = computeModificationStamp(fileEditorInput.getFile());
			}
		}
	}

	protected void doSaveDocument(final IProgressMonitor monitor, final IFileEditorInput fileEditorInput,
			final GlobalConstants element, final XtextDocument document) {
		monitor.beginTask("Saving", 2); //$NON-NLS-1$
		try {
			monitor.subTask("Partitioning"); //$NON-NLS-1$
			final var partition = partitioner.partition(document);
			monitor.worked(1);
			monitor.subTask("Reconciling"); //$NON-NLS-1$
			Display.getDefault().syncExec(() -> {
				GlobalConstantsReconciler.reconcile(element, partition, document.get());
				// save type if opened directly from a file and not in an FB type editor,
				// indicated by a FileEditorInput instead of a FBTypeEditorInput
				if (fileEditorInput instanceof FileEditorInput) {
					element.getTypeEntry().save();
				}
			});
		} catch (final Exception e) {
			Platform.getLog(getClass()).error("Error saving algorithms to FB type", e); //$NON-NLS-1$
		} finally {
			monitor.done();
		}
	}

	@Override
	protected void handleElementContentChanged(final IFileEditorInput fileEditorInput) {
		final FileInfo info = (FileInfo) getElementInfo(fileEditorInput);
		if (info == null) {
			return;
		}
		final XtextDocument document = (XtextDocument) info.fDocument;
		super.handleElementContentChanged(fileEditorInput);
		if (document == info.fDocument) { // still unchanged? -> update FB reference and reparse
			final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(fileEditorInput.getFile());
			if (typeEntry instanceof final GlobalConstantsEntry globalConstantsEntry) {
				removeUnchangedElementListeners(fileEditorInput, info);

				document.internalModify(resource -> {
					if (resource instanceof final GlobalConstantsResource globalConstantsResource) {
						globalConstantsResource.setGlobalConstants(globalConstantsEntry.getTypeEditable());
					}
					return null;
				});

				addUnchangedElementListeners(fileEditorInput, info);
			}
		}
	}
}
