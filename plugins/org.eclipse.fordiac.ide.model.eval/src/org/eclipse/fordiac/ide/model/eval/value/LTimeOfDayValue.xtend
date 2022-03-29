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
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.TimeOfDayValueConverter

class LTimeOfDayValue implements AnyDateValue {
	final long value;

	public static final LTimeOfDayValue DEFAULT = new LTimeOfDayValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toLTimeOfDayValue(long value) { new LTimeOfDayValue(value) }

	def static toLTimeOfDayValue(Number value) { new LTimeOfDayValue(value.longValue) }

	def static toLTimeOfDayValue(LocalTime value) { value.toNanoOfDay.toLTimeOfDayValue }

	def static toLTimeOfDayValue(String value) { TimeOfDayValueConverter.INSTANCE.toValue(value).toLTimeOfDayValue }

	def static toLTimeOfDayValue(AnyDateValue value) { value.toNanos.toLTimeOfDayValue }

	override LtodType getType() { ElementaryTypes.LTIME_OF_DAY }

	override toNanos() { value }

	override equals(Object obj) { if(obj instanceof LTimeOfDayValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }
	
	override toString() { TimeOfDayValueConverter.INSTANCE.toString(LocalTime.ofNanoOfDay(value)) }
}
