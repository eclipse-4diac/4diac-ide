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
package org.eclipse.fordiac.ide.export.forte_ng.base

import java.nio.file.Path
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Method
import org.eclipse.xtend.lib.annotations.Accessors

abstract class BaseFBHeaderTemplate<T extends BaseFBType> extends ForteFBTemplate {
	@Accessors(PROTECTED_GETTER) final T type
	final Map<Method, ILanguageSupport> methodLanguageSupport

	new(T type, String name, Path prefix, String baseClass) {
		super(name, prefix, baseClass)
		this.type = type
		methodLanguageSupport = type.methods.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it)
		]
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
		  «IF !type.internalFbs.empty»
		  	static const size_t csmAmountOfInternalFBs = «type.internalFbs.size»;
		  	«generateInternalFbDefinition»
		  	
		  «ENDIF»
		  «IF !type.internalVars.isEmpty»
		  	«generateInternalVarDelcaration(type)»
		  	
		  «ENDIF»
		  «IF !(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).empty»
		  	«generateInitialValueAssignmentDeclaration»
		  «ENDIF»
		  «type.internalVars.generateAccessors("getVarInternal")»
		  «type.internalFbs.generateInternalFBAccessors»
		  «generateAlgorithms»
		  «generateMethods»
		  «generateAdditionalDeclarations»
		
		  virtual void executeEvent(int pa_nEIID);
		
		  «type.generateBasicFBDataArray»
		
		public:
		  «IF type.internalFbs.empty»
		  	«FBClassName»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
		  	    «baseClass»(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, «IF !type.internalVars.empty»&scm_stInternalVars«ELSE»nullptr«ENDIF», m_anFBConnData, m_anFBVarsData) {
		  «ELSE»
		  	«FBClassName»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
		  	    «baseClass»(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, «IF !type.internalVars.empty»&scm_stInternalVars«ELSE»nullptr«ENDIF», m_anFBConnData, m_anFBVarsData, scmInternalFBs, csmAmountOfInternalFBs) {
		  «ENDIF»
		  };
		
		  «IF !type.internalFbs.empty»
		  	virtual ~«FBClassName»() {
		  	  for(size_t i = 0; i < csmAmountOfInternalFBs; ++i){
		  	    delete mInternalFBs[i];
		  	  }
		  	  delete[] mInternalFBs;
		  	};
		  «ELSE»
		  	virtual ~«FBClassName»() = default;
		  «ENDIF»
		
		  «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		  «generateEventAccessorDefinitions»
		
		};
		
		«generateIncludeGuardEnd»
		
	'''

	def abstract CharSequence generateAdditionalDeclarations()

	override protected generateHeaderIncludes() '''
		«generateClassInclude»
		«IF !type.internalFbs.isEmpty»
			#include "typelib.h"
		«ENDIF»
		«((type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).map[getType]
			+ methodLanguageSupport.values.filterNull.flatMap[getDependencies(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE})]
			+ type.internalFbs.map[getType]
		).toSet.generateDependencyIncludes»
		«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterIncludes»
		
		«type.compilerInfo?.header»
	'''

	def abstract CharSequence generateClassInclude()

	def protected generateAlgorithms() '''
		«FOR alg : type.algorithm»
			«alg.generateAlgorithm»
		«ENDFOR»
	'''

	def protected generateAlgorithm(Algorithm alg) '''
		void alg_«alg.name»(void);
	'''

	def protected generateMethods() '''
		«FOR method : type.methods»
			«methodLanguageSupport.get(method)?.generate(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE})»
		«ENDFOR»
	'''

	override getErrors() {
		(errors + methodLanguageSupport.values.filterNull.flatMap[getErrors].toSet).toList
	}

	override getWarnings() {
		(warnings + methodLanguageSupport.values.filterNull.flatMap[getWarnings].toSet).toList
	}

	override getInfos() {
		(infos + methodLanguageSupport.values.filterNull.flatMap[getInfos].toSet).toList
	}
}
