/*******************************************************************************
 * Copyright (c) 2017 fortiss GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Monika Wenger
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.filter

import java.util.ArrayList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
import org.eclipse.fordiac.ide.model.libraryElement.Event

import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants.*
import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils.*

class AdapterFilter {
	@Accessors(PUBLIC_GETTER)
	private List<String> errors = new ArrayList<String>;

	def lua(AdapterType type) '''
		«type.interfaceList.luaEventDataInterfaceSpec»
		
		return {interfaceSpec = interfaceSpec}
	'''
	
	def static luaEventDataInterfaceSpec(InterfaceList ifl) {
		val inputWith = new ArrayList<Integer>
		val inputWithIndexes = new ArrayList<Integer>
		for (Event e : ifl.eventInputs) {
			inputWithIndexes.add(e.getEventWith(inputWith, ifl.inputVars))
		}
		val outputWith = new ArrayList<Integer>
		val outputWithIndexes = new ArrayList<Integer>
		for (Event e : ifl.eventOutputs) {
			outputWithIndexes.add(e.getEventWith(outputWith, ifl.outputVars))
		}
		'''
		local interfaceSpec = {
		  numEIs = «ifl.eventInputs.size»,
		  EINames = «ifl.eventInputs.map[it.name].luaStringList»,
		  EIWith = «inputWith.luaIntegerList»,
		  EIWithIndexes = «inputWithIndexes.luaIntegerList»,
		  numEOs = «ifl.eventOutputs.size»,
		  EONames = «ifl.eventOutputs.map[it.name].luaStringList»,
		  EOWith = «outputWith.luaIntegerList»,
		  EOWithIndexes = «outputWithIndexes.luaIntegerList»,
		  numDIs = «ifl.inputVars.size»,
		  DINames = «ifl.inputVars.map[it.name].luaStringList»,
		  DIDataTypeNames = «ifl.inputVars.typeList.luaValueList»,
		  numDOs = «ifl.outputVars.size»,
		  DONames = «ifl.outputVars.map[it.name].luaStringList»,
		  DODataTypeNames = «ifl.outputVars.typeList.luaValueList»
		}'''
	}
}