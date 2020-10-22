/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Martin Jobst
 *     - initial API and implementation and/or initial documentation
 *   Alois Zoitl
 *     - Add internal var generation, fix adapter generation
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.basic

import java.nio.file.Path
import org.eclipse.fordiac.ide.export.forte_ng.ForteFBTemplate
import org.eclipse.fordiac.ide.export.forte_ng.st.STAlgorithmFilter
import org.eclipse.fordiac.ide.model.libraryElement.AdapterEvent
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.fordiac.ide.model.libraryElement.OtherAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration

class BasicFBImplTemplate extends ForteFBTemplate {

	@Accessors(PROTECTED_GETTER) BasicFBType type
	extension STAlgorithmFilter stAlgorithmFilter = new STAlgorithmFilter

	new(BasicFBType type, String name, Path prefix) {
		super(name, prefix)
		this.type = type
	}

	override generate() '''
		«generateHeader»

		«generateImplIncludes»

		«generateFBDefinition»

		«generateFBInterfaceDefinition»

		«generateFBInterfaceSpecDefinition»

		«IF !type.internalVars.isEmpty»
		  «generateInternalVarDefinition(type)»
		  
        «ENDIF»
		«IF !(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars).empty»
		«generateInitialValueAssignmentDefinition(type.interfaceList.inputVars + type.interfaceList.outputVars + type.internalVars)»
		
		«ENDIF»
		«generateAlgorithms»

		«generateStates»

		«generateECC»

	'''

	def protected generateAlgorithms() '''
		«FOR alg : type.algorithm»
			«alg.generateAlgorithm»
			
		«ENDFOR»
	'''

	def protected dispatch generateAlgorithm(Algorithm alg) {
		errors.add('''Cannot export algorithm «alg.class»''')
		return ""
	}

	def protected dispatch generateAlgorithm(OtherAlgorithm alg) '''
		void «FBClassName»::alg_«alg.name»(void) {
		  #pragma GCC warning "Algorithm of type: '«alg.language»' may lead to unexpected results!"
		  #pragma message ("warning Algorithm of type: '«alg.language»' may lead to unexpected results!")
		  «alg.text»
		}
	'''

	def protected dispatch generateAlgorithm(STAlgorithm alg) '''
		void «FBClassName»::alg_«alg.name»(void) {
		  «alg.generate(errors)»
		}
	'''

	def protected generateStates() '''
		«FOR state : type.ECC.ECState»
			«state.generateState»
			
		«ENDFOR»
	'''

	def protected generateState(ECState state) '''
		void «FBClassName»::enterState«state.name»(void) {
		  m_nECCState = scm_nState«state.name»;
		  «FOR action : state.ECAction»
		  	«IF action.algorithm !== null»
		  		alg_«action.algorithm.name»();
		  	«ENDIF»
		  	«IF action.output !== null»
		  		«action.output.generateSendEvent»
		  	«ENDIF»
		  «ENDFOR»
		}
	'''

	def protected dispatch generateSendEvent(Event event) '''
		sendOutputEvent(scm_nEvent«event.name»ID);
	'''
	
	def protected getAdapterEventName(AdapterEvent event) {
		event.name.split("\\.").get(1);
	}

	def protected dispatch generateSendEvent(AdapterEvent event) '''
		sendAdapterEvent(scm_n«event.adapterDeclaration.name»AdpNum, FORTE_«event.adapterDeclaration.typeName»::scm_nEvent«event.adapterEventName»ID);
	'''
	
	def protected generateECC() '''
		void «FBClassName»::executeEvent(int pa_nEIID){
		  bool bTransitionCleared;
		  do {
		    bTransitionCleared = true;
		    switch(m_nECCState) {
		      «FOR state : type.ECC.ECState»
		      	case scm_nState«state.name»:
		      	  «FOR transition : state.outTransitions SEPARATOR "\nelse"»
		      	  	«IF transition.conditionEvent !== null && !transition.conditionExpression.nullOrEmpty»
		      	  		if((«generateTransitionEvent(transition.conditionEvent)» == pa_nEIID) && («transition.conditionExpression.generate(type, errors)»))
		      	  	«ELSEIF transition.conditionEvent !== null»
		      	  		if(«generateTransitionEvent(transition.conditionEvent)» == pa_nEIID)
		      	  	«ELSEIF !transition.conditionExpression.nullOrEmpty»
		      	  		if(«transition.conditionExpression.generate(type, errors)»)
		      	  	«ELSE»
		      	  		if(1)
		      	  	«ENDIF»
		      	  	  enterState«transition.destination.name»();
		      	  «ENDFOR»
		      	  «IF !state.outTransitions.empty»else«ENDIF»
		      	    bTransitionCleared  = false; //no transition cleared
		      	  break;
		      «ENDFOR»
		      default:
		        DEVLOG_ERROR("The state is not in the valid range! The state value is: %d. The max value can be: «type.ECC.ECState.size».", m_nECCState.operator TForteUInt16 ());
		        m_nECCState = 0; // 0 is always the initial state
		        break;
		    }
		    pa_nEIID = cg_nInvalidEventID; // we have to clear the event after the first check in order to ensure correct behavior
		  } while(bTransitionCleared);
		}
	'''
	
	def protected dispatch generateTransitionEvent(Event event) '''
	    scm_nEvent«event.name»ID'''

    def protected dispatch generateTransitionEvent(AdapterEvent event) '''
        «EXPORT_PREFIX»«event.adapterDeclaration.name»().«event.adapterEventName»()'''
	
}
