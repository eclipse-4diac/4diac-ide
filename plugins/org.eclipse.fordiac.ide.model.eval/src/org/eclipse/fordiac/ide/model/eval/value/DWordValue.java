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

import org.eclipse.fordiac.ide.model.data.DwordType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class DWordValue implements AnyBitValue {
	public static final DWordValue DEFAULT = new DWordValue(0);

	private final int value;

	private DWordValue(final int value) {
		this.value = value;
	}

	public static DWordValue toDWordValue(final int value) {
		return new DWordValue(value);
	}

	public static DWordValue toDWordValue(final Number value) {
		return new DWordValue(value.intValue());
	}

	public static DWordValue toDWordValue(final String value) {
		return DWordValue.toDWordValue(((Number) NumericValueConverter.INSTANCE_DWORD.toValue(value)));
	}

	public static DWordValue toDWordValue(final AnyBitValue value) {
		return DWordValue.toDWordValue(value.intValue());
	}

	@Override
	public DwordType getType() {
		return IecTypes.ElementaryTypes.DWORD;
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
		return value;
	}

	@Override
	public long longValue() {
		return Integer.toUnsignedLong(value);
	}

	@Override
	public BigInteger bigIntegerValue() {
		return BigInteger.valueOf(longValue());
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
		final DWordValue other = (DWordValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return NumericValueConverter.INSTANCE_DWORD.toString(Integer.valueOf(intValue()));
	}
}
