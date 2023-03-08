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
 *     - Fix issues in adapter code generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.adapter

import java.nio.file.Path
import java.util.List
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

class AdapterFBHeaderTemplate extends ForteFBTemplate<AdapterFBType> {

	new(AdapterFBType type, String name, Path prefix) {
		super(type, name, prefix, "CAdapter")
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
		
		public:
		  «type.interfaceList.inputVars.generateAccessors("getDI", "getDO")»
		  «type.interfaceList.outputVars.generateAccessors("getDO", "getDI")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		
		  «type.interfaceList.eventInputs.generateEventAccessors»
		  «type.interfaceList.eventOutputs.generateEventAccessors»
		
		private:
		  FORTE_ADAPTER_DATA_ARRAY(«type.interfaceList.eventInputs.size», «type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);
		
		public:
		  «FBClassName»(CStringDictionary::TStringId pa_anAdapterInstanceName, CResource *pa_poSrcRes, bool pa_bIsPlug) :
		      «baseClass»(pa_poSrcRes, &scm_stFBInterfaceSpecSocket, pa_anAdapterInstanceName, &scm_stFBInterfaceSpecPlug, pa_bIsPlug, m_anFBConnData, m_anFBVarsData) {	
		   };
		
		  virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		#include "adapter.h"
		#include "typelib.h"
		«super.generateHeaderIncludes»
	'''

	override protected generateFBDeclaration() '''
		DECLARE_ADAPTER_TYPE(«FBClassName»)
	'''

	override protected generateFBInterfaceSpecDeclaration() '''
		static const SFBInterfaceSpec scm_stFBInterfaceSpecSocket;
		
		static const SFBInterfaceSpec scm_stFBInterfaceSpecPlug;
	'''

	override protected generateEventConstants(List<Event> events) '''
	public:
		«super.generateEventConstants(events)»
	
	private:
	'''

	def protected generateAccessors(List<VarDeclaration> vars, String socketFunction, String plugFunction) '''
		«FOR v : vars»
			CIEC_«v.typeName» «IF v.array»*«ELSE»&«ENDIF»«v.name»() {
			  «IF v.array»
			  	return static_cast<CIEC_«v.typeName»*>(static_cast<CIEC_ARRAY *>((isSocket()) ? «socketFunction»(«vars.indexOf(v)») : «plugFunction»(«vars.indexOf(v)»))[0]); //the first element marks the start of the array
			  «ELSE»
			  	return *static_cast<CIEC_«v.typeName»*>((isSocket()) ? «socketFunction»(«vars.indexOf(v)») : «plugFunction»(«vars.indexOf(v)»));
			  «ENDIF»
			}
			
		«ENDFOR»
	'''

	def protected generateEventAccessors(List<Event> events) '''
		«FOR event : events»
			int «event.name»() {
			  return m_nParentAdapterListEventID + scm_nEvent«event.name»ID;
			}
			
		«ENDFOR»
	'''
}
