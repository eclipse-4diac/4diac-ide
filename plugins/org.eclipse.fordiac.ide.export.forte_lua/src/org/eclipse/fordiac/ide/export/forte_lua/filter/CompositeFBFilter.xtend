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
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.Connection
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.xtend.lib.annotations.Accessors

import static extension org.eclipse.fordiac.ide.export.forte_lua.filter.LuaConstants.*

class CompositeFBFilter {
	@Accessors(PUBLIC_GETTER)
	List<String> errors = new ArrayList<String>;
	static final int ADAPTER_MARKER = 0x10000;

	def String lua(CompositeFBType type) '''
		«type.interfaceList.luaEventConstants»
		«type.interfaceList.luaFBVariableConstants»
				
		«type.interfaceList.luaInterfaceSpec»
		
		«type.luaFbnSpec»
		
		return {interfaceSpec = interfaceSpec, fbnSpec = fbnSpec}
	'''
	
	def static luaFbnSpec(CompositeFBType type)'''
	local fbnSpec = {
	  «type.FBNetwork.luaInternalFBs»,
	  «type.FBNetwork.luaParameters»,
	  «type.luaEventConnections»,
	  «type.luaFannedOutEventConnections»,
	  «type.luaDataConnections»,
	  «type.luaFannedOutDataConnections»,
	  «type.luaAdapterConnections»,
	  «type.FBNetwork.luaFbnData»
	}'''
	
	def static luaInternalFBs(FBNetwork fbn) '''
	internalFBs = {
	  «var fbs = fbn.networkElements.filter(e| !(e instanceof AdapterFB))»
	  «FOR fb : fbs SEPARATOR ','»
	  «IF !(fb instanceof AdapterFB)»
	  {fbNameID = "«fb.name»", fbTypeID = "«fb.fullTypeName»"}
	  «ENDIF»
	  «ENDFOR»
	}'''

	def static luaParameters(FBNetwork fbn){
	var fbs = fbn.networkElements.filter(e| !(e instanceof AdapterFB))
	var parameters = fbs.toList.getParameters
	'''
	parameters = {
	  «FOR p : parameters SEPARATOR ','» 
	  {fbNum = «p.get(0) as Integer», diNameID = "«p.get(1)»", paramValue = "«p.get(2)»"}
	  «ENDFOR»
	}'''
	} 

	def static luaEventConnections(CompositeFBType type) '''
	eventConnections = {
	  «var allCons = type.FBNetwork.eventConnections»
	  «var connections = allCons.filter(e| e.source.outputConnections.size == 1 || (e.source.outputConnections.size > 1 && e.source.outputConnections.get(0).equals(e)))»
	  «FOR con : connections SEPARATOR ','»  	
	  «var sne = con.source.FBNetworkElement»
	  «var dne = con.destination.FBNetworkElement»
	  «IF null !== dne && null !== sne»
	  {«sne.luaConnectionString(con.source, type, "src")», «dne.luaConnectionString(con.destination, type, "dst")»}
	  «ELSEIF null === dne»
	  {«sne.luaConnectionString(con.source, type, "src")», dstID = "«con.destination.name»", dstFBNum = -1}
	  «ELSEIF null === sne»
	  {srcID = "«con.source.name»", srcFBNum = -1, «dne.luaConnectionString(con.destination, type, "dst")»}
	  «ELSE»
	  {srcID = "«con.source.name»", srcFBNum = -1, dstID = "«con.destination.name»", dstFBNum = -1}
	  «ENDIF»
	  «ENDFOR»
	}'''
	
	def static String luaConnectionString(FBNetworkElement e, IInterfaceElement ev, CompositeFBType type, String stringID){
		var plugs = type.interfaceList.plugs.map[it.adapterFB].toList
		var sockets = type.interfaceList.sockets.map[it.adapterFB].toList
		if (e instanceof AdapterFB){
			if(plugs.contains(e)){
				return '''«stringID»ID = "«ev.name»", «stringID»FBNum = «ADAPTER_MARKER.bitwiseOr(plugs.indexOf(e))»'''
			}else{
				return '''«stringID»ID = "«ev.name»", «stringID»FBNum = «ADAPTER_MARKER.bitwiseOr(plugs.size + sockets.indexOf(e))»'''
			}
		}else{
			return '''«stringID»ID = "«ev.name»", «stringID»FBNum = «type.FBNetwork.networkElements.filter(f| !(f instanceof AdapterFB)).toList.indexOf(e)»'''
		}
	}
	
	
	def static luaFannedOutEventConnections(CompositeFBType type) '''
	fannedOutEventConnections = {
	  «var allCons = type.FBNetwork.eventConnections»
	  «var conList = allCons.filter(e| e.source.outputConnections.size == 1 || (e.source.outputConnections.size > 1 && e.source.outputConnections.get(0).equals(e))).toList»
	  «var connections = allCons.filter(e| e.source.outputConnections.size > 1 && !e.source.outputConnections.get(0).equals(e))»
	  «FOR con : connections SEPARATOR ','»
	  «var dne = con.destination.FBNetworkElement»
	  «IF null !== dne»
	  {connectionNum = «org.eclipse.fordiac.ide.export.forte_lua.filter.CompositeFBFilter.getConnectionNumber(conList, con)», «dne.luaConnectionString(con.destination, type, "dst")»}
	  «ELSE»
	  {dstID = "«con.destination.name»", -1}
	  «ENDIF»
	  «ENDFOR»
	}'''
	
