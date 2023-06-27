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
package org.eclipse.fordiac.ide.gitlab.wizard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.fordiac.ide.gitlab.treeviewer.GLTreeContentProvider;
import org.eclipse.fordiac.ide.gitlab.treeviewer.LeafNode;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.dialogs.ContainerCheckedTreeViewer;
import org.eclipse.fordiac.ide.gitlab.Project;
import org.eclipse.fordiac.ide.gitlab.Package;

public class GitLabListedLibsPage extends WizardPage {
	
	private ContainerCheckedTreeViewer treeViewer;

	protected GitLabListedLibsPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Available packages in GitLab");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	    container.setLayoutData(gd);
	    
        GridLayout layout = new GridLayout(1, true);
        container.setLayout(layout);
        
        treeViewer = new ContainerCheckedTreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        
        treeViewer.getTree().setHeaderVisible(true);
        treeViewer.getTree().setLinesVisible(true);
        treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
       
        createColumns(treeViewer);
        
        treeViewer.addCheckStateListener(event -> {
        	setPageComplete(isComplete());
        });
        treeViewer.expandAll();
        // required to avoid an error in the system
        setControl(container);
        setPageComplete(false);
	}
	
	private void createColumns(TreeViewer viewer) {
		// Projects and packages column
		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
        viewerColumn.getColumn().setWidth(500);
        viewerColumn.getColumn().setText("Packages and projects");
        viewerColumn.setLabelProvider(new ColumnLabelProvider() {
        	@Override
        	public String getText(Object element) {
        		if (element instanceof Project project) {
        			return project.getName();
        		} else if (element instanceof Package pack) {
        			return pack.getName();
        		} else if (element instanceof LeafNode leafNode) {
        			return leafNode.getVersion();
        		}
        		return "";
        	}
        });
	}
	
	// If there is something selected, we can click finish
	private boolean isComplete() {
		return treeViewer.getCheckedElements().length != 0;
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		// Setting the input here insures that we have already connected to GitLab and that the packages are indeed available
		treeViewer.setContentProvider(new GLTreeContentProvider(((GitLabImportWizardPage)getPreviousPage())
				.getDownloadManager().getPackagesAndLeaves()));
		treeViewer.setInput(getProjectAndPackagesMap());
	}
	
	public boolean finish() {
		try {
			for (Object o: treeViewer.getCheckedElements()) {
				if (o instanceof LeafNode leafNode) {
					((GitLabImportWizardPage) getPreviousPage()).getDownloadManager()
					.packageDownloader(leafNode.getProject(), leafNode.getPackage());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	private Map<Project, List<Package>> getProjectAndPackagesMap() {
		if (getPreviousPage() instanceof GitLabImportWizardPage && getPreviousPage().isPageComplete()) {
				return ((GitLabImportWizardPage)getPreviousPage()).getDownloadManager().getProjectsAndPackages();
		}
		return new HashMap<>(); 
	}

}
