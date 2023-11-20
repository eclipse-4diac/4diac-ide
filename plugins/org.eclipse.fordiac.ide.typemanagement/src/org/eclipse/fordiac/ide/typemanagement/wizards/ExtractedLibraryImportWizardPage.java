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
package org.eclipse.fordiac.ide.typemanagement.wizards;

import java.io.File;

import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.librarylinker.ExtractedLibraryImportContentProvider;
import org.eclipse.fordiac.ide.typemanagement.librarylinker.LibraryLinker;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TreeItem;

public class ExtractedLibraryImportWizardPage extends LibraryImportWizardPage {

	private LibraryLinker libraryLinker;
	private File selectedFile;
	private final StructuredSelection selection;

	protected ExtractedLibraryImportWizardPage(final String pageName, final StructuredSelection selection) {
		super(pageName);
		this.selection = selection;
		setColumnTitle(Messages.DirsWithUnzippedTypeLibs);
	}

	public void importLib() {
		libraryLinker.setSelectedProject(selection);
		libraryLinker.importLibrary(selectedFile.getName());
	}

	@Override
	void configureSelectionListener() {
		viewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final TreeItem item = (TreeItem) e.item;
				if (item.getData() instanceof final File file && file.isDirectory()) {
					selectedFile = file;
					setPageComplete(isComplete());
				}
			}
		});
	}

	@Override
	public void setVisible(final boolean visible) {
		libraryLinker = new LibraryLinker();
		viewer.setContentProvider(new ExtractedLibraryImportContentProvider());
		viewer.setInput(libraryLinker.listExtractedFiles());
		super.setVisible(visible);
	}

}
