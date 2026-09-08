/*******************************************************************************
 * Copyright (c) 2008, 2009, 2014, 2015, 2017 Profactor GbmH, fortiss GmbH
 * 				 2019, 2023 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl, Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added preference driven max width for value edit parts
 *   Prankur Agarwal - added preference max size for hidden connection label
 *******************************************************************************/
package org.eclipse.fordiac.ide.gef.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/** Class used to initialize default preference values. */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences preferences = DefaultScope.INSTANCE
				.getNode(GefPreferenceConstants.GEF_PREFERENCES_ID);
		preferences.putBoolean(GefPreferenceConstants.SNAP_TO_GRID, true);
		preferences.putBoolean(GefPreferenceConstants.SHOW_GRID, true);

		preferences.put(GefPreferenceConstants.PIN_LABEL_STYLE, GefPreferenceConstants.PIN_LABEL_STYLE_PIN_NAME);

		preferences.putInt(GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH, 1000);

		preferences.putBoolean(GefPreferenceConstants.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR, true);
		preferences.putBoolean(GefPreferenceConstants.EXPANDED_INTERFACE_EVENTS_TOP, true);

		preferences.putBoolean(GefPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX,
				GefPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX_DEFAULT_VALUE);

		preferences.putBoolean(GefPreferenceConstants.EXPLOSION_EFFECT_ON_DELETE, false);
	}
}
