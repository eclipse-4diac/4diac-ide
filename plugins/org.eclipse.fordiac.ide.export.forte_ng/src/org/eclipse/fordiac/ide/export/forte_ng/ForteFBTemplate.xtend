package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import java.util.List
import org.eclipse.fordiac.ide.export.ExportTemplate
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.With
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration

abstract class ForteFBTemplate extends ExportTemplate {

	static final CharSequence EXPORT_PREFIX = "st_"

	new(String name, Path prefix) {
		super(name, prefix) 
	}

	def protected FBType getType()

	static def CharSequence getExportPrefix() {
		return EXPORT_PREFIX
	}

	def protected CharSequence generateHeader() '''
		/*************************************************************************
		 *** FORTE Library Element
		 ***
		 *** This file was generated using the 4DIAC FORTE Export Filter V1.0.x NG!
		 ***
		 *** Name: «type.name»
		 *** Description: «type.comment»
		 *** Version:
		«FOR info : type.versionInfo»
			***     «info.version»: «info.date»/«info.author» - «info.organization» - «info.remarks»
		«ENDFOR»
		 *************************************************************************/
	'''

	def protected CharSequence generateIncludeGuardStart() '''
		#ifndef _«type.name.toUpperCase»_H_
		#define _«type.name.toUpperCase»_H_
	'''

	def protected CharSequence generateIncludeGuardEnd() '''
		#endif // _«type.name.toUpperCase»_H_
	'''

	def protected CharSequence generateHeaderIncludes() '''
		«(type.interfaceList.inputVars + type.interfaceList.outputVars).generateTypeIncludes»
		«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterIncludes»
		
		«type.compilerInfo?.header»
	'''

	def protected CharSequence generateImplIncludes() '''
		#include "«type.name».h"
		#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
		#include "«type.name»_gen.cpp"
		#endif
		
		«type.compilerInfo?.header»
	'''

	def protected CharSequence generateTypeIncludes(Iterable<VarDeclaration> vars) '''
		«FOR include : vars.map[it.typeName].sort.toSet»
			#include "forte_«include.toLowerCase».h"
			«IF include.startsWith("ANY")»
				#ERROR type contains variables of type ANY. Please check the usage of these variables as we can not gurantee correct usage on export!
			«ENDIF»
		«ENDFOR»
		«IF vars.exists[array]»
			#include "forte_array.h"
		«ENDIF»
		#include "forte_array_at.h"
	'''

	def protected CharSequence generateAdapterIncludes(Iterable<AdapterDeclaration> vars) '''
		«FOR include : vars.map[it.typeName].sort.toSet»
			#include "«include».h"
		«ENDFOR»
	'''

	def protected CharSequence generateFBDeclaration() '''
		DECLARE_FIRMWARE_FB(«FBClassName»)
	'''

	def protected CharSequence generateFBDefinition() '''
		DEFINE_FIRMWARE_FB(«FBClassName», «type.name.FORTEString»)
	'''

	def protected CharSequence generateFBInterfaceDeclaration() '''
		«IF !type.interfaceList.inputVars.empty»
			static const CStringDictionary::TStringId scm_anDataInputNames[];
			static const CStringDictionary::TStringId scm_anDataInputTypeIds[];
		«ENDIF»
		
		«IF !type.interfaceList.outputVars.empty»
			static const CStringDictionary::TStringId scm_anDataOutputNames[];
			static const CStringDictionary::TStringId scm_anDataOutputTypeIds[];
		«ENDIF»
		
		«IF !type.interfaceList.eventInputs.empty»
			«FOR event : type.interfaceList.eventInputs»
				static const TEventID scm_nEvent«event.name»ID = «type.interfaceList.eventInputs.indexOf(event)»;
			«ENDFOR»
			
			static const TDataIOID scm_anEIWith[];
			static const TForteInt16 scm_anEIWithIndexes[];
			static const CStringDictionary::TStringId scm_anEventInputNames[];
		«ENDIF»
		
		«IF !type.interfaceList.eventOutputs.empty»
			«FOR event : type.interfaceList.eventOutputs»
				static const TEventID scm_nEvent«event.name»ID = «type.interfaceList.eventOutputs.indexOf(event)»;
			«ENDFOR»
			
			static const TDataIOID scm_anEOWith[];
			static const TForteInt16 scm_anEOWithIndexes[];
			static const CStringDictionary::TStringId scm_anEventOutputNames[];
		«ENDIF»
		
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			«FOR adapter : type.interfaceList.sockets»
				static const int scm_n«adapter.name»AdpNum = «type.interfaceList.sockets.indexOf(adapter)»;
			«ENDFOR»
			«FOR adapter : type.interfaceList.plugs»
				static const int scm_n«adapter.name»AdpNum = «type.interfaceList.sockets.size + type.interfaceList.plugs.indexOf(adapter)»;
			«ENDFOR»
			
			const SAdapterInstanceDef scm_astAdapterInstances[];
		«ENDIF»
	'''

