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
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class LDateValue extends AnyDateValue {
	final long value;

	public static final LDateValue DEFAULT = new LDateValue(0)

	private new(long value) {
		this.value = value;
	}

	def static toLDateValue(long value) { new LDateValue(value) }

	def static toLDateValue(Number value) { new LDateValue(value.longValue) }

	def static toLDateValue(LocalDate value) {
		new LDateValue(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L)
	}

	def static toLDateValue(String value) { LocalDate.parse(value).toLDateValue }

	def static toLDateValue(AnyDateValue value) { value.toNanos.toLDateValue }

	override LdateType getType() { ElementaryTypes.LDATE }

	override toNanos() { value }

	override equals(Object obj) { if(obj instanceof LDateValue) value == obj.value else false }

	override hashCode() { Long.hashCode(value) }

	override toString() {
		LocalDateTime.ofEpochSecond(value / 1000000000L, (value % 1000000000L) as int, ZoneOffset.UTC).toLocalDate.
			toString
	}
}
