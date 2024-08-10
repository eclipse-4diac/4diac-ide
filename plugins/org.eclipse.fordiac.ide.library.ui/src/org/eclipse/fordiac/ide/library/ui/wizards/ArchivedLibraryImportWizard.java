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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ArchivedLibraryImportWizard extends Wizard implements IImportWizard {

	private ArchivedLibraryImportWizardPage firstPage;
	private StructuredSelection selection;

	@Override
	public boolean performFinish() {
		try {
			firstPage.unzipAndImportArchive();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = new StructuredSelection(selection.toList());
		setWindowTitle("Archive Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		firstPage = new ArchivedLibraryImportWizardPage("Import Archived Files", selection); // NON-NLS-1
		addPage(firstPage);
	}

}
