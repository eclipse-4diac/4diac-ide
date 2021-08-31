/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Melanie Winter
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.fbtypeeditor.servicesequence;

import org.eclipse.swt.graphics.RGB;

public final class ServiceConstants {
	private static final int LIGHT_GRAY_RGB = 234;
	public static final RGB LIGHT_GRAY = new RGB(LIGHT_GRAY_RGB, LIGHT_GRAY_RGB, LIGHT_GRAY_RGB);

	private static final int LIGHTER_GRAY_RGB = 250;
	public static final RGB LIGHTER_GRAY = new RGB(LIGHTER_GRAY_RGB, LIGHTER_GRAY_RGB, LIGHTER_GRAY_RGB);

	private static final int GRAY_RGB = 75;
	public static final RGB GRAY = new RGB(GRAY_RGB, GRAY_RGB, GRAY_RGB);

	private static final int TEXT_BLUE_R = 0;
	private static final int TEXT_BLUE_G = 120;
	private static final int TEXT_BLUE_B = 215;
	public static final RGB TEXT_BLUE = new RGB(TEXT_BLUE_R, TEXT_BLUE_G, TEXT_BLUE_B);

	public static final int LINE_WIDTH = 1;

	private static final int WHOLE_WIDTH = 1120;
	private static final int MIDDLE_SECTION_PERCENT = 16;
	private static final int RIGHT_LEFT_SECTION_PERCENT = 42;

	private static final int NAME_WIDTH_PERCENT = 22;
	private static final int PARAMETER_WIDTH_PERCENT = 10;
	private static final int ARROW_WIDTH_PERCENT = 10;
	private static final int EMPTY_SPACE_WIDTH_PERCENT = 32;

	public static int getMiddleSectionWidth() {
		return ((WHOLE_WIDTH * MIDDLE_SECTION_PERCENT) / 100);
	}

	public static int getRightLeftSectionWidth() {
		return ((WHOLE_WIDTH * RIGHT_LEFT_SECTION_PERCENT) / 100);
	}

	public static int getParameterLabelWidth() {
		return ((WHOLE_WIDTH * PARAMETER_WIDTH_PERCENT) / 100);
	}

	public static int getNameLabelWidth() {
		return ((WHOLE_WIDTH * NAME_WIDTH_PERCENT) / 100);
	}

	public static int getArrowWidth() {
		return ((WHOLE_WIDTH * ARROW_WIDTH_PERCENT) / 100);
	}

	public static int getEmptyLabelWidth() {
		return ((WHOLE_WIDTH * EMPTY_SPACE_WIDTH_PERCENT) / 100);
	}

	private ServiceConstants() {
		throw new UnsupportedOperationException();
	}
}
