/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
 *
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial implementation and/or documentation
 *******************************************************************************/

package org.eclipse.fordiac.util.marker;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.ui.editors.EditorUtils;
import org.eclipse.gef.GraphicalEditPart;

public class MarkerStore {

	public static Optional<MarkerStore> getStoreFromEditor() {
		if (EditorUtils.getCurrentActiveEditor() instanceof final MarkerStoreProvider provider) {
			return Optional.ofNullable(provider.getMarkerStore());
		}
		return Optional.empty();
	}

	/*
	 * Maps Marker descriptor ID to Editparts
	 */
	private final HashMap<String, GraphicalEditPart> store = new HashMap<>();

	public EObject getMarkedElement(final String markerID) {
		final GraphicalEditPart ep = store.get(markerID);
		if (ep != null && ep.getModel() instanceof final EObject element) {
			return element;
		}
		return null;
	}

	public GraphicalEditPart getMarkedEditPart(final String markerID) {
		return store.get(markerID);
	}

	public boolean isMarkedElement(final String markerID, final EObject element) {
		return EcoreUtil.equals(getMarkedElement(markerID), element);
	}

	public boolean isMarkedEditPart(final GraphicalEditPart ep) {
		return store.containsValue(ep);
	}

	public boolean isMarkedElement(final String qualifiedName) {
		return store.entrySet().stream().map(e -> e.getValue().getModel()).filter(INamedElement.class::isInstance)
				.map(INamedElement.class::cast).anyMatch(e -> e.getQualifiedName().equals(qualifiedName));
	}

	public boolean hasMarkerEntry(final String markerID) {
		return store.get(markerID) != null;
	}

	public void storeEditPart(final String markerID, final GraphicalEditPart ep) {
		store.put(markerID, ep);
	}

	public GraphicalEditPart removeElementByID(final String markerID) {
		return store.remove(markerID);
	}

	public void removeEditPart(final GraphicalEditPart ep) {
		final var entrySet = store.entrySet();
		for (final Entry<String, GraphicalEditPart> entry : entrySet) {
			if (entry.getValue().equals(ep)) {
				store.remove(entry.getKey());
			}
		}
	}

}
