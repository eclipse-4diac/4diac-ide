/*******************************************************************************
 * Copyright (c) 2018 ACIN/TU Vienna
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - first test in general and ColorHelper in particular
 *******************************************************************************/
package org.eclipse.fordiac.ide.util;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.junit.Test;

public class ColorHelperTest {

	@Test
	public void Lighter() {
		RGB colorRGB = new RGB(128, 128, 128);
		Color originalColor = new Color(null, colorRGB);
		Color lighterColor = ColorHelper.lighter(originalColor);

		assertEquals(lighterColor.getRGB(), new RGB(213, 213, 213));
	}

}
