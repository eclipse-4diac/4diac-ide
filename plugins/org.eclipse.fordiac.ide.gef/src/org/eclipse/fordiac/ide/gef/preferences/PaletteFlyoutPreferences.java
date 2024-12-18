/*******************************************************************************
 * Copyright (c) 2018 Johannes Kepler Unversity
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;

public class PaletteFlyoutPreferences implements FlyoutPreferences {
	private static final int INITIAL_PALETTE_SIZE = 200;

	/** Preference ID used to persist the palette location. */
	private final String paletteDockLocationID;

	/** Preference ID used to persist the palette size. */
	private final String paletteSizeID;

	/** Preference ID used to persist the flyout palette's state. */
	private final String paletteStateID;

	public PaletteFlyoutPreferences(final String paletteDockLocationID, final String paletteSizeID,
			final String paletteStateID) {
		this.paletteDockLocationID = paletteDockLocationID;
		this.paletteSizeID = paletteSizeID;
		this.paletteStateID = paletteStateID;
		checkPreferenceStoreStatus();
	}

	@Override
	public int getDockLocation() {
		return GefPreferenceConstants.STORE.getInt(paletteDockLocationID);
	}

	@Override
	public int getPaletteState() {
		return GefPreferenceConstants.STORE.getInt(paletteStateID);
	}

	@Override
	public int getPaletteWidth() {
		return GefPreferenceConstants.STORE.getInt(paletteSizeID);
	}

	@Override
	public void setDockLocation(final int location) {
		GefPreferenceConstants.STORE.setValue(paletteDockLocationID, location);
	}

	@Override
	public void setPaletteState(final int state) {
		GefPreferenceConstants.STORE.setValue(paletteStateID, state);

	}

	@Override
	public void setPaletteWidth(final int width) {
		GefPreferenceConstants.STORE.setValue(paletteSizeID, width);
	}

	private void checkPreferenceStoreStatus() {
		if (!GefPreferenceConstants.STORE.contains(paletteStateID)) {
			// there is no setting in the preference store. Set palette opend with a good
			// initial size
			setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			setPaletteWidth(INITIAL_PALETTE_SIZE);
		}
	}

}
