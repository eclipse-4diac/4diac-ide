/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 * 				 2020 Johannes Kepler Unviersity Linz
 * 				 2020 TU Wien
 *               2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *   Alois Zoitl  - extracted base class for all types from fbtemplate
 *   Martin Melik Merkumians - adds clause to prevent generation of zero size arrays
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *   Martin Jobst - add event accessors
 *                - add constructor calls for initial value assignments
 *                - add value conversion for initial value assignments
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import java.util.List
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.With

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

abstract class ForteFBTemplate extends ForteLibraryElementTemplate {

	final String DEFAULT_BASE_CLASS

	new(String name, Path prefix, String baseClass) {
		super(name, prefix)
		this.DEFAULT_BASE_CLASS = baseClass
	}

	override protected FBType getType()

	def protected baseClass() {
		if (type?.compilerInfo?.classdef !== null) {
			type.compilerInfo.classdef.trim.isEmpty ? DEFAULT_BASE_CLASS : type.compilerInfo.classdef
		} else {
			DEFAULT_BASE_CLASS
		}
	}

	def protected generateFBClassHeader() '''
		class «FBClassName»: public «baseClass» {
	'''

	def protected generateHeaderIncludes() '''
		«(type.interfaceList.inputVars + type.interfaceList.outputVars).map[type].generateTypeIncludes»
		«(type.interfaceList.sockets + type.interfaceList.plugs).generateAdapterIncludes»
		
		«type.compilerInfo?.header»
	'''

	def protected generateImplIncludes() '''
		#include "«type.name».h"
		#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
		#include "«type.name»_gen.cpp"
		#endif
		
		«type.compilerInfo?.header»
	'''

	def protected generateImplTypeIncludes(Iterable<DataType> vars) '''
		«IF !vars.empty»
			«vars.generateTypeIncludes»
		«ENDIF»
	'''

	def protected generateAdapterIncludes(Iterable<AdapterDeclaration> vars) '''
		«FOR include : vars.map[it.typeName].sort.toSet»
			#include "«include».h"
		«ENDFOR»
	'''

	def protected generateFBDeclaration() '''
		DECLARE_FIRMWARE_FB(«FBClassName»)
	'''

	def protected generateFBDefinition() '''
		DEFINE_FIRMWARE_FB(«FBClassName», «type.name.FORTEString»)
	'''

	def protected generateFBInterfaceDeclaration() '''
		«IF !type.interfaceList.inputVars.empty»
			static const CStringDictionary::TStringId scm_anDataInputNames[];
			static const CStringDictionary::TStringId scm_anDataInputTypeIds[];
		«ENDIF»
		
		«IF !type.interfaceList.outputVars.empty»
			static const CStringDictionary::TStringId scm_anDataOutputNames[];
			static const CStringDictionary::TStringId scm_anDataOutputTypeIds[];
		«ENDIF»
		
		«generateFBEventInputInterfaceDecl()»
		
		«generateFBEventOutputInterfaceDecl()»
		
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			«FOR adapter : type.interfaceList.sockets»
				static const int scm_n«adapter.name»AdpNum = «type.interfaceList.sockets.indexOf(adapter)»;
			«ENDFOR»
			«FOR adapter : type.interfaceList.plugs»
				static const int scm_n«adapter.name»AdpNum = «type.interfaceList.sockets.size + type.interfaceList.plugs.indexOf(adapter)»;
			«ENDFOR»
			
			static const SAdapterInstanceDef scm_astAdapterInstances[];
		«ENDIF»
	'''

	def protected generateFBEventOutputInterfaceDecl() '''«IF !type.interfaceList.eventOutputs.empty»
			«type.interfaceList.eventOutputs.generateEventConstants»
			
			«IF hasOutputWith» static const TDataIOID scm_anEOWith[]; «ENDIF»
			static const TForteInt16 scm_anEOWithIndexes[];
			static const CStringDictionary::TStringId scm_anEventOutputNames[];
		«ENDIF»'''

	def protected generateFBEventInputInterfaceDecl() '''«IF !type.interfaceList.eventInputs.empty»
			«type.interfaceList.eventInputs.generateEventConstants»
			
			«IF hasInputWith» static const TDataIOID scm_anEIWith[];«ENDIF»
			static const TForteInt16 scm_anEIWithIndexes[];
			static const CStringDictionary::TStringId scm_anEventInputNames[];
		«ENDIF»'''

	def protected generateEventConstants(List<Event> events) '''«FOR event : events»
			static const TEventID «event.generateEventName» = «events.indexOf(event)»;
		«ENDFOR»'''

