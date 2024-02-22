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
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class InitialValueChange extends AbstractCommandChange<VarDeclaration> {

	private final String newValue;
	private String oldValue;

	public InitialValueChange(final String name, final URI elementURI, final String newValue) {
		super(name, elementURI, VarDeclaration.class);
		this.newValue = newValue;
	}

	@Override
	protected Command createCommand(final VarDeclaration element) {
		return new ChangeValueCommand(element, newValue);
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		super.initializeValidationData(pm);
		final VarDeclaration element = getElement();
		if (element != null) {
			oldValue = getInitialValue(element);
		}
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		final RefactoringStatus status = super.isValid(pm);
		if (!status.hasFatalError()) {
			final VarDeclaration element = getElement();
			if (element != null && !Objects.equals(getInitialValue(element), oldValue)) {
				status.addFatalError(Messages.InitialValueChange_InitialValueChanged);
			}
		}
		return status;
	}

	protected static String getInitialValue(final VarDeclaration varDeclaration) {
		if (varDeclaration.getValue() != null && varDeclaration.getValue().getValue() != null) {
			return varDeclaration.getValue().getValue();
		}
		return ""; //$NON-NLS-1$
	}
}
