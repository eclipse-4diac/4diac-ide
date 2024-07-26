/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import org.eclipse.ltk.core.refactoring.participants.RenameRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

/**
 * A wizard for rename refactorings triggered within a model context, such as a
 * property sheet.
 */
public class RenameElementRefactoringWizard extends RefactoringWizard {

	public RenameElementRefactoringWizard(final RenameRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE);
	}

	@Override
	protected void addUserInputPages() {
		addPage(new RenameElementRefactoringWizardPage(getProcessor()));
	}

	protected RenameElementRefactoringProcessor getProcessor() {
		return getRefactoring().getAdapter(RenameElementRefactoringProcessor.class);
	}
}
