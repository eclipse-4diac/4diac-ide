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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.ChangeStructCommand;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerDataType;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateManipulatorChange extends AbstractCommandChange<StructManipulator> {

	final boolean structWasDeleted;

	public UpdateManipulatorChange(final StructManipulator manipulator, final boolean structWasDeleted) {
		super(getName(manipulator), EcoreUtil.getURI(manipulator), StructManipulator.class);
		this.structWasDeleted = structWasDeleted;
	}

	public static String getName(final StructManipulator manipulator) {
		return MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateManipulator,
				manipulator.getQualifiedName());
	}

	@Override
	public void initializeValidationData(final StructManipulator element, final IProgressMonitor pm) {
		// nothing to do here
	}

	@Override
	public RefactoringStatus isValid(final StructManipulator manipulator, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (manipulator.eContainer() == null) {
			status.addError(getName() + " invalid element");
		}
		return status;
	}

	@Override
	protected Command createCommand(final StructManipulator manipulator) {
		if (structWasDeleted) {
			return new ChangeStructCommand(manipulator, getErrorMarkerEntry(manipulator.getDataType(), manipulator),
					true);
		}
		return new ChangeStructCommand(manipulator);
	}

	private static ErrorMarkerDataType getErrorMarkerEntry(final DataType dtp, final StructManipulator manipulator) {
		final DataTypeLibrary lib = manipulator.getTypeEntry().getTypeLibrary().getDataTypeLibrary();
		return lib.createErrorMarkerType(dtp.getTypeEntry().getFullTypeName(), ""); //$NON-NLS-1$
	}
}
