/*******************************************************************************
 * Copyright (c) 2011, 2016 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 				 2018 Johannes Kepler University
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Ingo Hegny, Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypeeditor.ecc.preferences;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.fordiac.ide.fbtypeeditor.ecc.Activator;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;


/**
 * This class implements some static methods for returning different preference
 * settings.
 * 
 */

public class PreferenceGetter {

	/** The used colors. */
	private static Map<RGB, Color> usedColors = new HashMap<>();

	/**
	 * Returns the color for the specified preference.
	 * 
	 * @param pref The preference.
	 * 
	 * @return the color
	 */
	public static Color getColor(final String pref) {
		RGB rgb = PreferenceConverter.getColor(Activator.getDefault()
		.getPreferenceStore(), pref);

		if (!usedColors.containsKey(rgb)) {
			usedColors.put(rgb, new Color(null, rgb));
		}

		return usedColors.get(rgb);
	}
	
	

}
