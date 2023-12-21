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

import org.eclipse.fordiac.ide.model.data.LtodType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class LTimeOfDayValue implements AnyDateValue {
	public static final LTimeOfDayValue DEFAULT = new LTimeOfDayValue(0);

	private final long value;

	private LTimeOfDayValue(final long value) {
		ChronoField.NANO_OF_DAY.checkValidValue(value);
		this.value = value;
	}

	public static LTimeOfDayValue toLTimeOfDayValue(final long value) {
		return new LTimeOfDayValue(value);
	}

	public static LTimeOfDayValue toLTimeOfDayValue(final Number value) {
		return new LTimeOfDayValue(value.longValue());
	}

	public static LTimeOfDayValue toLTimeOfDayValue(final LocalTime value) {
		return LTimeOfDayValue.toLTimeOfDayValue(value.toNanoOfDay());
	}

	public static LTimeOfDayValue toLTimeOfDayValue(final String value) {
		return LTimeOfDayValue
				.toLTimeOfDayValue(((LocalTime) TypedValueConverter.INSTANCE_LTIME_OF_DAY.toValue(value)));
	}

	public static LTimeOfDayValue toLTimeOfDayValue(final AnyDateValue value) {
		return LTimeOfDayValue.toLTimeOfDayValue(value.toNanos());
	}

	@Override
	public LtodType getType() {
		return IecTypes.ElementaryTypes.LTIME_OF_DAY;
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
		final LTimeOfDayValue other = (LTimeOfDayValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return TypedValueConverter.INSTANCE_LTIME_OF_DAY.toString(toLocalTime());
	}
}
