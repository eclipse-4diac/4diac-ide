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
 *    - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.search.AbstractLiveSearchContext;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateManipulatorChange extends Change {

	final StructManipulator manipulator;
	final boolean structWasDeleted;

	public UpdateManipulatorChange(final StructManipulator manipulator, final boolean structWasDeleted) {
		this.manipulator = manipulator;
		this.structWasDeleted = structWasDeleted;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		final ChangeStructCommand cmd = getCommand();
		AbstractLiveSearchContext.executeAndSave(cmd, manipulator, pm);
		return new UpdateManipulatorChange(cmd.getNewMux(), !structWasDeleted);
	}

	private ChangeStructCommand getCommand() {
		if (structWasDeleted) {
			return new ChangeStructCommand(manipulator, getErrorMarkerEntry(manipulator.getDataType()), true);
		}
		return new ChangeStructCommand(manipulator);
	}

	private ErrorMarkerDataType getErrorMarkerEntry(final DataType dtp) {
		final DataTypeLibrary lib = manipulator.getTypeEntry().getTypeLibrary().getDataTypeLibrary();
		return lib.createErrorMarkerType(dtp.getTypeEntry().getFullTypeName(), ""); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateManipulator,
				manipulator.getQualifiedName());
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// No special initialization required
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {

		return null;
	}

	@Override
	public Object getModifiedElement() {

		return manipulator;
	}

}
