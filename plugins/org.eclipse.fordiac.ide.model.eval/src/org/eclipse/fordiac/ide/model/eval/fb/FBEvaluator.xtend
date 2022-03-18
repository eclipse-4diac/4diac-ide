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

import java.util.Map
import java.util.Queue
import org.eclipse.fordiac.ide.model.eval.AbstractEvaluator
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

abstract class FBEvaluator<T extends FBType> extends AbstractEvaluator {
	@Accessors final T type
	@Accessors final Queue<Event> queue
	final Map<String, Variable> variables

	new(T type, Queue<Event> queue, Iterable<Variable> variables, Evaluator parent) {
		super(parent)
		this.type = type
		this.queue = queue
		this.variables = variables?.toMap[name] ?: newHashMap;
		(type.interfaceList.inputVars + type.interfaceList.outputVars).forEach [ variable |
			this.variables.computeIfAbsent(variable.name)[newVariable(variable)]
		]
	}

	override evaluate() {
		queue?.poll.evaluate
		null
	}

	def abstract void evaluate(Event event)

	override getName() {
		type.name
	}

	override getSourceElement() {
		this.type
	}

	override getVariables() {
		variables.unmodifiableView
	}

	def protected getVariablesInternal() {
		variables
	}
}
