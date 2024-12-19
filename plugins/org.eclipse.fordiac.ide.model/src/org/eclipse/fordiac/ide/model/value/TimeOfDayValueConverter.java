/**
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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

import java.text.MessageFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

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

	private static final Pattern SCANNER_PATTERN = Pattern
			.compile("\\G\\d[_\\d]++:\\d[_\\d]++:\\d[_\\d]++(?:\\.\\d[_\\d]++)?"); //$NON-NLS-1$

	private TimeOfDayValueConverter() {
	}

	@Override
	public String toString(final LocalTime value) {
		return value.format(TIME_OF_DAY_FORMATTER);
	}

	@Override
	public LocalTime toValue(final String string) {
		if (string.indexOf("__") != -1) { //$NON-NLS-1$
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE, string));
		}
		try {
			return LocalTime.parse(string.replace("_", ""), TIME_OF_DAY_FORMATTER); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (final Exception e) {
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_InvalidTimeOfDayLiteral, string),
					e);
		}
	}

	@Override
	public LocalTime toValue(final Scanner scanner)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
