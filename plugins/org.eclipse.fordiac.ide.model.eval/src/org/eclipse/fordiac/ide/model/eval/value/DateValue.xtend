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

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.DateValueConverter

class DateValue implements AnyDateValue {
	final long value;

	public static final DateValue DEFAULT = new DateValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toDateValue(long value) { new DateValue(value) }

	def static toDateValue(Number value) { new DateValue(value.longValue) }

	def static toDateValue(LocalDate value) {
		new DateValue(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L)
	}

	def static toDateValue(String value) { DateValueConverter.INSTANCE.toValue(value).toDateValue }

	def static toDateValue(AnyDateValue value) { value.toNanos.toDateValue }

	override DateType getType() { ElementaryTypes.DATE }

	override toNanos() { value }

	override equals(Object obj) { if(obj instanceof DateValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() {
		DateValueConverter.INSTANCE.toString(
			LocalDateTime.ofEpochSecond(value / 1000000000L, (value % 1000000000L) as int, ZoneOffset.UTC).toLocalDate)
	}
}
