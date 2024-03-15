/*******************************************************************************
 * Copyright (c) 2015, 2020 fortiss GmbH
 * 				 2019 Jan Holzweber
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
 *   Jan Holzweber  - fixed adapter socket variable bug
 *   Kirill Dorofeev - extended support for adapters DI/DOs used in BFB
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.filter

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECC
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.InterfaceList
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.With

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaUtils.*

class LuaConstants {

	static final int FB_STATE = 0;
	// 8 bit (32-25) to identify type (input/output/adapter/internal)
	// 8 bit (24-17) for adapter indexes, 
	// 16 bit (16-1) for input/output indexes
	static final int FB_DI_FLAG = 33554432; // 2^25
	static final int FB_DO_FLAG = 67108864; // 2^26
	static final int FB_AD_FLAG = 134217728; // 2^27
	static final int FB_ADI_FLAG = 167772160; // 2^27 | 2^25
	static final int FB_ADO_FLAG = 201326592; // 2^27 | 2^26
	static final int FB_IN_FLAG = 268435456; // 2^28
	
	static final int WITH_DELIMITER = 65535;

	def static luaTypeName(FBType type) '''FORTE_«type.name»'''

	def static luaStateVariable() '''STATE'''

	def static luaFBStateVarName() '''FB_STATE'''

	def static luaStateName(ECState state) '''ECC_«state.name»'''

	def static luaInputEventName(
		Event event) '''«IF event.FBNetworkElement instanceof AdapterFB»AEI_«event.FBNetworkElement.name»_«event.name»«ELSE»EI_«event.name»«ENDIF»'''

	def static luaOutputEventName(
		Event event) '''«IF event.FBNetworkElement instanceof AdapterFB»AEO_«event.FBNetworkElement.name»_«event.name»«ELSE»EO_«event.name»«ENDIF»'''

	def static luaAdapterInputEventName(Event event, String adapterName) '''AEI_«adapterName»_«event.name»'''

	def static luaAdapterOutputEventName(Event event, String adapterName) '''AEO_«adapterName»_«event.name»'''

	def static luaFBInputVarName(VarDeclaration decl) '''DI_«decl.name»'''

	def static luaFBOutputVarName(VarDeclaration decl) '''DO_«decl.name»'''

	def static luaFBAdapterInputVarName(VarDeclaration decl, String adapterName) '''ADI_«adapterName»_«decl.name»'''

	def static luaFBAdapterOutputVarName(VarDeclaration decl, String adapterName) '''ADO_«adapterName»_«decl.name»'''

	def static luaFBInternalVarName(VarDeclaration decl) '''IN_«decl.name»'''

	def static luaVariable(VarDeclaration decl) '''VAR_«decl.name»'''

	def static luaAdapterVariable(String name, String adapterInstanceName) '''VAR_«adapterInstanceName»_«name»'''

	def static luaAlgorithmName(Algorithm alg) '''alg_«alg.name»'''

	def static luaFBStateConstant() '''local «luaFBStateVarName» = «FB_STATE»'''

	def static luaStateConstants(ECC ecc) '''
		«FOR state : ecc.ECState»
			local «state.luaStateName» = «ecc.ECState.indexOf(state)»
		«ENDFOR»
	'''

	def static luaEventConstants(InterfaceList ifl) '''
		«FOR event : ifl.eventInputs»
			local «event.luaInputEventName» = «ifl.eventInputs.indexOf(event)»
		«ENDFOR»
		«FOR event : ifl.eventOutputs»
			local «event.luaOutputEventName» = «ifl.eventOutputs.indexOf(event)»
		«ENDFOR»
	'''

	def static luaFBVariableConstants(InterfaceList ifl) '''
		«FOR decl : ifl.inputVars»
			local «decl.luaFBInputVarName» = «FB_DI_FLAG.bitwiseOr(ifl.inputVars.indexOf(decl))»
		«ENDFOR»
		«FOR decl : ifl.outputVars»
			local «decl.luaFBOutputVarName» = «FB_DO_FLAG.bitwiseOr(ifl.outputVars.indexOf(decl))»
		«ENDFOR»
	'''

	def static luaFBAdapterConstants(InterfaceList ifl) '''
		«FOR socket : ifl.sockets»
			«socket.luaFBAdapterInterfaceConstants(ifl.sockets,ifl.plugs.size)»
		«ENDFOR»
		«FOR plug : ifl.plugs»
			«plug.luaFBAdapterInterfaceConstants(ifl.plugs,0)»
		«ENDFOR»
	'''

	def static luaFBAdapterInterfaceConstants(AdapterDeclaration adapter, EList<?> ifl, int offset) '''
		«var aifl = getAdapterInterfaceList(adapter)»		
		«var adapterID = ifl.indexOf(adapter)+offset»
		«FOR decl : aifl.eventOutputs»
			local «decl.luaAdapterOutputEventName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(adapterID << 16).bitwiseOr(aifl.eventOutputs.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.eventInputs»
			local «decl.luaAdapterInputEventName(adapter.name)» = «FB_AD_FLAG.bitwiseOr(adapterID << 16).bitwiseOr(aifl.eventInputs.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.outputVars»
			local «decl.luaFBAdapterOutputVarName(adapter.name)» = «FB_ADI_FLAG.bitwiseOr(adapterID << 16).bitwiseOr(aifl.outputVars.indexOf(decl))»
		«ENDFOR»
		«FOR decl : aifl.inputVars»
			local «decl.luaFBAdapterInputVarName(adapter.name)» = «FB_ADO_FLAG.bitwiseOr(adapterID << 16).bitwiseOr(aifl.inputVars.indexOf(decl))»
		«ENDFOR»    
	'''

	def static getAdapterInterfaceList(AdapterDeclaration adapter) {
		if (adapter.isIsInput)
			return adapter.getType.plugType.interfaceList;
		return adapter.getType.interfaceList;
	}

	def static luaInternalConstants(BasicFBType type) '''
		«FOR decl : type.internalVars»
			local «decl.luaFBInternalVarName» = «FB_IN_FLAG.bitwiseOr(type.internalVars.indexOf(decl))»
		«ENDFOR»
	'''

	def static luaConstants(BasicFBType type) '''
		«luaFBStateConstant»
		«type.ECC.luaStateConstants»
		«type.interfaceList.luaEventConstants»
		«type.interfaceList.luaFBVariableConstants»
		«type.interfaceList.luaFBAdapterConstants»
		«type.luaInternalConstants»
	'''

	def static luaFBStateVariable() '''fb[«luaFBStateVarName»]'''

	def static luaFBVariable(VarDeclaration decl) {
		val type = decl.rootContainer as FBType 
		if (type.interfaceList.inputVars.contains(decl)) {
			'''fb[«decl.luaFBInputVarName»]'''
		} else if (type.interfaceList.outputVars.contains(decl)) {
			'''fb[«decl.luaFBOutputVarName»]'''
		} else if (type instanceof BasicFBType && (type as BasicFBType).internalVars.contains(decl)) {
			'''fb[«decl.luaFBInternalVarName»]'''
		} else {
			throw new IllegalArgumentException('''Unknown kind of variable «decl.name»''')
		}
	}

	def static luaFBVariablesPrefix(Iterable<VarDeclaration> variables) '''
		«FOR variable : variables»
			local «variable.luaVariable» = «variable.luaFBVariable»
		«ENDFOR»
	'''

	def static luaFBAdapterInECCVariablesPrefix(VarDeclaration adapterVariable, String adapterName, boolean isPlug) '''
		«IF isPlug»
			local «adapterVariable.name.luaAdapterVariable(adapterName)» = fb[«adapterVariable.isInput ? adapterVariable.luaFBAdapterInputVarName(adapterName) : adapterVariable.luaFBAdapterOutputVarName(adapterName)»]
		«ELSE»
			local «adapterVariable.name.luaAdapterVariable(adapterName)» = fb[«adapterVariable.isInput ? adapterVariable.luaFBAdapterOutputVarName(adapterName) : adapterVariable.luaFBAdapterInputVarName(adapterName)»]
		«ENDIF»
	'''

	def static luaFBVariablesSuffix(Iterable<VarDeclaration> variables) '''
		«FOR variable : variables.filter[!it.isIsInput]»
			«variable.luaFBVariable» = «variable.luaVariable»
		«ENDFOR»
	'''

	def static luaSendOutputEvent(Event event) '''fb(«event.luaOutputEventName»)'''

	def static luaSendAdapterOutputEvent(Event event) '''fb(AEO_«event.name.replace('.','_')»)'''

	def static getEventWith(Event event, List<Integer> with, List<VarDeclaration> vars) {
		if (event.with.empty) {
			return -1
		}
		val index = with.size
		for (With w : event.with) {
			with.add(vars.indexOf(w.variables))
		}
		with.add(WITH_DELIMITER)
		return index
	}

	def static getTypeList(List<VarDeclaration> vars) {
		val typeList = new ArrayList<Object>(vars.size)
		vars.forEach [
			if (it.isArray) {
				typeList.add("ARRAY")
				typeList.add(it.arraySize)
			}
			typeList.add(it.typeName)
		]
		return typeList
	}

	def static luaInterfaceSpec(InterfaceList ifl) {
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
		  DODataTypeNames = «ifl.outputVars.typeList.luaValueList»,
		  numAdapters = «ifl.plugs.size + ifl.sockets.size»,
		  adapterInstanceDefinition = {
		    «ifl.plugs.map['''{adapterNameID = "«it.name»", adapterTypeNameID = "«it.typeName»", isPlug = true}'''].join(",\n")»«IF !ifl.sockets.isEmpty && !ifl.plugs.isEmpty»,«ENDIF»
		    «ifl.sockets.map['''{adapterNameID = "«it.name»", adapterTypeNameID = "«it.typeName»", isPlug = false}'''].join(",\n")»
		  }
		}'''
	}

	def static luaInternalVarsInformation(BasicFBType type) '''
	local internalVarsInformation = {
	  numIntVars = «type.internalVars.size»,
	  intVarsNames = «type.internalVars.map[it.name].luaStringList»,
	  intVarsDataTypeNames = «type.internalVars.typeList.luaValueList»
	}'''

}
