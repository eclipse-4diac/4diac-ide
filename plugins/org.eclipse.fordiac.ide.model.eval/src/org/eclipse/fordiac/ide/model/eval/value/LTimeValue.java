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
import java.time.Duration;

import org.eclipse.fordiac.ide.model.data.LtimeType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class LTimeValue implements AnyDurationValue {
	public static final LTimeValue DEFAULT = new LTimeValue(0);

	private final long value;

	private LTimeValue(final long value) {
		this.value = value;
	}

	public static LTimeValue toLTimeValue(final long value) {
		return new LTimeValue(value);
	}

	public static LTimeValue toLTimeValue(final Number value) {
		return new LTimeValue(value.longValue());
	}

	public static LTimeValue toLTimeValue(final Duration value) {
		return new LTimeValue(value.toNanos());
	}

	public static LTimeValue toLTimeValue(final String string) {
		return LTimeValue.toLTimeValue(((Duration) TypedValueConverter.INSTANCE_LTIME.toValue(string)));
	}

	public static LTimeValue toLTimeValue(final AnyMagnitudeValue value) {
		return LTimeValue.toLTimeValue(value.longValue());
	}

	@Override
	public LtimeType getType() {
		return IecTypes.ElementaryTypes.LTIME;
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
		return BigInteger.valueOf(value);
	}

	@Override
	public BigDecimal bigDecimalValue() {
		return BigDecimal.valueOf(value);
	}

	@Override
	public Duration toDuration() {
		return Duration.ofNanos(value);
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
		final LTimeValue other = (LTimeValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return TypedValueConverter.INSTANCE_LTIME.toString(toDuration());
	}
}
