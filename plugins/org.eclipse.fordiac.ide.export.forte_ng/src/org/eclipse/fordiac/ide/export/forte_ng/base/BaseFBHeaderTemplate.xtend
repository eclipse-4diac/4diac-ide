/*******************************************************************************
 * Copyright (c) 2022, 2024 Martin Erich Jobst,
 *               			Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - Adjustments for changed FORTE implementation,
 *     add export for local constants
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.base

import java.nio.file.Path
import java.util.Map
import java.util.Set
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.forte_ng.ForteNgExportFilter
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.Method

abstract class BaseFBHeaderTemplate<T extends BaseFBType> extends ForteFBTemplate<T> {
	final Map<Method, ILanguageSupport> methodLanguageSupport
	final Map<Object, Object> cache = newHashMap

	new(T type, String name, Path prefix, String baseClass) {
		super(type, name, prefix, baseClass)
		methodLanguageSupport = type.methods.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it, #{ILanguageSupport.OPTION_CACHE -> cache})
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
		
		    «generateInternalVarDeclaration(type)»
		    «type.internalVars.generateVariableDeclarations(false)»
		    «type.internalConstVars.generateVariableDeclarations(true)»
		    «generateAccessorDeclaration("getVarInternal", false)»
		
		    «type.internalFbs.generateInternalFBDeclarations»
		    «generateAlgorithms»
		    «generateMethods»
		    «generateAdditionalDeclarations»
		    void executeEvent(TEventID paEIID, CEventChainExecutionThread *const paECET) override;
		
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		    «(type.internalVars + type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		
		  public:
		    «FBClassName»(CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer);
		    «generateInitializeDeclaration»
		
		    «generateInterfaceDeclarations»
		};
		
		«generateIncludeGuardEnd»
		
	'''

	def abstract CharSequence generateAdditionalDeclarations()

	override protected generateHeaderIncludes() '''
		«generateClassInclude»
		«super.generateHeaderIncludes»
	'''

	def abstract CharSequence generateClassInclude()

	def protected generateAlgorithms() '''
		«FOR alg : type.algorithm AFTER '\n'»
			«alg.generateAlgorithm»
		«ENDFOR»
	'''

	def protected generateAlgorithm(Algorithm alg) '''
		void alg_«alg.name»(void);
	'''

	def protected generateMethods() '''
		«FOR method : type.methods AFTER '\n'»
			«methodLanguageSupport.get(method)?.generate(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE})»
		«ENDFOR»
	'''

	override getErrors() {
		(super.getErrors + methodLanguageSupport.values.filterNull.flatMap[getErrors].toSet).toList
	}

	override getWarnings() {
		(super.getWarnings + methodLanguageSupport.values.filterNull.flatMap[getWarnings].toSet).toList
	}

	override getInfos() {
		(super.getInfos + methodLanguageSupport.values.filterNull.flatMap[getInfos].toSet).toList
	}
	
	override Set<INamedElement> getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) +
			methodLanguageSupport.values.filterNull.flatMap[getDependencies(options)] + type.internalFbs.map[getType]
			).toSet
	}
}
