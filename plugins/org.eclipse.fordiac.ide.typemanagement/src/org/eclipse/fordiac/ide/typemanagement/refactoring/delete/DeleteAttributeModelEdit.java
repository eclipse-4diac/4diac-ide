/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.text.MessageFormat;
import java.util.EnumSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeAttributeTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteAttributeCommand;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.libraryElement.Attribute;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ConfigurableModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class DeleteAttributeModelEdit extends ConfigurableModelEdit<Attribute> {
	private final AnyType anyType;

	protected DeleteAttributeModelEdit(final Attribute attribute, final AnyType anyType) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateAttribute, attribute.getName(),
				attribute.getTypeName(), ((INamedElement) attribute.eContainer()).getName()),
				EcoreUtil.getURI(attribute), Attribute.class);
		setEnabled(false); // not enabled by default
		this.anyType = anyType;
	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.DELETE, ChangeState.CHANGE_TO_ANY);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.DELETE);
	}

	@Override
	public void initializeValidationData(final Attribute element, final IProgressMonitor pm) {
		// No special initialization required
	}

	@Override
	public RefactoringStatus isValid(final Attribute element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (element.eContainer() == null) {
			status.addError(element.getQualifiedName() + " eContainer is null"); //$NON-NLS-1$
		}
		return status;
	}

	@Override
	protected Command createCommand(final Attribute element) {
		if (getState().contains(ChangeState.DELETE)) {
			return new DeleteAttributeCommand((ConfigurableObject) element.eContainer(), element);
		}
		if (getState().contains(ChangeState.CHANGE_TO_ANY)) {
			return ChangeAttributeTypeCommand.forDataType(element, anyType);
		}
		return null;
	}

}
