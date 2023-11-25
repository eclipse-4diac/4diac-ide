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

import org.eclipse.fordiac.ide.model.data.SintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class SIntValue implements AnySignedValue {
	public static final SIntValue DEFAULT = new SIntValue(((byte) 0));

	private final byte value;

	private SIntValue(final byte value) {
		this.value = value;
	}

	public static SIntValue toSIntValue(final byte value) {
		return new SIntValue(value);
	}

	public static SIntValue toSIntValue(final Number value) {
		return new SIntValue(value.byteValue());
	}

	public static SIntValue toSIntValue(final String value) {
		return SIntValue.toSIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static SIntValue toSIntValue(final AnyMagnitudeValue value) {
		return SIntValue.toSIntValue(value.byteValue());
	}

	@Override
	public SintType getType() {
		return IecTypes.ElementaryTypes.SINT;
	}

	@Override
	public byte byteValue() {
		return value;
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
		final SIntValue other = (SIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Byte.toString(value);
	}
}
