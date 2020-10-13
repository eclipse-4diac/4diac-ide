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
package org.eclipse.fordiac.ide.export.forte_ng.basic

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm

class BasicFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) BasicFBType type

	new(BasicFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»

		«generateIncludeGuardStart»

		«generateHeaderIncludes»

		class «FBClassName»: public CBasicFB {
		  «generateFBDeclaration»

		private:
		  «generateFBInterfaceDeclaration»

		  «generateFBInterfaceSpecDeclaration»

		«IF !type.internalVars.isEmpty»
		  «generateInternalVarDelcaration(type)»
        «ENDIF»
        «IF !(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).empty»
		  «generateInitialValueAssignmentDeclaration»
		«ENDIF»
		  «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «type.internalVars.generateAccessors("getVarInternal")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»

		  «generateAlgorithms»

		  «generateStates»

		  virtual void executeEvent(int pa_nEIID);

		  «type.generateBasicFBDataArray»

		public:
		  «FBClassName»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
		       CBasicFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, «IF !type.internalVars.empty»&scm_stInternalVars«ELSE»nullptr«ENDIF», m_anFBConnData, m_anFBVarsData) {
		  };

		  virtual ~«FBClassName»() = default;
		};

		«generateIncludeGuardEnd»

	'''

	override protected generateHeaderIncludes() '''
		#include "basicfb.h"
		«(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).generateTypeIncludes»
		«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterIncludes»

		«type.compilerInfo?.header»
	'''

	def protected generateAlgorithms() '''
		«FOR alg : type.algorithm»
			«alg.generateAlgorithm»
		«ENDFOR»
	'''

	def protected dispatch generateAlgorithm(Algorithm alg) {
		errors.add('''Cannot export algorithm «alg.class»''')
		return ""
	}

	def protected dispatch generateAlgorithm(OtherAlgorithm alg) '''
		void alg_«alg.name»(void);
	'''

	def protected dispatch generateAlgorithm(STAlgorithm alg) '''
		void alg_«alg.name»(void);
	'''

	def protected generateStates() '''
		«FOR state : type.ECC.ECState»
			static const TForteInt16 scm_nState«state.name» = «type.ECC.ECState.indexOf(state)»;
		«ENDFOR»

		«FOR state : type.ECC.ECState»
			«state.generateState»
		«ENDFOR»
	'''

	def protected CharSequence generateState(ECState state) '''
		void enterState«state.name»(void);
	'''
}
