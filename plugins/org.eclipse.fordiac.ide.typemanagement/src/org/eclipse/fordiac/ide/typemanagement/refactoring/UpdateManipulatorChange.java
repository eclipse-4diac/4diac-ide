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
import java.util.EnumSet;

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

public class UpdateManipulatorChange extends ConfigurableChange<StructManipulator> {

	public UpdateManipulatorChange(final StructManipulator manipulator) {
		super(getName(manipulator), EcoreUtil.getURI(manipulator), StructManipulator.class);
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
		if (getState().contains(ChangeState.CHANGE_TO_ANY)) {
			return new ChangeStructCommand(manipulator, IecTypes.GenericTypes.ANY, true);
		}
		// only return null if the UI has an incosistency, so returning null will force
		// an error
		return null;
	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.CHANGE_TO_ANY, ChangeState.NO_CHANGE);
	}

}
