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

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.typemanagement.util.SystemCreator;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

public class NewSystemWizard extends Wizard implements INewWizard {
	private IStructuredSelection selection;
	private NewSystemWizardPage page;

	public NewSystemWizard() {
		setWindowTitle(Messages.NewSystemWizardPage_NewSystemTitle);
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.selection = selection;
		setWindowTitle(Messages.NewSystemWizardPage_NewSystemTitle);
	}

	@Override
	public void addPages() {
		page = new NewSystemWizardPage(selection);
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		try {
			final WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
				@Override
				protected void execute(final IProgressMonitor monitor) throws CoreException {
					final SystemCreator systemCreator = new SystemCreator(getSystemLocation(), page.getSystemName(),
							page.getInitialApplicationName());
					systemCreator.createSystem(monitor);
					if (page.getOpenApplication() && systemCreator.getApplication() != null) {
						OpenListenerManager.openEditor(systemCreator.getApplication());
					}
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

	private final IContainer getSystemLocation() {
		final IPath targetPath = getTargetFile().getParent().getLocation();
		return ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(targetPath);
	}

	private final IFile getTargetFile() {
		final String sysName = page.getSystemName();
		final IWorkspaceRoot wsr = ResourcesPlugin.getWorkspace().getRoot();
		return wsr.getFile(new Path(page.getContainerFullPath() + File.separator + sysName));
	}
}
