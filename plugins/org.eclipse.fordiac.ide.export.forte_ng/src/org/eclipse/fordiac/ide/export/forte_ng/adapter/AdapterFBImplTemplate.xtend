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
		
		  «generateFBInterfaceDefinition»
		
		  «generateFBInterfaceSpecDefinition»
		}
		
		«generateFBDefinition»
		
		
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
		const SFBInterfaceSpec cFBInterfaceSpecSocket = {
		    .mEINames = «IF type.interfaceList.eventInputs.empty»{}«ELSE»cEventInputNames«ENDIF»,
		    .mEITypeNames = «IF type.interfaceList.eventInputs.empty || type.interfaceList.eventInputs.containsOnlyBasicEventType»{}«ELSE»cEventInputTypeIds«ENDIF»,
		    .mEONames = «IF type.interfaceList.eventOutputs.empty»{}«ELSE»cEventOutputNames«ENDIF»,
		    .mEOTypeNames = «IF type.interfaceList.eventOutputs.empty || type.interfaceList.eventOutputs.containsOnlyBasicEventType»{}«ELSE»cEventOutputTypeIds«ENDIF»,
		    .mDINames = «IF type.interfaceList.inputVars.empty»{}«ELSE»cDataInputNames«ENDIF»,
		    .mDONames = «IF type.interfaceList.outputVars.empty»{}«ELSE»cDataOutputNames«ENDIF»,
		    .mDIONames = «IF type.interfaceList.inOutVars.empty»{}«ELSE»cDataInOutNames«ENDIF»,
		    .mSocketNames = «IF type.interfaceList.sockets.empty»{}«ELSE»cSocketNameIds«ENDIF»,
		    .mPlugNames = «IF type.interfaceList.plugs.empty»{}«ELSE»cPlugNameIds«ENDIF»,
		};
	'''

	def generateFBInterfaceSpecPlug() '''
		const SFBInterfaceSpec cFBInterfaceSpecPlug = {
		    .mEINames = «IF type.interfaceList.eventOutputs.empty»{}«ELSE»cEventOutputNames«ENDIF»,
		    .mEITypeNames = «IF type.interfaceList.eventOutputs.empty || type.interfaceList.eventOutputs.containsOnlyBasicEventType»{}«ELSE»cEventOutputTypeIds«ENDIF»,
		    .mEONames = «IF type.interfaceList.eventInputs.empty»{}«ELSE»cEventInputNames«ENDIF»,
		    .mEOTypeNames = «IF type.interfaceList.eventInputs.empty || type.interfaceList.eventInputs.containsOnlyBasicEventType»{}«ELSE»cEventInputTypeIds«ENDIF»,
		    .mDINames = «IF type.interfaceList.outputVars.empty»{}«ELSE»cDataOutputNames«ENDIF»,
		    .mDONames = «IF type.interfaceList.inputVars.empty»{}«ELSE»cDataInputNames«ENDIF»,
		    .mDIONames = «IF type.interfaceList.inOutVars.empty»{}«ELSE»cDataInOutNames«ENDIF»,
		    .mSocketNames = «IF type.interfaceList.sockets.empty»{}«ELSE»cSocketNameIds«ENDIF»,
		    .mPlugNames = «IF type.interfaceList.plugs.empty»{}«ELSE»cPlugNameIds«ENDIF»,
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
		    «FBClassName»(paContainer, cFBInterfaceSpecPlug, paInstanceNameId, paParentAdapterlistID)«//no newline
		    »«type.interfaceList.eventInputs.generateEventConnectionInitializer»«//no newline
		    »«type.interfaceList.outputVars.generateDataConnectionPointerInitializer»«//no newline
		    »«type.interfaceList.inputVars.generateDataConnectionInitializer» {
		}
	'''
	
	def generateSocketConstructorImpl() '''
		«socketClassName»::«socketClassName»(CStringDictionary::TStringId paInstanceNameId,
		                                         forte::core::CFBContainer &paContainer,
		                                         TForteUInt8 paParentAdapterlistID) :
		    «FBClassName»(paContainer, cFBInterfaceSpecSocket, paInstanceNameId, paParentAdapterlistID)«//no newline
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
