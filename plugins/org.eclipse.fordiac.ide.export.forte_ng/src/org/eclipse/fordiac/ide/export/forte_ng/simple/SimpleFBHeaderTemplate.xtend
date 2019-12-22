package org.eclipse.fordiac.ide.export.forte_ng.simple

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.xtend.lib.annotations.Accessors

class SimpleFBHeaderTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) SimpleFBType type

	new(SimpleFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»
		
		«generateIncludeGuardStart»
		
		«generateHeaderIncludes»
		
		class «FBClassName»: public CSimpleFB {
		  «generateFBDeclaration»
		
		private:
		  «generateFBInterfaceDeclaration»
		
		  «generateFBInterfaceSpecDeclaration»
		
		  «type.interfaceList.inputVars.generateAccessors("getDI")»
		  «type.interfaceList.outputVars.generateAccessors("getDO")»
		  «type.internalVars.generateAccessors("getVarInternal")»
		  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessors»
		
		  «generateAlgorithms»
		
		  FORTE_FB_DATA_ARRAY(«type.interfaceList.eventOutputs.size», «type.interfaceList.inputVars.size», «type.interfaceList.outputVars.size», «type.interfaceList.sockets.size + type.interfaceList.plugs.size»);
		
		public:
		  «FBClassName»(CStringDictionary::TStringId pa_nInstanceNameId, CResource *pa_poSrcRes) : 
		       CSimpleFB(pa_poSrcRes, &scm_stFBInterfaceSpec, pa_nInstanceNameId, «IF !type.internalVars.empty»&scm_stInternalVars«ELSE»nullptr«ENDIF», m_anFBConnData, m_anFBVarsData) {
		  };
		
		  virtual ~«FBClassName»() = default;
		};
		
		«generateIncludeGuardEnd»
		
	'''

	override protected CharSequence generateHeaderIncludes() '''
		#include "simplefb.h"
		«(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).generateTypeIncludes»
		«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterIncludes»
		
		«type.compilerInfo?.header»
	'''

	def protected CharSequence generateAlgorithms() '''
		void alg_REQ(void);
	'''
}
