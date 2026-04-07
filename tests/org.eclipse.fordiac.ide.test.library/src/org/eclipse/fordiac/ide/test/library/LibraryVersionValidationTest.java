/*******************************************************************************
 * Copyright (c) 2026 Primetals Technologies Austria GmbH and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mario Kastner - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.test.library;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.library.model.util.VersionComparator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class LibraryVersionValidationTest {

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("validVersionRanges")
	void testValidVersionRanges(final String input) {
		assertTrue(VersionComparator.isValidRange(input));
	}

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("invalidVersionRanges")
	void testInvalidVersionRanges(final String input) {
		assertFalse(VersionComparator.isValidRange(input));
	}

	@SuppressWarnings("nls")
	static Stream<String> validVersionRanges() {
		// @formatter:off
		return Stream.of("1.0.0",
				"[1.0.0-2.0.0]",
				"(1.0.0-2.0.0]",
				"(1.0.0- 2.0.0] ",
				"[1.0.0-2.0.0)",
				"(1.0.0-2.0.0)",
				"[1-1]",
				"[0.0.0-999.999.999]",
				" [1.0.0-2.0.0] ");
		// @formatter:on
	}

	@SuppressWarnings("nls")
	static Stream<String> invalidVersionRanges() {
		// @formatter:off
		return Stream.of(null,
				"",
				" ",
				"[1.0.0-]",
				"[-2.0.0]",
				"[1.0.0-2.0.0",
				"1.0.0-2.0.0",
				"[1.0.0,2.0.0]",
				"[2.0.0-1.0.0]",
				"[1.0.0--2.0.0]",
				"[1.0.0-2.0.0]]",
				"[[1.0.0-2.0.0]",
				"1.2.3.qualifier", //osgi version range allows qualifiers, we do not
				"[abc-2.0.0]",
				"[1.0.0-abc]",
				"abc",
				"()",
				"[-]",
				"[1.0.0-2.0.0-3.0.0]",
				"[1.2.3.qualifier-2.3.4.beta]");
		// @formatter:on
	}
}
