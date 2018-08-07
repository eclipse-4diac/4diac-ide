/*******************************************************************************
 * Copyright (c) 2011 - 2017 Profactor GmbH, TU Wien ACIN, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.fordiac.ide.model.Palette.AdapterTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.PaletteGroup;
import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.typelibrary.DataTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibrary;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.resource.ImageDescriptor;

public final class FBInterfacePaletteFactory {
	private static final String PALETTE_DOCK_LOCATION = "FBInterfacePaletteFactory.Location"; //$NON-NLS-1$
	private static final String PALETTE_SIZE = "FBInterfacePaletteFactory.Size"; //$NON-NLS-1$
	private static final String PALETTE_STATE = "FBInterfacePaletteFactory.State"; //$NON-NLS-1$

	public static FlyoutPreferences createPalettePreferences() {
		boolean val = Activator.getDefault().getPreferenceStore().contains(PALETTE_STATE);
		
		FlyoutPreferences preferences = new FlyoutPreferences() {

			@Override
			public int getDockLocation() {
				return Activator.getDefault().getPreferenceStore().getInt(
						PALETTE_DOCK_LOCATION);
			}

			@Override
			public int getPaletteState() {
				return Activator.getDefault().getPreferenceStore()
						.getInt(PALETTE_STATE);

			}

			@Override
			public int getPaletteWidth() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_SIZE);

			}

			@Override
			public void setDockLocation(final int location) {
				Activator.getDefault().getPreferenceStore().setValue(
						PALETTE_DOCK_LOCATION, location);
			}

			@Override
			public void setPaletteState(final int state) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_STATE,
						state);

			}

			@Override
			public void setPaletteWidth(final int width) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_SIZE,
						width);

			}
		};
		
		if(!val){
			preferences.setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			preferences.setPaletteWidth(125);
		}
		
		return preferences;
	}

	public static PaletteRoot createPalette(Palette systemPalette) {
		final PaletteRoot palette = new PaletteRoot();
		fillPalette(systemPalette, palette);
		return palette;
	}

	private static void fillPalette(Palette systemPalette, final PaletteRoot palette) {
		PaletteDrawer drawer = new PaletteDrawer("EventTypes");

		for (DataType type : EventTypeLibrary.getInstance().getEventTypes()){
			ImageDescriptor desc = FordiacImage.ICON_DataType.getImageDescriptor();
			CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
					type.getName(), type.getComment(), new DataTypeCreationFactory(type), desc, desc);
			drawer.add(combined);
		}
		palette.add(drawer);
		
		drawer = new PaletteDrawer("DataTypes");

		for (DataType dataType : DataTypeLibrary.getInstance().getDataTypesSorted()) {
			ImageDescriptor desc = FordiacImage.ICON_DataType.getImageDescriptor();
			CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
					dataType.getName(), dataType.getComment(), new DataTypeCreationFactory(dataType), desc, desc);
			drawer.add(combined);
		}		
		palette.add(drawer);
				
		fillPalette(palette, systemPalette);
		
		
	}
	
	private static void fillPalette(final PaletteRoot palette,
			final Palette systemPalette) {
		Palette pal = null;
		if (systemPalette == null) {
			pal = TypeLibrary.getInstance().getPalette();
		} else {
			pal = systemPalette;
		}	
		
		PaletteDrawer drawer = createGroup(pal.getRootGroup(), "", palette); //$NON-NLS-1$
		if (drawer.getChildren().size() > 0) {
			palette.add(drawer);
		}
	}

	private static PaletteDrawer createGroup(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group,
			final String parentGroup, final PaletteRoot palette) {
		
		String newParent = ""; //$NON-NLS-1$
		if(!group.getLabel().equals("Root Group")){
			newParent += parentGroup.equals("") ? parentGroup + "." + group.getLabel() //$NON-NLS-1$ //$NON-NLS-2$
					: group.getLabel();
		}
		
		
		for (Iterator<PaletteGroup> iterator = group.getSubGroups().iterator(); iterator
				.hasNext();) {
			PaletteGroup paletteGroup = iterator.next();
			PaletteDrawer drawer = createGroup(paletteGroup, newParent, palette);
			if (drawer.getChildren().size() > 0) {
				palette.add(drawer);
			}

		}
		PaletteDrawer paletteContainer = new PaletteDrawer(
				!parentGroup.equals("") ? parentGroup + "." + group.getLabel() //$NON-NLS-1$ //$NON-NLS-2$
				: group.getLabel());
		paletteContainer.addAll(createAdapterEntries(group));
		return paletteContainer;
	}

	private static List<PaletteEntry> createAdapterEntries(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		List<PaletteEntry> entries = new ArrayList<PaletteEntry>();
		
		for (org.eclipse.fordiac.ide.model.Palette.PaletteEntry paletteEntry : group.getEntries()) {
			if(paletteEntry instanceof AdapterTypePaletteEntry){
				AdapterTypePaletteEntry entry = (AdapterTypePaletteEntry) paletteEntry;
				ImageDescriptor desc = FordiacImage.ICON_DataType.getImageDescriptor();
				CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
						entry.getLabel(), entry.getType().getComment(), 
						new DataTypeCreationFactory(entry.getType()), desc, desc);
				if (combined != null) {
					entries.add(combined);
				}
			}
		}
		return entries;
	}
}
