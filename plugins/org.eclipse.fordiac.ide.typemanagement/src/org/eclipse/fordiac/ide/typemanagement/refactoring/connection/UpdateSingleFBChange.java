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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.commands.change.UpdateFBTypeCommand;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.fordiac.ide.typemanagement.refactoring.AbstractCommandChange;
import org.eclipse.gef.commands.Command;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateSingleFBChange extends AbstractCommandChange<FBNetworkElement> {
	FBNetworkElement instance;

	protected UpdateSingleFBChange(final URI elementURI, final Class<FBNetworkElement> elementClass) {
		super(createName(elementURI), elementURI, elementClass);
	}

	private static String createName(final URI elementURI) {
		final TypeEntry entry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(elementURI);
		if (entry != null && entry.getType().eResource()
				.getEObject(elementURI.fragment()) instanceof final FBNetworkElement fbnelement) {
			return "Update " + fbnelement.getQualifiedName();
		}
		return "";
	}

	@Override
	public void initializeValidationData(final FBNetworkElement element, final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final FBNetworkElement element, final IProgressMonitor pm)
			throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Command createCommand(final FBNetworkElement element) {
		System.out.println(getName());
		return new UpdateFBTypeCommand(element);
	}

}
