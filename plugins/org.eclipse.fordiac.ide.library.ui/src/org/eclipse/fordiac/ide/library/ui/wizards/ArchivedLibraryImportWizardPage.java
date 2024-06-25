/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.library.LibraryLinker;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;

public class ArchivedLibraryImportWizardPage extends LibraryImportWizardPage {

	private LibraryLinker libraryLinker;
	private File selectedFile;
	private IProject selectedProject;

	protected ArchivedLibraryImportWizardPage(final String pageName, final StructuredSelection selection) {
		super(pageName);
		setColumnTitle(Messages.DirsWithArchives);
		final StructuredSelection sel = new StructuredSelection(selection.toList());
		if (!sel.isEmpty()) {
			if (sel.getFirstElement() instanceof final IProject project) {
				selectedProject = project;
			}
			if ((sel.getFirstElement() instanceof final IFolder folder)
					&& (folder.getParent() instanceof final IProject project)) {
				selectedProject = project;
			}
		}
	}

	public void unzipAndImportArchive() throws IOException {
		libraryLinker.extractLibrary(selectedFile, selectedProject, true);
	}

	@Override
	protected void configureSelectionListener() {
		viewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final TreeItem item = (TreeItem) e.item;
				if (item.getData() instanceof final File file && !file.isDirectory()) {
					selectedFile = file;
					setPageComplete(isComplete());
				}
			}
		});
	}

	@Override
	public void setVisible(final boolean visible) {
		libraryLinker = new LibraryLinker();
		viewer.setContentProvider(new ArchivedLibraryImportContentProvider());
		viewer.setInput(libraryLinker.listDirectoriesContainingArchives());
		super.setVisible(visible);
	}

}
