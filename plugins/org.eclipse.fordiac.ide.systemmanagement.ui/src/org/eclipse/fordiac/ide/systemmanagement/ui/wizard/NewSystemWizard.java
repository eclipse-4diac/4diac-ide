/*******************************************************************************
 * Copyright (c) 2008, 2010 - 2016  Profactor GmbH, TU Wien ACIN, fortiss GmbH
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.ui.commands.NewAppCommand;
import org.eclipse.fordiac.ide.systemmanagement.util.SystemPaletteManagement;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * The Class NewSystemWizard.
 */
public class NewSystemWizard extends Wizard implements INewWizard {

	/** The page. */
	private NewSystemPage page;

	/**
	 * Instantiates a new new system wizard.
	 */
	public NewSystemWizard() {
		setWindowTitle(Messages.NewSystemWizard_WizardName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new NewSystemPage(Messages.NewSystemWizard_WizardName);
		page.setTitle(Messages.NewSystemWizard_WizardName);
		page.setDescription(Messages.NewSystemWizard_WizardDesc);

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

	private static String[] getNatureIDs() {
		return new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID };
	}

	/**
	 * Creates a new project in the workspace.
	 *
	 * @param monitor the monitor
	 */
	private void createProject(final IProgressMonitor monitor) {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			IProject project = root.getProject(page.getProjectName());
			IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());

			IPath location = page.getLocationPath();
			if (!Platform.getLocation().equals(location)) {
				description.setLocation(location);
			}

			description.setNatureIds(getNatureIDs());

			project.create(description, monitor);
			project.open(monitor);

			// configure palette
			if (page.importDefaultPalette()) {
				SystemPaletteManagement.copyToolTypeLibToProject(project);
			}

			// create the system, after the palette is configured,
			// otherwise the palette is not correctly initialzed
//			AutomationSystem system = SystemManager.INSTANCE.createAutomationSystem(project);
//
//			TypeManagementPreferencesHelper.setupVersionInfo(system);
//			SystemManager.INSTANCE.addSystem(system);

//			createInitialApplication(monitor, system);
//
//			SystemManager.INSTANCE.saveSystem(system);
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
