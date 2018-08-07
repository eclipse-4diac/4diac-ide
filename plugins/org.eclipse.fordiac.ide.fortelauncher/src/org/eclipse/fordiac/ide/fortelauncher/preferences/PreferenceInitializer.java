/*******************************************************************************
 * Copyright (c) 2008, 2010 Profactor GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Thomas Strasser
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fortelauncher.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.fordiac.ide.fortelauncher.Activator;
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
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean isWin32 = Platform.getOS().equalsIgnoreCase(Platform.OS_WIN32);
		boolean isLinux = Platform.getOS().equalsIgnoreCase(Platform.OS_LINUX);
		if (isWin32) {
			store.setDefault(PreferenceConstants.P_PATH,
					"C:\\Training\\FORTE\\forte.exe"); //$NON-NLS-1$
		} else if (isLinux) {
			store.setDefault(PreferenceConstants.P_PATH,
					"/Training/FORTE/forte"); //$NON-NLS-1$
		} else {
			store.setDefault(PreferenceConstants.P_PATH,
					"Set path to the FORTE ...");
		}
	}

}
