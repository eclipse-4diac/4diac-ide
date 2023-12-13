/**
 * Copyright (c) 2022, 2023 Martin Erich Jobst
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 */
package org.eclipse.fordiac.ide.model.eval.value;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.fordiac.ide.model.data.UlintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class ULIntValue implements AnyUnsignedValue {
	public static final ULIntValue DEFAULT = new ULIntValue(0);

	private final long value;

	private ULIntValue(final long value) {
		this.value = value;
	}

	public static ULIntValue toULIntValue(final long value) {
		return new ULIntValue(value);
	}

	public static ULIntValue toULIntValue(final Number value) {
		return new ULIntValue(value.longValue());
	}

	public static ULIntValue toULIntValue(final String value) {
		return ULIntValue.toULIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static ULIntValue toULIntValue(final AnyMagnitudeValue value) {
		return ULIntValue.toULIntValue(value.longValue());
	}

	@Override
	public UlintType getType() {
		return IecTypes.ElementaryTypes.ULINT;
	}

	@Override
	public byte byteValue() {
		return (byte) value;
	}

	@Override
	public short shortValue() {
		return (short) value;
	}

	@Override
	public int intValue() {
		return (int) value;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return value;
	}

	@Override
	public BigInteger bigIntegerValue() {
		return new BigInteger(Long.toUnsignedString(value));
	}

	@Override
	public BigDecimal bigDecimalValue() {
		return new BigDecimal(bigIntegerValue());
	}

	@Override
	public int hashCode() {
		return Long.hashCode(value);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ULIntValue other = (ULIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Long.toUnsignedString(value);
	}
}
