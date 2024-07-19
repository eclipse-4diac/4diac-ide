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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.library.LibraryManager;
import org.eclipse.fordiac.ide.library.ui.Messages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ExtractedLibraryImportWizard extends Wizard implements IImportWizard {

	private LibrarySelectionPage firstPage;
	private IProject selectedProject;

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle(Messages.ExtractedLibraryImportWizard);
		setNeedsProgressMonitor(true);
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

	@Override
	public boolean performFinish() {
		LibraryManager.INSTANCE.importLibraries(selectedProject, firstPage.getChosenLibraries().values());
		return true;
	}

	@Override
	public void addPages() {
		firstPage = new LibrarySelectionPage(Messages.ImportExtractedFiles, false, false, true, true);
		addPage(firstPage);
	}
}
