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

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.NameRepository;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A change for renaming individual {@link INamedElement}s.
 */
public class RenameElementChange extends AbstractCommandChange<INamedElement> {

	private final String newName;
	private String oldName;

	public RenameElementChange(final String name, final URI elementURI, final String newName) {
		super(name, elementURI, INamedElement.class);
		this.newName = newName;
	}

	@Override
	protected Command createCommand(final INamedElement element) {
		return ChangeNameCommand.forName(element, newName);
	}

	@Override
	public void initializeValidationData(final INamedElement element, final IProgressMonitor pm) {
		oldName = element.getName();
	}

	@Override
	public RefactoringStatus isValid(final INamedElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!Objects.equals(element.getName(), oldName)) {
			status.addFatalError(Messages.RenameElementChange_NameChanged);
		} else if (!NameRepository.isValidName(element, newName)) {
			status.addFatalError(Messages.RenameElementChange_InvalidName);
		}
		return status;
	}

}
