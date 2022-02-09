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
import org.eclipse.fordiac.ide.model.eval.st.StructuredTextEvaluator
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType

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
		algorithmEvaluator = switch (alg : type.algorithm) {
			STAlgorithm: new StructuredTextEvaluator(alg, this.variables.values, this)
			default: throw new UnsupportedOperationException('''Cannot evaluate algorithm «alg.class»''')
		}
	}

	override evaluate(Event event) {
		if (event === null || type.interfaceList.eventInputs.contains(event)) {
			algorithmEvaluator.evaluate
			queue?.addAll(type.interfaceList.eventOutputs)
		}
	}
}
