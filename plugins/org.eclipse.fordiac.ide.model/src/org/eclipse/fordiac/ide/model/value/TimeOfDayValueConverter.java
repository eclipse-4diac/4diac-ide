/**
 * Copyright (c) 2022 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.value;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public final class TimeOfDayValueConverter implements ValueConverter<LocalTime> {
	public static final TimeOfDayValueConverter INSTANCE = new TimeOfDayValueConverter();

	static final DateTimeFormatter TIME_OF_DAY_FORMATTER = new DateTimeFormatterBuilder() //
			.appendValue(ChronoField.HOUR_OF_DAY, 2) // HH
			.appendLiteral(':') // :
			.appendValue(ChronoField.MINUTE_OF_HOUR, 2) // mm
			.appendLiteral(':') // :
			.appendValue(ChronoField.SECOND_OF_MINUTE, 2) // ss
			.optionalStart() // optional fraction
			.appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true) // .SSSSSSSSS
			.toFormatter();

	private TimeOfDayValueConverter() {
	}

	@Override
	public String toString(final LocalTime value) {
		return value.format(TIME_OF_DAY_FORMATTER);
	}

	@Override
	public LocalTime toValue(final String string) {
		try {
			return LocalTime.parse(string, TIME_OF_DAY_FORMATTER);
		} catch (final Exception e) {
			throw new IllegalArgumentException("Invalid time-of-day literal", e); //$NON-NLS-1$
		}
	}
}
