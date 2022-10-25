/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 				 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Martin Melik Merkumians - Adjustments for changed FORTE implementation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.base

import java.nio.file.Path
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.Method
import org.eclipse.xtend.lib.annotations.Accessors

abstract class BaseFBImplTemplate<T extends BaseFBType> extends ForteFBTemplate {
	@Accessors(PROTECTED_GETTER) final T type
	final Map<Algorithm, ILanguageSupport> algorithmLanguageSupport
	final Map<Method, ILanguageSupport> methodLanguageSupport

	new(T type, String name, Path prefix, String baseClass) {
		super(name, prefix, baseClass)
		this.type = type
		algorithmLanguageSupport = type.algorithm.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it)
		]
		methodLanguageSupport = type.methods.toInvertedMap [
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
			«type.generateInternalVarDefinition»
			
		«ENDIF»
		«IF !type.internalFbs.isEmpty»
			«type.generateInteralFbDeclarations»
			
		«ENDIF»	
		«IF !(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).empty»
			«generateInitialValueAssignmentDefinition((type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars))»
			
		«ENDIF»
		«IF !type.internalFbs.isEmpty»
			«generateChangeFBExecutionState»
			
		«ENDIF»	
		«generateAlgorithms»
		
		«generateMethods»
		
		«generateExecuteEvent»
		
	'''
	
	def generateChangeFBExecutionState() //
	'''
		EMGMResponse «FBClassName»::changeFBExecutionState(EMGMCommandType paCommand) {
		  EMGMResponse nRetVal = CFunctionBlock::changeFBExecutionState(paCommand);
		  if (e_RDY == nRetVal) {
		    nRetVal = changeInternalFBExecutionState(paCommand, csmAmountOfInternalFBs, mInternalFBs);
		  }
		  return nRetVal;
		}
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

	def protected generateMethods() '''
		«FOR method : type.methods»
			«methodLanguageSupport.get(method)?.generate(emptyMap)»
		«ENDFOR»
	'''

	def protected generateAlgorithmName(Algorithm alg) '''alg_«alg.name»'''

	def protected abstract CharSequence generateExecuteEvent()

	override protected generateImplIncludes() '''
		«super.generateImplIncludes»
		«(algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap[getDependencies(emptyMap)].generateDependencyIncludes»
	'''

	override getErrors() {
		(errors + (algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap[getErrors].toSet).
			toList
	}

	override getWarnings() {
		(warnings +
			(algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap[getWarnings].toSet).
			toList
	}

	override getInfos() {
		(infos + (algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap[getInfos].toSet).
			toList
	}
}
