/*******************************************************************************
 * Copyright (c) 2025 Johannes Kepler University Linz
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

import org.eclipse.core.runtime.IPath;
import org.eclipse.fordiac.ide.systemmanagement.ui.Messages;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class New4diacExampleWizard extends Wizard implements INewWizard {

	public New4diacExampleWizard() {
		setWindowTitle(Messages.New4diacExampleWizard_WizardTitle);
	}

	@Override
	public void addPages() {
		addPage(new New4diacExampleProjectPage());
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		// currently nothing to do here
	}

	@Override
	public boolean performFinish() {
		final New4diacExampleProjectPage examplePage = getExamplePage();
		final File exampleZipFile = examplePage.getExample();
		final String projectName = examplePage.getProjectName();
		final IPath projectPath = examplePage.getLocationPath();

		final IRunnableWithProgress operation = new ExampleCreationOperation(exampleZipFile, projectName, projectPath);

		try {
			getContainer().run(false, true, operation);
		} catch (final InvocationTargetException e) {
			FordiacLogHelper.logError("Couldn't create example", e); //$NON-NLS-1$
			return false;
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
			return false;
		}
		return true;
	}

	private New4diacExampleProjectPage getExamplePage() {
		return (New4diacExampleProjectPage) getPages()[0];
	}

}
