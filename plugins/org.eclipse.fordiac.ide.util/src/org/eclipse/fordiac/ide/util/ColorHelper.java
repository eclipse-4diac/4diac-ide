/*******************************************************************************
 * Copyright (c) 2010 Profactor GbmH, fortiss GmbH, TU Vienna/ACIN,
 *                    Johannes Kepler University Linz
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
 *   Martin Melik Merkumians
 *     - makes ctor private and class final
 *   Bianca Wiesmayr
 *     - define a first color for devices
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import java.security.SecureRandom;
import java.util.Random;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.swt.graphics.RGB;

public final class ColorHelper {

	private ColorHelper() {

	}

	private static final double GOLDEN_RATIO_CONJUGATE = 0.618033988749895;
	private static Random rand = new SecureRandom();
	private static double h = rand.nextDouble(); // static to get different colors

	public static org.eclipse.fordiac.ide.model.libraryElement.Color createRandomColor() {
		final RGB rgbColor = createRandomColor(0.6f, 0.85f);

		final org.eclipse.fordiac.ide.model.libraryElement.Color color = LibraryElementFactory.eINSTANCE.createColor();
		color.setRed(rgbColor.red);
		color.setGreen(rgbColor.green);
		color.setBlue(rgbColor.blue);
		return color;
	}

	public static RGB createRandomColor(float s, float v) {
		h += GOLDEN_RATIO_CONJUGATE;
		h %= 1;

		s = s + (float) ((rand.nextDouble() * 0.5) - 0.25);
		v = v + (float) ((rand.nextDouble() * 0.2) - 0.1);

		return new RGB((float) (h * 360.0), s, v);
	}

	public static org.eclipse.fordiac.ide.model.libraryElement.Color getStartingColor() {
		final org.eclipse.fordiac.ide.model.libraryElement.Color color = LibraryElementFactory.eINSTANCE.createColor();
		color.setRed(255);
		color.setGreen(190);
		color.setBlue(111);
		return color;
	}
}
