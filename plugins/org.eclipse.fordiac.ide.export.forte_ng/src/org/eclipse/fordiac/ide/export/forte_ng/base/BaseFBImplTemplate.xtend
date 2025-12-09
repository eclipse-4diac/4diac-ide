/*******************************************************************************
 * Copyright (c) 2022, 2025 Martin Erich Jobst,
 *                          Primetals Technologies Austria GmbH
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
import java.util.Set
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.Method

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

abstract class BaseFBImplTemplate<T extends BaseFBType> extends ForteFBTemplate<T> {
	final Map<Algorithm, ILanguageSupport> algorithmLanguageSupport
	final Map<Method, ILanguageSupport> methodLanguageSupport

	new(T type, String name, Path prefix, String baseClass, Map<?, ?> options) {
		super(type, name, prefix, baseClass, options)
		algorithmLanguageSupport = type.algorithm.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it, options)
		]
		methodLanguageSupport = type.methods.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it, options)
		]
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		namespace «type.generateTypeNamespace» {
		  namespace {
		    «generateTypeHash»
		
		    «generateFBInterfaceDefinition»
		    «generateFBInterfaceSpecDefinition»
		    «generateInternalVarDefinition»
		  }
		
		  «generateFBDefinition»
		  «IF !type.internalConstVars.isEmpty»
		  	«type.internalConstVars.generateVariableDefinitions(true)»			
		  «ENDIF»
		
		  «FBClassName»::«FBClassName»(const StringId paInstanceNameId, CFBContainer &paContainer) :
		      «baseClass»(paContainer, cFBInterfaceSpec, paInstanceNameId, «IF !type.internalVars.empty»cInternalsNames«ELSE»{}«ENDIF»)«// no newline
		      			»«type.internalFbs.generateInternalFBInitializer»«// no newline
		      			»«(type.internalVars + type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateVariableInitializer»«// no newline
		      			»«(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAdapterInitializer»«// no newline
		      			»«generateConnectionInitializer» {
		  }
		
		  «(type.internalVars + type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		  «generateExecuteEvent»
		  «generateInterfaceDefinitions»
		  «type.internalVars.generateAccessorDefinition("getVarInternal", false)»
		  «generateAlgorithms»
		  «generateMethods»
		}
	'''

	def generateInternalVarDefinition() '''
		«IF !type.internalVars.isEmpty»
			
			const auto cInternalsNames = std::array{«type.internalVars.FORTENameList»};
		«ENDIF»
	'''

	def generateChangeFBExecutionState() //
	'''
		EMGMResponse «FBClassName»::changeFBExecutionState(EMGMCommandType paCommand) {
		  return changeFBExecutionStateHelper(paCommand, csmAmountOfInternalFBs, mInternalFBs);
		}
	'''

	def protected generateSendEvent(Event event) {
		if (event.blockFBNetworkElement instanceof AdapterFB) {
			return '''sendAdapterEvent(*«event.blockFBNetworkElement.generateName», «event.blockFBNetworkElement.type.generateTypeName»::scmEvent«event.name»ID, paECET);'''
		}
		'''sendOutputEvent(scmEvent«event.name»ID, paECET);'''
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

	override getErrors() {
		(super.getErrors + (algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap [
			getErrors
		].toSet).toList
	}

	override getWarnings() {
		(super.getWarnings + (algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap [
			getWarnings
		].toSet).toList
	}

	override getInfos() {
		(super.getInfos + (algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap [
			getInfos
		].toSet).toList
	}

	override Set<INamedElement> getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) +
			(algorithmLanguageSupport.values + methodLanguageSupport.values).filterNull.flatMap [
				getDependencies(options)
			]
		).toSet
	}
}
