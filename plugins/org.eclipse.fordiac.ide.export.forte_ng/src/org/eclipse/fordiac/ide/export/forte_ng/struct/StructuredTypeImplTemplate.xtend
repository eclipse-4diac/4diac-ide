/*******************************************************************************
 * Copyright (c) 2020 Johannes Kepler University Linz
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Alois Zoitl - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.struct

import java.nio.file.Path
import org.eclipse.fordiac.ide.model.data.StructuredType

class StructuredTypeImplTemplate extends StructBaseTemplate {


	new(StructuredType type, String name, Path prefix) {
		super(type, name, prefix)
	}

	override generate() '''
		«generateHeader»

		«generateImplIncludes»

		DEFINE_FIRMWARE_DATATYPE(«type.name», «type.name.FORTEString»);
		
		«structClassName»::«structClassName»() :
		    CIEC_STRUCT(«type.name.FORTEString», «type.memberVariables.size», scmElementTypes, scmElementNames, e_APPLICATION + e_CONSTRUCTED + 1) {
		}
		
		const CStringDictionary::TStringId «structClassName»::scmElementTypes[] = {«type.memberVariables.FORTENameList»};
		const CStringDictionary::TStringId «structClassName»::scmElementNames[] = {«type.memberVariables.FORTETypeList»};

	'''

	def protected generateImplIncludes() '''
		#include "«structuredTypeFileName(type)».h"
		#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
		#include "«structuredTypeFileName(type)»_gen.cpp"
		#endif
	'''

}
