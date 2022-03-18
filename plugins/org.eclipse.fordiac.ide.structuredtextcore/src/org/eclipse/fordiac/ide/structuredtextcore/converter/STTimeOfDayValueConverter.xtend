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
package org.eclipse.fordiac.ide.structuredtextcore.converter

import java.time.LocalTime
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class STTimeOfDayValueConverter extends AbstractNullSafeConverter<LocalTime> {

	public static final DateTimeFormatter TIME_OF_DAY_FORMATTER = new DateTimeFormatterBuilder() //
	.appendValue(ChronoField.HOUR_OF_DAY, 2) // HH
	.appendLiteral(':') // :
	.appendValue(ChronoField.MINUTE_OF_HOUR, 2) // mm
	.appendLiteral(':') // :
	.appendValue(ChronoField.SECOND_OF_MINUTE, 2) // ss
	.optionalStart() // optional fraction
	.appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true) // .SSSSSSSSS
	.toFormatter

	override protected internalToString(LocalTime value) {
		value.format(TIME_OF_DAY_FORMATTER)
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		try {
			LocalTime.parse(string, TIME_OF_DAY_FORMATTER)
		} catch (Exception e) {
			throw new ValueConverterException("Invalid time-of-day literal", node, e)
		}
	}
}
