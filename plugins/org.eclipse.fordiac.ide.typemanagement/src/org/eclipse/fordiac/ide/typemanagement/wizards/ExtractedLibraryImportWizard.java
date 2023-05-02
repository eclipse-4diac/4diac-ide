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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class ExtractedLibraryImportWizard extends Wizard implements IImportWizard {
	
	private ExtractedLibraryImportWizardPage firstPage;

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Extracted Library Import Wizard"); //NON-NLS-1
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean performFinish() {
		return false;
	}
	
	@Override
	public void addPages() {
		firstPage = new ExtractedLibraryImportWizardPage("Import Extracted Files"); //NON-NLS-1
        addPage(firstPage);
	}

}
