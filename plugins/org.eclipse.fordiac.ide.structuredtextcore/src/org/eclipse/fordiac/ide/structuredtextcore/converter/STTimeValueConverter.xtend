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

import java.math.BigDecimal
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.regex.Pattern
import org.eclipse.xtext.conversion.ValueConverterException
import org.eclipse.xtext.conversion.impl.AbstractNullSafeConverter
import org.eclipse.xtext.nodemodel.INode

class STTimeValueConverter extends AbstractNullSafeConverter<Duration> {

	static final Pattern VALUE_PATTERN = Pattern.compile("([\\+\\-\\.0-9]+)\\s*([a-zA-Z]+)")

	override protected internalToString(Duration value) {
		val days = value.toDays
		val minutes = value.toMinutesPart
		val seconds = value.toSecondsPart
		val millis = value.toNanosPart / 1000000
		val micros = value.toNanosPart / 1000 % 1000
		val nanos = value.toNanosPart % 1000
		return '''«IF days !== 0»«days»D«ENDIF»«IF minutes !== 0»«minutes»M«ENDIF»«IF seconds !== 0»«seconds»«ENDIF»«IF millis !== 0»«millis»MS«ENDIF»«IF micros !== 0»«micros»US«ENDIF»«IF nanos !== 0»«nanos»NS«ENDIF»'''
	}

	override protected internalToValue(String string, INode node) throws ValueConverterException {
		try {
			val matcher = VALUE_PATTERN.matcher(string.replaceAll("_", ""))
			//if(!matcher.matches) throw new ValueConverterException("Invalid time literal", node, null)
			matcher.results.map [
				val valueGroup = group(1)
				val unitGroup = group(2)
				val value = new BigDecimal(valueGroup)
				val unit = parseUnit(unitGroup, node)
				Duration.of(value.multiply(BigDecimal.valueOf(unit.duration.toNanos)).toBigIntegerExact.longValueExact,
					ChronoUnit.NANOS)
			].reduce(Duration.ZERO)[a, b|a.plus(b)]
		} catch (Exception e) {
			throw new ValueConverterException("Invalid time literal", node, e)
		}
	}

	def private ChronoUnit parseUnit(String string, INode node) {
		switch (string.toUpperCase) {
			case "D": ChronoUnit.DAYS
			case "H": ChronoUnit.HOURS
			case "M": ChronoUnit.MINUTES
			case "S": ChronoUnit.SECONDS
			case "MS": ChronoUnit.MILLIS
			case "US": ChronoUnit.MICROS
			case "NS": ChronoUnit.NANOS
			default: throw new ValueConverterException('''Invalid time unit «string»''', node, null)
		}
	}

}
