/*******************************************************************************
 * Copyright (c) 2024 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("static-method")
class IdentifierVerifierTest {

	@Test
	void testVerifyIdentifierNullArgument() {
		assertTrue(IdentifierVerifier.verifyIdentifier(null).isPresent());
	}

	@ParameterizedTest
	@ValueSource(strings = { "X", "TEST", "Test", "test", "test42", "_test", "test_name" })
	void testVerifyValidIdentifier(final String identifier) {
		assertTrue(IdentifierVerifier.verifyIdentifier(identifier).isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "TEST$", "Test\u00c4", "4test", "__test", "test__name", "test_", "test name",
			"test\\nname", "ANY" })
	void testVerifyInvalidIdentifier(final String identifier) {
		assertTrue(IdentifierVerifier.verifyIdentifier(identifier.translateEscapes()).isPresent());
	}

	@Test
	void testVerifyPackageNameNullArgument() {
		assertTrue(IdentifierVerifier.verifyPackageName(null).isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "X", "TEST", "Test", "test", "test42", "_test", "test_name", //
			"pkg::TEST", "pkg::Test", "pkg::test", "pkg::test42", "pkg::_test", "pkg::test_name", //
			"TEST::pkg", "Test::pkg", "test::pkg", "test42::pkg", "_test::pkg", "test_name::pkg", //
			"pkg::test::test1" })
	void testVerifyValidPackageName(final String identifier) {
		assertTrue(IdentifierVerifier.verifyPackageName(identifier).isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = { "TEST$", "Test\u00c4", "4test", "__test", "test__name", "test_", "test name",
			"test\\nname", "ANY", //
			"pkg::TEST$", "pkg::Test\u00c4", "pkg::4test", "pkg::__test", "pkg::test__name", "pkg::test_",
			"pkg::test name", "pkg::test\\nname", "pkg::ANY", //
			"TEST$::pkg", "Test\u00c4::pkg", "4test::pkg", "__test::pkg", "test__name::pkg", "test_::pkg",
			"test name::pkg", "test\\nname::pkg", "ANY::pkg", //
			"pkg::test::TEST$", "pkg:test", "pkg:::test", "pkg::::test", "pkg::" })
	void testVerifyInvalidPackageName(final String identifier) {
		assertTrue(IdentifierVerifier.verifyPackageName(identifier.translateEscapes()).isPresent());
	}
}
