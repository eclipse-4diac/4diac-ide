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
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public abstract class AbstractTypeEntryDataHandler<T extends TypeEntry> {

	protected final T typeEntry;

	protected final Map<String, Set<INamedElement>> children;
	protected HashMap<INamedElement, T> inputSet;

	public AbstractTypeEntryDataHandler(final T typeEntry) {
		this.typeEntry = typeEntry;
		children = new HashMap<>();
		setInputSet(new HashMap<>());
	}

	public T getTypeEntry() {
		return typeEntry;
	}

	public HashMap<INamedElement, T> getInputSet() {
		return inputSet;
	}

	public void setInputSet(final HashMap<INamedElement, T> inputSet) {
		this.inputSet = inputSet;
	}

	public Map<String, Set<INamedElement>> getChildren() {
		return children;
	}

	public Set<INamedElement> getChild(final String key) {
		return children.get(key);
	}

	public Set<INamedElement> putChild(final String key, final Set<INamedElement> child) {
		return children.put(key, child);
	}

	public Set<INamedElement> getCollectedElements() {
		return inputSet.keySet();
	}

	public T getTypeOfElement(final Object element) {
		if (element instanceof final INamedElement iNamedElement && getInputSet().containsKey(iNamedElement)) {
			return getInputSet().get(iNamedElement);
		}
		return null;
	}

	public List<T> getTypeOfElementList(final List<?> elements) {
		return elements.stream().map(e -> getTypeOfElement(e)).toList();
	}

	public abstract HashMap<INamedElement, T> createInputSet(final T inputTypeEntry);
}
