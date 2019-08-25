/*******************************************************************************
 * Copyright (c) 2019 Johannes Kepler University
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
package org.eclipse.fordiac.ide.model.annotations;

import java.text.Collator;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;

public final class PaletteAnnotations {

	public static AdapterTypePaletteEntry getAdapterTypeEntry(final Palette palette, final String adapterTypeName) {
		PaletteEntry entry = palette.getTypeEntry(adapterTypeName);
		return (entry instanceof AdapterTypePaletteEntry) ? (AdapterTypePaletteEntry) entry : null;
	}

	public static EList<AdapterTypePaletteEntry> getAdapterTypes(final Palette palette) {
		return getAdapterGroup(palette.getRootGroup());
	}

	public static EList<AdapterTypePaletteEntry> getAdapterTypesSorted(final Palette palette) {
		EList<AdapterTypePaletteEntry> adapterList = getAdapterTypes(palette);
		ECollections.sort(adapterList, (o1, o2) -> Collator.getInstance().compare(o1.getLabel(), o2.getLabel()));
		return adapterList;
	}

	private static EList<AdapterTypePaletteEntry> getAdapterGroup(final PaletteGroup group) {
		EList<AdapterTypePaletteEntry> retVal = new BasicEList<>();
		group.getSubGroups().forEach(paletteGroup -> retVal.addAll(getAdapterGroup(paletteGroup)));
		retVal.addAll(getAdapterGroupEntries(group));
		return retVal;
	}

	private static EList<AdapterTypePaletteEntry> getAdapterGroupEntries(final PaletteGroup group) {
		EList<AdapterTypePaletteEntry> retVal = new BasicEList<>();
		group.getEntries().forEach(entry -> {
			if (entry instanceof AdapterTypePaletteEntry) {
				retVal.add((AdapterTypePaletteEntry) entry);
			}
		});
		return retVal;
	}

	private PaletteAnnotations() {
		throw new UnsupportedOperationException("The utility class PaletteAnnotations should not be instatiated");
	}

}
