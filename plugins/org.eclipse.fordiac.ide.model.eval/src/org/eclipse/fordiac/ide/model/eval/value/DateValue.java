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

import org.eclipse.fordiac.ide.model.data.DateType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class DateValue implements AnyDateValue {
	public static final DateValue DEFAULT = new DateValue(0);

	private final long value;

	private DateValue(final long value) {
		this.value = value;
	}

	public static DateValue toDateValue(final long value) {
		return new DateValue(value);
	}

	public static DateValue toDateValue(final Number value) {
		return new DateValue(value.longValue());
	}

	public static DateValue toDateValue(final LocalDate value) {
		return new DateValue(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000_000_000L);
	}

	public static DateValue toDateValue(final String value) {
		return DateValue.toDateValue(((LocalDate) TypedValueConverter.INSTANCE_DATE.toValue(value)));
	}

	public static DateValue toDateValue(final AnyDateValue value) {
		return DateValue.toDateValue(value.toNanos());
	}

	@Override
	public DateType getType() {
		return IecTypes.ElementaryTypes.DATE;
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
		final DateValue other = (DateValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return TypedValueConverter.INSTANCE_DATE.toString(toLocalDate());
	}
}
