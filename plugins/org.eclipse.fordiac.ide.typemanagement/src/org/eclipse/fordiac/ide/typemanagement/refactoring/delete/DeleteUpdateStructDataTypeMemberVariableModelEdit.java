/*******************************************************************************
 * Copyright (c) 2024 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Oberlehner
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.delete;

import java.text.MessageFormat;
import java.util.EnumSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.commands.delete.DeleteMemberVariableCommand;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ConfigurableModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class DeleteUpdateStructDataTypeMemberVariableModelEdit extends ConfigurableModelEdit<VarDeclaration> {

	public DeleteUpdateStructDataTypeMemberVariableModelEdit(final VarDeclaration varDeclaration) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateMemberVariable,
				varDeclaration.getName(), varDeclaration.getTypeName(),
				((INamedElement) varDeclaration.eContainer()).getName()), EcoreUtil.getURI(varDeclaration),
				VarDeclaration.class);
		setEnabled(false); // not enabled by default
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
	public void initializeValidationData(final VarDeclaration element, final IProgressMonitor pm) {
		// No special initialization required
	}

	@Override
	public RefactoringStatus isValid(final VarDeclaration element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();

		if (!(element.getType() instanceof StructuredType)) {
			status.addError("This should not happen"); //$NON-NLS-1$
		}

		return status;
	}

	@Override
	protected Command createCommand(final VarDeclaration varDeclaration) {
		if (getState().contains(ChangeState.DELETE)
				&& varDeclaration.eContainer() instanceof final StructuredType type) {
			return new DeleteMemberVariableCommand(type, varDeclaration);
		}
		if (getState().contains(ChangeState.CHANGE_TO_ANY)) {
			return ChangeDataTypeCommand.forDataType(varDeclaration, IecTypes.GenericTypes.ANY_STRUCT);
		}

		return null;
	}

}