/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH
 *                          Johannes Kepler University
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
 *     - add readInputData and writeOutputData
 *   Alois Zoitl
 *     - Fix issues in adapter code generation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.adapter

import java.nio.file.Path
import java.util.List
import java.util.Map
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.Event

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class AdapterFBImplTemplate extends ForteFBTemplate<AdapterType> {

	new(AdapterType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, "CAdapter", options)
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		
		«generateFBInterfaceDefinition»
		
		«generateFBInterfaceSpecDefinition»
		
		«generateReadInputDataDefinition»
		
		«generateWriteOutputDataDefinition»
	'''

	override protected generateFBDefinition() '''
		DEFINE_ADAPTER_TYPE(«FBClassName», «type.generateTypeSpec»)
	'''

	def generateFBInterfaceSpecSocket() '''
		const SFBInterfaceSpec «FBClassName»::scmFBInterfaceSpecSocket = {
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr, nullptr«ELSE»scmEventInputNames, «IF type.interfaceList.eventInputs.containsOnlyBasicEventType»nullptr«ELSE»scmEventInputTypeIds«ENDIF», «IF hasInputWith»scmEIWith«ELSE»nullptr«ENDIF», scmEIWithIndexes«ENDIF»,
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr, nullptr«ELSE»scmEventOutputNames, «IF type.interfaceList.eventOutputs.containsOnlyBasicEventType»nullptr«ELSE»scmEventOutputTypeIds«ENDIF», «IF hasOutputWith»scmEOWith«ELSE»nullptr«ENDIF», scmEOWithIndexes«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scmDataInputNames, scmDataInputTypeIds«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scmDataOutputNames, scmDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.inOutVars.size», «IF type.interfaceList.inOutVars.empty»nullptr«ELSE»scmDataInOutNames«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», «IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»scmAdapterInstances«ELSE»nullptr«ENDIF»
		};
	'''

	def generateFBInterfaceSpecPlug() '''
		const SFBInterfaceSpec «FBClassName»::scmFBInterfaceSpecPlug = {
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr, nullptr«ELSE»scmEventOutputNames, «IF type.interfaceList.eventOutputs.containsOnlyBasicEventType»nullptr«ELSE»scmEventOutputTypeIds«ENDIF», «IF hasOutputWith»scmEOWith«ELSE»nullptr«ENDIF», scmEOWithIndexes«ENDIF»,
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr, nullptr«ELSE»scmEventInputNames, «IF type.interfaceList.eventInputs.containsOnlyBasicEventType»nullptr«ELSE»scmEventInputTypeIds«ENDIF», «IF hasInputWith»scmEIWith«ELSE»nullptr«ENDIF», scmEIWithIndexes«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scmDataOutputNames, scmDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scmDataInputNames, scmDataInputTypeIds«ENDIF»,
		  «type.interfaceList.inOutVars.size», «IF type.interfaceList.inOutVars.empty»nullptr«ELSE»scmDataInOutNames«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», «IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»scmAdapterInstances«ELSE»nullptr«ENDIF»
		};
	'''

	override protected generateFBInterfaceSpecDefinition() '''
		«generateFBInterfaceSpecSocket»
		
		«generateFBInterfaceSpecPlug»
	'''

	override protected generateReadInputDataDefinition() '''
		void «FBClassName»::readInputData(«IF (type.interfaceList.eventInputs + type.interfaceList.eventOutputs).exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  if(isSocket()) {
		    «type.interfaceList.eventInputs.generateReadInputDataBody»
		  } else {
		    «type.interfaceList.eventOutputs.generateReadInputDataBody»
		  }
		}
	'''

	override protected generateReadInputDataBody(List<Event> events) '''
		«IF events.exists[!with.empty]»
			switch(paEIID) {
			  «FOR event : events.filter[!with.empty]»
			  	case «event.generateEventID»: {
			  	  «FOR variable : event.with.map[withVariable]»
			  	  	«val index = variable.interfaceElementIndex»readData(«index», *mDIs[«index»], mDIConns[«index»]);
			  	  «ENDFOR»
			  	  break;
			  	}
			  «ENDFOR»
			  default:
			    break;
			}
		«ELSE»
			// nothing to do
		«ENDIF»
	'''

	override protected generateWriteOutputDataDefinition() '''
		void «FBClassName»::writeOutputData(«IF (type.interfaceList.eventInputs + type.interfaceList.eventOutputs).exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  if(isSocket()) {
		    «type.interfaceList.eventOutputs.generateWriteOutputDataBody»
		  } else {
		    «type.interfaceList.eventInputs.generateWriteOutputDataBody»
		  }
		}
	'''

	override protected generateWriteOutputDataBody(List<Event> events) '''
		«IF events.exists[!with.empty]»
			switch(paEIID) {
			  «FOR event : events.filter[!with.empty]»
			  	case «event.generateEventID»: {
			  	  «FOR variable : event.with.map[withVariable]»
			  	  	«val index = variable.interfaceElementIndex»writeData(«index», *mDOs[«index»], mDOConns[«index»]);
			  	  «ENDFOR»
			  	  break;
			  	}
			  «ENDFOR»
			  default:
			    break;
			}
		«ELSE»
			// nothing to do
		«ENDIF»
	'''

}
