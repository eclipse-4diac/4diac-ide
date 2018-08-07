/*******************************************************************************
 * Copyright (c) 2008, 2012 - 2016 Profactor GbmH, TU Wien ACIN, fortiss GmbH,  
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl 
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.systemconfiguration.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.fordiac.ide.gef.utilities.TemplateCreationFactory;
import org.eclipse.fordiac.ide.model.Palette.DeviceTypePaletteEntry;
import org.eclipse.fordiac.ide.model.Palette.Palette;
import org.eclipse.fordiac.ide.model.Palette.ResourceTypeEntry;
import org.eclipse.fordiac.ide.model.Palette.SegmentTypePaletteEntry;
import org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
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

	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "SystemConfPaletteFactory.Location";//$NON-NLS-1$

	/** Preference ID used to persist the palette size. */
	private static final String PALETTE_SIZE = "SystemConfPaletteFactory.Size";//$NON-NLS-1$

	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "SystemConfPaletteFactory.State";//$NON-NLS-1$

	/**
	 * Return a FlyoutPreferences instance used to save/load the preferences of a
	 * flyout palette.
	 * 
	 * @return the flyout preferences
	 */
	public static FlyoutPreferences createPalettePreferences() {
		boolean val = org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault().getPreferenceStore().contains(PALETTE_STATE);
				
		FlyoutPreferences preferences = new FlyoutPreferences() {
			
			@Override
			public int getDockLocation() {
				return org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault()
						.getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}

			@Override
			public int getPaletteState() {
				return org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault()
						.getPreferenceStore().getInt(PALETTE_STATE);

			}

			@Override
			public int getPaletteWidth() {
				return org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault()
						.getPreferenceStore().getInt(PALETTE_SIZE);

			}

			@Override
			public void setDockLocation(final int location) {
				org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault()
						.getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}

			@Override
			public void setPaletteState(final int state) {
				org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault()
						.getPreferenceStore().setValue(PALETTE_STATE, state);

			}

			@Override
			public void setPaletteWidth(final int width) {
				org.eclipse.fordiac.ide.systemconfiguration.Activator.getDefault()
						.getPreferenceStore().setValue(PALETTE_SIZE, width);

			}
		};
		
		if(!val){
			preferences.setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			preferences.setPaletteWidth(125);
		}
		
		return preferences;
	}

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
		fillPalette(palette, system);

		// update the palette if it changes!
		// FIXME
		system.getPalette().eAdapters().add(new EContentAdapter() {

			@Override
			public void notifyChanged(final Notification notification) {
				Display.getDefault().syncExec(() -> {
					palette.setVisible(false);
					palette.getChildren().clear();
					fillPalette(palette, system);
					palette.setVisible(true);
				});
			}
		});
		return palette;
	}


	/**
	 * Fills a given GEF palette according to an palette model from the core
	 * model. Independent from the palette model an additional group with default
	 * tools is added. FIXME subgroups!!!!!!!
	 * 
	 * @param palette
	 *          the empty GEF palette
	 * @param paletteModel
	 *          the EMF model
	 */
	private static void fillPalette(final PaletteRoot palette,
			final org.eclipse.fordiac.ide.model.libraryElement.AutomationSystem system) {
		Palette pal = system.getPalette();
		
		for (org.eclipse.fordiac.ide.model.Palette.PaletteGroup group : pal.getRootGroup().getSubGroups()) {
			if(!group.getEntries().isEmpty()){
				createDevGroup(palette, group);
			}
			if(!group.getEntries().isEmpty()){
				createRESGroup(palette, group);
			}
			if(!group.getEntries().isEmpty()){
				createSEGGroup(palette, group);
			}
		}
	}

	private static PaletteDrawer createRESGroup(final PaletteRoot palette,
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		PaletteDrawer paletteContainer = new PaletteDrawer(group.getLabel());
		
		paletteContainer.addAll(createRESEntries(group));
		
		if(!paletteContainer.getChildren().isEmpty()){ 
			palette.add(paletteContainer);
		}

		return paletteContainer;
	}

	private static PaletteDrawer createDevGroup(final PaletteRoot palette,
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		PaletteDrawer paletteContainer = new PaletteDrawer(group.getLabel());
		
		paletteContainer.addAll(createDEVEntries(group));
		
		if(!paletteContainer.getChildren().isEmpty()){ 
			palette.add(paletteContainer);
		}

		return paletteContainer;
	}

	private static PaletteDrawer createSEGGroup(final PaletteRoot palette,
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		PaletteDrawer paletteContainer = new PaletteDrawer(group.getLabel());
			
		paletteContainer.addAll(createSEGEntries(group));
		
		if(!paletteContainer.getChildren().isEmpty()){ 
			palette.add(paletteContainer);
		}
		return paletteContainer;
	}

	private static List<PaletteEntry> createRESEntries(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		List<PaletteEntry> entries = new ArrayList<>();
		
		for (org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry : group.getEntries()) {
			if(entry instanceof ResourceTypeEntry){
				PaletteEntry paletteEntry = createCreationEntry(entry, FordiacImage.ICON_Resource.getImageDescriptor());
				if (paletteEntry != null) {
					entries.add(paletteEntry);
				}				
			}
		}
		return entries;
	}

	private static List<PaletteEntry> createDEVEntries(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		List<PaletteEntry> entries = new ArrayList<>();
		for (org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry : group.getEntries()) {
			if(entry instanceof DeviceTypePaletteEntry){
				PaletteEntry paletteEntry = createCreationEntry(entry, FordiacImage.ICON_Device.getImageDescriptor());
				if (paletteEntry != null) {
					entries.add(paletteEntry);				
				}
			}
		}
		return entries;
	}

	private static List<PaletteEntry> createSEGEntries(
			final org.eclipse.fordiac.ide.model.Palette.PaletteGroup group) {
		List<PaletteEntry> entries = new ArrayList<>();
		for (org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry : group.getEntries()) {
			if(entry instanceof SegmentTypePaletteEntry){
				PaletteEntry paletteEntry = createCreationEntry(entry, FordiacImage.ICON_Segment.getImageDescriptor());
				if (paletteEntry != null) {
					entries.add(paletteEntry);
				}
			}
		}
		return entries;
	}

	/**
	 * Creates a new PaletteEntry
	 * 
	 * @param entry
	 * @return a new PaletteEntry
	 */
	private static PaletteEntry createCreationEntry(
			final org.eclipse.fordiac.ide.model.Palette.PaletteEntry entry,
			final ImageDescriptor desc) {
		LibraryElement type = entry.getType();
		if (type == null) {
			return null;
		}
		return new CombinedTemplateCreationEntry(type.getName(), type.getComment(), new TemplateCreationFactory(entry),
				desc, desc);
	}
	
	private SystemConfPaletteFactory() {
		throw new UnsupportedOperationException("Class SystemconfPaletteFactory should not be insantiated!");
	}

}
