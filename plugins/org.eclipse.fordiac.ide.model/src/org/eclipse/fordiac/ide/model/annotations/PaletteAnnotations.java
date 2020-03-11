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
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.fordiac.ide.model.Activator;
import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.FBTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.SubApplicationTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.impl.PaletteEntryImpl;
import org.eclipse.fordiac.ide.model.dataimport.exceptions.TypeImportException;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;

public final class PaletteAnnotations {

	public static EList<AdapterTypePaletteEntry> getAdapterTypesSorted(final Palette palette) {
		EMap<String, AdapterTypePaletteEntry> adapterList = palette.getAdapterTypes();

		return ECollections.asEList(adapterList.entrySet().stream().map(entry -> entry.getValue())
				.sorted((o1, o2) -> Collator.getInstance().compare(o1.getLabel(), o2.getLabel()))
				.collect(Collectors.toList()));
	}

	public static void addTypeEntry(Palette palette, PaletteEntry entry) {
		entry.setPalette(palette);
		if (entry instanceof AdapterTypePaletteEntry) {
			palette.getAdapterTypes().put(entry.getLabel(), (AdapterTypePaletteEntry) entry);
		} else if (entry instanceof DeviceTypePaletteEntry) {
			palette.getDeviceTypes().put(entry.getLabel(), (DeviceTypePaletteEntry) entry);
		} else if (entry instanceof FBTypePaletteEntry) {
			palette.getFbTypes().put(entry.getLabel(), (FBTypePaletteEntry) entry);
		} else if (entry instanceof ResourceTypeEntry) {
			palette.getResourceTypes().put(entry.getLabel(), (ResourceTypeEntry) entry);
		} else if (entry instanceof SegmentTypePaletteEntry) {
			palette.getSegmentTypes().put(entry.getLabel(), (SegmentTypePaletteEntry) entry);
		} else if (entry instanceof SubApplicationTypePaletteEntry) {
			palette.getSubAppTypes().put(entry.getLabel(), (SubApplicationTypePaletteEntry) entry);
		} else {
			Activator.getDefault()
					.logError("Unknown pallet entry to be added to palette: " + entry.getClass().getName());
		}
	}

	public static void removeTypeEntry(Palette palette, PaletteEntry entry) {
		if (entry instanceof AdapterTypePaletteEntry) {
			palette.getAdapterTypes().remove(entry.getLabel());
		} else if (entry instanceof DeviceTypePaletteEntry) {
			palette.getDeviceTypes().remove(entry.getLabel());
		} else if (entry instanceof FBTypePaletteEntry) {
			palette.getFbTypes().remove(entry.getLabel());
		} else if (entry instanceof ResourceTypeEntry) {
			palette.getResourceTypes().remove(entry.getLabel());
		} else if (entry instanceof SegmentTypePaletteEntry) {
			palette.getSegmentTypes().remove(entry.getLabel());
		} else if (entry instanceof SubApplicationTypePaletteEntry) {
			palette.getSubAppTypes().remove(entry.getLabel());
		} else {
			Activator.getDefault()
					.logError("Unknown pallet entry to be removed from palette: " + entry.getClass().getName());
		}
	}

	public static LibraryElement loadType(PaletteEntryImpl paletteEntryImpl) {
		LibraryElement retval = null;
		try {
			retval = paletteEntryImpl.getTypeImporter(paletteEntryImpl.getPalette(), paletteEntryImpl.getFile())
					.importType();
		} catch (XMLStreamException | CoreException | TypeImportException e) {
			Activator.getDefault().logError("Error loading type: " + paletteEntryImpl.getFile().getName(), //$NON-NLS-1$
					e);
		}

		if (null == retval) {
			Activator.getDefault().logError("Error loading type: " + paletteEntryImpl.getFile().getName()); //$NON-NLS-1$
		}
		return retval;
	}

	private PaletteAnnotations() {
		throw new UnsupportedOperationException("The utility class PaletteAnnotations should not be instatiated");
	}

}
