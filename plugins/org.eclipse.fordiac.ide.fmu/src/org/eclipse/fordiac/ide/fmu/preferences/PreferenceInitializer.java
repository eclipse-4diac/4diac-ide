/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		final IPreferenceStore store = FMUPreferenceConstants.STORE;
		store.setDefault(FMUPreferenceConstants.P_PATH, ""); //$NON-NLS-1$
		store.setDefault(FMUPreferenceConstants.P_FMU_WIN32, false);
		store.setDefault(FMUPreferenceConstants.P_FMU_WIN64, false);
		store.setDefault(FMUPreferenceConstants.P_FMU_LIN32, false);
		store.setDefault(FMUPreferenceConstants.P_FMU_LIN64, false);
	}

}
