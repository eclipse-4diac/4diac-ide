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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class UpdateFBChange extends Change {
	private final URI typeURI;
	CompositeChange updates;

	public UpdateFBChange(final URI typeURI) {
		this.typeURI = typeURI;
		updates = new CompositeChange("");
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(typeURI).getType() instanceof final FBType sourceType) {
			new BlockTypeInstanceSearch(sourceType.getTypeEntry()).performSearch().stream()
					.map(FBNetworkElement.class::cast).forEach(fbnelem -> {
						updates.add(new UpdateSingleFBChange(EcoreUtil.getURI(fbnelem), FBNetworkElement.class));
					});
		}
	}

	@Override
	public String getName() {
		return "Update all Instances of " + typeURI.lastSegment();
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		return updates.perform(pm);
	}

	@Override
	public Object getModifiedElement() {
		// TODO Auto-generated method stub
		return null;
	}
}
