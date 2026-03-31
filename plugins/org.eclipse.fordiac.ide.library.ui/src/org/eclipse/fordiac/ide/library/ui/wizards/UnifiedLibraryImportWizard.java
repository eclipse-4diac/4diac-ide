/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.library.ui.wizards;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

public class UnifiedLibraryImportWizard extends Wizard implements IImportWizard {

	private UnifiedLibraryImportWizardPage page;
	private IProject selectedProject;

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle("Library Import"); //$NON-NLS-1$
		setNeedsProgressMonitor(true);

		final StructuredSelection sel = new StructuredSelection(selection.toList());
		if (!sel.isEmpty()) {
			final Object first = sel.getFirstElement();
			if (first instanceof final IProject p) {
				selectedProject = p;
			} else if (first instanceof final IFolder f && f.getParent() instanceof final IProject p) {
				selectedProject = p;
			}
		}
	}

	@Override
	public void addPages() {
		page = new UnifiedLibraryImportWizardPage(selectedProject);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		return page.performImport(getContainer()); // runs with progress
	}
}
