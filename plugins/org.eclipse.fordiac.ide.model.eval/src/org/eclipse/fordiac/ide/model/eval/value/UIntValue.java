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

import org.eclipse.fordiac.ide.model.data.UintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class UIntValue implements AnyUnsignedValue {
	public static final UIntValue DEFAULT = new UIntValue(((short) 0));

	private final short value;

	private UIntValue(final short value) {
		this.value = value;
	}

	public static UIntValue toUIntValue(final short value) {
		return new UIntValue(value);
	}

	public static UIntValue toUIntValue(final Number value) {
		return new UIntValue(value.shortValue());
	}

	public static UIntValue toUIntValue(final String value) {
		return UIntValue.toUIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static UIntValue toUIntValue(final AnyMagnitudeValue value) {
		return UIntValue.toUIntValue(value.shortValue());
	}

	@Override
	public UintType getType() {
		return IecTypes.ElementaryTypes.UINT;
	}

	@Override
	public byte byteValue() {
		return (byte) value;
	}

	@Override
	public short shortValue() {
		return value;
	}

	@Override
	public int intValue() {
		return Short.toUnsignedInt(value);
	}

	@Override
	public long longValue() {
		return Short.toUnsignedLong(value);
	}

	@Override
	public double doubleValue() {
		return this.intValue();
	}

	@Override
	public float floatValue() {
		return this.intValue();
	}

	@Override
	public BigInteger bigIntegerValue() {
		return BigInteger.valueOf(longValue());
	}

	@Override
	public BigDecimal bigDecimalValue() {
		return BigDecimal.valueOf(longValue());
	}

	@Override
	public int hashCode() {
		return Short.hashCode(value);
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
		final UIntValue other = (UIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Integer.toUnsignedString(intValue());
	}
}
