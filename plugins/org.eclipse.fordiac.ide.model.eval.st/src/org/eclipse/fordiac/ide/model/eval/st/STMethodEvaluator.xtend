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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*

@FinalFieldsConstructor
class STMethodEvaluator extends StructuredTextEvaluator {
	final STMethod method

	final Variable<?> returnVariable

	new(STMethod method, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(method.name, context, variables, parent)
		this.method = method
		method.body.varDeclarations.filter [
			it instanceof STVarInputDeclarationBlock || it instanceof STVarInputDeclarationBlock
		].flatMap[varDeclarations].reject [
			this.variables.containsKey(name) || !count.empty
		].forEach [
			evaluateVariableInitialization
		]
		if (method.returnType !== null) {
			returnVariable = newVariable(method.name, method.returnType)
			this.variables.put(returnVariable.name, returnVariable)
		} else
			returnVariable = null
	}

	override prepare() {
	}

	override evaluate() {
		prepare();
		method.evaluateStructuredTextMethod
		method.trap
		returnVariable?.value
	}

	def protected void evaluateStructuredTextMethod(STMethod method) {
		method.body.varDeclarations.reject(STVarInputDeclarationBlock).reject(STVarInOutDeclarationBlock).flatMap [
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

	override STMethod getSourceElement() {
		method
	}

	override getDependencies() {
		method.collectUsedTypes
	}
}
