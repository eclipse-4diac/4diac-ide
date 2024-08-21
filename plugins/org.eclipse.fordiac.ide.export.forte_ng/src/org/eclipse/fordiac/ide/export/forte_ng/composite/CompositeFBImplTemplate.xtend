/**********************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH, Johannes Kepler University Linz, 
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
import java.util.HashSet
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterConnection
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.AdapterType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.Connection
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class CompositeFBImplTemplate extends ForteFBTemplate<CompositeFBType> {

	final List<FB> fbs
	var eConnNumber = 0
	var fannedOutEventConns = 0
	var dataConnNumber = 0
	var fannedOutDataConns = 0
	final Map<VarDeclaration, ILanguageSupport> fbNetworkInitialVariableLanguageSupport

	new(CompositeFBType type, String name, Path prefix) {
		super(type, name, prefix, "CCompositeFB")
		fbs = type.FBNetwork.networkElements.filter(FB).reject(AdapterFB).toList
		fbNetworkInitialVariableLanguageSupport = fbs.flatMap[interface.inputVars].filter[!value?.value.nullOrEmpty].
			toInvertedMap [
				ILanguageSupportFactory.createLanguageSupport("forte_ng", it)
			]
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		«generateFBInterfaceDefinition»
		«generateFBInterfaceSpecDefinition»
		
		«FBClassName»::«FBClassName»(const CStringDictionary::TStringId paInstanceNameId, forte::core::CFBContainer &paContainer) :
		    «baseClass»(paContainer, scmFBInterfaceSpec, paInstanceNameId, scmFBNData)«//no newline
		    »«fbs.generateInternalFBInitializer»«// no newline
		    »«(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateVariableInitializer»«// no newline
		    »«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterInitializer»«generateConnectionInitializer» {
		};
		«generateInitializeDefinition»
		
		«(type.interfaceList.inputVars + type.interfaceList.inOutVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		«generateSetFBNetworkInitialValuesDefinition»
		«generateFBNetwork»
		«generateReadInternal2InterfaceOutputDataDefinition»
		«generateInterfaceDefinitions»
	'''

	override protected generateInterfaceDefinitions() '''
		«super.generateInterfaceDefinitions»
		«IF (!type.interfaceList.inOutVars.empty)»
			«type.interfaceList.outMappedInOutVars.generateConnectionAccessorsDefinition("getDIOOutConInternalUnchecked", "CInOutDataConnection *", true)»
		«ENDIF»
	'''

	def protected generateReadInternal2InterfaceOutputDataDefinition() '''
		void «FBClassName»::readInternal2InterfaceOutputData(«IF type.interfaceList.eventOutputs.exists[!with.empty]»const TEventID paEOID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventOutputs.generateReadInternal2InterfaceOutputDataBody»
		}
	'''

	def protected generateReadInternal2InterfaceOutputDataBody(List<Event> events) '''
		«IF events.exists[!with.empty]»
			switch(paEOID) {
			  «FOR event : events.filter[!with.empty]»
			  	case «event.generateEventID»: {
			  	  «FOR variable : event.with.map[withVariable].filter[!it.inOutVar]»
			  	  	if(CDataConnection *conn = getIn2IfConUnchecked(«variable.interfaceElementIndex»); conn) { conn->readData(«variable.generateName»); }
			  	  «ENDFOR»
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

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterType)]»
			const SCFB_FBInstanceData «FBClassName»::scmInternalFBs[] = {
			  «FOR elem : fbs SEPARATOR ",\n"»{«elem.name.FORTEStringId», «elem.type.generateTypeSpec»}«ENDFOR»
			};
		«ENDIF»
		
		«IF !type.FBNetwork.eventConnections.empty»
			«type.FBNetwork.eventConnections.exportEventConns»
			
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			«type.FBNetwork.dataConnections.exportDataConns»
			
		«ENDIF»
		«IF !type.FBNetwork.adapterConnections.empty»
			«type.FBNetwork.adapterConnections.exportAdapterConns»
			
		«ENDIF»
		«generateFBNDataStruct()»
	'''

	protected def generateFBNDataStruct() '''
		const SCFB_FBNData «FBClassName»::scmFBNData = {
		  «fbs.size», «IF !fbs.isEmpty»scmInternalFBs«ELSE»nullptr«ENDIF»,
		  «eConnNumber», «IF 0 != eConnNumber»scmEventConnections«ELSE»nullptr«ENDIF»,
		  «fannedOutEventConns», «IF 0 != fannedOutEventConns»scmFannedOutEventConnections«ELSE»nullptr«ENDIF»,
		  «dataConnNumber», «IF 0 != dataConnNumber»scmDataConnections«ELSE»nullptr«ENDIF»,
		  «fannedOutDataConns», «IF 0 != fannedOutDataConns»scmFannedOutDataConnections«ELSE»nullptr«ENDIF»,
		  «type.FBNetwork.adapterConnections.size», «IF !type.FBNetwork.adapterConnections.empty»scmAdapterConnections«ELSE»nullptr«ENDIF»
		};
		
	'''

	def protected generateConnectionPortID(IInterfaceElement iface, FBNetworkElement elem) {
		return if (type.FBNetwork.networkElements.contains(elem))
			'''GENERATE_CONNECTION_PORT_ID_2_ARG(«elem.name.FORTEStringId», «iface.name.FORTEStringId»), «elem.fbId»'''
		else
			'''GENERATE_CONNECTION_PORT_ID_1_ARG(«iface.name.FORTEStringId»), -1'''
	}

	def protected dispatch fbId(FBNetworkElement elem) '''«fbs.indexOf(elem)»'''

	def protected dispatch fbId(
		AdapterFB elem) '''CCompositeFB::scmAdapterMarker | «IF elem.isPlug»«getPlugIndex(elem)»«ELSE»«type.interfaceList.sockets.indexOf(elem.adapterDecl)»«ENDIF»'''

	def protected getPlugIndex(AdapterFB elem) {
		type.interfaceList.sockets.size + type.interfaceList.plugs.indexOf(elem.adapterDecl)
	}

	def protected exportEventConns(EList<EventConnection> eConns) {
		var retVal = new StringBuilder()
		var conSet = new HashSet()
		var fannedOutConns = new StringBuilder()

		retVal.append("const SCFB_FBConnectionData " + FBClassName + "::scmEventConnections[] = {\n")

		for (Connection eConn : eConns) {
			if (!conSet.contains(eConn)) {
				conSet.add(eConn)

				retVal.append(eConn.getConnListEntry)

				if (eConn.source.getOutputConnections.size > 1) {
					// we have fan out
					for (Connection fannedConn : eConn.source.getOutputConnections().filter[!(it == eConn)]) {
						conSet.add(fannedConn)
						fannedOutConns.append(fannedConn.genFannedOutConnString(eConnNumber))
						fannedOutEventConns++
					}
				}
				eConnNumber++;
			}
		}

		retVal.append("};\n")

		if (0 != fannedOutEventConns) {
			retVal.append("\nconst SCFB_FBFannedOutConnectionData " + FBClassName +
				"::scmFannedOutEventConnections[] = {\n")
			retVal.append(fannedOutConns)
			retVal.append("};\n"); // $NON-NLS-1$
		}
		return retVal
	}

	def protected exportDataConns(EList<DataConnection> dataConns) {
		var retVal = new StringBuilder()
		var conSet = new HashSet()
		var fannedOutConns = new StringBuilder()

		retVal.append("const SCFB_FBConnectionData " + FBClassName + "::scmDataConnections[] = {\n")

		for (DataConnection dConn : dataConns) {
			if (!conSet.contains(dConn)) {
				val primConn = getPrimaryDataConn(dConn)
				conSet.add(primConn);

				retVal.append(primConn.getConnListEntry)

				if (primConn.source.getOutputConnections.size > 1) {
					// we have fan out
					for (Connection fannedConn : primConn.source.getOutputConnections().filter[!(it == primConn)]) {
						conSet.add(fannedConn)
						if (fannedConn.hasCFBInterfaceDestination && primConn.hasCFBInterfaceDestination) {
							fannedOutConns.append(
								"#error a fanout to several composite FB's outputs is currently not supported: "); // $NON-NLS-1$
							errors.add(" - " + name +
								" FORTE does currently not allow that a data a composite's data connection may be connected to several data outputs of the composite FB."); // $NON-NLS-1$
						}
						fannedOutConns.append(fannedConn.genFannedOutConnString(dataConnNumber))
						fannedOutDataConns++
					}
				}
				dataConnNumber++;
			}
		}

		retVal.append("};\n")

		if (0 != fannedOutDataConns) {
			retVal.append("\nconst SCFB_FBFannedOutConnectionData " + FBClassName +
				"::scmFannedOutDataConnections[] = {\n")
			retVal.append(fannedOutConns)
			retVal.append("};\n"); // $NON-NLS-1$
		}
		return retVal
	}

	def protected exportAdapterConns(EList<AdapterConnection> adapterConns) '''
		const SCFB_FBConnectionData «FBClassName»::scmAdapterConnections[] = {
		  «FOR aConn : adapterConns»«aConn.getConnListEntry»«ENDFOR»
		};
	'''

	def protected getConnListEntry(
		Connection con) '''  {«con.source.generateConnectionPortID(con.sourceElement)», «con.destination.generateConnectionPortID(con.destinationElement)»},
	'''

	def protected genFannedOutConnString(Connection con, int connNum) {
		'''  {«connNum», «con.destination.generateConnectionPortID(con.destinationElement)»},
		'''
	}

	override protected generateConnectionInitializer() //
	'''«super.generateConnectionInitializer»«// no newline
	   »«type.interfaceList.outMappedInOutVars.generateDataConnectionInitializer(true)»'''

	def private getPrimaryDataConn(DataConnection dataConn) {
		// if from the source one connection is target to the interface of the CFB we have to take this first
		for (Connection dc : dataConn.getSource().getOutputConnections()) {
			if (dc.hasCFBInterfaceDestination) {
				return dc
			}
		}
		return dataConn
	}

	def private hasCFBInterfaceDestination(Connection conn) {
		conn?.getDestination()?.eContainer()?.eContainer() instanceof CompositeFBType
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
}
