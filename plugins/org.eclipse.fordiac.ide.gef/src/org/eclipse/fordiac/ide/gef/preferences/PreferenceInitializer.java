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

	public static final int DEFAULT_MAX_INTERFACE_BAR_SIZE = 40;

	@Override
	public void initializeDefaultPreferences() {
		final IEclipsePreferences preferences = DefaultScope.INSTANCE
				.getNode(GefPreferenceConstants.GEF_PREFERENCES_ID);
		preferences.putBoolean(GefPreferenceConstants.SNAP_TO_GRID, true);
		preferences.putBoolean(GefPreferenceConstants.SHOW_GRID, true);

		preferences.put(GefPreferenceConstants.PIN_LABEL_STYLE, GefPreferenceConstants.PIN_LABEL_STYLE_PIN_NAME);

		preferences.putInt(GefPreferenceConstants.MAX_VALUE_LABEL_SIZE, 25); // big enough to fully show an IP address
																				// and port
		preferences.putInt(GefPreferenceConstants.MAX_DEFAULT_VALUE_LENGTH, 1000);

		preferences.putInt(GefPreferenceConstants.MAX_PIN_LABEL_SIZE, 12);
		preferences.putInt(GefPreferenceConstants.MAX_INTERFACE_BAR_SIZE, DEFAULT_MAX_INTERFACE_BAR_SIZE);
		preferences.putInt(GefPreferenceConstants.MIN_INTERFACE_BAR_SIZE, 40);
		preferences.putInt(GefPreferenceConstants.MAX_HIDDEN_CONNECTION_LABEL_SIZE, 60);
		preferences.putInt(GefPreferenceConstants.MAX_TYPE_LABEL_SIZE, 15);

		preferences.putBoolean(GefPreferenceConstants.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR, true);
		preferences.putBoolean(GefPreferenceConstants.EXPANDED_INTERFACE_EVENTS_TOP, true);

		preferences.putBoolean(GefPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX,
				GefPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX_DEFAULT_VALUE);
	}
}
