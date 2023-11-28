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

import java.util.Queue
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.variable.FBVariable
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.variable.VariableOperations
import org.eclipse.fordiac.ide.model.libraryElement.AdapterFB
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

abstract class FBEvaluator<T extends FBType> extends AbstractEvaluator {
	@Accessors final T type
	@Accessors FBEvaluatorEventQueue eventQueue

	new(T type, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(new FBVariable(CONTEXT_NAME, type, variables), parent)
		this.type = type
	}

	@Deprecated(forRemoval=true)
	new(T type, Variable<?> context, Iterable<Variable<?>> variables, Queue<Event> queue, Evaluator parent) {
		this(type, context, variables, parent)
		this.eventQueue = new FBEvaluatorEventQueueAdapter(queue, type)
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
	def abstract void evaluate(Event event)

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

	/**
	 * Get the FB instance of this evaluator
	 * 
	 * @deprecated Use {@link #getContext() getContext()} instead
	 */
	@Deprecated(forRemoval=true)
	def getInstance() { context }

	/**
	 * Get the FB instance of this evaluator
	 * 
	 * @deprecated Use {@link #getContext() getContext()} instead
	 */
	@Deprecated(forRemoval=true)
	def getQueue() {
		switch (queue : eventQueue) { FBEvaluatorEventQueueAdapter: queue.delegate }
	}

	override getSourceElement() {
		this.type
	}

	override getDependencies() {
		(type.eAllContents.toIterable.filter(VarDeclaration).flatMap [
			VariableOperations.getDependencies(it)
		] + children.values.flatMap [
			dependencies
		]).toSet
	}

	override getVariables() {
		context.value.members
	}

	override reset(Iterable<Variable<?>> variables) {
		context.value = new FBVariable(CONTEXT_NAME, type, variables).value
		update(context.value.members.values)
	}

	@Deprecated(forRemoval=true)
	@FinalFieldsConstructor
	protected static class FBEvaluatorEventQueueAdapter implements FBEvaluatorEventQueue {
		@Accessors final Queue<Event> delegate
		@Accessors final FBType type

		override receiveInputEvent() throws InterruptedException {
			if(delegate.peek.applicable) delegate.poll else null
		}

		override sendOutputEvent(Event event) {
			delegate.offer(event)
		}

		def protected boolean isApplicable(Event event) {
			event?.eContainer?.eContainer == type && event.isInput
		}
	}
}
