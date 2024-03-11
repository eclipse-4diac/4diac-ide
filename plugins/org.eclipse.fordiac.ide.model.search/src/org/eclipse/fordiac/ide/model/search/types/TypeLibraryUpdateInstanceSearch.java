/*******************************************************************************
 * Copyright (c) 2023 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.search.types;

import java.util.Set;

import org.eclipse.fordiac.ide.model.libraryElement.ErrorMarkerFBNElement;
import org.eclipse.fordiac.ide.model.libraryElement.FB;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;

public class TypeLibraryUpdateInstanceSearch extends FBInstanceSearch {

	public TypeLibraryUpdateInstanceSearch(final Set<TypeEntry> typeEntries, final TypeLibrary typeLib) {
		super(new TypeLibUpdateSearchFilter(typeEntries, typeLib));
	}

	private static class TypeLibUpdateSearchFilter implements SearchFilter {

		private final Set<TypeEntry> typeEntries;
		private final TypeLibrary typeLib;

		public TypeLibUpdateSearchFilter(final Set<TypeEntry> typeEntries, final TypeLibrary typeLib) {
			this.typeEntries = typeEntries;
			this.typeLib = typeLib;
		}

		@Override
		public boolean apply(final INamedElement searchCandidate) {
			return searchCandidate instanceof final FB fb && typeEntries.contains(fb.getTypeEntry())
					|| searchCandidate instanceof final ErrorMarkerFBNElement errorMarker
							&& typeLib.getFBTypeEntry(errorMarker.getFullTypeName()) != null;
		}

	}
}
