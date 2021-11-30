/*******************************************************************************
 * Copyright (c) 2018 ACIN/TU Vienna
 *               2020 Primetals Technologies Germany GmbH
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
 *   Ernst Blecha
 *     - update test to JUnit Jupiter
 *******************************************************************************/
package org.eclipse.fordiac.ide.util.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.util.ColorHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.junit.jupiter.api.Test;

/* Important things to note on JUnit-Tests:
 *
 * + The name of the class containing the tests needs to conform to one of the following:
 *     a) start with Test
 *     b) end with Test
 *     c) end with TestCase
 *     d) end with Tests
 * + The method containing the testcase needs to have the @Test annotation.
 * + The plugin needs to be set to packaging-type "eclipse-test-plugin" in pom.xml.
 *
 * Tests are contained in a separate plugin stored inside the tests directory.
 *
 * Please add any additional information to this comment and put the following notice into your test-class:
 */

//see org.eclipse.fordiac.ide.util.ColorHelperTest.java for information on implementing tests

class ColorHelperTest {

	@SuppressWarnings("static-method") // JUnit needs this as a non-static method
	@Test
	void lighter() {
		final int MEDIUM = 128;
		final int LIGHT = 213;

		final RGB colorRGB = new RGB(MEDIUM, MEDIUM, MEDIUM);
		final Color originalColor = new Color(null, colorRGB);
		final Color lighterColor = ColorHelper.lighter(originalColor);

		assertEquals(lighterColor.getRGB(), new RGB(LIGHT, LIGHT, LIGHT));
	}

}
