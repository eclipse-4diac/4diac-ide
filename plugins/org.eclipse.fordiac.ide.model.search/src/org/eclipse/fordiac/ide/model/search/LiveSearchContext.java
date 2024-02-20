/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.search;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class LiveSearchContext implements ISearchContext {

	private final TypeLibrary typelib;

	public LiveSearchContext(final IProject project) {
		this.typelib = Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project));
	}

	public LiveSearchContext(final TypeLibrary typelib) {
		this.typelib = Objects.requireNonNull(typelib);
	}

	@Override
	public Collection<URI> getAllTypes() {
		return typelib.getAllTypes().stream().map(TypeEntry::getURI).filter(Objects::nonNull).toList();
	}

	@Override
	public LibraryElement getLibraryElement(final URI uri) {
		final TypeEntry typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForURI(uri);
		final LiveSearchTypeEntry liveEntry = new LiveSearchTypeEntry(typeEntry);
		return liveEntry.getLiveType();
	}

	@Override
	public Collection<URI> getSubappTypes() {
		return typelib.getSubAppTypes().values().stream().map(TypeEntry::getURI).filter(Objects::nonNull).toList();
	}

	@Override
	public Collection<URI> getFBTypes() {
		return typelib.getFbTypes().values().stream().map(TypeEntry::getURI).filter(Objects::nonNull).toList();
	}
}
