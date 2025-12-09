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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public abstract class AbstractTypeEntryDataHandler<T extends TypeEntry> {

	protected final T typeEntry;

	protected final Map<String, Set<EObject>> children;
	protected Map<EObject, T> inputSet;

	protected AbstractTypeEntryDataHandler(final T typeEntry) {
		this.typeEntry = typeEntry;
		children = new HashMap<>();
		setInputSet(new HashMap<>());
	}

	public T getTypeEntry() {
		return typeEntry;
	}

	public Map<EObject, T> getInputSet() {
		return inputSet;
	}

	public void setInputSet(final Map<EObject, T> inputSet) {
		this.inputSet = inputSet;
	}

	public Map<String, Set<EObject>> getChildren() {
		return children;
	}

	public Set<EObject> getChild(final String key) {
		return children.get(key);
	}

	public Set<EObject> putChild(final String key, final Set<EObject> child) {
		return children.put(key, child);
	}

	public Set<EObject> getCollectedElements() {
		return inputSet.keySet();
	}

	public T getTypeOfElement(final Object element) {
		if (element instanceof final EObject eObject && getInputSet().containsKey(eObject)) {
			return getInputSet().get(eObject);
		}
		return null;
	}

	public List<T> getTypeOfElementList(final List<?> elements) {
		return elements.stream().map(this::getTypeOfElement).toList();
	}

	public boolean loadInputSet() {
		inputSet = createInputSet(typeEntry);
		return inputSet.isEmpty();
	}

	protected abstract Map<EObject, T> createInputSet(final T inputTypeEntry);
}
