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

import org.eclipse.fordiac.ide.model.data.IntType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class IntValue implements AnySignedValue {
	public static final IntValue DEFAULT = new IntValue(((short) 0));

	private final short value;

	private IntValue(final short value) {
		this.value = value;
	}

	public static IntValue toIntValue(final short value) {
		return new IntValue(value);
	}

	public static IntValue toIntValue(final Number value) {
		return new IntValue(value.shortValue());
	}

	public static IntValue toIntValue(final String value) {
		return IntValue.toIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static IntValue toIntValue(final AnyMagnitudeValue value) {
		return IntValue.toIntValue(value.shortValue());
	}

	@Override
	public IntType getType() {
		return IecTypes.ElementaryTypes.INT;
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
		return value;
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
		final IntValue other = (IntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Short.toString(value);
	}
}
