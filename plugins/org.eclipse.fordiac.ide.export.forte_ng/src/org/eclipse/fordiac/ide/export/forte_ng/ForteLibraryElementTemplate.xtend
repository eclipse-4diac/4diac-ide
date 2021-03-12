/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 * 				 2020 Johannes Kepler Unviersity Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted base class for all types from fbtemplate
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import java.util.List
import org.eclipse.fordiac.ide.export.ExportTemplate
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

abstract class ForteLibraryElementTemplate extends ExportTemplate {

	public static final CharSequence EXPORT_PREFIX = "st_"

	new(String name, Path prefix) {
		super(name, prefix) 
	}

	def protected LibraryElement getType()


	def protected getExportPrefix() {
		return EXPORT_PREFIX
	}

	def protected generateHeader() '''
		/*************************************************************************
		 *** FORTE Library Element
		 ***
		 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
		 ***
		 *** Name: «type.name»
		 *** Description: «type.comment»
		 *** Version:
		«FOR info : type.versionInfo»
			***     «info.version»: «info.date»/«info.author» - «info.organization» - «info.remarks»
		«ENDFOR»
		 *************************************************************************/
	'''

	def protected generateIncludeGuardStart() '''
		#ifndef _«type.name.toUpperCase»_H_
		#define _«type.name.toUpperCase»_H_
	'''

	def protected generateIncludeGuardEnd() '''
		#endif // _«type.name.toUpperCase»_H_
	'''

	def protected generateTypeIncludes(Iterable<VarDeclaration> vars) '''
		«FOR include : vars.map[it.typeName].sort.toSet»
			#include "forte_«include.toLowerCase».h"
			«IF include.startsWith("ANY")»
				#error type contains variables of type ANY. Please check the usage of these variables as we can not gurantee correct usage on export!
			«ENDIF»
		«ENDFOR»
		«IF vars.exists[array]»
			#include "forte_array.h"
		«ENDIF»
		#include "forte_array_at.h"
	'''

	def protected generateAccessors(List<VarDeclaration> vars, String function) '''
		«FOR v : vars»
			CIEC_«v.typeName» «IF v.array»*«ELSE»&«ENDIF»«exportPrefix»«v.name»() {
			  «IF v.array»
			  	return static_cast<CIEC_«v.typeName»*>((*static_cast<CIEC_ARRAY *>(«function»(«vars.indexOf(v)»)))[0]); //the first element marks the start of the array
			  «ELSE»
			  	return *static_cast<CIEC_«v.typeName»*>(«function»(«vars.indexOf(v)»));
			  «ENDIF»
			}
			
		«ENDFOR»
	'''

	def protected getFORTEString(String s) '''g_nStringId«s»'''
	
	def protected getFORTENameList(List<? extends INamedElement> elements) {
		elements.map[name.FORTEString].join(", ")
	}

	def protected getFORTETypeList(List<? extends VarDeclaration> elements) {
		elements.map['''«IF it.array»«"ARRAY".FORTEString», «it.arraySize», «ENDIF»«it.type.name.FORTEString»'''].join(", ")
	}

}
