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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

public class ArchivedLibraryImportWizardPage extends WizardPage {

	protected TreeViewer viewer;
	private Path selectedPath;
	private IProject selectedProject;

	protected ArchivedLibraryImportWizardPage(final String pageName, final StructuredSelection selection) {
		super(pageName);
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
		LibraryManager.INSTANCE.extractLibrary(selectedPath, selectedProject, true);
	}

	protected void configureSelectionListener() {
		viewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				final TreeItem item = (TreeItem) e.item;
				if (item.getData() instanceof final Path path && !Files.isDirectory(path)) {
					selectedPath = path;
					setPageComplete(isComplete());
				}
			}
		});
	}

	@Override
	public void setVisible(final boolean visible) {
		viewer.setContentProvider(new ArchivedLibraryImportContentProvider());
		viewer.setInput(LibraryManager.INSTANCE.listDirectoriesContainingArchives());
		super.setVisible(visible);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);

		final GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		container.setLayoutData(gd);

		final GridLayout layout = new GridLayout(1, true);
		container.setLayout(layout);

		viewer = new TreeViewer(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		viewer.getTree().setHeaderVisible(true);
		viewer.getTree().setLinesVisible(true);
		viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));

		configureSelectionListener();

		createColumns();

		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false);
	}

	private void createColumns() {
		// Projects and packages column
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(500);
		viewerColumn.getColumn().setText(Messages.DirsWithArchives);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Path path) {
					return path.getFileName().toString();
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	protected boolean isComplete() {
		return !viewer.getStructuredSelection().isEmpty();
	}
}
