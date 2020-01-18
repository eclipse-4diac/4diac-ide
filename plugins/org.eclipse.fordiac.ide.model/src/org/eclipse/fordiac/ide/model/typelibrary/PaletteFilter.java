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
import java.util.List;

import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.ui.internal.misc.StringMatcher;

public class PaletteFilter {

	public interface IPaletteEntryFilter {

		boolean handleType(PaletteEntry entry);
	}

	public static final IPaletteEntryFilter FB_AND_SUBAPP_TYPES = entry -> (entry instanceof FBTypePaletteEntry)
			|| (entry instanceof SubApplicationTypePaletteEntry);

	private final Palette palette;
	private StringMatcher matcher;

	public PaletteFilter(Palette palette) {
		this.palette = palette;
	}

	public List<PaletteEntry> findFBAndSubappTypes(final String searchString) {
		return findTypes(searchString, FB_AND_SUBAPP_TYPES);
	}

	public List<PaletteEntry> findTypes(final String searchString, final IPaletteEntryFilter filter) {
		setMatcher(searchString);
		return checkGroupsForEntries(palette.getRootGroup(), filter);
	}

	public void setMatcher(final String searchString) {
		// emulate behavior as in PatternFilter used in the pallteview and typenavigator
		// search
		String searchPattern = searchString;
		if (!searchString.endsWith(" ")) { //$NON-NLS-1$
			searchPattern += "*"; //$NON-NLS-1$
		}
		searchPattern = "*" + searchPattern; //$NON-NLS-1$
		matcher = new StringMatcher(searchPattern, true, false);
	}

	private List<PaletteEntry> checkGroupsForEntries(final PaletteGroup group, final IPaletteEntryFilter filter) {
		List<PaletteEntry> types = new ArrayList<>();
		for (PaletteEntry entry : group.getEntries()) {
			if (filter.handleType(entry) && matcher.match(entry.getLabel())) {
				types.add(entry);
			}
		}
		for (PaletteGroup pGroup : group.getSubGroups()) {
			types.addAll(checkGroupsForEntries(pGroup, filter));
		}
		return types;
	}

}
