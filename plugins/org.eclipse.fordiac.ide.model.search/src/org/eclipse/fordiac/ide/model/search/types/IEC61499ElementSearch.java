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
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.search.ISearchContext;

public class IEC61499ElementSearch {

	private final ISearchContext searchContext;
	private final IEC61499SearchFilter filter;
	private final ISearchChildrenProvider childrenProvider;
	private final List<EObject> results = new ArrayList<>();

	public IEC61499ElementSearch(final ISearchContext searchContext, final IEC61499SearchFilter filter,
			final ISearchChildrenProvider childrenProvider) {
		this.searchContext = searchContext;
		this.filter = filter;
		this.childrenProvider = childrenProvider;
	}

	public List<? extends EObject> performSearch() {
		results.clear();
		searchContext.getTypes().map(searchContext::getLibraryElement).forEach(this::checkElement);

		return results;
	}

	private void checkElement(final EObject el) {
		if (filter.apply(el)) {
			results.add(el);
		}
		if (childrenProvider.hasChildren(el)) {
			childrenProvider.getChildren(el).forEach(this::checkElement);
		}
	}

}
