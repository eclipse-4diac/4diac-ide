/*******************************************************************************
 * Copyright (c) 2016 fortiss GmbH.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.utilities;

import org.eclipse.fordiac.ide.application.ApplicationPlugin;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;

public enum FBNetworkFlyoutPreferences implements FlyoutPreferences {	
	INSTANCE;
	
	/** Preference ID used to persist the palette location. */
	private static final String PALETTE_DOCK_LOCATION = "FBNetworkPalette.Location"; //$NON-NLS-1$

	/** Preference ID used to persist the palette size. */
	private static final String PALETTE_SIZE = "FBNetworkPalette.Size"; //$NON-NLS-1$

	/** Preference ID used to persist the flyout palette's state. */
	private static final String PALETTE_STATE = "FBNetworkPalette.State"; //$NON-NLS-1$
	
	private FBNetworkFlyoutPreferences(){
		if(ApplicationPlugin.getDefault().getPreferenceStore().contains(PALETTE_STATE)){
			//ensure that when workspace is the first opened that the palette is opened with a given size
			setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			setPaletteWidth(200);
		}
	}

	@Override
	public int getDockLocation() {
		return ApplicationPlugin.getDefault().getPreferenceStore().getInt(PALETTE_DOCK_LOCATION);
	}
	
	@Override
	public int getPaletteState() {
		return ApplicationPlugin.getDefault().getPreferenceStore().getInt(PALETTE_STATE);
	}

	@Override
	public int getPaletteWidth() {
		return ApplicationPlugin.getDefault().getPreferenceStore().getInt(PALETTE_SIZE);
	}

	@Override
	public void setDockLocation(final int location) {
		ApplicationPlugin.getDefault().getPreferenceStore().setValue(PALETTE_DOCK_LOCATION, location);
	}

	@Override
	public void setPaletteState(final int state) {
		ApplicationPlugin.getDefault().getPreferenceStore().setValue(PALETTE_STATE, state);

	}

	@Override
	public void setPaletteWidth(final int width) {
		ApplicationPlugin.getDefault().getPreferenceStore().setValue(PALETTE_SIZE,width);
	}

}
