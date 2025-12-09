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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import org.eclipse.fordiac.ide.model.data.LdateType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class LDateValue implements AnyDateValue {
	public static final LDateValue DEFAULT = new LDateValue(0);

	private final long value;

	private LDateValue(final long value) {
		this.value = value;
	}

	public static LDateValue toLDateValue(final long value) {
		return new LDateValue(value);
	}

	public static LDateValue toLDateValue(final Number value) {
		return new LDateValue(value.longValue());
	}

	public static LDateValue toLDateValue(final LocalDate value) {
		return new LDateValue((value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000_000_000L));
	}

	public static LDateValue toLDateValue(final String value) {
		return LDateValue.toLDateValue(((LocalDate) TypedValueConverter.INSTANCE_LDATE.toValue(value)));
	}

	public static LDateValue toLDateValue(final AnyDateValue value) {
		return LDateValue.toLDateValue(value.toNanos());
	}

	@Override
	public LdateType getType() {
		return IecTypes.ElementaryTypes.LDATE;
	}

	@Override
	public long toNanos() {
		return value;
	}

	public LocalDate toLocalDate() {
		return LocalDateTime.ofEpochSecond(value / 1000_000_000L, (int) (value % 1000_000_000L), ZoneOffset.UTC)
				.toLocalDate();
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
		final LDateValue other = (LDateValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return TypedValueConverter.INSTANCE_LDATE.toString(toLocalDate());
	}
}
