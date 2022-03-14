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
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmMapper
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarInputDeclarationBlock
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

@FinalFieldsConstructor
class STMethodEvaluator extends StructuredTextEvaluator {
	final STMethod method

	final Variable returnVariable

	final extension STAlgorithmMapper mapper = new STAlgorithmMapper

	new(STMethod method, Iterable<Variable> variables, Evaluator parent) {
		super(method.name, variables, parent)
		this.method = method
		method.body.varDeclarations.filter(STVarInputDeclarationBlock).flatMap[varDeclarations].reject [
			this.variables.containsKey(name)
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
		returnVariable?.value
	}

	def protected void evaluateStructuredTextMethod(STMethod method) {
		method.body.varDeclarations.reject(STVarInputDeclarationBlock).flatMap[varDeclarations].forEach [
			evaluateVariableInitialization
		]
		try {
			method.body.statements.evaluateStatementList
		} catch (StructuredTextException e) {
			// return
		}
	}

	override org.eclipse.fordiac.ide.model.libraryElement.STMethod getSourceElement() {
		method.toModel as org.eclipse.fordiac.ide.model.libraryElement.STMethod
	}
}
