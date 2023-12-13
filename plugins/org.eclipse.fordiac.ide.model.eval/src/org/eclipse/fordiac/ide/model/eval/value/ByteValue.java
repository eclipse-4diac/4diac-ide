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

import org.eclipse.fordiac.ide.model.data.ByteType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class ByteValue implements AnyBitValue {
	public static final ByteValue DEFAULT = new ByteValue((byte) 0);

	private final byte value;

	private ByteValue(final byte value) {
		this.value = value;
	}

	public static ByteValue toByteValue(final byte value) {
		return new ByteValue(value);
	}

	public static ByteValue toByteValue(final Number value) {
		return new ByteValue(value.byteValue());
	}

	public static ByteValue toByteValue(final String value) {
		return ByteValue.toByteValue((Number) NumericValueConverter.INSTANCE_BYTE.toValue(value));
	}

	public static ByteValue toByteValue(final AnyBitValue value) {
		return ByteValue.toByteValue(value.byteValue());
	}

	@Override
	public ByteType getType() {
		return IecTypes.ElementaryTypes.BYTE;
	}

	@Override
	public boolean boolValue() {
		return value != 0;
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
	public BigInteger bigIntegerValue() {
		return BigInteger.valueOf(longValue());
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
		final ByteValue other = (ByteValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return NumericValueConverter.INSTANCE_BYTE.toString(Integer.valueOf(intValue()));
	}
}
