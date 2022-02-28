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

import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import java.nio.file.Path
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import java.util.Map
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent

abstract class BaseFBImplTemplate<T extends BaseFBType> extends ForteFBTemplate {
	@Accessors(PROTECTED_GETTER) final T type
	final Map<Algorithm, ILanguageSupport> algorithmLanguageSupport

	new(T type, String name, Path prefix, String baseClass) {
		super(name, prefix, baseClass)
		this.type = type
		algorithmLanguageSupport = type.algorithm.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it)
		]
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		
		«generateFBInterfaceDefinition»
		
		«generateFBInterfaceSpecDefinition»
		
		«IF !type.internalVars.isEmpty»
			«generateInternalVarDefinition(type)»
			
		«ENDIF»
		«IF !type.internalFbs.isEmpty»
			«generateInteralFbDeclarations(type)»
			
		«ENDIF»	
		«IF !(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).empty»
			«generateInitialValueAssignmentDefinition((type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars))»
			
		«ENDIF»
		«generateAlgorithms»
		
		«generateExecuteEvent»
		
	'''

	def protected dispatch generateSendEvent(Event event) '''
		sendOutputEvent(scm_nEvent«event.name»ID);
	'''

	def protected dispatch generateSendEvent(AdapterEvent event) '''
		sendAdapterEvent(scm_n«event.adapterDeclaration.name»AdpNum, FORTE_«event.adapterDeclaration.typeName»::scm_nEvent«event.adapterEventName»ID);
	'''

	def protected getAdapterEventName(AdapterEvent event) {
		event.name.split("\\.").get(1);
	}

	def protected generateAlgorithms() '''
		«FOR algorithm : type.algorithm»
			«algorithm.generateAlgorithm»
		«ENDFOR»
	'''

	def protected generateAlgorithm(Algorithm alg) '''
		void «FBClassName»::«alg.generateAlgorithmName»(void) {
		  «algorithmLanguageSupport.get(alg)?.generate(emptyMap)»
		}
	'''

	def protected generateAlgorithmName(Algorithm alg) '''alg_«alg.name»'''

	def protected abstract CharSequence generateExecuteEvent()

	override protected generateImplIncludes() '''
		«super.generateImplIncludes»
		«algorithmLanguageSupport.values.filterNull.flatMap[getDependencies(emptyMap)].generateDependencyIncludes»
	'''

	override getErrors() {
		(super.errors + algorithmLanguageSupport.values.filterNull.flatMap[errors].toSet).toList
	}

	override getWarnings() {
		(super.warnings + algorithmLanguageSupport.values.filterNull.flatMap[warnings].toSet).toList
	}

	override getInfos() {
		(super.infos + algorithmLanguageSupport.values.filterNull.flatMap[infos].toSet).toList
	}
}
