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
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.ExportTemplate
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.data.DateAndTimeType
import org.eclipse.fordiac.ide.model.data.DateType
import org.eclipse.fordiac.ide.model.data.LdateType
import org.eclipse.fordiac.ide.model.data.LdtType
import org.eclipse.fordiac.ide.model.data.LtimeType
import org.eclipse.fordiac.ide.model.data.LtodType
import org.eclipse.fordiac.ide.model.data.TimeOfDayType
import org.eclipse.fordiac.ide.model.data.TimeType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.data.StringType
import org.eclipse.fordiac.ide.model.data.WstringType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.data.ArrayType

abstract class ForteNgExportTemplate extends ExportTemplate {

	protected new(String name, Path prefix) {
		super(name, prefix)
	}

	def protected generateDependencyIncludes(Iterable<? extends INamedElement> dependencies) '''
		«dependencies.filter(DataType).generateTypeIncludes»
		«FOR include : dependencies.reject(DataType).map[name].toSet»
			#include "«include».h"
		«ENDFOR»
	'''

	def protected generateTypeIncludes(Iterable<DataType> types) '''
		«FOR include : types.map[generateTypeInclude.toString].toSet»
			#include "«include»"
		«ENDFOR»
		#include "iec61131_functions.h"
		#include "forte_array_common.h"
		#include "forte_array.h"
		#include "forte_array_fixed.h"
		#include "forte_array_variable.h"
	'''

	def protected CharSequence generateTypeInclude(DataType type) {
		switch (type) {
			TimeType: "forte_time.h"
			LtimeType: "forte_ltime.h"
			DateType: "forte_date.h"
			LdateType: "forte_ldate.h"
			TimeOfDayType: "forte_time_of_day.h"
			LtodType: "forte_ltime_of_day.h"
			DateAndTimeType: "forte_date_and_time.h"
			LdtType: "forte_ldate_and_time.h"
			StringType case type.isSetMaxLength: "forte_string_fixed.h"
			StringType: "forte_string.h"
			WstringType: "forte_wstring.h"
			ArrayType: type.baseType.generateTypeInclude
			case GenericTypes.isAnyType(type): '''forte_«type.name.toLowerCase»_variant.h'''
			default: '''forte_«type.name.toLowerCase».h'''
		}
	}

	def getFileBasename() { name.replaceAll("\\.[^.]+$", "") }
}
