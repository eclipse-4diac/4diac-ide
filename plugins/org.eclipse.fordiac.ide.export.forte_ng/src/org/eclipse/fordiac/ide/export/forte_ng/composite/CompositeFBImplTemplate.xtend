/**********************************************************************************
 * Copyright (c) 2019, 2025 fortiss GmbH, Johannes Kepler University Linz, 
 *                          Martin Erich Jobst, Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - fixed adapter and fb number generation in connection lists
 *                - added adapter connection generation 
 *   Martin Melik Merkumians - add code for export CFB internal VarInOut usage
 ********************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.composite

import java.nio.file.Path
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.Connection
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class CompositeFBImplTemplate extends ForteFBTemplate<CompositeFBType> {

	final List<FB> fbs
	final Map<VarDeclaration, ILanguageSupport> fbNetworkInitialVariableLanguageSupport

	new(CompositeFBType type, String name, Path prefix, Map<?, ?> options) {
		super(type, name, prefix, "CCompositeFB", options)
		fbs = type.FBNetwork.networkElements.filter(FB).reject(AdapterFB).toList
		fbNetworkInitialVariableLanguageSupport = fbs.flatMap[interface.inputVars].filter[!value?.value.nullOrEmpty].
			toInvertedMap [
				ILanguageSupportFactory.createLanguageSupport("forte_ng", it, options)
			]
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateUseStringId»
		
		namespace {
		  «generateTypeHash»
		
		  «generateFBInterfaceDefinition»
		
		  «generateFBInterfaceSpecDefinition»
		
		  «generateFBNetwork»
		}
		
		«generateFBDefinition»
		
		«FBClassName»::«FBClassName»(const CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer) :
		    «baseClass»(paContainer, cFBInterfaceSpec, paInstanceNameId, cFBNData)«//no newline
		    »«fbs.generateInternalFBInitializer»«// no newline
		    »«type.interfaceList.outputVars.filter[inputConnections.empty].generateVariableInitializer»«// no newline
		    »«(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAdapterInitializer»«// no newline
			»«generateConnectionInitializer» {
		};
		
		«(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		«generateSetFBNetworkInitialValuesDefinition»
		«generateInterfaceDefinitions»
	'''

	override protected generateInterfaceDefinitions() '''
		«super.generateInterfaceDefinitions»
		«type.interfaceList.inputVars.generateConnectionAccessorsDefinition("getIf2InConUnchecked", "CDataConnection *", true)»
		«IF (!type.interfaceList.inOutVars.empty)»
			«type.interfaceList.outMappedInOutVars.generateConnectionAccessorsDefinition("getDIOOutConInternalUnchecked", "CInOutDataConnection *", true)»
		«ENDIF»
	'''

	def private generateFBNetwork() '''
		«IF !type.FBNetwork.eventConnections.empty»
			«type.FBNetwork.eventConnections.generateConnections("cEventConnections")»
			
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			«type.FBNetwork.dataConnections.generateConnections("cDataConnections")»
			
		«ENDIF»
		«IF !type.FBNetwork.adapterConnections.empty»
			«type.FBNetwork.adapterConnections.generateConnections("cAdapterConnections")»
			
		«ENDIF»
		«generateFBNDataStruct()»
	'''

	def private generateFBNDataStruct() '''
		const SCFB_FBNData cFBNData = {
		  .mEventConnections = «IF !type.FBNetwork.eventConnections.empty»cEventConnections«ELSE»{}«ENDIF»,
		  .mDataConnections = «IF !type.FBNetwork.dataConnections.empty»cDataConnections«ELSE»{}«ENDIF»,
		  .mAdapterConnections = «IF !type.FBNetwork.adapterConnections.empty»cAdapterConnections«ELSE»{}«ENDIF»,
		};
	'''

	def private generateConnectionPortID(IInterfaceElement iface, FBNetworkElement elem) {
		return if (type.FBNetwork.networkElements.contains(elem))
			'''«elem.name.FORTEStringId», «iface.name.FORTEStringId»'''
		else
			'''CStringDictionary::scmInvalidStringId, «iface.name.FORTEStringId»'''
	}

	def private generateConnections(EList<? extends Connection> connections, String listName) '''
		const auto «listName» = std::to_array<SCFB_FBConnectionData>({
		  «FOR conn : connections»
		  	«conn.generateConnectionEntry»
		  «ENDFOR»
		});
	'''

	def private generateConnectionEntry(Connection con) //
	'''  {«con.source.generateConnectionPortID(con.sourceElement)», «con.destination.generateConnectionPortID(con.destinationElement)»},'''

	override protected generateConnectionInitializer() //
	'''«super.generateConnectionInitializer»«// no newline
		   »«type.interfaceList.inputVars.generateDataConnectionInitializer(true)»«// no newline
		   »«type.interfaceList.outMappedInOutVars.generateDataConnectionInitializer(true)»'''

	override protected generateDataConnectionInitializer(List<VarDeclaration> variables, boolean internal) //
	'''«FOR variable : variables BEFORE ",\n" SEPARATOR ",\n"»«variable.generateNameAsConnection(internal)»(*this, «variables.indexOf(variable)», «variable.generateVariableDefaultValue»)«ENDFOR»'''

	override protected generateWriteOutputDataVariable(VarDeclaration variable) {
		if (!variable.inOutVar && !variable.inputConnections.empty && variable.inputConnections.first.negated) {
			'''
				«variable.generateName» = func_NOT(«variable.inputConnections.first.generateConnectionValue»);
				«super.generateWriteOutputDataVariable(variable)»
			'''
		} else
			super.generateWriteOutputDataVariable(variable)
	}

	def generateSetFBNetworkInitialValuesDefinition() '''
		«IF fbs.flatMap[interface.inputVars].exists[!value?.value.nullOrEmpty]»
			void «FBClassName»::setFBNetworkInitialValues() {
			  «FOR fb : fbs»
			  	«FOR variable : fb.interface.inputVars.filter[!value?.value.nullOrEmpty]»
			  		«IF fb.type.genericType»
			  			if (auto v = «fb.generateName»->getDataInput(«variable.name.FORTEStringId»)) { v->setValue(«variable.generateFBNetworkInitialValue»); }
			  		«ELSE»
			  			«fb.generateName»->«variable.generateName» = «variable.generateFBNetworkInitialValue»;
			  		«ENDIF»
			  	«ENDFOR»
			  «ENDFOR»
			}
			
		«ENDIF»
	'''

	def CharSequence generateFBNetworkInitialValue(VarDeclaration decl) {
		fbNetworkInitialVariableLanguageSupport.get(decl)?.generate(emptyMap)
	}

	override getErrors() {
		(super.getErrors + fbNetworkInitialVariableLanguageSupport.values.filterNull.flatMap[getErrors].toSet).toList
	}

	override getWarnings() {
		(super.getWarnings + fbNetworkInitialVariableLanguageSupport.values.filterNull.flatMap[getWarnings].toSet).
			toList
	}

	override getInfos() {
		(super.getInfos + fbNetworkInitialVariableLanguageSupport.values.filterNull.flatMap[getInfos].toSet).toList
	}

	override getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) + fbNetworkInitialVariableLanguageSupport.values.filterNull.flatMap [
			getDependencies(options)
		]).toSet
	}

	override Set<String> getUsedStrings(Map<?, ?> options) {
		val strings = super.getUsedStrings(options)
		fbs.forEach [
			{
				getUsedFBStrings(it, strings)
				getUsedInitialFBVarStrings(it, strings)
			}
		]
		type.FBNetwork.eventConnections.forEach[getUsedConStrings(it, strings)]
		type.FBNetwork.dataConnections.forEach[getUsedConStrings(it, strings)]
		type.FBNetwork.adapterConnections.forEach[getUsedConStrings(it, strings)]
		return strings
	}

	def protected void getUsedConStrings(Connection con, Set<String> strings) {
		// fb instances names are already added when the network elements are added so we can ignore them here
		strings.add(con.source.name)
		strings.add(con.destination.name)
	}

	def protected void getUsedInitialFBVarStrings(FBNetworkElement fbe, Set<String> strings) {
		if (fbe.type.genericType) {
			for (variable : fbe.interface.inputVars.filter[!value?.value.nullOrEmpty]) {
				strings.add(variable.name)
			}
		}
	}

}
