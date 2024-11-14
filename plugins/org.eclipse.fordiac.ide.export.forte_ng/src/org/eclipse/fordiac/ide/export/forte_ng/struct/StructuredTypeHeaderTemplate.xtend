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
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.model.data.StructuredType

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class StructuredTypeHeaderTemplate extends StructBaseTemplate {

	new(StructuredType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, options)
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		class «className» final : public CIEC_STRUCT {
		  DECLARE_FIRMWARE_DATATYPE(«type.generateTypeNamePlain»)
		
		  public:
		    «className»();
		«IF !type.memberVariables.empty»
		
		    «className»(«generateConstructorParameters»);
		
		    «type.memberVariables.generateVariableDeclarations(false)»
		«ENDIF»
		    size_t getStructSize() const override {
		      return «type.memberVariables.size»;
		    }
		
		    const CStringDictionary::TStringId* elementNames() const override {
		      return scmElementNames;
		    }
		
		    CStringDictionary::TStringId getStructTypeNameID() const override;
		
		    void setValue(const CIEC_ANY &paValue) override;
		
		    «generateAccessorDeclaration("getMember", false)»
		    «generateAccessorDeclaration("getMember", true)»
		
		  private:
		    static const CStringDictionary::TStringId scmElementNames[];
		
		};
		
		«generateIncludeGuardEnd»
		
	'''

	def protected generateHeaderIncludes() '''
		«generateDependencyInclude("forte_struct.h")»
		
		«getDependencies(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE}).generateDependencyIncludes»
		
		«type.compilerInfo?.header»
	'''
}
