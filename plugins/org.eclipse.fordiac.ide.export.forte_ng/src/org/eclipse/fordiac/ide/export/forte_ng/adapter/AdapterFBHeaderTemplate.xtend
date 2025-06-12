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

class AdapterFBHeaderTemplate extends ForteFBTemplate<AdapterType> {

	new(AdapterType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, "forte::CAdapter", options)
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		«generateFBClassHeader»
		    «generateFBDeclaration»
		
		  private:
		    «generateFBInterfaceDeclaration»
		
		    «(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDeclaration»
		  public:
		    «type.interfaceList.inputVars.generateVariableDeclarations(false)»
		    «type.interfaceList.outputVars.generateVariableDeclarations(false)»
		    «type.interfaceList.eventInputs.generateEventAccessors»
		    «type.interfaceList.eventOutputs.generateEventAccessors»
		    ~«FBClassName»() override = default;
		    
		  protected:
		    «FBClassName»(forte::core::CFBContainer &paContainer,
		                  const SFBInterfaceSpec &paInterfaceSpec,
		                  const CStringDictionary::TStringId paInstanceNameId,
		                  TForteUInt8 paParentAdapterlistID);
		};
		
		«generatePlugClass»
		
		«generateSocketClass»
		
		«generateIncludeGuardEnd»		
	'''

	override protected generateHeaderIncludes() '''
		«generateDependencyInclude("core/adapter.h")»
		«super.generateHeaderIncludes»
	'''

	override protected generateFBClassHeader() '''
		class «FBClassName» : public «baseClass» {
	'''

	override protected generateFBDeclaration() '''
		DECLARE_ADAPTER_TYPE(«FBClassName»)
	'''

	override protected generateEventConstants(List<Event> events) '''
	public:
	  «super.generateEventConstants(events)»
	
	private:
	'''

	def private generateEventAccessors(List<Event> events) '''
		«FOR event : events»
			TEventID «event.generateName»() {
			  return getParentAdapterListEventID() + scmEvent«event.name»ID;
			}
			
		«ENDFOR»
	'''
	
	def generatePlugClass() '''
		«generatePlugSocketClassStart("_Plug")»
		
		    «type.interfaceList.eventInputs.generateEventConnectionDeclarations»
		    «type.interfaceList.outputVars.generateDataConnectionDeclarations(true)»
		    «type.interfaceList.inputVars.generateDataConnectionDeclarations(false)»
		  private:
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		    «generateAccessorDeclarations()»
		};
	'''

	def generateSocketClass() '''
		«generatePlugSocketClassStart("_Socket")»
		
		    «type.interfaceList.eventOutputs.generateEventConnectionDeclarations»
		    «type.interfaceList.inputVars.generateDataConnectionDeclarations(true)»
		    «type.interfaceList.outputVars.generateDataConnectionDeclarations(false)»
		  private:
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		    «generateAccessorDeclarations()»
		};
	'''
	
	def generatePlugSocketClassStart(String kind) '''
	  class «FBClassName»«kind» final : public «FBClassName» {
	    public:
	      «FBClassName»«kind»(CStringDictionary::TStringId paInstanceNameId,
	                          forte::core::CFBContainer &paContainer,
	                          TForteUInt8 paParentAdapterlistID);
	      ~«FBClassName»«kind»() override = default;
	'''
	
}
