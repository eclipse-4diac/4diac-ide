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

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.search.ISearchContext;

/**
 * Search context for searching within a given LibraryElement
 *
 */
final class LibraryElementSearchContext implements ISearchContext {
	private final LibraryElement typeEditable;

	LibraryElementSearchContext(final LibraryElement typeEditable) {
		this.typeEditable = typeEditable;
	}

	@Override
	public Stream<URI> getTypes() {
		return Stream.of(typeEditable.getTypeEntry().getURI());
	}

	@Override
	public Collection<URI> getSubappTypes() {
		return Collections.emptyList();
	}

	@Override
	public LibraryElement getLibraryElement(final URI uri) {
		if (uri.equals(typeEditable.getTypeEntry().getURI())) {
			return typeEditable;
		}
		return null;
	}

	@Override
	public Collection<URI> getFBTypes() {
		return Collections.emptyList();
	}

	@Override
	public Collection<URI> getAllTypes() {
		return Collections.emptyList();
	}
}