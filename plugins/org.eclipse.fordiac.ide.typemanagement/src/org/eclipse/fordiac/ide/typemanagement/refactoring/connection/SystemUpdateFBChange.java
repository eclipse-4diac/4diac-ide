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
 *   Mathias Garstenauer - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.typemanagement.refactoring.connection;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.typemanagement.Messages;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

/**
 * A Change that represents updating FBs of one System. This is needed, as
 * updating each FB on its own leads to longer execution times.
 *
 * @see org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand
 * @see org.eclipse.fordiac.ide.typemanagement.refactoring.UpdateFBInstanceChange
 */
public class SystemUpdateFBChange extends AbstractCommandChange<AutomationSystem> {
	private final List<URI> updateURIs;

	/**
	 * Creates a new Instance
	 *
	 * @param elementURI URI of the System in which all of the FB Types are
	 *                   contained
	 * @param list       FB URIs which should be updated
	 */
	protected SystemUpdateFBChange(final URI elementURI, final List<URI> list) {
		super(Objects.requireNonNull(elementURI).trimFileExtension().lastSegment() + Messages.SystemUpdateFBChange_Name,
				elementURI, AutomationSystem.class);
		updateURIs = Objects.requireNonNull(list);
	}

	@Override
	public void initializeValidationData(final AutomationSystem element, final IProgressMonitor pm) {
		// no additional ValidationData needed
	}

	@Override
	public RefactoringStatus isValid(final AutomationSystem element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		final RefactoringStatus status = new RefactoringStatus();
		updateURIs.forEach(uri -> {
			if (!uri.toPlatformString(true).equals(this.getElementURI().toPlatformString(true))) {
				status.merge(RefactoringStatus.createFatalErrorStatus(
						uri + Messages.ConnectionsToStructRefactoring_FBNotInSystem + this.getElementURI()));
			}
		});
		return status;
	}

	@Override
	protected Command createCommand(final AutomationSystem element) {
		final CompoundCommand cmds = new CompoundCommand();
		updateURIs.forEach(uri -> {
			if (element.eResource().getEObject(uri.fragment()) instanceof final FBNetworkElement fbnelem) {
				cmds.add(new UpdateFBTypeCommand(fbnelem));
			}
		});
		return cmds.isEmpty() ? null : cmds;
	}

}
