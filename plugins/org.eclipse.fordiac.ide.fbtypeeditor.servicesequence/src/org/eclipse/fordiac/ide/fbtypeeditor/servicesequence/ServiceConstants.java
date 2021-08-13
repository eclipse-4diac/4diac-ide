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
	private static final int GRAY_RGB = 234;
	public static final RGB LIGHT_GRAY = new RGB(GRAY_RGB, GRAY_RGB, GRAY_RGB);

	private static final int TEXT_BLUE_R = 0;
	private static final int TEXT_BLUE_G = 120;
	private static final int TEXT_BLUE_B = 215;
	public static final RGB TEXT_BLUE = new RGB(TEXT_BLUE_R, TEXT_BLUE_G, TEXT_BLUE_B);

	private ServiceConstants() {
		throw new UnsupportedOperationException();
	}
}
