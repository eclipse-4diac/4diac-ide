/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2023 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType

class ServiceInterfaceFBImplTemplate extends ForteFBTemplate<ServiceInterfaceFBType> {

	new(ServiceInterfaceFBType type, String name, Path prefix) {
		super(type, name, prefix, "CFunctionBlock")
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		«generateFBInterfaceDefinition»
		«generateFBInterfaceSpecDefinition»
		
		«FBClassName»::«FBClassName»(const CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
		    «baseClass»( pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId)«//no newline
			»«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateVariableInitializer»«generateConnectionInitializer» {
		};
		
		«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		«generateExecuteEvent»
		
		«generateInterfaceDefinitions»
	'''

	def protected generateExecuteEvent() '''
		void «FBClassName»::executeEvent(TEventID paEIID, CEventChainExecutionThread * paECET) {
		  switch(paEIID) {
		    «FOR event : type.interfaceList.eventInputs»
		    	case scm_nEvent«event.name»ID:
		    	  #error add code for «event.name» event!
		    	  /*
		    	    do not forget to send output event, calling e.g.
		    	      sendOutputEvent(scm_nEventCNFID);
		    	  */
		    	  break;
		    «ENDFOR»
		  }
		}
	'''
}
