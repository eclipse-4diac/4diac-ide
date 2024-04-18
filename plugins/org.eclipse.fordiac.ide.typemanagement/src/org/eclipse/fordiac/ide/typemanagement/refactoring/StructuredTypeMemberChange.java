/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.commands.change.ChangeDataTypeCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class StructuredTypeMemberChange extends Change {

	private final StructuredType affectedStruct;
	private final TypeEntry oldTypeEntry;
	private final List<String> affectedMembers;
	private final String newStructMemberName;

	private final CompoundCommand cmd = new CompoundCommand();

	public StructuredTypeMemberChange(final StructuredType affectedStruct, final TypeEntry oldTypeEntry,
			final String newStructMemberName) {

		this.affectedStruct = affectedStruct;
		this.oldTypeEntry = oldTypeEntry;
		this.newStructMemberName = newStructMemberName;
		this.affectedMembers = affectedStruct.getMemberVariables().stream()
				.filter(var -> var.getType().equals(oldTypeEntry.getType())).map(VarDeclaration::getName).toList();

		this.buildRefactoringChange();

	}

	public void buildRefactoringChange() {
		final StructuredType structuredTypeEditable = (StructuredType) affectedStruct.getTypeEntry().getTypeEditable();
		for (final VarDeclaration varDeclaration : structuredTypeEditable.getMemberVariables()) {
			final String typeName = varDeclaration.getTypeName();
			if (typeName.equals(this.oldTypeEntry.getTypeName())) {
				cmd.add(ChangeDataTypeCommand.forDataType(varDeclaration,
						(DataType) this.oldTypeEntry.getTypeEditable()));
			}
		}

	}

	@Override
	public String getName() {
		return "Update Struct Members: " + this.affectedMembers.toString(); //$NON-NLS-1$
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {

		final StructuredType structuredTypeEditable = (StructuredType) affectedStruct.getTypeEntry().getTypeEditable();

		cmd.execute();
		structuredTypeEditable.getTypeEntry().save(structuredTypeEditable, pm);

		return null;
	}

	@Override
	public StructuredType getModifiedElement() {
		return this.affectedStruct;
	}

	public CompoundCommand getRefactoringCommand() {
		return cmd;
	}

	public TypeEntry getModifiedTypeEntry() {
		return this.oldTypeEntry;
	}

	public String getNewTypeEntryName() {
		return this.newStructMemberName;
	}

}
