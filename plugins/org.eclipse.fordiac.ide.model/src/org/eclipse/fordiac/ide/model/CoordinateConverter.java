/********************************************************************************
 * Copyright (c) 2019, 2023 Johannes Kepler University, Linz
 *                          Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Alois Zoitl
 *    - initial API and implementation and/or initial documentation
 *  Martin Erich Jobst
 *    - add average character width
 ********************************************************************************/
package org.eclipse.fordiac.ide.model;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElementFactory;
import org.eclipse.fordiac.ide.model.libraryElement.Position;
import org.eclipse.fordiac.ide.ui.preferences.UIPreferenceConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.widgets.Display;

/**
 * Helper class for transforming between the coordination resolution between IEC
 * 61499-2 Annex B.1 definitions to the resolution used in 4diac IDE editors.
 *
 */
public enum CoordinateConverter {
	INSTANCE;

	/**
	 * The scaler transformation value for coordinate conversion.
	 *
	 * In general it the lineHeigth / 100 as defined in IEC 61499-2 Annex B.1
	 */
	private final double transformationScale;
	private final double lineHeight;
	private final double averageCharacterWidth;

	/*
	 * Default line height and chacater width if no Display and thus no font
	 * information is available. These values are based on the default mono-space
	 * font.
	 */
	private static final double DEFAULT_LINE_HEIGHT = 19.0;
	private static final double DEFAULT_CHARACTER_WIDTH = 8.0;

	private static class LineHeightRunnable implements Runnable {
		private double lineHeight;
		private double averageCharacterWidth;

		public double getLineHeight() {
			return lineHeight;
		}

		public double getAverageCharacterWidth() {
			return averageCharacterWidth;
		}

		@Override
		public void run() {
			final Font diagramFont = JFaceResources.getFont(UIPreferenceConstants.DIAGRAM_FONT);
			final FontMetrics fontMetrics = FigureUtilities.getFontMetrics(diagramFont);
			lineHeight = fontMetrics.getHeight();
			averageCharacterWidth = fontMetrics.getAverageCharacterWidth();
		}
	}

	CoordinateConverter() {
		final LineHeightRunnable lineHeightCalc = new LineHeightRunnable();

		final Display display = Display.getDefault();
		if (display != null) {
			display.syncExec(lineHeightCalc);
			lineHeight = lineHeightCalc.getLineHeight();
			averageCharacterWidth = lineHeightCalc.getAverageCharacterWidth();
		} else {
			lineHeight = DEFAULT_LINE_HEIGHT;
			averageCharacterWidth = DEFAULT_CHARACTER_WIDTH;
		}
		transformationScale = lineHeight / 100.0;
	}

	/**
	 * Convert IEC 61499 coordinate to screen coordinate
	 *
	 * @param value IEC 61499 coordinate value
	 * @return according screen coordinate value
	 */
	public int iec61499ToScreen(final double value) {
		return (int) (value * transformationScale);
	}

	/**
	 * Convert screen coordinate value to IEC 61499 coordinate system
	 *
	 * @param value screen coordinate value
	 * @return according IEC 61499 coordinate value
	 */
	public double screenToIEC61499(final int value) {
		return value / transformationScale;
	}

	public double getLineHeight() {
		return lineHeight;
	}

	public double getAverageCharacterWidth() {
		return averageCharacterWidth;
	}

	public Position createPosFromScreenCoordinates(final int x, final int y) {
		final Position pos = LibraryElementFactory.eINSTANCE.createPosition();
		pos.setX(screenToIEC61499(x));
		pos.setY(screenToIEC61499(y));
		return pos;
	}
}
