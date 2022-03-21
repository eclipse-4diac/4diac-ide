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
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.value.DateAndTimeValueConverter

class LDateAndTimeValue implements AnyDateValue {
	final long value;

	public static final LDateAndTimeValue DEFAULT = new LDateAndTimeValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toLDateAndTimeValue(long value) { new LDateAndTimeValue(value) }

	def static toLDateAndTimeValue(Number value) { new LDateAndTimeValue(value.longValue) }

	def static toLDateAndTimeValue(LocalDateTime value) {
		new LDateAndTimeValue(LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(value, ChronoUnit.NANOS))
	}

	def static toLDateAndTimeValue(String value) { DateAndTimeValueConverter.INSTANCE.toValue(value).toLDateAndTimeValue }

	def static toLDateAndTimeValue(AnyDateValue value) { value.toNanos.toLDateAndTimeValue }

	override LdtType getType() { ElementaryTypes.LDATE_AND_TIME }

	override toNanos() { value }

	override equals(Object obj) { if(obj instanceof LDateAndTimeValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() {
		DateAndTimeValueConverter.INSTANCE.toString(
			LocalDateTime.ofEpochSecond(value / 1000000000L, (value % 1000000000L) as int, ZoneOffset.UTC))
	}
}
