/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.resource;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;

public class LibraryElementXtextResource extends LazyLinkingResource {
	private LibraryElement libraryElement;
	private final List<EObject> additionalContent = new ArrayList<>();

	protected void updateInternalLibraryElement() {
		clearInternalLibraryElement();
		if (contents != null && !contents.isEmpty()) {
			if (libraryElement != null) {
				contents.add(EcoreUtil.copy(libraryElement));
			}
			contents.addAll(EcoreUtil.copyAll(additionalContent));
			relink();
		}
	}

	protected void clearInternalLibraryElement() {
		if (contents != null) {
			contents.removeIf(LibraryElement.class::isInstance);
			contents.removeAll(additionalContent);
		}
	}

	public void setLibraryElement(final LibraryElement libraryElement) {
		this.libraryElement = libraryElement;
		updateInternalLibraryElement();
	}

	public LibraryElement getLibraryElement() {
		return libraryElement;
	}

	public List<EObject> getAdditionalContent() {
		return additionalContent;
	}

	@Override
	public synchronized EObject getEObject(final String uriFragment) {
		try {
			return super.getEObject(uriFragment);
		} catch (final IllegalArgumentException e) {
			return null;
		}
	}
}
