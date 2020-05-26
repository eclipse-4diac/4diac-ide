package org.eclipse.fordiac.ide.export.forte_ng.adapter

import java.nio.file.Path
import java.util.List
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

class AdapterFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) AdapterFBType type

	new(AdapterFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		class «FBClassName»: public CAdapter {
		  «generateFBDeclaration»
		
		private:
		  «generateFBInterfaceDeclaration»
		
		  «generateFBInterfaceSpecDeclaration»
		
		  «type.interfaceList.inputVars.generateAccessors("getDI", "getDO")»
		  «type.interfaceList.outputVars.generateAccessors("getDO", "getDI")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		
		public:
		  «type.interfaceList.eventInputs.generateEventAccessors»
		  «type.interfaceList.eventOutputs.generateEventAccessors»
		
		private:
		  FORTE_ADAPTER_DATA_ARRAY(«type.interfaceList.eventInputs.size», «type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);
		
		public:
		  ADAPTER_CTOR(«FBClassName») {};
		
		  virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected generateHeaderIncludes() '''
		#include "adapter.h"
		#include "typelib.h"
		«super.generateHeaderIncludes»
	'''

	override protected generateFBDeclaration() '''
		DECLARE_ADAPTER_TYPE(«FBClassName»)
	'''

	override protected generateFBInterfaceSpecDeclaration() '''
		static const SFBInterfaceSpec scm_stFBInterfaceSpecSocket;
		
		static const SFBInterfaceSpec scm_stFBInterfaceSpecPlug;
	'''

	def protected generateAccessors(List<VarDeclaration> vars, String socketFunction, String plugFunction) '''
		«FOR v : vars»
			CIEC_«v.typeName» «IF v.array»*«ELSE»&«ENDIF»«v.name»() {
			  «IF v.array»
			  	return static_cast<CIEC_«v.typeName»*>(static_cast<CIEC_ARRAY *>((isSocket()) ? «socketFunction»(«vars.indexOf(v)») : «plugFunction»(«vars.indexOf(v)»))[0]); //the first element marks the start of the array
			  «ELSE»
			  	return *static_cast<CIEC_«v.typeName»*>((isSocket()) ? «socketFunction»(«vars.indexOf(v)») : «plugFunction»(«vars.indexOf(v)»));
			  «ENDIF»
			}
			
		«ENDFOR»
	'''

	def protected generateEventAccessors(List<Event> events) '''
		«FOR event : events»
			int «event.name»() {
			  return m_nParentAdapterListEventID + scm_nEvent«event.name»ID;
			}
			
		«ENDFOR»
	'''
}
