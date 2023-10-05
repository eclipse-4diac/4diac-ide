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
package org.eclipse.fordiac.ide.structuredtextcore.ui.refactoring;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.xtext.ide.refactoring.IRenameNameValidator;
import org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor;
import org.eclipse.xtext.ide.refactoring.RefactoringIssueAcceptor.Severity;

@SuppressWarnings("restriction")
public class STCoreRenameNameValidator implements IRenameNameValidator {

	@Override
	public void validate(final EObject target, final String newName, final RefactoringIssueAcceptor issues) {
		IdentifierVerifier.verifyIdentifier(newName, target).ifPresent(message -> issues.add(Severity.FATAL, message));
	}
}
