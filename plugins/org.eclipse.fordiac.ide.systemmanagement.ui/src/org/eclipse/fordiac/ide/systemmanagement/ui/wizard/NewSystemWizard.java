/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemmanagement.ui.wizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.fordiac.ide.model.libraryElement.Application;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.systemmanagement.SystemManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.commands.NewAppCommand;
import org.eclipse.fordiac.ide.typemanagement.preferences.TypeManagementPreferencesHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.util.OpenListenerManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class NewSystemWizard extends Wizard implements INewWizard {
	private IStructuredSelection selection;
	private NewSystemWizardPage page;

	public NewSystemWizard() {
		setWindowTitle(FordiacMessages.NewType);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle(FordiacMessages.NewType);
	}

	@Override
	public void addPages() {
		page = new NewSystemWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		try {
			WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor monitor) {
					IProgressMonitor monitorToUse = (null == monitor) ? new NullProgressMonitor() : monitor;

					IPath targetPath = ResourcesPlugin.getWorkspace().getRoot().getLocation()
							.append(page.getContainerFullPath());
					IContainer location = ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(targetPath);
					AutomationSystem system = SystemManager.INSTANCE.createNewSystem(location, page.getSystemName());
					TypeManagementPreferencesHelper.setupVersionInfo(system);
					createInitialApplication(monitorToUse, system);
				}
			};
			getContainer().run(false, true, op);
		} catch (InvocationTargetException | InterruptedException x) {
			return false;
		}
		// everything worked fine
		return true;
	}

	private void createInitialApplication(final IProgressMonitor monitor, AutomationSystem system) {
		NewAppCommand cmd = new NewAppCommand(system, page.getInitialApplicationName(), ""); //$NON-NLS-1$
		cmd.execute(monitor, null);

		Application app = cmd.getApplication();
		if (page.getOpenApplication() && null != app) {
			OpenListenerManager.openEditor(app);
		}
	}

}
