/*******************************************************************************
 * Copyright (c) 2022-2023 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.model.eval.fb

import java.util.Map
import java.util.Queue
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.value.ECStateValue
import org.eclipse.fordiac.ide.model.eval.variable.ECStateVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType
import org.eclipse.fordiac.ide.model.libraryElement.ECState
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.Event

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.asBoolean

class BasicFBEvaluator extends BaseFBEvaluator<BasicFBType> {
	final Map<ECTransition, Evaluator> transitionEvaluators

	new(BasicFBType type, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(type, context, variables, parent)
		transitionEvaluators = type.ECC.ECTransition.filter[!it.conditionExpression.nullOrEmpty].toInvertedMap [
			EvaluatorFactory.createEvaluator(it, eClass.instanceClass as Class<? extends ECTransition>, this.context,
				emptySet, this)
		]
	}

	@Deprecated(forRemoval=true)
	new(BasicFBType type, Variable<?> context, Iterable<Variable<?>> variables, Queue<Event> queue, Evaluator parent) {
		super(type, context, variables, queue, parent)
		transitionEvaluators = type.ECC.ECTransition.filter[!it.conditionExpression.nullOrEmpty].toInvertedMap [
			EvaluatorFactory.createEvaluator(it, eClass.instanceClass as Class<? extends ECTransition>, this.context,
				emptySet, this)
		]
	}

	override evaluate(Event event) {
		for (var transition = state.evaluateTransitions(event); transition !== null; transition = state.
			evaluateTransitions(null)) {
			transition.destination.evaluateState
		}
	}

	def private evaluateTransitions(ECState state, Event event) {
		state.outTransitions.findFirst [ transition |
			(transition.conditionEvent === null || transition.conditionEvent == event) &&
				(transition.conditionExpression.nullOrEmpty || transitionEvaluators.get(transition).evaluate.asBoolean)
		]
	}

	def private evaluateState(ECState state) {
		this.state = state
		state.trap.ECAction.forEach [
			algorithmEvaluators.get(algorithm)?.evaluate
			if(output !== null) eventQueue?.sendOutputEvent(output)
			update(variables.values)
		]
	}

	def ECState getState() {
		(context.members.get(ECStateVariable.NAME) as ECStateVariable).value.state
	}

	def Map<ECTransition, Evaluator> getTransitionEvaluators() {
		transitionEvaluators
	}

	def void setState(ECState state) {
		(context.members.get(ECStateVariable.NAME) as ECStateVariable).value = new ECStateValue(state)
	}

	override getDependencies() {
		(super.dependencies + transitionEvaluators.values.flatMap [
			dependencies
		]).toSet
	}
}
