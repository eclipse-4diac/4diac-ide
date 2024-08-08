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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class SystemUpdateFBChange extends AbstractCommandChange<AutomationSystem> {
	private final List<URI> updateURIs;

	protected SystemUpdateFBChange(final URI elementURI, final List<URI> list) {
		super(elementURI.trimFileExtension().lastSegment() + Messages.SystemUpdateFBChange_Name, elementURI, AutomationSystem.class);
		updateURIs = list;
	}

	@Override
	public void initializeValidationData(final AutomationSystem element, final IProgressMonitor pm) {
		// TODO

	}

	@Override
	public RefactoringStatus isValid(final AutomationSystem element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO
		return null;
	}

	@Override
	protected Command createCommand(final AutomationSystem element) {
		final CompoundCommand cmds = new CompoundCommand();
		updateURIs.forEach(uri -> {
			if (element.eResource().getEObject(uri.fragment()) instanceof final FBNetworkElement fbnelem) {
				cmds.add(new UpdateFBTypeCommand(fbnelem));
			}
		});
		return cmds;
	}

}
