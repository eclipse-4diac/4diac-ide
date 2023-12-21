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

import org.eclipse.fordiac.ide.model.data.WordType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.NumericValueConverter;

public final class WordValue implements AnyBitValue {
	public static final WordValue DEFAULT = new WordValue(((short) 0));

	private final short value;

	private WordValue(final short value) {
		this.value = value;
	}

	public static WordValue toWordValue(final short value) {
		return new WordValue(value);
	}

	public static WordValue toWordValue(final Number value) {
		return new WordValue(value.shortValue());
	}

	public static WordValue toWordValue(final String value) {
		return WordValue.toWordValue(((Number) NumericValueConverter.INSTANCE_WORD.toValue(value)));
	}

	public static WordValue toWordValue(final AnyBitValue value) {
		return WordValue.toWordValue(value.shortValue());
	}

	@Override
	public WordType getType() {
		return IecTypes.ElementaryTypes.WORD;
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
		return ((short) Short.toUnsignedInt(value));
	}

	@Override
	public int intValue() {
		return Short.toUnsignedInt(value);
	}

	@Override
	public long longValue() {
		return Short.toUnsignedLong(value);
	}

	@Override
	public BigInteger bigIntegerValue() {
		return BigInteger.valueOf(longValue());
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
		final WordValue other = (WordValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return NumericValueConverter.INSTANCE_WORD.toString(Integer.valueOf(intValue()));
	}
}
