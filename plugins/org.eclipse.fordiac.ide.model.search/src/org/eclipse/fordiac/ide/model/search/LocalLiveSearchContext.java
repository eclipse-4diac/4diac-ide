/*******************************************************************************
 * Copyright (c) 2025 Primetals Technologies Austria GmbH
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
package org.eclipse.fordiac.ide.model.search;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager;

/**
 * SearchContext for all local TypeEntries (i.e. no linked TypeEntries)
 */
public class LocalLiveSearchContext extends AbstractLiveSearchContext {

	public LocalLiveSearchContext(final IProject project) {
		super(Objects.requireNonNull(TypeLibraryManager.INSTANCE.getTypeLibrary(project)));
	}

	public LocalLiveSearchContext(final TypeLibrary typelib) {
		super(Objects.requireNonNull(typelib));
	}

	@Override
	public Stream<URI> getTypes() {
		return getTypelib().getAllTypes().filter(e -> !e.getFile().isLinked(IResource.CHECK_ANCESTORS))
				.map(TypeEntry::getURI).filter(Objects::nonNull);
	}

}
