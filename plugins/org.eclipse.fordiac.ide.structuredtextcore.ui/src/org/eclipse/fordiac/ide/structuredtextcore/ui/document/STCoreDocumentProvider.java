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
package org.eclipse.fordiac.ide.structuredtextcore.ui.document;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.structuredtextcore.util.STCoreReconciler;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.model.XtextDocument;

import com.google.inject.Inject;

public class STCoreDocumentProvider extends LibraryElementXtextDocumentProvider {

	@Inject
	private STCoreDocumentPartitioner partitioner;

	@Inject
	private STCoreReconciler reconciler;

	@Override
	public void setDocumentContent(final IDocument document, final LibraryElement element) {
		document.set(partitioner.combine(element));
	}

	@Override
	public void doSaveDocument(final IProgressMonitor monitor, final IFileEditorInput fileEditorInput,
			final LibraryElement element, final XtextDocument document) throws CoreException {
		monitor.beginTask("Saving", 2); //$NON-NLS-1$
		try {
			monitor.subTask("Partitioning"); //$NON-NLS-1$
			final var partition = partitioner.partition(document);
			monitor.worked(1);
			monitor.subTask("Reconciling"); //$NON-NLS-1$
			if (partition.isPresent()) {
				Display.getDefault().syncExec(() -> {
					reconciler.reconcile(element, partition);
					// save type if opened directly from a file and not in an FB type editor,
					// indicated by a FileEditorInput instead of a TypeEditorInput
					if (fileEditorInput instanceof FileEditorInput) {
						try {
							element.getTypeEntry().save(element, monitor);
						} catch (final CoreException e) {
							throw new RuntimeException(e);
						}
					}
				});
			}
		} finally {
			monitor.done();
		}
	}
}
