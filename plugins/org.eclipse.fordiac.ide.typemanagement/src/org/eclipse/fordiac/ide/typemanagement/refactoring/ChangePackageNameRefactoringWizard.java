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

import org.eclipse.ltk.ui.refactoring.RefactoringWizard;

public class ChangePackageNameRefactoringWizard extends RefactoringWizard {

	public ChangePackageNameRefactoringWizard(final ChangePackageNameRefactoring refactoring) {
		super(refactoring, DIALOG_BASED_USER_INTERFACE);
	}

	@Override
	protected void addUserInputPages() {
		addPage(new ChangePackageNameRefactoringWizardPage(getChangePackageNameProcessor()));
	}

	private ChangePackageNameRefactoringProcessor getChangePackageNameProcessor() {
		return ((ChangePackageNameRefactoring) getRefactoring()).getProcessor();
	}
}
