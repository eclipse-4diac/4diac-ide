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

import java.time.LocalTime;
import java.time.temporal.ChronoField;

import org.eclipse.fordiac.ide.model.data.TimeOfDayType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class TimeOfDayValue implements AnyDateValue {
	public static final TimeOfDayValue DEFAULT = new TimeOfDayValue(0);

	private final long value;

	private TimeOfDayValue(final long value) {
		ChronoField.NANO_OF_DAY.checkValidValue(value);
		this.value = value;
	}

	public static TimeOfDayValue toTimeOfDayValue(final long value) {
		return new TimeOfDayValue(value);
	}

	public static TimeOfDayValue toTimeOfDayValue(final Number value) {
		return new TimeOfDayValue(value.longValue());
	}

	public static TimeOfDayValue toTimeOfDayValue(final LocalTime value) {
		return TimeOfDayValue.toTimeOfDayValue(value.toNanoOfDay());
	}

	public static TimeOfDayValue toTimeOfDayValue(final String value) {
		return TimeOfDayValue.toTimeOfDayValue(((LocalTime) TypedValueConverter.INSTANCE_TIME_OF_DAY.toValue(value)));
	}

	public static TimeOfDayValue toTimeOfDayValue(final AnyDateValue value) {
		return TimeOfDayValue.toTimeOfDayValue(value.toNanos());
	}

	@Override
	public TimeOfDayType getType() {
		return IecTypes.ElementaryTypes.TIME_OF_DAY;
	}

	@Override
	public long toNanos() {
		return value;
	}

	public LocalTime toLocalTime() {
		return LocalTime.ofNanoOfDay(value);
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
		final TimeOfDayValue other = (TimeOfDayValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return TypedValueConverter.INSTANCE_TIME_OF_DAY.toString(toLocalTime());
	}
}
