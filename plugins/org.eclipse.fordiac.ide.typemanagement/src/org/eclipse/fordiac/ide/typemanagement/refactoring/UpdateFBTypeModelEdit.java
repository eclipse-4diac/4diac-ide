/*******************************************************************************
 * Copyright (c) 2023, 2026 Johannes Kepler University,
 *                          Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dario Romano - initial API and implementation and/or initial documentation
 *   Fabio Gandolfi - added FB type update
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.text.MessageFormat;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.commands.change.UpdateInternalFBCommand;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType;
import org.eclipse.fordiac.ide.model.libraryElement.BlockFBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateFBTypeModelEdit extends ModelEdit<BlockFBNetworkElement> {

	private final TypeEntry typeEntry;

	public UpdateFBTypeModelEdit(final BlockFBNetworkElement instance, final TypeEntry typeEntry) {
		super(MessageFormat.format(Messages.UpdateFBInstances, FBNetworkHelper.getFullHierarchicalName(instance)),
				EcoreUtil.getURI(instance), BlockFBNetworkElement.class);
		this.typeEntry = typeEntry;
	}

	@Override
	public void initializeValidationData(final BlockFBNetworkElement element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final BlockFBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {

		final RefactoringStatus status = new RefactoringStatus();
		if (element.eContainer() == null) {
			status.addFatalError(element.getQualifiedName() + " eContainer is null"); //$NON-NLS-1$
		}
		return status;
	}

	@Override
	protected Command createCommand(final BlockFBNetworkElement element) {
		if (element.eContainer() instanceof BaseFBType && element instanceof final FB fb) {
			return new UpdateInternalFBCommand(fb, typeEntry);
		}

		return new UpdateFBTypeCommand(element, typeEntry);
	}
}
