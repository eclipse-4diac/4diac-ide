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
 *     - Fix connections and parameter generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.composite

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.xtend.lib.annotations.Accessors

class CompositeFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) CompositeFBType type

	new(CompositeFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		class «FBClassName»: public CCompositeFB {
		  «generateFBDeclaration»
		
		private:
		  «generateFBInterfaceDeclaration»
		
		  «generateFBInterfaceSpecDeclaration»
		
		  «generateFBNetwork»
		
		  «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		
		  FORTE_FB_DATA_ARRAY(«type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);
		
		public:
		  COMPOSITE_FUNCTION_BLOCK_CTOR(«FBClassName») {};
		
		  virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected CharSequence generateHeaderIncludes() '''
		#include "cfb.h"
		#include "typelib.h"
		«super.generateHeaderIncludes»
	'''

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterFBType)]»
			static const SCFB_FBInstanceData scm_astInternalFBs[];
		«ENDIF»
		static const SCFB_FBParameter scm_astParamters[];
		«IF !type.FBNetwork.eventConnections.empty»
			static const SCFB_FBConnectionData scm_astEventConnections[];
			static const SCFB_FBFannedOutConnectionData scm_astFannedOutEventConnections[];
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			static const SCFB_FBConnectionData scm_astDataConnections[];
			static const SCFB_FBFannedOutConnectionData scm_astFannedOutDataConnections[];
		«ENDIF»
		static const SCFB_FBNData scm_stFBNData;
	'''
}
