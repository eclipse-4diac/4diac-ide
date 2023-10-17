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
 *   Alois Zoitl        - Migrated from RenameTypeAction
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RenameTypeRefactoring;
import org.eclipse.fordiac.ide.typemanagement.refactoring.RenameTypeRefactoringWizard;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

public class RenameTypeHandler extends AbstractHandler {

	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection sel = HandlerUtil.getCurrentSelection(event);
		final TypeEntry typeEntry = getTypeEntryFromSelection(sel);
		if (typeEntry != null) {
			final RenameTypeRefactoring refactoring = new RenameTypeRefactoring(typeEntry, typeEntry.getTypeName());
			executeRefactoringWizard(new RenameTypeRefactoringWizard(refactoring, 0),
					HandlerUtil.getActiveShell(event));
		}
		// Tell the LTK action that we are not a composite change. Even if we where we
		// did everything in the wizard. Maybe a split like the RenameResourceHandler
		// would be good in the future
		return Boolean.FALSE;
	}

	@Override
	public void setEnabled(final Object evaluationContext) {
		final ISelection sel = (ISelection) HandlerUtil.getVariable(evaluationContext,
				ISources.ACTIVE_CURRENT_SELECTION_NAME);
		setBaseEnabled(null != getTypeEntryFromSelection(sel));
	}

	private static TypeEntry getTypeEntryFromSelection(final ISelection selection) {
		if (selection instanceof final IStructuredSelection structSel && !structSel.isEmpty() && structSel.size() == 1
				&& structSel.getFirstElement() instanceof final IFile file) {
			return TypeLibraryManager.INSTANCE.getTypeEntryForFile(file);
		}
		return null;
	}

	private static void executeRefactoringWizard(final RefactoringWizard wizard, final Shell parent) {
		try {
			final RefactoringWizardOpenOperation operation = new RefactoringWizardOpenOperation(wizard);
			operation.run(parent, Messages.RenameType_Name);
		} catch (final InterruptedException exception) {
			FordiacLogHelper.logError(exception.getMessage(), exception);
			Thread.currentThread().interrupt();
		}
	}

}
