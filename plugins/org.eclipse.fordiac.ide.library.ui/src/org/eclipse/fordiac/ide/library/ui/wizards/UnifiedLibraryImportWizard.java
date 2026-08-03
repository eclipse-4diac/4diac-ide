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
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class UnifiedLibraryImportWizard extends Wizard implements IImportWizard {

	private UnifiedLibraryImportWizardPage page;
	private IProject targetProject;

	public static int openDialog(final IProject project, final Shell shell) {
		if (project != null) {
			final UnifiedLibraryImportWizard wizard = new UnifiedLibraryImportWizard();
			wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(project));
			final WizardDialog dialog = new WizardDialog(shell, wizard);
			dialog.setBlockOnOpen(true);
			dialog.create();
			return dialog.open();
		}
		return Window.CANCEL;
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		setWindowTitle("Library Import"); //$NON-NLS-1$
		setNeedsProgressMonitor(true);

		final IProject project = getProject(selection);
		targetProject = SystemManager.hasFordiacProjectNature(project) ? project : null;
	}

	@Override
	public void addPages() {
		page = new UnifiedLibraryImportWizardPage(targetProject);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		return page.performImport(getContainer()); // runs with progress
	}

	private static IProject getProject(final IStructuredSelection selection) {
		if (selection != null && !selection.isEmpty()) {
			final Object first = selection.getFirstElement();
			if (first instanceof final IProject p) {
				return p;
			}
			if (first instanceof final IFolder f && f.getParent() instanceof final IProject p) {
				return p;
			}
		}
		return null;
	}
}
