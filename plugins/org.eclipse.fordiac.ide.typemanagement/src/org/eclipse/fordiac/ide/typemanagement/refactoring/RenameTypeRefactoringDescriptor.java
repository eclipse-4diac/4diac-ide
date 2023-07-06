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

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringDescriptor;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameTypeRefactoringDescriptor extends RefactoringDescriptor {

	public static final String REFACTORING_ID = "org.eclipse.fordiac.ide.typemanagement.refactoring.renametype";

	private final Map fArguments;

	public RenameTypeRefactoringDescriptor(final String project, final String description, final String comment,
			final Map arguments) {
		super(REFACTORING_ID, project, description, comment,
				RefactoringDescriptor.STRUCTURAL_CHANGE | RefactoringDescriptor.MULTI_CHANGE);
		fArguments = arguments;
	}

	@Override
	public Refactoring createRefactoring(final RefactoringStatus status) throws CoreException {
		final RenameTypeRefactoring refactoring = new RenameTypeRefactoring();

		return refactoring;
	}

	public Map getArguments() {
		return fArguments;
	}
}