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

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode

class STDateAndTimeValueConverter extends AbstractNullSafeConverter<LocalDateTime> {

	static final DateTimeFormatter DATE_AND_TIME = new DateTimeFormatterBuilder() //
	.append(DateTimeFormatter.ISO_LOCAL_DATE) // YYYY-MM-dd
	.appendLiteral('-') // -
	.append(STTimeOfDayValueConverter.TIME_OF_DAY_FORMATTER) // HH:mm:ss(.SSSSSSSSS)
	.toFormatter()

	override protected internalToString(LocalDateTime value) {
		value.format(DATE_AND_TIME)
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		try {
			LocalDateTime.parse(string, DATE_AND_TIME)
		} catch (Exception e) {
			throw new ValueConverterException("Invalid date-and-time literal", node, e)
		}
	}

}
