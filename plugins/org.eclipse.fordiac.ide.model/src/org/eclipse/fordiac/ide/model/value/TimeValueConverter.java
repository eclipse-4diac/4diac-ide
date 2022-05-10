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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public final class TimeValueConverter implements ValueConverter<Duration> {
	public static final TimeValueConverter INSTANCE = new TimeValueConverter();

	static final Pattern VALUE_PATTERN = Pattern.compile("([\\+\\-\\.0-9]+)\\s*([a-zA-Z]+)"); //$NON-NLS-1$

	private TimeValueConverter() {
	}

	@Override
	public String toString(final Duration value) {
		final var days = value.toDays();
		final var minutes = value.toMinutesPart();
		final var seconds = value.toSecondsPart();
		final var millis = value.toNanosPart() / 1_000_000;
		final var micros = value.toNanosPart() / 1000 % 1000;
		final var nanos = value.toNanosPart() % 1000;
		final StringBuilder builder = new StringBuilder();
		if (days != 0) {
			builder.append(days).append('D');
		}
		if (minutes != 0) {
			builder.append(minutes).append('M');
		}
		if (seconds != 0) {
			builder.append(seconds).append('S');
		}
		if (millis != 0) {
			builder.append(millis).append("MS"); //$NON-NLS-1$
		}
		if (micros != 0) {
			builder.append(micros).append("US"); //$NON-NLS-1$
		}
		if (nanos != 0) {
			builder.append(nanos).append("NS"); //$NON-NLS-1$
		}
		return builder.toString();
	}

	@Override
	public Duration toValue(final String string) throws IllegalArgumentException {
		try {
			final var matcher = VALUE_PATTERN.matcher(string.replace("_", "")); //$NON-NLS-1$ //$NON-NLS-2$
			return matcher.results().map(result -> {
				final var valueGroup = result.group(1);
				final var unitGroup = result.group(2);
				final var value = new BigDecimal(valueGroup);
				final var unit = parseUnit(unitGroup);
				return Duration.of(value.multiply(BigDecimal.valueOf(unit.getDuration().toNanos())).toBigIntegerExact()
						.longValueExact(), ChronoUnit.NANOS);
			}).reduce(Duration.ZERO, Duration::plus);
		} catch (final Exception e) {
			throw new IllegalArgumentException(Messages.VALIDATOR_INVALID_TIME_LITERAL, e);
		}
	}

	private static ChronoUnit parseUnit(final String string) {
		switch (string.toUpperCase()) {
		case "D": //$NON-NLS-1$
			return ChronoUnit.DAYS;
		case "H": //$NON-NLS-1$
			return ChronoUnit.HOURS;
		case "M": //$NON-NLS-1$
			return ChronoUnit.MINUTES;
		case "S": //$NON-NLS-1$
			return ChronoUnit.SECONDS;
		case "MS": //$NON-NLS-1$
			return ChronoUnit.MILLIS;
		case "US": //$NON-NLS-1$
			return ChronoUnit.MICROS;
		case "NS": //$NON-NLS-1$
			return ChronoUnit.NANOS;
		default:
			throw new IllegalArgumentException("Invalid time unit"); //$NON-NLS-1$
		}
	}
}
