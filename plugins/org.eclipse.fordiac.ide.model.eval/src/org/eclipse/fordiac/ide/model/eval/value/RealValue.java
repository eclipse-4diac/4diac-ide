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

import org.eclipse.fordiac.ide.model.data.RealType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class RealValue implements AnyRealValue {
	public static final RealValue DEFAULT = new RealValue(0.0f);

	private final float value;

	private RealValue(final float value) {
		this.value = value;
	}

	public static RealValue toRealValue(final float value) {
		return new RealValue(value);
	}

	public static RealValue toRealValue(final Number value) {
		return new RealValue(value.floatValue());
	}

	public static RealValue toRealValue(final String value) {
		return RealValue.toRealValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static RealValue toRealValue(final AnyMagnitudeValue value) {
		return RealValue.toRealValue(value.floatValue());
	}

	@Override
	public RealType getType() {
		return IecTypes.ElementaryTypes.REAL;
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
		return (long) value;
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
		return bigDecimalValue().toBigInteger();
	}

	@Override
	public BigDecimal bigDecimalValue() {
		return BigDecimal.valueOf(value);
	}

	@Override
	public int hashCode() {
		return Float.hashCode(value);
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
		final RealValue other = (RealValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Float.toString(value);
	}
}
