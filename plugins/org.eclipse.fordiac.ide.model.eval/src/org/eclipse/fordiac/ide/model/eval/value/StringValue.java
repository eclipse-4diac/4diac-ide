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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.eclipse.fordiac.ide.model.data.StringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;

public final class StringValue implements AnyStringValue, AnySCharsValue {
	public static final StringValue DEFAULT = new StringValue(new byte[0]);

	private final byte[] value;

	private StringValue(final byte[] value) {
		this.value = value;
	}

	public static StringValue toStringValue(final String value) {
		return StringValue.toStringValue(value, AnyStringValue.MAX_LENGTH);
	}

	public static StringValue toStringValue(final String value, final int maxLength) {
		return new StringValue(StringValue.truncate(value.getBytes(StandardCharsets.UTF_8), maxLength));
	}

	public static StringValue toStringValue(final AnyCharsValue value) {
		return StringValue.toStringValue(value, AnyStringValue.MAX_LENGTH);
	}

	public static StringValue toStringValue(final AnyCharsValue value, final int maxLength) {
		if (value instanceof final CharValue charValue) {
			return maxLength > 0 ? new StringValue(new byte[] { (byte) charValue.charValue() }) : DEFAULT;
		}
		if (value instanceof final StringValue stringValue) {
			return new StringValue(truncate(stringValue.value, maxLength));
		}
		return StringValue.toStringValue(value.stringValue(), maxLength);
	}

	@Override
	public CharValue charAt(final int index) {
		return CharValue.toCharValue(index > 0 && index <= value.length ? value[index - 1] : (byte) 0);
	}

	public StringValue withCharAt(final int index, final CharValue c) {
		if (index <= 0) {
			throw new StringIndexOutOfBoundsException(index);
		}
		final byte[] newValue = Arrays.copyOf(value, Math.max(value.length, index));
		newValue[index - 1] = (byte) c.charValue();
		return new StringValue(newValue);
	}

	@Override
	public StringType getType() {
		return IecTypes.ElementaryTypes.STRING;
	}

	@Override
	public int length() {
		return value.length;
	}

	@Override
	public char charValue() {
		return value.length > 0 ? (char) value[0] : '\u0000';
	}

	@Override
	public String stringValue() {
		return new String(value, StandardCharsets.UTF_8);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(value);
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
		final StringValue other = (StringValue) obj;
		return Arrays.equals(value, other.value);
	}

	@Override
	public String toString() {
		return StringValueConverter.toString(stringValue(), false);
	}

	private static byte[] truncate(final byte[] value, final int maxLength) {
		if (value.length > maxLength) {
			return Arrays.copyOf(value, maxLength);
		}
		return value;
	}
}
