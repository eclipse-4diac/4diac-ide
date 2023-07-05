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

import org.eclipse.fordiac.ide.gitlab.Messages;
import org.eclipse.fordiac.ide.gitlab.Package;
import org.eclipse.fordiac.ide.gitlab.Project;
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

public class GitLabListedLibsPage extends WizardPage {

	private ContainerCheckedTreeViewer treeViewer;

	protected GitLabListedLibsPage(final String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription(Messages.GitLab_Available_Packages);
	}

	@Override
	public void createControl(final Composite parent) {
		final Composite container = new Composite(parent, SWT.NONE);
		final GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		container.setLayoutData(gd);

		final GridLayout layout = new GridLayout(1, true);
		container.setLayout(layout);

		treeViewer = new ContainerCheckedTreeViewer(container, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setLinesVisible(true);
		treeViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));

		createColumns(treeViewer);

		treeViewer.addCheckStateListener(event -> setPageComplete(isComplete()));
		treeViewer.expandAll();
		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false);
	}

	private static void createColumns(final TreeViewer viewer) {
		// Projects and packages column
		final TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(500);
		viewerColumn.getColumn().setText(Messages.GitLab_Packages_And_Projects);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof final Project project) {
					return project.getName();
				}
				if (element instanceof final Package pack) {
					return pack.getName();
				}
				if (element instanceof final LeafNode leafNode) {
					return leafNode.getVersion();
				}
				return ""; //$NON-NLS-1$
			}
		});
	}

	// If there is something selected, we can click finish
	private boolean isComplete() {
		return treeViewer.getCheckedElements().length != 0;
	}

	@Override
	public void setVisible(final boolean visible) {
		super.setVisible(visible);
		// Setting the input here insures that we have already connected to GitLab and
		// that the packages are indeed available
		treeViewer.setContentProvider(new GLTreeContentProvider(
				((GitLabImportWizardPage) getPreviousPage()).getDownloadManager().getPackagesAndLeaves()));
		treeViewer.setInput(getProjectAndPackagesMap());
	}

	public boolean finish() {
		try {
			for (final Object o : treeViewer.getCheckedElements()) {
				if (o instanceof final LeafNode leafNode) {
					((GitLabImportWizardPage) getPreviousPage()).getDownloadManager()
							.packageDownloader(leafNode.getProject(), leafNode.getPackage());
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Map<Project, List<Package>> getProjectAndPackagesMap() {
		if (getPreviousPage() instanceof GitLabImportWizardPage && getPreviousPage().isPageComplete()) {
			return ((GitLabImportWizardPage) getPreviousPage()).getDownloadManager().getProjectsAndPackages();
		}
		return new HashMap<>();
	}

}
