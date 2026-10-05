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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.library.export.PackageNameMatcher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PackageNameMatchingTest {

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("provideExactMatches")
	void testExactMatch(final String packageName, final String pattern, final Boolean expected) {
		testMatch(packageName, pattern, expected);
	}

	private static Stream<Arguments> provideExactMatches() {
		return Stream.of(Arguments.of("controls", "controls", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "controls::motor", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "controls::valve", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::internal", "controls::motor", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls", "controls::motor", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "controls", Boolean.FALSE)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("provideSingleSegmentWildcardMatches")
	void testSingleSegmentWildcard(final String packageName, final String pattern, final Boolean expected) {
		testMatch(packageName, pattern, expected);
	}

	private static Stream<Arguments> provideSingleSegmentWildcardMatches() {
		return Stream.of(Arguments.of("controls::motor", "controls::*", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::valve", "controls::*", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::internal", "controls::*", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls", "controls::*", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::Drive", "controls::*::Drive", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::internal::Drive", "controls::*::Drive", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "*::motor", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls", "*", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "*", Boolean.FALSE)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("provideMultiSegmentWildcardMatches")
	void testMultiSegmentWildcard(final String packageName, final String pattern, final Boolean expected) {
		testMatch(packageName, pattern, expected);
	}

	private static Stream<Arguments> provideMultiSegmentWildcardMatches() {
		return Stream.of(Arguments.of("controls::motor", "controls::**", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::internal", "controls::**", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls", "controls::**", Boolean.FALSE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::Drive", "controls::**::Drive", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor::internal::Drive", "controls::**::Drive", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "**::motor", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("a::b::motor", "**::motor", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls", "**", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("controls::motor", "**", Boolean.TRUE)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@SuppressWarnings("static-method")
	@ParameterizedTest
	@MethodSource("provideBacktrackingMatches")
	void testMultiSegmentWildcardBacktracking(final String packageName, final String pattern, final Boolean expected) {
		testMatch(packageName, pattern, expected);
	}

	private static Stream<Arguments> provideBacktrackingMatches() {
		return Stream.of(Arguments.of("a::x::b::b::c", "a::**::b::c", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("a::x::y::b::c", "a::**::b::c", Boolean.TRUE), //$NON-NLS-1$ //$NON-NLS-2$
				Arguments.of("a::x::y::b::d", "a::**::b::c", Boolean.FALSE)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private static void testMatch(final String packageName, final String pattern, final Boolean expected) {
		assertEquals(expected, Boolean.valueOf(PackageNameMatcher.matchesPattern(packageName, pattern)));
	}
}
