/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH, Johannes Kepler University
 * 							Primetals Technologies Austria GmbH
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
 *   Martin Melik Merkumians - add code for export CFB internal VarInOut usage
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.composite

import java.nio.file.Path
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

class CompositeFBHeaderTemplate extends ForteFBTemplate<CompositeFBType> {

	final List<FB> fbs

	new(CompositeFBType type, String name, Path prefix) {
		super(type, name, prefix, "CCompositeFB")
		fbs = type.FBNetwork.networkElements.filter(FB).reject(AdapterFB).toList
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
		
		    «fbs.generateInternalFBDeclarations»
		
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		    «generateReadInternal2InterfaceOutputDataDeclaration»
		    «(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		    «generateSetFBNetworkInitialValuesDeclaration»
		
		  public:
		    «FBClassName»(CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer);
		    «generateInitializeDeclaration»
		
		    «generateInterfaceDeclarations»
		};
		
		«generateIncludeGuardEnd»
		
	'''
	
	def generateSetFBNetworkInitialValuesDeclaration() '''
		«IF fbs.flatMap[interface.inputVars].exists[!value?.value.nullOrEmpty]»
			void setFBNetworkInitialValues() override;
		«ENDIF»
	'''

	override protected CharSequence generateHeaderIncludes() '''
		«generateDependencyInclude("cfb.h")»
		«generateDependencyInclude("typelib.h")»
		«super.generateHeaderIncludes»
	'''

	def protected generateReadInternal2InterfaceOutputDataDeclaration() '''
		void readInternal2InterfaceOutputData(TEventID paEOID) override;
	'''

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterType)]»
			static const SCFB_FBInstanceData scmInternalFBs[];
		«ENDIF»
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

	override generateInterfaceVariableAndConnectionDeclarations() '''
		«super.generateInterfaceVariableAndConnectionDeclarations»
		«type.interfaceList.outMappedInOutVars.generateDataConnectionDeclarations(false, true)»
	'''

	override generateAccessorDeclarations() '''
		«super.generateAccessorDeclarations»
		«IF (!type.interfaceList.inOutVars.empty)»
			«generateConnectionAccessorsDeclaration("getDIOOutConInternalUnchecked", "CInOutDataConnection *")»
		«ENDIF»
	'''

	override Set<INamedElement> getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) + type.FBNetwork.networkElements.map[getType]).toSet
	}
}
