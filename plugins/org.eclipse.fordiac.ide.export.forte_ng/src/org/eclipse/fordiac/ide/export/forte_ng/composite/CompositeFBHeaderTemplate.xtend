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

class CompositeFBHeaderTemplate extends ForteFBTemplate<CompositeFBType> {

	new(CompositeFBType type, String name, Path prefix) {
		super(type, name, prefix, "CCompositeFB")
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
		
		  «generateFBNetwork»
		
		  «generateReadInputDataDeclaration»
		  «generateWriteOutputDataDeclaration»
		  «(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		
		public:
		  «FBClassName»(const CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes);
		
		  «generateInterfaceDeclarations»
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
