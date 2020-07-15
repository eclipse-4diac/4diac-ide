/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.xtend.lib.annotations.Accessors

class SimpleFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) SimpleFBType type

	new(SimpleFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»

		«generateIncludeGuardStart»

		«generateHeaderIncludes»

		class «FBClassName»: public CSimpleFB {
		  «generateFBDeclaration»

		private:
		  «generateFBInterfaceDeclaration»

		  «generateFBInterfaceSpecDeclaration»

        «IF !type.internalVars.isEmpty»
          «generateInternalVarDelcaration(type)»

        «ENDIF»
          «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «type.internalVars.generateAccessors("getVarInternal")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»

		  «generateAlgorithms»

		  FORTE_FB_DATA_ARRAY(«type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);

		public:
		  «FBClassName»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) : 
		       CSimpleFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, «IF !type.internalVars.empty»&scm_stInternalVars«ELSE»nullptr«ENDIF», m_anFBConnData, m_anFBVarsData) {
		  };

		  virtual ~«FBClassName»() = default;
		};

		«generateIncludeGuardEnd»

	'''

	override protected generateHeaderIncludes() '''
		#include "simplefb.h"
		«(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).generateTypeIncludes»
		«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterIncludes»
		
		«type.compilerInfo?.header»
	'''

	def protected generateAlgorithms() '''
		void alg_REQ(void);
	'''
}
