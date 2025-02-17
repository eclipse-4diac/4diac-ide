/*******************************************************************************
 * Copyright (c) 2008, 2009, 2011, 2015 - 2017 Profactor GbmH, fortiss GmbH
 * 				 2019 Johannes Kepler University Linz
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
 *   Alois Zoitl - added separate colors for different data types
 *   Virendra Ashiwal, Bianca Wiesmayer
 *   	- Changed Adapter color(P_ADAPTER_CONNECTOR_COLOR) to static Green RGB(80,200,120)
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = UIPreferenceConstants.STORE;
		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_EVENT_CONNECTOR_COLOR, new RGB(99, 179, 31));

		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_BOOL_CONNECTOR_COLOR, new RGB(159, 164, 138));
		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_ANY_BIT_CONNECTOR_COLOR, new RGB(130, 163, 169));
		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_ANY_INT_CONNECTOR_COLOR, new RGB(24, 81, 158));
		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_ANY_REAL_CONNECTOR_COLOR, new RGB(219, 180, 24));
		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_ANY_STRING_CONNECTOR_COLOR,
				new RGB(189, 134, 99));
		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_REMAINING_DATA_CONNECTOR_COLOR,
				new RGB(0, 0, 255));

		PreferenceConverter.setDefault(store, UIPreferenceConstants.P_ADAPTER_CONNECTOR_COLOR, new RGB(132, 93, 175));

		store.setDefault(UIPreferenceConstants.P_HIDE_EVENT_CON, false);
		store.setDefault(UIPreferenceConstants.P_HIDE_DATA_CON, false);

		store.setDefault(UIPreferenceConstants.P_DEFAULT_COMPLIANCE_PROFILE, "HOLOBLOC"); //$NON-NLS-1$

		store.setDefault(UIPreferenceConstants.P_SHOW_ERRORS_AT_MOUSE_CURSOR,
				UIPreferenceConstants.P_SHOW_ERRORS_AT_MOUSE_CURSOR_DEFAULT_VALUE);

		store.setDefault(UIPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX,
				UIPreferenceConstants.P_DEACTIVATE_COMMENT_TRANSFERRING_DEMUX_TO_MUX_DEFAULT_VALUE);

	}
}
