/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class FunctionFBImplTemplate extends ForteFBTemplate<FunctionFBType> {
	final ILanguageSupport bodyLanguageSupport

	new(FunctionFBType type, String name, Path prefix) {
		super(type, name, prefix, "CFunctionBlock")
		bodyLanguageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", type.body)
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		«generateFBInterfaceDefinition»
		«generateFBInterfaceSpecDefinition»
		
		«FBClassName»::«FBClassName»(const CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer) :
		    «baseClass»(paContainer, &scmFBInterfaceSpec, paInstanceNameId)«// no newline
			»«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateVariableInitializer»«generateConnectionInitializer» {
		}
		
		«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		«generateInterfaceDefinitions»
		«generateExecuteEvent»
		
		«generateBody»
	'''

	def protected CharSequence generateExecuteEvent() '''
		void «FBClassName»::executeEvent(const TEventID paEIID, CEventChainExecutionThread *const paECET) {
		  «generateBodyCall»
		  «FOR event : type.interfaceList.eventOutputs»
		  	«event.generateSendEvent»
		  «ENDFOR»
		}
	'''

	def protected generateBodyCall() //
	'''«IF bodyReturnVariable !== null»«bodyReturnVariable.generateName» = «ENDIF»func_«type.name»(«generateBodyCallArguments»);'''

	def protected CharSequence generateBodyCallArguments() //
	'''«FOR variable : bodyCallArguments SEPARATOR ", "»«variable.generateName»«ENDFOR»'''

	def protected getBodyCallArguments() {
		(type.interfaceList.inputVars + type.interfaceList.outputVars).filter[!name.nullOrEmpty]
	}

	def protected getBodyReturnVariable() {
		type.interfaceList.outputVars.findFirst[name.nullOrEmpty]
	}

	def protected generateBody() {
		bodyLanguageSupport.generate(emptyMap)
	}

	def protected generateSendEvent(Event event) {
		'''sendOutputEvent(scmEvent«event.name»ID, paECET);'''
	}

	override protected generateImplIncludes() '''
		«super.generateImplIncludes»
		«getDependencies(emptyMap).generateDependencyIncludes»
	'''

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
