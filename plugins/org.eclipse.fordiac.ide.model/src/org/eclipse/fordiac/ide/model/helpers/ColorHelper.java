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
package org.eclipse.fordiac.ide.model.helpers;

import java.security.SecureRandom;
import java.util.Random;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.swt.graphics.RGB;

public final class ColorHelper {

	private ColorHelper() {

	}

	// ================
	// Color generation
	// ================

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

		s += (float) ((rand.nextDouble() * 0.5) - 0.25);
		v += (float) ((rand.nextDouble() * 0.2) - 0.1);

		return new RGB((float) (h * 360.0), s, v);
	}

	public static org.eclipse.fordiac.ide.model.libraryElement.Color getStartingColor() {
		final org.eclipse.fordiac.ide.model.libraryElement.Color color = LibraryElementFactory.eINSTANCE.createColor();
		color.setRed(255);
		color.setGreen(190);
		color.setBlue(111);
		return color;
	}

	// ===================================================
	// Contrast calculation (WCAG 2.x)
	// https://www.w3.org/TR/WCAG20/#visual-audio-contrast
	// ===================================================

	/** Largest value of an 8-bit color channel. */
	private static final double MAX_CHANNEL_VALUE = 255.0;

	/*
	 * Weights of the sRGB primaries in relative luminance (ITU-R BT.709). The eye
	 * is most sensitive to green and least sensitive to blue. The weights sum to 1.
	 */
	private static final double LUMINANCE_WEIGHT_RED = 0.2126;
	private static final double LUMINANCE_WEIGHT_GREEN = 0.7152;
	private static final double LUMINANCE_WEIGHT_BLUE = 0.0722;

	/*
	 * Constants of the sRGB transfer function (gamma encoding). Dark values use a
	 * linear segment, brighter ones a power curve. WCAG 2.x prints the threshold as
	 * 0.03928; the sRGB standard value is 0.04045. Both give identical results for
	 * 8-bit input.
	 */
	private static final double SRGB_LINEAR_THRESHOLD = 0.04045;
	private static final double SRGB_LINEAR_SLOPE = 12.92;
	private static final double SRGB_CURVE_OFFSET = 0.055;
	private static final double SRGB_CURVE_SCALE = 1.055;
	private static final double SRGB_CURVE_EXPONENT = 2.4;

	/**
	 * Models light reflected from the screen surface (flare). It keeps the contrast
	 * ratio finite for black and prevents tiny differences between very dark colors
	 * from being exaggerated.
	 */
	private static final double AMBIENT_LIGHT_FLARE = 0.05;

	/** WCAG AA minimum contrast for normal-size text (level AA, 1.4.3). */
	private static final double WCAG_AA_NORMAL_TEXT_CONTRAST = 4.5;

	private static final RGB BLACK = new RGB(0, 0, 0);
	private static final RGB WHITE = new RGB((int) MAX_CHANNEL_VALUE, (int) MAX_CHANNEL_VALUE, (int) MAX_CHANNEL_VALUE);

	/**
	 * Relative luminance as defined by WCAG 2.x: the perceived brightness of a
	 * color, from 0.0 (black) to 1.0 (white). The channels are first converted from
	 * gamma-encoded sRGB to linear light and then weighted by the eye's sensitivity
	 * to each primary.
	 */
	private static double relativeLuminance(final RGB rgb) {
		return LUMINANCE_WEIGHT_RED * toLinear(rgb.red) + LUMINANCE_WEIGHT_GREEN * toLinear(rgb.green)
				+ LUMINANCE_WEIGHT_BLUE * toLinear(rgb.blue);
	}

	/** Converts one gamma-encoded 8-bit sRGB channel to linear light (0..1). */
	private static double toLinear(final int channel) {
		final double encoded = channel / MAX_CHANNEL_VALUE;
		if (encoded <= SRGB_LINEAR_THRESHOLD) {
			return encoded / SRGB_LINEAR_SLOPE;
		}
		return Math.pow((encoded + SRGB_CURVE_OFFSET) / SRGB_CURVE_SCALE, SRGB_CURVE_EXPONENT);
	}

	/**
	 * WCAG contrast ratio between two colors, from 1.0 (identical) to 21.0 (black
	 * on white). The order of the arguments does not matter.
	 */
	private static double contrastRatio(final RGB a, final RGB b) {
		final double la = relativeLuminance(a);
		final double lb = relativeLuminance(b);
		return (Math.max(la, lb) + AMBIENT_LIGHT_FLARE) / (Math.min(la, lb) + AMBIENT_LIGHT_FLARE);
	}

	/**
	 * Whether white text is more readable than black text on the given background.
	 * Equivalent to a luminance threshold of about 0.179, but computed from the
	 * actual contrast ratios.
	 */
	public static boolean prefersWhiteText(final RGB background) {
		return contrastRatio(background, WHITE) > contrastRatio(background, BLACK);
	}

	/**
	 * Best contrast ratio achievable on this background with black or white text.
	 */
	public static double bestTextContrast(final RGB background) {
		return Math.max(contrastRatio(background, WHITE), contrastRatio(background, BLACK));
	}

	/**
	 * Whether black or white text reaches WCAG AA for normal text on this
	 * background.
	 */
	public static boolean isReadable(final RGB background) {
		return bestTextContrast(background) >= WCAG_AA_NORMAL_TEXT_CONTRAST;
	}
}
