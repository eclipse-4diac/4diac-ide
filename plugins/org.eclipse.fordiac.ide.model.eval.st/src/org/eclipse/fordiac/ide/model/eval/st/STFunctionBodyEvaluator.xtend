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
import org.eclipse.fordiac.ide.model.libraryElement.STFunctionBody
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil.*

class STFunctionBodyEvaluator extends StructuredTextEvaluator {
	static final String RETURN_VARIABLE_NAME = ""

	final STFunctionBody body

	STFunctionSource parseResult
	Variable<?> returnVariable

	new(STFunctionBody body, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(if (parent !== null) '''«parent.name».«parent.name»''' else "body", context, variables, parent)
		this.body = body
	}

	override prepare() {
		if (parseResult === null) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = body.parse(errors, warnings, infos)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new Exception("Parse error: " + errors.join(", "))
			}
			parseResult.functions.head?.prepareStructuredTextFunction
		}
	}

	override evaluate() {
		prepare
		sourceElement.evaluateStructuredTextFunction
		sourceElement.trap
		returnVariable?.value
	}

	def protected void prepareStructuredTextFunction(STFunction function) {
		function.varDeclarations.filter[input].flatMap[varDeclarations].reject [
			variables.containsKey(name) || !count.empty
		].forEach [
			evaluateVariableInitialization
		]
		if (function.returnType !== null) {
			returnVariable = variables.get(RETURN_VARIABLE_NAME)
			if (returnVariable === null) {
				returnVariable = newVariable(RETURN_VARIABLE_NAME, function.returnType)
				this.variables.put(RETURN_VARIABLE_NAME, returnVariable)
			}
		}
	}

	def protected void evaluateStructuredTextFunction(STFunction function) {
		function.varDeclarations.reject[input].flatMap [
			varDeclarations
		].forEach [
			if (variables.containsKey(name))
				variables.get(name).evaluateInitializerExpression(defaultValue)
			else
				evaluateVariableInitialization
		]
		function.varDeclarations.flatMap[varDeclarations].reject[this.variables.containsKey(name)].forEach [
			throw new IllegalStateException('''Must pass variable «name» as argument to «function.name»''')
		]
		try {
			function.code.evaluateStatementList
		} catch (StructuredTextException e) {
			// return
		}
	}

	def protected boolean isInput(STVarDeclarationBlock block) {
		block instanceof STVarInputDeclarationBlock || block instanceof STVarInOutDeclarationBlock
	}

	override STFunction getSourceElement() {
		parseResult?.functions?.head
	}

	override getDependencies() {
		prepare
		parseResult?.collectUsedTypes
	}
}
