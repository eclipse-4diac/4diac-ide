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

import org.eclipse.fordiac.ide.model.data.UsintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class USIntValue implements AnyUnsignedValue {
	public static final USIntValue DEFAULT = new USIntValue(((byte) 0));

	private final byte value;

	private USIntValue(final byte value) {
		this.value = value;
	}

	public static USIntValue toUSIntValue(final byte value) {
		return new USIntValue(value);
	}

	public static USIntValue toUSIntValue(final Number value) {
		return new USIntValue(value.byteValue());
	}

	public static USIntValue toUSIntValue(final String value) {
		return USIntValue.toUSIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static USIntValue toUSIntValue(final AnyMagnitudeValue value) {
		return USIntValue.toUSIntValue(value.byteValue());
	}

	@Override
	public UsintType getType() {
		return IecTypes.ElementaryTypes.USINT;
	}

	@Override
	public byte byteValue() {
		return value;
	}

	@Override
	public short shortValue() {
		return (short) Byte.toUnsignedInt(value);
	}

	@Override
	public int intValue() {
		return Byte.toUnsignedInt(value);
	}

	@Override
	public long longValue() {
		return Byte.toUnsignedLong(value);
	}

	@Override
	public double doubleValue() {
		return intValue();
	}

	@Override
	public float floatValue() {
		return intValue();
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
		return Byte.hashCode(value);
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
		final USIntValue other = (USIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Integer.toUnsignedString(intValue());
	}
}
