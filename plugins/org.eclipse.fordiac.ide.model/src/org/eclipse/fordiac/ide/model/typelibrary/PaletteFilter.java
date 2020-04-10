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

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.ui.internal.misc.StringMatcher;

public class PaletteFilter {

	private final Palette palette;

	public PaletteFilter(Palette palette) {
		this.palette = palette;

	}

	public List<PaletteEntry> findFBAndSubappTypes(final String searchString) {
		Stream<Entry<String, ? extends PaletteEntry>> stream = Stream.concat(palette.getFbTypes().entrySet().stream(),
				palette.getSubAppTypes().entrySet().stream());
		return findTypes(searchString, stream);
	}

	public List<PaletteEntry> findTypes(final String searchString,
			final Stream<Entry<String, ? extends PaletteEntry>> stream) {
		final StringMatcher matcher = setMatcher(searchString);
		return stream.filter(entry -> matcher.match(entry.getKey())).map(entry -> entry.getValue())
				.collect(Collectors.toList());
	}

	private StringMatcher setMatcher(final String searchString) {
		// emulate behavior as in PatternFilter used in the pallteview and typenavigator
		// search
		String searchPattern = searchString;
		if (!searchString.endsWith(" ")) { //$NON-NLS-1$
			searchPattern += "*"; //$NON-NLS-1$
		}
		searchPattern = "*" + searchPattern; //$NON-NLS-1$
		return new StringMatcher(searchPattern, true, false);
	}
}
