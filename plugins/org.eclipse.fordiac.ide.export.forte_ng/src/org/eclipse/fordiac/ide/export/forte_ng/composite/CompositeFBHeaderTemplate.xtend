package org.eclipse.fordiac.ide.export.forte_ng.composite

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.fordiac.ide.model.libraryElement.CompositeFBType
import org.eclipse.xtend.lib.annotations.Accessors

class CompositeFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) CompositeFBType type

	new(CompositeFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		class «FBClassName»: public CCompositeFB {
		  «generateFBDeclaration»
		
		private:
		  «generateFBInterfaceDeclaration»
		
		  «generateFBInterfaceSpecDeclaration»
		
		  «generateFBNetwork»
		
		  «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		
		  FORTE_FB_DATA_ARRAY(«type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);
		
		public:
		  COMPOSITE_FUNCTION_BLOCK_CTOR(«FBClassName») {};
		
		  virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected CharSequence generateHeaderIncludes() '''
		#include "cfb.h"
		#include "typelib.h"
		«super.generateHeaderIncludes»
	'''

	def protected generateFBNetwork() '''
		«IF type.FBNetwork.networkElements.exists[!(it.type instanceof AdapterFBType)]»
			static const SCFB_FBInstanceData scm_astInternalFBs[];
		«ENDIF»
		«IF !type.FBNetwork.eventConnections.empty»
			static const SCFB_FBConnectionData scm_astEventConnections[];
		«ENDIF»
		«IF !type.FBNetwork.dataConnections.empty»
			static const SCFB_FBConnectionData scm_astDataConnections[];
		«ENDIF»
		static const SCFB_FBNData scm_stFBNData;
	'''
}
