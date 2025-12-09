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

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.fordiac.ide.model.Messages;

public final class TimeValueConverter implements ValueConverter<Duration> {
	public static final TimeValueConverter INSTANCE = new TimeValueConverter();

	static final Pattern VALUE_PATTERN = Pattern.compile("([+-]?\\d++(?:\\.\\d++)?)\\s*([a-zA-Z]++)"); //$NON-NLS-1$
	static final Pattern SCANNER_PATTERN = Pattern.compile("\\G[+-]?(?:\\d[_\\d]*+(?:\\.\\d[_\\d]*+)?\\s*\\w++)++"); //$NON-NLS-1$

	private TimeValueConverter() {
	}

	@Override
	public String toString(final Duration value) {
		if (value.isZero()) {
			return "0s"; //$NON-NLS-1$
		}
		final var days = value.toDays();
		final var hours = value.toHoursPart();
		final var minutes = value.toMinutesPart();
		final var seconds = value.toSecondsPart();
		final var millis = value.toNanosPart() / 1_000_000;
		final var micros = value.toNanosPart() / 1000 % 1000;
		final var nanos = value.toNanosPart() % 1000;
		final StringBuilder builder = new StringBuilder();
		if (days != 0) {
			builder.append(days).append('d');
		}
		if (hours != 0) {
			builder.append(hours).append('h');
		}
		if (minutes != 0) {
			builder.append(minutes).append('m');
		}
		if (seconds != 0) {
			builder.append(seconds).append('s');
		}
		if (millis != 0) {
			builder.append(millis).append("ms"); //$NON-NLS-1$
		}
		if (micros != 0) {
			builder.append(micros).append("us"); //$NON-NLS-1$
		}
		if (nanos != 0) {
			builder.append(nanos).append("ns"); //$NON-NLS-1$
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
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_INVALID_TIME_LITERAL, string),
					e);
		}
	}

	private static ChronoUnit parseUnit(final String string) {
		return switch (string.toUpperCase()) {
		case "D": //$NON-NLS-1$
			yield ChronoUnit.DAYS;
		case "H": //$NON-NLS-1$
			yield ChronoUnit.HOURS;
		case "M": //$NON-NLS-1$
			yield ChronoUnit.MINUTES;
		case "S": //$NON-NLS-1$
			yield ChronoUnit.SECONDS;
		case "MS": //$NON-NLS-1$
			yield ChronoUnit.MILLIS;
		case "US": //$NON-NLS-1$
			yield ChronoUnit.MICROS;
		case "NS": //$NON-NLS-1$
			yield ChronoUnit.NANOS;
		default:
			throw new IllegalArgumentException(MessageFormat.format(Messages.VALIDATOR_InvalidTimeUnit, string));
		};
	}

	@Override
	public Duration toValue(final Scanner scanner)
			throws IllegalArgumentException, NoSuchElementException, IllegalStateException {
		return toValue(scanner, SCANNER_PATTERN);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
