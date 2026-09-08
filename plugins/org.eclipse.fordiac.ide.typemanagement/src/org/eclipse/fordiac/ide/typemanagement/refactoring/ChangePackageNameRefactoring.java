/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
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

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizardOpenOperation;
import org.eclipse.swt.widgets.Shell;

public class ChangePackageNameRefactoring extends RenameRefactoring {

	public ChangePackageNameRefactoring(final TypeEntry typeEntry, final String newPackageName) {
		super(new ChangePackageNameRefactoringProcessor(typeEntry, newPackageName));
	}

	public static void openWizard(final TypeEntry typeEntry, final Shell shell) {
		openWizard(typeEntry, typeEntry.getPackageName(), shell);
	}

	public static void openWizard(final TypeEntry typeEntry, final String packageName, final Shell shell) {
		try {
			RefactoringUtil.saveAllAndBuild();
			final ChangePackageNameRefactoring refactoring = new ChangePackageNameRefactoring(typeEntry, packageName);
			final ChangePackageNameRefactoringWizard wizard = new ChangePackageNameRefactoringWizard(refactoring);
			final RefactoringWizardOpenOperation openOperation = new RefactoringWizardOpenOperation(wizard);
			openOperation.run(shell, Messages.ChangePackageNameRefactoring_Name);
		} catch (final OperationCanceledException e) {
			// ignore
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (final Exception e) {
			FordiacLogHelper.logError("Error during package name refactoring", e); //$NON-NLS-1$
		}
	}

	@Override
	public ChangePackageNameRefactoringProcessor getProcessor() {
		return (ChangePackageNameRefactoringProcessor) super.getProcessor();
	}
}
