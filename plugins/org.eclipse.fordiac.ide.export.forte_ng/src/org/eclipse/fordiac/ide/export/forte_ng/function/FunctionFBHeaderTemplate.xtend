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
import java.util.Map
import java.util.Set
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

class FunctionFBHeaderTemplate extends ForteFBTemplate<FunctionFBType> {
	final ILanguageSupport bodyLanguageSupport

	new(FunctionFBType type, String name, Path prefix) {
		super(type, name, prefix, "CFunctionBlock")
		bodyLanguageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", type.body)
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
		    «(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		
		  public:
		    «FBClassName»(CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer);
		    «generateInitializeDeclaration»
		
		    «generateInterfaceDeclarations»
		};
		
		«generateBody»
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		#include "funcbloc.h"
		«getDependencies(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE}).generateDependencyIncludes»
		
		«type.compilerInfo?.header»
	'''

	def protected generateBody() {
		bodyLanguageSupport.generate(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE})
	}

	override getErrors() {
		(super.getErrors + bodyLanguageSupport.errors).toList
	}

	override getWarnings() {
		(super.getErrors + bodyLanguageSupport.warnings).toList
	}

	override getInfos() {
		(super.getErrors + bodyLanguageSupport.infos).toList
	}

	override Set<INamedElement> getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) + bodyLanguageSupport.getDependencies(options)).toSet
	}
}
