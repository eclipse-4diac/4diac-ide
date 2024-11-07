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
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeValueCommand;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class AttributeValueChange extends AbstractCommandChange<Attribute> {

	private final String newValue;
	private String oldValue;

	public AttributeValueChange(final String name, final URI elementURI, final String newValue) {
		super(name, elementURI, Attribute.class);
		this.newValue = newValue;
	}

	@Override
	protected Command createCommand(final Attribute element) {
		return new ChangeAttributeValueCommand(element, newValue);
	}

	@Override
	public void initializeValidationData(final Attribute element, final IProgressMonitor pm) {
		oldValue = element.getValue();
	}

	@Override
	public RefactoringStatus isValid(final Attribute element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!Objects.equals(element.getValue(), oldValue)) {
			status.addFatalError(Messages.AttributeValueChange_AttributeValueChanged);
		}
		return status;
	}

}
