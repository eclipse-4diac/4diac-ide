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
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class StructuredTypeMemberChange extends Change {

	private final StructuredType affectedStruct;
	private final TypeEntry typeEntry;

	private final CompoundCommand cmd = new CompoundCommand();

	public StructuredTypeMemberChange(final StructuredType affectedStruct, final TypeEntry oldTypeEntry) {
		this.affectedStruct = affectedStruct;
		this.typeEntry = oldTypeEntry;
	}

	@Override
	public String getName() {
		return "Update Struct Members - " + affectedStruct.getName() + "." //$NON-NLS-1$ //$NON-NLS-2$
				+ affectedStruct.getTypeEntry().getFile().getFileExtension() + " - " //$NON-NLS-1$
				+ affectedStruct.getTypeEntry().getFile().getProject().getName();
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {

	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		return new RefactoringStatus();
	}

	/** TODO here we need to return the Undo change */
	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		for (final VarDeclaration varDeclaration : affectedStruct.getMemberVariables()) {
			if (varDeclaration.getType() != null && typeEntry == varDeclaration.getType().getTypeEntry()) {
				cmd.add(ChangeDataTypeCommand.forDataType(varDeclaration, (DataType) typeEntry.getTypeEditable()));
			}
		}

		AbstractLiveSearchContext.executeAndSave(cmd, affectedStruct, pm);
		return null;
	}

	@Override
	public Object getModifiedElement() {
		return null;
	}

}
