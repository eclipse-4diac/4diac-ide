package org.eclipse.fordiac.ide.export.forte_ng.adapter

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFBType
import org.eclipse.xtend.lib.annotations.Accessors

class AdapterFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) AdapterFBType type

	new(AdapterFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»
		
		«generateImplIncludes»
		
		«generateFBDefinition»
		
		«generateFBInterfaceDefinition»
		
		«generateFBInterfaceSpecDefinition»
		
	'''

	override protected CharSequence generateFBDefinition() '''
		DEFINE_ADAPTER_TYPE(«FBClassName», «type.name.FORTEString»)
	'''

	override protected CharSequence generateFBInterfaceSpecDefinition() '''
		const SFBInterfaceSpec «FBClassName»::scm_stFBInterfaceSpecSocket = {
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr«ELSE»scm_anEventInputNames, scm_anEIWith, scm_anEIWithIndexes«ENDIF»,
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr«ELSE»scm_anEventOutputNames, scm_anEOWith, scm_anEOWithIndexes«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scm_anDataInputNames, scm_anDataInputTypeIds«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scm_anDataOutputNames, scm_anDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», nullptr
		};
		
		const SFBInterfaceSpec «FBClassName»::scm_stFBInterfaceSpecPlug = {
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr«ELSE»scm_anEventOutputNames, scm_anEOWith, scm_anEOWithIndexes«ENDIF»,
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr«ELSE»scm_anEventInputNames, scm_anEIWith, scm_anEIWithIndexes«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scm_anDataOutputNames, scm_anDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scm_anDataInputNames, scm_anDataInputTypeIds«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», nullptr
		};
	'''
}
