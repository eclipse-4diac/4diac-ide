/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.asciidoc.editors;

import java.io.ByteArrayInputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.typeeditor.ITypeEditorPage;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.mylyn.internal.wikitext.ui.editor.MarkupEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

public class AsciiDocDocumentationEditor extends MarkupEditor implements ITypeEditorPage {

	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, getDocumentationEditorInput(input));
		setPartName("AsciiDoc Description");
		setTitleImage(FordiacImage.ICON_DOCUMENTATION_EDITOR.getImage());
	}

	private IEditorInput getDocumentationEditorInput(final IEditorInput input) {
		if (input instanceof final IFileEditorInput fileEI) {
			IFolder assetsFolder;
			try {
				assetsFolder = getAssetsFolder(fileEI.getFile());
				final IFile docFile = assetsFolder.getFile("type.adoc"); //$NON-NLS-1$
				if (!docFile.exists()) {
					createNewDocFile(docFile);
				}
				return new FileEditorInput(docFile);
			} catch (final CoreException e) {
				FordiacLogHelper.logError("Cann not get ascii doc file!", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	private static IFolder getAssetsFolder(final IFile file) throws CoreException {
		final IContainer parent = file.getParent();

		final String assetFolderName = "." + file.getName() + ".assets"; //$NON-NLS-1$ //$NON-NLS-2$
		final IFolder assetFolder = parent.getFolder(new Path(assetFolderName));
		if (!assetFolder.exists()) {
			assetFolder.create(true, true, new NullProgressMonitor());
		}
		return assetFolder;
	}

	@Override
	public void doSave(final IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		super.doSave(progressMonitor);
	}

	@Override
	protected void performSave(final boolean overwrite, final IProgressMonitor progressMonitor) {
		// TODO Auto-generated method stub
		super.performSave(overwrite, progressMonitor);
	}

	private static void createNewDocFile(final IFile docFile) throws CoreException {
		// for now create an empty file, here could be the place to inject a doc
		// template
		final ByteArrayInputStream emptyStream = new ByteArrayInputStream(new byte[0]);
		docFile.create(emptyStream, true, null);
	}

	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean outlineSelectionChanged(final Object selectedElement) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMarkerTarget(final IMarker marker) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reloadType() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getSelectableObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
