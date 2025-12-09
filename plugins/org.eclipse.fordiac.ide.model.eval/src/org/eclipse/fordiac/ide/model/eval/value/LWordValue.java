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

import java.math.BigInteger;

import org.eclipse.fordiac.ide.model.data.LwordType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class LWordValue implements AnyBitValue {
	public static final LWordValue DEFAULT = new LWordValue(0);

	private final long value;

	private LWordValue(final long value) {
		this.value = value;
	}

	public static LWordValue toLWordValue(final long value) {
		return new LWordValue(value);
	}

	public static LWordValue toLWordValue(final Number value) {
		return new LWordValue(value.longValue());
	}

	public static LWordValue toLWordValue(final String value) {
		return LWordValue.toLWordValue(((Number) NumericValueConverter.INSTANCE_LWORD.toValue(value)));
	}

	public static LWordValue toLWordValue(final AnyBitValue value) {
		return LWordValue.toLWordValue(value.longValue());
	}

	@Override
	public LwordType getType() {
		return IecTypes.ElementaryTypes.LWORD;
	}

	@Override
	public boolean boolValue() {
		return value != 0;
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
	public BigInteger bigIntegerValue() {
		return new BigInteger(Long.toUnsignedString(value));
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
		final LWordValue other = (LWordValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return NumericValueConverter.INSTANCE_LWORD.toString(Long.valueOf(longValue()));
	}
}
