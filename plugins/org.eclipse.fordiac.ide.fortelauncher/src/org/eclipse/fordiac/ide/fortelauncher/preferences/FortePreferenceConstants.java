/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martijn Rooker, Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fortelauncher.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Constant definitions for plug-in preferences.
 */
public final class FortePreferenceConstants {

	public static final String FORTELAUNCHER_PREFERENCES_ID = "org.eclipse.fordiac.ide.fortelauncher"; //$NON-NLS-1$

	public static final IPreferenceStore STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE,
			FORTELAUNCHER_PREFERENCES_ID);

	/** The Constant P_PATH. */
	public static final String P_PATH = "pathPreference"; //$NON-NLS-1$

	private FortePreferenceConstants() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
