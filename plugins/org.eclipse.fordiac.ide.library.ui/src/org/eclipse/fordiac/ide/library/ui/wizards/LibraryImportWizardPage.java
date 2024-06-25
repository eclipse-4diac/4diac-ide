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

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public abstract class LibraryImportWizardPage extends WizardPage {

	protected TreeViewer viewer;
	protected String columnTitle;

	protected LibraryImportWizardPage(String pageName) {
		super(pageName);
	}
	
	protected void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}
	
	abstract void configureSelectionListener();
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		container.setLayoutData(gd);

		GridLayout layout = new GridLayout(1, true);
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
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(500);
		viewerColumn.getColumn().setText(columnTitle);
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
	
	protected boolean isComplete() {
    	return !viewer.getStructuredSelection().isEmpty();
    }
	
}
