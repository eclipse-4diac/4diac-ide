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
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

@FinalFieldsConstructor
class STAlgorithmEvaluator extends StructuredTextEvaluator {
	final org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm

	STAlgorithm parseResult

	new(org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm algorithm, Variable<?> context,
		Iterable<Variable<?>> variables, Evaluator parent) {
		super(algorithm.name, context, variables, parent)
		this.algorithm = algorithm
	}

	override prepare() {
		if (parseResult === null) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = algorithm.parse(errors, warnings, infos)
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
		parseResult.evaluateStructuredTextAlgorithm
		parseResult.trap
		null
	}

	def protected void evaluateStructuredTextAlgorithm(STAlgorithm alg) {
		alg.body.varTempDeclarations.flatMap[varDeclarations].forEach[evaluateVariableInitialization]
		try {
			alg.body.statements.evaluateStatementList
		} catch (StructuredTextException e) {
			// return
		}
	}

	override STAlgorithm getSourceElement() {
		parseResult
	}

	override getDependencies() {
		prepare
		parseResult?.collectUsedTypes
	}
}
