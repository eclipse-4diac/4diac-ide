/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Ernst Blecha
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

/**
 * Constant definitions for plug-in preferences.
 *
 */
public final class PreferenceConstants {

	/** Allocation block size used during xml file export. */
	public static final String P_ALLOCATION_SIZE = "allocationSize"; //$NON-NLS-1$
	public static final int P_ALLOCATION_SIZE_DEFAULT_VALUE = 100;

	public static final String MARGIN_TOP_BOTTOM = "MarginTopBottom"; //$NON-NLS-1$
	public static final String MARGIN_LEFT_RIGHT = "MarginLeftRight"; //$NON-NLS-1$
	public static final int MARGIN_TOP_BOTTOM_DEFAULT_VALUE = 0;
	public static final int MARGIN_LEFT_RIGHT_DEFAULT_VALUE = 0;

	// Additional constants defined in
	// org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants

	private PreferenceConstants() {
		throw new UnsupportedOperationException("PreferenceConstants utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
