/*******************************************************************************
 * Copyright (c) 2008, 2010 - 2016  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 				 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger, Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - changed new system wizard to a new 4diac project wizard for
 *                 the new project layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.ui.wizards.UnifiedLibraryImportWizardPage;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.util.SystemCreator;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * The Class NewSystemWizard.
 */
public class New4diacProjectWizard extends Wizard implements INewWizard {

	private static final String[] LIBRARY_STANDARD_SELECTION = { "convert", "core", "events", "iec61131-3", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"net", "system", "utils" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/** The pages. */
	private New4diacProjectPage page;
	private UnifiedLibraryImportWizardPage libPage;

	/**
	 * Instantiates a new new system wizard.
	 */
	public New4diacProjectWizard() {
		setWindowTitle(Messages.New4diacProjectWizard_WizardTitle);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new New4diacProjectPage(Messages.New4diacProjectWizard_WizardTitle);
		page.setTitle(Messages.New4diacProjectWizard_WizardTitle);
		page.setDescription(Messages.New4diacProjectWizard_WizardDesc);

		libPage = new UnifiedLibraryImportWizardPage(null, LIBRARY_STANDARD_SELECTION, true);
		libPage.setTitle(Messages.New4diacProjectWizard_LibPageName);
		libPage.setDescription(Messages.New4diacProjectWizard_LibPageDesc);

		addPage(page);
		addPage(libPage);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage currentPage) {
		if (currentPage == page) {
			libPage.setTargetProject(ResourcesPlugin.getWorkspace().getRoot().getProject(page.getProjectName()));
		}
		return super.getNextPage(currentPage);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			final WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				@Override
				protected void execute(final IProgressMonitor monitor) {
					createProject(monitor != null ? monitor : new NullProgressMonitor());
				}
			};
			getContainer().run(false, true, op);

		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
			return false;
		} catch (final InterruptedException x) {
			Thread.currentThread().interrupt(); // mark interruption
			return false;
		}

		libPage.performImport(getContainer());

		// everything worked fine
		return true;
	}

	/**
	 * Creates a new project in the workspace.
	 *
	 * @param monitor the monitor
	 */
	private void createProject(final IProgressMonitor monitor) {
		try {

			final IProject newProject = SystemManager.INSTANCE.createNew4diacProject(page.getProjectName(),
					page.getLocationPath(), monitor);
			libPage.setTargetProject(newProject);
			final SystemCreator systemCreator = new SystemCreator(newProject, page.getInitialSystemName(),
					page.getInitialApplicationName());
			systemCreator.createSystem(monitor);
			if (page.getOpenApplication() && systemCreator.getApplication() != null) {
				OpenListenerManager.openEditor(systemCreator.getApplication());
			}
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		// currently nothing to do here
	}

}
