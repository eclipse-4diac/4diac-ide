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
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.ui;

import org.eclipse.fordiac.ide.typemanagement.refactoring.structmember.AddStructMemberRefactoring;
import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

final class AddStructMemberRefactoringWizard extends RefactoringWizard {

	AddStructMemberRefactoringWizard(final AddStructMemberRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE | PREVIEW_EXPAND_FIRST_NODE);
		setDefaultPageTitle(refactoring.getName());
	}

	@Override
	protected void addUserInputPages() {
		addPage(new AddStructMemberRefactoringWizardPage(getStructMemberRefactoring()));
	}

	private AddStructMemberRefactoring getStructMemberRefactoring() {
		return (AddStructMemberRefactoring) getRefactoring();
	}
}
