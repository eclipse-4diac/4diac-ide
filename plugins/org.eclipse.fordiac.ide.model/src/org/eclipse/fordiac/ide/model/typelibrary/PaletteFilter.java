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

public class PaletteFilter {

	public interface IPaletteEntryFilter {

		boolean handleType(PaletteEntry entry);
	}

	public static final IPaletteEntryFilter FB_AND_SUBAPP_TYPES = entry -> (entry instanceof FBTypePaletteEntry)
			|| (entry instanceof SubApplicationTypePaletteEntry);

	private final Palette palette;

	public PaletteFilter(Palette palette) {
		this.palette = palette;
	}

	public List<PaletteEntry> findFBAndSubappTypes(final String searchString) {
		return findTypes(searchString, FB_AND_SUBAPP_TYPES);
	}

	public List<PaletteEntry> findTypes(final String searchString, final IPaletteEntryFilter filter) {
		return checkGroupsForEntries(palette.getRootGroup(), searchString.toLowerCase(), filter);
	}

	private static List<PaletteEntry> checkGroupsForEntries(final PaletteGroup group, final String searchString,
			final IPaletteEntryFilter filter) {
		List<PaletteEntry> types = new ArrayList<>();
		for (PaletteEntry entry : group.getEntries()) {
			if (filter.handleType(entry) && entry.getLabel().toLowerCase().startsWith(searchString)) {
				types.add(entry);
			}
		}
		for (PaletteGroup pGroup : group.getSubGroups()) {
			types.addAll(checkGroupsForEntries(pGroup, searchString, filter));
		}
		return types;
	}

}
