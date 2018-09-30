/*******************************************************************************
 * Copyright (c) 2017 - 2018 fortiss GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jose Cabral
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fmu.preferences;

/**
 * Constant definitions for plug-in preferences.
 */
public final class PreferenceConstants {
	
	private PreferenceConstants() {
	}

	/** The Constant P_PATH. */
	public static final String P_PATH = "pathPreference";  //$NON-NLS-1$
	
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
