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
 *                - refactor memory layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.struct

import java.nio.file.Path
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate
import org.eclipse.fordiac.ide.model.data.StructuredType

abstract class StructBaseTemplate extends ForteLibraryElementTemplate<StructuredType> {

	new(StructuredType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, options)
	}

	def protected CharSequence generateConstructorParameters() //
	'''«FOR param : type.memberVariables SEPARATOR ", "»const «param.generateVariableTypeNameAsParameter» &«param.generateNameAsParameter»«ENDFOR»'''
}
