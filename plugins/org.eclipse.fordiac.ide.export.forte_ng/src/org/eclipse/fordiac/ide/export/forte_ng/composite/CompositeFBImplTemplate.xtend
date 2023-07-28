/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University Linz
 *               2023 Martin Erich Jobst
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
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.composite

import java.nio.file.Path
import java.util.ArrayList
import java.util.HashSet
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.Connection
import org.eclipse.fordiac.ide.model.libraryElement.DataConnection
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

class CompositeFBImplTemplate extends ForteFBTemplate<CompositeFBType> {

	var fbs = new ArrayList<FBNetworkElement>
	var numCompFBParams = 0;
	var eConnNumber = 0
	var fannedOutEventConns = 0
	var dataConnNumber = 0
	var fannedOutDataConns = 0

	new(CompositeFBType type, String name, Path prefix) {
		super(type, name, prefix, "CCompositeFB")
		fbs.addAll(type.FBNetwork.networkElements.filter[!(it.type instanceof AdapterFBType)])
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		«generateFBInterfaceDefinition»
		«generateFBInterfaceSpecDefinition»
		
		«FBClassName»::«FBClassName»(const CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) :
		    «baseClass»(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, &scm_stFBNData)«//no newline
			»«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateVariableInitializer»«generateConnectionInitializer» {
		};
		
		«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateSetInitialValuesDefinition»
		«generateFBNetwork»
		
		«generateInterfaceDefinitions»
	'''

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterFBType)]»
			const SCFB_FBInstanceData «FBClassName»::scm_astInternalFBs[] = {
			  «FOR elem : fbs SEPARATOR ",\n"»{«elem.name.FORTEStringId», «elem.type.name.FORTEStringId»}«ENDFOR»
			};
		«ENDIF»
		
		«type.FBNetwork.networkElements.exportFBParams»
		
		«IF !type.FBNetwork.eventConnections.empty»
			«type.FBNetwork.eventConnections.exportEventConns»
			
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			«type.FBNetwork.dataConnections.exportDataConns»
			
		«ENDIF»
		«generateFBNDataStruct()»
	'''

	protected def generateFBNDataStruct() '''
		const SCFB_FBNData «FBClassName»::scm_stFBNData = {
		  «fbs.size», «IF !fbs.isEmpty»scm_astInternalFBs«ELSE»nullptr«ENDIF»,
		  «eConnNumber», «IF 0 != eConnNumber»scm_astEventConnections«ELSE»nullptr«ENDIF»,
		  «fannedOutEventConns», «IF 0 != fannedOutEventConns»scm_astFannedOutEventConnections«ELSE»nullptr«ENDIF»,
		  «dataConnNumber», «IF 0 != dataConnNumber»scm_astDataConnections«ELSE»nullptr«ENDIF»,
		  «fannedOutDataConns», «IF 0 != fannedOutDataConns»scm_astFannedOutDataConnections«ELSE»nullptr«ENDIF»,
		  «numCompFBParams», «IF 0 != numCompFBParams»scm_astParamters«ELSE»nullptr«ENDIF»
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
		AdapterFB elem) '''CCompositeFB::scm_nAdapterMarker | «IF elem.isPlug»«getPlugIndex(elem)»«ELSE»«type.interfaceList.sockets.indexOf(elem.adapterDecl)»«ENDIF»'''

	def protected getPlugIndex(AdapterFB elem) {
		type.interfaceList.sockets.size + type.interfaceList.plugs.indexOf(elem.adapterDecl)
	}

	def protected exportEventConns(EList<EventConnection> eConns) {
		var retVal = new StringBuilder()
		var conSet = new HashSet()
		var fannedOutConns = new StringBuilder()

		retVal.append("const SCFB_FBConnectionData " + FBClassName + "::scm_astEventConnections[] = {\n")

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
				"::scm_astFannedOutEventConnections[] = {\n")
			retVal.append(fannedOutConns)
			retVal.append("};\n"); // $NON-NLS-1$
		}
		return retVal
	}

	def protected exportDataConns(EList<DataConnection> dataConns) {
		var retVal = new StringBuilder()
		var conSet = new HashSet()
		var fannedOutConns = new StringBuilder()

		retVal.append("const SCFB_FBConnectionData " + FBClassName + "::scm_astDataConnections[] = {\n")

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
				"::scm_astFannedOutDataConnections[] = {\n")
			retVal.append(fannedOutConns)
			retVal.append("};\n"); // $NON-NLS-1$
		}
		return retVal
	}

	def protected getConnListEntry(
		Connection con) '''  {«con.source.generateConnectionPortID(con.sourceElement)», «con.destination.generateConnectionPortID(con.destinationElement)»},
	'''

	def protected genFannedOutConnString(Connection con, int connNum) {
		'''  {«connNum», «con.destination.generateConnectionPortID(con.destinationElement)»},
		'''
	}

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

	def protected exportFBParams(EList<FBNetworkElement> fbs) {
		var retVal = new StringBuilder()

		for (FBNetworkElement fb : fbs) {
			for (VarDeclaration v : fb.getInterface.getInputVars.filter[it.value !== null && !it.value.value.isEmpty]) {
				retVal.append('''  {«fb.fbId», g_nStringId«v.name», "«getParamValue(v)»"},
				''')
				numCompFBParams++
			}
		}

		'''«IF 0 != numCompFBParams»
		const SCFB_FBParameter «FBClassName»::scm_astParamters[] = {
		«retVal.toString»
		};«ENDIF»'''
	}

	def private getParamValue(VarDeclaration v) {
		v.value.value.replace("\"", "\\\"");
	}

}
