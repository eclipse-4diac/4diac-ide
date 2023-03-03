/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 *               2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *   Martin Jobst - add constructor with member list
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.struct

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate
import org.eclipse.fordiac.ide.model.data.StructuredType

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

abstract class StructBaseTemplate extends ForteLibraryElementTemplate<StructuredType> {

	override protected getExportPrefix() '''''' // currently we don't want to have prefixes for structs members

	new(StructuredType type, String name, Path prefix) {
		super(type, name, prefix)
	}

	def protected getStructClassName() '''CIEC_«type.name»'''

	def static structuredTypeFileName(StructuredType type) '''forte_«type.name.toLowerCase»'''

	def protected CharSequence generateConstructorParameters() //
	'''«FOR param : type.memberVariables SEPARATOR ", "»const «param.generateTypeName» &«param.generateName»«ENDFOR»'''
}
