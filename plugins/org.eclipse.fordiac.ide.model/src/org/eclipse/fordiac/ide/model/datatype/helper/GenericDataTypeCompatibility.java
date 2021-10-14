/*******************************************************************************
 * Copyright (c) 2018-2020 Johannes Kepler University
 * 				 2020 Primetals Technologies Germany GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Michael Jaeger - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.datatype.helper;

import org.eclipse.fordiac.ide.model.data.AnyBitType;
import org.eclipse.fordiac.ide.model.data.AnyCharType;
import org.eclipse.fordiac.ide.model.data.AnyCharsType;
import org.eclipse.fordiac.ide.model.data.AnyDateType;
import org.eclipse.fordiac.ide.model.data.AnyElementaryType;
import org.eclipse.fordiac.ide.model.data.AnyIntType;
import org.eclipse.fordiac.ide.model.data.AnyMagnitudeType;
import org.eclipse.fordiac.ide.model.data.AnyNumType;
import org.eclipse.fordiac.ide.model.data.AnyRealType;
import org.eclipse.fordiac.ide.model.data.AnySignedType;
import org.eclipse.fordiac.ide.model.data.AnyStringType;
import org.eclipse.fordiac.ide.model.data.AnyType;
import org.eclipse.fordiac.ide.model.data.AnyUnsignedType;
import org.eclipse.fordiac.ide.model.data.DataType;


public final class GenericDataTypeCompatibility {

	public static boolean isAnyUnsignedCompatibleWith(final DataType other) {
		return other instanceof AnyUnsignedType || ElementaryDataTypeCompatibility.isAnyIntCompatibleWith(other);
	}

	public static boolean isAnySignedCompatibleWith(final DataType other) {
		return other instanceof AnySignedType || ElementaryDataTypeCompatibility.isAnyIntCompatibleWith(other);
	}

	public static boolean isAnyIntCompatibleWith(final DataType other) {
		return other instanceof AnyIntType || ElementaryDataTypeCompatibility.isAnyNumCompatibleWith(other);
	}

	public static boolean isAnyRealCompatibleWith(final DataType other) {
		return other instanceof AnyRealType || ElementaryDataTypeCompatibility.isAnyNumCompatibleWith(other);
	}

	public static boolean isAnyNumCompatibleWith(final DataType other) {
		return other instanceof AnyNumType || ElementaryDataTypeCompatibility.isAnyMagnitudeCompatibleWith(other);
	}

	public static boolean isAnyMagnitudeCompatibleWith(final DataType other) {
		return other instanceof AnyMagnitudeType
				|| ElementaryDataTypeCompatibility.isAnyElementaryCompatibleWith(other);
	}

	public static boolean isAnyElementaryCompatibleWith(final DataType other) {
		return other instanceof AnyElementaryType || ElementaryDataTypeCompatibility.isAnyCompatibleWith(other);
	}

	public static boolean isAnyCompatibleWith(final DataType other) {
		return other instanceof AnyType;
	}

	public static boolean isAnyBitCompatibleWith(final DataType other) {
		return other instanceof AnyBitType || ElementaryDataTypeCompatibility.isAnyElementaryCompatibleWith(other);
	}

	public static boolean isAnyCharsCompatibleWith(final DataType other) {
		return other instanceof AnyCharsType || ElementaryDataTypeCompatibility.isAnyElementaryCompatibleWith(other);
	}

	public static boolean isAnyStringCompatibleWith(final DataType other) {
		return other instanceof AnyStringType || ElementaryDataTypeCompatibility.isAnyCharsCompatibleWith(other);
	}

	public static boolean isAnyCharCompatibleWith(final DataType other) {
		return other instanceof AnyCharType || ElementaryDataTypeCompatibility.isAnyCharsCompatibleWith(other);
	}

	public static boolean isAnyDateCompatibleWith(final DataType other) {
		return other instanceof AnyDateType || ElementaryDataTypeCompatibility.isAnyElementaryCompatibleWith(other);
	}

	private GenericDataTypeCompatibility() {
		throw new UnsupportedOperationException();
	}

}
