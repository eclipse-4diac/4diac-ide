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

import org.eclipse.fordiac.ide.model.data.DintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class DIntValue implements AnySignedValue {
	public static final DIntValue DEFAULT = new DIntValue(0);

	private final int value;

	private DIntValue(final int value) {
		this.value = value;
	}

	public static DIntValue toDIntValue(final int value) {
		return new DIntValue(value);
	}

	public static DIntValue toDIntValue(final Number value) {
		return new DIntValue(value.intValue());
	}

	public static DIntValue toDIntValue(final String value) {
		return DIntValue.toDIntValue((Number) NumericValueConverter.INSTANCE.toValue(value));
	}

	public static DIntValue toDIntValue(final AnyMagnitudeValue value) {
		return DIntValue.toDIntValue(value.intValue());
	}

	@Override
	public DintType getType() {
		return IecTypes.ElementaryTypes.DINT;
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
		final DIntValue other = (DIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
