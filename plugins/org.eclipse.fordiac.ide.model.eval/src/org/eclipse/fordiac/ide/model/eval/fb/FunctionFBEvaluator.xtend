/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
import org.eclipse.fordiac.ide.model.eval.EvaluatorFactory
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.Event
import org.eclipse.fordiac.ide.model.libraryElement.FunctionBody
import org.eclipse.fordiac.ide.model.libraryElement.FunctionFBType
import org.eclipse.xtend.lib.annotations.Accessors

class FunctionFBEvaluator extends FBEvaluator<FunctionFBType> {
	@Accessors final Evaluator functionEvaluator

	new(FunctionFBType type, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(type, context, variables, parent)
		functionEvaluator = EvaluatorFactory.createEvaluator(type.body,
			type.body.eClass.instanceClass as Class<? extends FunctionBody>, null, this.variables.values, this)
	}

	override prepare() {
		functionEvaluator.prepare
	}

	override evaluate() {
		if (eventQueue === null) {
			val result = functionEvaluator.evaluate
			update(variables.values)
			result
		} else
			super.evaluate
	}

	override evaluate(Event event) {
		functionEvaluator.evaluate
		update(variables.values)
	}

	override getDependencies() {
		(super.dependencies + functionEvaluator.dependencies).toSet
	}

	override getChildren() {
		emptyMap
	}
}
