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

import java.time.Duration
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class TimeValue extends AnyDurationValue {
	final long value;

	public static final TimeValue DEFAULT = new TimeValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toTimeValue(long value) { new TimeValue(value) }

	def static toTimeValue(Number value) { new TimeValue(value.longValue) }

	def static toTimeValue(Duration value) { new TimeValue(value.toNanos) }

	def static toTimeValue(String value) { value.parseValue.toTimeValue }

	def static toTimeValue(AnyMagnitudeValue value) { value.longValue.toTimeValue }

	override TimeType getType() { ElementaryTypes.TIME }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override toDuration() { Duration.ofNanos(value) }

	override equals(Object obj) { if(obj instanceof TimeValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }
}
