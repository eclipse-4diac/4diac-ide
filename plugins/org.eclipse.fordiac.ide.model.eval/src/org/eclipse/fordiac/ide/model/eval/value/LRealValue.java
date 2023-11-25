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

import org.eclipse.fordiac.ide.model.data.LrealType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class LRealValue implements AnyRealValue {
	public static final LRealValue DEFAULT = new LRealValue(0.0);

	private final double value;

	private LRealValue(final double value) {
		this.value = value;
	}

	public static LRealValue toLRealValue(final double value) {
		return new LRealValue(value);
	}

	public static LRealValue toLRealValue(final Number value) {
		return new LRealValue(value.doubleValue());
	}

	public static LRealValue toLRealValue(final String value) {
		return LRealValue.toLRealValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static LRealValue toLRealValue(final AnyMagnitudeValue value) {
		return LRealValue.toLRealValue(value.doubleValue());
	}

	@Override
	public LrealType getType() {
		return IecTypes.ElementaryTypes.LREAL;
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
		return (float) value;
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
		return Double.hashCode(value);
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
		final LRealValue other = (LRealValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Double.toString(value);
	}
}
