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
import org.eclipse.fordiac.ide.gef.Activator;
import org.eclipse.jface.preference.IPreferenceStore;

/** Class used to initialize default preference values. */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(DiagramPreferences.SNAP_TO_GRID, true);
		store.setDefault(DiagramPreferences.SHOW_GRID, true);

		store.setDefault(DiagramPreferences.PIN_LABEL_STYLE, DiagramPreferences.PIN_LABEL_STYLE_PIN_NAME);

		store.setDefault(DiagramPreferences.MAX_VALUE_LABEL_SIZE, 25); // big enough to fully show an ip address and
		store.setDefault(DiagramPreferences.MAX_DEFAULT_VALUE_LENGTH, 1000);
		// port
		store.setDefault(DiagramPreferences.MAX_PIN_LABEL_SIZE, 12);
		store.setDefault(DiagramPreferences.MAX_INTERFACE_BAR_SIZE, 40);
		store.setDefault(DiagramPreferences.MIN_INTERFACE_BAR_SIZE, 40);
		store.setDefault(DiagramPreferences.MAX_HIDDEN_CONNECTION_LABEL_SIZE, 60);
		store.setDefault(DiagramPreferences.MAX_TYPE_LABEL_SIZE, 15);

		store.setDefault(DiagramPreferences.EXPANDED_INTERFACE_OLD_DIRECT_BEHAVIOUR, true);
		store.setDefault(DiagramPreferences.EXPANDED_INTERFACE_EVENTS_TOP, true);
	}
}
