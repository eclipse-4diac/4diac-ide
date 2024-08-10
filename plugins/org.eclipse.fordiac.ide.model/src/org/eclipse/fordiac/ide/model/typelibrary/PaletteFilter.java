/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University Linz
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
package org.eclipse.fordiac.ide.model.typelibrary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.ui.dialogs.SearchPattern;

public class PaletteFilter {

	private final TypeLibrary typeLib;
	private final SearchPattern matcher = new SearchPattern();

	public PaletteFilter(final TypeLibrary typeLib) {
		this.typeLib = typeLib;

	}

	public List<TypeEntry> findFBAndSubappTypes(final String searchString) {
		return sortResultsByBestMatch(searchString, findTypes(searchString,
				Stream.concat(typeLib.getFbTypes().stream(), typeLib.getSubAppTypes().stream()))).toList();
	}

	private Stream<TypeEntry> findTypes(final String searchString, final Stream<TypeEntry> stream) {
		setSearchPattern(searchString);
		return stream.filter(entry -> matcher.matches(entry.getFullTypeName()))
				// only forward types that can be loaded correctly
				.filter(entry -> (null != entry.getType()));
	}

	private void setSearchPattern(final String searchString) {
		// emulate behavior as in PatternFilter used in the pallteview and typenavigator
		// search
		String searchPattern = searchString;
		if (!searchString.endsWith(" ")) { //$NON-NLS-1$
			searchPattern += "*"; //$NON-NLS-1$
		}
		searchPattern = "*" + searchPattern; //$NON-NLS-1$
		matcher.setPattern(searchPattern);
	}

	private Stream<TypeEntry> sortResultsByBestMatch(final String searchString, final Stream<TypeEntry> results) {
		final String searchPattern = searchString;
		final List<TypeEntry> sortedResults = results.sorted(Comparator.comparing(TypeEntry::getTypeName)).toList();
		final List<TypeEntry> exact = new ArrayList<>();
		final List<TypeEntry> right = new ArrayList<>();
		final List<TypeEntry> rest = new ArrayList<>();

		for (final TypeEntry entry : sortedResults) {
			matcher.setPattern(searchPattern);
			if (matcher.matches(entry.getTypeName())) {
				exact.add(entry);
			} else {
				matcher.setPattern(searchPattern + "*"); //$NON-NLS-1$
				if (matcher.matches(entry.getTypeName())) {
					right.add(entry);
				} else {
					rest.add(entry);
				}
			}
		}
		return Stream.of(exact, right, rest).flatMap(Collection::stream);
	}
}
