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

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * Constant definitions for plug-in preferences.
 */
public final class FMUPreferenceConstants {

	public static final String FMU_PREFERENCES_ID = "org.eclipse.fordiac.ide.fmu"; //$NON-NLS-1$

	public static final IPreferenceStore STORE = new ScopedPreferenceStore(InstanceScope.INSTANCE, FMU_PREFERENCES_ID);

	private FMUPreferenceConstants() {
	}

	/** The Constant P_PATH. */
	public static final String P_PATH = "pathPreference"; //$NON-NLS-1$

	/** The Constant P_FMU_WIN32. */
	public static final String P_FMU_WIN32 = "win32"; //$NON-NLS-1$

	/** The Constant P_FMU_WIN64. */
	public static final String P_FMU_WIN64 = "win64"; //$NON-NLS-1$

	/** The Constant P_FMU_POS32. */
	public static final String P_FMU_LIN32 = "linux32"; //$NON-NLS-1$

	/** The Constant P_FMU_POS64. */
	public static final String P_FMU_LIN64 = "linux64"; //$NON-NLS-1$

	public static final String P_FMU_WIN32_LIBRARY = "win32Forte.dll"; //$NON-NLS-1$

	public static final String P_FMU_WIN64_LIBRARY = "win64Forte.dll"; //$NON-NLS-1$

	public static final String P_FMU_LIN32_LIBRARY = "linux32Forte.so"; //$NON-NLS-1$

	public static final String P_FMU_LIN64_LIBRARY = "linux32Forte.so"; //$NON-NLS-1$

}
