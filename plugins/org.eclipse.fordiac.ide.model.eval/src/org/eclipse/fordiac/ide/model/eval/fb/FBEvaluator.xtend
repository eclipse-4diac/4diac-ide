/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.xtend.lib.annotations.Accessors

abstract class FBEvaluator<T extends FBType> extends AbstractEvaluator {
	@Accessors final T type
	@Accessors final Queue<Event> queue
	@Accessors final FBVariable instance

	new(T type, Variable<?> context, Iterable<Variable<?>> variables, Queue<Event> queue, Evaluator parent) {
		super(context, parent)
		this.type = type
		this.queue = queue
		this.instance = new FBVariable(CONTEXT_NAME, type, variables)
	}

	override evaluate() {
		while (queue?.peek.applicable) {
			queue.poll.evaluate
		}
		null
	}

	def abstract void evaluate(Event event)

	def boolean isApplicable(Event event) {
		event?.eContainer?.eContainer == type && event.isInput
	}

	override getName() {
		type.name
	}

	override getSourceElement() {
		this.type
	}

	override getVariables() {
		instance.value.members
	}
}
