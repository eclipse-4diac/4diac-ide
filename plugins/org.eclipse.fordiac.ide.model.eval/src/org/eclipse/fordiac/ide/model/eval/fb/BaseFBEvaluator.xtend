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
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm
import org.eclipse.fordiac.ide.model.libraryElement.BaseFBType
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.xtend.lib.annotations.Accessors

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

abstract class BaseFBEvaluator<T extends BaseFBType> extends FBEvaluator<T> {
	@Accessors final Map<Algorithm, Evaluator> algorithmEvaluators

	new(T type, Queue<Event> queue, Iterable<Variable> variables, Evaluator parent) {
		super(type, queue, variables, parent)
		type.internalVars.forEach [ variable |
			this.variablesInternal.computeIfAbsent(variable.name)[newVariable(variable)]
		]
		algorithmEvaluators = type.algorithm.toInvertedMap [
			EvaluatorFactory.createEvaluator(it, eClass.instanceClass as Class<? extends Algorithm>,
				getVariables.values, this)
		]
	}
	
	override prepare() {
		algorithmEvaluators.values.forEach[prepare]
	}
	
	override getChildren() {
		algorithmEvaluators
	}
}
