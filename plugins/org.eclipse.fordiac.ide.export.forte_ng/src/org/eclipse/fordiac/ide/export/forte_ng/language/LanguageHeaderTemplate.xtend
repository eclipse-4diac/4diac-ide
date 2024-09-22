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
package org.eclipse.fordiac.ide.export.forte_ng.language

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport

class LanguageHeaderTemplate extends ForteNgExportTemplate {
	final ILanguageSupport languageSupport

	new(ILanguageSupport languageSupport, String name, Path prefix) {
		super(name, prefix)
		this.languageSupport = languageSupport
	}

	override generate() '''
		«generateHeader»

		«generateIncludeGuardStart»

		«generateHeaderIncludes»

		«generateLanguage»

		«generateIncludeGuardEnd»
	'''

	def protected generateLanguage() '''
		«languageSupport?.generate(#{ForteNgExportFilter.OPTION_HEADER -> true})»
	'''

	def protected generateHeader() '''
		/*************************************************************************
		 *** FORTE Language Element
		 ***
		 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
		 ***
		 *** Name: «fileBasename»
		 *************************************************************************/
	'''

	def protected generateIncludeGuardStart() '''
		#ifndef _«fileBasename.toUpperCase»_H_
		#define _«fileBasename.toUpperCase»_H_
	'''

	def protected generateIncludeGuardEnd() '''
		#endif // _«fileBasename.toUpperCase»_H_
	'''

	def protected generateHeaderIncludes() '''
		«IF languageSupport !== null»«languageSupport.getDependencies(#{ForteNgExportFilter.OPTION_HEADER -> true}).generateDependencyIncludes»«ENDIF»
	'''

	override getErrors() {
		if(languageSupport !== null) (super.errors + languageSupport.errors).toList else super.errors
	}

	override getWarnings() {
		if(languageSupport !== null) (super.warnings + languageSupport.warnings).toList else super.warnings
	}

	override getInfos() {
		if(languageSupport !== null) (super.infos + languageSupport.infos).toList else super.infos
	}
}
