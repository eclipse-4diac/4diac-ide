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

import org.eclipse.fordiac.ide.model.data.AnyDateType
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

abstract class AnyDateValue implements AnyElementaryValue {
	protected static final DateTimeFormatter TIME_OF_DAY_FORMATTER = new DateTimeFormatterBuilder() //
	.appendValue(ChronoField.HOUR_OF_DAY, 2) // HH
	.appendLiteral(':') // :
	.appendValue(ChronoField.MINUTE_OF_HOUR, 2) // mm
	.appendLiteral(':') // :
	.appendValue(ChronoField.SECOND_OF_MINUTE, 2) // ss
	.optionalStart() // optional fraction
	.appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true) // .SSSSSSSSS
	.toFormatter

	protected static final DateTimeFormatter DATE_AND_TIME_FORMATTER = new DateTimeFormatterBuilder() //
	.append(DateTimeFormatter.ISO_LOCAL_DATE) // YYYY-MM-dd
	.appendLiteral('-') // -
	.append(TIME_OF_DAY_FORMATTER) // HH:mm:ss(.SSSSSSSSS)
	.toFormatter()

	override AnyDateType getType()

	def long toNanos()
}
