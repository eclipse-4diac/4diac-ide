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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class AdapterFBHeaderTemplate extends ForteFBTemplate<AdapterType> {

	new(AdapterType type, String name, Path prefix, Map<?,?> options) {
		super(type, name, prefix, "CAdapter", options)
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
		
		    «generateReadInputDataDeclaration»
		    «generateWriteOutputDataDeclaration»
		  public:
		    «type.interfaceList.inputVars.generateAccessors("getDI", "getDO")»
		    «type.interfaceList.outputVars.generateAccessors("getDO", "getDI")»
		    «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		    «type.interfaceList.eventInputs.generateEventAccessors»
		    «type.interfaceList.eventOutputs.generateEventAccessors»
		    «FBClassName»(CStringDictionary::TStringId paAdapterInstanceName, forte::core::CFBContainer &paContainer, bool paIsPlug) :
		        «baseClass»(paContainer, scmFBInterfaceSpecSocket, paAdapterInstanceName, scmFBInterfaceSpecPlug, paIsPlug) {	
		    };
		
		    virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		«generateDependencyInclude("adapter.h")»
		«generateDependencyInclude("typelib.h")»
		«super.generateHeaderIncludes»
	'''

	override protected generateFBDeclaration() '''
		DECLARE_ADAPTER_TYPE(«FBClassName»)
	'''

	override protected generateFBInterfaceSpecDeclaration() '''
		static const SFBInterfaceSpec scmFBInterfaceSpecSocket;
		
		static const SFBInterfaceSpec scmFBInterfaceSpecPlug;
	'''

	override protected generateEventConstants(List<Event> events) '''
	public:
	  «super.generateEventConstants(events)»
	
	private:
	'''

	def protected generateAccessors(List<VarDeclaration> vars, String socketFunction, String plugFunction) '''
		«FOR v : vars»
			«v.generateVariableTypeName» &«v.generateName» {
			  return *static_cast<«v.generateVariableTypeName»*>((isSocket()) ? «socketFunction»(«vars.indexOf(v)») : «plugFunction»(«vars.indexOf(v)»));
			}
			
		«ENDFOR»
	'''

	def protected generateEventAccessors(List<Event> events) '''
		«FOR event : events»
			TEventID «event.generateName»() {
			  return mParentAdapterListEventID + scmEvent«event.name»ID;
			}
			
		«ENDFOR»
	'''

	def protected generateAccessors(List<AdapterDeclaration> adapters) '''
		«FOR adapter : adapters»
			«adapter.type.generateTypeName» &«adapter.generateName» {
			  return *static_cast<«adapter.type.generateTypeName»*>(mAdapters[«adapters.indexOf(adapter)»]);
			};
			
		«ENDFOR»
	'''
}
