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

import org.eclipse.fordiac.ide.model.data.WcharType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.WStringValueConverter;

public final class WCharValue implements AnyCharValue, AnyWCharsValue {
	public static final WCharValue DEFAULT = new WCharValue(((char) 0));

	private final char value;

	private WCharValue(final char value) {
		this.value = value;
	}

	public static WCharValue toWCharValue(final char value) {
		return new WCharValue(value);
	}

	public static WCharValue toWCharValue(final String value) {
		return new WCharValue(!value.isEmpty() ? value.charAt(0) : '\u0000');
	}

	public static WCharValue toWCharValue(final AnyCharsValue value) {
		return WCharValue.toWCharValue(value.charValue());
	}

	@Override
	public int length() {
		return 1;
	}

	@Override
	public char charValue() {
		return value;
	}

	@Override
	public String stringValue() {
		return Character.toString(value);
	}

	@Override
	public WcharType getType() {
		return IecTypes.ElementaryTypes.WCHAR;
	}

	@Override
	public int hashCode() {
		return Character.hashCode(value);
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
		final WCharValue other = (WCharValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return WStringValueConverter.INSTANCE.toString(stringValue());
	}
}
