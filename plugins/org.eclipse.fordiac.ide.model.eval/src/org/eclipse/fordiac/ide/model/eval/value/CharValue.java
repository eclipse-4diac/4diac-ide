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

import org.eclipse.fordiac.ide.model.data.CharType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.StringValueConverter;

public final class CharValue implements AnyCharValue, AnySCharsValue {
	public static final CharValue DEFAULT = new CharValue(((byte) 0));

	private final byte value;

	private CharValue(final byte value) {
		this.value = value;
	}

	public static CharValue toCharValue(final byte value) {
		return new CharValue(value);
	}

	public static CharValue toCharValue(final String value) {
		return CharValue.toCharValue(value.isEmpty() ? (byte) 0 : (byte) value.charAt(0));
	}

	public static CharValue toCharValue(final AnyCharsValue value) {
		return CharValue.toCharValue(((byte) value.charValue()));
	}

	@Override
	public CharType getType() {
		return IecTypes.ElementaryTypes.CHAR;
	}

	@Override
	public int length() {
		return 1;
	}

	@Override
	public char charValue() {
		return (char) value;
	}

	@Override
	public String stringValue() {
		return Character.toString((char) value);
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
		final CharValue other = (CharValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return StringValueConverter.INSTANCE.toString(stringValue());
	}
}
