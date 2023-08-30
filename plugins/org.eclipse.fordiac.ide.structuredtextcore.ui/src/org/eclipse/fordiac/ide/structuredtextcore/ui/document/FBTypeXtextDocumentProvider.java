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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.xtext.ui.editor.model.XtextDocument;

/** @deprecated Use {@link LibraryElementXtextDocumentProvider} directly */
@Deprecated(forRemoval = true)
public abstract class FBTypeXtextDocumentProvider extends LibraryElementXtextDocumentProvider {

	@Override
	public void doSaveDocument(final IProgressMonitor monitor, final IFileEditorInput fileEditorInput,
			final LibraryElement element, final XtextDocument document) {
		if (element instanceof final FBType fbType) {
			doSaveDocument(monitor, fileEditorInput, fbType, document);
		}
	}

	@Override
	public void setDocumentContent(final IDocument monitor, final LibraryElement element) {
		if (element instanceof final FBType fbType) {
			setDocumentContent(monitor, fbType);
		}
	}

	public abstract void doSaveDocument(IProgressMonitor monitor, IFileEditorInput fileEditorInput, FBType element,
			XtextDocument document);

	public abstract void setDocumentContent(IDocument monitor, FBType element);
}
