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

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.fordiac.ide.gef.Messages;
import org.eclipse.fordiac.ide.util.FordiacLogHelper;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.osgi.service.prefs.BackingStoreException;

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
		return Platform.getPreferencesService().getInt(GefPreferenceConstants.GEF_PREFERENCES_ID, paletteDockLocationID,
				0, null);
	}

	@Override
	public int getPaletteState() {
		return Platform.getPreferencesService().getInt(GefPreferenceConstants.GEF_PREFERENCES_ID, paletteStateID, 0,
				null);
	}

	@Override
	public int getPaletteWidth() {
		return Platform.getPreferencesService().getInt(GefPreferenceConstants.GEF_PREFERENCES_ID, paletteSizeID, 0,
				null);
	}

	@Override
	public void setDockLocation(final int location) {
		setValue(paletteDockLocationID, location);
	}

	@Override
	public void setPaletteState(final int state) {
		setValue(paletteStateID, state);

	}

	@Override
	public void setPaletteWidth(final int width) {
		setValue(paletteSizeID, width);
	}

	private static void setValue(final String valueID, final int value) {
		final IEclipsePreferences prefs = InstanceScope.INSTANCE.getNode(GefPreferenceConstants.GEF_PREFERENCES_ID);
		prefs.putInt(valueID, value);
		try {
			prefs.flush();
		} catch (final BackingStoreException e) {
			FordiacLogHelper.logError(Messages.HandlerPreferenceSafeError, e);
		}
	}

	private void checkPreferenceStoreStatus() {
		if (Platform.getPreferencesService().getString(GefPreferenceConstants.GEF_PREFERENCES_ID, paletteStateID, null,
				null) == null) {
			// there is no setting in the preference store. Set palette opend with a good
			// initial size
			setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
			setPaletteWidth(INITIAL_PALETTE_SIZE);
		}
	}

}
