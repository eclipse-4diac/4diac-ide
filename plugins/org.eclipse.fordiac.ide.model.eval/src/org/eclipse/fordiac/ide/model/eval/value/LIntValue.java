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

import org.eclipse.fordiac.ide.model.data.LintType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class LIntValue implements AnySignedValue {
	public static final LIntValue DEFAULT = new LIntValue(0);

	private final long value;

	private LIntValue(final long value) {
		this.value = value;
	}

	public static LIntValue toLIntValue(final long value) {
		return new LIntValue(value);
	}

	public static LIntValue toLIntValue(final Number value) {
		return new LIntValue(value.longValue());
	}

	public static LIntValue toLIntValue(final String value) {
		return LIntValue.toLIntValue(((Number) NumericValueConverter.INSTANCE.toValue(value)));
	}

	public static LIntValue toLIntValue(final AnyMagnitudeValue value) {
		return LIntValue.toLIntValue(value.longValue());
	}

	@Override
	public LintType getType() {
		return IecTypes.ElementaryTypes.LINT;
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
		final LIntValue other = (LIntValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return Long.toString(value);
	}
}
