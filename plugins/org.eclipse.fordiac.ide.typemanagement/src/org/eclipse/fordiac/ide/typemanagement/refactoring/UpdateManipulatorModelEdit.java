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
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateManipulatorModelEdit extends ModelEdit<StructManipulator> {

	public UpdateManipulatorModelEdit(final StructManipulator manipulator) {
		super(getName(manipulator), EcoreUtil.getURI(manipulator), StructManipulator.class);
		setEnabled(false); // not enabled by default
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
			status.addError(getName() + " invalid element"); //$NON-NLS-1$
		}
		return status;
	}

	@Override
	protected Command createCommand(final StructManipulator manipulator) {
		return new ChangeStructCommand(manipulator, IecTypes.GenericTypes.ANY, true);
	}
}
