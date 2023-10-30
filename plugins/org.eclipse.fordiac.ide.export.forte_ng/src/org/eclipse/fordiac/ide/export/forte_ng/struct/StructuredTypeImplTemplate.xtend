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
import org.eclipse.fordiac.ide.model.data.StructuredType

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class StructuredTypeImplTemplate extends StructBaseTemplate {

	new(StructuredType type, String name, Path prefix) {
		super(type, name, prefix)
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		DEFINE_FIRMWARE_DATATYPE(«type.generateTypeNamePlain», «type.generateTypeSpec»);
		
		const CStringDictionary::TStringId «className»::scmElementNames[] = {«type.memberVariables.FORTENameList»};
		
		«className»::«className»() :
		    CIEC_STRUCT()«type.memberVariables.generateVariableInitializer» {
		}
		«IF !type.memberVariables.empty»
		
		«className»::«className»(«generateConstructorParameters») :
		    CIEC_STRUCT()«type.memberVariables.generateVariableInitializerFromParameters» {
		}
		«ENDIF»
		
		CStringDictionary::TStringId «className»::getStructTypeNameID() const {
		  return «type.generateTypeSpec»;
		}
		
		«generateSetValue»
		
		«type.memberVariables.generateAccessorDefinition("getMember", false)»
		«type.memberVariables.generateAccessorDefinition("getMember", true)»
	'''

	def protected generateImplIncludes() '''
		#include "«fileBasename».h"
		#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
		#include "«fileBasename»_gen.cpp"
		#endif
		
		«getDependencies(emptyMap).generateDependencyIncludes»
	'''
	
	def protected generateSetValue() '''
		void «className»::setValue(const CIEC_ANY &paValue) {
		  if (paValue.getDataTypeID() == e_STRUCT) {
		    auto &otherStruct = static_cast<const CIEC_STRUCT &>(paValue);
		    if («type.generateTypeSpec» == otherStruct.getStructTypeNameID()) {
		      operator=(static_cast<const «className» &>(paValue));
		    }
		  }
		}
	'''

}
