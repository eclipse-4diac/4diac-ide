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
package org.eclipse.fordiac.ide.model.eval.st

import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource

import static extension org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*

class VarDeclarationEvaluator extends StructuredTextEvaluator {
	final VarDeclaration varDeclaration

	STInitializerExpressionSource parseResult

	new(VarDeclaration varDeclaration, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(varDeclaration.name, context, variables, parent)
		this.varDeclaration = varDeclaration
	}

	override prepare() {
		if (parseResult === null && varDeclaration.initialValue !== null) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = varDeclaration.initialValue.parse(
				varDeclaration?.eResource?.URI,
				varDeclaration.featureType,
				null,
				null,
				errors,
				warnings,
				infos
			)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new IllegalArgumentException(errors.join(", "))
			}
		}
	}

	override evaluate() {
		prepare
		val result = newVariable(varDeclaration.name, parseResult?.initializerExpression?.resultType ?: varDeclaration.actualType)
		if (parseResult?.initializerExpression !== null) {
			result.evaluateInitializerExpression(parseResult.initializerExpression)
		}
		result.value
	}

	override getSourceElement() { varDeclaration }
}
