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
 *   Martijn Rooker, Gerhard Ebenhofer, Thomas Strasser
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbrtlauncher.preferences;

/**
 * Constant definitions for plug-in preferences.
 */
public final class PreferenceConstants {

	/** The Constant P_PATH. */
	public static final String P_PATH = "pathPreference"; //$NON-NLS-1$
	/** The Constant P_LIB. */
	public static final String P_LIB = "libPreference"; //$NON-NLS-1$
	
	private PreferenceConstants() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}
	
}
