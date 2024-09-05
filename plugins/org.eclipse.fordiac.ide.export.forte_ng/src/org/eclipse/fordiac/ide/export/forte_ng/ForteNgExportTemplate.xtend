/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportOptions
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

abstract class ForteNgExportTemplate extends ExportTemplate {

	protected new(String name, Path prefix) {
		super(name, prefix)
	}

	def protected generateDependencyIncludes(Iterable<? extends INamedElement> dependencies) '''
		«dependencies.filter(DataType).generateTypeIncludes»
		«FOR include : dependencies.reject(DataType).map[generateDefiningInclude].toSet.sort»
			«include.generateDependencyInclude»
		«ENDFOR»
	'''

	def protected generateTypeIncludes(Iterable<DataType> types) '''
		«FOR include : types.map[generateDefiningInclude].toSet.sort»
			«include.generateDependencyInclude»
		«ENDFOR»
		«generateDependencyInclude("iec61131_functions.h")»
		«generateDependencyInclude("forte_array_common.h")»
		«generateDependencyInclude("forte_array.h")»
		«generateDependencyInclude("forte_array_fixed.h")»
		«generateDependencyInclude("forte_array_variable.h")»
	'''

	def protected generateDependencyInclude(String path) {
		if (ForteNgExportOptions.useSystemIncludes)
			'''#include <«path»>'''
		else
			'''#include "«path»"'''
	}

	def getFileBasename() { name.replaceAll("\\.[^.]+$", "") }
}
