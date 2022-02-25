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
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType
import org.eclipse.fordiac.ide.model.libraryElement.Algorithm

class SimpleFBEvaluator extends FBEvaluator<SimpleFBType> {
	Evaluator algorithmEvaluator

	new(SimpleFBType type, Evaluator parent) {
		this(type, null, parent)
	}

	new(SimpleFBType type, Iterable<Variable> variables, Evaluator parent) {
		this(type, null, variables, parent)
	}

	new(SimpleFBType type, Queue<Event> queue, Iterable<Variable> variables, Evaluator parent) {
		super(type, queue, variables, parent)
		algorithmEvaluator = EvaluatorFactory.createEvaluator(type.algorithm.head,
			type.algorithm.head.eClass.instanceClass as Class<? extends Algorithm>, getVariables.values, this)
		if (algorithmEvaluator === null) {
			throw new UnsupportedOperationException('''Cannot evaluate algorithm «type.algorithm.head.eClass.name»''')
		}
	}

	override prepare() {
		algorithmEvaluator.prepare
	}

	override evaluate(Event event) {
		if (event === null || type.interfaceList.eventInputs.contains(event)) {
			algorithmEvaluator.evaluate
			queue?.addAll(type.interfaceList.eventOutputs)
		}
	}
}
