/**
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
 */
package org.eclipse.fordiac.ide.export.forte_ng.util

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import org.eclipse.fordiac.ide.model.data.AnyElementaryType
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.StructuredType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.value.TypedValueConverter

import static extension org.eclipse.xtext.util.Strings.convertToJavaString

final class ForteNgExportUtil {
	private new() {
	}

	def static CharSequence generateVariableDefaultValue(VarDeclaration decl) {
		if (decl.value?.value.nullOrEmpty) {
			decl.type.generateTypeDefaultValue
		} else {
			val converter = new TypedValueConverter(decl.type)
			val value = converter.toValue(decl.value.value)
			'''«decl.type.generateTypeName»(«switch (value) {
				String: '''"«value.convertToJavaString»"'''
				Duration: Long.toString(value.toNanos)
				LocalTime: Long.toString(value.toNanoOfDay)
				LocalDate: Long.toString(value.toEpochSecond(LocalTime.MIDNIGHT, ZoneOffset.UTC) * 1000000000L)
				LocalDateTime: Long.toString(LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).until(value, ChronoUnit.NANOS))
				default: value
			}»)'''
		}
	}

	def static CharSequence generateTypeDefaultValue(DataType type) {
		switch (type) {
			AnyStringType: '''«type.generateTypeName»("")'''
			AnyElementaryType: '''«type.generateTypeName»(0)'''
			ArrayType: '''«type.generateTypeName»()'''
			StructuredType: '''«type.generateTypeName»{}'''
			default:
				"0"
		}
	}

	def static CharSequence generateTypeName(VarDeclaration variable) //
	'''«IF variable.array»CIEC_ARRAY_COMMON<«ENDIF»«variable.type.generateTypeName»«IF variable.array»>«ENDIF»'''

	def static CharSequence generateTypeName(DataType type) {
		switch (type) {
			TimeType,
			LtimeType: "CIEC_TIME"
			DateType,
			LdateType: "CIEC_DATE"
			TimeOfDayType,
			LtodType: "CIEC_TIME_OF_DAY"
			DateAndTimeType,
			LdtType: "CIEC_DATE_AND_TIME"
			ArrayType: "CIEC_ARRAY"
			default: '''CIEC_«type.name»'''
		}
	}
}
