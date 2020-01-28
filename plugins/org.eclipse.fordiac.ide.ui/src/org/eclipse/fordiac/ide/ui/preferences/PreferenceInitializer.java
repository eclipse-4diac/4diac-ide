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
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.fordiac.ide.ui.UIPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = UIPlugin.getDefault().getPreferenceStore();
		PreferenceConverter.setDefault(store, PreferenceConstants.P_EVENT_CONNECTOR_COLOR, new RGB(255, 0, 0));

		PreferenceConverter.setDefault(store, PreferenceConstants.P_BOOL_CONNECTOR_COLOR, new RGB(159, 164, 138));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_ANY_BIT_CONNECTOR_COLOR, new RGB(130, 163, 169));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_ANY_INT_CONNECTOR_COLOR, new RGB(24, 81, 158));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_ANY_REAL_CONNECTOR_COLOR, new RGB(219, 180, 24));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_ANY_STRING_CONNECTOR_COLOR, new RGB(189, 134, 99));
		PreferenceConverter.setDefault(store, PreferenceConstants.P_REMAINING_DATA_CONNECTOR_COLOR, new RGB(0, 0, 255));

		PreferenceConverter.setDefault(store, PreferenceConstants.P_ADAPTER_CONNECTOR_COLOR, new RGB(0, 174, 0));

		store.setDefault(PreferenceConstants.P_HIDE_EVENT_CON, false);
		store.setDefault(PreferenceConstants.P_HIDE_DATA_CON, false);

		store.setDefault(PreferenceConstants.P_DEFAULT_COMPLIANCE_PROFILE, "HOLOBLOC"); //$NON-NLS-1$

	}
}
