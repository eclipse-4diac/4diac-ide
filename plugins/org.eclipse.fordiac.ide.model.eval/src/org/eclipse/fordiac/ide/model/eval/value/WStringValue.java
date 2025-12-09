/**
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import java.util.Objects;

import org.eclipse.fordiac.ide.model.data.WstringType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.WStringValueConverter;

public final class WStringValue implements AnyStringValue, AnyWCharsValue {
	public static final WStringValue DEFAULT = new WStringValue(""); //$NON-NLS-1$

	private final String value;

	private WStringValue(final String value) {
		this.value = value;
	}

	public static WStringValue toWStringValue(final String value) {
		return WStringValue.toWStringValue(value, AnyStringValue.MAX_LENGTH);
	}

	public static WStringValue toWStringValue(final String value, final int maxLength) {
		return new WStringValue(WStringValue.truncate(value, maxLength));
	}

	public static WStringValue toWStringValue(final AnyCharsValue value) {
		return WStringValue.toWStringValue(value, AnyStringValue.MAX_LENGTH);
	}

	public static WStringValue toWStringValue(final AnyCharsValue value, final int maxLength) {
		return WStringValue.toWStringValue(value.stringValue(), maxLength);
	}

	@Override
	public WCharValue charAt(final int index) {
		return WCharValue.toWCharValue(index > 0 && index <= value.length() ? value.charAt(index - 1) : '\u0000');
	}

	public WStringValue withCharAt(final int index, final WCharValue c) {
		if (index <= 0) {
			throw new StringIndexOutOfBoundsException(index);
		}
		final StringBuilder newValue = new StringBuilder(value);
		if (newValue.length() < index) {
			newValue.setLength(index);
		}
		newValue.setCharAt(index - 1, c.charValue());
		return new WStringValue(newValue.toString());
	}

	@Override
	public WstringType getType() {
		return IecTypes.ElementaryTypes.WSTRING;
	}

	@Override
	public int length() {
		return value.length();
	}

	@Override
	public char charValue() {
		return !value.isEmpty() ? value.charAt(0) : '\u0000';
	}

	@Override
	public String stringValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
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
		final WStringValue other = (WStringValue) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return WStringValueConverter.toString(stringValue(), true);
	}

	private static String truncate(final String value, final int maxLength) {
		if (value.length() > maxLength) {
			return value.substring(0, maxLength);
		}
		return value;
	}
}