	def protected CharSequence generateFBInterfaceDefinition() {
		val inputWith = newArrayList
		val inputWithIndexes = newArrayList
		type.interfaceList.eventInputs.forEach[event | {
			if(event.with.empty) {
				inputWithIndexes.add(-1)
			} else {
				inputWithIndexes.add(inputWith.size)
				for (With with : event.with) {
					inputWith.add(type.interfaceList.inputVars.indexOf(with.variables))
				}
				inputWith.add(255)
			}
		}]
		val outputWith = newArrayList
		val outputWithIndexes = newArrayList
		type.interfaceList.eventOutputs.forEach[event | {
			if(event.with.empty) {
				outputWithIndexes.add(-1)
			} else {
				outputWithIndexes.add(outputWith.size)
				for (With with : event.with) {
					outputWith.add(type.interfaceList.outputVars.indexOf(with.variables))
				}
				outputWith.add(255)
			}
		}]
		return '''
			«IF !type.interfaceList.inputVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scm_anDataInputNames[] = {«type.interfaceList.inputVars.FORTENameList»};
				
				const CStringDictionary::TStringId «FBClassName»::scm_anDataInputTypeIds[] = {«type.interfaceList.inputVars.FORTETypeList»};
			«ENDIF»
			
			«IF !type.interfaceList.outputVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scm_anDataOutputNames[] = {«type.interfaceList.outputVars.FORTENameList»};
				
				const CStringDictionary::TStringId «FBClassName»::scm_anDataOutputTypeIds[] = {«type.interfaceList.outputVars.FORTETypeList»};
			«ENDIF»
			
			«IF !type.interfaceList.eventInputs.empty»
				const TDataIOID «FBClassName»::scm_anEIWith[] = {«inputWith.join(", ")»};
				const TForteInt16 «FBClassName»::scm_anEIWithIndexes[] = {«inputWithIndexes.join(", ")»};
				const CStringDictionary::TStringId «FBClassName»::scm_anEventInputNames[] = {«type.interfaceList.eventInputs.FORTENameList»};
			«ENDIF»
			
			«IF !type.interfaceList.eventOutputs.empty»
				const TDataIOID «FBClassName»::scm_anEOWith[] = {«outputWith.join(", ")»};
				const TForteInt16 «FBClassName»::scm_anEOWithIndexes[] = {«outputWithIndexes.join(", ")»};
				const CStringDictionary::TStringId «FBClassName»::scm_anEventOutputNames[] = {«type.interfaceList.eventOutputs.FORTENameList»};
			«ENDIF»
			
			«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
				const SAdapterInstanceDef «FBClassName»::scm_astAdapterInstances[] = {
				  «FOR adapter : (type.interfaceList.sockets + type.interfaceList.plugs) SEPARATOR ",\n"»{«adapter.typeName.FORTEString», «adapter.name.FORTEString», «!adapter.isInput»}«ENDFOR»
				};
			«ENDIF»
		'''
	}

	def protected CharSequence generateFBInterfaceSpecDeclaration() '''
		static const SFBInterfaceSpec scm_stFBInterfaceSpec;
	'''

	def protected CharSequence generateFBInterfaceSpecDefinition() '''
		const SFBInterfaceSpec «FBClassName»::scm_stFBInterfaceSpec = {
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr«ELSE»scm_anEventInputNames, scm_anEIWith, scm_anEIWithIndexes«ENDIF»,
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr«ELSE»scm_anEventOutputNames, scm_anEOWith, scm_anEOWithIndexes«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scm_anDataInputNames, scm_anDataInputTypeIds«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scm_anDataOutputNames, scm_anDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», «IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»scm_astAdapterInstances«ELSE»nullptr«ENDIF»
		};
	'''

	def protected CharSequence generateAccessors(List<VarDeclaration> vars, String function) '''
		«FOR v : vars»
			CIEC_«v.typeName» «IF v.array»*«ELSE»&«ENDIF»«EXPORT_PREFIX»«v.name»() {
			  «IF v.array»
			  	return static_cast<CIEC_«v.typeName»*>(static_cast<CIEC_ARRAY *>(«function»(«vars.indexOf(v)»))[0]); //the first element marks the start of the array
			  «ELSE»
			  	return *static_cast<CIEC_«v.typeName»*>(«function»(«vars.indexOf(v)»));
			  «ENDIF»
			}
			
		«ENDFOR»
	'''

	def protected CharSequence generateAccessors(List<AdapterDeclaration> adapters) '''
		«FOR adapter : adapters»
			FORTE_«adapter.typeName»& «adapter.name»() {
			  return (*static_cast<FORTE_«adapter.typeName»*>(m_apoAdapters[«adapters.indexOf(adapter)»]));
			};
			
		«ENDFOR»
	'''

	def protected CharSequence getFORTENameList(List<? extends INamedElement> elements) {
		return elements.map[name.FORTEString].join(", ")
	}

	def protected CharSequence getFORTETypeList(List<? extends VarDeclaration> elements) {
		return elements.map['''«IF it.array»«"ARRAY".FORTEString», «it.arraySize», «ENDIF»«it.type.name.FORTEString»'''].join(", ")
	}

	def protected CharSequence getFBClassName() '''FORTE_«type.name»'''

	def protected CharSequence getFORTEString(String s) '''g_nStringId«s»'''
}
