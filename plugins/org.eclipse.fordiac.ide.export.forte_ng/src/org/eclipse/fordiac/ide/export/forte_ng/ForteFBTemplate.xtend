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
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.LibraryElementTags
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.GenericTypes
import org.eclipse.fordiac.ide.model.datatype.helper.RetainHelper.RetainTag
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableFB
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FB
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.model.libraryElement.With
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary

import static extension org.eclipse.fordiac.ide.export.forte_ng.util.ForteNgExportUtil.*

abstract class ForteFBTemplate<T extends FBType> extends ForteLibraryElementTemplate<T> {

	final String DEFAULT_BASE_CLASS

	new(T type, String name, Path prefix, String baseClass, Map<?, ?> options) {
		super(type, name, prefix, options)
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

	def protected generateFBDeclaration() '''
		DECLARE_FIRMWARE_FB(«FBClassName»)
	'''

	def protected generateFBDefinition() '''
		DEFINE_FIRMWARE_FB(«FBClassName», «type.generateTypeSpec», TypeHash)
	'''

	def protected generateFBInterfaceDeclaration() '''
		«type.interfaceList.eventOutputs.generateEventConstants»
		«type.interfaceList.eventInputs.generateEventConstants»
	'''

	def protected generateEventConstants(List<Event> events) '''«FOR event : events»
			static const TEventID «event.generateEventID» = «events.indexOf(event)»;
		«ENDFOR»'''

	def protected generateEventID(Event event) '''scmEvent«event.name»ID'''

	def protected generateFBInterfaceDefinition() '''
		«IF !type.interfaceList.eventInputs.empty»
			const auto cEventInputNames = std::array{«type.interfaceList.eventInputs.FORTENameList»};
			«IF !type.interfaceList.eventInputs.containsOnlyBasicEventType»
				const auto cEventInputTypeIds = std::array{«type.interfaceList.eventInputs.FORTEEventTypeList»};
			«ENDIF»
		«ENDIF»
		«IF !type.interfaceList.eventOutputs.empty»
			const auto cEventOutputNames = std::array{«type.interfaceList.eventOutputs.FORTENameList»};
			«IF !type.interfaceList.eventOutputs.containsOnlyBasicEventType»
				const auto cEventOutputTypeIds = std::array{«type.interfaceList.eventOutputs.FORTEEventTypeList»};
			«ENDIF»
		«ENDIF»
		«IF !type.interfaceList.inputVars.empty»
			const auto cDataInputNames = std::array{«type.interfaceList.inputVars.FORTENameList»};
		«ENDIF»
		«IF !type.interfaceList.outputVars.empty»
			const auto cDataOutputNames = std::array{«type.interfaceList.outputVars.FORTENameList»};
		«ENDIF»
		«IF !type.interfaceList.inOutVars.empty»
			const auto cDataInOutNames = std::array{«type.interfaceList.inOutVars.FORTENameList»};
		«ENDIF»
		«IF !type.interfaceList.sockets.empty» 
			const auto cSocketNameIds = std::array{«type.interfaceList.sockets.FORTENameList»};
		«ENDIF»
		«IF !type.interfaceList.plugs.empty»
			const auto cPlugNameIds = std::array{«type.interfaceList.plugs.FORTENameList»};
		«ENDIF»
	'''
	

	def containsOnlyBasicEventType(EList<Event> events) {
		events.findFirst[!it.typeName.contentEquals(EventTypeLibrary.EVENT)] === null
	}

	// changes to this method require a recheck of the two methods generateFBInterfaceSpecSocket, generateFBInterfaceSpecPlug of AdapterFBImplTemplate
	// as there this code is duplicated
	def protected generateFBInterfaceSpecDefinition() '''
		const SFBInterfaceSpec cFBInterfaceSpec = {
		    .mEINames = «IF type.interfaceList.eventInputs.empty»{}«ELSE»cEventInputNames«ENDIF»,
		    .mEITypeNames = «IF type.interfaceList.eventInputs.empty || type.interfaceList.eventInputs.containsOnlyBasicEventType»{}«ELSE»cEventInputTypeIds«ENDIF»,
		    .mEONames = «IF type.interfaceList.eventOutputs.empty»{}«ELSE»cEventOutputNames«ENDIF»,
		    .mEOTypeNames = «IF type.interfaceList.eventOutputs.empty || type.interfaceList.eventOutputs.containsOnlyBasicEventType»{}«ELSE»cEventOutputTypeIds«ENDIF»,
		    .mDINames = «IF type.interfaceList.inputVars.empty»{}«ELSE»cDataInputNames«ENDIF»,
		    .mDONames = «IF type.interfaceList.outputVars.empty»{}«ELSE»cDataOutputNames«ENDIF»,
		    .mDIONames = «IF type.interfaceList.inOutVars.empty»{}«ELSE»cDataInOutNames«ENDIF»,
		    .mSocketNames = «IF type.interfaceList.sockets.empty»{}«ELSE»cSocketNameIds«ENDIF»,
		    .mPlugNames = «IF type.interfaceList.plugs.empty»{}«ELSE»cPlugNameIds«ENDIF»,
		};
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
			  	  	«variable.generateReadInputDataVariable»
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

	def protected generateReadInputDataVariable(VarDeclaration variable) '''
		readData(«variable.absoluteDataPortIndex», «variable.generateName», «IF variable.inOutVar»&«ENDIF»«variable.generateNameAsConnection»);
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
			  	  	«variable.generateWriteOutputDataVariable»
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

	def protected generateWriteOutputDataVariable(VarDeclaration variable) '''
		writeData(«variable.absoluteDataPortIndex», «variable.generateName», «variable.generateNameAsConnection»);
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
		«IF !type.interfaceList.sockets.empty»
			«generateAccessorDeclaration("getSocketPinUnchecked", "forte::ISocketPin *", false)»
		«ENDIF»
		«IF !type.interfaceList.plugs.empty»
			«generateAccessorDeclaration("getPlugPinUnchecked", "forte::IPlugPin *", false)»
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
		«type.interfaceList.inOutVars.generateVariableDeclarations(false)»
		«type.interfaceList.outputVars.generateVariableDeclarations(false)»
		«type.interfaceList.sockets.generateSocketDeclarations»
		«type.interfaceList.plugs.generatePlugDeclarations»
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
		«IF !type.interfaceList.sockets.empty»
			«type.interfaceList.sockets.toList.generateAccessorDefinition("getSocketPinUnchecked", "forte::ISocketPin *", false)»
		«ENDIF»
		«IF !type.interfaceList.plugs.empty»
			«type.interfaceList.plugs.toList.generateAccessorDefinition("getPlugPinUnchecked", "forte::IPlugPin *", false)»
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
		void setInitialValues() override;
	'''

	def protected generateSetInitialValuesDefinition(Iterable<VarDeclaration> variables) '''
		void «className»::setInitialValues() {
		  «baseClass»::setInitialValues();
		  «generateVariableDefaultAssignment(variables.filter[!isRetainedVariable(it)])»
		}
		
	'''

	def private boolean isRetainedVariable(VarDeclaration variable) {
		return variable.getAttributeValue(LibraryElementTags.RETAIN_ATTRIBUTE) !== null &&
			variable.getAttributeValue(LibraryElementTags.RETAIN_ATTRIBUTE).equals(RetainTag.RETAIN.string);
	}

	def protected generateVariableDefaultAssignment(Iterable<VarDeclaration> variables) '''
		«FOR variable : variables»
			«IF variable.needsGenericAccess»
				«variable.generateName».setValue(«variable.generateVariableDefaultValue»);
			«ELSE»
				«variable.generateName» = «variable.generateVariableDefaultValue»;
			«ENDIF»
		«ENDFOR»
	'''

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
			«element.generateDataConnectionDeclaration(input, internalConnection)»
		«ENDFOR»
	'''

	def protected generateDataConnectionDeclaration(VarDeclaration element, boolean input,
		boolean internalConnection) '''
		«element.generateDataConnectionType(input, internalConnection)»«element.generateNameAsConnection(internalConnection)»;
	'''

	def protected generateDataConnectionType(VarDeclaration element, boolean input, boolean internalConnection) //
	'''C«IF !input»Out«ENDIF»«IF element.inOutVar»InOut«ENDIF»DataConnection«IF !input»<«element.generateVariableTypeName»> «ELSE» *«ENDIF»'''

	def protected generateConnectionInitializer() //
	'''«type.interfaceList.eventOutputs.generateEventConnectionInitializer»«//no newline
	   »«type.interfaceList.inputVars.generateDataConnectionPointerInitializer»«//no newline
	   »«type.interfaceList.outputVars.generateDataConnectionInitializer»«//no newline
	   »«type.interfaceList.inOutVars.generateDataConnectionPointerInitializer»«//no newline
	   »«type.interfaceList.outMappedInOutVars.generateDataConnectionInitializer»'''

	def protected generateEventConnectionInitializer(List<Event> events) //
	'''«FOR event : events BEFORE ",\n" SEPARATOR ",\n"»«event.generateNameAsConnection»(*this, «events.indexOf(event)»)«ENDFOR»'''

	def protected generateDataConnectionInitializer(List<VarDeclaration> variables) {
		generateDataConnectionInitializer(variables, false);
	}

	def protected generateDataConnectionInitializer(List<VarDeclaration> variables, boolean internal) //
	'''«FOR variable : variables BEFORE ",\n" SEPARATOR ",\n"»«variable.generateNameAsConnection(internal)»(*this, «variables.indexOf(variable)», «variable.generateName»)«ENDFOR»'''

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

	def protected CharSequence generateNameAsConnection(VarDeclaration varDeclaration, boolean internal) {
		if (internal) {
			'''conn_if2in_«varDeclaration.name»'''
		} else if (varDeclaration.inOutVar) {
			'''conn_inout_«IF varDeclaration.isIsInput»in«ELSE»out«ENDIF»_«varDeclaration.name»'''
		} else {
			'''conn_«varDeclaration.name»'''
		}
	}

	def protected CharSequence generateNameAsConnection(INamedElement element, boolean internal) {
		switch (element) {
			VarDeclaration: generateNameAsConnection(element, internal)
			default: '''conn_«element.name»'''
		}
	}

	def protected CharSequence generateNameAsConnection(INamedElement element) {
		generateNameAsConnection(element, false)
	}

	def protected generateEventAccessorDefinitions() '''
		«FOR event : type.interfaceList.eventInputs BEFORE '\n' SEPARATOR '\n'»
			«event.generateEventAccessorDefinition»
		«ENDFOR»
		«IF type.interfaceList.eventInputs.size == 1»
			
			«type.interfaceList.eventInputs.first.generateEventAccessorCallOperator»
		«ENDIF»
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
