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
package org.eclipse.fordiac.ide.model.eval.st

import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.ECTransition
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpression
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.emf.ecore.util.EcoreUtil.getRootContainer
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

@FinalFieldsConstructor
class ECTransitionEvaluator extends StructuredTextEvaluator {
	final ECTransition transition

	STExpression parseResult

	new(ECTransition transition, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super("anonymous", context, variables, parent)
		this.transition = transition
	}

	override prepare() {
		if (parseResult === null) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = transition.conditionExpression.parse(
				switch (root : transition.rootContainer) { FBType: root },
				errors,
				warnings,
				infos
			)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new Exception("Parse error: " + errors.join(", "))
			}
		}
	}

	override evaluate() {
		prepare();
		parseResult.trap.evaluateExpression
	}

	override ECTransition getSourceElement() {
		transition
	}
}
