/*******************************************************************************
 * Copyright (c) 2019 fortiss GmbH
 *               2020 Johannes Kepler University
 *               2020 TU Wien/ACIN
 *               2022 - 2023 Martin Erich Jobst
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
 *     - adopt new ST language support
 *   Alois Zoitl
 *     - Add internal var generation, fix adapter generation
 *   Martin Melik Merkumians - adds generation of initial value assignment
 *******************************************************************************/
package org.eclipse.fordiac.ide.export.forte_ng.basic

import java.nio.file.Path
import java.util.Map
import java.util.Set
import org.eclipse.fordiac.ide.export.forte_ng.base.BaseFBImplTemplate
import org.eclipse.fordiac.ide.export.language.ILanguageSupport
import org.eclipse.fordiac.ide.export.language.ILanguageSupportFactory
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement

class BasicFBImplTemplate extends BaseFBImplTemplate<BasicFBType> {
	final Map<ECTransition, ILanguageSupport> transitionLanguageSupport

	new(BasicFBType type, String name, Path prefix) {
		super(type, name, prefix, "CBasicFB")
		transitionLanguageSupport = type.ECC.ECTransition.toInvertedMap [
			ILanguageSupportFactory.createLanguageSupport("forte_ng", it)
		]
	}

	override generate() '''
		«super.generate»
		
		«generateStates»
		
	'''

	def protected generateStates() '''
		«FOR state : type.ECC.ECState»
			«state.generateState»
			
		«ENDFOR»
	'''

	def protected generateState(ECState state) '''
		void «FBClassName»::enterState«state.name»(void) {
		  m_nECCState = «state.generateStateName»;
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

	override generateExecuteEvent() '''
		void «FBClassName»::executeEvent(int pa_nEIID){
		  do {
		    switch(m_nECCState) {
		      «FOR state : type.ECC.ECState»
		      	case «state.generateStateName»:
		      	  «FOR transition : state.outTransitions SEPARATOR "\nelse"»
		      	  	if(«transition.generateTransitionCondition») enterState«transition.destination.name»();
		      	  «ENDFOR»
		      	  «IF !state.outTransitions.empty»else «ENDIF»return; //no transition cleared
		      	  «IF !state.outTransitions.empty»break;«ENDIF»
		      «ENDFOR»
		      default:
		        DEVLOG_ERROR("The state is not in the valid range! The state value is: %d. The max value can be: «type.ECC.ECState.size».", m_nECCState.operator TForteUInt16 ());
		        m_nECCState = 0; // 0 is always the initial state
		        return;
		    }
		    pa_nEIID = cg_nInvalidEventID; // we have to clear the event after the first check in order to ensure correct behavior
		  } while(true);
		}
	'''

	def protected generateTransitionCondition(ECTransition transition) {
		switch (it : transition) {
			case conditionEvent !== null && !conditionExpression.nullOrEmpty: //
			'''(«generateTransitionEvent(transition.conditionEvent)» == pa_nEIID) && («transitionLanguageSupport.get(transition)?.generate(emptyMap)»)'''
			case conditionEvent !== null: //
			'''«generateTransitionEvent(transition.conditionEvent)» == pa_nEIID'''
			case !conditionExpression.nullOrEmpty:
				transitionLanguageSupport.get(transition)?.generate(emptyMap)
			default:
				"1"
		}
	}

	def protected generateTransitionEvent(Event event) {
		if (event.FBNetworkElement instanceof AdapterFB) {
			return '''«EXPORT_PREFIX»«event.FBNetworkElement.name»().«event.name»()'''
		}
		event.generateEventName
	}

	def protected generateStateName(ECState state) '''scm_nState«state.name»'''

	override getErrors() {
		(super.getErrors + transitionLanguageSupport.values.filterNull.flatMap [
			getErrors
		].toSet).toList
	}

	override getWarnings() {
		(super.getWarnings + transitionLanguageSupport.values.filterNull.flatMap [
			getWarnings
		].toSet).toList
	}

	override getInfos() {
		(super.getInfos + transitionLanguageSupport.values.filterNull.flatMap [
			getInfos
		].toSet).toList
	}

	override Set<INamedElement> getDependencies(Map<?, ?> options) {
		(super.getDependencies(options) + transitionLanguageSupport.values.filterNull.flatMap [
			getDependencies(options)
		]).toSet
	}
}
