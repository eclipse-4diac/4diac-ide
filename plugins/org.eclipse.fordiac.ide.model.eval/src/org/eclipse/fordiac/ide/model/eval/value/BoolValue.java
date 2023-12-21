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

import org.eclipse.fordiac.ide.model.data.BoolType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.BoolValueConverter;

public final class BoolValue implements AnyBitValue {
	public static final BoolValue FALSE = new BoolValue(false);
	public static final BoolValue TRUE = new BoolValue(true);
	public static final BoolValue DEFAULT = BoolValue.FALSE;

	private final boolean value;

	private BoolValue(final boolean value) {
		this.value = value;
	}

	public static BoolValue toBoolValue(final boolean value) {
		return new BoolValue(value);
	}

	public static BoolValue toBoolValue(final Boolean value) {
		return new BoolValue(value.booleanValue());
	}

	public static BoolValue toBoolValue(final String value) {
		return BoolValue.toBoolValue(BoolValueConverter.INSTANCE.toValue(value));
	}

	public static BoolValue toBoolValue(final AnyBitValue value) {
		return BoolValue.toBoolValue(value.boolValue());
	}

	@Override
	public BoolType getType() {
		return IecTypes.ElementaryTypes.BOOL;
	}

	@Override
	public boolean boolValue() {
		return value;
	}

	@Override
	public byte byteValue() {
		return ((byte) intValue());
	}

	@Override
	public short shortValue() {
		return ((short) intValue());
	}

	@Override
	public int intValue() {
		return value ? 1 : 0;
	}

	@Override
	public long longValue() {
		return intValue();
	}

	@Override
	public BigInteger bigIntegerValue() {
		return BigInteger.valueOf(longValue());
	}

	@Override
	public int hashCode() {
		return Boolean.hashCode(value);
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
		final BoolValue other = (BoolValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return BoolValueConverter.INSTANCE.toString(Boolean.valueOf(value));
	}
}
