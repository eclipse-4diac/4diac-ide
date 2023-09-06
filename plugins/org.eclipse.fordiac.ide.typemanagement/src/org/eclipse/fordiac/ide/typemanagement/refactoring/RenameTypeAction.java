/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class RenameTypeAction implements IWorkbenchWindowActionDelegate {

	private TypeEntry typeEntry;
	private IWorkbenchWindow fWindow;

	@Override
	public void dispose() {
		// Do nothing
	}

	@Override
	public void init(final IWorkbenchWindow window) {
		fWindow = window;
	}

	@Override
	public void run(final IAction action) {
		if (typeEntry != null) {
			final RenameTypeRefactoring refactoring = new RenameTypeRefactoring(typeEntry, typeEntry.getTypeName());
			run(new RenameTypeRefactoringWizard(refactoring, 0), fWindow.getShell(), "Rename Type"); //$NON-NLS-1$
		}
	}

	public void run(final RefactoringWizard wizard, final Shell parent, final String dialogTitle) {
		try {
			final RefactoringWizardOpenOperation operation = new RefactoringWizardOpenOperation(wizard);
			operation.run(parent, dialogTitle);
		} catch (final InterruptedException exception) {
			FordiacLogHelper.logError(exception.getMessage(), exception);
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		typeEntry = null;
		if (selection instanceof final IStructuredSelection extended) {
			final Object[] elements = extended.toArray();
			if (elements.length == 1 && elements[0] instanceof IFile) {
				typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile((IFile) elements[0]);
			}
		}

	}
}