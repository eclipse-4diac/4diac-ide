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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.library.ui.wizards.LibrarySelectionPage;
import org.eclipse.fordiac.ide.model.commands.create.CreateApplicationCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * The Class NewSystemWizard.
 */
public class New4diacProjectWizard extends Wizard implements INewWizard {

	/** The pages. */
	private New4diacProjectPage page;
	private LibrarySelectionPage libPage;

	/**
	 * Instantiates a new new system wizard.
	 */
	public New4diacProjectWizard() {
		setWindowTitle(Messages.New4diacProjectWizard_WizardName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new New4diacProjectPage(Messages.New4diacProjectWizard_WizardName);
		page.setTitle(Messages.New4diacProjectWizard_WizardName);
		page.setDescription(Messages.New4diacProjectWizard_WizardDesc);

		libPage = new LibrarySelectionPage(Messages.New4diacProjectWizard_WizardName, true, true, true, false);
		libPage.setTitle(Messages.New4diacProjectWizard_WizardName);
		libPage.setDescription(Messages.New4diacProjectWizard_WizardDesc);

		addPage(page);
		addPage(libPage);
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
					page.getLocationPath(), libPage.getChosenLibraries(), monitor);
			final AutomationSystem system = SystemManager.INSTANCE.createNewSystem(newProject,
					page.getInitialSystemName(), monitor);
			TypeManagementPreferencesHelper.setupVersionInfo(system);
			createInitialApplication(system);
		} catch (final CoreException e) {
			FordiacLogHelper.logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}
	}

	private void createInitialApplication(final AutomationSystem system) {
		final CreateApplicationCommand cmd = new CreateApplicationCommand(system, page.getInitialApplicationName());

		final Application app = cmd.getCreatedElement();
		if (page.getOpenApplication() && null != app) {
			OpenListenerManager.openEditor(app);
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
