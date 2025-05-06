/*******************************************************************************
 * Copyright (c) 2025 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.forte_ng.globalconst

import java.nio.file.Path
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteLibraryElementTemplate
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.model.libraryElement.GlobalConstants

class GlobalConstantsHeaderTemplate extends ForteLibraryElementTemplate<GlobalConstants> {

	new(GlobalConstants type, String name, Path prefix, Map<?, ?> options) {
		super(type, name, prefix, options)
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		class «className» final {
		  public:
		    «type.constants.generateVariableDeclarations(true)»
		    «className»() = delete;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	def protected generateHeaderIncludes() '''
		«getDependencies(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE}).generateDependencyIncludes»
		
		«type.compilerInfo?.header»
	'''
}
