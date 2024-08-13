/*******************************************************************************
 * Copyright (c) 2011, 2024 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 *                          Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor;

import org.eclipse.fordiac.ide.gef.preferences.PaletteFlyoutPreferences;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.typelibrary.AdapterTypeEntry;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.jface.resource.ImageDescriptor;

public final class FBInterfacePaletteFactory {
	public static final PaletteFlyoutPreferences PALETTE_PREFERENCES = new PaletteFlyoutPreferences(
			"FBInterfacePaletteFactory.Location", //$NON-NLS-1$
			"FBInterfacePaletteFactory.Size", //$NON-NLS-1$
			"FBInterfacePaletteFactory.State"); //$NON-NLS-1$

	public static PaletteRoot createPalette(final TypeLibrary typeLib, final boolean createAdapterEntries) {
		final PaletteRoot palette = new PaletteRoot();
		palette.add(createEventDrawer());
		palette.add(createDataTypeDrawer(typeLib));
		if (createAdapterEntries) {
			createAdapterEntry(palette, typeLib);
		}
		return palette;
	}

	private static PaletteDrawer createEventDrawer() {
		final PaletteDrawer drawer = new PaletteDrawer(Messages.FBInterfacePaletteFactory_EventTypes);

		for (final DataType type : EventTypeLibrary.getInstance().getEventTypes()) {
			final ImageDescriptor desc = FordiacImage.ICON_DATA_TYPE.getImageDescriptor();
			final CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(type.getName(),
					type.getComment(), new DataTypeCreationFactory(type), desc, desc);
			drawer.add(combined);
		}
		return drawer;
	}

	private static PaletteDrawer createDataTypeDrawer(final TypeLibrary typeLib) {
		PaletteDrawer drawer;
		drawer = new PaletteDrawer(Messages.FBInterfacePaletteFactory_DataTypes);

		for (final DataType dataType : typeLib.getDataTypeLibrary().getDataTypesSorted()) {
			final ImageDescriptor desc = FordiacImage.ICON_DATA_TYPE.getImageDescriptor();
			final CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(dataType.getName(),
					dataType.getComment(), new DataTypeCreationFactory(dataType), desc, desc);
			drawer.add(combined);
		}
		return drawer;
	}

	private static void createAdapterEntry(final PaletteRoot palette, final TypeLibrary typeLib) {
		final PaletteDrawer drawer = new PaletteDrawer(Messages.FBInterfacePaletteFactory_AdapterTypes);

		for (final AdapterTypeEntry entry : typeLib.getAdapterTypesSorted()) {
			final ImageDescriptor desc = FordiacImage.ICON_DATA_TYPE.getImageDescriptor();
			drawer.add(new CombinedTemplateCreationEntry(entry.getTypeName(), entry.getType().getComment(),
					new DataTypeCreationFactory(entry.getType()), desc, desc));
		}

		if (!drawer.getChildren().isEmpty()) {
			palette.add(drawer);
		}
	}

	private FBInterfacePaletteFactory() {
		throw new UnsupportedOperationException("Class FBInterfacePaletteFactory should not be created!\n"); //$NON-NLS-1$
	}

}
