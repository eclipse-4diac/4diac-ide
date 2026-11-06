/*******************************************************************************
 * Copyright (c) 2026 Malte Grave
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Malte Grave - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.fordiac.ide.structuredtextcore.formatting2.FormatterUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("nls")
class FormatterUtilTest {

	@ParameterizedTest
	@ValueSource(strings = { "\n", "\r\n" })
	void wrapLinesKeepsLineBreaks(final String lineSeparator) {
		final String text = "first" + lineSeparator + "second" + lineSeparator + lineSeparator + "third";
		assertEquals(text + lineSeparator, FormatterUtil.wrapLines(text, 40, lineSeparator));
	}

	@ParameterizedTest
	@ValueSource(strings = { "\n", "\r\n" })
	void wrapLinesWrapsAtWordBoundary(final String lineSeparator) {
		assertEquals("first" + lineSeparator + "second" + lineSeparator,
				FormatterUtil.wrapLines("first second", 7, lineSeparator));
	}
}
