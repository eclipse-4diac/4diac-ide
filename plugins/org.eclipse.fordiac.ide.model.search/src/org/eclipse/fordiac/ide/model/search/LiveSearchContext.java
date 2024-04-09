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
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

public class LiveSearchContext extends AbstractLiveSearchContext {

	public LiveSearchContext(final IProject project) {
		super(Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project)));
	}

	public LiveSearchContext(final TypeLibrary typelib) {
		super(Objects.requireNonNull(typelib));
	}

	@Override
	public Stream<URI> getTypes() {
		return getTypelib().getAllTypes().stream().map(TypeEntry::getURI).filter(Objects::nonNull);
	}

	@Override
	public Collection<URI> getAllTypes() {
		return getTypelib().getAllTypes().stream().map(TypeEntry::getURI).filter(Objects::nonNull).toList();
	}

	@Override
	public Collection<URI> getSubappTypes() {
		return getTypelib().getSubAppTypes().values().stream().map(TypeEntry::getURI).filter(Objects::nonNull).toList();
	}

	@Override
	public Collection<URI> getFBTypes() {
		return getTypelib().getFbTypes().values().stream().map(TypeEntry::getURI).filter(Objects::nonNull).toList();
	}

}
