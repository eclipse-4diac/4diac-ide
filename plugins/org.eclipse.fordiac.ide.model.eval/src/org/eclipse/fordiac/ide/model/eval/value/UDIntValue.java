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

import org.eclipse.fordiac.ide.model.data.UdintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class UDIntValue implements AnyUnsignedValue {
	public static final UDIntValue DEFAULT = new UDIntValue(0);

	private final int value;

	private UDIntValue(final int value) {
		this.value = value;
	}

	public static UDIntValue toUDIntValue(final int value) {
		return new UDIntValue(value);
	}

	public static UDIntValue toUDIntValue(final Number value) {
		return new UDIntValue(value.intValue());
	}

	public static UDIntValue toUDIntValue(final String value) {
		return UDIntValue.toUDIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static UDIntValue toUDIntValue(final AnyMagnitudeValue value) {
		return UDIntValue.toUDIntValue(value.intValue());
	}

	@Override
	public UdintType getType() {
		return IecTypes.ElementaryTypes.UDINT;
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
		return value;
	}

	@Override
	public long longValue() {
		return Integer.toUnsignedLong(value);
	}

	@Override
	public double doubleValue() {
		return longValue();
	}

	@Override
	public float floatValue() {
		return longValue();
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
		return Integer.hashCode(value);
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
		final UDIntValue other = (UDIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Integer.toUnsignedString(value);
	}
}
