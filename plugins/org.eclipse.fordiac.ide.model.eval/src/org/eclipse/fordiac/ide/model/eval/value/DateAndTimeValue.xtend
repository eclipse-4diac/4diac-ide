/*******************************************************************************
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.value

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.DateAndTimeValueConverter

class DateAndTimeValue implements AnyDateValue {
	final long value;

	public static final DateAndTimeValue DEFAULT = new DateAndTimeValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toDateAndTimeValue(long value) { new DateAndTimeValue(value) }

	def static toDateAndTimeValue(Number value) { new DateAndTimeValue(value.longValue) }

	def static toDateAndTimeValue(LocalDateTime value) {
		new DateAndTimeValue(LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(value, ChronoUnit.NANOS))
	}

	def static toDateAndTimeValue(String value) { DateAndTimeValueConverter.INSTANCE.toValue(value).toDateAndTimeValue }

	def static toDateAndTimeValue(AnyDateValue value) { value.toNanos.toDateAndTimeValue }

	override DateAndTimeType getType() { ElementaryTypes.DATE_AND_TIME }

	override toNanos() { value }

	override equals(Object obj) { if(obj instanceof DateAndTimeValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() {
		DateAndTimeValueConverter.INSTANCE.toString(
			LocalDateTime.ofEpochSecond(value / 1000000000L, (value % 1000000000L) as int, ZoneOffset.UTC))
	}
}
