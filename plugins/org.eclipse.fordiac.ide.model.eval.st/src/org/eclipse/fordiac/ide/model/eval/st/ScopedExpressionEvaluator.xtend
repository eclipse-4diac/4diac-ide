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
package org.eclipse.fordiac.ide.model.eval.st

import java.util.Collection
import org.eclipse.emf.ecore.EObject
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorException
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STAlgorithm
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STExpressionSource
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

@FinalFieldsConstructor
class ScopedExpressionEvaluator extends StructuredTextEvaluator {
	final String expression

	STExpressionSource parseResult

	new(String expression, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super("anonymous", context ?: parent?.context, variables ?: parent?.variables?.values, parent)
		this.expression = expression
	}

	override prepare() {
		if (parseResult === null) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = expression.parse(
				null,
				switch (type : context?.type) { FBType: type },
				additionalScope,
				errors,
				warnings,
				infos
			)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new EvaluatorException("Parse error: " + errors.join(", "), this)
			}
		}
	}

	def protected Collection<? extends EObject> getAdditionalScope() {
		switch (source:parent?.sourceElement) {
			STAlgorithm: source.body.varTempDeclarations
			STMethod: source.body.varDeclarations
			STFunction: source.varDeclarations
		}
	}

	override evaluate() {
		prepare
		parseResult.expression.evaluateExpression
	}

	override String getSourceElement() {
		expression
	}

	override getDependencies() {
		prepare
		parseResult?.collectUsedTypes
	}
}
