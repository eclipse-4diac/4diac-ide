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

import java.time.LocalTime
import java.time.temporal.ChronoField
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.TypedValueConverter

class TimeOfDayValue implements AnyDateValue {
	final long value;

	public static final TimeOfDayValue DEFAULT = new TimeOfDayValue(0)

	private new(long value) {
		ChronoField.NANO_OF_DAY.checkValidValue(value)
		this.value = value;
	}

	def static toTimeOfDayValue(long value) { new TimeOfDayValue(value) }

	def static toTimeOfDayValue(Number value) { new TimeOfDayValue(value.longValue) }

	def static toTimeOfDayValue(LocalTime value) { value.toNanoOfDay.toTimeOfDayValue }

	def static toTimeOfDayValue(String value) {
		(TypedValueConverter.INSTANCE_TIME_OF_DAY.toValue(value) as LocalTime).toTimeOfDayValue
	}

	def static toTimeOfDayValue(AnyDateValue value) { value.toNanos.toTimeOfDayValue }

	override TimeOfDayType getType() { ElementaryTypes.TIME_OF_DAY }

	override toNanos() { value }

	def LocalTime toLocalTime() {
		LocalTime.ofNanoOfDay(value)
	}

	override equals(Object obj) { if(obj instanceof TimeOfDayValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() { TypedValueConverter.INSTANCE_TIME_OF_DAY.toString(toLocalTime) }
}
