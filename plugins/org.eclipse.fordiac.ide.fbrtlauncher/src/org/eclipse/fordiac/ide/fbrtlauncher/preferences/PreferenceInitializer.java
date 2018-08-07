/*******************************************************************************
 * Copyright (c) 2008 - 2010, 2016 Profactor GmbH, fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Martijn Rooker, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbrtlauncher.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.fordiac.ide.fbrtlauncher.Activator;
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
		boolean isMacOS = Platform.getOS().equalsIgnoreCase(Platform.OS_MACOSX);
		if (isWin32) {
			store.setDefault(PreferenceConstants.P_PATH, "C:\\Training\\FBRT\\fbrt.jar"); //$NON-NLS-1$
			store.setDefault(PreferenceConstants.P_LIB,
					"convert;crypt;events;hmi;ita;mach;math;mva;net;plc;process;student;template;test;"); //$NON-NLS-1$
		} else if (isLinux || isMacOS) {
			store.setDefault(PreferenceConstants.P_PATH, "/Training/FBRT/fbrt.jar"); //$NON-NLS-1$
			store.setDefault(PreferenceConstants.P_LIB,
					"convert;crypt;events;hmi;ita;mach;math;mva;net;plc;process;student;template;test;"); //$NON-NLS-1$
		} else {
			store.setDefault(PreferenceConstants.P_PATH, "Set path to the FBRT ...");
			store.setDefault(PreferenceConstants.P_LIB, "Set the used FBRT Libraries ...");
		}
	}

}
