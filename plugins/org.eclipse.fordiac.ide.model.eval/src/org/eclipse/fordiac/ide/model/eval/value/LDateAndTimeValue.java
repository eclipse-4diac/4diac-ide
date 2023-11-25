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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.eclipse.fordiac.ide.model.data.LdtType;
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes;
import org.eclipse.fordiac.ide.model.value.TypedValueConverter;

public final class LDateAndTimeValue implements AnyDateValue {
	public static final LDateAndTimeValue DEFAULT = new LDateAndTimeValue(0);

	private final long value;

	private LDateAndTimeValue(final long value) {
		this.value = value;
	}

	public static LDateAndTimeValue toLDateAndTimeValue(final long value) {
		return new LDateAndTimeValue(value);
	}

	public static LDateAndTimeValue toLDateAndTimeValue(final Number value) {
		return new LDateAndTimeValue(value.longValue());
	}

	public static LDateAndTimeValue toLDateAndTimeValue(final LocalDateTime value) {
		return new LDateAndTimeValue(
				LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(value, ChronoUnit.NANOS));
	}

	public static LDateAndTimeValue toLDateAndTimeValue(final String value) {
		return LDateAndTimeValue
				.toLDateAndTimeValue(((LocalDateTime) TypedValueConverter.INSTANCE_LDATE_AND_TIME.toValue(value)));
	}

	public static LDateAndTimeValue toLDateAndTimeValue(final AnyDateValue value) {
		return LDateAndTimeValue.toLDateAndTimeValue(value.toNanos());
	}

	@Override
	public LdtType getType() {
		return IecTypes.ElementaryTypes.LDATE_AND_TIME;
	}

	@Override
	public long toNanos() {
		return value;
	}

	public LocalDateTime toLocalDateTime() {
		return LocalDateTime.ofEpochSecond(value / 1000_000_000L, (int) (value % 1000_000_000L), ZoneOffset.UTC);
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
		final LDateAndTimeValue other = (LDateAndTimeValue) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return TypedValueConverter.INSTANCE_LDATE_AND_TIME.toString(toLocalDateTime());
	}
}
