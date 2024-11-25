/*******************************************************************************
 * Copyright (c) 2019, 2024 fortiss GmbH, Johannes Kepler Unviersity Linz
 * 				 			TU Wien, Martin Erich Jobst.
 *               			Primetals Technologies Austria GmbH
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
 *                           - adds generation of initial value assignment
 *                           - adds export of internal constants
 *                           - adds capabilities for VarInOut connections
 * 							 - add code for export CFB internal VarInOut usage
 *   Martin Jobst - add event accessors
 *                - add constructor calls for initial value assignments
 *                - add value conversion for initial value assignments
 *                - refactor memory layout
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng

import java.nio.file.Path
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.fordiac.ide.model.LibraryElementTags
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.With

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.data.EventType
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary

abstract class ForteFBTemplate<T extends FBType> extends ForteLibraryElementTemplate<T> {

	final String DEFAULT_BASE_CLASS

	new(T type, String name, Path prefix, String baseClass) {
		super(type, name, prefix)
		this.DEFAULT_BASE_CLASS = baseClass
	}

	def protected baseClass() {
		if (type?.compilerInfo?.classdef !== null) {
			type.compilerInfo.classdef.trim.isEmpty ? DEFAULT_BASE_CLASS : type.compilerInfo.classdef
		} else {
			DEFAULT_BASE_CLASS
		}
	}

	def protected generateFBClassHeader() '''
		class «FBClassName» final : public «baseClass» {
	'''

	def protected generateHeaderIncludes() '''
		«getDependencies(#{ForteNgExportFilter.OPTION_HEADER -> Boolean.TRUE}).generateDependencyIncludes»
		«type.compilerInfo?.header»
	'''

	def protected generateImplIncludes() '''
		#include "«fileBasename».h"
		#ifdef FORTE_ENABLE_GENERATED_SOURCE_CPP
		#include "«type.generateTypeGenIncludePath»"
		#endif
		
		«getDependencies(emptyMap).generateDependencyIncludes»
		«type.compilerInfo?.header»
	'''

	def protected generateFBDeclaration() '''
		DECLARE_FIRMWARE_FB(«FBClassName»)
	'''

	def protected generateFBDefinition() '''
		DEFINE_FIRMWARE_FB(«FBClassName», «type.generateTypeSpec»)
		
	'''

	def protected generateFBInterfaceDeclaration() '''
		«IF !type.interfaceList.inputVars.empty»
			static const CStringDictionary::TStringId scmDataInputNames[];
			static const CStringDictionary::TStringId scmDataInputTypeIds[];
		«ENDIF»
		«IF !type.interfaceList.outputVars.empty»
			static const CStringDictionary::TStringId scmDataOutputNames[];
			static const CStringDictionary::TStringId scmDataOutputTypeIds[];
		«ENDIF»
		«IF !type.interfaceList.inOutVars.empty»
			static const CStringDictionary::TStringId scmDataInOutNames[];
			static const CStringDictionary::TStringId scmDataInOutTypeIds[];
		«ENDIF»
		«generateFBEventInputInterfaceDecl»
		«generateFBEventOutputInterfaceDecl»
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			«FOR adapter : type.interfaceList.sockets»
				static const int scm«adapter.name»AdpNum = «type.interfaceList.sockets.indexOf(adapter)»;
			«ENDFOR»
			«FOR adapter : type.interfaceList.plugs»
				static const int scm«adapter.name»AdpNum = «type.interfaceList.sockets.size + type.interfaceList.plugs.indexOf(adapter)»;
			«ENDFOR»
			static const SAdapterInstanceDef scmAdapterInstances[];
		«ENDIF»
	'''

	def protected generateFBEventOutputInterfaceDecl() '''«IF !type.interfaceList.eventOutputs.empty»
			«type.interfaceList.eventOutputs.generateEventConstants»
			«IF hasOutputWith»static const TDataIOID scmEOWith[];«ENDIF»
			static const TForteInt16 scmEOWithIndexes[];
			static const CStringDictionary::TStringId scmEventOutputNames[];
			«IF !type.interfaceList.eventOutputs.containsOnlyBasicEventType»
				static const CStringDictionary::TStringId scmEventOutputTypeIds[]; 
			«ENDIF»
		«ENDIF»'''

	def protected generateFBEventInputInterfaceDecl() '''«IF !type.interfaceList.eventInputs.empty»
			«type.interfaceList.eventInputs.generateEventConstants»
			«IF hasInputWith»static const TDataIOID scmEIWith[];«ENDIF»
			static const TForteInt16 scmEIWithIndexes[];
			static const CStringDictionary::TStringId scmEventInputNames[];
			«IF !type.interfaceList.eventInputs.containsOnlyBasicEventType»
				static const CStringDictionary::TStringId scmEventInputTypeIds[]; 
			«ENDIF»
		«ENDIF»'''

	def protected generateEventConstants(List<Event> events) '''«FOR event : events»
			static const TEventID «event.generateEventID» = «events.indexOf(event)»;
		«ENDFOR»'''

	def protected generateEventID(Event event) '''scmEvent«event.name»ID'''

	def protected generateFBInterfaceDefinition() {
		val inputWith = newArrayList
		val inputWithIndexes = newArrayList
		type.interfaceList.eventInputs.forEach [ event |
			{
				val associatedInputs = event.with.filter[!it.variables.isInOutVar]
				if (associatedInputs.empty) {
					inputWithIndexes.add(-1)
				} else {
					inputWithIndexes.add(inputWith.size)
					associatedInputs.forEach[inputWith.add(type.interfaceList.inputVars.indexOf(it.variables))]
					inputWith.add("scmWithListDelimiter")
				}
			}
		]
		val outputWith = newArrayList
		val outputWithIndexes = newArrayList
		type.interfaceList.eventOutputs.forEach [ event |
			{
				val associatedOutputs = event.with.filter[!it.variables.isInOutVar]
				if (associatedOutputs.empty) {
					outputWithIndexes.add(-1)
				} else {
					outputWithIndexes.add(outputWith.size)
					associatedOutputs.forEach[outputWith.add(type.interfaceList.outputVars.indexOf(it.variables))]
					outputWith.add("scmWithListDelimiter")
				}
			}
		]
		'''
			«IF !type.interfaceList.inputVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scmDataInputNames[] = {«type.interfaceList.inputVars.FORTENameList»};
				const CStringDictionary::TStringId «FBClassName»::scmDataInputTypeIds[] = {«type.interfaceList.inputVars.FORTETypeList»};
			«ENDIF»
			«IF !type.interfaceList.outputVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scmDataOutputNames[] = {«type.interfaceList.outputVars.FORTENameList»};
				const CStringDictionary::TStringId «FBClassName»::scmDataOutputTypeIds[] = {«type.interfaceList.outputVars.FORTETypeList»};
			«ENDIF»
			«IF !type.interfaceList.inOutVars.empty»
				const CStringDictionary::TStringId «FBClassName»::scmDataInOutNames[] = {«type.interfaceList.inOutVars.FORTENameList»};
				const CStringDictionary::TStringId «FBClassName»::scmDataInOutTypeIds[] = {«type.interfaceList.inOutVars.FORTETypeList»};
			«ENDIF»
			«IF !type.interfaceList.eventInputs.empty»
				«IF !inputWith.empty»
					const TDataIOID «FBClassName»::scmEIWith[] = {«inputWith.join(", ")»};
				«ENDIF»
				const TForteInt16 «FBClassName»::scmEIWithIndexes[] = {«inputWithIndexes.join(", ")»};
				const CStringDictionary::TStringId «FBClassName»::scmEventInputNames[] = {«type.interfaceList.eventInputs.FORTENameList»};
				«IF !type.interfaceList.eventInputs.containsOnlyBasicEventType»
					const CStringDictionary::TStringId «FBClassName»::scmEventInputTypeIds[] = {«type.interfaceList.eventInputs.FORTEEventTypeList»};
				«ENDIF»
			«ENDIF»
			«IF !type.interfaceList.eventOutputs.empty»
				«IF !outputWith.empty»
					const TDataIOID «FBClassName»::scmEOWith[] = {«outputWith.join(", ")»};
				«ENDIF»
				const TForteInt16 «FBClassName»::scmEOWithIndexes[] = {«outputWithIndexes.join(", ")»};
				const CStringDictionary::TStringId «FBClassName»::scmEventOutputNames[] = {«type.interfaceList.eventOutputs.FORTENameList»};
				«IF !type.interfaceList.eventOutputs.containsOnlyBasicEventType»
						const CStringDictionary::TStringId «FBClassName»::scmEventOutputTypeIds[] = {«type.interfaceList.eventOutputs.FORTEEventTypeList»};
				«ENDIF»
			«ENDIF»
			«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
				const SAdapterInstanceDef «FBClassName»::scmAdapterInstances[] = {
				  «FOR adapter : (type.interfaceList.sockets + type.interfaceList.plugs) SEPARATOR ",\n"»{«adapter.typeName.FORTEStringId», «adapter.name.FORTEStringId», «!adapter.isInput»}«ENDFOR»
				};
			«ENDIF»
		'''
	}

	def protected generateFBInterfaceSpecDeclaration() '''
		static const SFBInterfaceSpec scmFBInterfaceSpec;
	'''

	def protected hasInputWith() {
		!type.interfaceList.eventInputs.flatMap[it.with].filter[!it.variables.inOutVar].empty
	}

	def protected hasOutputWith() {
		!type.interfaceList.eventOutputs.flatMap[it.with].filter[!it.variables.inOutVar].empty
	}

	def containsOnlyBasicEventType(EList<Event> events) {
		events.findFirst[!it.typeName.contentEquals(EventTypeLibrary.EVENT)] === null
	}
	
	// changes to this method require a recheck of the two methods generateFBInterfaceSpecSocket, generateFBInterfaceSpecPlug of AdapterFBImplTemplate
	// as there this code is duplicated
	def protected generateFBInterfaceSpecDefinition() '''
		const SFBInterfaceSpec «FBClassName»::scmFBInterfaceSpec = {
		  «type.interfaceList.eventInputs.size», «IF type.interfaceList.eventInputs.empty»nullptr, nullptr, nullptr, nullptr«ELSE»scmEventInputNames, «IF type.interfaceList.eventInputs.containsOnlyBasicEventType»nullptr«ELSE»scmEventInputTypeIds«ENDIF», «IF hasInputWith»scmEIWith«ELSE»nullptr«ENDIF», scmEIWithIndexes«ENDIF»,
		  «type.interfaceList.eventOutputs.size», «IF type.interfaceList.eventOutputs.empty»nullptr, nullptr, nullptr, nullptr«ELSE»scmEventOutputNames, «IF type.interfaceList.eventOutputs.containsOnlyBasicEventType»nullptr«ELSE»scmEventOutputTypeIds«ENDIF», «IF hasOutputWith»scmEOWith«ELSE»nullptr«ENDIF», scmEOWithIndexes«ENDIF»,
		  «type.interfaceList.inputVars.size», «IF type.interfaceList.inputVars.empty»nullptr, nullptr«ELSE»scmDataInputNames, scmDataInputTypeIds«ENDIF»,
		  «type.interfaceList.outputVars.size», «IF type.interfaceList.outputVars.empty»nullptr, nullptr«ELSE»scmDataOutputNames, scmDataOutputTypeIds«ENDIF»,
		  «type.interfaceList.inOutVars.size», «IF type.interfaceList.inOutVars.empty»nullptr«ELSE»scmDataInOutNames«ENDIF»,
		  «type.interfaceList.plugs.size + type.interfaceList.sockets.size», «IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»scmAdapterInstances«ELSE»nullptr«ENDIF»
		};
	'''

	def protected generateInternalVarDeclaration(BaseFBType baseFBType) '''
		«IF !baseFBType.internalVars.isEmpty»
			static const CStringDictionary::TStringId scmInternalsNames[];
			static const CStringDictionary::TStringId scmInternalsTypeIds[];
			static const SInternalVarsInformation scmInternalVars;
			
		«ENDIF»
	'''

	def protected generateInternalVarDefinition(BaseFBType baseFBType) '''
		«IF !baseFBType.internalVars.isEmpty»
			const CStringDictionary::TStringId «FBClassName»::scmInternalsNames[] = {«baseFBType.internalVars.FORTENameList»};
			const CStringDictionary::TStringId «FBClassName»::scmInternalsTypeIds[] = {«baseFBType.internalVars.FORTETypeList»};
			const SInternalVarsInformation «FBClassName»::scmInternalVars = {«baseFBType.internalVars.size», scmInternalsNames, scmInternalsTypeIds};
		«ENDIF»
	'''

	def protected generateReadInputDataDeclaration() '''
		void readInputData(TEventID paEIID) override;
	'''

	def protected generateReadInputDataDefinition() '''
		void «FBClassName»::readInputData(«IF type.interfaceList.eventInputs.exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventInputs.generateReadInputDataBody»
		}
	'''

	def protected generateReadInputDataBody(List<Event> events) '''
		«IF events.exists[!with.empty]»
			switch(paEIID) {
			  «FOR event : events.filter[!with.empty]»
			  	case «event.generateEventID»: {
			  	  «FOR variable : event.with.map[withVariable]»
			  	  	readData(«variable.interfaceElementIndex», «variable.generateName», «IF variable.inOutVar»&«ENDIF»«variable.generateNameAsConnection»);
			  	  «ENDFOR»
			  	  break;
			  	}
			  «ENDFOR»
			  default:
			    break;
			}
		«ELSE»
			// nothing to do
		«ENDIF»
	'''

	def protected generateWriteOutputDataDeclaration() '''
		void writeOutputData(TEventID paEIID) override;
	'''

	def protected generateWriteOutputDataDefinition() '''
		void «FBClassName»::writeOutputData(«IF type.interfaceList.eventOutputs.exists[!with.empty]»const TEventID paEIID«ELSE»TEventID«ENDIF») {
		  «type.interfaceList.eventOutputs.generateWriteOutputDataBody»
		}
	'''

	def protected generateWriteOutputDataBody(List<Event> events) '''
		«IF events.exists[!with.empty]»
			switch(paEIID) {
			  «FOR event : events.filter[!with.empty]»
			  	case «event.generateEventID»: {
			  	  «FOR variable : event.with.map[withVariable]»
			  	  	writeData(«variable.interfaceElementIndex», «variable.generateName», «variable.generateNameAsConnection»);
			  	  «ENDFOR»
			  	  break;
			  	}
			  «ENDFOR»
			  default:
			    break;
			}
		«ELSE»
			// nothing to do
		«ENDIF»
	'''

	def protected getWithVariable(With with) {
		val varDeclaration = with.variables
		if (varDeclaration.inOutVar && varDeclaration.isIsInput) {
			varDeclaration.getInOutVarOpposite()
		} else
			varDeclaration
	}

	def protected generateInterfaceDeclarations() '''
		«generateInterfaceVariableAndConnectionDeclarations()»
		«generateAccessorDeclarations()»
		«generateEventAccessorDefinitions»
	'''

	protected def CharSequence generateAccessorDeclarations() '''
		«generateAccessorDeclaration("getDI", false)»
		«generateAccessorDeclaration("getDO", false)»
		«IF (!type.interfaceList.inOutVars.empty)»
			«generateAccessorDeclaration("getDIO", false)»
		«ENDIF»
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			«generateAccessorDeclaration("getAdapterUnchecked", "CAdapter *", false)»
		«ENDIF»
		«generateConnectionAccessorsDeclaration("getEOConUnchecked", "CEventConnection *")»
		«generateConnectionAccessorsDeclaration("getDIConUnchecked", "CDataConnection **")»
		«generateConnectionAccessorsDeclaration("getDOConUnchecked", "CDataConnection *")»
		«IF (!type.interfaceList.inOutVars.empty)»
			«generateConnectionAccessorsDeclaration("getDIOInConUnchecked", "CInOutDataConnection **")»
		«ENDIF»
		«IF (!type.interfaceList.inOutVars.empty)»
			«generateConnectionAccessorsDeclaration("getDIOOutConUnchecked", "CInOutDataConnection *")»
		«ENDIF»
	'''

	protected def CharSequence generateInterfaceVariableAndConnectionDeclarations() '''
		«type.interfaceList.inputVars.generateVariableDeclarations(false)»
		«type.interfaceList.outputVars.generateVariableDeclarations(false)»
		«type.interfaceList.inOutVars.generateVariableDeclarations(false)»
		«type.interfaceList.sockets.generateAdapterDeclarations»
		«type.interfaceList.plugs.generateAdapterDeclarations»
		«type.interfaceList.outputVars.generateConnectionVariableDeclarations»
		«type.interfaceList.outMappedInOutVars.generateConnectionVariableDeclarations»
		«type.interfaceList.eventOutputs.generateEventConnectionDeclarations»
		«type.interfaceList.inputVars.generateDataConnectionDeclarations(true)»
		«type.interfaceList.outputVars.generateDataConnectionDeclarations(false)»
		«type.interfaceList.inOutVars.generateDataConnectionDeclarations(true)»
		«type.interfaceList.outMappedInOutVars.generateDataConnectionDeclarations(false)»
	'''

	def protected generateInterfaceDefinitions() '''
		«generateReadInputDataDefinition»
		
		«generateWriteOutputDataDefinition»
		
		«type.interfaceList.inputVars.generateAccessorDefinition("getDI", false)»
		«type.interfaceList.outputVars.generateAccessorDefinition("getDO", false)»
		«IF (!type.interfaceList.inOutVars.empty)»
			«type.interfaceList.inOutVars.generateAccessorDefinition("getDIO", false)»
		«ENDIF»
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			«(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAccessorDefinition("getAdapterUnchecked", "CAdapter *", false)»
		«ENDIF»
		«type.interfaceList.eventOutputs.generateConnectionAccessorsDefinition("getEOConUnchecked", "CEventConnection *")»
		«type.interfaceList.inputVars.generateConnectionAccessorsDefinition("getDIConUnchecked", "CDataConnection **")»
		«type.interfaceList.outputVars.generateConnectionAccessorsDefinition("getDOConUnchecked", "CDataConnection *")»
		«IF (!type.interfaceList.inOutVars.empty)»
			«type.interfaceList.inOutVars.generateConnectionAccessorsDefinition("getDIOInConUnchecked", "CInOutDataConnection **")»«ENDIF»
		«IF (!type.interfaceList.inOutVars.empty)»
			«type.interfaceList.outMappedInOutVars.generateConnectionAccessorsDefinition("getDIOOutConUnchecked", "CInOutDataConnection *")»
		«ENDIF»
	'''

	def protected generateSetInitialValuesDeclaration(Iterable<VarDeclaration> variables) '''
		«IF containsNonRetainedVariable(variables)»
			void setInitialValues() override;
		«ENDIF»
	'''

	def protected generateSetInitialValuesDefinition(Iterable<VarDeclaration> variables) '''
		«IF (containsNonRetainedVariable(variables))»
			void «className»::setInitialValues() {
			  «generateVariableDefaultAssignment(variables.filter[!isRetainedVariable(it)])»
			}
			
		«ENDIF»	
	'''

	def private boolean isRetainedVariable(VarDeclaration variable) {
		return variable.getAttributeValue(LibraryElementTags.RETAIN_ATTRIBUTE) !== null &&
			variable.getAttributeValue(LibraryElementTags.RETAIN_ATTRIBUTE).equals(RetainTag.RETAIN.string);
	}

	def private boolean containsNonRetainedVariable(Iterable<VarDeclaration> variables) {
		return variables.exists[!isRetainedVariable(it)];
	}

	def private generateVariableDefaultAssignment(Iterable<VarDeclaration> variables) '''
		«FOR variable : variables»
			«variable.generateName» = «variable.generateVariableDefaultValue»;
		«ENDFOR»
	'''

	def protected generateConnectionVariableDeclarations(List<VarDeclaration> variables) '''
		«FOR variable : variables AFTER '\n'»
			«variable.generateVariableTypeName» «variable.generateNameAsConnectionVariable»;
		«ENDFOR»
	'''

	def protected generateConnectionVariableInitializer(Iterable<VarDeclaration> variables) '''
	«FOR variable : variables BEFORE ",\n" SEPARATOR ",\n"»«variable.generateNameAsConnectionVariable»(«variable.generateName»)«ENDFOR»'''

	def protected generateEventConnectionDeclarations(List<Event> elements) '''
		«FOR element : elements AFTER '\n'»
			CEventConnection «element.generateNameAsConnection»;
		«ENDFOR»
	'''

	def protected generateDataConnectionDeclarations(List<VarDeclaration> elements, boolean input) {
		generateDataConnectionDeclarations(elements, input, false)
	}

	def protected generateDataConnectionDeclarations(List<VarDeclaration> elements, boolean input,
		boolean internalConnection) '''
		«FOR element : elements AFTER '\n'»
			«IF element.isInOutVar»CInOutDataConnection«ELSE»CDataConnection«ENDIF» «IF input»*«ENDIF»«element.generateNameAsConnection(internalConnection)»;
		«ENDFOR»
	'''

	def protected generateConnectionInitializer() //
	'''«type.interfaceList.outputVars.generateConnectionVariableInitializer»«// no newline
	   »«type.interfaceList.eventOutputs.generateEventConnectionInitializer»«//no newline
	   »«type.interfaceList.inputVars.generateDataConnectionPointerInitializer»«//no newline
	   »«type.interfaceList.outputVars.generateDataConnectionInitializer»«//no newline
	   »«type.interfaceList.inOutVars.generateDataConnectionPointerInitializer»«//no newline
	   »«type.interfaceList.outMappedInOutVars.generateDataConnectionInitializer»'''

	def protected generateEventConnectionInitializer(List<Event> events) //
	'''«FOR event : events BEFORE ",\n" SEPARATOR ",\n"»«event.generateNameAsConnection»(this, «events.indexOf(event)»)«ENDFOR»'''

	def protected generateDataConnectionInitializer(List<VarDeclaration> variables) {
		generateDataConnectionInitializer(variables, false);
	}

	def protected generateDataConnectionInitializer(List<VarDeclaration> variables, boolean internal) //
	'''«FOR variable : variables BEFORE ",\n" SEPARATOR ",\n"»«variable.generateNameAsConnection(internal)»(this, «variables.indexOf(variable)», &«IF internal»«variable.generateName»«ELSE»«variable.generateNameAsConnectionVariable»«ENDIF»)«ENDFOR»'''

	def protected generateDataConnectionPointerInitializer(List<VarDeclaration> variables) //
	'''«FOR variable : variables BEFORE ",\n" SEPARATOR ",\n"»«variable.generateNameAsConnection»(nullptr)«ENDFOR»'''

	def protected generateConnectionAccessorsDeclaration(String function, String type) '''
		«type»«function»(TPortId) override;
	'''

	def protected generateConnectionAccessorsDefinition(List<? extends INamedElement> elements, String function,
		String type) {
		generateConnectionAccessorsDefinition(elements, function, type, false)
	}

	def protected generateConnectionAccessorsDefinition(List<? extends INamedElement> elements, String function,
		String type, boolean internal) '''
		«IF elements.empty»
			«type»«className»::«function»(TPortId) {
			  return nullptr;
			}
			
		«ELSE»
			«type»«className»::«function»(const TPortId paIndex) {
			  switch(paIndex) {
			    «FOR element : elements»
			    	case «elements.indexOf(element)»: return &«element.generateNameAsConnection(internal)»;
			    «ENDFOR»
			  }
			  return nullptr;
			}
			
		«ENDIF»
	'''

	def protected CharSequence generateNameAsConnection(VarDeclaration varDeclaration) {
		generateNameAsConnection(varDeclaration, false)
	}

	def protected CharSequence generateNameAsConnection(VarDeclaration varDeclaration, boolean internal) '''
	«IF varDeclaration.inOutVar»conn_«varDeclaration.name»«IF varDeclaration.isIsInput»In«ELSE»Out«ENDIF»«IF internal»Internal«ENDIF»«ELSE»conn_«varDeclaration.name»«ENDIF»'''

	def protected CharSequence generateNameAsConnection(INamedElement element, boolean internal) {
		switch (element) {
			VarDeclaration: generateNameAsConnection(element, internal)
			default: '''conn_«element.name»'''
		}
	}

	def protected CharSequence generateNameAsConnection(INamedElement element) {
		generateNameAsConnection(element, false)
	}

	def protected CharSequence generateNameAsConnectionVariable(INamedElement element) '''var_conn_«element.name»'''

	def protected generateEventAccessorDefinitions() '''
		«FOR event : type.interfaceList.eventInputs BEFORE '\n'»
			«event.generateEventAccessorDefinition»
		«ENDFOR»
		«type.interfaceList.eventInputs.head?.generateEventAccessorCallOperator»
	'''

	def protected generateEventAccessorDefinition(Event event) '''
		void «event.generateName»(«event.generateEventAccessorParameters») {
		  «FOR variable : (event.inputParameters + event.inOutParameters).filter(VarDeclaration)»
		  	«variable.generateName» = «variable.generateNameAsParameter»;
		  «ENDFOR»
		  executeEvent(«event.generateEventID», nullptr);
		  «FOR variable : (event.outputParameters + event.inOutParameters).filter(VarDeclaration)»
		  	«IF GenericTypes.isAnyType(variable.type)»
		  		«variable.generateNameAsParameter».setValue(«variable.generateName».unwrap());
		  	«ELSE»
		  		«variable.generateNameAsParameter» = «variable.generateName»;
		  	«ENDIF»
		  «ENDFOR»
		}
		
	'''

	def protected generateEventAccessorCallOperator(Event event) '''
		void operator()(«event.generateEventAccessorParameters») {
		  «event.generateName»(«event.generateEventAccessorForwardArguments»);
		}
	'''

	def protected CharSequence generateEventAccessorParameters(Event event) //
	'''«FOR param : event.eventAccessorParameters SEPARATOR ", "»«IF param.isInput && !param.inOutVar»const «ENDIF»«param.generateVariableTypeNameAsParameter» &«param.generateNameAsParameter»«ENDFOR»'''

	def protected CharSequence generateEventAccessorForwardArguments(Event event) //
	'''«FOR param : event.eventAccessorParameters SEPARATOR ", "»«param.generateNameAsParameter»«ENDFOR»'''

	def protected getEventAccessorParameters(Event event) {
		(event.inputParameters + event.inOutParameters + event.outputParameters).filter(VarDeclaration)
	}

	def protected getFBClassName() { className }

	def protected generateInitializeDeclaration() '''
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			bool initialize() override;
		«ENDIF»
	'''

	def protected generateInitializeDefinition() '''
		«IF !type.interfaceList.sockets.empty || !type.interfaceList.plugs.empty»
			
			bool «FBClassName»::initialize() {
««« initialize adapters before base class to support establishing connections in CCompositeFB::initialize()
			  «(type.interfaceList.sockets + type.interfaceList.plugs).toList.generateAdapterInitialize»
			  return «baseClass»::initialize();
			}
		«ENDIF»
	'''

	def protected generateAdapterInitialize(List<AdapterDeclaration> adapters) '''
		«FOR adapter : adapters»
			if(!«adapter.generateName».initialize()) { return false; }
		«ENDFOR»
		«FOR adapter : adapters»
			«adapter.generateName».setParentFB(this, «adapters.indexOf(adapter)»);
		«ENDFOR»
	'''

	def generateInternalFBDeclarations(List<FB> internalFbs) '''
		«FOR fb : internalFbs»
			forte::core::CInternalFB<«fb.type.generateTypeName»> «fb.generateName»;
		«ENDFOR»		
	'''

	def generateInternalFBInitializer(List<FB> internalFbs) // /
	'''«FOR fb : internalFbs BEFORE ",\n" SEPARATOR ",\n"»«fb.generateInternalFBInitializer»«ENDFOR»'''

	def generateInternalFBInitializer(FB fb) {
		if (fb.type.genericType)
			'''«fb.generateName»(«fb.name.FORTEStringId», "«fb.generateInternalFBConfigString»", *this)'''
		else
			'''«fb.generateName»(«fb.name.FORTEStringId», *this)'''
	}

	def generateInternalFBConfigString(FB fb) {
		switch (fb) {
			ConfigurableFB case fb.dataType !== null: //
			'''«fb.type.generateTypeNamePlain»_1«fb.dataType.generateTypeNamePlain»'''
			default:
				fb.type.generateTypeNamePlain
		}
	}

	override Set<INamedElement> getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) + (type.interfaceList.sockets + type.interfaceList.plugs).map[getType]
			).toSet
	}
}
