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
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.ui.dialogs.SearchPattern;

public class PaletteFilter {

	private final Palette palette;
	private final SearchPattern matcher = new SearchPattern();

	public PaletteFilter(final Palette palette) {
		this.palette = palette;

	}

	public List<PaletteEntry> findFBAndSubappTypes(final String searchString) {
		final Stream<Entry<String, ? extends PaletteEntry>> stream = Stream.concat(palette.getFbTypes().entrySet().stream(),
				palette.getSubAppTypes().entrySet().stream());
		return sortResultsByBestMatch(searchString, findTypes(searchString, stream));
	}

	public List<PaletteEntry> findTypes(final String searchString,
			final Stream<Entry<String, ? extends PaletteEntry>> stream) {
		setSearchPattern(searchString);
		return stream.filter(entry -> matcher.matches(entry.getKey()))
				.filter(entry -> (null != entry.getValue().getType())). // only forward types that can be loaded //
				// correctly
				map(Entry<String, ? extends PaletteEntry>::getValue).collect(Collectors.toList());
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

	private List<PaletteEntry> sortResultsByBestMatch(final String searchString, final List<PaletteEntry> results) {
		final String searchPattern = searchString;
		final List<PaletteEntry> sortedResults = results;
		sortedResults.sort(Comparator.comparing(PaletteEntry::getLabel));
		final List<PaletteEntry> exact = new ArrayList<>();
		final List<PaletteEntry> right = new ArrayList<>();
		final List<PaletteEntry> rest = new ArrayList<>();

		for (final PaletteEntry entry : sortedResults) {
			matcher.setPattern(searchPattern);
			if (matcher.matches(entry.getLabel())) {
				exact.add(entry);
				continue;
			}
			matcher.setPattern(searchPattern + "*"); //$NON-NLS-1$
			if (matcher.matches(entry.getLabel())) {
				right.add(entry);
				continue;
			}
			rest.add(entry);
		}
		return Stream.of(exact, right, rest).flatMap(Collection::stream).collect(Collectors.toList());
	}
}
