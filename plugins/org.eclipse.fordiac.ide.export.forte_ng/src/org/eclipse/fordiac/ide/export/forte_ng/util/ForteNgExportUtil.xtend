/**
 * Copyright (c) 2022 - 2023 Martin Erich Jobst
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

import org.eclipse.fordiac.ide.model.data.ArrayType
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

final class ForteNgExportUtil {
	private new() {
	}

	def static CharSequence generateName(VarDeclaration variable) {
		switch (root : variable.rootContainer) {
			BaseFBType case root.internalConstVars.contains(variable): '''var_const_«variable.name»'''
			AdapterType: '''var_«variable.name»()'''
			default: '''var_«variable.name»'''
		}
	}

	def static CharSequence generateTypeName(INamedElement type) {
		switch (type) {
			ArrayType:
				type.subranges.reverseView.fold(type.baseType.generateTypeName) [ result, subrange |
					val fixed = subrange.setLowerLimit && subrange.setUpperLimit
					'''«IF fixed»CIEC_ARRAY_FIXED«ELSE»CIEC_ARRAY_VARIABLE«ENDIF»<«result»«IF fixed», «subrange.lowerLimit», «subrange.upperLimit»«ENDIF»>'''
				].toString
			DataType: '''CIEC_«type.generateTypeNamePlain»«IF GenericTypes.isAnyType(type)»_VARIANT«ENDIF»'''
			default: type.name
		}
	}

	def static CharSequence generateTypeNameAsParameter(INamedElement type) {
		switch (type) {
			ArrayType:
				type.subranges.reverseView.fold(type.baseType.generateTypeName) [ result, subrange |
					'''CIEC_ARRAY_COMMON<«result»>'''
				].toString
			default: type.generateTypeName
		}
	}

	def static String generateTypeNamePlain(DataType type) {
		switch (type) {
			TimeType: "TIME"
			LtimeType: "LTIME"
			DateType: "DATE"
			LdateType: "LDATE"
			TimeOfDayType: "TIME_OF_DAY"
			LtodType: "LTIME_OF_DAY"
			DateAndTimeType: "DATE_AND_TIME"
			LdtType: "LDATE_AND_TIME"
			ArrayType: "ARRAY"
			StringType: "STRING"
			WstringType: "WSTRING"
			default: type.name
		}
	}
}
