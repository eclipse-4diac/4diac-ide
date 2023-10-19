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
package org.eclipse.fordiac.ide.structuredtextcore.ui.editor;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.resource.FileExtensionProvider;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Inject;

public class STCoreURIEditorOpener extends LanguageSpecificURIEditorOpener {

	private static final Logger logger = Logger.getLogger(LanguageSpecificURIEditorOpener.class);

	@Inject
	private IStorage2UriMapper mapper;

	@Inject(optional = true)
	private IWorkbench workbench;

	@Inject
	private FileExtensionProvider fileExtensionProvider;

	@Override
	public IEditorPart open(final URI uri, final EReference crossReference, final int indexInList,
			final boolean select) {
		final Iterator<Pair<IStorage, IProject>> storages = mapper.getStorages(uri.trimFragment()).iterator();
		if (storages != null && storages.hasNext()) {
			try {
				final IStorage storage = storages.next().getFirst();
				final IEditorInput editorInput = EditorUtils.createEditorInput(storage);
				final IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
				final IEditorPart editor = IDE.openEditor(activePage, editorInput, getEditorId(uri), select);
				selectAndReveal(editor, uri, crossReference, indexInList, select);
				return EditorUtils.getXtextEditor(editor);
			} catch (final WrappedException e) {
				logger.error("Error while opening editor part for EMF URI '" + uri + "'", e.getCause()); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (final PartInitException partInitException) {
				logger.error("Error while opening editor part for EMF URI '" + uri + "'", partInitException); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return null;
	}

	protected String getEditorId(final URI uri) {
		if (uri.fileExtension() == null
				|| fileExtensionProvider.getPrimaryFileExtension().equals(uri.fileExtension())) {
			return super.getEditorId();
		}
		return "org.eclipse.fordiac.ide.fbtypeeditor.editors.FBTypeEditor"; //$NON-NLS-1$
	}
}
