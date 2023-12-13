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

import java.util.List
import org.eclipse.fordiac.ide.model.data.AnyStringType
import org.eclipse.fordiac.ide.model.data.DataFactory
import org.eclipse.fordiac.ide.model.data.DataType
import org.eclipse.fordiac.ide.model.eval.Evaluator
import org.eclipse.fordiac.ide.model.eval.EvaluatorException
import org.eclipse.fordiac.ide.model.eval.variable.Variable
import org.eclipse.fordiac.ide.model.eval.variable.VariableEvaluator
import org.eclipse.fordiac.ide.model.helpers.PackageNameHelper
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STInitializerExpressionSource
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STTypeDeclaration

import static org.eclipse.fordiac.ide.model.eval.variable.VariableOperations.*

import static extension org.eclipse.fordiac.ide.model.eval.value.ValueOperations.*
import static extension org.eclipse.fordiac.ide.structuredtextalgorithm.util.StructuredTextParseUtil.*
import static extension org.eclipse.fordiac.ide.structuredtextcore.stcore.util.STCoreUtil.*
import static extension org.eclipse.xtext.EcoreUtil2.*

class VarDeclarationEvaluator extends StructuredTextEvaluator implements VariableEvaluator {
	final VarDeclaration varDeclaration

	INamedElement resultType
	STTypeDeclaration parseResultType
	STInitializerExpressionSource parseResult

	new(VarDeclaration varDeclaration, Variable<?> context, Iterable<Variable<?>> variables, Evaluator parent) {
		super(varDeclaration.name, context, variables, parent)
		this.varDeclaration = varDeclaration
	}

	override prepare() {
		prepareResultType
		prepareInitialValue
	}

	def protected prepareResultType() {
		if (parseResultType === null && varDeclaration.array) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResultType = varDeclaration.parseType(
				errors,
				warnings,
				infos
			)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResultType === null) {
				throw new IllegalArgumentException(errors.join(", "))
			}
		}
	}

	def protected prepareInitialValue() {
		if (parseResult === null && !varDeclaration.value?.value.nullOrEmpty) {
			val errors = newArrayList
			val warnings = newArrayList
			val infos = newArrayList
			parseResult = parseInitialValue(errors, warnings, infos)
			errors.forEach[error("Parse error: " + it)]
			warnings.forEach[warn("Parse warning: " + it)]
			infos.forEach[info("Parse info: " + it)]
			if (parseResult === null) {
				throw new IllegalArgumentException(errors.join(", "))
			}
		}
	}

	def protected parseInitialValue(List<String> errors, List<String> warnings, List<String> infos) {
		varDeclaration.value.value.parse(
			varDeclaration.eResource?.URI,
			evaluateResultType,
			varDeclaration.getContainerOfType(LibraryElement),
			null,
			errors,
			warnings,
			infos
		)
	}

	override evaluate() {
		evaluateVariable.value
	}

	override evaluateVariable() throws EvaluatorException, InterruptedException {
		prepareInitialValue
		val result = newVariable(varDeclaration.name, evaluateResultType)
		if (parseResult?.initializerExpression !== null) {
			result.evaluateInitializerExpression(parseResult.trap.initializerExpression)
		}
		result
	}

	override validateVariable(List<String> errors, List<String> warnings,
		List<String> infos) throws EvaluatorException, InterruptedException {
		if (!varDeclaration.value?.value.nullOrEmpty)
			parseInitialValue(errors, warnings, infos) !== null
		else
			true
	}

	override evaluateResultType() throws EvaluatorException, InterruptedException {
		if (resultType === null) {
			prepareResultType
			resultType = if (parseResultType !== null)
				parseResultType?.evaluateTypeDeclaration
			else
				varDeclaration.type
		}
		resultType
	}

	override validateResultType(List<String> errors, List<String> warnings,
		List<String> infos) throws EvaluatorException, InterruptedException {
		if (varDeclaration.array)
			varDeclaration.parseType(errors, warnings, infos) !== null
		else
			true
	}

	def protected INamedElement evaluateTypeDeclaration(STTypeDeclaration declaration) {
		val type = switch (type: declaration.type) {
			AnyStringType case declaration.maxLength !== null:
				type.newStringType(declaration.maxLength.evaluateExpression.asInteger)
			DataType:
				type
		}
		if (declaration.array)
			type.newArrayType(
				if (declaration.ranges.empty)
					declaration.count.map[DataFactory.eINSTANCE.createSubrange]
				else
					declaration.ranges.map[evaluateSubrange]
			)
		else
			type
	}

	override getDependencies() {
		(typeDependencies + initialValueDependencies).toSet
	}

	def protected getTypeDependencies() {
		if (varDeclaration.FBNetworkElement === null) {
			prepareResultType
			parseResultType?.collectUsedTypes ?: #{PackageNameHelper.getFullTypeName(varDeclaration.type)}
		} else
			emptySet
	}

	def protected getInitialValueDependencies() {
		prepareInitialValue
		parseResult?.collectUsedTypes ?: emptySet
	}

	override getSourceElement() { varDeclaration }
}
