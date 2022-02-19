/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *     - adopt algorithm support
 *   Alois Zoitl
 *     - Add internal var generation
 *   Ernst Blecha
 *     - Add array-like bitaccess
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.xtend.lib.annotations.Accessors

class SimpleFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) final SimpleFBType type
	final ILanguageSupport languageSupport

	new(SimpleFBType type, String name, Path prefix) {
		super(name, prefix, "CSimpleFB")
		this.type = type
		this.languageSupport = ILanguageSupportFactory.createLanguageSupport("forte_ng", type.algorithm)
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
		
	'''

	def protected generateAlgorithms() '''
		«type.algorithm.generateAlgorithm»
	'''

	def protected generateAlgorithm(Algorithm alg) '''
		void «FBClassName»::alg_«alg.name»(void) {
		  «languageSupport?.generate ?: ""»
		}
	'''

	override protected generateImplIncludes() '''
		«super.generateImplIncludes»
		«IF languageSupport !== null»«languageSupport.dependencies.filter(DataType).generateImplTypeIncludes»«ENDIF»
	'''

	override getErrors() {
		if(languageSupport !== null) (super.errors + languageSupport.errors).toList else super.errors
	}

	override getWarnings() {
		if(languageSupport !== null) (super.warnings + languageSupport.warnings).toList else super.warnings
	}

	override getInfos() {
		if(languageSupport !== null) (super.infos + languageSupport.infos).toList else super.infos
	}
}
