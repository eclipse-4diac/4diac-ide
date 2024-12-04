/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH
 *                          Martin Erich Jobst
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.service

import java.nio.file.Path
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType

class ServiceInterfaceFBImplTemplate extends ForteFBTemplate<ServiceInterfaceFBType> {

	new(ServiceInterfaceFBType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, "CFunctionBlock", options)
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		«generateFBInterfaceDefinition»
		«generateFBInterfaceSpecDefinition»
		
		«FBClassName»::«FBClassName»(const CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer) :
		    «baseClass»(paContainer, scmFBInterfaceSpec, paInstanceNameId)«//no newline
			»«(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateVariableInitializer»«// no newline
			»«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterInitializer»«generateConnectionInitializer» {
		};
		«generateInitializeDefinition»
		
		«(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		«generateExecuteEvent»
		
		«generateInterfaceDefinitions»
	'''

	def protected generateExecuteEvent() '''
		void «FBClassName»::executeEvent(const TEventID paEIID, CEventChainExecutionThread *const paECET) {
		  switch(paEIID) {
		    «FOR event : type.interfaceList.eventInputs»
		    	case scmEvent«event.name»ID:
		    	  #error add code for «event.name» event!
		    	  /*
		    	    do not forget to send output event, calling e.g.
		    	      sendOutputEvent(scmEventCNFID, paECET);
		    	  */
		    	  break;
		    «ENDFOR»
		  }
		}
	'''
}
