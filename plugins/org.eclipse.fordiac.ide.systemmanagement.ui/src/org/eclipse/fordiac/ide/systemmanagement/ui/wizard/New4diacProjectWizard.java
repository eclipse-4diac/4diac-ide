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
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.commands.NewAppCommand;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * The Class NewSystemWizard.
 */
public class New4diacProjectWizard extends Wizard implements INewWizard {

	/** The page. */
	private New4diacProjectPage page;

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

		addPage(page);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor monitor) {
					createProject(monitor != null ? monitor : new NullProgressMonitor());
				}
			};
			getContainer().run(false, true, op);
		} catch (InvocationTargetException | InterruptedException x) {
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

			IProject newProject = SystemManager.INSTANCE.createNew4diacProject(page.getProjectName(),
					page.getLocationPath(), page.importDefaultPalette(), monitor);
			AutomationSystem system = SystemManager.INSTANCE.createNewSystem(newProject, page.getInitialSystemName());
			TypeManagementPreferencesHelper.setupVersionInfo(system);
			createInitialApplication(monitor, system);
		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}
	}

	private void createInitialApplication(final IProgressMonitor monitor, AutomationSystem system) {
		NewAppCommand cmd = new NewAppCommand(system, page.getInitialApplicationName(),
				Messages.NewApplicationWizard_Comment);
		cmd.execute(monitor, null);

		Application app = cmd.getApplication();
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
