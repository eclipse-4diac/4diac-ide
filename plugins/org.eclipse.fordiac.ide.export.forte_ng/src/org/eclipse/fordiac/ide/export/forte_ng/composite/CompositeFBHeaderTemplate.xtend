/*******************************************************************************
 * Copyright (c) 2019, 2023 fortiss GmbH, Johannes Kepler University
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
 *   Alois Zoitl - Fix connections and parameter generation
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
		    «generateReadInternal2InterfaceOutputDataDeclaration»
		    «(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		
		  public:
		    «FBClassName»(CStringDictionary::TStringId paInstanceNameId, CResource *paSrcRes);
		
		    «generateInterfaceDeclarations»
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected CharSequence generateHeaderIncludes() '''
		#include "cfb.h"
		#include "typelib.h"
		«super.generateHeaderIncludes»
	'''
	
	def protected generateReadInternal2InterfaceOutputDataDeclaration() '''
		void readInternal2InterfaceOutputData(TEventID paEOID) override;
	'''

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterFBType)]»
			static const SCFB_FBInstanceData scmInternalFBs[];
		«ENDIF»
		static const SCFB_FBParameter scmParamters[];
		«IF !type.FBNetwork.eventConnections.empty»
			static const SCFB_FBConnectionData scmEventConnections[];
			static const SCFB_FBFannedOutConnectionData scmFannedOutEventConnections[];
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			static const SCFB_FBConnectionData scmDataConnections[];
			static const SCFB_FBFannedOutConnectionData scmFannedOutDataConnections[];
		«ENDIF»
		«IF !type.FBNetwork.adapterConnections.empty»
			static const SCFB_FBConnectionData scmAdapterConnections[];
		«ENDIF»
		static const SCFB_FBNData scmFBNData;
	'''
}
