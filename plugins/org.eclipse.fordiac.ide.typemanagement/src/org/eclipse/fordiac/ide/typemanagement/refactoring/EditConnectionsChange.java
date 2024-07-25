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
package org.eclipse.fordiac.ide.typemanagement.refactoring;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public class EditConnectionsChange extends Change {
	private final Map<String, String> replacableConMap;
	private final URI source;
	private final URI structURI;
	private final boolean isSourceReconnect;
	private final CompositeChange repairs;

	public EditConnectionsChange(final URI elementURI, final Class<FBNetwork> elementClass,
			final Map<String, String> replacableConMap, final URI source, final URI structURI,
			final boolean isSourceReconnect) {
		this.replacableConMap = replacableConMap;
		this.source = source;
		this.structURI = structURI;
		this.isSourceReconnect = isSourceReconnect;

		repairs = new CompositeChange("");
		if (TypeLibraryManager.INSTANCE.getTypeEntryForURI(source).getType() instanceof final FBType type) {
			new BlockTypeInstanceSearch(type.getTypeEntry()).performSearch().stream().map(FBNetworkElement.class::cast)
					.forEach(elem -> repairs.add(new RepairBrokenConnectionChange(EcoreUtil.getURI(elem),
							FBNetworkElement.class, structURI, replacableConMap, isSourceReconnect)));
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeValidationData(final IProgressMonitor pm) {
		// TODO Auto-generated method stub

	}

	@Override
	public RefactoringStatus isValid(final IProgressMonitor pm) throws CoreException, OperationCanceledException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Change perform(final IProgressMonitor pm) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getModifiedElement() {
		// TODO Auto-generated method stub
		return null;
	}
}
