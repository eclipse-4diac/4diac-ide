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
package org.eclipse.fordiac.ide.typemanagement.refactoring.structmember;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

final class ConnectAddedStructMemberModelEdit extends ModelEdit<LibraryElement> {
	private final AddStructMemberContext context;
	private final String memberName;

	ConnectAddedStructMemberModelEdit(final AddStructMemberContext context, final String memberName) {
		super(Messages.AddStructMemberRefactoring_ChangeName, context.getTargetModelURI(), LibraryElement.class);
		this.context = context;
		this.memberName = memberName;
	}

	@Override
	public void initializeValidationData(final LibraryElement element, final IProgressMonitor pm) {
		// The stable context itself is the validation data.
	}

	@Override
	public RefactoringStatus isValid(final LibraryElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		return context.matches(element) ? new RefactoringStatus()
				: RefactoringStatus.createFatalErrorStatus(Messages.AddStructMemberRefactoring_InvalidContext);
	}

	@Override
	protected Command createCommand(final LibraryElement element) {
		return new CreateStructMemberConnectionCommand(element, context, memberName);
	}
}
