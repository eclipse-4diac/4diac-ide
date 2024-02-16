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
 *   Patrick Aigner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.search.types.FBInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class FBTypeEntryDataHandler extends AbstractTypeEntryDataHandler<TypeEntry> {
	public FBTypeEntryDataHandler(final TypeEntry typeEntry) {
		super(typeEntry);
	}

	@Override
	public HashMap<INamedElement, TypeEntry> createInputSet(final TypeEntry inputTypeEntry) {
		final HashMap<INamedElement, TypeEntry> inputElementsSet = new HashMap<>();
		final IProject project = typeEntry.getFile().getProject();
		final FBInstanceSearch search = new FBInstanceSearch(typeEntry.getFullTypeName());
		search.performProjectSearch(project).stream().forEach(sp -> {
			inputElementsSet.put(sp, inputTypeEntry);
		});

		search.performInternalFBSearch(inputTypeEntry.getTypeLibrary()).stream().forEach(sp -> {
			inputElementsSet.put(sp, inputTypeEntry);
		});

		return inputElementsSet;
	}

}
