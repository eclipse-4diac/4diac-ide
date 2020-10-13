/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
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
 *   Alois Zoitl
 *     - Add internal var generation
 *   Ernst Blecha
 *     - Add array-like bitaccess
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm

class SimpleFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) SimpleFBType type
	extension STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter

	new(SimpleFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
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
		«IF !(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).empty»
        «generateInitialValueAssignmentDefinition((type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars))»
        «ENDIF»
		«generateAlgorithms»

	'''

	def protected generateAlgorithms() '''
		«type.algorithm.generateAlgorithm»
	'''

	def protected dispatch generateAlgorithm(Algorithm alg) {
		errors.add('''Cannot export algorithm «alg.class»''')
		return ""
	}

	def protected dispatch generateAlgorithm(STAlgorithm alg) '''
		void «FBClassName»::alg_«alg.name»(void) {
		  «alg.generate(errors)»
		}
	'''

	def protected dispatch generateAlgorithm(OtherAlgorithm alg) '''
		void «FBClassName»::alg_«alg.name»(void) {
		  #pragma GCC warning "Algorithm of type: '«alg.language»' may lead to unexpected results!"
		  #pragma message ("warning Algorithm of type: '«alg.language»' may lead to unexpected results!")
		  «alg.text»
		}
	'''
}
