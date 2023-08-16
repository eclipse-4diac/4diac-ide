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
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class AdapterFBImplTemplate extends ForteFBTemplate<AdapterFBType> {

	new(AdapterFBType type, String name, Path prefix) {
		super(type, name, prefix, "CAdapter")
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		
		«generateFBInterfaceDefinition»
		
		«generateFBInterfaceSpecDefinition»
		
	'''

	override protected generateFBDefinition() '''
		DEFINE_ADAPTER_TYPE(«FBClassName», «type.generateTypeSpec»)
	'''
	
	def generateFBInterfaceSpecSocket()
	'''
	const SFBInterfaceSpec «FBClassName»::scmFBInterfaceSpecSocket = {
	  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr«ELSE»scmEventInputNames, «IF hasInputWith»scmEIWith«ELSE»nullptr«ENDIF», scmEIWithIndexes«ENDIF»,
	  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr«ELSE»scmEventOutputNames, «IF hasOutputWith»scmEOWith«ELSE»nullptr«ENDIF», scmEOWithIndexes«ENDIF»,
	  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scmDataInputNames, scmDataInputTypeIds«ENDIF»,
	  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scmDataOutputNames, scmDataOutputTypeIds«ENDIF»,
	  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», nullptr
	};
	'''
	
	def generateFBInterfaceSpecPlug()
	'''
	const SFBInterfaceSpec «FBClassName»::scmFBInterfaceSpecPlug = {
	  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr«ELSE»scmEventOutputNames, «IF hasOutputWith»scmEOWith«ELSE»nullptr«ENDIF», scmEOWithIndexes«ENDIF»,
	  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr«ELSE»scmEventInputNames, «IF hasInputWith»scmEIWith«ELSE»nullptr«ENDIF», scmEIWithIndexes«ENDIF»,
	  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scmDataOutputNames, scmDataOutputTypeIds«ENDIF»,
	  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scmDataInputNames, scmDataInputTypeIds«ENDIF»,
	  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», nullptr
	};
	'''

	override protected generateFBInterfaceSpecDefinition()
	'''
	«generateFBInterfaceSpecSocket»

	«generateFBInterfaceSpecPlug»
	'''
}
