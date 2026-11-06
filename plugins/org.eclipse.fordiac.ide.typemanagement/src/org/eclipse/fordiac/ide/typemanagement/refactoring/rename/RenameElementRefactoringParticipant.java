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
 *   Michael Oberlehner - move the mapping of the element URI to the helper
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.IdentifierVerifier;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.RenameParticipant;
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext;

/**
 * A participant for rename refactorings triggered within an Xtext context.
 */
@SuppressWarnings("restriction")
public class RenameElementRefactoringParticipant extends RenameParticipant {

	private URI elementURI;

	@Override
	protected boolean initialize(final Object element) {
		if (element instanceof final IRenameElementContext context && isRelevant(context)) {
			elementURI = RenameElementRefactoringHelper.getCanonicalURI(context.getTargetElementURI());
			return true;
		}
		return false;
	}

	@SuppressWarnings("static-method") // subclasses may override
	protected boolean isRelevant(final IRenameElementContext context) {
		return RenameElementRefactoringHelper.isRelevant(context);
	}

	@Override
	public RefactoringStatus checkConditions(final IProgressMonitor pm, final CheckConditionsContext context)
			throws OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		IdentifierVerifier.verifyIdentifier(getArguments().getNewName()).ifPresent(status::addFatalError);
		return status;
	}

	@Override
	public Change createChange(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return RenameElementRefactoringHelper.createRenameChange(getName(), elementURI, getArguments().getNewName());
	}

	@Override
	public String getName() {
		return Messages.RenameElementRefactoringParticipant_Name;
	}
}
