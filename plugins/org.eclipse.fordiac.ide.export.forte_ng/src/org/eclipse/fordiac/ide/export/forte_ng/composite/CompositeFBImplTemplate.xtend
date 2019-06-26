package org.eclipse.fordiac.ide.export.forte_ng.composite

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType

class CompositeFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) CompositeFBType type

	new(CompositeFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
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
			  «FOR elem : type.FBNetwork.networkElements.filter[!(it.type instanceof AdapterFBType)] SEPARATOR ",\n"»{«elem.name.FORTEString», «elem.type.name.FORTEString»}«ENDFOR»
			};
			
		«ENDIF»
		«IF !type.FBNetwork.eventConnections.empty»
			const SCFB_FBConnectionData «FBClassName»::scm_astEventConnections[] = {
			  «FOR conn : type.FBNetwork.eventConnections SEPARATOR ",\n"»{«conn.source.generateConnectionPortID(conn.sourceElement)», «conn.destination.generateConnectionPortID(conn.destinationElement)»}«ENDFOR»
			};
			
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			const SCFB_FBConnectionData «FBClassName»::scm_astDataConnections[] = {
			  «FOR conn : type.FBNetwork.dataConnections SEPARATOR ",\n"»{«conn.source.generateConnectionPortID(conn.sourceElement)», «conn.destination.generateConnectionPortID(conn.destinationElement)»}«ENDFOR»
			};
			
		«ENDIF»
		const SCFB_FBNData «FBClassName»::scm_stFBNData = {
		  «type.FBNetwork.networkElements.filter[!(it.type instanceof AdapterFBType)].size», «IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterFBType)]»scm_astInternalFBs«ELSE»nullptr«ENDIF»,
		  «type.FBNetwork.eventConnections.size», «IF !type.FBNetwork.eventConnections.empty»scm_astEventConnections«ELSE»nullptr«ENDIF»,
		  0, nullptr,
		  «type.FBNetwork.dataConnections.size», «IF !type.FBNetwork.dataConnections.empty»scm_astDataConnections«ELSE»nullptr«ENDIF»,
		  0, nullptr,
		  0, nullptr
		};
	'''

	def protected generateConnectionPortID(IInterfaceElement iface, FBNetworkElement elem) {
		return if(type.FBNetwork.networkElements.contains(elem))
			'''GENERATE_CONNECTION_PORT_ID_2_ARG(«elem.name.FORTEString», «iface.name.FORTEString»), «IF elem.type instanceof AdapterFBType»CCompositeFB::scm_nAdapterMarker | «ENDIF»«type.FBNetwork.networkElements.indexOf(elem)»'''
		else
			'''GENERATE_CONNECTION_PORT_ID_1_ARG(«iface.name.FORTEString»), -1'''
	}
}
