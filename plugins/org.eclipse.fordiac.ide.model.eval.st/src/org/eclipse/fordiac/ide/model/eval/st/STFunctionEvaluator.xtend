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
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInOutDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil.*

@FinalFieldsConstructor
class STFunctionEvaluator extends StructuredTextEvaluator {
	final STFunction function

	final Variable<?> returnVariable

	new(STFunction function, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(function.name, null, variables, parent)
		this.function = function
		function.varDeclarations.filter [
			it instanceof STVarInputDeclarationBlock || it instanceof STVarInputDeclarationBlock
		].flatMap[varDeclarations].reject [
			this.variables.containsKey(name) || !count.empty
		].forEach [
			evaluateVariableInitialization
		]
		if (function.returnType !== null) {
			returnVariable = newVariable(function.name, function.returnType)
			this.variables.put(returnVariable.name, returnVariable)
		} else
			returnVariable = null
	}

	override prepare() {
	}

	override evaluate() {
		prepare();
		function.evaluateStructuredTextFunction
		function.trap
		returnVariable?.value
	}

	def protected void evaluateStructuredTextFunction(STFunction function) {
		function.varDeclarations.reject(STVarInputDeclarationBlock).reject(STVarInOutDeclarationBlock).flatMap [
			varDeclarations
		].forEach [
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

	override STFunction getSourceElement() {
		function
	}

	override getDependencies() {
		function.collectUsedTypes
	}
}
