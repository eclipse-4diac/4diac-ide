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

import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorException
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors

abstract class FBEvaluator<T extends FBType> extends AbstractEvaluator {
	@Accessors final T type
	@Accessors FBEvaluatorEventQueue eventQueue

	new(T type, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(new FBVariable(CONTEXT_NAME, type, variables), parent)
		this.type = type
	}

	override evaluate() {
		var Event event
		while ((event = eventQueue?.receiveInputEvent) !== null) {
			if (!event.applicable) {
				throw new UnsupportedOperationException('''The event «event.name» is not applicable for this evaluator''')
			}
			event.evaluate
		}
		null
	}

	/**
	 * Evaluate a single event
	 */
	def abstract void evaluate(Event event) throws EvaluatorException, InterruptedException

	/**
	 * Determine if the event is applicable to the current evaluator
	 */
	def boolean isApplicable(Event event) {
		switch (container: event?.eContainer?.eContainer) {
			case type: event.isInput
			AdapterFB: container.adapterDecl?.eContainer?.eContainer == type && !event.isInput
			default: false
		}
	}

	override getName() {
		type.name
	}

	override FBVariable getContext() {
		super.context as FBVariable
	}

	override getSourceElement() {
		this.type
	}

	override getDependencies() {
		(type.eAllContents.toIterable.filter(VarDeclaration).flatMap [
			VariableOperations.getDependencies(it)
		]).toSet
	}

	override getVariables() {
		context.value.members
	}

	override reset(Iterable<Variable<?>> variables) {
		context.value = new FBVariable(CONTEXT_NAME, type, variables).value
		update(context.value.members.values)
	}
}
