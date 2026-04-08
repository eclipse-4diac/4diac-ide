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
package org.eclipse.fordiac.ide.typemanagement.refactoring.rename;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ModelEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class RenameUpdateStructDataTypeMemberVariableModelEdit extends ModelEdit<VarDeclaration> {

	private final String newTypeDeclaration;
	private String oldTypeDeclaration;

	public RenameUpdateStructDataTypeMemberVariableModelEdit(final VarDeclaration varDeclaration,
			final String newTypeDeclaration) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateMemberVariable,
				varDeclaration.getName(), varDeclaration.getTypeName(),
				((INamedElement) varDeclaration.eContainer()).getName()), EcoreUtil.getURI(varDeclaration),
				VarDeclaration.class);
		this.newTypeDeclaration = newTypeDeclaration;
	}

	@Override
	public void initializeValidationData(final VarDeclaration element, final IProgressMonitor pm) {
		oldTypeDeclaration = element.getFullTypeName();
	}

	@Override
	public RefactoringStatus isValid(final VarDeclaration element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (!Objects.equals(element.getFullTypeName(), oldTypeDeclaration)) {
			status.addFatalError(Messages.DataTypeChange_TypeDeclarationChanged);
		}
		return status;
	}

	@Override
	protected Command createCommand(final VarDeclaration varDeclaration) {
		return ChangeDataTypeCommand.forTypeDeclaration(varDeclaration, newTypeDeclaration);
	}

}
