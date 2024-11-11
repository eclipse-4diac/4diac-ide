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
import org.eclipse.fordiac.ide.model.commands.delete.DeleteInternalFBCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.ConfigurableChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class DeleteInternalFBChange extends ConfigurableChange<FB> {

	public DeleteInternalFBChange(final FB internalFb) {
		super(MessageFormat.format(Messages.DeleteFBTypeParticipant_Change_UpdateInternalFB,
				internalFb.getQualifiedName()), EcoreUtil.getURI(internalFb), FB.class);
	}

	@Override
	public EnumSet<ChangeState> getAllowedChoices() {
		return EnumSet.of(ChangeState.DELETE, ChangeState.NO_CHANGE);
	}

	@Override
	public EnumSet<ChangeState> getDefaultSelection() {
		return EnumSet.of(ChangeState.DELETE);
	}

	@Override
	protected Command createCommand(final FB fb) {
		Command cmd = null;
		if (getState().contains(ChangeState.DELETE)) {
			cmd = new DeleteInternalFBCommand(fb);
		}
		return cmd;
	}

	@Override
	public void initializeValidationData(final FB element, final IProgressMonitor pm) {
		// nothing to do here
	}

	@Override
	public RefactoringStatus isValid(final FB element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		if (element.eContainer() == null) {
			status.addError(element.getQualifiedName() + " is null");
		}
		return status;
	}

}