	def static int getConnectionNumber(List<?> allCons, Connection con){
		var num = allCons.indexOf(con.source.outputConnections.get(0))
		return num
	}
	
	def static luaDataConnections(CompositeFBType type) '''
	dataConnections = {
	  «var allCons = type.FBNetwork.dataConnections»
	  «var connections = allCons.filter(e| e.source.outputConnections.size == 1 || (e.source.outputConnections.size > 1 && e.source.outputConnections.get(0).equals(e)))»
	  «FOR con : connections SEPARATOR ','»  	
	  «var sne = con.source.FBNetworkElement»
	  «var dne = con.destination.FBNetworkElement»
	  «IF null !== dne && null !== sne»
	  {«sne.luaConnectionString(con.source, type, "src")», «dne.luaConnectionString(con.destination, type, "dst")»}
	  «ELSEIF null === dne»
	  {«sne.luaConnectionString(con.source, type, "src")», dstID = "«con.destination.name»", dstFBNum = -1}
	  «ELSEIF null === sne»
	  {srcID = "«con.source.name»", srcFBNum = -1, «dne.luaConnectionString(con.destination, type, "dst")»}
	  «ELSE»
	  {srcID = "«con.source.name»", srcFBNum = -1, dstID = "«dne.name».«con.destination.name»", dstFBNum = -1}
	  «ENDIF»
	  «ENDFOR»
	}'''
	
	def static luaAdapterConnections(CompositeFBType type) '''
	adapterConnections = {
	  «val connections = type.FBNetwork.adapterConnections»
	  «FOR con : connections SEPARATOR ','»  	
		  «val sne = con.source.FBNetworkElement»
		  «val dne = con.destination.FBNetworkElement»
		  {«sne.luaConnectionString(con.source, type, "src")», «dne.luaConnectionString(con.destination, type, "dst")»}
	  «ENDFOR»
	}'''
	
	def static luaFannedOutDataConnections(CompositeFBType type) '''
	fannedOutDataConnections = {
	  «var allCons = type.FBNetwork.dataConnections»
	  «var conList = allCons.filter(e| e.source.outputConnections.size == 1 || (e.source.outputConnections.size > 1 && e.source.outputConnections.get(0).equals(e))).toList»
	  «var connections = allCons.filter(e| e.source.outputConnections.size > 1 && !e.source.outputConnections.get(0).equals(e))»
	  «FOR con : connections SEPARATOR ','»
  	  «var dne = con.destination.FBNetworkElement»
	  «IF null !== dne»
	  {connectionNum = «org.eclipse.fordiac.ide.export.forte_lua.filter.CompositeFBFilter.getConnectionNumber(conList, con)», «dne.luaConnectionString(con.destination, type, "dst")»}
  	  «ELSE»
	  {dstID = "«con.destination.name»", dstFBNum = -1}
  	  «ENDIF»
	  «ENDFOR»
	}'''
	
	def static luaFbnData(FBNetwork fbn) '''
	numFBs = «fbn.networkElements.filter(e| !(e instanceof AdapterFB)).size»,
	numECons = «fbn.eventConnections.filter(e| e.source.outputConnections.size == 1 || (e.source.outputConnections.size > 1 && e.source.outputConnections.get(0).equals(e))).size»,
	numFECons = «fbn.eventConnections.filter(e| e.source.outputConnections.size > 1 && !e.source.outputConnections.get(0).equals(e)).size»,
	numDCons = «fbn.dataConnections.filter(e| e.source.outputConnections.size == 1 || (e.source.outputConnections.size > 1 && e.source.outputConnections.get(0).equals(e))).size»,
	numFDCons = «fbn.dataConnections.filter(e| e.source.outputConnections.size > 1 && !e.source.outputConnections.get(0).equals(e)).size»,
	numAdpCons = «fbn.adapterConnections.size»,
	numParams = «fbn.getNumParameter»
	'''
	
	def static private int getNumParameter(FBNetwork fbn){
		return fbn.networkElements.filter(e| !(e instanceof AdapterFB)).toList.getParameters.size
	}
	
	def static private ArrayList<ArrayList<?>> getParameters(List<FBNetworkElement> fbs){
		var parameters = new ArrayList<ArrayList<?>>
		for(ne : fbs){
			for(iv : ne.interface.inputVars){
				if(null !== iv.value && !iv.value.value.empty){
					var list = new ArrayList<Object>
					list.add(fbs.indexOf(ne))
					list.add(iv.name)
					list.add(iv.value.value)
					parameters.add(list)
				}
			}
		}
		return parameters
	}
}