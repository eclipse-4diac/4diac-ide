/*******************************************************************************
 * Copyright (c) 2008, 2009, 2016, 2017 Profactor GbmH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gerhard Ebenhofer, Alois Zoitl
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * The Class ColorManager.
 */
public final class ColorManager {

	/** The color table. */
	private static final Map<RGB, Color> fColorTable = new HashMap<>(10);

	/**
	 * Dispose.
	 */
	public static void dispose() {
		final Iterator<Color> e = fColorTable.values().iterator();
		while (e.hasNext()) {
			(e.next()).dispose();
		}
	}

	/**
	 * Gets the color.
	 *
	 * @param rgb the rgb
	 *
	 * @return the color
	 */
	public static Color getColor(final RGB rgb) {
		return fColorTable.computeIfAbsent(rgb, Color::new);
	}

	public static Color getColor(final org.eclipse.fordiac.ide.model.libraryElement.Color color) {
		return getColor(new RGB(color.getRed(), color.getGreen(), color.getBlue()));
	}

	private ColorManager() {
		throw new UnsupportedOperationException("LaunchRuntimeUtils utility class should not be instantiated!"); //$NON-NLS-1$
	}
}
