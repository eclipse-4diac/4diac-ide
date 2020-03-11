/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import java.util.Map.Entry;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.preferences.PaletteFlyoutPreferences;
import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.ui.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;

/**
 * This class is used to create a GEF palette for the user interface. The
 * creation is mainly based on the core model of the
 * <code>org.eclipse.fordiac.ide.model</code> plugin.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 */
public final class SystemConfPaletteFactory {

	public static final PaletteFlyoutPreferences PALETTE_PREFERENCES = new PaletteFlyoutPreferences(
			"SystemConfPaletteFactory.Location", //$NON-NLS-1$
			"SystemConfPaletteFactory.Size", //$NON-NLS-1$
			"SystemConfPaletteFactory.State");//$NON-NLS-1$

	/**
	 * Creates the PaletteRoot for a PaletteViewer with the contents from
	 * FBTypePalette.
	 *
	 * @param system the system
	 *
	 * @return PaletteRoot
	 */
	public static PaletteRoot createPalette(final AutomationSystem system) {
		final PaletteRoot palette = new PaletteRoot();
		fillPalette(palette, system.getPalette());

		system.getPalette().eAdapters().add(new EContentAdapter() {

			@Override
			public void notifyChanged(final Notification notification) {
				Display.getDefault().syncExec(() -> {
					palette.setVisible(false);
					palette.getChildren().clear();
					fillPalette(palette, system.getPalette());
					palette.setVisible(true);
				});
			}
		});
		return palette;
	}

	/**
	 * Fills a given GEF palette according to an palette model from the core model.
	 * Independent from the palette model an additional group with default tools is
	 * added.
	 *
	 * @param palette      the empty GEF palette
	 * @param paletteModel the EMF model
	 */
	private static void fillPalette(final PaletteRoot palette, final Palette typePalette) {
		createDevGroup(palette, typePalette);
		createRESGroup(palette, typePalette);
		createSEGGroup(palette, typePalette);
	}

	private static PaletteDrawer createDevGroup(final PaletteRoot palette, final Palette typePalette) {
		PaletteDrawer paletteContainer = new PaletteDrawer("Devices");

		for (Entry<String, DeviceTypePaletteEntry> entry : typePalette.getDeviceTypes().entrySet()) {
			PaletteEntry paletteEntry = createCreationEntry(entry.getValue(),
					FordiacImage.ICON_DEVICE.getImageDescriptor());
			if (paletteEntry != null) {
				paletteContainer.add(paletteEntry);
			}
		}

		if (!paletteContainer.getChildren().isEmpty()) {
			palette.add(paletteContainer);
		}

		return paletteContainer;
	}

	private static PaletteDrawer createRESGroup(final PaletteRoot palette, final Palette typePalette) {
		PaletteDrawer paletteContainer = new PaletteDrawer("Resources");

		for (Entry<String, ResourceTypeEntry> entry : typePalette.getResourceTypes().entrySet()) {
			PaletteEntry paletteEntry = createCreationEntry(entry.getValue(),
					FordiacImage.ICON_RESOURCE.getImageDescriptor());
			if (paletteEntry != null) {
				paletteContainer.add(paletteEntry);
			}
		}

		if (!paletteContainer.getChildren().isEmpty()) {
			palette.add(paletteContainer);
		}

		return paletteContainer;
	}

	private static PaletteDrawer createSEGGroup(final PaletteRoot palette, final Palette typePalette) {
		PaletteDrawer paletteContainer = new PaletteDrawer("Segments");

		for (Entry<String, SegmentTypePaletteEntry> entry : typePalette.getSegmentTypes().entrySet()) {
			PaletteEntry paletteEntry = createCreationEntry(entry.getValue(),
					FordiacImage.ICON_SEGMENT.getImageDescriptor());
			if (paletteEntry != null) {
				paletteContainer.add(paletteEntry);
			}
		}

		if (!paletteContainer.getChildren().isEmpty()) {
			palette.add(paletteContainer);
		}
		return paletteContainer;
	}

	/**
	 * Creates a new PaletteEntry
	 *
	 * @param entry
	 * @return a new PaletteEntry
	 */
	private static PaletteEntry createCreationEntry(final org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry,
			final ImageDescriptor desc) {
		LibraryElement type = entry.getType();
		if (type == null) {
			return null;
		}
		return new CombinedTemplateCreationEntry(type.getName(), type.getComment(), new TemplateCreationFactory(entry),
				desc, desc);
	}

	private SystemConfPaletteFactory() {
		throw new UnsupportedOperationException("Class SystemconfPaletteFactory should not be insantiated!"); //$NON-NLS-1$
	}

}
