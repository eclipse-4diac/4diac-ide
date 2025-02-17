/*******************************************************************************
 * Copyright (c) 2008, 2009 Profactor GbmH, fortiss GmbH,
 * 				 2018 - 2019 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl - added separate colors for different data types
 *******************************************************************************/
package org.eclipse.fordiac.ide.ui.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * This class implements some static methods for returning different preference
 * settings.
 *
 * @author Gerhard Ebenhofer (gerhard.ebenhofer@profactor.at)
 * @version $Id: PreferenceGetter.java 21624 2007-06-22 11:18:13Z gebenh $
 */

public final class PreferenceGetter {

	/** The used colors. */
	private static final Map<RGB, Color> usedColors = new HashMap<>();

	/**
	 * Returns the color for the specified preference.
	 *
	 * @param pref The preference.
	 *
	 * @return the color
	 */
	public static Color getColor(final String pref) {
		return getColor(UIPreferenceConstants.STORE, pref);
	}

	/**
	 * Returns the color for the specified preference.
	 *
	 * @param pref  The preference.
	 * @param store the store
	 *
	 * @return the color
	 */
	public static Color getColor(final IPreferenceStore store, final String pref) {
		final RGB rgb = PreferenceConverter.getColor(store, pref);
		return usedColors.computeIfAbsent(rgb, rgbCol -> new Color(null, rgbCol));
	}

	public static Color getDataColor(final String dataType) {
		if ("BOOL".equals(dataType)) { //$NON-NLS-1$
			return getColor(UIPreferenceConstants.P_BOOL_CONNECTOR_COLOR);
		}
		if (isAnyBit(dataType)) {
			return getColor(UIPreferenceConstants.P_ANY_BIT_CONNECTOR_COLOR);
		}
		if (isAnyInt(dataType)) {
			return getColor(UIPreferenceConstants.P_ANY_INT_CONNECTOR_COLOR);
		}
		if (isAnyReal(dataType)) {
			return getColor(UIPreferenceConstants.P_ANY_REAL_CONNECTOR_COLOR);
		}
		if (isAnyString(dataType)) {
			return getColor(UIPreferenceConstants.P_ANY_STRING_CONNECTOR_COLOR);
		}
		return getDefaultDataColor();
	}

	public static Color getDefaultDataColor() {
		return getColor(UIPreferenceConstants.P_REMAINING_DATA_CONNECTOR_COLOR);
	}

	private static boolean isAnyBit(final String dataType) {
		return "ANY_BIT".equals(dataType) || "BYTE".equals(dataType) || "WORD".equals(dataType) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| "DWORD".equals(dataType) || "LWORD".equals(dataType); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static boolean isAnyInt(final String dataType) {
		return "ANY_INT".equals(dataType) || "SINT".equals(dataType) || "INT".equals(dataType) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| "DINT".equals(dataType) || "LINT".equals(dataType) || "USINT".equals(dataType) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				|| "UINT".equals(dataType) || "UDINT".equals(dataType) || "ULINT".equals(dataType); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private static boolean isAnyReal(final String dataType) {
		return "ANY_REAL".equals(dataType) || "REAL".equals(dataType) || "LREAL".equals(dataType); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private static boolean isAnyString(final String dataType) {
		return "ANY_STRING".equals(dataType) || "STRING".equals(dataType) || "WSTRING".equals(dataType); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private PreferenceGetter() {
		throw new UnsupportedOperationException("PreferenceGetter utility class should not be instantiated!"); //$NON-NLS-1$
	}

}
