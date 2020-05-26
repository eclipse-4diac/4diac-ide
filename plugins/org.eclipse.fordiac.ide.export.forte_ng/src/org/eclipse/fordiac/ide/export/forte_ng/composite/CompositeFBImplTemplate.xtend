/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University Linz
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
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.EventConnection
import org.eclipse.emf.common.util.EList
import java.util.HashSet
import org.eclipse.fordiac.ide.model.libraryElement.Connection

class CompositeFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) CompositeFBType type

	var fbs = new ArrayList<FBNetworkElement>
	
	var eConnNumber = 0
	var fannedOutEventConns = 0;

	new(CompositeFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
		fbs.addAll(type.FBNetwork.networkElements.filter[!(it.type instanceof AdapterFBType)])
	}

	override generate() '''
		«generateHeader»

		«generateImplIncludes»

		«generateFBDefinition»

		«generateFBInterfaceDefinition»

		«generateFBInterfaceSpecDefinition»

		«generateFBNetwork»

	'''

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterFBType)]»
			const SCFB_FBInstanceData «FBClassName»::scm_astInternalFBs[] = {
			  «FOR elem : fbs SEPARATOR ",\n"»{«elem.name.FORTEString», «elem.type.name.FORTEString»}«ENDFOR»
			};

		«ENDIF»
		«IF !type.FBNetwork.eventConnections.empty»
		    «exportCFBEventConns(type.FBNetwork.eventConnections)»

		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			const SCFB_FBConnectionData «FBClassName»::scm_astDataConnections[] = {
			  «FOR conn : type.FBNetwork.dataConnections SEPARATOR ",\n"»{«conn.source.generateConnectionPortID(conn.sourceElement)», «conn.destination.generateConnectionPortID(conn.destinationElement)»}«ENDFOR»
			};

		«ENDIF»
		const SCFB_FBNData «FBClassName»::scm_stFBNData = {
		  «fbs.size», «IF !fbs.isEmpty»scm_astInternalFBs«ELSE»nullptr«ENDIF»,
		  «eConnNumber», «IF 0 != eConnNumber»scm_astEventConnections«ELSE»nullptr«ENDIF»,
		  «fannedOutEventConns», «IF 0 != fannedOutEventConns»scm_astFannedOutEventConnections«ELSE»nullptr«ENDIF»,
		  «type.FBNetwork.dataConnections.size», «IF !type.FBNetwork.dataConnections.empty»scm_astDataConnections«ELSE»nullptr«ENDIF»,
		  0, nullptr,
		  0, nullptr
		};
	'''

	def protected generateConnectionPortID(IInterfaceElement iface, FBNetworkElement elem) {
		return if(type.FBNetwork.networkElements.contains(elem))
			'''GENERATE_CONNECTION_PORT_ID_2_ARG(«elem.name.FORTEString», «iface.name.FORTEString»), «elem.fbId»'''
		else
			'''GENERATE_CONNECTION_PORT_ID_1_ARG(«iface.name.FORTEString»), -1'''
	}

	def protected dispatch fbId(FBNetworkElement elem)
	   '''«fbs.indexOf(elem)»'''

	def protected dispatch fbId(AdapterFB elem)
	   '''CCompositeFB::scm_nAdapterMarker | «IF elem.isPlug»«getPlugIndex(elem)»«ELSE»«type.interfaceList.sockets.indexOf(elem.adapterDecl)»«ENDIF»'''

    def protected getPlugIndex(AdapterFB elem) {
        type.interfaceList.sockets.size + type.interfaceList.plugs.indexOf(elem.adapterDecl)
    }
    
    def protected exportCFBEventConns(EList<EventConnection> eConns) {
        var retVal = new StringBuilder()
        var conSet = new HashSet()
        var fannedOutConns = new StringBuilder()
        
        retVal.append("const SCFB_FBConnectionData " + FBClassName + "::scm_astEventConnections[] = {\n")

        for (Connection eConn : eConns) {
            if (!conSet.contains(eConn)) {
                conSet.add(eConn)

                retVal.append(eConn.getConnListEntry)

                if (!eConn.source.getOutputConnections().empty) {
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

        retVal.append("\nconst SCFB_FBFannedOutConnectionData " + FBClassName + "::scm_astFannedOutEventConnections[] = {\n") 
        if (0 != fannedOutEventConns) {
            retVal.append(fannedOutConns)
        }
        retVal.append("};\n"); //$NON-NLS-1$
    }

    def protected getConnListEntry(Connection con)
    '''  {«con.source.generateConnectionPortID(con.sourceElement)», «con.destination.generateConnectionPortID(con.destinationElement)»},
    '''

    def protected genFannedOutConnString(Connection con, int connNum) {
        '''  {«connNum», «con.destination.generateConnectionPortID(con.destinationElement)»},
        '''
    }
    

}
