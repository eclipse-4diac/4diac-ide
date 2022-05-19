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
import java.time.temporal.ChronoUnit
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.TypedValueConverter

class LTimeValue implements AnyDurationValue {
	final long value;

	public static final LTimeValue DEFAULT = new LTimeValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toLTimeValue(long value) { new LTimeValue(value) }

	def static toLTimeValue(Number value) { new LTimeValue(value.longValue) }

	def static toLTimeValue(Duration value) { new LTimeValue(value.toNanos) }

	def static toLTimeValue(String string) {
		(TypedValueConverter.INSTANCE_LTIME.toValue(string) as Duration).toLTimeValue
	}

	def static toLTimeValue(AnyMagnitudeValue value) { value.longValue.toLTimeValue }

	override LtimeType getType() { ElementaryTypes.LTIME }

	override byteValue() { value as byte }

	override shortValue() { value as short }

	override intValue() { value as int }

	override longValue() { value }

	override doubleValue() { value }

	override floatValue() { value }

	override toDuration() { Duration.ofNanos(value) }

	override equals(Object obj) { if(obj instanceof LTimeValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() { TypedValueConverter.INSTANCE_LTIME.toString(Duration.of(value, ChronoUnit.NANOS)) }
}
