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

import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.SimpleFBType

class SimpleFBEvaluator extends BaseFBEvaluator<SimpleFBType> {
	new(SimpleFBType type, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(type, context, variables, parent)
	}

	override evaluate(Event event) {
		val algorithm = if(event !== null) type.getAlgorithmNamed(event.name) else type.algorithm.head
		if (algorithm !== null) {
			algorithmEvaluators.get(algorithm)?.evaluate
		}
		eventQueue?.sendOutputEvents(type.interfaceList.eventOutputs)
		update(variables.values)
	}
}
