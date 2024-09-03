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
 *   Michael Oberlehner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.dialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.search.types.AttributeTypeInstanceSearch;
import org.eclipse.fordiac.ide.model.typelibrary.AttributeTypeEntry;

public class AttributeTypeEntryDataHandler extends AbstractTypeEntryDataHandler<AttributeTypeEntry> {

	public AttributeTypeEntryDataHandler(final AttributeTypeEntry typeEntry) {
		super(typeEntry);
	}

	@Override
	protected Map<INamedElement, AttributeTypeEntry> createInputSet(final AttributeTypeEntry inputTypeEntry) {
		final AttributeTypeInstanceSearch search = new AttributeTypeInstanceSearch(inputTypeEntry);
		final List<? extends EObject> performSearch = search.performSearch();
		final Map<INamedElement, AttributeTypeEntry> result = new HashMap<>();
		performSearch.forEach(e -> result.put((INamedElement) e, inputTypeEntry));
		return result;
	}

}
