/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
 * 				 2021 Primetals Technologies Austria GmbH
 * 				 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Jaeger - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - fixed isDateCompatibleWith
 *   Martin Melik Merkumians - adds missing keyword types
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.datatype.helper;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes;

public final class ElementaryDataTypeCompatibility {

	public static boolean isRealCompatibleWith(final DataType other) {
		return other == ElementaryTypes.REAL || isLRealCompatibleWith(other);
	}

	public static boolean isLRealCompatibleWith(final DataType other) {
		return (other == ElementaryTypes.LREAL || other == GenericTypes.ANY_REAL) || isAnyNumCompatibleWith(other);
	}

	public static boolean isUsintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.USINT || isUintCompatibleWith(other) || isIntCompatibleWith(other);
	}

	public static boolean isUintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.UINT || isUdintCompatibleWith(other) || isRealCompatibleWith(other)
				|| isDintCompatibleWith(other);
	}

	public static boolean isUdintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.UDINT || isUlintCompatibleWith(other) || isLRealCompatibleWith(other)
				|| isLintCompatibleWith(other);
	}

	public static boolean isUlintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.ULINT || isAnyUnsignedCompatibleWith(other);
	}

	public static boolean isSintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.SINT || isIntCompatibleWith(other);
	}

	public static boolean isIntCompatibleWith(final DataType other) {
		return other == ElementaryTypes.INT || isDintCompatibleWith(other) || isRealCompatibleWith(other);
	}

	public static boolean isDintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.DINT || isLintCompatibleWith(other) || isLRealCompatibleWith(other);
	}

	public static boolean isLintCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LINT || isAnySignedCompatibleWith(other);
	}

	public static boolean isTimeCompatibleWith(final DataType other) {
		return other == ElementaryTypes.TIME || isLtimeCompatibleWith(other);
	}

	public static boolean isLtimeCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LTIME || other == GenericTypes.ANY_DURATION
				|| isAnyMagnitudeCompatibleWith(other);
	}

	public static boolean isBoolCompatibleWith(final DataType other) {
		return other == ElementaryTypes.BOOL || isByteCompatibleWith(other);
	}

	public static boolean isByteCompatibleWith(final DataType other) {
		return other == ElementaryTypes.BYTE || isWordCompatibleWith(other);
	}

	public static boolean isWordCompatibleWith(final DataType other) {
		return other == ElementaryTypes.WORD || isDwordCompatibleWith(other);
	}

	public static boolean isDwordCompatibleWith(final DataType other) {
		return other == ElementaryTypes.DWORD || isLwordCompatibleWith(other);
	}

	public static boolean isLwordCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LWORD || other == GenericTypes.ANY_BIT || isAnyElementaryCompatibleWith(other);
	}

	public static boolean isStringCompatibleWith(final DataType other) {
		return other == ElementaryTypes.STRING || other == GenericTypes.ANY_STRING || isAnyCharsCompatibleWith(other);
	}

	public static boolean isWstringCompatibleWith(final DataType other) {
		return other == ElementaryTypes.WSTRING || other == GenericTypes.ANY_STRING || isAnyCharsCompatibleWith(other);
	}

	public static boolean isCharCompatibleWith(final DataType other) {
		return other == ElementaryTypes.CHAR || isStringCompatibleWith(other);
	}

	public static boolean isWcharCompatibleWith(final DataType other) {
		return other == ElementaryTypes.WCHAR || isWstringCompatibleWith(other);
	}

	public static boolean isDateAndTimeCompatibleWith(final DataType other) {
		return other == ElementaryTypes.DATE_AND_TIME || other == ElementaryTypes.DT
				|| other == ElementaryTypes.LDATE_AND_TIME || other == ElementaryTypes.LDT
				|| isAnyDateCompatibleWith(other);
	}

	public static boolean isLdtCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LDT || other == ElementaryTypes.LDATE_AND_TIME
				|| isAnyDateCompatibleWith(other);
	}

	public static boolean isDateCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LDATE || other == ElementaryTypes.DATE || isAnyDateCompatibleWith(other);
	}

	public static boolean isLdateCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LDATE || isAnyDateCompatibleWith(other);
	}

	public static boolean isTimeOfDayCompatibleWith(final DataType other) {
		return other == ElementaryTypes.TIME_OF_DAY || other == ElementaryTypes.TOD || isAnyDateCompatibleWith(other);
	}

	public static boolean isLtodCompatibleWith(final DataType other) {
		return other == ElementaryTypes.LTOD || other == ElementaryTypes.LTIME_OF_DAY || isAnyDateCompatibleWith(other);
	}

	public static boolean isAnyCharsCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_CHARS || isAnyElementaryCompatibleWith(other);
	}

	public static boolean isAnyUnsignedCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_SIGNED || isAnyIntCompatibleWith(other);
	}

	public static boolean isAnySignedCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_SIGNED || isAnyIntCompatibleWith(other);
	}

	public static boolean isAnyIntCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_INT || isAnyNumCompatibleWith(other);
	}

	public static boolean isAnyNumCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_NUM || isAnyMagnitudeCompatibleWith(other);
	}

	public static boolean isAnyMagnitudeCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_MAGNITUDE || isAnyElementaryCompatibleWith(other);
	}

	public static boolean isAnyElementaryCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_ELEMENTARY || isAnyCompatibleWith(other);
	}

	public static boolean isAnyCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY;
	}

	private static boolean isAnyDateCompatibleWith(final DataType other) {
		return other == GenericTypes.ANY_DATE || isAnyElementaryCompatibleWith(other);
	}

	private ElementaryDataTypeCompatibility() {
		throw new UnsupportedOperationException();
	}

}
