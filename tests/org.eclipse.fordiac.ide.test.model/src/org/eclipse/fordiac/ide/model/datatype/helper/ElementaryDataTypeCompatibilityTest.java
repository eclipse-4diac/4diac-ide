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

public class ElementaryDataTypeCompatibilityTest {

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> lrealImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("lrealImplicitlyCastableInTestCases")
	public void lrealImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.LREAL.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> realImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("realImplicitlyCastableInTestCases")
	public void realImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.REAL.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> lintImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("lintImplicitlyCastableInTestCases")
	public void lintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.LINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> dintImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("dintImplicitlyCastableInTestCases")
	public void dintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.DINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> intImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("intImplicitlyCastableInTestCases")
	public void intImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.INT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> sintImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("sintImplicitlyCastableInTestCases")
	public void sintImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.SINT.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> lwordImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("lwordImplicitlyCastableInTestCases")
	public void lwordImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.LWORD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> dwordImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("dwordImplicitlyCastableInTestCases")
	public void dwordImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.DWORD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> wordImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("wordImplicitlyCastableInTestCases")
	public void wordImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.WORD.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> byteImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("byteImplicitlyCastableInTestCases")
	public void byteImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.BYTE.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> boolImplicitlyCastableInTestCases() {
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
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("boolImplicitlyCastableInTestCases")
	public void boolImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String dataTypeName) {
		final var castable = IecTypes.ElementaryTypes.BOOL.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

	@SuppressWarnings({ "boxing" })
	public static Stream<Arguments> dateImplicitlyCastableInTestCases() {
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
				Arguments.of(IecTypes.ElementaryTypes.DATE, true, IecTypes.ElementaryTypes.DATE.getName()),
				Arguments.of(IecTypes.ElementaryTypes.LTOD, false, IecTypes.ElementaryTypes.LTOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.TOD, false, IecTypes.ElementaryTypes.TOD.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WSTRING, false, IecTypes.ElementaryTypes.WSTRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.STRING, false, IecTypes.ElementaryTypes.STRING.getName()),
				Arguments.of(IecTypes.ElementaryTypes.WCHAR, false, IecTypes.ElementaryTypes.WCHAR.getName()),
				Arguments.of(IecTypes.ElementaryTypes.CHAR, false, IecTypes.ElementaryTypes.CHAR.getName()));

	}

	@SuppressWarnings({ "static-method", "boxing" })
	@DisplayName("Implicit cast checks for DATE")
	@ParameterizedTest(name = "Cast in type {2}")
	@MethodSource("dateImplicitlyCastableInTestCases")
	public void dateImplicitlyCastableInTest(final DataType targetType, final boolean isAllowed,
			final String typeName) {
		final var castable = IecTypes.ElementaryTypes.DATE.isCompatibleWith(targetType);
		assertEquals(isAllowed, castable);
	}

}
