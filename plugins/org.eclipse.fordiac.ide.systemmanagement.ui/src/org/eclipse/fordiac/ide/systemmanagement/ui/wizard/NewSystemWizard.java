/*******************************************************************************
 * Copyright (c) 2008, 2010 - 2016  Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
import org.eclipse.fordiac.ide.systemmanagement.ui.Activator;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.VersionInfo;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.systemmanagement.util.SystemPaletteManagement;
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
		page = new NewSystemPage(
				Messages.NewSystemWizard_WizardName);
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
					createProject(monitor != null ? monitor
							: new NullProgressMonitor());
				}
			};
			getContainer().run(false, true, op);
		} catch (InvocationTargetException x) {
			return false;
		} catch (InterruptedException x) {
			return false;
		}
		// everything worked fine
		return true;
	}

	protected String[] getNatureIDs() {
		return new String[] { SystemManager.FORDIAC_PROJECT_NATURE_ID};
	}
	
	/**
	 * Creates a new project in the workspace.
	 * 
	 * @param monitor
	 *            the monitor
	 */
	public AutomationSystem createProject(final IProgressMonitor monitor) {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			String projectName = page.getProjectName(); 
			boolean defaultPalette = page.importDefaultPalette();
			IPath location = page.getLocationPath();

			IProject project = root.getProject(projectName);
			IProjectDescription description = ResourcesPlugin.getWorkspace()
					.newProjectDescription(project.getName());
			if (!Platform.getLocation().equals(location)) {
				description.setLocation(location);
			}

			description
					.setNatureIds(getNatureIDs()); 

			project.create(description, monitor);
			project.open(monitor);

			// configure palette
			if (defaultPalette) {
				SystemPaletteManagement.copyToolTypeLibToProject(project);
			}

			// create the system, after the palette is configured,
			// otherwise the palette is not correctly initialzed
			AutomationSystem system = SystemManager.INSTANCE.createAutomationSystem(project);

			setupVersionInfo(system);
			SystemManager.INSTANCE.addSystem(system);
			
			NewApplicationWizard.performApplicationCreation(system, page.getInitialApplicationName(), page.getOpenApplication(), getShell());
			
			SystemManager.INSTANCE.saveSystem(system);
			
			return system;

		} catch (CoreException e) {
			Activator.getDefault().logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}
		return null;
	}

	private static void setupVersionInfo(AutomationSystem system) {
		VersionInfo verInfo = LibraryElementFactory.eINSTANCE.createVersionInfo();			
		//TODO retrieve this information from some generic location, maybe wizard
		verInfo.setAuthor("Author");
		verInfo.setOrganization("Eclipse 4diac");
		verInfo.setVersion("1.0");
		system.getVersionInfo().add(verInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench,
			final IStructuredSelection selection) {
		//currently nothing to do here
	}

}
