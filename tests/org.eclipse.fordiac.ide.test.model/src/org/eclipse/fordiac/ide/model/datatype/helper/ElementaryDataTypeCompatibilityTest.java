/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Melik Merkumians
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.model.datatype.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ElementaryDataTypeCompatibilityTest {

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> lrealImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LREAL")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("lrealImplicitlyCastableInTestCases")
	void lrealImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.LREAL.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> realImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, true, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for REAL")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("realImplicitlyCastableInTestCases")
	void realImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.REAL.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> uLintImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, true, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for ULINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("uLintImplicitlyCastableInTestCases")
	void uLintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.ULINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> uDintImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, true, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, true, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for UDINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("uDintImplicitlyCastableInTestCases")
	void uDintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.UDINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> uIntImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, true, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, true, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, true, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, true, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, true, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for UINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("uIntImplicitlyCastableInTestCases")
	void uIntImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.UINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> uSintImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, true, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, true, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, true, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, true, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, true, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, true, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, true, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for USINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("uSintImplicitlyCastableInTestCases")
	void uSintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.USINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> lintImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("lintImplicitlyCastableInTestCases")
	void lintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.LINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> dintImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, true, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for DINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("dintImplicitlyCastableInTestCases")
	void dintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.DINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> intImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, true, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, true, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, true, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for INT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("intImplicitlyCastableInTestCases")
	void intImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.INT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> sintImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, true, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, true, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, true, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, true, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, true, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, true, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for SINT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("sintImplicitlyCastableInTestCases")
	void sintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.SINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> lwordImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, true, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LWORD")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("lwordImplicitlyCastableInTestCases")
	void lwordImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.LWORD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> dwordImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, true, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, true, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for DWORD")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("dwordImplicitlyCastableInTestCases")
	void dwordImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.DWORD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> wordImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, true, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, true, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, true, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for WORD")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("wordImplicitlyCastableInTestCases")
	void wordImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.WORD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> byteImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, true, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, true, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, true, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, true, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for BYTE")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("byteImplicitlyCastableInTestCases")
	void byteImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.BYTE.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> boolImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, true, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, true, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, true, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, true, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, true, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for BOOL")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("boolImplicitlyCastableInTestCases")
	void boolImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.BOOL.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> lTimeImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, true, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LTIME")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("lTimeImplicitlyCastableInTestCases")
	void lTimeImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.LTIME.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> timeImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, true, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, true, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for TIME")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("timeImplicitlyCastableInTestCases")
	void timeImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.TIME.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> ldtImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, true, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LDT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("ldtImplicitlyCastableInTestCases")
	void ldtImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.LDT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> dtImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, true, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, true, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for DT")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("dtImplicitlyCastableInTestCases")
	void dtImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.DT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> lDateImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, true, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LDATE")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("lDateImplicitlyCastableInTestCases")
	void lDateImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.LDATE.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> dateImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, true, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, true, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings("static-method")
	@DisplayName("Implicit cast checks for DATE")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("dateImplicitlyCastableInTestCases")
	void dateImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.DATE.isCompatibleWith(targetType);
		assertEquals(Boolean.valueOf(isAllowed), Boolean.valueOf(castable));
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> lTodImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, true, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for LTOD")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("lTodImplicitlyCastableInTestCases")
	void lTodImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.LTOD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> todImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, true, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for TOD")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("todImplicitlyCastableInTestCases")
	void todImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.TOD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> wstringImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, true, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for WSTRING")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("wstringImplicitlyCastableInTestCases")
	void wstringImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.WSTRING.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> stringImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, true, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for STRING")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("stringImplicitlyCastableInTestCases")
	void stringImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.STRING.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> wCharImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, true, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, true, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for WCHAR")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("wCharImplicitlyCastableInTestCases")
	void wCharImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.WCHAR.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	static Stream<Arguments> charImplicitlyCastableInTestCases() {
		return Stream.of(Arguments.of(IecTypes.ElementaryTypes.LREAL, false, IecTypes.ElementaryTypes.LREAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.REAL, false, IecTypes.ElementaryTypes.REAL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LINT, false, IecTypes.ElementaryTypes.LINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DINT, false, IecTypes.ElementaryTypes.DINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.INT, false, IecTypes.ElementaryTypes.INT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.SINT, false, IecTypes.ElementaryTypes.SINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.ULINT, false, IecTypes.ElementaryTypes.ULINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UDINT, false, IecTypes.ElementaryTypes.UDINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.UINT, false, IecTypes.ElementaryTypes.UINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.USINT, false, IecTypes.ElementaryTypes.USINT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LWORD, false, IecTypes.ElementaryTypes.LWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DWORD, false, IecTypes.ElementaryTypes.DWORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WORD, false, IecTypes.ElementaryTypes.WORD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BYTE, false, IecTypes.ElementaryTypes.BYTE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.BOOL, false, IecTypes.ElementaryTypes.BOOL.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTIME, false, IecTypes.ElementaryTypes.LTIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TIME, false, IecTypes.ElementaryTypes.TIME.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDT, false, IecTypes.ElementaryTypes.LDT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DT, false, IecTypes.ElementaryTypes.DT.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LDATE, false, IecTypes.ElementaryTypes.LDATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.DATE, false, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, true, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, true, IecTypes.ElementaryTypes.CHAR.getName()));
	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for CHAR")
	@ParameterizedTest(name = "Cast in type {2} is {1}")
	@MethodSource("charImplicitlyCastableInTestCases")
	void charImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed, final String typeName) {
		final var castable = IecTypes.ElementaryTypes.CHAR.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

}
