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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class DataTypeChange extends AbstractCommandChange<IInterfaceElement> {

	private final String newTypeDeclaration;
	private String oldTypeDeclaration;

	public DataTypeChange(final String name, final URI elementURI, final String newTypeDeclaration) {
		super(name, elementURI, IInterfaceElement.class);
		this.newTypeDeclaration = newTypeDeclaration;
	}

	@Override
	protected Command createCommand(final IInterfaceElement element) {
		return ChangeDataTypeCommand.forTypeDeclaration(element, newTypeDeclaration);
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		super.initializeValidationData(pm);
		final IInterfaceElement element = getElement();
		if (element != null) {
			oldTypeDeclaration = element.getFullTypeName();
		}
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = super.isValid(pm);
		if (!status.hasFatalError()) {
			final IInterfaceElement element = getElement();
			if (element != null && !Objects.equals(element.getFullTypeName(), oldTypeDeclaration)) {
				status.addFatalError(Messages.DataTypeChange_TypeDeclarationChanged);
			}
		}
		return status;
	}
}
