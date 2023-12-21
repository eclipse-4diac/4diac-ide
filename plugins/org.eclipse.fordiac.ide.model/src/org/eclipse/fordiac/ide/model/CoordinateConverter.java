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
import org.eclipse.fordiac.ide.ui.preferences.PreferenceConstants;
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
			final Font diagramFont = JFaceResources.getFont(PreferenceConstants.DIAGRAM_FONT);
			final FontMetrics fontMetrics = FigureUtilities.getFontMetrics(diagramFont);
			lineHeight = fontMetrics.getHeight();
			averageCharacterWidth = fontMetrics.getAverageCharacterWidth();
		}
	}

	CoordinateConverter() {
		final LineHeightRunnable lineHeightCalc = new LineHeightRunnable();

		Display.getDefault().syncExec(lineHeightCalc);
		lineHeight = lineHeightCalc.getLineHeight();
		averageCharacterWidth = lineHeightCalc.getAverageCharacterWidth();
		transformationScale = lineHeight / 100.0;
	}

	/**
	 * Take the string representing the value from the 61499-2 XML file to an 4diac
	 * IDE internal coordinate
	 *
	 * @param value string representation of a coordinate value
	 * @return 4diac IDE internal coordinate value
	 */
	public int convertFrom1499XML(final String value) {
		double parsedValue = 0;
		if ((null != value) && (0 != value.length())) {
			parsedValue = Double.parseDouble(value);
		}
		return (int) (parsedValue * transformationScale);
	}

	/**
	 * Take an 4diac IDE internal coordinate and transform it to a string
	 * representation suitable for an IEC 61499-2 XML
	 *
	 * @param value 4diac IDE internal coordinate
	 * @return string representation of the value in IEC 61499-2 format
	 */
	public String convertTo1499XML(final int value) {
		return Double.toString(value / transformationScale);
	}

	public double getLineHeight() {
		return lineHeight;
	}

	public double getAverageCharacterWidth() {
		return averageCharacterWidth;
	}
}
