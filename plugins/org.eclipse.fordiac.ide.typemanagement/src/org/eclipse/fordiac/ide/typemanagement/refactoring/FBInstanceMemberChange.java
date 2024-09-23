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

public class FBInstanceMemberChange extends Change {

	private final StructuredType affectedStruct;
	private final TypeEntry typeEntry;
	private final String oldName;

	private final CompoundCommand cmd = new CompoundCommand();

	public FBInstanceMemberChange(final StructuredType affectedStruct, final TypeEntry oldTypeEntry,
			final String oldName) {
		this.affectedStruct = affectedStruct;
		this.typeEntry = oldTypeEntry;
		this.oldName = oldName;
	}

	@Override
	public String getName() {
		return "Update Struct Members - " + affectedStruct.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
				+ affectedStruct.getTypeEntry().getFile().getFileExtension() + " - " //$NON-NLS-1$
				+ affectedStruct.getTypeEntry().getFile().getProject().getName();
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// No special initialization required
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/** TODO here we need to return the Undo change */
	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {

		final StructuredType structuredTypeEditable = (StructuredType) affectedStruct.getTypeEntry().getTypeEditable();
		for (final VarDeclaration varDeclaration : structuredTypeEditable.getMemberVariables()) {
			final String typeName = varDeclaration.getTypeName();
			if (typeName.equals(oldName)) {
				cmd.add(ChangeDataTypeCommand.forDataType(varDeclaration, (DataType) typeEntry.getTypeEditable()));
			}
		}

		cmd.execute();
		structuredTypeEditable.getTypeEntry().save(structuredTypeEditable, pm);

		return null;
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

}
