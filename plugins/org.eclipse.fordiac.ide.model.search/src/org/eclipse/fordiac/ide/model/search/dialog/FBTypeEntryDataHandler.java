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

import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.search.types.BlockTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class FBTypeEntryDataHandler extends AbstractTypeEntryDataHandler<TypeEntry> {
	public FBTypeEntryDataHandler(final TypeEntry typeEntry) {
		super(typeEntry);
	}

	@Override
	public Map<INamedElement, TypeEntry> createInputSet(final TypeEntry inputTypeEntry) {
		final BlockTypeInstanceSearch search = new BlockTypeInstanceSearch(typeEntry);
		return search.performSearch().stream().filter(INamedElement.class::isInstance).map(INamedElement.class::cast)
				.collect(Collectors.toMap(el -> el, el -> inputTypeEntry));
	}

}
