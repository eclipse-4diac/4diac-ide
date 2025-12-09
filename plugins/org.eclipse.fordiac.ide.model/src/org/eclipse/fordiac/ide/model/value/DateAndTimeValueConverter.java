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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public final class DateAndTimeValueConverter implements ValueConverter<LocalDateTime> {
	public static final DateAndTimeValueConverter INSTANCE = new DateAndTimeValueConverter();

	static final DateTimeFormatter DATE_AND_TIME = new DateTimeFormatterBuilder() //
			.append(DateTimeFormatter.ISO_LOCAL_DATE) // YYYY-MM-dd
			.appendLiteral('-') // -
			.append(TimeOfDayValueConverter.TIME_OF_DAY_FORMATTER) // HH:mm:ss(.SSSSSSSSS)
			.toFormatter();

	private static final Pattern SCANNER_PATTERN = Pattern
			.compile("\\G\\d[_\\d]++-\\d[_\\d]++-\\d[_\\d]++-\\d[_\\d]++:\\d[_\\d]++:\\d[_\\d]++(?:\\.\\d[_\\d]++)?"); //$NON-NLS-1$

	private DateAndTimeValueConverter() {
	}

	@Override
	public String toString(final LocalDateTime value) {
		return value.format(DATE_AND_TIME);
	}

	@Override
	public LocalDateTime toValue(final String string) {
		if (string.indexOf("__") != -1) { //$NON-NLS-1$
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_CONSECUTIVE_UNDERSCORES_ERROR_MESSAGE, string));
		}
		try {
			return LocalDateTime.parse(string.replace("_", ""), DATE_AND_TIME); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (final Exception e) {
			throw new IllegalArgumentException(
					MessageFormat.format(Messages.VALIDATOR_INVALID_DATE_AND_TIME_FORMAT, string), e);
		}
	}

	@Override
	public LocalDateTime toValue(final Scanner scanner)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
