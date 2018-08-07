/*******************************************************************************
 * Copyright (c) 2008, 2009, 2013 - 2016 Profactor GmbH, fortiss GmbH
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
package org.eclipse.fordiac.ide.fbtypeeditor.ecc;

import org.eclipse.fordiac.ide.util.imageprovider.FordiacImage;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.jface.resource.ImageDescriptor;

public final class ECCPaletteFactory {
	private static final String PALETTE_DOCK_LOCATION = "ECCPaletteFactory.Location"; //$NON-NLS-1$
	private static final String PALETTE_SIZE = "ECCPaletteFactory.Size"; //$NON-NLS-1$
	private static final String PALETTE_STATE = "ECCPaletteFactory.State"; //$NON-NLS-1$

	public static FlyoutPreferences createPalettePreferences() {
		boolean val = Activator.getDefault().getPreferenceStore().contains(PALETTE_STATE);
		FlyoutPreferences preferences = new FlyoutPreferences() {
			@Override
			public int getDockLocation() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
			}

			@Override
			public int getPaletteState() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_STATE);

			}

			@Override
			public int getPaletteWidth() {
				return Activator.getDefault().getPreferenceStore().getInt(PALETTE_SIZE);

			}

			@Override
			public void setDockLocation(final int location) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
			}

			@Override
			public void setPaletteState(final int state) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_STATE, state);

			}

			@Override
			public void setPaletteWidth(final int width) {
				Activator.getDefault().getPreferenceStore().setValue(PALETTE_SIZE, width);

			}
		};

		if (!val) {
			preferences.setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			preferences.setPaletteWidth(125);
		}
		return preferences;
	}

	public static PaletteRoot createPalette() {
		final PaletteRoot palette = new PaletteRoot();
		fillPalette(palette);
		return palette;
	}

	private static void fillPalette(final PaletteRoot palette) {
		PaletteGroup eccGroup = new PaletteGroup(Messages.ECCPaletteFactory_LABEL_ECCGroup);
		ImageDescriptor desc = FordiacImage.ICON_ECState.getImageDescriptor();
		CombinedTemplateCreationEntry combined = new CombinedTemplateCreationEntry(
				Messages.ECCPaletteFactory_LABEL_State, Messages.ECCPaletteFactory_TOOLTIP_State,
				new StateCreationFactory(), desc, desc);
		eccGroup.add(combined);
		desc = FordiacImage.ICON_ECAction.getImageDescriptor();
		combined = new CombinedTemplateCreationEntry(Messages.ECCPaletteFactory_LABEL_Action,
				Messages.ECCPaletteFactory_TOOLTIP_Action, new ActionCreationFactory(), desc, desc);
		eccGroup.add(combined);
		desc = FordiacImage.ICON_Algorithm.getImageDescriptor();
		combined = new CombinedTemplateCreationEntry(Messages.ECCPaletteFactory_LABEL_STAlgorithm,
				Messages.ECCPaletteFactory_TOOLTIP_STAlgorithm, new STAlgorithmCreationFactory(), desc, desc);
		eccGroup.add(combined);
		palette.add(eccGroup);
	}

}