	def protected generateEventName(Event event) '''scm_nEvent«event.name»ID'''

	def protected generateFBInterfaceDefinition() {
		val inputWith = newArrayList
		val inputWithIndexes = newArrayList
		type.interfaceList.eventInputs.forEach [ event |
			{
				if (event.with.empty) {
					inputWithIndexes.add(-1)
				} else {
					inputWithIndexes.add(inputWith.size)
					for (With with : event.with) {
						inputWith.add(type.interfaceList.inputVars.indexOf(with.variables))
					}
					inputWith.add(255)
				}
			}
		]
		val outputWith = newArrayList
		val outputWithIndexes = newArrayList
		type.interfaceList.eventOutputs.forEach [ event |
			{
				if (event.with.empty) {
					outputWithIndexes.add(-1)
				} else {
					outputWithIndexes.add(outputWith.size)
					for (With with : event.with) {
						outputWith.add(type.interfaceList.outputVars.indexOf(with.variables))
					}
					outputWith.add(255)
				}
			}
		]
		'''
			«IF !type.interfaceList.inputVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scm_anDataInputNames[] = {«type.interfaceList.inputVars.FORTENameList»};
				
				const CStringDictionary::TStringId «FBClassName»::scm_anDataInputTypeIds[] = {«type.interfaceList.inputVars.FORTETypeList»};
			«ENDIF»
			
			«IF !type.interfaceList.outputVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scm_anDataOutputNames[] = {«type.interfaceList.outputVars.FORTENameList»};
				
				const CStringDictionary::TStringId «FBClassName»::scm_anDataOutputTypeIds[] = {«type.interfaceList.outputVars.FORTETypeList»};
			«ENDIF»
			
			«IF !type.interfaceList.eventInputs.empty»
				«IF !inputWith.empty»
					const TDataIOID «FBClassName»::scm_anEIWith[] = {«inputWith.join(", ")»};
				«ENDIF»
				const TForteInt16 «FBClassName»::scm_anEIWithIndexes[] = {«inputWithIndexes.join(", ")»};
				const CStringDictionary::TStringId «FBClassName»::scm_anEventInputNames[] = {«type.interfaceList.eventInputs.FORTENameList»};
			«ENDIF»
			
			«IF !type.interfaceList.eventOutputs.empty»
				«IF !outputWith.empty»
					const TDataIOID «FBClassName»::scm_anEOWith[] = {«outputWith.join(", ")»};
				«ENDIF»
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

	def protected generateFBInterfaceSpecDeclaration() '''
		static const SFBInterfaceSpec scm_stFBInterfaceSpec;
	'''

	def protected hasInputWith() {
		type.interfaceList.eventInputs.exists[!it.with.empty]
	}

	def protected hasOutputWith() {
		type.interfaceList.eventOutputs.exists[!it.with.empty]
	}

	def protected generateFBInterfaceSpecDefinition() '''
		const SFBInterfaceSpec «FBClassName»::scm_stFBInterfaceSpec = {
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr«ELSE»scm_anEventInputNames, «IF hasInputWith»scm_anEIWith«ELSE»nullptr«ENDIF», scm_anEIWithIndexes«ENDIF»,
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr«ELSE»scm_anEventOutputNames, «IF hasOutputWith»scm_anEOWith«ELSE»nullptr«ENDIF», scm_anEOWithIndexes«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scm_anDataInputNames, scm_anDataInputTypeIds«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scm_anDataOutputNames, scm_anDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», «IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»scm_astAdapterInstances«ELSE»nullptr«ENDIF»
		};
	'''

	def protected generateInternalVarDelcaration(BaseFBType baseFBType) '''
		«IF !baseFBType.internalVars.isEmpty»
			static const CStringDictionary::TStringId scm_anInternalsNames[];
			static const CStringDictionary::TStringId scm_anInternalsTypeIds[];
			static const SInternalVarsInformation scm_stInternalVars;
		«ENDIF»
	'''

	def protected generateInitialValueAssignmentDeclaration() '''
		virtual void setInitialValues();
	'''

	def protected generateInitialValueAssignmentDefinition(Iterable<VarDeclaration> declarationList) '''
		void FORTE_«type.name»::setInitialValues() {
		  «FOR variable : declarationList»
		  	«IF null !== variable.value && !variable.value.value.isEmpty »
		  		«variable.generateInitialAssignment»
		  	«ENDIF»
		  «ENDFOR»
		}
	'''

	def protected CharSequence generateInitialAssignment(VarDeclaration variable) {
		try {
			'''«EXPORT_PREFIX»«variable.name»() = «variable.generateVariableDefaultValue»;'''
		} catch (Exception e) {
			errors.add(e.message)
			'''// «e.message»'''
		}
	}

	def protected generateInternalVarDefinition(BaseFBType baseFBType) '''
		«IF !baseFBType.internalVars.isEmpty»
			const CStringDictionary::TStringId «FBClassName»::scm_anInternalsNames[] = {«baseFBType.internalVars.FORTENameList»};
			const CStringDictionary::TStringId «FBClassName»::scm_anInternalsTypeIds[] = {«baseFBType.internalVars.FORTETypeList»};
			const SInternalVarsInformation «FBClassName»::scm_stInternalVars = {«baseFBType.internalVars.size», scm_anInternalsNames, scm_anInternalsTypeIds};
		«ENDIF»
	'''

	def protected generateAccessors(List<AdapterDeclaration> adapters) '''
		«FOR adapter : adapters»
			FORTE_«adapter.typeName»& «EXPORT_PREFIX»«adapter.name»() {
			  return (*static_cast<FORTE_«adapter.typeName»*>(m_apoAdapters[«adapters.indexOf(adapter)»]));
			};
			
		«ENDFOR»
	'''

	def protected generateInternalFBAccessors(List<FB> fbs) '''
		«FOR fb : fbs»
			«fb.generateInternalFBAccessors(fbs.indexOf(fb))»
		«ENDFOR»
	'''

	def protected generateInternalFBAccessors(FB fb, int index) '''
		FORTE_«fb.type.name» &fb_«fb.name»() {
		  return *static_cast<FORTE_«fb.type.name»*>(mInternalFBs[«index»]);
		};
	'''

	def protected generateEventAccessorDefinitions() '''
		«FOR event : type.interfaceList.eventInputs»
			«event.generateEventAccessorDefinition»
		«ENDFOR»
		«type.interfaceList.eventInputs.head?.generateEventAccessorCallOperator»
	'''

	def protected generateEventAccessorDefinition(Event event) '''
		void «event.generateEventAccessorName»(«event.generateEventAccessorParameters») {
		  «FOR variable : event.inputParameters.filter(VarDeclaration)»
		  	«exportPrefix»«variable.name»() = «variable.generateName»;
		  «ENDFOR»
		  receiveInputEvent(«event.generateEventName», nullptr);
		  «FOR variable : event.outputParameters.filter(VarDeclaration)»
		  	«variable.generateName» = «exportPrefix»«variable.name»();
		  «ENDFOR»
		}
	'''

	def protected generateEventAccessorCallOperator(Event event) '''
		void operator()(«event.generateEventAccessorParameters») {
			«event.generateEventAccessorName»(«event.generateEventAccessorForwardArguments»);
		}
	'''

	def protected generateEventAccessorName(Event event) '''evt_«event.name»'''

	def protected CharSequence generateEventAccessorParameters(Event event) //
	'''«FOR param : event.eventAccessorParameters SEPARATOR ", "»«IF param.isIsInput»const «ENDIF»«param.generateTypeName» &«param.generateName»«ENDFOR»'''

	def protected CharSequence generateEventAccessorForwardArguments(Event event) //
	'''«FOR param : event.eventAccessorParameters SEPARATOR ", "»«param.generateName»«ENDFOR»'''

	def protected getEventAccessorParameters(Event event) {
		(event.inputParameters + event.outputParameters).filter(VarDeclaration)
	}

	def protected getFBClassName() '''FORTE_«type.name»'''

	def protected generateBasicFBDataArray(
		BaseFBType baseType) '''FORTE_BASIC_FB_DATA_ARRAY(«baseType.interfaceList.eventOutputs.size», «baseType.interfaceList.inputVars.size», «baseType.interfaceList.outputVars.size», «baseType.internalVars.size», «type.interfaceList.sockets.size + baseType.interfaceList.plugs.size»);'''

	def generateInternalFbDefinition() '''
		static const SCFB_FBInstanceData scmInternalFBs[];
	'''

	def generateInteralFbDeclarations(BaseFBType type) '''
		const SCFB_FBInstanceData «FBClassName»::scmInternalFBs[] = {
		  «FOR elem : type.internalFbs SEPARATOR ",\n"»{«elem.name.FORTEString», «elem.type.name.FORTEString»}«ENDFOR»
		};
	'''
}
