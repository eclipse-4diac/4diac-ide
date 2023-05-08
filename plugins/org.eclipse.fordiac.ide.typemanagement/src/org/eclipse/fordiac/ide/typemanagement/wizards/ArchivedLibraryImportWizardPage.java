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
import java.io.IOException;

import org.eclipse.fordiac.ide.typemanagement.librarylinker.ArchivedLibraryImportContentProvider;
import org.eclipse.fordiac.ide.typemanagement.librarylinker.LibraryLinker;
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
	
	private Composite container;
	private TreeViewer viewer;
	private LibraryLinker libraryLinker;
	private File selectedFile;
	private StructuredSelection selection;

	protected ArchivedLibraryImportWizardPage(String pageName, StructuredSelection selection) {
		super(pageName);
		this.selection = selection;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	    container.setLayoutData(gd);
	    
        GridLayout layout = new GridLayout(1, true);
        container.setLayout(layout);
        
        viewer = new TreeViewer(container, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        
        viewer.getTree().setHeaderVisible(true);
        viewer.getTree().setLinesVisible(true);
        viewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
        viewer.getTree().addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		TreeItem item = (TreeItem) e.item;
        		if (item.getData() instanceof File file && !file.isDirectory()) {
        			selectedFile = file;
        			setPageComplete(isComplete());
        		}
        	}
		});
       
        createColumns();
        
        // required to avoid an error in the system
        setControl(container);
        setPageComplete(false);
	}
	
	private void createColumns() {
		// Projects and packages column
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(500);
		viewerColumn.getColumn().setText("Directories containing archives");
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof File file) {
					return file.getName();
				}
				return "";
			}
		});

	}
	
	private boolean isComplete() {
    	return !viewer.getStructuredSelection().isEmpty();
    }
	
	@Override
	public void setVisible(boolean visible) {
		libraryLinker = new LibraryLinker();
		viewer.setContentProvider(new ArchivedLibraryImportContentProvider());
        viewer.setInput(libraryLinker.listDirectoriesContainingArchives());
		super.setVisible(visible);
	}
	
	public void unzipAndImportArchive() throws IOException {
		libraryLinker.extractLibrary(selectedFile, selection);
	}
	
}
