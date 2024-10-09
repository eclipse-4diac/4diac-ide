/*******************************************************************************
 * Copyright (c) 2023, 2024 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.export.forte_ng.function

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType

class FunctionFBHeaderTemplate extends FunctionFBTemplate {

	new(FunctionFBType type, String name, Path prefix) {
		super(type, name, prefix, "CFunctionBlock")
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		«generateFBClassHeader»
		  «generateFBDeclaration»
		
		  private:
		    «generateFBInterfaceDeclaration»
		
		    «generateFBInterfaceSpecDeclaration»
		
		    void executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) override;
		
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		    «(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		
		  public:
		    «FBClassName»(CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer);
		    «generateInitializeDeclaration»
		
		    «generateInterfaceDeclarations»
		};
		
		«generateBody»
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		«generateDependencyInclude("funcbloc.h")»
		«super.generateHeaderIncludes»
	'''

	def protected generateBody() {
		if (bodyLanguageSupport !== null)
			bodyLanguageSupport.generate(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE})
		else
			'''«generateFunctionSignature»;'''
	}
}
