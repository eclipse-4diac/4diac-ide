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
import org.eclipse.fordiac.ide.structuredtextalgorithm.stalgorithm.STMethod
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

class STMethodEvaluator extends StructuredTextEvaluator {
	final org.eclipse.fordiac.ide.model.libraryElement.STMethod method

	STMethod parseResult
	Variable<?> returnVariable

	new(org.eclipse.fordiac.ide.model.libraryElement.STMethod method, Variable<?> context,
		Iterable<Variable<?>> variables, Evaluator parent) {
		super(method.name, context, variables, parent)
		this.method = method
	}

	new(STMethod method, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(method.name, context, variables, parent)
		this.method = null
		parseResult = method
	}

	override prepare() {
		if (parseResult === null) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = method?.parse(errors, warnings, infos)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new Exception("Parse error: " + errors.join(", "))
			}
		}
	}

	override evaluate() {
		prepare
		parseResult.prepareStructuredTextMethod
		parseResult.evaluateStructuredTextMethod
		parseResult.trap
		returnVariable?.value
	}

	def protected void prepareStructuredTextMethod(STMethod method) {
		method.body.varDeclarations.filter[input].flatMap[varDeclarations].reject [
			variables.containsKey(name) || !count.empty
		].forEach [
			evaluateVariableInitialization
		]
		if (method.returnType !== null) {
			returnVariable = newVariable(method.name, method.returnType)
			variables.put(returnVariable.name, returnVariable)
		}
	}

	def protected void evaluateStructuredTextMethod(STMethod method) {
		method.body.varDeclarations.reject[input].flatMap [
			varDeclarations
		].forEach [
			evaluateVariableInitialization
		]
		method.body.varDeclarations.flatMap[varDeclarations].reject[this.variables.containsKey(name)].forEach [
			throw new IllegalStateException('''Must pass variable «name» as argument to «method.name»''')
		]
		try {
			method.body.statements.evaluateStatementList
		} catch (StructuredTextException e) {
			// return
		}
	}

	def protected boolean isInput(STVarDeclarationBlock block) {
		block instanceof STVarInputDeclarationBlock || block instanceof STVarInOutDeclarationBlock
	}

	override STMethod getSourceElement() {
		parseResult
	}

	override getDependencies() {
		prepare
		parseResult?.collectUsedTypes
	}
}
