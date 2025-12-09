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
import org.eclipse.fordiac.ide.model.commands.change.ChangeConditionExpressionCommand;
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class ConditionExpressionEdit extends ModelEdit<ECTransition> {

	private final String newValue;
	private String oldValue;

	public ConditionExpressionEdit(final String name, final URI elementURI, final String newValue) {
		super(name, elementURI, ECTransition.class);
		this.newValue = newValue;
	}

	@Override
	protected Command createCommand(final ECTransition element) {
		return new ChangeConditionExpressionCommand(element, newValue);
	}

	@Override
	public void initializeValidationData(final ECTransition element, final IProgressMonitor pm) {
		oldValue = element.getConditionExpression();
	}

	@Override
	public RefactoringStatus isValid(final ECTransition element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!Objects.equals(element.getConditionExpression(), oldValue)) {
			status.addFatalError(Messages.ConditionExpressionEdit_ConditionExpressionChanged);
		}
		return status;
	}
}
