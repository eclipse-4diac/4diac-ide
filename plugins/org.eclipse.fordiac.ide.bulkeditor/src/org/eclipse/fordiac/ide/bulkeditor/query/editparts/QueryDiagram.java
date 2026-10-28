/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.bulkeditor.query.editparts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.bulkeditor.query.QueryModelHelper;

/** Contents of the query viewer: the query and its collapsed elements. */
public class QueryDiagram {

	private final EObject queryRoot;
	private final Set<EObject> collapsedElements = new HashSet<>();

	public QueryDiagram(final EObject queryRoot) {
		this.queryRoot = queryRoot;
	}

	public EObject getQueryRoot() {
		return queryRoot;
	}

	public boolean isCollapsed(final EObject element) {
		return collapsedElements.contains(element);
	}

	public void toggleCollapsed(final EObject element) {
		if (!collapsedElements.remove(element)) {
			collapsedElements.add(element);
		}
	}

	public List<EObject> getVisibleChildren(final EObject element) {
		return isCollapsed(element) ? List.of() : QueryModelHelper.getChildNodes(element);
	}

	/** @return all elements shown as node, parents before their children */
	public List<EObject> getVisibleElements() {
		if (queryRoot == null) {
			return List.of();
		}
		final List<EObject> elements = new ArrayList<>();
		collectVisibleElements(queryRoot, elements);
		return elements;
	}

	private void collectVisibleElements(final EObject element, final List<EObject> elements) {
		elements.add(element);
		getVisibleChildren(element).forEach(child -> collectVisibleElements(child, elements));
	}
}
