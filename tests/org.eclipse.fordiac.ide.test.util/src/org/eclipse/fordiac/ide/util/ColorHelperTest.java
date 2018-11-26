/*******************************************************************************
 * Copyright (c) 2018 ACIN/TU Vienna
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
