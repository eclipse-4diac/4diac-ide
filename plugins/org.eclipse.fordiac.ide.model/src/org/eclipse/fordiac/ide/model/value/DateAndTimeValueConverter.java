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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.eclipse.fordiac.ide.model.Messages;

public final class DateAndTimeValueConverter implements ValueConverter<LocalDateTime> {
	public static final DateAndTimeValueConverter INSTANCE = new DateAndTimeValueConverter();

	static final DateTimeFormatter DATE_AND_TIME = new DateTimeFormatterBuilder() //
			.append(DateTimeFormatter.ISO_LOCAL_DATE) // YYYY-MM-dd
			.appendLiteral('-') // -
			.append(TimeOfDayValueConverter.TIME_OF_DAY_FORMATTER) // HH:mm:ss(.SSSSSSSSS)
			.toFormatter();

	private DateAndTimeValueConverter() {
	}

	@Override
	public String toString(final LocalDateTime value) {
		return value.format(DATE_AND_TIME);
	}

	@Override
	public LocalDateTime toValue(final String string) {
		try {
			return LocalDateTime.parse(string, DATE_AND_TIME);
		} catch (final Exception e) {
			throw new IllegalArgumentException(Messages.VALIDATOR_INVALID_DATE_AND_TIME_FORMAT, e);
		}
	}
}
