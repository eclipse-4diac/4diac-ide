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
package org.eclipse.fordiac.ide.typemanagement.refactoring.edit;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class DataTypeEdit extends ModelEdit<VarDeclaration> {

	private final String newTypeName;
	private String oldTypeName;

	public DataTypeEdit(final String name, final URI elementURI, final String newTypeName) {
		super(name, elementURI, VarDeclaration.class);
		this.newTypeName = newTypeName;
	}

	@Override
	protected Command createCommand(final VarDeclaration element) {
		return ChangeDataTypeCommand.forTypeName(element, newTypeName);
	}

	@Override
	public void initializeValidationData(final VarDeclaration element, final IProgressMonitor pm) {
		oldTypeName = element.getFullTypeName();
	}

	@Override
	public RefactoringStatus isValid(final VarDeclaration element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!Objects.equals(element.getFullTypeName(), oldTypeName)) {
			status.addFatalError(Messages.DataTypeChange_DataTypeChanged);
		}
		return status;
	}
}
