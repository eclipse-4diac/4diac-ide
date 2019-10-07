package org.eclipse.fordiac.ide.export.forte_ng.service

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.ServiceInterfaceFBType
import org.eclipse.xtend.lib.annotations.Accessors

class ServiceInterfaceFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) ServiceInterfaceFBType type

	new(ServiceInterfaceFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		
		«generateFBInterfaceDefinition»
		
		«generateFBInterfaceSpecDefinition»
		
		«generateExecuteEvent»
		
	'''
	
	def protected CharSequence generateExecuteEvent() '''
		void «FBClassName»::executeEvent(int pa_nEIID) {
		  switch(pa_nEIID) {
		    «FOR event : type.interfaceList.eventInputs»
		    	case scm_nEvent«event.name»ID:
		    	  #error add code for «event.name» event!
		    	  /*
		    	    do not forget to send output event, calling e.g.
		    	      sendOutputEvent(scm_nEventCNFID);
		    	  */
		    	  break;
		    «ENDFOR»
		  }
		}
	'''
}
