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

	var CharSequence myClassName = null;
	
	//this a lows to reuse accessor method generation form ForteFBTemplate
	override getClassName() { (myClassName === null) ? super.className : myClassName}  

	new(AdapterType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, "forte::CAdapter", options)
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateUseStringId»
		
		namespace {
		  «generateTypeHash»
		}
		
		«generateFBDefinition»
		
		«generateFBInterfaceDefinition»
		
		«generateFBInterfaceSpecDefinition»
		
		«FBClassName»::«FBClassName»(forte::core::CFBContainer &paContainer,
		                             const SFBInterfaceSpec &paInterfaceSpec,
		                             const CStringDictionary::TStringId paInstanceNameId,
		                             TForteUInt8 paParentAdapterlistID) :
		    CAdapter(paContainer, paInterfaceSpec, paInstanceNameId, paParentAdapterlistID)«// no newline
		    »«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateVariableInitializer» {
		}
		
		«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		
		«generatePlugImpl»
		
		«generateSocketImpl»
	'''

	override protected generateFBDefinition() '''
		DEFINE_ADAPTER_TYPE(«FBClassName», «type.generateTypeSpec», TypeHash)
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
	
	def generatePlugImpl() '''
		«generatePlugConstructorImpl»
		
		«generatePlugReadInputData»
		
		«generatePlugWriteOutputData»		
		«{ myClassName = plugClassName
		   null // do not add to output string	
		}»
		«type.interfaceList.outputVars.generateAccessorDefinition("getDI", false)»
		«type.interfaceList.inputVars.generateAccessorDefinition("getDO", false)»
		«type.interfaceList.eventInputs.generateConnectionAccessorsDefinition("getEOConUnchecked", "CEventConnection *")»
		«type.interfaceList.outputVars.generateConnectionAccessorsDefinition("getDIConUnchecked", "CDataConnection **")»
		«type.interfaceList.inputVars.generateConnectionAccessorsDefinition("getDOConUnchecked", "CDataConnection *")»
		«{myClassName = null
		   null // do not add to output string	
		}»
	'''
	
	def generateSocketImpl() '''
		«generateSocketConstructorImpl»
		
		«generateSocketReadInputData»
		
		«generateSocketWriteOutputData»
		«{myClassName = socketClassName
		   null // do not add to output string	
		}»
		«type.interfaceList.inputVars.generateAccessorDefinition("getDI", false)»
		«type.interfaceList.outputVars.generateAccessorDefinition("getDO", false)»
		«type.interfaceList.eventOutputs.generateConnectionAccessorsDefinition("getEOConUnchecked", "CEventConnection *")»
		«type.interfaceList.inputVars.generateConnectionAccessorsDefinition("getDIConUnchecked", "CDataConnection **")»
		«type.interfaceList.outputVars.generateConnectionAccessorsDefinition("getDOConUnchecked", "CDataConnection *")»
		«{myClassName = null
		   null // do not add to output string	
		}»
	'''
	
	def generatePlugConstructorImpl() '''
		«plugClassName»::«plugClassName»(CStringDictionary::TStringId paInstanceNameId,
		                                         forte::core::CFBContainer &paContainer,
		                                         TForteUInt8 paParentAdapterlistID) :
		    «FBClassName»(paContainer, «FBClassName»::scmFBInterfaceSpecPlug, paInstanceNameId, paParentAdapterlistID)«//no newline
		    »«type.interfaceList.eventInputs.generateEventConnectionInitializer»«//no newline
		    »«type.interfaceList.outputVars.generateDataConnectionPointerInitializer»«//no newline
		    »«type.interfaceList.inputVars.generateDataConnectionInitializer» {
		}
	'''
	
	def generateSocketConstructorImpl() '''
		«socketClassName»::«socketClassName»(CStringDictionary::TStringId paInstanceNameId,
		                                         forte::core::CFBContainer &paContainer,
		                                         TForteUInt8 paParentAdapterlistID) :
		    «FBClassName»(paContainer, «FBClassName»::scmFBInterfaceSpecSocket, paInstanceNameId, paParentAdapterlistID)«//no newline
		    »«type.interfaceList.eventOutputs.generateEventConnectionInitializer»«//no newline
		    »«type.interfaceList.inputVars.generateDataConnectionPointerInitializer»«//no newline
		    »«type.interfaceList.outputVars.generateDataConnectionInitializer» {
		}
	'''
	
	def generatePlugReadInputData()  '''
		void «plugClassName»::readInputData(«IF type.interfaceList.eventOutputs.exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventOutputs.generateReadInputDataBody(socketClassName)»
		}
	'''
	
	def generateSocketReadInputData()  '''
		void «socketClassName»::readInputData(«IF type.interfaceList.eventInputs.exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventInputs.generateReadInputDataBody(plugClassName)»
		}
	'''
	
	def generateReadInputDataBody(List<Event> events, CharSequence peerName) '''
	«IF events.exists[!with.empty]»
		switch(paEIID) {
		  «FOR event : events.filter[!with.empty]»
		  	case «event.generateEventID»: {
		  	  «FOR variable : event.with.map[withVariable]»
		  	  	«variable.generateReadInputDataVariable»
		  	  «ENDFOR»
		  	  if(auto peer = static_cast<«peerName» *>(getPeer()); peer) {
		  	    «FOR variable : event.with.map[withVariable]»
		  	       peer->«variable.generateName» = «variable.generateName»;
		  	    «ENDFOR»
		  	  }
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
	
	def generatePlugWriteOutputData()  '''
		void «plugClassName»::writeOutputData(«IF type.interfaceList.eventInputs.exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventInputs.generateWriteOutputDataBody»
		}
	'''

	def generateSocketWriteOutputData()  '''
		void «socketClassName»::writeOutputData(«IF type.interfaceList.eventOutputs.exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventOutputs.generateWriteOutputDataBody»
		}
	'''

	def getPlugClassName() '''«FBClassName»_Plug'''

	def getSocketClassName() '''«FBClassName»_Socket'''
}
