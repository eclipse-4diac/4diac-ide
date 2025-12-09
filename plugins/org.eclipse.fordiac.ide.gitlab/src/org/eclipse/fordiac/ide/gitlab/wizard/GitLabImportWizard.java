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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class GitLabImportWizard extends Wizard implements IImportWizard {

	private GitLabImportWizardPage mainPage;
	private GitLabListedLibsPage listOfLibsPage;
	private StructuredSelection selection;

	// What happens after the "Finish" button has been clicked
	@Override
	public boolean performFinish() {
		listOfLibsPage.finish();
		// should be the last thing to be done

		return true;
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle("File Import Wizard"); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
		this.selection = new StructuredSelection(selection.toList());
	}

	@Override
	public void addPages() {
		mainPage = new GitLabImportWizardPage("Import GitLab Files"); //$NON-NLS-1$
		addPage(mainPage);
		listOfLibsPage = new GitLabListedLibsPage("Results", selection); //$NON-NLS-1$
		addPage(listOfLibsPage);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		if (page == mainPage) {
			return listOfLibsPage;
		}
		return null;
	}
}
