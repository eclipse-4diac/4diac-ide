/*******************************************************************************
 * Copyright (c) 2015, 2024 fortiss GmbH
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
 *   Kirill Dorofeev - extended support for adapters used in BFB
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_lua.filter

import java.util.ArrayList
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECC
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants.*
import org.eclipse.fordiac.ide.model.libraryElement.STMethod
import org.eclipse.fordiac.ide.model.libraryElement.Method

class BasicFBFilter {
	Map<ECTransition, ILanguageSupport> transitionLanguageSupport;
	
	def private void setupLanguageSupport(BasicFBType type){	
		transitionLanguageSupport = type.ECC.ECTransition.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_lua", it)
		]
	}


	@Accessors(PUBLIC_GETTER)
	List<String> errors = new ArrayList<String>;

	def String lua(BasicFBType type) '''
		«type.setupLanguageSupport»
		local STfunc = require "STfunc"
		
		«type.luaConstants»
		
		«type.luaMethods»
		
		«type.luaAlgorithms»
		
		«type.ECC.luaStates»
		
		«type.ECC.luaECC(type.variables, type.adapterSocketsVariables, type.adapterPlugsVariables)»
		
		«type.interfaceList.luaInterfaceSpec»
		
		«type.luaInternalVarsInformation»
		
		return {ECC = executeEvent, interfaceSpec = interfaceSpec, internalVarsInformation = internalVarsInformation}
	'''

	def private luaECC(ECC ecc, Iterable<VarDeclaration> variables,
		Map<AdapterDeclaration, String> adapterSocketsVariables,
		Map<AdapterDeclaration, String> adapterPlugsVariables) '''
		local function transition(fb, id)
		  local «luaStateVariable()» = «luaFBStateVariable()»
		  «variables.luaFBVariablesPrefix»
		  «FOR adapter : adapterSocketsVariables.keySet»
		  	«FOR input: adapter.getType.interfaceList.inputVars» 
		  		«input.luaFBAdapterInECCVariablesPrefix(adapter.name, false)»
		  	«ENDFOR»
		  	«FOR output: adapter.getType.interfaceList.outputVars» 
		  		«output.luaFBAdapterInECCVariablesPrefix(adapter.name, false)»
		  	«ENDFOR»
		  «ENDFOR»
		  «FOR adapter : adapterPlugsVariables.keySet»
		  	«FOR input: adapter.getType.interfaceList.inputVars» 
		  		«input.luaFBAdapterInECCVariablesPrefix(adapter.name, true)»
		  	«ENDFOR»
		  	«FOR output: adapter.getType.interfaceList.outputVars» 
		  		«output.luaFBAdapterInECCVariablesPrefix(adapter.name, true)»
		  	«ENDFOR»
		  «ENDFOR»
		  «ecc.luaTransitions»
		end
		
		local function executeEvent(fb, id)
		  local modified = transition(fb, id)
		  while modified do
		    modified = transition(fb, -1)
		  end
		end
	'''

	def private Iterable<VarDeclaration> getVariables(BasicFBType type) {
		type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars
	}

	def private Map<AdapterDeclaration, String> getAdapterSocketsVariables(BasicFBType type) {
		var Map<AdapterDeclaration, String> ret = new HashMap<AdapterDeclaration, String>;
		for (adapterDecl : type.interfaceList.sockets) {
			ret.put(adapterDecl, adapterDecl.name)
		}
		return ret
	}

	def private Map<AdapterDeclaration, String> getAdapterPlugsVariables(BasicFBType type) {
		var Map<AdapterDeclaration, String> ret = new HashMap<AdapterDeclaration, String>;
		for (adapterDecl : type.interfaceList.plugs) {
			ret.put(adapterDecl, adapterDecl.name)
		}
		return ret
	}

	def private luaTransitions(ECC ecc) '''
	«FOR state : ecc.ECState BEFORE 'if ' SEPARATOR '\nelseif ' AFTER '\nelse return false\nend'»
		«state.luaStateName» == «luaStateVariable» then
		«state.luaTransition»«ENDFOR»'''

	def private luaTransition(ECState state) '''
	«FOR tran : state.outTransitions BEFORE 'if ' SEPARATOR '\nelseif ' AFTER '\nelse return false\nend'»
		«tran.luaTransitionCondition» then return enter«tran.destination.luaStateName»(fb)«ENDFOR»'''

	def private luaTransitionCondition(
		ECTransition tran) '''«IF tran.conditionEvent !== null»«tran.conditionEvent.luaInputEventName» == id«ELSE»true«ENDIF» and «IF !tran.conditionExpression.nullOrEmpty»«tran.luaTransitionConditionExpression»«ELSE»true«ENDIF»'''

	def private luaTransitionConditionExpression(ECTransition tran) {
		transitionLanguageSupport.get(tran)?.generate(emptyMap)
	}

	def private luaStates(ECC ecc) '''
		«FOR state : ecc.ECState»
			«state.luaState»
			
		«ENDFOR»
	'''

	def private luaState(ECState state) '''
		local function enter«state.luaStateName»(fb)
		  «luaFBStateVariable» = «state.luaStateName»
		  «FOR action : state.ECAction»
		  	«IF null !== action.algorithm»«action.algorithm.luaAlgorithmName»(fb)«ENDIF»
		  	«IF action.output.FBNetworkElement instanceof AdapterFB»
		  		«action.output?.luaSendAdapterOutputEvent»
		  	«ELSE»	
		  		«action.output?.luaSendOutputEvent»
		  	«ENDIF»
		  «ENDFOR»
		  return true
		end
	'''

	def private luaMethods(BasicFBType type) '''
		«FOR meth : type.methods»
			«meth.luaMethod»
			
		«ENDFOR»
	'''
	
	def private dispatch luaMethod(Method meth) {
		throw new UnsupportedOperationException("Cannot export algorithm " + meth.class)
	}
	
	def private dispatch luaMethod(STMethod meth) {
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", meth)
		val result = '''«lang.generate(Collections.emptyMap())»'''
		errors.addAll(lang.errors.map['''Error in algorithm «meth.name»: «it»'''])
		lang.errors.clear()
		return result
	}

	def private luaAlgorithms(BasicFBType type) '''
		«FOR alg : type.algorithm»
			«alg.luaAlgorithm»
			
		«ENDFOR»
	'''

	def private dispatch luaAlgorithm(Algorithm alg) {
		throw new UnsupportedOperationException("Cannot export algorithm " + alg.class)
	}

	def private dispatch luaAlgorithm(STAlgorithm alg) {
		/*val result = '''
			local function «alg.luaAlgorithmName»(fb)
			  «stAlgorithmFilter.lua(alg)»
			end
		'''
		errors.addAll(stAlgorithmFilter.errors.map['''Error in algorithm «alg.name»: «it»'''])
		stAlgorithmFilter.errors.clear()*/
		val lang = ILanguageSupportFactory.createLanguageSupport("forte_lua", alg)
		val result = '''
			local function «alg.luaAlgorithmName»(fb)
			  «lang.generate(Collections.emptyMap())»
			end
		'''
		errors.addAll(lang.errors.map['''Error in algorithm «alg.name»: «it»'''])
		lang.errors.clear()
		return result
	}

}
