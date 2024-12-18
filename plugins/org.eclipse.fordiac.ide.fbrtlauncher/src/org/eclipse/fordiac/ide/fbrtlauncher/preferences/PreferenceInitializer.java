/*******************************************************************************
 * Copyright (c) 2008 - 2010, 2016 Profactor GmbH, fortiss GmbH
 * 				 2020			   Andrea Zoitl
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Thomas Strasser, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *   Andrea Zoitl
 *     - externalized all translatable strings
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbrtlauncher.preferences;

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
		final IPreferenceStore store = FbrtPreferenceConstants.STORE;
		store.setDefault(FbrtPreferenceConstants.P_PATH, ""); //$NON-NLS-1$
		store.setDefault(FbrtPreferenceConstants.P_LIB,
				"convert;crypt;events;hmi;ita;mach;math;mva;net;plc;process;student;template;test;"); //$NON-NLS-1$
	}

}
